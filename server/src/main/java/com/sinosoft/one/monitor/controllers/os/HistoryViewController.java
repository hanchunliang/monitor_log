package com.sinosoft.one.monitor.controllers.os;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.sinosoft.one.monitor.os.linux.domain.OsAvailableViewHandle;
import com.sinosoft.one.monitor.os.linux.domain.OsService;
import com.sinosoft.one.monitor.os.linux.domain.OsStatiService;
import com.sinosoft.one.monitor.os.linux.domain.OsViewHandle;
import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.viewmodel.OsGridModel;
import com.sinosoft.one.monitor.os.linux.model.viewmodel.StatiDataModel;
import com.sinosoft.one.monitor.os.linux.util.OsTransUtil;
import com.sinosoft.one.monitor.os.linux.util.OsUtil;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;

@Path
public class HistoryViewController {
	
	@Autowired
	private OsService osService;
	
	@Autowired
	private OsViewHandle osViewHandle;
	
	@Autowired
	private OsStatiService osStatiService;
	
	@Autowired
	private OsAvailableViewHandle osAvailableViewHandle;
	//CPU天数统计报表
	@Get("historyCPU/{timespan}/{osId}")
	public String historyCPU(@Param("osId") String osId,@Param("timespan") String timespan,Invocation inv){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE_YEAR_MON_DAY);
		Os os=osService.getOsBasicById(osId);
		Date currentDate=new Date();
		Date beginDate=OsTransUtil.getBeforeDate(currentDate, timespan);
		DecimalFormat dec = new DecimalFormat("0.##"); 
		StatiDataModel statiDataModel=osStatiService.findStatiMxMinAvgByTimeSpan(osId,OsUtil.CPU_STATIF_FLAG, beginDate, currentDate, OsUtil.STATI_CLOUN_NAME_AVG);
		if(statiDataModel!=null){
			inv.addModel("MaxAgv",dec.format(new BigDecimal(statiDataModel.getMaxAvgValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
			inv.addModel("MinAgv",dec.format(new BigDecimal(statiDataModel.getMinAvgValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
			inv.addModel("Agv",dec.format(new BigDecimal(statiDataModel.getAvgValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
		}else {
			inv.addModel("MaxAgv","无数据" );
			inv.addModel("MinAgv","无数据"  );
			inv.addModel("Agv", "无数据" );
		}
		inv.addModel("os", os);
		inv.addModel("beginDate", simpleDateFormat.format(beginDate));
		inv.addModel("currentDate", simpleDateFormat.format(currentDate));
		if (timespan.toString().equals("7")) {
			return "historyCPUSevenDay";
		}
		return "historyCPUThirtyDay";
	}
	//DISK天数统计报表
	@Get("historyDisk/{timespan}/{osId}")
	public String historyDisk(@Param("osId") String osId,@Param("timespan") String timespan,Invocation inv){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE_YEAR_MON_DAY);
		Os os=osService.getOsBasicById(osId); 
		Date currentDate=new Date();
		Date beginDate=OsTransUtil.getBeforeDate(currentDate, timespan);
		StatiDataModel statiDataModel=osStatiService.findStatiMxMinAvgByTimeSpan(osId,OsUtil.DISK_STATIF_FLAG, beginDate, currentDate, OsUtil.STATI_CLOUN_NAME_AVG);
		DecimalFormat dec = new DecimalFormat("0.##"); 
		if(statiDataModel!=null){
			inv.addModel("MaxAgv",dec.format(new BigDecimal(statiDataModel.getMaxAvgValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
			inv.addModel("MinAgv",dec.format(new BigDecimal(statiDataModel.getMinAvgValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
			inv.addModel("Agv",dec.format(new BigDecimal(statiDataModel.getAvgValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
		}else {
			inv.addModel("MaxAgv","无数据" );
			inv.addModel("MinAgv","无数据"  );
			inv.addModel("Agv", "无数据" );
		}
		inv.addModel("os", os);
		inv.addModel("beginDate", simpleDateFormat.format(beginDate));
		inv.addModel("currentDate", simpleDateFormat.format(currentDate));
		if (timespan.toString().equals("7")) {
			return "historyDiskSevenDay";
		}
		return "historyDiskThirtyDay";
	}
	
	//内存天数统计报表
	@Get("historyMem/{timespan}/{osId}")
	public String historyRam(@Param("osId") String osId,@Param("timespan") String timespan,Invocation inv){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE_YEAR_MON_DAY);
		Os os=osService.getOsBasicById(osId);
		Date currentDate=new Date();
		Date beginDate=OsTransUtil.getBeforeDate(currentDate, timespan);
		StatiDataModel statiDataModel=osStatiService.findStatiMxMinAvgByTimeSpan(osId,OsUtil.RAM_STATIF_FLAG, beginDate, currentDate, OsUtil.STATI_CLOUN_NAME_AVG);
		DecimalFormat dec = new DecimalFormat("0.##"); 
		if(statiDataModel!=null){
			inv.addModel("MaxAgv",dec.format(new BigDecimal(statiDataModel.getMaxAvgValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
			inv.addModel("MinAgv",dec.format(new BigDecimal(statiDataModel.getMinAvgValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
			inv.addModel("Agv",dec.format(new BigDecimal(statiDataModel.getAvgValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
		}else {
			inv.addModel("MaxAgv","无数据" );
			inv.addModel("MinAgv","无数据"  );
			inv.addModel("Agv", "无数据" );
		}
		inv.addModel("os", os);
		inv.addModel("beginDate", simpleDateFormat.format(beginDate));
		inv.addModel("currentDate", simpleDateFormat.format(currentDate));
		if (timespan.toString().equals("7")) {
			return "historyMemorySevenDay";
		}
		return "historyMemoryThirtyDay";
	}
	//响应时间天数统计报表
	@Get("historyRespond/{timespan}/{osId}")
	public String historyRespond(@Param("osId") String osId,@Param("timespan") String timespan,Invocation inv){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE_YEAR_MON_DAY);
		Os os=osService.getOsBasicById(osId);
		Date currentDate=new Date();
		Date beginDate=OsTransUtil.getBeforeDate(currentDate, timespan);
		StatiDataModel statiDataModel=osStatiService.findStatiMxMinAvgByTimeSpan(osId,OsUtil.RSPOND_STATIF_FLAG, beginDate, currentDate, OsUtil.STATI_CLOUN_NAME_AVG);
		DecimalFormat dec = new DecimalFormat("0.##"); 
		if(statiDataModel!=null){	
		inv.addModel("MaxAgv",dec.format(new BigDecimal(statiDataModel.getMaxAvgValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
			inv.addModel("MinAgv",dec.format(new BigDecimal(statiDataModel.getMinAvgValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
			inv.addModel("Agv",dec.format(new BigDecimal(statiDataModel.getAvgValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
		}else {
			inv.addModel("MaxAgv","无数据" );
			inv.addModel("MinAgv","无数据"  );
			inv.addModel("Agv", "无数据" );
		}
		inv.addModel("os", os);
		inv.addModel("beginDate", simpleDateFormat.format(beginDate));
		inv.addModel("currentDate", simpleDateFormat.format(currentDate));
		if (timespan.toString().equals("7")) {
			return "historyRespSevenDay";
		}
		return "historyRespThirtyDay";
	}
	//可用性报表
	@Get("historyAvaylable/{timespan}/{osId}")
	public String historyAvailable(@Param("osId") String osId,@Param("timespan") String timespan,Invocation inv){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(OsUtil.DATEFORMATE_YEAR_MON_DAY);
		Os os=osService.getOsBasicById(osId);
		Date currentDate=new Date();
		Date beginDate=OsTransUtil.getBeforeDate(currentDate, timespan);
		inv.addModel("os", os);
		inv.addModel("beginDate", simpleDateFormat.format(beginDate));
		inv.addModel("currentDate", simpleDateFormat.format(currentDate));
		if (timespan.toString().equals("7")) {
			return "historyAvailableSevenDay";
		}
		return "historyAvailableThirdthDay";
	}
	
	/**
	 * 可用性报表图饼
	 * @param osId
	 * @return
	 */
	@Post("historyAvailablePie/{timespan}/{osId}")
	public Reply getUsability(@Param("osId") String osId ,@Param("timespan") String timespan) {
		Map<String, Double> map = new HashMap<String, Double>();
		Date currentTime=new Date();
		map = osAvailableViewHandle.creatAvailablePie(osId, currentTime,Integer.valueOf(timespan));
		return Replys.with(map).as(Json.class);
	}
	/**
	 * 可用性报表表格
	 * @param osId
	 * @return
	 */
	@Post("historyAvailableGrid/{timespan}/{osId}")
	public void historyAvailableGrid(@Param("osId") String osId,@Param("timespan") String timespan,Invocation inv){
		Date currentDate=new Date();
		List<OsGridModel> osViewModels=osViewHandle.creatAvailableHistoryGrid(osId, OsUtil.CPU_STATIF_FLAG, currentDate, Integer.valueOf(timespan));
		Page page = new PageImpl(osViewModels);
		Gridable<OsGridModel> gridable = new Gridable<OsGridModel>(page);
		gridable.setIdField("id"); 
		gridable.setCellStringField("time,normalRun,crashTime,aveRepairTime,aveFaultTime");
		try {
			UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * CPU报表窗口曲线
	 * @param osId
	 * @param timespan
	 * @return
	 */
	@Post("historyCPUStatiLine/{timespan}/{osId}")
	public Reply historyCPUSevenDayLine(@Param("osId") String osId,@Param("timespan") String timespan){
		Date currentDate=new Date();
		Map<String,List<List<?>>> map=osViewHandle.creatStatiLine(osId, OsUtil.CPU_STATIF_FLAG, currentDate, Integer.valueOf(timespan));
		Map<String,List<List<?>>> viewMap=new  HashMap<String,List<List<?>>>();
		viewMap.put("CPU用户使用率最大值%", map.get("max"));
		viewMap.put("CPU用户使用率最小值%", map.get("min"));
		viewMap.put("CPU用户使用率平均值%", map.get("ave"));
		return Replys.with(viewMap).as(Json.class);
	}
	
	/**
	 * CPU报表窗口表格
	 * @param osId
	 * @param timespan
	 * @return
	 */
	@Post("historyCPUStatiGrid/{timespan}/{osId}")
	public void historyCPUSevenDayGrid(@Param("osId") String osId,@Param("timespan") String timespan,Invocation inv){
		Date currentDate=new Date();
		List<OsGridModel> osViewModels=osViewHandle.creatStatiGrid(osId, OsUtil.CPU_STATIF_FLAG, currentDate, Integer.valueOf(timespan));
		Page page = new PageImpl(osViewModels);
		Gridable<OsGridModel> gridable = new Gridable<OsGridModel>(page);
		gridable.setIdField("id"); 
		gridable.setCellStringField("time,minValue,maxValue,averageValue");
		try {
			UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 磁盘利用率窗口曲线
	 * @param osId
	 * @param timespan
	 * @return
	 */
	@Post("historyDiskStatiLine/{timespan}/{osId}")
	public Reply historyDiskSevenDayLine(@Param("osId") String osId,@Param("timespan") String timespan){
		Date currentDate=new Date();
		Map<String,List<List<?>>> map=osViewHandle.creatStatiLine(osId, OsUtil.DISK_STATIF_FLAG, currentDate, Integer.valueOf(timespan));
		Map<String,List<List<?>>> viewMap=new  HashMap<String,List<List<?>>>();
		viewMap.put("磁盘利用率最大值%", map.get("max"));
		viewMap.put("磁盘利用率最小值%", map.get("min"));
		viewMap.put("磁盘利用率平均值%", map.get("ave"));
		return Replys.with(viewMap).as(Json.class);
	}
	/**
	 * 磁盘利用率窗口表格
	 */
	@Post("historyDiskStatiGrid/{timespan}/{osId}")
	public void historyDiskSevenDayGrid(@Param("osId") String osId,@Param("timespan") String timespan,Invocation inv){
		Date currentDate=new Date();
		List<OsGridModel> osViewModels=osViewHandle.creatStatiGrid(osId, OsUtil.DISK_STATIF_FLAG, currentDate, Integer.valueOf(timespan));
		Page page = new PageImpl(osViewModels);
		Gridable<OsGridModel> gridable = new Gridable<OsGridModel>(page);
		gridable.setIdField("id"); 
		gridable.setCellStringField("time,minValue,maxValue,averageValue");
		try {
			UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 内存利用率窗口曲线
	 * @param osId
	 * @param timespan
	 * @return
	 */
	@Post("historyMemStatiLine/{timespan}/{osId}")
	public Reply historyMemSevenDayLine(@Param("osId") String osId,@Param("timespan") String timespan){
		Date currentDate=new Date();
		Map<String,List<List<?>>> map=osViewHandle.creatStatiLine(osId, OsUtil.RAM_STATIF_FLAG, currentDate, Integer.valueOf(timespan));
		Map<String,List<List<?>>> viewMap=new  HashMap<String,List<List<?>>>();
		viewMap.put("内存利用率最大值%", map.get("max"));
		viewMap.put("内存利用率最小值%", map.get("min"));
		viewMap.put("内存利用率平均值%", map.get("ave"));
		return Replys.with(viewMap).as(Json.class);
	}
	/**
	 * 内存利用窗口表格
	 */
	@Post("historyMemStatiGrid/{timespan}/{osId}")
	public void historyMemSevenDayGrid(@Param("osId") String osId,@Param("timespan") String timespan,Invocation inv){
		Date currentDate=new Date();
		List<OsGridModel> osViewModels=osViewHandle.creatStatiGrid(osId, OsUtil.RAM_STATIF_FLAG, currentDate, Integer.valueOf(timespan));
		Page page = new PageImpl(osViewModels);
		Gridable<OsGridModel> gridable = new Gridable<OsGridModel>(page);
		gridable.setIdField("id"); 
		gridable.setCellStringField("time,minValue,maxValue,averageValue");
		try {
			UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 响应时间窗口曲线
	 * @param osId
	 * @param timespan
	 * @return
	 */
	@Post("historyRepStatiLine/{timespan}/{osId}")
	public Reply historyRepSevenDayLine(@Param("osId") String osId,@Param("timespan") String timespan){
		Date currentDate=new Date();
		Map<String,List<List<?>>> map=osViewHandle.creatStatiLine(osId, OsUtil.RSPOND_STATIF_FLAG, currentDate, Integer.valueOf(timespan));
		Map<String,List<List<?>>> viewMap=new  HashMap<String,List<List<?>>>();
		viewMap.put("响应时间最大值", map.get("max"));
		viewMap.put("响应时间最小值", map.get("min"));
		viewMap.put("响应时间平均值", map.get("ave"));
		return Replys.with(viewMap).as(Json.class);
	}
	/**
	 * 响应时间窗口表格
	 */
	@Post("historyRepStatiGrid/{timespan}/{osId}")
	public void historyRepSevenDayGrid(@Param("osId") String osId,@Param("timespan") String timespan,Invocation inv){
		Date currentDate=new Date();
		List<OsGridModel> osViewModels=osViewHandle.creatStatiGrid(osId, OsUtil.RSPOND_STATIF_FLAG, currentDate, Integer.valueOf(timespan));
		Page page = new PageImpl(osViewModels);
		Gridable<OsGridModel> gridable = new Gridable<OsGridModel>(page);
		gridable.setIdField("id"); 
		gridable.setCellStringField("time,minValue,maxValue,averageValue");
		try {
			UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
