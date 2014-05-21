package com.sinosoft.one.monitor.os.linux.repository;
// Generated 2013-2-27 21:43:52 by One Data Tools 1.0.0

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.os.linux.model.OsAvailable;
import com.sinosoft.one.monitor.os.linux.model.OsStati;
import com.sinosoft.one.monitor.os.linux.model.viewmodel.OsGridModel;
import com.sinosoft.one.monitor.os.linux.model.viewmodel.StatiDataModel;

public interface OsAvailableRepository extends PagingAndSortingRepository<OsAvailable, String> {
	
	//删除可用性统计记录
	@SQL("delete GE_MONITOR_OS_AVAILABLE where OS_INFO_ID=?1 and TIME_SPAN= ?2")
	public void deleteOsAvailableByDate(String osInfoId,Date timeSpan );
	
	//根据时间获取可用性统计记录 确定的一天
	@Query("from OsAvailable o where o.os.osInfoId=?1 and o.timeSpan = to_date(?2,?3)")
	public OsAvailable getOsAvailableByDate(String osInfoId,String timeSpan ,String dateFormat);
	
	
	//根据时间获取可用性统计记录
	@Query("from OsAvailable o where o.os.osInfoId=?1 and o.timeSpan between to_date(?2,?4) and to_date(?3,?4) order by o.timeSpan")
	public List<OsAvailable> getOsAvailablesByDate(String osInfoId,String begin,String end ,String dateFormat);
	
	
	
	//根据时间获取可用性统计记录
	@SQL("select TIME_SPAN \"date\",NORMAL_RUN \"normalRun\" ,CRASH_TIME \"crashTime\", AVE_REPAIR_TIME \"aveRepairTime\" , AVE_FAULT_TIME \"aveFaultTime\"  from GE_MONITOR_OS_AVAILABLE o where o.OS_INFO_ID=?1 and o.TIME_SPAN between to_date(?2,?4) and to_date(?3,?4) ORDER by o.TIME_SPAN")
	public List<StatiDataModel> getOsAvailablesHistoryByDate(String osInfoId,String begin,String end ,String dateFormat);
}

