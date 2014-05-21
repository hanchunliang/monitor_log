package com.sinosoft.one.monitor.application.domain;

import com.sinosoft.one.monitor.application.model.*;
import com.sinosoft.one.monitor.application.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-9-3
 * Time: 下午6:00
 * To change this template use File | Settings | File Templates.
 */
@Component
public class MessageProcessor {
    @Autowired
    private ExceptionInfoRepository exceptionInfoRepository;
    @Autowired
    private UrlTraceLogRepository urlTraceLogRepository;
    @Autowired
    private UrlTraceLogTempRepository urlTraceLogTempRepository;
    @Autowired
    private UrlVisitsStaRepository urlVisitsStaRepository;
    @Autowired
    private MethodResponseTimeRepository methodResponseTimeRepository;
    @Autowired
    private UrlResponseTimeRepository urlResponseTimeRepository;
    @Autowired
    private RequestPerMinuteRepository requestPerMinuteRepository;

    private Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    public void executeExceptionInfo(List<ExceptionInfo> exceptionInfoList){
        logger.debug("store data》executeExceptionInfo");
        long start = System.currentTimeMillis();
        exceptionInfoRepository.save(exceptionInfoList);
        logger.debug("cost："+( System.currentTimeMillis()-start)+"ms");
    }
    public void executeUrlTraceLogInfo(List<UrlTraceLog> urlTraceLogList){
        logger.debug("store data》executeUrlTraceLogInfo");
        long start = System.currentTimeMillis();
        urlTraceLogRepository.save(urlTraceLogList);
        List<UrlTraceLogTemp> urlTraceLogTempList = toUrlTraceLogTempList(urlTraceLogList);
        urlTraceLogTempRepository.save(urlTraceLogTempList);
        logger.debug("cost："+( System.currentTimeMillis()-start)+"ms");
    }

    public void executeUrlVisitsSta(List<UrlVisitsSta> urlVisitsStas){
        logger.debug("store data》executeUrlVisitsSta");
        long start = System.currentTimeMillis();
        urlVisitsStaRepository.save(urlVisitsStas);
        logger.debug("cost："+( System.currentTimeMillis()-start)+"ms");
    }
    public void executeMethodResponseTime(List<MethodResponseTime> methodResponseTimes){
        logger.debug("store data》executeMethodResponseTime");
        long start = System.currentTimeMillis();
        methodResponseTimeRepository.save(methodResponseTimes);
        logger.debug("cost："+( System.currentTimeMillis()-start)+"ms");
    }
    public void executeUrlResponseTime(List<UrlResponseTime> urlResponseTimeList){
        logger.debug("store data》executeUrlResponseTime");
        long start = System.currentTimeMillis();
        urlResponseTimeRepository.save(urlResponseTimeList);
        logger.debug("cost："+( System.currentTimeMillis()-start)+"ms");
    }
    public void executeRequestPerMinute(List<RequestPerMinute> requestPerMinutes){
        logger.debug("store data》executeUrlResponseTime");
        long start = System.currentTimeMillis();
        requestPerMinuteRepository.save(requestPerMinutes);
        logger.debug("cost："+( System.currentTimeMillis()-start)+"ms");
    }

    private List<UrlTraceLogTemp> toUrlTraceLogTempList(List<UrlTraceLog> urlTraceLogList) {
        List<UrlTraceLogTemp> urlTraceLogTempList = new ArrayList();
        for (UrlTraceLog urlTraceLog:urlTraceLogList){
            UrlTraceLogTemp urlTraceLogTemp = toUrlTraceLogTemp(urlTraceLog);
            urlTraceLogTempList.add(urlTraceLogTemp);
        }
        return urlTraceLogTempList;
    }

    private UrlTraceLogTemp toUrlTraceLogTemp(UrlTraceLog urlTraceLog) {
        UrlTraceLogTemp urlTraceLogTemp = new UrlTraceLogTemp();
        urlTraceLogTemp.setApplicationId(urlTraceLog.getApplicationId());
        urlTraceLogTemp.setAlarmId(urlTraceLog.getAlarmId());
        urlTraceLogTemp.setBizScenarioId(urlTraceLog.getBizScenarioId());
        urlTraceLogTemp.setBeginTime(new Timestamp(urlTraceLog.getBeginTime().getTime()));
        urlTraceLogTemp.setConsumeTime(urlTraceLog.getConsumeTime());
        urlTraceLogTemp.setEndTime(new Timestamp(urlTraceLog.getEndTime().getTime()));
        urlTraceLogTemp.setHasException(urlTraceLog.getHasException());
        urlTraceLogTemp.setId(urlTraceLog.getId());
        List<MethodTraceLogTemp> methodTraceLogList = toMehtodTraceLogList(urlTraceLog.getMethodTraceLogList());
        urlTraceLogTemp.setMethodTraceLogList(methodTraceLogList);
        urlTraceLogTemp.setRecordTime(urlTraceLog.getRecordTime());
        urlTraceLogTemp.setRequestParams(urlTraceLog.getRequestParams());
        urlTraceLogTemp.setSessionId(urlTraceLog.getSessionId());
        urlTraceLogTemp.setTraceId(urlTraceLog.getTraceId());
        urlTraceLogTemp.setUrl(urlTraceLog.getUrl());
        urlTraceLogTemp.setUrlId(urlTraceLog.getUrlId());
        urlTraceLogTemp.setUserId(urlTraceLog.getUserId());
        urlTraceLogTemp.setUserIp(urlTraceLog.getUserIp());
        urlTraceLogTemp.setUsername(urlTraceLog.getUsername());
        return urlTraceLogTemp;
    }

    private List<MethodTraceLogTemp> toMehtodTraceLogList(List<MethodTraceLog> methodTraceLogList) {
        List<MethodTraceLogTemp> methodTraceLogTempList = new ArrayList<MethodTraceLogTemp>();
        for(MethodTraceLog methodTraceLog:methodTraceLogList){
            MethodTraceLogTemp methodTraceLogTemp = toMethodTraceLogTemp(methodTraceLog);
            methodTraceLogTempList.add(methodTraceLogTemp);
        }
        return methodTraceLogTempList;
    }

    private MethodTraceLogTemp toMethodTraceLogTemp(MethodTraceLog methodTraceLog) {
        MethodTraceLogTemp m = new MethodTraceLogTemp();
        m.setBeginTime(new Timestamp(methodTraceLog.getBeginTime().getTime()));
        m.setClassName(methodTraceLog.getClassName());
        m.setConsumeTime(methodTraceLog.getConsumeTime());
        m.setEndTime(new Timestamp(methodTraceLog.getEndTime().getTime()));
        m.setId(methodTraceLog.getId());
        m.setInParam(methodTraceLog.getInParam());
        m.setMethodId(methodTraceLog.getMethodId());
        m.setMethodName(methodTraceLog.getMethodName());
        m.setOutParam(methodTraceLog.getOutParam());
        m.setRecordTime(new Timestamp(methodTraceLog.getRecordTime().getTime()));
        m.setUrlTraceLogId(methodTraceLog.getUrlTraceLogId());
        return m;
    }
}
