package com.sinosoft.one.monitor.application.domain;

import com.google.common.collect.MapMaker;
import com.sinosoft.one.monitor.application.model.*;
import com.sinosoft.one.monitor.application.repository.EumUrlAvaRepository;
import com.sinosoft.one.monitor.application.repository.EumUrlAvaStaRepository;
import com.sinosoft.one.monitor.common.*;
import com.sinosoft.one.monitor.db.oracle.domain.StaTimeEnum;
import com.sinosoft.one.monitor.utils.AvailableCalculate;
import com.sinosoft.one.monitor.utils.ResponseUtil;
import com.sinosoft.one.util.thread.ThreadUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

/**
 * 业务仿真
 * User: ChengQi
 * Date: 13-3-4
 * Time: PM3:44
 */
@Component
@Lazy(false)
public class BusinessEmulation {

    private static Logger logger = LoggerFactory.getLogger(BusinessEmulation.class);

    private static final int CORE_POOL_SIZE = 200;

    //default_interval set is 5m*60sec
    private static final int DEFAULT_INTERVAL = 5*60;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(CORE_POOL_SIZE, new ThreadUtils.CustomizableThreadFactory("appEMU"));

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationEmuService applicationEmuService;

	@Autowired
	private AlarmMessageBuilder alarmMessageBuilder;

    private static ConcurrentMap<String, Investigation> holders = new MapMaker().concurrencyLevel(32).makeMap();//监控站点线程




    @PostConstruct
    public void init(){
      List<Application> applications =  applicationService.findValidateApplication();
      logger.info("{}:,  {}",BusinessEmulation.class.toString(),applications.size());
      for(Application application:applications){
          application.setEnumUrls(applicationEmuService.findEumUrlByApplicationId(application.getId()));

          if(application.getEnumUrls().isEmpty())
              continue;
          eum(application);
      }
    }

    private void eum(Application application){
        Investigation investigation =  new Investigation(application);
        long  interval =interval(application.getInterval());
        //延时时间按照
        ScheduledFuture<?>  scheduledFuture = executorService.scheduleAtFixedRate(new Investigation(application), interval,
                interval , TimeUnit.SECONDS);
        investigation.setScheduledFuture(scheduledFuture);
        holders.put(application.getId(),investigation);
    }

    @Transactional(readOnly = false)
    private void recordEnum(EumUrl url, boolean result, BigDecimal interval){
	    AvailabilityStatus availabilityStatus;
	    if(!result) {
		    availabilityStatus = AvailabilityStatus.ERROR;
	    } else {
		    availabilityStatus = AvailabilityStatus.NORMAL;
	    }
	    alarmMessageBuilder.newMessageBase(url.getApplication().getId())
			    .alarmSource(AlarmSource.EUM)
			    .addAlarmAttribute(AttributeName.Availability, availabilityStatus.value())
			    .subResourceType(ResourceType.APPLICATION_SCENARIO_URL)
			    .subResourceId(url.getUrlId()).alarm();

        Date date = new Date();
//	    Date now = LocalDate.now().toDate();
        Date now = StaTimeEnum.getTime(StaTimeEnum.TODAY,date);
        EumUrlAvaSta eumUrlAvaSta = applicationEmuService.getEumUrlStatisticsByEnumIdAndDate(url.getId(), now);
	    eumUrlAvaSta.setRecordTime(now);
	    BigDecimal newInterval = interval;
	    // 调整Interval
	    if(eumUrlAvaSta.getId() == null) {
		    int minutes = DateTime.now().getMinuteOfHour();
		    if(minutes < interval.intValue()) {
			    newInterval = BigDecimal.valueOf(minutes);
		    }
	    }

        //记录当天的统计信息
        applicationEmuService.saveEnumUrlAvailableStatistics(url.getId(), eumUrlAvaSta, result, newInterval);
        //记录至今天访问明细
        applicationEmuService.saveEnumUrlAvailableDetail(url.getId(),result, newInterval,date);
    }


    /**
     * reStart allApplication emulation
     */
    public void restart(String applicationId){
        if(holders.get(applicationId)!=null){
            holders.get(applicationId).stop();
            eum(applicationService.findApplication(applicationId));
        }
    }

    private long interval(BigDecimal interval){
        if(interval==null)
            return DEFAULT_INTERVAL;

       return (interval.equals(BigDecimal.ZERO)?DEFAULT_INTERVAL:interval.longValue())*60;
    }

    private class Investigation implements Runnable {

        private Logger loggerInv = LoggerFactory.getLogger(Investigation.class);

        private final Application application;

        private ScheduledFuture<?>  scheduledFuture;

        private final List<EumUrl> urls ;

        public Investigation(final Application application){
            this.application = application;
            this.urls = this.application.getEnumUrls();
        }


        @Override
        public void run() {

            System.out.println("application id = " + application.getId() + " time is = " + new Date() + " url is = " + urls.size());
            //  loggerInv.info("Investigation {} has started, url size is : {}",application.getApplicationName(),urls.size());

                for (EumUrl url : urls) {
	                boolean result = false;
	                try {
		                result = ResponseUtil.getResponseCode(createHttpUrl(url.getUrl()))!= 404;

                        recordEnum(url, result, this.application.getInterval());
	                } catch (Throwable e) {
		                //applicationEmuService.saveEnumUrlAvailableDetail(url.getId(),result,this.application.getInterval());
		                logger.warn("Scan url availability exception.", e);
	                }
                }

        }


        public void stop(){
            this.scheduledFuture.cancel(true);
        }

        String createHttpUrl(String url) {
            StringBuilder str = new  StringBuilder();
            if(url.contains("http://")||url.contains("https://")){
                url =  StringUtils.removeStart(url,"http://");
                url =  StringUtils.removeStart(url,"https://");
                url = StringUtils.substring(url,15);
                url = StringUtils.removeStart(url,"/");
            }
            str.append("http://").append(application.getApplicationIp()).append(":");
            str.append(application.getApplicationPort());
            str.append("//");
            //str.append(application.getApplicationName());
            if(url.startsWith("/"))
                str.append(url);
            else
                str.append("/").append(url);
            return str.toString();
        }

        public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
            this.scheduledFuture = scheduledFuture;
        }
    }


}
