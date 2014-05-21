package com.sinosoft.one.monitor.application.domain;

import com.google.common.collect.MapMaker;
import com.sinosoft.one.monitor.alarm.model.Alarm;
import com.sinosoft.one.monitor.alarm.repository.AlarmRepository;
import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.application.model.Url;
import com.sinosoft.one.monitor.application.model.UrlResponseTime;
import com.sinosoft.one.monitor.application.model.viewmodel.ApplicationDetailAlarmViewModel;
import com.sinosoft.one.monitor.application.model.viewmodel.ApplicationDetailPieViewModel;
import com.sinosoft.one.monitor.application.repository.ApplicationRepository;
import com.sinosoft.one.monitor.application.repository.UrlResponseTimeRepository;
import com.sinosoft.one.monitor.common.HealthStaForMonitor;
import com.sinosoft.one.monitor.common.HealthStaCache;
import com.sinosoft.one.monitor.common.HealthStaService;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import com.sinosoft.one.monitor.utils.DateUtil;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * 应用明细服务类.
 * User: carvin
 * Date: 13-3-6
 * Time: 下午8:18
 */
@Service
@Lazy(false)
public class ApplicationDetailService {
	@Autowired
	private AlarmRepository alarmRepository;
	@Autowired
	private UrlResponseTimeRepository urlResponseTimeRepository;
	@Autowired
	private HealthStaCache healthStaCache;
	@Autowired
	private HealthStaService healthStaService;
	@Autowired
	private ApplicationRepository applicationRepository;
    @Autowired
    private ApplicationService applicationService;

    public ScheduledExecutorService scheduledExecutorService =  Executors.newScheduledThreadPool(10);

    private static ConcurrentMap<String, ScheduledFuture<?>> threadHolderMap = new MapMaker().concurrencyLevel(32).makeMap();

