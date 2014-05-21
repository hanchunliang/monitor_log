package com.sinosoft.one.monitor.os.linux.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.OsAvailabletemp;
import com.sinosoft.one.monitor.os.linux.model.OsCpu;
import com.sinosoft.one.monitor.os.linux.model.OsStati;
import com.sinosoft.one.monitor.os.linux.repository.OsCpuRepository;
import com.sinosoft.one.monitor.os.linux.util.OsTransUtil;
import com.sinosoft.one.monitor.os.linux.util.OsUtil;

/**
 * Cpu部分数据库操作类
 * @author chenxiongxi
 * @version 1.0
 * @created 27-����-2013 14:42:30
 */
@Component
public class OsCpuService {
	@Autowired
	private OsCpuRepository osCpuRepository;
	/**
	 * 保存CPU采集数据
	 * @param cpu
	 */
	public boolean saveCpu(String osInfoId,OsCpu osCpu ,Date sampleTime){
		Os os =new Os();
		os.setOsInfoId(osInfoId);
		osCpu.setOs(os);
		osCpu.setSampleDate(sampleTime);
		osCpuRepository.save(osCpu);
		return true;
	}
	
	
	/**
	 * 获取CPU采集数据
	 * @param cpu
	 */
	public List<OsCpu>  getCpuByDate(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osCpuRepository.findOsCpuByDate(osInfoId, simpleDateFormat.format(begin), simpleDateFormat.format(end) ,OsUtil.ORCL_DATEFORMATE);
	}

	/**
	 * cpu利用率最大值
	 * @param osInfoId
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getMaxCpuUtilZation(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osCpuRepository.findMaxCpuUtilZation(osInfoId, simpleDateFormat.format(begin), simpleDateFormat.format(end) ,OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * cpu利用率最小值
	 * @param osInfoId
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getMinCpuUtilZation(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osCpuRepository.findMaxCpuUtilZation(osInfoId, simpleDateFormat.format(begin), simpleDateFormat.format(end) ,OsUtil.ORCL_DATEFORMATE);
	}
	
	
	/**
	 * cpu利用率最大值
	 * @param osInfoId
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getMaxCpuUserUtilZation(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osCpuRepository.findMaxCpuUtilZation(osInfoId, simpleDateFormat.format(begin), simpleDateFormat.format(end) ,OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * cpu利用率最小值
	 * @param osInfoId
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getMinCpuUserUtilZation(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osCpuRepository.findMaxCpuUtilZation(osInfoId, simpleDateFormat.format(begin), simpleDateFormat.format(end) ,OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * 删除小于该时间的记录 
	 * @param osInfoId
	 * @param sampleTime
	 */
	public void deleteCpuByLessThanTime(String osInfoId,Date sampleTime){
		osCpuRepository.deleteCpuByLessThanTime(osInfoId, sampleTime);
	}
	
	
	/**
	 * 获取最后一次Cpu的记录
	 * @param osInfoId
	 * @param currentTime 当前时间
	 * @param interCycle 采样轮询
	 * @return
	 */
	public OsCpu findNealyCpu(String osInfoId,Date currentTime ,int interCycle ){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		Date date=new Date(currentTime.getTime()-(interCycle*60*1000+2000));
		return osCpuRepository.findNealyCpu(osInfoId, simpleDateFormat.format(currentTime),simpleDateFormat.format(date) ,OsUtil.ORCL_DATEFORMATE);
	}
	
	
}
