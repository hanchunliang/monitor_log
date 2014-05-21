package com.sinosoft.one.monitor.application.repository;
// Generated 2013-2-27 18:41:37 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.application.model.MethodResponseTime;
import com.sinosoft.one.monitor.application.model.UrlTraceLog;
import org.exolab.castor.mapping.xml.Sql;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface MethodTraceLogRepository extends PagingAndSortingRepository<UrlTraceLog, String> {
    @SQL("delete from GE_MONITOR_URL_TRACE_LOG where RECORD_TIME<?1")
    void deleteByStartTime(Date startTime);

    @SQL("delete from GE_MONITOR_METHOD_TL_TEMP where RECORD_TIME<?1")
    void deleteTempByEndTime(Date endTime);

    @SQL("select min(a.consume_time) minResponseTime,\n" +
            "       max(a.consume_time) maxResponseTime,\n" +
            "       sum(a.consume_time) totalResponseTime,\n" +
            "       count(1) totalCount\n" +
            "  from ge_monitor_method_tl_temp a where a.method_id=?3 \n" +
            "   and a.url_trace_log_id in (\n" +
            "       select b.id from ge_monitor_url_trace_log_temp b where b.url_id = ?2 and b.APPLICATION_ID = ?1\n" +
            "   )\n" +
            "   and a.record_time < ?4")
    MethodResponseTime calculateMethodResponseTime(String applicationId,String urlId,String methodId,Date currTime);
}

