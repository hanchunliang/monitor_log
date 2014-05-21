package com.sinosoft.one.monitor.controllers.application.manager;

import com.sinosoft.one.monitor.alarm.domain.AlarmService;
import com.sinosoft.one.monitor.alarm.model.Alarm;
import com.sinosoft.one.monitor.application.domain.LogDetailService;
import com.sinosoft.one.monitor.application.model.ExceptionInfo;
import com.sinosoft.one.monitor.application.model.LogDetail;
import com.sinosoft.one.monitor.application.model.MethodTraceLog;
import com.sinosoft.one.monitor.application.model.UrlTraceLog;
import com.sinosoft.one.monitor.application.repository.UrlTraceLogRepository;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Intro:
 * User: Kylin
 * Date: 13-3-9
 * Time: 上午11:41
 * To change this template use File | Settings | File Templates.
 */
@Path("appmethod")
public class ApplicationMethodController {

    @Autowired
    LogDetailService logDetailService;
    @Autowired
    UrlTraceLogRepository urlTraceLogRepository;
    @Autowired
    AlarmService alarmService;

    @Get("viewLogDetail/${applicationId}/${urlId}/${urlTraceLogId}")
    public String viewLogDetail(@Param("applicationId") String applicationId, @Param("urlId") String urlId,
                                @Param("urlTraceLogId") String urlTraceLogId, Invocation inv){
        String alarmDetailId=inv.getParameter("alarmDetailId");
        //alarmDetailId不为null，说明tracelogID为null
        if(null!=alarmDetailId){
            //alarmDetailId作为判断是有有tracelog用
            inv.addModel("alarmDetailId",alarmDetailId);
            inv.addModel("existLogId","notExist");
            //alarmId用以得到告警信息
            inv.addModel("alarmId",alarmDetailId);
        }else{
            inv.addModel("alarmDetailId",null);
            inv.addModel("existLogId","Exist");
            inv.addModel("alarmId",urlTraceLogRepository.findOne(urlTraceLogId).getAlarmId());
        }
	    inv.addModel("applicationId", applicationId);
	    inv.addModel("urlId", urlId);
		inv.addModel("logId", urlTraceLogId);
        return "logDetail";
    }

    @Get("getLogDetail/{logId}")
    public Reply getLogDetail(@Param("logId") String logId, Invocation inv){
        List<LogDetail> logDetails = logDetailService.getLogDetail(logId);
        if(logDetails != null){
            return Replys.with(logDetails).as(Json.class);
        }
        return Replys.simple().fail();
    }

    @Get("getParamDetail/{logId}")
    public String getParamDetail(@Param("logId") String logId, Invocation inv){

        String ParamData = logDetailService.getParamDetail(logId);
        return "@"+ ParamData;
    }

    @Get("getExceptionInfo/{logId}")
    public String getExceptionInfo(@Param("logId") String logId, Invocation inv){
	    List<ExceptionInfo> exceptionInfoList = logDetailService.getExceptionInfo(logId);
        String exceptionDescription="无";
        String exceptionStackTrace = "";
        if(exceptionInfoList.size()>0){
            exceptionDescription = exceptionInfoList.get(0).getExceptionDescription();
            exceptionStackTrace = exceptionInfoList.get(0).getExceptionStackTrace();
        }

        return "@" + exceptionDescription +"\n\r"+ exceptionStackTrace;
    }

    @Get("getExceptionInfoOfAlarm/{alarmDetailId}")
    public String getExceptionInfoOfAlarm(@Param("alarmDetailId") String alarmDetailId, Invocation inv){
        ExceptionInfo exceptionInfo = logDetailService.getExceptionInfoOfAlarm(alarmDetailId);
        String exceptionDescription = exceptionInfo == null ? "" : exceptionInfo.getExceptionDescription();
        String exceptionStackTrace = exceptionInfo == null ? "无" : exceptionInfo.getExceptionStackTrace();
        exceptionStackTrace = StringUtils.isBlank(exceptionStackTrace) ? "无" : exceptionStackTrace;
        return "@" + exceptionDescription+"\t"+ exceptionStackTrace;
    }

    @Get("getAlarmInfo/{alarmId}")
    public String getAlarmInfo(@Param("alarmId") String alarmId, Invocation inv){
        Alarm alarm = alarmService.findAlarm(alarmId);
        String alarmInfo = alarm == null ? "无" : alarm.getMessage();
        alarmInfo = StringUtils.isBlank(alarmInfo) ? "无" : alarmInfo;
        return "@" + alarmInfo;
    }

    @Get("getMethodDetail/{applicationId}/{urlId}/{logId}/{methodId}")
    public String getMethodDetail(@Param("applicationId") String applicationId ,
                                  @Param("urlId") String urlId,
                                  @Param("logId") String logId,
                                  @Param("methodId") String methodId,
                                  Invocation inv){

        MethodTraceLog methodTraceLog = logDetailService.getMethodDetail(methodId);
        String inParam = methodTraceLog.getInParam();
        String outParam = methodTraceLog.getOutParam();
        if("[]".equals(inParam)){
            inv.addModel("inParam" , "无");
        } else {
            inv.addModel("inParam" , inParam);
        }

        if("[]".equals(outParam)){
            inv.addModel("outParam" , "无");
        } else {
            inv.addModel("outParam" , outParam);
        }

	    inv.addModel("applicationId", applicationId);
	    inv.addModel("urlId", urlId);
	    inv.addModel("logId", logId);

        return "methodDetail";
    }
}
