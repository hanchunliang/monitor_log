package com.sinosoft.one.monitor.os.linux.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.monitor.os.linux.model.OsStati;
import com.sinosoft.one.monitor.os.linux.model.viewmodel.StatiDataModel;
import com.sinosoft.one.monitor.os.linux.repository.OsStatiRepository;
import com.sinosoft.one.monitor.os.linux.util.OsTransUtil;
import com.sinosoft.one.monitor.os.linux.util.OsUtil;

/**
 * 内存部分数据库操作类
 * @author chenxiongxi
 * @version 1.0
 * @created 27-����-2013 14:42:30
 */
@Component
public class OsStatiService {
	
	@Autowired
	private OsStatiRepository osStatiRepository;
	
	/**
	 *  创建当前一小时的统计数据 不断更新 (CPU、物理内存、交换内存、磁盘、响应时间)
	 * @param ram
	 */
	public void  creatStatiOneHour(String osInfoId ,String type,Date hourPoint,String maxValue ,String minValue,String averageValue){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		hourPoint=new DateTime(hourPoint).plusHours(1).toDate();
		OsStati osStati=osStatiRepository.getStatiThisTime(osInfoId, type, simpleDateFormat.format( hourPoint), OsUtil.ORCL_DATEFORMATE);
		if(osStati==null)
			osStati=new OsStati();
		osStati.setOsid(osInfoId);
		osStati.setRecordTime(hourPoint);
		osStati.setType(type);
		osStati.setMaxValue(maxValue);
		osStati.setMinValue(minValue);
		osStati.setAverageValue(averageValue);
		 osStatiRepository.save(osStati);
	}
	
	
	/**
	 * 保存基本信息统计结果集合
	 * @param osStatis
	 */
	public void saveStatiOneHourList(List<OsStati> osStatis){
		osStatiRepository.save(osStatis);
	}
	
	/**
	 * 查询统计表一个时间点的某种记录
	 * @param osid
	 * @param timepoint时间点
	 * @return
	 */
	public OsStati findStatiByTimePoint(String osid,String type,Date timepoint){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osStatiRepository.getStatiThisTime(osid,type, simpleDateFormat.format(timepoint),  OsUtil.ORCL_DATEFORMATE);
	}
	
 
	/**
	 * 查询统计表一个时间段的统计结果记录
	 * @param osid
	 * @param timepoint时间点
	 * @return
	 */
	public List<StatiDataModel> findStatiByTimeSpan(String osid,String type,Date begin ,Date end ){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osStatiRepository.findStatiValue(osid, type, simpleDateFormat.format(begin), simpleDateFormat.format(end), OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * 查询统计表一个时间段的 某列 最大 最小品均 统计结果记录
	 * @param osid
	 * @param timepoint时间点
	 * @return
	 */
	public StatiDataModel findStatiMxMinAvgByTimeSpan(String osid,String type,Date begin ,Date end ,String cloun){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osStatiRepository.findStatiMaxMinAvgByTimeSpan(osid, type, simpleDateFormat.format(begin), simpleDateFormat.format(end), OsUtil.ORCL_DATEFORMATE);
	}
	
}
