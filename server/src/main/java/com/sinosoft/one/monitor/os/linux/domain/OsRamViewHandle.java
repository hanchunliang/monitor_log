package com.sinosoft.one.monitor.os.linux.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.OsCpu;
import com.sinosoft.one.monitor.os.linux.model.OsRam;
import com.sinosoft.one.monitor.os.linux.model.viewmodel.OsGridModel;
import com.sinosoft.one.monitor.os.linux.util.OsTransUtil;
import com.sinosoft.one.monitor.os.linux.util.OsUtil;

@Component
public class OsRamViewHandle {

	@Autowired
	private OsRamService osRamService;
	
	@Autowired
	private OsService osService;
	
	/**
	 * 多个操作系统构建物理内存的图形MAP
	 * @param Oss
	 * @param currentTime
	 * @param interCycle
	 * @param timespan
	 */
	public Map<String,List<List<?>>> creatRamLineData(List<Os>Oss,Date currentTime,int interCycle,int timespan){
		Map<String,List<List<?>>> osCpuUiliZation =new HashMap<String,List<List<?>>>();
		for (Os os : Oss) {
			List<List<?>> data=new ArrayList<List<?>>();
			List<OsRam> osRams=osRamService.getRamByDate(os.getOsInfoId(), new DateTime(currentTime).minusHours(timespan).toDate(), currentTime);
			for (OsRam osRam:osRams) {
				 DateTime date= new DateTime(osRam.getSampleDate());
				Long time = date.getMillis();
				Double utiliZation = Double.parseDouble(osRam.getMemUtiliZation());
				data.add(Lists.newArrayList(time,utiliZation));
			}
			osCpuUiliZation.put(os.getName(), data);
		}
		return osCpuUiliZation;
	}
	
	
	
	/**
	 * 构建多个操作系统交换内存的图形MAP
	 * @param Oss
	 * @param currentTime
	 * @param interCycle
	 * @param timespan
	 */
	public Map<String,List<List<?>>> creatSwapLineData(List<Os>Oss,Date currentTime,int interCycle,int timespan){
		Map<String,List<List<?>>> osCpuUiliZation =new HashMap<String,List<List<?>>>();
		for (Os os : Oss) {
			List<List<?>> data=new ArrayList<List<?>>();
			List<OsRam> osRams=osRamService.getRamByDate(os.getOsInfoId(), new DateTime(currentTime).minusHours(timespan).toDate(), currentTime);
			for (OsRam osRam:osRams) {
				DateTime date= new DateTime(osRam.getSampleDate());
				Long time = date.getMillis();
				Double utiliZation = Double.parseDouble(osRam.getSwapUtiliZation());
				data.add(Lists.newArrayList(time,utiliZation));
			}
			osCpuUiliZation.put(os.getName(), data);
		}
		return osCpuUiliZation;
	}
	
	/**
	 * 构建一个操作系统物理内存曲线图形
	 * @param Oss
	 * @param currentTime
	 * @param interCycle
	 * @param timespan
	 * @return
	 */
	public Map<String, List<List<?>>> creatOneRamLineData(String osid,Date currentTime,int timespan){
		Map<String, List<List<?>>>line=new HashMap<String, List<List<?>>>();
		List<List<?>> ramdata=new ArrayList<List<?>>();
		List<List<?>> swapdata=new ArrayList<List<?>>();
		List<OsRam>osRams=osRamService.getRamByDate(osid, new DateTime(currentTime).minusHours(timespan).toDate(), currentTime);
		for (OsRam osRam:osRams) {
			DateTime date= new DateTime(osRam.getSampleDate());
			Long time = date.getMillis();
			Double rmsutiliZation = Double.parseDouble(osRam.getMemUtiliZation());
			Double swaputiliZation = Double.parseDouble(osRam.getSwapUtiliZation());
			ramdata.add(Lists.newArrayList(time,rmsutiliZation));
			swapdata.add(Lists.newArrayList(time,swaputiliZation));
		}
		line.put("ram", ramdata);
		line.put("swap", swapdata);
		return line;
	}
	
	/**
	 * 构建一个操作系统交换内存的曲线
	 * @param os
	 * @param currentTime
	 * @param interCycle
	 * @param timespan
	 * @return
	 */
	public List<List<?>> creatOneSwapLineData(String osid,Date currentTime,int timespan){
		List<List<?>> data=new ArrayList<List<?>>();
		List<OsRam>osRams=osRamService.getRamByDate(osid, new DateTime(currentTime).minusHours(timespan).toDate(), currentTime);
		for (OsRam osRam:osRams) {
			DateTime date= new DateTime(osRam.getSampleDate());
			Long time = date.getMillis();
			Double utiliZation = Double.parseDouble(osRam.getSwapUtiliZation());
			data.add(Lists.newArrayList(time,utiliZation));
		} 
		return data;
	}
	
	/**
	 * 构建当前内存的grid
	 * @param osId
	 * @param currentTime
	 * @return
	 */
	public List<OsGridModel> creatCurrentMemView(String osId,Date currentTime){
		Os os=osService.getOsBasicById(osId);
		OsRam osRam=osRamService.findNealyRam(os.getOsInfoId(), currentTime, os.getIntercycleTime());
		List<OsGridModel>osSwapViewModels=new ArrayList<OsGridModel>();
		if(osRam==null){
			OsGridModel osRamViewModel=new OsGridModel();
			osRamViewModel.setName("物理内存利用率");
			osRamViewModel.setUtilzation("未知");
			osRamViewModel.setLink("<a href='#' onclick='viewWindow(this,\"historyMem/7\")' class='img-7'></a>");
			osRamViewModel.setUsed("未知");
			osSwapViewModels.add(osRamViewModel);
			OsGridModel osSwapViewModel=new OsGridModel();
			osSwapViewModel.setName("交换内存利用率");
			osSwapViewModel.setUtilzation("未知");
			osSwapViewModel.setLink("<a href='#' onclick='viewWindow(this,\"historyMem/7\")' class='img-7'></a>");
			osSwapViewModel.setUsed("未知");
			osSwapViewModels.add(osSwapViewModel);
		}else{
			OsGridModel osRamViewModel=new OsGridModel();
			osRamViewModel.setName("物理内存利用率");
			osRamViewModel.setUtilzation(osRam.getMemUtiliZation()); 
			osRamViewModel.setLink("<a href='#' onclick='viewWindow(this,\"historyMem/7\")' class='img-7'></a>");
			osRamViewModel.setUsed(OsTransUtil.countUtilZation("1024", osRam.getMemUsed()));
			osSwapViewModels.add(osRamViewModel);
			OsGridModel osSwapViewModel=new OsGridModel();
			osSwapViewModel.setName("交换内存利用率");
			osSwapViewModel.setUtilzation(osRam.getSwapUtiliZation());
			osSwapViewModel.setLink(" ");
			osSwapViewModel.setUsed(OsTransUtil.countUtilZation("1024", osRam.getSwapUsed()));
			osSwapViewModels.add(osSwapViewModel);
		}
		return osSwapViewModels;
	}
}
