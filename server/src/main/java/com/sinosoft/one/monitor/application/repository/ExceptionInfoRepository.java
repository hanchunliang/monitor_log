package com.sinosoft.one.monitor.application.repository;
// Generated 2013-2-27 18:41:37 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.application.model.ExceptionInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface ExceptionInfoRepository extends PagingAndSortingRepository<ExceptionInfo, String> {
	@SQL("select count(1) from ge_monitor_exception_info t where t.application_id=?1 and t.record_time between ?2 and ?3")
	int staExceptionInfoForNotMonitorUrl(String applicationId, Date startDate, Date endDate);
    @SQL("delete from ge_monitor_exception_info where record_time < ?1")
    void deleteByStartTime(Date startTime);
}

