package com.sinosoft.one.monitor.os.linux.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.OsAvailable;
import com.sinosoft.one.monitor.os.linux.model.OsAvailabletemp;
import com.sinosoft.one.monitor.os.linux.model.viewmodel.OsGridModel;
import com.sinosoft.one.monitor.os.linux.model.viewmodel.StatiDataModel;
import com.sinosoft.one.monitor.os.linux.repository.OsAvailableRepository;
import com.sinosoft.one.monitor.os.linux.repository.OsAvailabletempRepository;
import com.sinosoft.one.monitor.os.linux.util.OsTransUtil;
import com.sinosoft.one.monitor.os.linux.util.OsUtil;
import com.sinosoft.one.monitor.utils.AvailableCalculate;
import com.sinosoft.one.monitor.utils.AvailableCalculate.AvailableCountsGroupByInterval;

/**
 * 可用性的数据库操作
 * @author Administrator
 *	@author chenxiongxi
 * @version 1.0
 * @created 27-����-2013 14:42:30
 */
@Component
public class OsAvailableServcie {
	@Autowired
	private OsAvailabletempRepository osAvailabletempRepository;
	@Autowired
	private OsAvailableRepository osAvailableRepository;
	/**
	 * 保存可用性统计表数据 //每天00点保存
	 * @param ava
	 */
	public void saveAvailable(String osId,long nomorRun,long crashtime,long aveRepair,long aveFault ,Date timeSpan, int stopCount){
		OsAvailable osAvailable =new OsAvailable();
		Os os=new Os();
		os.setOsInfoId(osId);
		osAvailable.setOs(os);
		osAvailable.setAveFaultTime(aveFault);
		osAvailable.setAveRepairTime(aveRepair);
		osAvailable.setCrashTime(crashtime);
		osAvailable.setTimeSpan(timeSpan);
		osAvailable.setNormalRun(nomorRun);
		osAvailable.setStopCount(stopCount);
		osAvailableRepository.save(osAvailable);
	}
	
