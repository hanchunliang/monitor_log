package com.sinosoft.one.monitor.os.linux.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.OsRam;
import com.sinosoft.one.monitor.os.linux.model.OsRespondtime;
import com.sinosoft.one.monitor.os.linux.model.OsStati;
import com.sinosoft.one.monitor.os.linux.repository.OsRespondtimeRepository;
import com.sinosoft.one.monitor.os.linux.util.OsUtil;
@Component
public class OsRespondTimeService {
	@Autowired
	private OsRespondtimeRepository osRespondtimeRepository;
	/**
	 * 保存响应时间采集数据
	 * @param ava
	 */
	public boolean saveRespondTime(String osInfoId,String respondTime ,Date sampleTime){
		OsRespondtime osRespondtime=new OsRespondtime();
		Os os=new Os();
		os.setOsInfoId(osInfoId);
		osRespondtime.setOs (os);
		osRespondtime.setSampleDate(sampleTime);
		osRespondtime.setRespondTime(respondTime);
		osRespondtimeRepository.save(osRespondtime);
		return true;
	}
	
	/**
	 * 获取响应时间数据
	 * @param ava
	 */
	public List<OsRespondtime> getRespondTimeByTime(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osRespondtimeRepository.findOsRespondTimeByDate(osInfoId, simpleDateFormat.format(begin), simpleDateFormat.format(end), OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * 获取响应时间最大值
	 * @param ava
	 */
	public String getMaxRespondTime(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osRespondtimeRepository.findMaxRespondTime(osInfoId, simpleDateFormat.format(begin), simpleDateFormat.format(end), OsUtil.ORCL_DATEFORMATE);
	}
	
	/**
	 * 获取响应时间最小值
	 * @param ava
	 */
	public String getMinRespondTime(String osInfoId,Date begin,Date end){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osRespondtimeRepository.findMinRespondTime(osInfoId, simpleDateFormat.format(begin), simpleDateFormat.format(end), OsUtil.ORCL_DATEFORMATE);
	}
	
	
	/**
	 * 删除小于该时间的记录 
	 * @param osInfoId
	 * @param sampleTime
	 */
	public void deleteResponTimekByLessThanTime(String osInfoId,Date sampleTime){
		osRespondtimeRepository.deleteResponTimeByLessThanTime(osInfoId, sampleTime);
	}
	
	/**
	 * 获取最后一次记 在轮询时间内的响应时间
	 */
	public OsRespondtime findNealyResponTime(String osInfoId,Date currentTime,int interCycle ){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		Date date=new Date(currentTime.getTime()-(interCycle*60*1000));
		return osRespondtimeRepository.findNealyResponTime(osInfoId, simpleDateFormat.format(currentTime), simpleDateFormat.format(date), OsUtil.ORCL_DATEFORMATE);
	} 
	
	
	/**
	 * 获取最后一次记 在轮询时间内的响应时间(不定在轮询点内)
	 */
	public String findLastResponTime(String osInfoId,Date currentTime ,int interCycleTime ){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		return osRespondtimeRepository.findLastResponTime(osInfoId,simpleDateFormat.format(new DateTime(currentTime).minusMinutes(interCycleTime+1).toDate()), simpleDateFormat.format(currentTime), OsUtil.ORCL_DATEFORMATE);
	}
}
