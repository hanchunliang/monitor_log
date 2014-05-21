package com.sinosoft.one.monitor.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.one.monitor.alarm.domain.AlarmService;
import com.sinosoft.one.monitor.alarm.model.Alarm;
import com.sinosoft.one.monitor.application.domain.ApplicationEnhance;
import com.sinosoft.one.monitor.application.domain.ApplicationEnhanceFactory;
import com.sinosoft.one.monitor.application.domain.ApplicationService;
import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.utils.MessageUtils;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;

/**
 * 首页
 * @author bao
 *
 */
@Path
public class IndexController {
	
	@Autowired
	private AlarmService alarmService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private ApplicationEnhanceFactory applicationEnhanceFactory;

    private Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Get("index")
	public String index() {
		return "index";
	}
	
	public Reply alarmList(Invocation inv) {
		/* 获取项目根路径*/
		String contextPath = inv.getServletContext().getContextPath();
		/* 封装表格行数据信息List->rows*/
		List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
		/* 查询数据库健康列表数据*/
		List<Alarm> alarmList = alarmService.queryLatestAlarmsRowsFifty();
		/* 循环构建表格行数据*/
		for(Alarm alarm : alarmList) {
			Map<String, Object> row = new HashMap<String, Object>();
			List<String> cell = new ArrayList<String>();
			
			/* 健康状况 1-健康(绿色=fine) ；其它状态均不健康(红色=poor)*/
			String healthyClass = MessageUtils.SeverityLevel2CssClass(alarm.getSeverity());
			/* 构建预警详细信息地址*/
			String url = contextPath + "/alarm/manager/alarmmanager/detail/"+alarm.getId();
			/* 格式化表格数据信息*/
			cell.add(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_DIV, healthyClass));
			String title = alarm.getMessage();
			String subTitle = title.length()>10 ? title.substring(0, 9)+"...." : title;
			cell.add(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORM_A_SUBTITLE, url, title, subTitle));
			cell.add(alarm.getResourceName());
			cell.add(alarm.getMonitorType());
			cell.add(DateFormatUtils.format(alarm.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			row.put("cell", cell);
			rows.add(row);
		}
		Map<String, Object> grid = new HashMap<String, Object>();
		grid.put("rows", rows);
		return Replys.with(grid).as(Json.class);
	}
	
	public Reply applicationList(Invocation inv) {
		/* 获取项目根路径*/
		String contextPath = inv.getServletContext().getContextPath();
		/* 封装表格行数据信息List->rows*/
		List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
		/* 查询数据库健康列表数据*/
        long start = System.currentTimeMillis();
		List<Application> applications = applicationService.findValidateApplication();
        long cost = System.currentTimeMillis()-start;
        logger.debug("applicationService.findValidateApplication() consumeTime："+cost);
        start = System.currentTimeMillis();
		/* 循环构建表格行数据*/
		for(Application application : applications) {
			ApplicationEnhance applicationEnhance = null;
            long s = System.currentTimeMillis();
			applicationEnhance = applicationEnhanceFactory.enhanceApplication(application);
            logger.debug("applicationEnhanceFactory："+(System.currentTimeMillis()-s));
			Map<String, Object> row = new HashMap<String, Object>();
			List<String> cell = new ArrayList<String>();
			/* 健康状况 1-健康(绿色=fine) ；其它状态均不健康(红色=poor)*/
			String healthyClass = MessageUtils.SeverityLevel2CssClass(applicationEnhance.getHealth());
			
			/* 可用性 1-可用sys_up ；0-不可用 sys_down*/
			String usabilityClass = MessageUtils.trend2CssClass(applicationEnhance.getTrend());
			/* 构建数据库监控详细信息地址*/
			String url = contextPath + "/application/manager/detail/main/"+application.getId();
			/* 格式化表格数据信息*/
			cell.add(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_A, url, application.getApplicationName()));
			cell.add(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_DIV, usabilityClass));
			cell.add(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_DIV, healthyClass));
			row.put("cell", cell);
			rows.add(row);
		}
        cost = System.currentTimeMillis()-start;
        logger.debug("循环构建表格行数据："+cost);
        start = System.currentTimeMillis();
		Map<String, Object> grid = new HashMap<String, Object>();
		grid.put("rows", rows);
		return Replys.with(grid).as(Json.class);
	}
}
