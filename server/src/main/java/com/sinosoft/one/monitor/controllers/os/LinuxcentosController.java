package com.sinosoft.one.monitor.controllers.os;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.sinosoft.one.monitor.alarm.domain.AlarmService;
import com.sinosoft.one.monitor.alarm.model.Alarm;
import com.sinosoft.one.monitor.alarm.repository.AlarmRepository;
import com.sinosoft.one.monitor.os.linux.domain.OsAvailableServcie;
import com.sinosoft.one.monitor.os.linux.domain.OsAvailableViewHandle;
import com.sinosoft.one.monitor.os.linux.domain.OsCpuViewHandle;
import com.sinosoft.one.monitor.os.linux.domain.OsDiskViewHandle;
import com.sinosoft.one.monitor.os.linux.domain.OsProcessService;
import com.sinosoft.one.monitor.os.linux.domain.OsRamViewHandle;
import com.sinosoft.one.monitor.os.linux.domain.OsRespondTimeService;
import com.sinosoft.one.monitor.os.linux.domain.OsService;
import com.sinosoft.one.monitor.os.linux.domain.OsViewHandle;
import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.OsAvailabletemp;
import com.sinosoft.one.monitor.os.linux.model.OsDisk;
import com.sinosoft.one.monitor.os.linux.model.OsRespondtime;
import com.sinosoft.one.monitor.os.linux.model.viewmodel.OsGridModel;
import com.sinosoft.one.monitor.os.linux.util.OsUtil;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;

@Path
public class LinuxcentosController {
	@Autowired
	private OsService osService; 
	
	@Autowired
	private OsProcessService osProcessService;
	
	@Autowired
	private OsRespondTimeService osRespondTimeService;
	
	@Autowired
	private OsAvailableServcie osAvailableServcie;
	
	@Autowired
	private OsViewHandle osViewHandle;
	
	@Autowired
	private OsRamViewHandle osRamViewHandle;
	
	@Autowired
	private OsAvailableViewHandle osAvailableViewHandle;
	
	@Autowired
	private OsCpuViewHandle osCpuViewHandle;
	
