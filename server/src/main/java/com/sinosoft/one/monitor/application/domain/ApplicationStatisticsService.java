package com.sinosoft.one.monitor.application.domain;


import com.sinosoft.one.monitor.application.model.*;
import com.sinosoft.one.monitor.application.repository.*;
import com.sinosoft.one.monitor.utils.DateUtil;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-10-16
 * Time: 上午10:37
 * To change this template use File | Settings | File Templates.
 */
@Component
@Lazy(false)
public class ApplicationStatisticsService {
    /**
     * 定时任务，每隔一个时间段查询一次数据
     */
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    @Autowired
    private MethodResponseTimeRepository methodResponseTimeRepository;
    @Autowired
    private UrlVisitsStaRepository urlVisitsStaRepository;
    @Autowired
    private UrlResponseTimeRepository urlResponseTimeRepository;
    @Autowired
    private RequestPerMinuteRepository requestPerMinuteRepository;
    @Autowired
    private UrlTraceLogTempRepository urlTraceLogTempRepository;
    @Autowired
    private MethodTraceLogRepository methodTraceLogRepository;
    private Logger logger = LoggerFactory.getLogger(ApplicationStatisticsService.class);
    private Date preTime;
    //在临时表中处理时间小于currTime的数据
    private Date currTime;
    private Date preHour;
    //统计当前小时的数据
    private Date currHour;
    @Autowired
    private ApplicationStore applicationStore;
    /**
     * 方法临时缓存KEY:(应用ID:URL_ID:方法ID:小时)，VALUE:MethodResponseTime对象
     */
    private Map<String,MethodResponseTime> methodResponseTimeMap = new HashMap<String, MethodResponseTime>();
    private Map<String,UrlVisitsSta> urlVisitsStaMap = new HashMap<String, UrlVisitsSta>();
    private Map<String,UrlResponseTime> urlResponseTimeMap = new HashMap<String, UrlResponseTime>();
    private Map<String,RequestPerMinute> requestPerMinuteMap = new HashMap<String, RequestPerMinute>();
    @PostConstruct
    public void start(){
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                //设定要处理数据的时间段，如果是临界数据则取前一段数据
                generateCalculateTime();
                //处理统计
                execute();
                //更新前一次的时间
                refreshTime();
                logger.debug("ApplicationStatisticsService process calculate,cost:"+(System.currentTimeMillis()-start));
            }
        },30,60, TimeUnit.SECONDS);
    }

    /**
     * 更新处理时间，currTime是处理时间，currHour是当前统计的小时
     * 当currTime!=preTime时，currTime赋值为当前小时的0时刻，
     * 此时统计的数据是前一小时的数据，故currHour赋值为preHour。
     * 正常情况下currTime取值为当前时间currHour取值为当前小时
     */
    private void generateCalculateTime(){
        currTime = new Date();
        currHour = DateUtil.getHoursDate(currTime);
        if (preTime ==null){
            preTime = currTime;
            preHour = currHour;
        }
        //如果时间段跨域小时，则先处理上一小时的时间段
        if (currTime.getHours()!= preTime.getHours()){
            currTime = currHour;
            currHour = preHour;
        }
    }

    /**
     * 处理结束后临时保留一份处理时间
     */
    private void refreshTime(){
        preTime = currTime;
        preHour = currHour;
    }
    /**
     * 执行统计
     */
    private void execute(){
        applicationStore.load();
        //获取全部应用ID
        List<Application> applications = applicationStore.getApplications();
        for (Application application:applications){
            //每小时应用系统访问数统计
            RequestPerMinute requestPerMinute = getRequestPerMinute(application.getId());
            //获取指定应用的URL ID
            List<Url> urls = applicationStore.getUrls(application.getId());
            for (Url url:urls){
                //Url响应时间统计,每小时访问次数统计
                countUrlResponseTime(application,url,requestPerMinute);
                //获取指定URL的方法ID
                List<Method> methods = applicationStore.getMethods(url.getId());
                for (Method method:methods){
                    //方法响应时间统计
                    countMethodResponseTime(application,url,method);
                }
            }
        }
        commit();
        applicationStore.clear();
    }

    /**
     * 清除临时表数据
     */
    private void commit(){
        // 清除临时表数据
        urlTraceLogTempRepository.deleteByEndTime(currTime);
        methodTraceLogRepository.deleteTempByEndTime(currTime);
        //批量保存MethodResponseTime
        Collection<MethodResponseTime> methodResponseTimes = methodResponseTimeMap.values();
        methodResponseTimeRepository.save(methodResponseTimes);

        //批量保存UrlVisitsSta
        Collection<UrlVisitsSta> urlVisitsStas = urlVisitsStaMap.values();
        urlVisitsStaRepository.save(urlVisitsStas);

        //批量保存UrlResponseTime
        Collection<UrlResponseTime> urlResponseTimes = urlResponseTimeMap.values();
        urlResponseTimeRepository.save(urlResponseTimes);

        //批量保存RequestPerMinute
        Collection<RequestPerMinute> requestPerMinutes = requestPerMinuteMap.values();
        requestPerMinuteRepository.save(requestPerMinutes);
    }

    /**
     * Url响应时间统计
     * @param application
     * @param url
     * @param requestPerMinute
     */
    private void countUrlResponseTime(Application application,Url url,RequestPerMinute requestPerMinute){
        //统计这一时间段的数据
        UrlResponseTime calculateInfo = urlTraceLogTempRepository.calculeteUrlResponseTime(application.getId(),url.getId(),currTime);

        UrlResponseTime urlResponseTime = getUrlResponseTime(application.getId(), url.getId());
        urlResponseTime.setUrl(url.getUrl());

        if (calculateInfo.getTotalCount() != 0){
            if(urlResponseTime.getMinResponseTime()==0
                    || urlResponseTime.getMinResponseTime() > calculateInfo.getMinResponseTime()) {
                urlResponseTime.setMinResponseTime(calculateInfo.getMinResponseTime());
            }
            if(urlResponseTime.getMaxResponseTime() < calculateInfo.getMaxResponseTime()) {
                urlResponseTime.setMaxResponseTime(calculateInfo.getMaxResponseTime());
            }
            urlResponseTime.addTotalResponseTime(calculateInfo.getTotalResponseTime());
            urlResponseTime.increaseTotalCount(calculateInfo.getTotalCount());
            if (urlResponseTime.getTotalCount()!=0){
                urlResponseTime.setAvgResponseTime(
                        urlResponseTime.getTotalResponseTime()/urlResponseTime.getTotalCount());
            }
        }

        countUrlVisitsPerHour(application,url,requestPerMinute,urlResponseTime.getTotalCount());
    }
    /**
     * 从缓存中获取UrlResponseTime对象，如果没有则以applicationId+urlId+currHour.getTime()为key将
     * 将UrlResponseTime对象对象存入缓存
     * @param applicationId
     * @return
     */
    private UrlResponseTime getUrlResponseTime(String applicationId,String urlId){
        String preKey = applicationId+urlId+preHour.getTime();
        String key = applicationId+urlId+currHour.getTime();
        UrlResponseTime urlResponseTime = urlResponseTimeMap.get(key);
        if (urlResponseTime == null){
            urlResponseTime = new UrlResponseTime();
            urlResponseTime.setApplicationId(applicationId);
            urlResponseTime.setUrlId(urlId);
            urlResponseTime.setRecordTime(currHour);
            urlResponseTime = urlResponseTimeRepository.save(urlResponseTime);
            if (urlResponseTimeMap.containsKey(preKey)){
                urlResponseTimeMap.remove(preKey);
            }
            urlResponseTimeMap.put(key, urlResponseTime);
        }
        return urlResponseTime;
    }

    /**
     * 从缓存中获取RequestPerMinute对象，如果没有则以applicationId+currHour.getTime()为key将
     * 将RequestPerMinute对象对象存入缓存
     * @param applicationId
     * @return
     */
    private RequestPerMinute getRequestPerMinute(String applicationId){
        String preKey = applicationId+preHour.getTime();
        String key = applicationId+currHour.getTime();
        RequestPerMinute requestPerMinute = requestPerMinuteMap.get(key);
        if (requestPerMinute==null){
            requestPerMinute = new RequestPerMinute();
            requestPerMinute.setApplicationId(applicationId);
            requestPerMinute.setRecordTime(currHour);
            requestPerMinute = requestPerMinuteRepository.save(requestPerMinute);
            if (requestPerMinuteMap.containsKey(preKey)){
                requestPerMinuteMap.remove(preKey);
            }
            requestPerMinuteMap.put(key,requestPerMinute);
        }
        return requestPerMinute;
    }

    /**
     * 每小时访问数统计
     * @param application
     * @param url
     * @param requestPerMinute
     * @param total
     */
    private void countUrlVisitsPerHour(Application application,Url url,RequestPerMinute requestPerMinute,int total){
        UrlVisitsSta urlVisitsSta =getUrlVisitsSta(application.getId(),url.getId());
        requestPerMinute.addRpm(total);
        urlVisitsSta.increaseVisitNumber(total);
    }
    /**
     * 从缓存中获取UrlVisitsSta对象，如果没有则以applicationId+urlId+currHour.getTime()为key将
     * 将UrlVisitsSta对象对象存入缓存
     * @param applicationId
     * @return
     */
    private UrlVisitsSta getUrlVisitsSta(String applicationId,String urlId){
        String preKey = applicationId+urlId+preHour.getTime();
        String key = applicationId+urlId+currHour.getTime();
        UrlVisitsSta urlVisitsSta = urlVisitsStaMap.get(key);
        if(urlVisitsSta == null) {
            urlVisitsSta = new UrlVisitsSta();
            urlVisitsSta.setApplicationId(applicationId);
            urlVisitsSta.setRecordTime(currHour);
            urlVisitsSta.setUrlId(urlId);
            urlVisitsSta = urlVisitsStaRepository.save(urlVisitsSta);
            if (urlVisitsStaMap.containsKey(preKey)){
                urlVisitsStaMap.remove(preKey);
            }
            urlVisitsStaMap.put(key, urlVisitsSta);
        }
        return urlVisitsSta;
    }

    /**
     * 方法响应时间统计
     * @param application
     * @param url
     * @param method
     */
    private void countMethodResponseTime(Application application,Url url,Method method){
        //统计指点时间段的数据
        MethodResponseTime calculateInfo = methodTraceLogRepository.calculateMethodResponseTime(application.getId(),url.getId(),method.getId(),currTime);

        MethodResponseTime methodResponseTime = getMethodResponseTime(application.getId(),url.getId(),method.getId());
        methodResponseTime.setMethodName(method.getMethodName());

        if (calculateInfo.getTotalCount() != 0){
            long minResponseTime = calculateInfo.getMinResponseTime();
            long maxResponseTime = calculateInfo.getMaxResponseTime();
            if (methodResponseTime.getMinResponseTime()==0
                    ||  minResponseTime<methodResponseTime.getMinResponseTime()){
                methodResponseTime.setMinResponseTime(minResponseTime);
            }
            if (maxResponseTime>methodResponseTime.getMaxResponseTime()){
                methodResponseTime.setMaxResponseTime(maxResponseTime);
            }
            methodResponseTime.addTotalResponseTime(calculateInfo.getTotalResponseTime());
            methodResponseTime.increaseTotalCount(calculateInfo.getTotalCount());
            if(calculateInfo.getTotalCount()!=0){
                methodResponseTime.setAvgResponseTime(
                        calculateInfo.getTotalResponseTime()/calculateInfo.getTotalCount());
            }
        }
    }

    /**
     * 从缓存中获取MethodResponseTime对象，如果没有则以applicationId+urlId+methodId+currHour.getTime()
     * 为key将MethodResponseTime对象对象存入缓存
     * @param applicationId
     * @param urlId
     * @param methodId
     * @return
     */
    private MethodResponseTime getMethodResponseTime(String applicationId,String urlId,String methodId){
        String preKey = applicationId+urlId+methodId+preHour.getTime();
        String key = applicationId+urlId+methodId+currHour.getTime();
        MethodResponseTime methodResponseTime = methodResponseTimeMap.get(key);
        //如果没有该对象则创建一个放入缓存
        if (methodResponseTime==null){
            methodResponseTime = new MethodResponseTime();
            methodResponseTime.setApplicationId(applicationId);
            methodResponseTime.setUrlId(urlId);
            methodResponseTime.setMethodId(methodId);
            methodResponseTime.setRecordTime(currHour);
            methodResponseTime = methodResponseTimeRepository.save(methodResponseTime);
            //如果创建新的methodResponseTime对象则要删除老的methodResponseTime对象
            if (methodResponseTimeMap.containsKey(preKey)){
                methodResponseTimeMap.remove(preKey);
            }
            methodResponseTimeMap.put(key,methodResponseTime);
        }
        return methodResponseTime;
    }
}
