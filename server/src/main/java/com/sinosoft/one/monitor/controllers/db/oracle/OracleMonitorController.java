package com.sinosoft.one.monitor.controllers.db.oracle;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.one.monitor.db.oracle.domain.OracleBatchInfoService;
import com.sinosoft.one.monitor.db.oracle.domain.OracleInfoService;
import com.sinosoft.one.monitor.db.oracle.domain.StaTimeEnum;
import com.sinosoft.one.monitor.db.oracle.model.Info;
import com.sinosoft.one.monitor.db.oracle.model.Lastevent;
import com.sinosoft.one.monitor.db.oracle.model.OracleAvaInfoModel;
import com.sinosoft.one.monitor.db.oracle.model.OracleHealthInfoModel;
import com.sinosoft.one.monitor.db.oracle.model.OracleStaBaseInfoModel;
import com.sinosoft.one.monitor.db.oracle.model.StaGraphModel;
import com.sinosoft.one.monitor.utils.MessageUtils;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;

/**
 * oracle - 批量配置视图Controller
 * @author bao
 *
 */
@Path
public class OracleMonitorController {
	@Autowired
	private OracleBatchInfoService oracleBatchInfoService;
	@Autowired
	private OracleInfoService oracleInfoService;
	/* 返回前台ajax请求数据信息*/
	private Map<String, Object> message = new HashMap<String, Object>();
	
	@Get("oracleMonitor")
	public String oracleMonitor() {
		return "oracleMonitor";
	}
	
	/**
	 * oracle - 批量配置视图
	 * 包含：可用性、性能、列表视图
	 * @param inv
	 * @return
	 */
	@Get("avaInfoList/{avaInfoStyle}")
    public String avaInfoList(@Param("avaInfoStyle")int avaInfoStyle, Invocation inv){
		StaTimeEnum avaStyle = null;
		String dateStyle = "";
		switch (avaInfoStyle) {
		case 1: //24小时内
			dateStyle = "HH:mm";
			avaStyle = StaTimeEnum.LAST_24HOUR;
			break;
		case 30: //30天统计信息
			dateStyle = "yyyy-MM-dd HH:mm";
			avaStyle = StaTimeEnum.LAST_30DAY;
			break;
		default: //默认返回24小时
			dateStyle = "HH:mm";
			avaStyle = StaTimeEnum.LAST_24HOUR;
			break;
		}
		/* 查询可用性历史纪录信息*/
        List<OracleAvaInfoModel> oracleAvaInfoList = oracleBatchInfoService.avaInfoList(avaStyle);
        inv.addModel("oracleAvaInfoList",oracleAvaInfoList);
        List<String> dateList = new ArrayList<String>();
        //long startTime = 0L;
        if(dateList!=null&&oracleAvaInfoList.size()>0){
            long startTime = new Date().getTime();
            if(oracleAvaInfoList.get(0).getGraphInfo()!=null
                    &&oracleAvaInfoList.get(0).getGraphInfo().size()>0
                    &&oracleAvaInfoList.get(0).getGraphInfo().get(0)!=null
                    &&oracleAvaInfoList.get(0).getGraphInfo().get(0).length>0){
                String time = oracleAvaInfoList.get(0).getGraphInfo().get(0)[0];
                startTime = Long.valueOf(time);
            }
            SimpleDateFormat sdf = new SimpleDateFormat(dateStyle);
            for(int i=0;i<24;i++){
                dateList.add(sdf.format(new Date(startTime)));
                startTime += 3600*1000;
            }
        }
        inv.addModel("avaInfoStyle", avaInfoStyle);
        inv.addModel("dateList", dateList);
        return "oracleAvaInfo";
    }
	
	/**
	 * 性能信息
	 * @return
	 */
    @Post("performance")
    @Get("performance")
	public String performance(){
		List<StaGraphModel> staGraphs = oracleBatchInfoService.listMonitorEventSta();
		/* 缓存库命中率*/
        HighchartForMany memory_utilization = new HighchartForMany("memory_utilization");
		/* 活动的远程连接数*/
		//Highchart CPU_utilization = new Highchart("CPU_utilization");
		/* 连接时间*/
        HighchartForMany exchange_utilization = new HighchartForMany("exchange_utilization");
		/* 活动连接数*/
        HighchartForMany reply_utilization = new HighchartForMany("reply_utilization");
		for(StaGraphModel staGraph : staGraphs) {
            HighchartSerieForMany memorySerie = new HighchartSerieForMany(staGraph.getName());
            HighchartSerieForMany exchangeSerie = new HighchartSerieForMany(staGraph.getName());
            HighchartSerieForMany replySerie = new HighchartSerieForMany(staGraph.getName());
			for(Lastevent lastevent : staGraph.getLasteventList()) {
				memorySerie.addData(lastevent.getRecordTime(),lastevent.getBufferHitRate()*100.0);
				exchangeSerie.addData(lastevent.getRecordTime(),Double.valueOf(lastevent.getConnectTime()));
				replySerie.addData(lastevent.getRecordTime(),Double.valueOf(lastevent.getActiveCount()));
			}
			memory_utilization.addSerie(memorySerie);
			exchange_utilization.addSerie(exchangeSerie);
			reply_utilization.addSerie(replySerie);
		}
        return "@"+JSON.toJSONString(Arrays.asList(memory_utilization, exchange_utilization, reply_utilization));
	}

