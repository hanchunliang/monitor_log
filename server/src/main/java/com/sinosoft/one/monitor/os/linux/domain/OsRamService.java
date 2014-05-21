package com.sinosoft.one.monitor.os.linux.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.OsDisk;
import com.sinosoft.one.monitor.os.linux.model.OsRam;
import com.sinosoft.one.monitor.os.linux.model.OsStati;
import com.sinosoft.one.monitor.os.linux.repository.OsRamRepository;
import com.sinosoft.one.monitor.os.linux.util.OsTransUtil;
import com.sinosoft.one.monitor.os.linux.util.OsUtil;
/**
 * 内存部分数据库操作类
 * @author chenxiongxi
 * @version 1.0
 * @created 27-����-2013 14:42:30
 */
@Component
public class OsRamService {
	
	@Autowired
	private OsRamRepository osRamRepository;
	

	/**
	 * 保存内存采集数据
	 */
	public boolean saveRam(String osInfoId,OsRam osRam  ,Date sampleTime){
		Os os=new Os();
		os.setOsInfoId(osInfoId);
		osRam.setOs(os);
		osRam.setSampleDate(sampleTime);
		osRamRepository.save(osRam);
		return true;
	}
	/**
	 * 获取内存采集数据
	 * @param ram
	 * @param begin起始时间
	 * @param end 结束时间
	 */
	public List<OsRam> getRamByDate(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osRamRepository.findOsRamByDate(osInfoId, simpleDateFormat.format(begin), simpleDateFormat.format(end), OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * 物理内存利用率最大值
	 * @param osInfoId
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getMaxMemUtilZation(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osRamRepository.findMaxMemUtilZation(osInfoId, simpleDateFormat.format(begin), simpleDateFormat.format(end), OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * 物理内存利用率最小值
	 * @param osInfoId
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getMinMemUtilZation(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osRamRepository.findMinMemUtilZation(osInfoId, simpleDateFormat.format(begin), simpleDateFormat.format(end), OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * 交换内存利用率最大值
	 * @param osInfoId
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getMaxSwapUtilZation(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osRamRepository.findMaxSwapUtilZation(osInfoId, simpleDateFormat.format(begin), simpleDateFormat.format(end), OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * 交换内存利用率最大值
	 * @param osInfoId
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getMinSwapUtilZation(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osRamRepository.findMinSwapUtilZation(osInfoId, simpleDateFormat.format(begin), simpleDateFormat.format(end), OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * 删除小于该时间的记录 
	 * @param osInfoId
	 * @param sampleTime
	 */
	public void deleteRamByLessThanTime(String osInfoId,Date sampleTime){
		osRamRepository.deleteRamByLessThanTime(osInfoId, sampleTime);
	}
	

	/**
	 * 获取最近轮询点的记录
	 */
	public OsRam findNealyRam(String osInfoId,Date currentTime ,int interCycle){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		Date date=new Date(currentTime.getTime()-(interCycle*60*1000+2000));
		return osRamRepository.findNealyRam(osInfoId, simpleDateFormat.format(currentTime),simpleDateFormat.format(date) ,OsUtil.ORCL_DATEFORMATE);
	}
	 
}
