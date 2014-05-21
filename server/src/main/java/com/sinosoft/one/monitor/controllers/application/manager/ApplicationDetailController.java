package com.sinosoft.one.monitor.controllers.application.manager;

import com.sinosoft.one.monitor.application.domain.ApplicationAvailableInf;
import com.sinosoft.one.monitor.application.domain.ApplicationDetailService;
import com.sinosoft.one.monitor.application.domain.ApplicationEmuService;
import com.sinosoft.one.monitor.application.domain.EumUrlsNotFoundException;
import com.sinosoft.one.monitor.application.model.BizScenario;
import com.sinosoft.one.monitor.application.model.UrlResponseTime;
import com.sinosoft.one.monitor.application.model.viewmodel.ApplicationDetailAlarmViewModel;
import com.sinosoft.one.monitor.application.model.viewmodel.ApplicationDetailPieViewModel;
import com.sinosoft.one.monitor.common.Trend;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.portal.Pipe;
import com.sinosoft.one.mvc.web.portal.Portal;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 应用明细处理Controller.
 * User: carvin
 * Date: 13-3-6
 * Time: 下午8:15
 */
@Path("detail")
public class ApplicationDetailController {
	@Autowired
	private ApplicationDetailService applicationDetailService;
	@Autowired
	private ApplicationEmuService applicationEmuService;

	@Get("/main/{applicationId}")
	public String applicationDetail(@Param("applicationId") String applicationId, Invocation invocation) {
		invocation.addModel("applicationId", applicationId);
		ApplicationAvailableInf applicationAvailableInf = null;
		try {
			applicationAvailableInf = applicationEmuService.getAppAvailableToday(applicationId);
		} catch (EumUrlsNotFoundException e) {

		}
		invocation.addModel("applicationAvailableInf", applicationAvailableInf);
		return "applicationDetail";
	}

	@Post("/alarm/{applicationId}")
	public String alarm(@Param("applicationId") String applicationId, Invocation invocation) {
		ApplicationAvailableInf applicationAvailableInf = (ApplicationAvailableInf)invocation.getModel("applicationAvailableInf");
		ApplicationDetailAlarmViewModel applicationDetailAlarmViewModel = applicationDetailService.generateAlarmViewModel(applicationId);
		String availability = "iup";
		if(applicationAvailableInf != null) {
			if(applicationAvailableInf.getTrend() == Trend.DROP) {
				availability = "idown";
			} else if(applicationAvailableInf.getTrend() == Trend.RISE) {
				availability = "iup";
			}
		}
		applicationDetailAlarmViewModel.setAvailability(availability);
		invocation.addModel("alarmViewModel", applicationDetailAlarmViewModel);
		return "applicationDetailAlarm";
	}

	@Post("/pie/{applicationId}")
	public String pie(@Param("applicationId") String applicationId, Invocation invocation) {
		ApplicationDetailPieViewModel applicationDetailPieViewModel = applicationDetailService.generatePieViewModel(applicationId);
		ApplicationAvailableInf applicationAvailableInf = (ApplicationAvailableInf)invocation.getModel("applicationAvailableInf");

		if(applicationAvailableInf == null) {
			applicationDetailPieViewModel.setAvailabilityCount(0);
			applicationDetailPieViewModel.setUnavailabilityCount(0);
		} else {
			applicationDetailPieViewModel.setAvailabilityCount(applicationAvailableInf.getAvailableCount());
			applicationDetailPieViewModel.setUnavailabilityCount(applicationAvailableInf.getCount() - applicationAvailableInf.getAvailableCount());
		}

		invocation.addModel("pieViewModel", applicationDetailPieViewModel);
		return "applicationDetailPie";
	}

	@Get("/urls/{applicationId}")
	public void urls(@Param("applicationId") String applicationId, HttpServletResponse response) throws  Exception{
		List<UrlResponseTime> urlResponseTimeList = applicationDetailService.queryUrlResponseTimesFromCatch(applicationId);
		Page page = new PageImpl(urlResponseTimeList);
		Gridable<UrlResponseTime> gridable = new Gridable<UrlResponseTime>(page);
		String cellString = "urlHref,minResponseTime,maxResponseTime,avgResponseTime,healthBar";
		gridable.setIdField("urlId");
		gridable.setCellStringField(cellString);
		UIUtil.with(gridable).as(UIType.Json).render(response);
	}
}