	/**
	 * 健康状态列表
	 * @return
	 */
	@Get("healthList/{healthType}")
	public Reply healthList(@Param("healthType")int healthType, Invocation inv) {
		String contextPath = inv.getServletContext().getContextPath();
		StaTimeEnum healthStyle = null;
		switch (healthType) {
		case 1: //24小时内
			healthStyle = StaTimeEnum.LAST_24HOUR;
			break;
		case 30: //30天统计信息
			healthStyle = StaTimeEnum.LAST_30DAY;
			break;
		default: //默认返回24小时
			healthStyle = StaTimeEnum.LAST_24HOUR;
			break;
		}
		List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
		List<OracleHealthInfoModel> oracleHealthInfos = oracleBatchInfoService.healthInfoList(healthStyle);
		for(OracleHealthInfoModel oracleHealthInfo : oracleHealthInfos) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", oracleHealthInfo.getMonitorID());
			List<String> cell = new ArrayList<String>();
			/* 构建数据库监控详细信息地址*/
			String url = contextPath + "/db/oracle/home/viewInfo/"+ oracleHealthInfo.getMonitorID();
			cell.add(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_A, url,oracleHealthInfo.getMonitorName()));
			for(int i=0; i<oracleHealthInfo.getGraphInfo().size(); i++) {
				String[] values = oracleHealthInfo.getGraphInfo().get(i);
				String cssClass = "unknow";
				if("1".equals(values[0])) { //正常
					cssClass = "normal";
				} else if("2".equals(values[0])) { //警告
					cssClass = "warn";
				} else if("3".equals(values[0])) { //严重
					cssClass = "critical";
				} else if("4".equals(values[0])) { //未知
					cssClass = "unknow";
				}
				//cell.add(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_SPAN,  cssClass, values[1]));
				cell.add(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_SPAN,  cssClass, ""));
			}
			row.put("cell", cell);
			rows.add(row);
		}
		Map<String, Object> grid = new HashMap<String, Object>();
		grid.put("rows", rows);
		return Replys.with(grid).as(Json.class);
	}
	
	/**
	 * 数据库列表视图
	 * @return
	 */
	public Reply thresholdList(Invocation inv) {
		return Replys.with(buildThresholdGrid(inv)).as(Json.class);
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	@Get("addUI")
	public String addUI() {
		return "oracleSave";
	}
	
	/**
	 * 添加操作
	 * @param oracleInfo
	 * @param inv
	 * @return
	 */
	@Post("save")
	public Reply add(Info oracleInfo,Invocation inv) {
		try {
			oracleInfo.setSysTime(new Date());
            oracleInfoService.saveMonitor(oracleInfo);
			message.put("result", true);
		} catch (Exception e) {
			message.put("result", false);
			message.put("type", "disconnect");
			message.put("tip", "该数据库地址无法连接，请确认信息是否正确!");
		}
		return Replys.with(message).as(Json.class);
	}
	
	/**
	 * 编辑页面
	 * @param monitorId
	 * @param inv
	 * @return
	 */
	@Get("editUI/{monitorId}")
	public String editUI(@Param("monitorId")String monitorId,Invocation inv) {
		Info oracleInfo = oracleInfoService.getInfo(monitorId);
		inv.addModel("oracleInfo", oracleInfo);
		return "/views/addOracle.jsp";
	}
	
	/**
	 * 编辑操作
	 * @param oracleInfo
	 * @param inv
	 * @return
	 */
	@Post("edit")
	public Reply edit(Info oracleInfo,Invocation inv) {
		try{
			Info info = oracleInfoService.getInfo(oracleInfo.getId());
			info.setName(oracleInfo.getName());
			info.setIpAddress(oracleInfo.getIpAddress());
			info.setSubnetMask(oracleInfo.getSubnetMask());
			info.setPort(oracleInfo.getPort());
			info.setPullInterval(oracleInfo.getPullInterval());
			info.setUsername(oracleInfo.getUsername());
			info.setPassword(oracleInfo.getPassword());
			info.setInstanceName(oracleInfo.getInstanceName());
			oracleInfoService.editMonitor(info);
			message.put("result", true);
		} catch (Exception e) {
			message.put("result", false);
			message.put("type", "disconnect");
			message.put("tip", "该数据库地址无法连接，请确认信息是否正确!");
		}
		return Replys.with(message).as(Json.class);
	}
	
	/**
	 * 删除操作(包含删除一个和批量删除操作)
	 * @param monitorIds
	 * @param inv
	 * @return
	 */
	@Get("remove")
	public Reply remove(@Param("monitorIds")List<String> monitorIds, Invocation inv) {

		oracleInfoService.deleteMonitor(monitorIds);
		message.put("result", true);
		return Replys.with(message).as(Json.class);
	}
	
	/**
	 * 构建数据库列表视图数据
	 * @param inv
	 * @return
	 */
	private Map<String, Object> buildThresholdGrid(Invocation inv) {
		/* 获取项目根路径*/
		String contextPath = inv.getServletContext().getContextPath();
		/* 封装表格行数据信息List->rows*/
		List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
		/* 查询数据库健康列表数据*/
		List<OracleStaBaseInfoModel> oracleStaBaseInfos = oracleBatchInfoService.listStaBaseInfo();
		
		String message_format_a="<a href={0}>{1}</a>";
//		String message_form_a_subtitle="<a href={0} title={1}>{2}</a>";
		String message_format_div="<div class={0}></div>";
//		String message_format_span="<span class={0}>{1}</span>";
		String handler_format="<a href={0} class='eid'>编辑</a> <a href='javascript:void(0)' class='del' onclick='delRow(this)'>删除</a>";
		/* 循环构建表格行数据*/
		for(OracleStaBaseInfoModel oracleStaBaseInfo : oracleStaBaseInfos) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", oracleStaBaseInfo.getMonitorID());
			List<String> cell = new ArrayList<String>();
			/* 可用性 1-可用sys_up ；0-不可用 sys_down*/
			String usabilityClass = "1".equals(oracleStaBaseInfo.getUsability()) ? "fine" : "poor";
			/* 健康状况 1-健康(绿色=fine) ；其它状态均不健康(红色=poor)*/
			String[] healthy = oracleStaBaseInfo.getHealthy();
			String healthyClass = "1".equals(healthy[0]) ? "fine" : "poor";
			/* 构建数据库监控详细信息地址*/
			String url = contextPath + "/db/oracle/home/viewInfo/"+ oracleStaBaseInfo.getMonitorID();
			/* 构建修改连接+对应数据库MonitorID*/
			String editUrl = contextPath + "/db/oracle/editUI/" + oracleStaBaseInfo.getMonitorID();
			/* 格式化表格数据信息*/
			cell.add(MessageFormat.format(message_format_a, url, oracleStaBaseInfo.getMonitorName()));
			cell.add(MessageFormat.format(message_format_div, usabilityClass));
			cell.add(MessageFormat.format(message_format_div, healthyClass));
			cell.add(MessageFormat.format(handler_format, editUrl));
			row.put("cell", cell);
			rows.add(row);
		}
		Map<String, Object> grid = new HashMap<String, Object>();
		grid.put("rows", rows);
		return grid;
	}
}
class HighchartForMany {

