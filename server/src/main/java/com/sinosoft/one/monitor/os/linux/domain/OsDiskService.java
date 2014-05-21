package com.sinosoft.one.monitor.os.linux.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.OsCpu;
import com.sinosoft.one.monitor.os.linux.model.OsDisk;
import com.sinosoft.one.monitor.os.linux.model.OsStati;
import com.sinosoft.one.monitor.os.linux.repository.OsDiskRepository;
import com.sinosoft.one.monitor.os.linux.util.OsTransUtil;
import com.sinosoft.one.monitor.os.linux.util.OsUtil;

/**
 * 磁盘部分数据库操作类
 * @author chenxiongxi
 * @version 1.0
 * @created 27-����-2013 14:42:30
 */
@Component
public class OsDiskService {
	
	@Autowired
	private OsDiskRepository osDiskRepository;
	/**
	 * 保存磁盘采集数据
	 * @param disk
	 */
	public boolean saveDisk(String osInfoId, List<OsDisk> osDisks,Date sampleTime){
		Os os=new Os();
		for (OsDisk osDisk : osDisks) {
			os.setOsInfoId(osInfoId);
			osDisk.setOs(os);
			osDisk.setSampleDate(sampleTime);
		}
		osDiskRepository.save(osDisks);
		return true;
	}
	
	/**
	 * 获取内存采集数据
	 * @param ram
	 * @param begin起始时间
	 * @param end 结束时间
	 */
	public List<OsDisk> getDiskByDate(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osDiskRepository.findOsDiskByDate(osInfoId, simpleDateFormat.format(begin), simpleDateFormat.format(end), OsUtil.ORCL_DATEFORMATE);
	}

	/**
	 * 删除小于该时间的记录 
	 * @param osInfoId
	 * @param sampleTime
	 */
	public void deleteDiskByLessThanTime(String osInfoId,Date sampleTime){
		osDiskRepository.deleteDiskByLessThanTime(osInfoId, sampleTime);
	}
	
	
	/**
	 * 获取最大磁盘
	 * @param osInfoId
	 * @param begin
	 * @param end
	 * @return
	 */
	public  String getMaxDiskUtilZation(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osDiskRepository.findMaxDiskUtilZation(osInfoId,simpleDateFormat.format(begin),simpleDateFormat.format(end) , OsUtil.ORCL_DATEFORMATE);
	}
	
	
	/**
	 * 
	 */
	public  String getMinDiskUtilZation(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osDiskRepository.findMinDiskUtilZation(osInfoId,simpleDateFormat.format(begin),simpleDateFormat.format(end) , OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * 获取在轮询间隔内最后一次记录
	 */
	public List<OsDisk> findNealyDisk(String osInfoId,Date currentTime,int interCycle ){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		Date date=new Date(currentTime.getTime()-(interCycle*60*1000+2000));
		return osDiskRepository.findNealyDisk(osInfoId, simpleDateFormat.format(date), simpleDateFormat.format(currentTime),OsUtil.ORCL_DATEFORMATE);
	}
}
