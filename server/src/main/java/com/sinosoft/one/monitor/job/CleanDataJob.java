package com.sinosoft.one.monitor.job;

import com.sinosoft.one.monitor.alarm.repository.AlarmRepository;
import com.sinosoft.one.monitor.application.repository.*;
import com.sinosoft.one.monitor.db.oracle.domain.StaTimeEnum;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 清理数据库中的垃圾数据 ，表清单如下：
 * 1.GE_MONITOR_ALARM
 * 2.GE_MONITOR_EXCEPTION_INFO
 * 3.GE_MONITOR_METHOD_TRACE_LOG
 * 4.GE_MONITOR_METHOD_RESPONSETIME
 * 5.GE_MONITOR_URL_TRACE_LOG
 * 6.GE_MONITOR_REQUEST_PER_MINUTE
 * 7.GE_MONITOR_URL_RESPONSE_TIME
 * 8.GE_MONITOR_URL_VISITS_STA
 *
 * Created with IntelliJ IDEA.
 * User: Chunliang.Han
 * Date: 13-8-22
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
public class CleanDataJob extends AbstractJobDetail{
    private static Logger logger = LoggerFactory.getLogger(CleanDataJob.class);
    /**
     * 1.GE_MONITOR_ALARM
     */
    AlarmRepository alarmRepository;
    /**
     * 2.GE_MONITOR_EXCEPTION_INFO
     */
    ExceptionInfoRepository exceptionInfoRepository;
    /**
     * 3.GE_MONITOR_METHOD_TRACE_LOG
     */
    LogDetailRepository logDetailRepository;
    /**
     * 4.GE_MONITOR_METHOD_RESPONSETIME
     */
    MethodResponseTimeRepository methodResponseTimeRepository;
    /**
     * 5.GE_MONITOR_URL_TRACE_LOG
     */
    MethodTraceLogRepository methodTraceLogRepository;
    /**
     * 6.GE_MONITOR_REQUEST_PER_MINUTE
     */
    RequestPerMinuteRepository requestPerMinuteRepository;
    /**
     * 7.GE_MONITOR_URL_RESPONSE_TIME
     */
    UrlResponseTimeRepository urlResponseTimeRepository;
    /**
     * 8.GE_MONITOR_URL_VISITS_STA
     */
    UrlVisitsStaRepository urlVisitsStaRepository;

    public static void setLogger(Logger logger) {
        CleanDataJob.logger = logger;
    }

    public void setAlarmRepository(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    public void setExceptionInfoRepository(ExceptionInfoRepository exceptionInfoRepository) {
        this.exceptionInfoRepository = exceptionInfoRepository;
    }

    public void setLogDetailRepository(LogDetailRepository logDetailRepository) {
        this.logDetailRepository = logDetailRepository;
    }

    public void setMethodResponseTimeRepository(MethodResponseTimeRepository methodResponseTimeRepository) {
        this.methodResponseTimeRepository = methodResponseTimeRepository;
    }

    public void setMethodTraceLogRepository(MethodTraceLogRepository methodTraceLogRepository) {
        this.methodTraceLogRepository = methodTraceLogRepository;
    }

    public void setRequestPerMinuteRepository(RequestPerMinuteRepository requestPerMinuteRepository) {
        this.requestPerMinuteRepository = requestPerMinuteRepository;
    }

    public void setUrlResponseTimeRepository(UrlResponseTimeRepository urlResponseTimeRepository) {
        this.urlResponseTimeRepository = urlResponseTimeRepository;
    }

    public void setUrlVisitsStaRepository(UrlVisitsStaRepository urlVisitsStaRepository) {
        this.urlVisitsStaRepository = urlVisitsStaRepository;
    }

    @Override
    public void executeTransactional(JobExecutionContext ctx)
            throws JobExecutionException {
        Date startTime = StaTimeEnum.getTime(StaTimeEnum.LAST_30DAY,new Date());
        long ts = System.currentTimeMillis();
        logger.warn("开始清表，时间id：" + startTime);
        alarmRepository.deleteByStartTime(startTime);
        exceptionInfoRepository.deleteByStartTime(startTime);
        logDetailRepository.deleteByStartTime(startTime);
        methodResponseTimeRepository.deleteByStartTime(startTime);
        methodTraceLogRepository.deleteByStartTime(startTime);
        requestPerMinuteRepository.deleteByStartTime(startTime);
        urlResponseTimeRepository.deleteByStartTime(startTime);
        urlVisitsStaRepository.deleteByStartTime(startTime);
        ts = System.currentTimeMillis()-ts;
        logger.warn("结束清表，当前id：" + startTime + ",总耗时：" + ts + "ms");
    }
    public static void main(String[] args){
          System.out.println(new Date());
    }
}