	/**
	 * 获取可用性统计表数据 （统计表中时间为天的整点）
	 * @param ava
	 * @param date（一天时间整点）
	 */
	public OsAvailable getAvailable(String osInfoId,Date date){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osAvailableRepository.getOsAvailableByDate(osInfoId, simpleDateFormat.format(date), OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * 根据时间删除
	 * @param osInfoId
	 * @param timeSpan
	 */
	public void deleteAvailable(String osInfoId,Date timeSpan){
		//删除
		osAvailableRepository.deleteOsAvailableByDate(osInfoId,timeSpan);
	}
	
	/**
	 * 修改统计表记录
	 * @param osAvailable
	 */
	public void updateAvailable(OsAvailable osAvailable,long nomorRun,long crashtime,long aveRepair,long aveFault,int stopCount){
		osAvailable.setNormalRun(nomorRun);
		osAvailable.setAveFaultTime(aveFault);
		osAvailable.setAveRepairTime(aveRepair);
		osAvailable.setCrashTime(crashtime);
		osAvailable.setStopCount(stopCount);
		osAvailableRepository.save(osAvailable);
	}
	/**
	 * 修改统计表记录
	 * @param osAvailable
	 */
	public void updateAvailable(OsAvailable osAvailable){
		osAvailableRepository.save(osAvailable);
	}
	/**
	 * 保存可用性临时数据//每个采样点保存
	 * @param ava
	 */
	public OsAvailabletemp saveAvailableTemp(OsAvailabletemp osAvailabletemp){
		return osAvailabletempRepository.save(osAvailabletemp);
	}
	
	/**
	 * 获取可用性临时数据
	 *  
	 */
	public List<OsAvailabletemp> getAvailableTemps(String osid,Date beginTime,Date endTime){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osAvailabletempRepository.findOsAvailabletempByDate( osid,simpleDateFormat.format(beginTime), simpleDateFormat.format(endTime),OsUtil.ORCL_DATEFORMATE);
	}
	
	public static void main(String[] args) {
		
	}
	
	/**
	 * 获取可用统计数据
	 * timespan 时间1  7 30 等  天为单位
	 */
	public List<OsAvailable> getAvailablesByDate(String osid,Date curruntTime,int timespan){
		Date beginTime =new Date();
		if(timespan>1){
//			timespan=(timespan-1)*24;
			beginTime =OsTransUtil.getBeforeDate(curruntTime, timespan+"");
		}else{
			beginTime =OsTransUtil.getDayPointByDate(curruntTime);
//			timespan=24;
		}
		//取当天的24小时整时点
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osAvailableRepository.getOsAvailablesByDate( osid,simpleDateFormat.format(beginTime), simpleDateFormat.format(curruntTime),OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * 获取可用统计数据返回统计类型MODEL
	 * timespan 时间1  7 30 等  天为单位
	 */
	public List<StatiDataModel> getOsAvailablesHistoryByDate(String osid,Date curruntTime,int timespan){
//		if(timespan>1){
//			timespan=(timespan-1)*24;
//		}else{
//			timespan=24;
//		}
		//取当天的24小时整时点
		curruntTime=OsTransUtil.getDayPointByDate(curruntTime);
		Date beginTime =OsTransUtil.getBeforeDate(curruntTime, timespan+"");
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osAvailableRepository.getOsAvailablesHistoryByDate( osid,simpleDateFormat.format(beginTime), simpleDateFormat.format(curruntTime),OsUtil.ORCL_DATEFORMATE);
	}
	
	
	/**
	 * 删除可用性临时数据
	 */
	public void deleteAvailableTemp(String osInfoId,Date beginTime,Date endTime){
//		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
//		osAvailabletempRepository.deleteOsAvailabletempByDate(osInfoId, simpleDateFormat.format(beginTime), simpleDateFormat.format(endTime),OsUtil.ORCL_DATEFORMATE);
		osAvailabletempRepository.deleteOsAvailabletempByDate(osInfoId, beginTime, endTime);

	}
	
	/**
	 * 删除可用性临时数据 前24小时之外的全部数据
	 */
	public void deleteTempByLessThanTime(String osInfoId,Date date ){
//		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		osAvailabletempRepository.deleteTempByLessThanTime(osInfoId, date);
	}
	
	/**
	 * 获取最后一次可用性的记录时间
	 */
	public OsAvailabletemp getLastAvailable(String osInfoId,Date currentTime ){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osAvailabletempRepository.findLastAvailable(osInfoId, simpleDateFormat.format(currentTime), OsUtil.ORCL_DATEFORMATE);
	}
	
	
	/**
	 * 
	 * @param osInfoId
	 * @param currentTime
	 * @param interCycle
	 * @return
	 */
	public OsAvailabletemp getNealyAvailable(String osInfoId,Date currentTime ,int interCycle){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		Calendar c=Calendar.getInstance();
		c.set(Calendar.MINUTE, 0);
		Date date=new Date(currentTime.getTime()-Long.valueOf(interCycle*60*1000));
		return osAvailabletempRepository.findNealyAvailable(osInfoId, simpleDateFormat.format(date), OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * 获取前24时中保存的可用性状态  最近24小时 数据
	 * 保存到统计表
	 */
	public List<OsAvailabletemp> getFFHourAvailale(String osInfoId,Date currentTime){
		Calendar c  = Calendar.getInstance();
		////获取当前时间的小时数 取整时点
		c.set(Calendar.DATE, currentTime.getDate());
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.add(Calendar.HOUR_OF_DAY, -24);
		Date d1 = c.getTime();	//前24小时
		List<OsAvailabletemp> osAvailabletemps=getAvailableTemps(osInfoId, d1, currentTime);
		return osAvailabletemps;
	}
	
	
	/**
	 * 删除上一段24小时段（前天）中保存的可用性数据
	 */
	public  void deleteLTFHourAvailale(String osInfoId,Date currentTime){
		//当前天数
		Calendar c  = Calendar.getInstance();
		c.set(Calendar.DATE, currentTime.getDate());
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.add(Calendar.HOUR_OF_DAY, -24);
		//取当天的前24小时整时点
		Date d1 = c.getTime();
		c.add(Calendar.HOUR_OF_DAY, -24);
		//取当天的前48小时整时点
		Date d2 = c.getTime();
		deleteAvailableTemp(osInfoId, d2,d1);
	}
	
	/**
	 * 获取临时表统计轮询时间改变的计算
	 */
	public List<AvailableCountsGroupByInterval> findGroupByInterCycleTime(String osinfoID,Date todaytime){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osAvailabletempRepository.findGroupByInterCycleTime(osinfoID, simpleDateFormat.format(todaytime),OsUtil.ORCL_DATEFORMATE);
	}
	
}
