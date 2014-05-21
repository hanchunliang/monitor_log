package com.sinosoft.one.monitor.application.repository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.application.model.UrlResponseTime;
import com.sinosoft.one.monitor.application.model.UrlTraceLogTemp;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-10-18
 * Time: 下午1:33
 * To change this template use File | Settings | File Templates.
 */
public interface UrlTraceLogTempRepository extends PagingAndSortingRepository<UrlTraceLogTemp, String> {
    @SQL("delete from GE_MONITOR_URL_TRACE_LOG_TEMP where RECORD_TIME<?1")
    void deleteByEndTime(Date endTime);

    @SQL("select min(a.consume_time) minResponseTime,\n" +
            "       max(a.consume_time) maxResponseTime, \n" +
            "       sum(a.consume_time) totalResponseTime,\n" +
            "       count(1) totalCount" +
            "  from GE_MONITOR_URL_TRACE_LOG_TEMP a \n" +
            " where a.url_id = ?2 \n" +
            "   and a.APPLICATION_ID = ?1\n" +
            "   and a.record_time < ?3")
    UrlResponseTime calculeteUrlResponseTime(String applicationId, String urlId, Date currTime);

//    @SQL("")
//    int calculateUrlVisitsSta(Date currTime);
}
