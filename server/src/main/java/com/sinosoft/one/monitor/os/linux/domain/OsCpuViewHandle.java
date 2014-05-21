package com.sinosoft.one.monitor.os.linux.domain;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.OsCpu;
import com.sinosoft.one.monitor.os.linux.model.OsRam;
import com.sinosoft.one.monitor.os.linux.model.viewmodel.OsGridModel;
import com.sinosoft.one.monitor.os.linux.util.OsUtil;
@Component
public class OsCpuViewHandle {
	@Autowired
	private OsCpuService osCpuService;
	
	@Autowired
	private OsService osService;
	
	private static Map<String,String> map=new HashMap<String, String>();
	static{
		 map.put("runQueue", "运行队列");
//		 map.put("Link", "<a href='javascript:void(0)' onclick='viewWindow(this)' class='img-7'></a>");
		 map.put("blockProcess", "阻塞进程");
//		 map.put("blockProcessLink", "<a href='javascript:void(0)' onclick='viewWindow(this)' class='img-7'></a>");
		 map.put("userTime", "用户时间(%)");
//		 map.put("userTimeLink", "<a href='javascript:void(0)' onclick='viewWindow(this)' class='img-7'></a>");
		 map.put("sysTime", "系统时间(%)");
//		 map.put("sysTimeLink", "<a href='javascript:void(0)' onclick='viewWindow(this)' class='img-7'></a>");
		 map.put("ioWait", "I/O等待(%)");
//		 map.put("ioWaitLink", "<a href='javascript:void(0)' onclick='viewWindow(this)' class='img-7'></a>");
		 map.put("cpuIdle", "空闲时间(%) ");
//		 map.put("cpuIdleLink", "<a href='javascript:void(0)' onclick='viewWindow(this)' class='img-7'></a>");
		 map.put("interRupt", "中断/秒");
//		 map.put("interRuptLink", "<a href='javascript:void(0)' onclick='viewWindow(this)' class='img-7'></a>");
	}
	
	/**
	 * 构建多个操作系统CPU的图形MAP
	 * @param Oss
	 * @param currentTime
	 * @param interCycle
	 * @param timespan
	 */
	public Map<String,List<List<?>>> creatCpuLineData(List<Os>Oss,Date currentTime,int interCycle,int timespan){
		Map<String,List<List<?>>> osCpuUiliZation =new HashMap<String,List<List<?>>>();
		for (Os os : Oss) {
			List<List<?>> data=new ArrayList<List<?>>();
			List<OsCpu> osCpus=osCpuService.getCpuByDate(os.getOsInfoId(), new DateTime(currentTime).minusHours(timespan).toDate(), currentTime);
			for (OsCpu osCpu:osCpus) {
				 DateTime date= new DateTime(osCpu.getSampleDate());
				Long time = date.getMillis();
				Double utiliZation = Double.parseDouble(osCpu.getUtiliZation());
				data.add(Lists.newArrayList(time,utiliZation));
			}
			osCpuUiliZation.put(os.getName(), data);
		}
		return osCpuUiliZation;
	}
	
	/**
	 * 构建1个操作系统CPU利用率的曲线图形
	 * @param os
	 * @param currentTime
	 * @param interCycle
	 * @param timespan 小时数 
	 * @return
	 */
	public List<List<?>>  creatOneCpuUsedLineData(String osid,Date currentTime,int timespan){
			List<List<?>> data=new ArrayList<List<?>>();
			List<OsCpu> osCpus=osCpuService.getCpuByDate(osid, new DateTime(currentTime).minusHours(timespan).toDate(), currentTime);
			for (OsCpu osCpu:osCpus) {
				 DateTime date= new DateTime(osCpu.getSampleDate());
				Long time = date.getMillis();
//				Double utiliZation = Double.parseDouble(osCpu.getUtiliZation());
				Double utiliZation = Double.parseDouble(osCpu.getUserTime()+osCpu.getSysTime());
				data.add(Lists.newArrayList(time,utiliZation));
			}
			 
		return data;
	}
	
	/**
	 * 一个操作系统分解CPU的userTime
	 * @param os
	 * @param currentTime
	 * @param interCycle
	 * @param timespan
	 * @return
	 */
	public List<List<?>> creatOneCpuUserTimeLine(List<OsCpu> osCpus){
		List<List<?>> data=new ArrayList<List<?>>();
		for (OsCpu osCpu:osCpus) {
			 DateTime date= new DateTime(osCpu.getSampleDate());
			Long time = date.getMillis();
			Double userTime = Double.parseDouble(osCpu.getUserTime());
			data.add(Lists.newArrayList(time,userTime));
		}
		return data;
	}
	