    /** 渲染元素ID*/
    private String renderId;
    /** x轴显示数据*/
    private List<String> categories = new ArrayList<String>();
    /** 线性数据*/
    private List<HighchartSerieForMany> Series = new ArrayList<HighchartSerieForMany>();

    /**
     * step
     */
    private int step = 1;

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public HighchartForMany(String renderId) {
        this.renderId = renderId;
    }

    public String getRenderId() {
        return renderId;
    }

    public void setRenderId(String renderId) {
        this.renderId = renderId;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void addCategory(String category) {
        categories.add(category);
    }

    public List<HighchartSerieForMany> getSeries() {
        return Series;
    }

    public void addSerie(HighchartSerieForMany Serie) {
        Series.add(Serie);
    }
}

class HighchartSerieForMany{
    private String name;
    private List<List> data = new ArrayList<List>();
    public HighchartSerieForMany(String name) {
        this.name = name;
    }
    public void addData(Date date,Double yValue) {
        List<String> list = new ArrayList<String>();
        list.add("Date.UTC("+date.getYear()+","+date.getMonth()+","+date.getDate()+","+date.getHours()+","+date.getMinutes()+","+date.getSeconds()+")");
        list.add(yValue+"");
        data.add(list);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<List> getData() {
        return data;
    }

    public void setData(List<List> data) {
        this.data = data;
    }
}
