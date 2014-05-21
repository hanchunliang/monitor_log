package com.sinosoft.one.monitor.os.linux.repository;
// Generated 2013-2-28 10:40:23 by One Data Tools 1.0.0

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.os.linux.model.OsRam;
import com.sinosoft.one.monitor.os.linux.model.OsRespondtime;

public interface OsRespondtimeRepository extends PagingAndSortingRepository<OsRespondtime, String> {
	
	//根据时间
	@Query("from OsRespondtime o where o.sampleDate between to_date(?2,?4) and to_date(?3,?4) and o.os.osInfoId= ?1 ORDER by o.sampleDate")
	public List<OsRespondtime> findOsRespondTimeByDate(String osid,String beginTime,String endTime,String dateFormat);
	
	//物理内存利用率最大值
	@SQL("select MAX(RESPOND_TIME) from GE_MONITOR_OS_RESPONDTIME where SAMPLE_DATE between to_date(?2,?4) and to_date(?3,?4) and OS_INFO_ID= ?1 ")
	public String findMaxRespondTime(String osInfoId,String begin,String end,String dateFormat);
	
	//物理内存内存利用率最小值
	@SQL("select MIN(RESPOND_TIME) from GE_MONITOR_OS_RESPONDTIME where SAMPLE_DATE between to_date(?2,?4) and to_date(?3,?4) and OS_INFO_ID= ?1 ")
	public String findMinRespondTime(String osInfoId,String begin,String end,String dateFormat);

	//小于目标时间删除
	@SQL("delete from GE_MONITOR_OS_RESPONDTIME o where o.SAMPLE_DATE< ?2 and o.OS_INFO_ID= ?1 ")
	public void deleteResponTimeByLessThanTime(String osid,Date date);
	
	//获取离轮询间隔内最近一次的记录
	@SQL("select * from GE_MONITOR_OS_RESPONDTIME o where o.SAMPLE_DATE=(select max(SAMPLE_DATE) from GE_MONITOR_OS_RESPONDTIME where SAMPLE_DATE between to_date(?3,?4) and to_date(?2,?4) and OS_INFO_ID =?1) and o.OS_INFO_ID = ?1")
	public OsRespondtime findNealyResponTime(String osid,String currentTime,String currentTime2,String dateFormat );
	
	
	//获取当前时间前最后一次响应时间(不定在轮询点内)
	@SQL("select RESPOND_TIME from GE_MONITOR_OS_RESPONDTIME where SAMPLE_DATE between to_date(?2,?4) and to_date(?3,?4)")
	public String findLastResponTime(String osid,String biganTime,String currentTime,String dateFormat );

}