	/**
	 * 一个操作系统分解CPU的systime 曲线
	 * @param os
	 * @param currentTime
	 * @param interCycle
	 * @param timespan
	 * @return
	 */
	public List<List<?>> creatOneCpuSysTimeLine(List<OsCpu> osCpus){
		List<List<?>> data=new ArrayList<List<?>>();
		for (OsCpu osCpu:osCpus) {
			 DateTime date= new DateTime(osCpu.getSampleDate());
			Long time = date.getMillis();
			Double sysTime = Double.parseDouble(osCpu.getSysTime());
			data.add(Lists.newArrayList(time,sysTime));
		}
		return data;
	}
	
	/**
	 * 一个操作系统分解CPU的IO曲线
	 * @param os
	 * @param currentTime
	 * @param interCycle
	 * @param timespan
	 * @return
	 */
	public List<List<?>> creatOneCpuIOLine( List<OsCpu> osCpus){
		List<List<?>> data=new ArrayList<List<?>>();
		for (OsCpu osCpu:osCpus) {
			 DateTime date= new DateTime(osCpu.getSampleDate());
			Long time = date.getMillis();
			Double ioWait = Double.parseDouble(osCpu.getIoWait());
			data.add(Lists.newArrayList(time,ioWait));
		}
		return data;
	}
	
	/**
	 * 一个操作系统分解CPU的IDLE曲线
	 * @param os
	 * @param currentTime
	 * @param interCycle
	 * @param timespan
	 * @return
	 */
	public List<List<?>>  creatOneCpuIDLELine(List<OsCpu> osCpus){
		List<List<?>> data=new ArrayList<List<?>>();
		for (OsCpu osCpu:osCpus) {
			 DateTime date= new DateTime(osCpu.getSampleDate());
			Long time = date.getMillis();
			Double cpuIdle = Double.parseDouble(osCpu.getCpuIdle());
			data.add(Lists.newArrayList(time,cpuIdle));
		}
		return data;
	}
	/**
	 * 构建当前Cpu的grid
	 * @param osId
	 * @param currentTime
	 * @return
	 */
	public List<OsGridModel> creatCurrentCpuView(String osId,Date currentTime){
		Os os=osService.getOsBasicById(osId);
		OsCpu osCpu=osCpuService.findNealyCpu(os.getOsInfoId(), currentTime, os.getIntercycleTime());
		List<OsGridModel>osSwapViewModels=new ArrayList<OsGridModel>();
		OsGridModel osRamViewModel=new OsGridModel();
		osRamViewModel.setName("CPU用户使用率");
		if(osCpu==null){
			osRamViewModel.setUtilzation("未知");
		}else{
			osRamViewModel.setUtilzation(osCpu.getUserTime());
		}
		osRamViewModel.setLink("<a href='#'onclick='viewWindow(this,\"historyCPU/7\")' class='img-7'></a>");
		osSwapViewModels.add(osRamViewModel);
		return osSwapViewModels;
	}
	
	/**
	 * 构建当前Cpu分解的grid
	 * @param osId
	 * @param currentTime
	 * @return
	 */ 
	public List<OsGridModel> creatCpuResolveView(String osId,Date currentTime){
		List<OsGridModel>osSwapViewModels=new ArrayList<OsGridModel>();
		Os os=osService.getOsBasicById(osId);
		OsCpu osCpu=osCpuService.findNealyCpu(os.getOsInfoId(), currentTime, os.getIntercycleTime());
		try {
			if(osCpu==null){
				osCpu= new OsCpu();
			}
				for (Field field : osCpu.getClass().getDeclaredFields()) {
					if(!map.keySet().contains(field.getName())){
						continue;
					}
					field.setAccessible(true);
					OsGridModel osGridModel = new OsGridModel();
					osGridModel.setName(map.get(field.getName()));
					if(field.get(osCpu)==null){
						osGridModel.setValue("未知");
//						osGridModel.setStuts("<a  class='img-unknow'></a>");
					}else{
						osGridModel.setValue(field.get(osCpu).toString());
//						osGridModel.setStuts("<a  class='img-unknow'></a>");
					}
//					osGridModel.setLink(map.get("Link"));
					osSwapViewModels.add(osGridModel);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return osSwapViewModels;
	}
	
	
	/*public static void main(String[] args){
		DateTime now =new DateTime(2013,3,11,17,50,04,DateTimeZone.UTC);
		System.out.println(now.getMillis());
	}*/
	
}
	
	
