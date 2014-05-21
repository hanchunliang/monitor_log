package com.sinosoft.one.monitor.application.domain;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.MapMaker;
import com.sinosoft.one.monitor.application.model.Application;
import javax.annotation.PostConstruct;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sinosoft.one.monitor.application.model.EumUrl;
import com.sinosoft.one.monitor.application.model.EumUrlAva;
import com.sinosoft.one.monitor.application.model.EumUrlAvaSta;
import com.sinosoft.one.monitor.application.repository.EumUrlAvaRepository;
import com.sinosoft.one.monitor.application.repository.EumUrlAvaStaRepository;
import com.sinosoft.one.monitor.application.repository.EumUrlRepository;
import com.sinosoft.one.monitor.common.Trend;
import com.sinosoft.one.monitor.utils.AvailableCalculate;

/**
 * 仿真URL服务对象
 * User: cq
 * Date: 13-3-6
 * Time: AM11:37
 */
@Service
@Lazy(false)
public class ApplicationEmuService {

    @Autowired
    private EumUrlRepository eumUrlRepository;

    @Autowired
    private EumUrlAvaRepository eumUrlAvaRepository;

    @Autowired
    private EumUrlAvaStaRepository eumUrlAvaStaRepository;

    private Logger logger = LoggerFactory.getLogger(ApplicationEmuService.class);

    @Autowired
    private ApplicationService applicationService;

    public ScheduledExecutorService scheduledExecutorService =  Executors.newScheduledThreadPool(10);

    /**
     * 线程池缓存
     */
    private static ConcurrentMap<String, ScheduledFuture<?>> threadHolderMap = new MapMaker().concurrencyLevel(32).makeMap();

    private static ConcurrentMap<String,ApplicationAvailableInf> applicationAvailableInfCache = new MapMaker().concurrencyLevel(32).makeMap();


