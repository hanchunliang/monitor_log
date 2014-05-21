package com.sinosoft.one.monitor.os.linux.repository;
// Generated 2013-2-27 21:43:52 by One Data Tools 1.0.0

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.os.linux.model.OsAvailabletemp;
import com.sinosoft.one.monitor.os.linux.model.OsCpu;

public interface OsCpuRepository extends PagingAndSortingRepository<OsCpu, String> {
	
	//根据时间
	@SQL("select * from GE_MONITOR_OS_CPU o where o.SAMPLE_DATE between to_date(?2,?4) and to_date(?3,?4) and o.OS_INFO_ID= ?1 ORDER by o.SAMPLE_DATE")
	public List<OsCpu> findOsCpuByDate(String osid,String beginTime,String endTime,String dateFormat);
	
	//CPU利用率最大值
	@SQL("select MAX(UTILI_ZATION) from GE_MONITOR_OS_CPU where SAMPLE_DATE between to_date(?2,?4) and to_date(?3,?4) and OS_INFO_ID= ?1 ")
	public String findMaxCpuUtilZation(String osInfoId,String beginTime,String endTime,String dateFormat);
	
	//CPU利用率最小值
	@SQL("select MIN(UTILI_ZATION) from GE_MONITOR_OS_CPU where SAMPLE_DATE between to_date(?2,?4) and to_date(?3,?4) and OS_INFO_ID= ?1 ")
	public String findMinCpuUtilZation(String osInfoId,String beginTime,String endTime,String dateFormat);
	
	
	//CPU用户时间最大值
	@SQL("select MAX(USER_TIME) from GE_MONITOR_OS_CPU where SAMPLE_DATE between to_date(?2,?4) and to_date(?3,?4) and OS_INFO_ID= ?1 ")
	public String findMaxCpuUserUtilZation(String osInfoId,String beginTime,String endTime,String dateFormat);
		
	//CPU用户时间最小值
	@SQL("select MIN(USER_TIME) from GE_MONITOR_OS_CPU where SAMPLE_DATE between to_date(?2,?4) and to_date(?3,?4) and OS_INFO_ID= ?1 ")
	public String findMinCpuUserUtilZation(String osInfoId,String beginTime,String endTime,String dateFormat);

	//小于目标时间删除
	@SQL("delete from GE_MONITOR_OS_CPU o where o.SAMPLE_DATE< ?2 and o.OS_INFO_ID= ?1 ")
	public void deleteCpuByLessThanTime(String osid,Date date);
	
	@SQL("select * from GE_MONITOR_OS_CPU o where o.SAMPLE_DATE=(select max(SAMPLE_DATE) from GE_MONITOR_OS_CPU where SAMPLE_DATE between to_date(?3,?4) and to_date(?2,?4) and OS_INFO_ID=?1 ) and o.OS_INFO_ID = ?1")
	public OsCpu findNealyCpu(String osid,String currentTime,String currentTime2,String dateFormat );
}

