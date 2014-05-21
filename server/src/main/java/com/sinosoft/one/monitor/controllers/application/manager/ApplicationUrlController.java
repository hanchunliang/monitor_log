 package com.sinosoft.one.monitor.controllers.application.manager;

import com.sinosoft.one.monitor.application.domain.ApplicationUrlService;
import com.sinosoft.one.monitor.application.model.MethodResponseTime;
import com.sinosoft.one.monitor.application.model.viewmodel.ApplicationUrlCountViewModel;
import com.sinosoft.one.monitor.application.model.viewmodel.ApplicationUrlHealthAvaViewModel;
import com.sinosoft.one.monitor.application.model.viewmodel.ApplicationUrlInfoViewModel;
import com.sinosoft.one.monitor.application.model.viewmodel.UrlTraceLogViewModel;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 应用URL Controller
 * User: carvin
 * Date: 13-3-9
 * Time: 下午11:31
 */
@Path("/url")
public class ApplicationUrlController {
	@Autowired
	private ApplicationUrlService applicationUrlService;

	@Get("/methodresponsetime/${applicationId}/${urlId}")
	@Post("/methodresponsetime/${applicationId}/${urlId}")
	public void queryMethodResponseTimes(@Param("applicationId") String applicationId, @Param("urlId") String urlId,
	                                     Invocation invocation) throws Exception {
		int pageNo = Integer.parseInt(invocation.getParameter("pageNo"));
		int rowNum = Integer.parseInt(invocation.getParameter("rowNum"));
		Pageable pageable = new PageRequest(pageNo-1, rowNum);
		Page<MethodResponseTime> methodResponseTimeList = applicationUrlService.queryMethodResponseTimes(pageable, urlId);
		Gridable<MethodResponseTime> gridable = new Gridable<MethodResponseTime>(methodResponseTimeList);
		String cellString = "methodName,maxResponseTime,minResponseTime,avgResponseTime";
		gridable.setIdField("methodId");
		gridable.setCellStringField(cellString);
		UIUtil.with(gridable).as(UIType.Json).render(invocation.getResponse());
	}

    @Post("/tracelog/${urlId}")
    @Get("/tracelog/${urlId}")
    public void queryUrlTraceLogs(@Param("urlId") String urlId,
                                  Invocation invocation) throws Exception {
        int pageNo = Integer.parseInt(invocation.getParameter("pageNo"));
        int rowNum = Integer.parseInt(invocation.getParameter("rowNum"));
        Pageable pageable = new PageRequest(pageNo-1, rowNum);
        String givenTime=invocation.getRequest().getParameter("_givenTime");
        String givenSeverity=invocation.getRequest().getParameter("_givenSeverity");
        Page<UrlTraceLogViewModel> urlTraceLogList=null;
        if(!StringUtils.isBlank(givenTime)||!StringUtils.isBlank(givenSeverity)){
            urlTraceLogList = applicationUrlService.queryUrlTraceLogs(pageable, urlId,givenTime,givenSeverity);
        }else {
            urlTraceLogList = applicationUrlService.queryUrlTraceLogs(pageable, urlId);
        }
        Gridable<UrlTraceLogViewModel> gridable = new Gridable<UrlTraceLogViewModel>(urlTraceLogList);
        String cellString = "userIp,recordTimeStr,statusStr,operateStr";
        gridable.setIdField("id");
        gridable.setCellStringField(cellString);
        UIUtil.with(gridable).as(UIType.Json).render(invocation.getResponse());
    }

	@Get("/main/${applicationId}/${urlId}")
	public String main(@Param("applicationId") String applicationId, @Param("urlId") String urlId,
	                 Invocation invocation) {
		ApplicationUrlInfoViewModel applicationUrlInfoViewModel = applicationUrlService.generateUrlInfoViewModel(applicationId, urlId);
		invocation.addModel("urlInfo", applicationUrlInfoViewModel);
		return "urlDetail";
	}

	@Get("/healthava/${applicationId}/${urlId}")
	public Reply queryUrlHealthAndAva(@Param("applicationId") String applicationId, @Param("urlId") String urlId) {
		ApplicationUrlHealthAvaViewModel applicationUrlHealthAvaViewModel = applicationUrlService.generateUrlHealthAndAvaStaViewModel(applicationId, urlId);
		return Replys.with(applicationUrlHealthAvaViewModel.toJsonString()).as(Json.class);
	}

	@Get("/urlcountsta/${applicationId}/${urlId}")
	public Reply queryUrlCountSta(@Param("applicationId") String applicationId, @Param("urlId") String urlId) {
		ApplicationUrlCountViewModel applicationUrlCountViewModel = applicationUrlService.generateUrlCountStaViewModel(applicationId, urlId);
		return Replys.with(applicationUrlCountViewModel.toJsonString()).as(Json.class);
	}
}