    @PostConstruct
    void init(){
        List<Application> applications = applicationService.findValidateApplication();
        for(Application application:applications){
            execute(application);
        }
    }
    public void cancelApplicationThread(String applicationId){
        ScheduledFuture scheduledFuture = threadHolderMap.get(applicationId);
        if(scheduledFuture!=null){
            scheduledFuture.cancel(true);
            threadHolderMap.remove(applicationId);
            applicationAvailableInfCache.remove(applicationId);
        }
    }
    void execute(Application application){
        final String appId =application.getId();
        int interval = application.getInterval().intValue();
        ScheduledFuture<?>  scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    List<EumUrl> eumUrls = eumUrlRepository.findByApplication_Id(appId);
                    if(eumUrls.isEmpty())
                        return;
                    int  count = 0;
                    int  avCount = 0;
                    for(EumUrl eumUrl:eumUrls){
                        UrlAvailableInf urlAvailableInf =  getUrlAvailableToday(eumUrl.getUrlId());
                        count+=urlAvailableInf.getCount();
                        avCount+=urlAvailableInf.getAvailableCount();
                    }
                    ApplicationAvailableInf applicationAvailableInf=new  ApplicationAvailableInf(calTrend(eumUrls),count,avCount);
                    applicationAvailableInfCache.put(appId,applicationAvailableInf);
                }catch (Throwable t){}
            }
        },0,interval, TimeUnit.MINUTES);
        threadHolderMap.put(appId,scheduledFuture);
    }
    boolean checkExist(String applicationId){
        ApplicationAvailableInf availableInf = applicationAvailableInfCache.get(applicationId);
        if (availableInf==null)
            return false;
        return true;
    }
    void addApplicationInCache(String applicationId,ApplicationAvailableInf applicationAvailableInf){
        applicationAvailableInfCache.put(applicationId,applicationAvailableInf);
        execute(applicationService.findApplication(applicationId));
    }



    /**
     * enumUrl缓存
     */
    private LoadingCache<String,EumUrl> enumUrlCache = CacheBuilder.newBuilder().build(new CacheLoader<String,EumUrl>(){
        @Override
        public EumUrl load(String urlId) throws Exception {
            List<EumUrl> eumUrls = eumUrlRepository.findByUrlId(urlId);
            if (eumUrls.isEmpty())
                throw new IllegalArgumentException("urlId is " + urlId + ",can't find eumUrl");
            if (eumUrls.size() > 1)
                throw new MutileEumUrlException();
            return eumUrls.get(0);
        }
    });


    /**
     * 查询当天的仿真URL统计数据，如果没有会默认返回初始化的仿真URL统计数据
     * @param eumUrlId
     * @return
     */
    public EumUrlAvaSta getTodayEumUrlStatistics(String eumUrlId){
        return getEumUrlStatisticsByEnumIdAndDate(eumUrlId,LocalDate.now().toDate());
    }

    EumUrlAvaSta getEumUrlStatisticsByEnumIdAndDate(String eumUrlId,Date date){
        List<EumUrlAvaSta> eumUrlAvaStas = eumUrlAvaStaRepository.findByRecordTimeAndEumUrlId(date,eumUrlId);
        return eumUrlAvaStas.isEmpty()?newEumUrlAvaSta():eumUrlAvaStas.get(0);
    }

    public EumUrlAva getTodayLatestEumUrlAva(String eumUrlId){
        Assert.hasText(eumUrlId);
        Sort desc = new Sort(Sort.Direction.DESC,"recordTime");
        Pageable pageDesc = new PageRequest(0,1,desc);
        List<EumUrlAva> eumUrlAvas = eumUrlAvaRepository.findByEumUrlId(eumUrlId, pageDesc).getContent();
        if(eumUrlAvas.isEmpty())
            return  null;
        return eumUrlAvas.get(0);
    }

    public EumUrlAva getEumUrlAvaTodayAndLatestAndUnavailable(String eumUrlId){
        Assert.hasText(eumUrlId);
        Sort desc = new Sort(Sort.Direction.DESC,"recordTime");
        Pageable pageDesc = new PageRequest(0,1,desc);
        List<EumUrlAva> eumUrlAvas = eumUrlAvaRepository.findByEumUrlIdAndState(eumUrlId,"0", pageDesc).getContent();
        if(eumUrlAvas.isEmpty())
            return  null;
        return eumUrlAvas.get(0);
    }

    public EumUrlAva getTodayFirstEumUrlAva(String eumUrlId){
        Sort asc = new Sort("recordTime");
        Pageable pageAsc = new PageRequest(0,1,asc);
        List<EumUrlAva> eumUrlAvas =eumUrlAvaRepository.findByEumUrlId(eumUrlId, pageAsc).getContent();
        if(eumUrlAvas.isEmpty())
            return null;
        return eumUrlAvas.get(0);
    }

    private EumUrlAvaSta newEumUrlAvaSta(){
        EumUrlAvaSta eumUrlAvaSta = new EumUrlAvaSta();
        eumUrlAvaSta.setRecordTime(new Date());
        eumUrlAvaSta.setNormalRuntime(BigDecimal.ZERO);
        eumUrlAvaSta.setTotalFailureTime(BigDecimal.ZERO);
        eumUrlAvaSta.setFailureCount(BigDecimal.ZERO);
        return eumUrlAvaSta;
    }

    public ApplicationAvailableInf getApplicationAvailableToday(String applicationId) throws EumUrlsNotFoundException {
        Assert.hasText(applicationId);
        long s = System.currentTimeMillis();
        List<EumUrl> eumUrls = eumUrlRepository.findByApplication_Id(applicationId);
        logger.debug("eumUrlRepository.findByApplication_Id(applicationId);"+(System.currentTimeMillis()-s));
        if(eumUrls.isEmpty())
            throw new EumUrlsNotFoundException("application Id is "+applicationId+" not found any eumUrls!");
        int  count = 0;
        int  avCount = 0;
        s = System.currentTimeMillis();
        for(EumUrl eumUrl:eumUrls){
            UrlAvailableInf urlAvailableInf =  getUrlAvailableToday(eumUrl.getUrlId());
            count+=urlAvailableInf.getCount();
            avCount+=urlAvailableInf.getAvailableCount();
        }
        logger.debug("loop List<EumUrl>:"+(System.currentTimeMillis()-s));
        return new  ApplicationAvailableInf(calTrend(eumUrls),count,avCount);
    }
    public ApplicationAvailableInf getAppAvailableToday(String applicationId) throws EumUrlsNotFoundException {
        Assert.hasText(applicationId);
        boolean hasExist = checkExist(applicationId);
        if (hasExist){
            return applicationAvailableInfCache.get(applicationId);
        }
        ApplicationAvailableInf availableInf = getApplicationAvailableToday(applicationId);
        addApplicationInCache(applicationId,availableInf);
        return availableInf;
    }

    public Trend urlAvaTrendByUrlId(String urlId){
        EumUrl eumUrl = getEumUrlByUrlId(urlId);
        return calTrend(Lists.newArrayList(eumUrl));
    }


    public UrlAvailableInf getUrlAvailableToday(String urlId){
        EumUrl eumUrl = getEumUrlByUrlId(urlId);
	    EumUrlAva eumUrlAva = getTodayFirstEumUrlAva(eumUrl.getId());
        Interval interval = new Interval(new DateTime(eumUrlAva == null ? LocalDate.now().toDate(): eumUrlAva.getRecordTime()), DateTime.now());
	    eumUrlAva = getEumUrlAvaTodayAndLatestAndUnavailable(eumUrl.getId());
        return new UrlAvailableInf(urlAvaTrendByUrlId(urlId),
                eumUrlAvaRepository.countByEmuId(eumUrl.getId()),
                eumUrlAvaRepository.countByEmuIdAndStatus(eumUrl.getId(),"1"),
                interval.toPeriod(), eumUrlAva == null ? null : eumUrlAva.getRecordTime(),
                eumUrl.getUrl());

    }

    EumUrl getEumUrlByUrlId(String urlId) {
        Assert.hasText(urlId);
        try {
            return enumUrlCache.get(urlId);
        } catch (ExecutionException e) {
          throw new RuntimeException(e);
        }
    }


    List<TimeQuantumAvailableInfo> findAvailableStatisticsByUrlId(String urlId) {
        Assert.hasText(urlId);
        DateTime now = DateTime.now().withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        DateTime prev = now.minusHours(6);
        List<TimeQuantumAvailableStatistics>  list = eumUrlAvaRepository.statisticsByEumUrlIdAndRecordTime(getEumUrlByUrlId(urlId).getId(),prev.toDate(),now.toDate());
        Map<String,TimeQuantumAvailableInfo> map = Maps.newHashMap();
        for(TimeQuantumAvailableStatistics statistics:list){
            TimeQuantumAvailableInfo timeQuantumAvailableInfo = null;
            if(map.get(statistics.getTimeQuantum())==null){
                 timeQuantumAvailableInfo = new TimeQuantumAvailableInfo();
                if(statistics.getStatus().equals("1")){
                    timeQuantumAvailableInfo.setAvaCount(statistics.getCount());
                }
                timeQuantumAvailableInfo.setCount(statistics.getCount());
	            timeQuantumAvailableInfo.setTimeQuantum(statistics.getTimeQuantum());
                map.put(statistics.getTimeQuantum(),timeQuantumAvailableInfo);
            }else{
                timeQuantumAvailableInfo =map.get(statistics.getTimeQuantum());
                if(statistics.getStatus().equals("1")){
                    timeQuantumAvailableInfo.setAvaCount(timeQuantumAvailableInfo.getAvaCount() + statistics.getCount());
                }
                int count = timeQuantumAvailableInfo.getCount()+statistics.getCount();
                timeQuantumAvailableInfo.setCount(count);
                timeQuantumAvailableInfo.setTimeQuantum(statistics.getTimeQuantum());
            }
        }
        return Lists.newArrayList(map.values());
    }


    void saveEnumUrlAvailableDetail(String eumUrlId,boolean result,BigDecimal interval,Date recordTime){
        deleteEnumUrlAvaData(eumUrlId);
        EumUrlAva eumUrlAva = new EumUrlAva();
        eumUrlAva.setEumUrlId(eumUrlId);
        eumUrlAva.setInterval(interval);
        //eumUrlAva.setRecordTime(DateTime.now().toDate());
        eumUrlAva.setRecordTime(recordTime);
        eumUrlAva.setState(result?"1":"0");
        eumUrlAvaRepository.save(eumUrlAva);
    }

    void deleteEnumUrlAvaData(String eumUrlId){
        EumUrlAva eumAvaLast = getTodayLatestEumUrlAva(eumUrlId);
        if(eumAvaLast!=null){
            LocalDate prevDate = new LocalDate(eumAvaLast.getRecordTime());
            if(prevDate.compareTo(LocalDate.now())<0){
                eumUrlAvaRepository.deleteByLessThanDate(LocalDate.now().minusDays(1).toDate());
            }
        }
    }

    List<EumUrl> findEumUrlByApplicationId(String applicationId){
        return  eumUrlRepository.findByApplication_Id(applicationId);
    }


    private Trend calTrend(List<EumUrl> eumUrls ){
        long start = System.currentTimeMillis();
        int yesterdayCount=0;
        int todayCount=0;
        Date yesterday = LocalDate.now().minusDays(1).toDate();
        for(EumUrl eumUrl:eumUrls){
            todayCount += getTodayEumUrlStatistics(eumUrl.getId()).getTotalFailureTime().intValue();
            yesterdayCount+= getEumUrlStatisticsByEnumIdAndDate(eumUrl.getId(),yesterday).getTotalFailureTime().intValue();
        }
        logger.debug("calTrend consume:{}",System.currentTimeMillis()-start);
        if(yesterdayCount>todayCount){
            return Trend.RISE;
        }
        else if(yesterdayCount<todayCount){
            return Trend.DROP;
        }
        else
            return Trend.SAME;
    }


    public void saveEnumUrlAvailableStatistics(String eumUrlId, EumUrlAvaSta eumUrlAvaSta, boolean result,BigDecimal interval) {
        Assert.hasText(eumUrlId);
        Assert.notNull(interval);

        List<AvailableCalculate.AvailableCountsGroupByInterval> avaCount = eumUrlAvaRepository.countsGroupByInterval(eumUrlId,"1");
        List<AvailableCalculate.AvailableCountsGroupByInterval> unAvaCount = eumUrlAvaRepository.countsGroupByInterval(eumUrlId,"0");
        EumUrlAva eumAvaLast = getTodayLatestEumUrlAva(eumUrlId);

        AvailableCalculate.AvailableCalculateParam availableCalculateParam =  new AvailableCalculate.AvailableCalculateParam(
                new AvailableCalculate.AvailableStatistics( eumUrlAvaSta.getNormalRuntime().longValue(),
                        eumUrlAvaSta.getTotalFailureTime().longValue(), eumUrlAvaSta.getFailureCount().intValue()),
                avaCount,unAvaCount,interval.intValue(),result,
                eumAvaLast == null?null:new AvailableCalculate.AvailableInf(
                        eumAvaLast.getRecordTime(), eumAvaLast.getState().equals("1"),
                        eumAvaLast.getInterval().intValue())
        );
        AvailableCalculate avaResult = AvailableCalculate.calculate(availableCalculateParam);

        eumUrlAvaSta.setTotalFailureTime(avaResult.getStopTime());
        eumUrlAvaSta.setFailureCount(avaResult.getFalseCount());
        eumUrlAvaSta.setAvgFailureTime(avaResult.getTimeBetweenFailures());
        eumUrlAvaSta.setNormalRuntime(avaResult.getAliveTime());
        eumUrlAvaSta.setEumUrlId(eumUrlId);

        eumUrlAvaStaRepository.save(eumUrlAvaSta);
    }

}