    private static ConcurrentMap<String, List<UrlResponseTime>> urlResponseTimeCache = new MapMaker().concurrencyLevel(32).makeMap();

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
            urlResponseTimeCache.remove(applicationId);
        }
    }

    void execute(Application application){
        final String appId = application.getId();
        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Date startDate = DateUtil.getTodayBeginDate();
                    Date endDate = new Date();
                    List<Url> urls = applicationRepository.selectAllUrlsWithApplicationId(appId);
                    List<UrlResponseTime> urlResponseTimes = new ArrayList<UrlResponseTime>();
                    for(Url url : urls) {
                        UrlResponseTime urlResponseTime = urlResponseTimeRepository.selectUrlResponseTimesForMonitorUrl(url.getId(), startDate, endDate);
                        urlResponseTime = urlResponseTime == null ? new UrlResponseTime() : urlResponseTime;
                        long totalTime = urlResponseTime.getTotalResponseTime();
                        long totalCount = urlResponseTime.getTotalCount();
                        if (totalCount!=0){
                            urlResponseTime.setAvgResponseTime(totalTime/totalCount);
                            if (urlResponseTime.getMinResponseTime()==0&&urlResponseTime.getMaxResponseTime()>0){
                                urlResponseTime.setMinResponseTime(1L);
                            }
                        }
                        urlResponseTime.setUrlId(url.getId());
                        urlResponseTime.setUrl(url.getUrl());
                        urlResponseTimes.add(urlResponseTime);
                    }
                    for(UrlResponseTime urlResponseTime : urlResponseTimes) {
                        urlResponseTime.setHealthBar(healthStaCache.getHealthBar(appId, urlResponseTime.getUrlId()));
                        urlResponseTime.setUrlHref("<a href='javascript:void(0);' onclick=\"urlDetail('" + appId + "', '" + urlResponseTime.getUrlId() + "')\">" + urlResponseTime.getUrl() + "</a>");
                    }
                    urlResponseTimeCache.put(appId,urlResponseTimes);
                }catch (Throwable t){

                }
            }
        },0,27, TimeUnit.SECONDS);
        threadHolderMap.put(application.getId(),scheduledFuture);
    }

	/**
	 * 生成应用明细告警展示信息
	 * @param applicationId 所属应用ID
	 * @return 告警展示信息
	 */
	public ApplicationDetailAlarmViewModel generateAlarmViewModel(String applicationId) {
		Date startDate = DateUtil.getTodayBeginDate();
		Date endDate = new Date();

		Application application = applicationRepository.findApplicationbyId(applicationId);
		int interval = application.getInterval().intValue();
		ApplicationDetailAlarmViewModel applicationDetailAlarmViewModel = new ApplicationDetailAlarmViewModel();
		// 获得健康度
		SeverityLevel severityLevel = healthStaService.healthStaForCurrent(applicationId, interval);

		applicationDetailAlarmViewModel.setSeverityLevel(severityLevel);
		int criticalCount = alarmRepository.countCriticalByMonitorId(applicationId, startDate, endDate);


		Pageable pageable = new PageRequest(0, 10, Sort.Direction.DESC, "create_time");
		Page<Alarm> alarmPage = alarmRepository.selectCriticalAlarmsByMonitorId(pageable, applicationId, startDate, endDate);
		Iterator<Alarm> alarmIterator = alarmPage.iterator();
		while (alarmIterator.hasNext()) {
			applicationDetailAlarmViewModel.addAlarmInfo(alarmIterator.next().getMessage());
		}

		applicationDetailAlarmViewModel.setCriticalCount(criticalCount);
		return applicationDetailAlarmViewModel;
	}

	/**
	 * 生成应用饼图展示信息
	 * @param applicationId 所属应用ID
	 * @return 饼图展示信息
	 */
	public ApplicationDetailPieViewModel generatePieViewModel(String applicationId) {
		ApplicationDetailPieViewModel applicationDetailPieViewModel = new ApplicationDetailPieViewModel();
		Date startDate = DateUtil.getTodayBeginDate();
		Date endDate = new Date();
		List<HealthStaForMonitor> healthStaForMonitors = alarmRepository.selectHealthStaForMonitor(applicationId, startDate, endDate);
		for(HealthStaForMonitor healthStaForMonitor : healthStaForMonitors) {
			if(healthStaForMonitor.getSeverity() == SeverityLevel.CRITICAL) {
				applicationDetailPieViewModel.setCriticalCount(healthStaForMonitor.getCount());
			} else if(healthStaForMonitor.getSeverity() == SeverityLevel.WARNING) {
				applicationDetailPieViewModel.setWarningCount(healthStaForMonitor.getCount());
			} else if(healthStaForMonitor.getSeverity() == SeverityLevel.INFO) {
				applicationDetailPieViewModel.setNormalCount(healthStaForMonitor.getCount());
			}
		}

		return applicationDetailPieViewModel;
	}

	public List<UrlResponseTime> queryUrlResponseTimes(String applicationId) {
		Date startDate = DateUtil.getTodayBeginDate();
		Date endDate = new Date();
		List<Url> urls = applicationRepository.selectAllUrlsWithApplicationId(applicationId);
		List<UrlResponseTime> urlResponseTimes = new ArrayList<UrlResponseTime>();
		for(Url url : urls) {
			UrlResponseTime urlResponseTime = urlResponseTimeRepository.selectUrlResponseTimesForMonitorUrl(url.getId(), startDate, endDate);
			urlResponseTime = urlResponseTime == null ? new UrlResponseTime() : urlResponseTime;
            long totalTime = urlResponseTime.getTotalResponseTime();
            long totalCount = urlResponseTime.getTotalCount();
            if (totalCount!=0){
                urlResponseTime.setAvgResponseTime(totalTime/totalCount);
                if (urlResponseTime.getMinResponseTime()==0&&urlResponseTime.getMaxResponseTime()>0){
                    urlResponseTime.setMinResponseTime(1L);
                }
            }
			urlResponseTime.setUrlId(url.getId());
			urlResponseTime.setUrl(url.getUrl());
			urlResponseTimes.add(urlResponseTime);
		}
		for(UrlResponseTime urlResponseTime : urlResponseTimes) {
			urlResponseTime.setHealthBar(healthStaCache.getHealthBar(applicationId, urlResponseTime.getUrlId()));
			urlResponseTime.setUrlHref("<a href='javascript:void(0);' onclick=\"urlDetail('" + applicationId + "', '" + urlResponseTime.getUrlId() + "')\">" + urlResponseTime.getUrl() + "</a>");
		}
		return urlResponseTimes;
	}

    public List<UrlResponseTime> queryUrlResponseTimesFromCatch(String applicationId){
        List<UrlResponseTime> urlResponseTimes = urlResponseTimeCache.get(applicationId);
        if (urlResponseTimes==null){
            urlResponseTimes = queryUrlResponseTimes(applicationId);
            urlResponseTimeCache.put(applicationId,urlResponseTimes);
            execute(applicationService.findApplication(applicationId));
        }
        return urlResponseTimes;
    }

}
