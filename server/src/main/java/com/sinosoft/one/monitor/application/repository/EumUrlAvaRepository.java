package com.sinosoft.one.monitor.application.repository;
// Generated 2013-3-4 15:45:31 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.application.domain.TimeQuantumAvailableStatistics;
import com.sinosoft.one.monitor.application.model.EumUrlAva;
import com.sinosoft.one.monitor.utils.AvailableCalculate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;


public interface EumUrlAvaRepository extends PagingAndSortingRepository<EumUrlAva, String> {

     public Page<EumUrlAva> findByEumUrlId(String eumUrlId,Pageable pageable);

     public List<EumUrlAva> findByEumUrlIdAndState(String eumUrlId,String state);

     public Page<EumUrlAva> findByEumUrlIdAndState(String eumUrlId,String state,Pageable pageable);

     @SQL("select count(1) as count ,interval from GE_MONITOR_EUM_URL_AVA where eum_url_id=?1 and state =?2 GROUP BY INTERVAL")
     public List<AvailableCalculate.AvailableCountsGroupByInterval> countsGroupByInterval(String eumUrlId,String state);

     @SQL("select count(1) from GE_MONITOR_EUM_URL_AVA where eum_url_id=?1")
     public int countByEmuId(String eumUrlId);

     @SQL("SELECT count(1) from GE_MONITOR_EUM_URL_AVA where eum_url_id=?1 and STATE = ?2")
     public int countByEmuIdAndStatus(String eumUrlId,String status);

     @SQL("select to_char(a.record_time, 'yyyy-MM-dd HH24') as timeQuantum, state as status, count(1) as count" +
             "  from ge_monitor_eum_url_ava a" +
             "  where a.eum_url_id=?1 and a.record_time between ?2 and ?3 " +
             " group by to_char(a.record_time, 'yyyy-MM-dd HH24'), state")
     public List<TimeQuantumAvailableStatistics> statisticsByEumUrlIdAndRecordTime(String eumUrlId,Date start,Date end);

	@SQL("delete ge_monitor_eum_url_ava a where a.record_time < ?1")
	 void deleteByLessThanDate(Date date);

}

