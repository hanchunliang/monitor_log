 package com.sinosoft.one.monitor.application.repository;
// Generated 2013-2-27 18:41:37 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.application.model.UrlTraceLog;
import com.sinosoft.one.monitor.application.model.viewmodel.UrlTraceLogViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface UrlTraceLogRepository extends PagingAndSortingRepository<UrlTraceLog, String> {
	@SQL("select l.id, l.user_id, l.user_ip,l.record_time, a.severity, i.id as exceptionId from ge_monitor_url_trace_log l, ge_monitor_alarm a, ge_monitor_exception_info i\n" +
			"where l.alarm_id=a.id(+) and l.id=i.url_trace_log_id(+) and l.url_id=?2 AND l.record_time BETWEEN ?3 AND ?4 ORDER BY l.record_time DESC")
	Page<UrlTraceLogViewModel> selectUrlTraceLogs(Pageable pageable, String urlId, Date startDate, Date endDate);

    UrlTraceLog findByAlarmId(String alarmId);

    @SQL("select l.id, l.user_id, l.user_ip,l.record_time, a.severity, i.id as exceptionId from ge_monitor_url_trace_log l, ge_monitor_alarm a, ge_monitor_exception_info i\n" +
            "where l.alarm_id=a.id(+) AND l.id=i.url_trace_log_id(+) AND l.url_id=?2 " +
            "#if(?3!=null){ AND l.record_time BETWEEN ?3 AND ?4 } " +
            "#if(?5 !='' and ?5 != 'INFO'){ AND a.severity=?5 } " +
            "#if(?5=='INFO'){ AND (a.severity is null or a.severity not in ('CRITICAL','WARNING','UNKNOW')) } " +
            "ORDER BY l.record_time DESC")
    Page<UrlTraceLogViewModel> selectUrlTraceLogs(Pageable pageable, String urlId, Date startDate, Date endDate, String givenSeverity);
}