	@Autowired
	private OsDiskViewHandle osDiskViewHandle;
	@Autowired
	private AlarmRepository alarmRepository;
	@Post("osInfo/{osId}")
	public Reply osInfo(@Param("osId") String osId ) {
		Map<String, String> map = new HashMap<String, String>();
		Date currentTime=new Date();
		//获取操作系统基本信息】【
		Os os=osService.getOsBasicById(osId);
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE);
		map.put("monitorName", os.getName());
		map.put("health", "1");
		map.put("type", os.getType());
		map.put("osName", os.getIpAddr());
		map.put("os", os.getType());
		//获取最后一次 响应时间
		OsRespondtime responTime= osRespondTimeService.findNealyResponTime(osId, currentTime,os.getIntercycleTime());
		//获取最后一次轮询时间
		OsAvailabletemp lastSampleTime=osProcessService.getLastSampleTime(osId, currentTime);
		String healthyFlag= "<img src='/monitor/global/images/bussinessY.gif'>&nbsp;&nbsp;健康状态为正常.";
		List<Alarm> alarmList = alarmRepository.findAlarmByMonitorId(os.getOsInfoId(),  new DateTime(currentTime).minusMinutes(os.getIntercycleTime()).toDate(), currentTime);
		for (Alarm alarm : alarmList) {
			if (alarm.getSeverity().equals(SeverityLevel.INFO)) {
				healthyFlag = "<img src='/monitor/global/images/bussinessY.gif'>&nbsp;&nbsp;健康状态为正常.&nbsp;没有出现告警。";
            } else if (alarm.getSeverity().equals(SeverityLevel.WARNING)) {
                healthyFlag = "<img src='/monitor/global/images/bussinessY3.gif'>&nbsp;&nbsp;健康状态为警告.&nbsp;出现中等的告警。";
            } else if (alarm.getSeverity().equals(SeverityLevel.CRITICAL)) {
                healthyFlag = "<img src='/monitor/global/images/bussinessY2.gif'>&nbsp;&nbsp;健康状态为严重.&nbsp;出现出现严重的告警。";
            } else if (alarm.getSeverity().equals(SeverityLevel.UNKNOW)) {
                healthyFlag = "<img src='/monitor/global/images/icon_health_unknown.gif'>&nbsp;&nbsp;健康状态为未知.";
            }
		}
		if(lastSampleTime==null){
			map.put("lastTime", "未知");
			Calendar c  = Calendar.getInstance();
			c.setTime(currentTime);
			c.add(Calendar.MINUTE, os.getIntercycleTime());
			map.put("nextTime", "未知"); 
		}else{
			if(currentTime.getTime()-lastSampleTime.getSampleDate().getTime()>os.getIntercycleTime()*60*1000){
				map.put("nextTime", "未知"); 
				healthyFlag = "<img src='/monitor/global/images/bussinessY2.gif'>&nbsp;&nbsp;当前为不可用";
			}else{
				Calendar c  = Calendar.getInstance();
				c.setTime(lastSampleTime.getSampleDate());
				c.add(Calendar.MINUTE, os.getIntercycleTime());
				Date nextSampleTime=c.getTime();
				map.put("nextTime", simpleDateFormat.format(nextSampleTime)); 
			}
			map.put("lastTime", simpleDateFormat.format(lastSampleTime.getSampleDate()));
		
		}
		
		
		map.put("healthy", healthyFlag);
		if(responTime==null)
			map.put("respondTime",0+"毫秒");
		else{
			map.put("respondTime",responTime.getRespondTime()+"毫秒");
		}
		String messageFormat1 = "<span class={0}>{1}</span>";
		return Replys.with(map).as(Json.class);
	}

	/**
	 * 今天的可用性图饼
	 * @param osId
	 * @return
	 */
	@Post("getUsability/{osId}")
	public Reply getUsability(@Param("osId") String osId ) {
		Map<String, Double> map = new HashMap<String, Double>();
		Date currentTime=new Date();
		map = osAvailableViewHandle.creatAvailablePie(osId, currentTime, 1);
		return Replys.with(map).as(Json.class);
	}

	/**
	 * 今天的利用率 罗盘
	 * @param osId
	 * @return
	 */
	@Post("getUtilzation/{osId}")
	public Reply getUtilzation(@Param("osId") String osId ) {
		Date currentTime=new Date();
		Map<String, Double> map = new HashMap<String, Double>();
		map=osViewHandle.creatUtilZationView(osId, currentTime);
		return Replys.with(map).as(Json.class);
	}

	/**
	 * 概览页面CPU 与内存曲线
	 * @param osId
	 * @return
	 */ 
	@Post("getCpuAndRam/{osId}")
	public Reply getCpuAndRam(@Param("osId") String osId ) {
		Date currentTime=new Date();
		Map<String,List<List<?>>> oneOsCpuAndMem= osViewHandle.createOneOsCpuAndMemline(osId, currentTime, 3);
		return Replys.with(oneOsCpuAndMem).as(Json.class);
	}
	/**
	 * 概览页面CPU分解信息曲线  cpu利用率曲线（内存CPU表格下面的曲线）
	 * @param osId
	 * @return
	 */
	@Post("getCpuInfo/{osId}")
	public Reply getCpuInfo(@Param("osId") String osId ) {
		Date currentTime=new Date();
		Map<String,List<List<?>>> lineMap =osViewHandle.createOneCpuResolveView(osId, currentTime, 3);
		return Replys.with(lineMap).as(Json.class);
	}


	/**
	 * 概览页面内存利用率表格
	 *  CPU与内存利用率曲线下面的表格
	 * @param osId
	 * @param inv
	 */
	@Post("gridMemory/{osId}")
	public void gridMemory(@Param("osId") String osId ,Invocation inv) {
		Date currentTime=new Date();
		List<OsGridModel> osRamViewModels=osRamViewHandle.creatCurrentMemView(osId, currentTime);
		Page page = new PageImpl(osRamViewModels);
		Gridable<OsGridModel> gridable = new Gridable<OsGridModel>(page);
		gridable.setIdField("id");
		gridable.setCellStringField("name,utilzation,used,link");
		try {
			UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 概览页面CPU利用率  CPU与内存利用率曲线下面（ 内存表格左边）的表格
	 * @param osId
	 * @param inv
	 */
	@Post("gridCpu/{osId}")
	public void gridCpu(@Param("osId") String osId ,Invocation inv){
		Date currentTime=new Date();
		List<OsGridModel> osGridModels=osCpuViewHandle.creatCurrentCpuView(osId, currentTime);
		Page page = new PageImpl(osGridModels);
		Gridable<OsGridModel> gridable = new Gridable<OsGridModel>(page);
		gridable.setIdField("id");
		gridable.setCellStringField("name,utilzation,link");
		try {
			UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 概览页面CPU分解grid 当前时间 io 中断等grid 最下面的分解CPU信息表格
	 * @param osId
	 * @return
	 */
	@Post("gridCpuResolve/{osId}")
	public void gridCpuResolve(@Param("osId") String osId ,Invocation inv ){
		Date currentTime=new Date();
		List<OsGridModel> osGridModels=osCpuViewHandle.creatCpuResolveView(osId, currentTime);
		Page page = new PageImpl(osGridModels);
		Gridable<OsGridModel> gridable = new Gridable<OsGridModel>(page);
		gridable.setIdField("id");
		gridable.setCellStringField("name,value");
		try {
			UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 概览页面磁盘
	 * @param osId
	 * @param inv
	 */
	@Post("gridDiskGrid/{osId}")
	public void gridDisk(@Param("osId") String osId ,Invocation inv ){
		Date currentTime=new Date();
		List<OsDisk> osGridModels=osDiskViewHandle.creatCpuResolveView(osId, currentTime);
		Page page = new PageImpl(osGridModels);
		Gridable<OsDisk> gridable = new Gridable<OsDisk>(page);
		gridable.setIdField("id");
		gridable.setCellStringField("diskPath,total,usedUtiliZation,used,freeUtiliZation,free");
		try {
			UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
