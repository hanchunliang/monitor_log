package com.sinosoft.one.monitor.controllers.alarm.manager;

import com.sinosoft.one.monitor.alarm.domain.AlarmService;
import com.sinosoft.one.monitor.alarm.model.Alarm;
import com.sinosoft.one.monitor.alarm.repository.AlarmRepository;
import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.application.model.UrlTraceLog;
import com.sinosoft.one.monitor.application.repository.ApplicationRepository;
import com.sinosoft.one.monitor.application.repository.UrlTraceLogRepository;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.monitor.db.oracle.repository.InfoRepository;
import com.sinosoft.one.monitor.os.linux.repository.OsRepository;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 告警列表和告警详细信息处理Controller.
 * User: zfb
 * Date: 13-3-8
 * Time: 下午3:11
 */
@Path("alarmmanager")
public class AlarmManagerController {

    @Autowired
    AlarmRepository alarmRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    AlarmService alarmService;
    @Autowired
    OsRepository osRepository;
    @Autowired
    InfoRepository infoRepository;
    @Autowired
    UrlTraceLogRepository urlTraceLogRepository;

    @Get("list")
    public String getAlarmList(Invocation inv){
        return "alarmList";
    }

    //向页面返回json数据
    private void getJsonDataOfAlarms(Page<Alarm> alarms,Invocation inv) throws Exception {
        Gridable<Alarm> gridable=new Gridable<Alarm>(alarms);
        gridable.setIdField("id");
        gridable.setCellStringField("status,message,appName,monitorType,recordTime");
        try {
            UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
        } catch (Exception e) {
            throw new Exception("JSON数据转化出错！",e);
        }
    }

    //没有选择时间和类型的ajax请求
    @Post("alarm")
    public void getAlarmListWithNoChoice(Invocation inv) throws Exception {
        int currentPageNumber=Integer.valueOf(inv.getRequest().getParameter("pageNo"))-1;
        Page<Alarm> allAlarms= alarmService.queryLatestAlarmsByPageNo(currentPageNumber);
        getAlarmListWithGivenCondition(allAlarms, inv);
    }

    //只选择时间,或者只选择类型的ajax请求
    @Post("onecondition/{givenTimeOrTypeOrLevel}")
    public void getAlarmListWithGivenTimeOrType(@Param("givenTimeOrTypeOrLevel")String givenTimeOrTypeOrLevel,Invocation inv) throws Exception {
        int currentPageNumber=Integer.valueOf(inv.getRequest().getParameter("pageNo"))-1;
        PageRequest pageRequest = new PageRequest(currentPageNumber,10);
        Page<Alarm> timeOrTypeAlarms = null;
        String condition="";
      
        if(!StringUtils.isBlank(givenTimeOrTypeOrLevel)){
            if("twentyFourHours".equals(givenTimeOrTypeOrLevel)){
            	condition=String.valueOf(24);
                timeOrTypeAlarms=alarmRepository.findAlarmsWithGivenTime(condition,pageRequest);
            }else if("thirtyDays".equals(givenTimeOrTypeOrLevel)){
            	condition=String.valueOf(30);
                timeOrTypeAlarms=alarmRepository.findAlarmsWithGivenTime(condition,pageRequest);
            }else if(ResourceType.APPLICATION.name().equals(givenTimeOrTypeOrLevel)){
            	condition=ResourceType.APPLICATION.name();
                timeOrTypeAlarms=alarmRepository.findAlarmsWithGivenType(condition,pageRequest);
            }else if(ResourceType.OS.name().equals(givenTimeOrTypeOrLevel)){
            	condition=ResourceType.OS.name();
                timeOrTypeAlarms=alarmRepository.findAlarmsWithGivenType(condition,pageRequest);
            }else if(ResourceType.DB.name().equals(givenTimeOrTypeOrLevel)){
            	condition=ResourceType.DB.name();
                timeOrTypeAlarms=alarmRepository.findAlarmsWithGivenType(condition,pageRequest);
            }
            else if(SeverityLevel.CRITICAL.name().equals(givenTimeOrTypeOrLevel)){
            	condition=SeverityLevel.CRITICAL.name();
                timeOrTypeAlarms=alarmRepository.findAlarmsWithGivenLevel(condition,pageRequest);
            }else if(SeverityLevel.WARNING.name().equals(givenTimeOrTypeOrLevel)){
            	condition=SeverityLevel.WARNING.name();
                timeOrTypeAlarms=alarmRepository.findAlarmsWithGivenLevel(condition,pageRequest);
            }

            getAlarmListWithGivenCondition(timeOrTypeAlarms, inv);
        }else {
            timeOrTypeAlarms= alarmService.queryLatestAlarmsByPageNo(currentPageNumber);
            getAlarmListWithGivenCondition(timeOrTypeAlarms, inv);
        }
    }

    //时间和类型都选择的ajax请求
    @Post("twocondition/{conditionOne}/{conditiontwo}")
    public void getAlarmListWithGivenTimeAndType(@Param("conditionOne")String conditionOne, @Param("conditiontwo") String conditiontwo,Invocation inv) throws Exception {
        int currentPageNumber=Integer.valueOf(inv.getRequest().getParameter("pageNo"))-1;
        PageRequest pageRequest = new PageRequest(currentPageNumber,10);
        String givenTime="";
        String givenType="";
        String givenLevel="";
        Page<Alarm> allAlarmsWithGivenTimeAndType=null;
        if("twentyFourHours".equals(conditionOne)||"twentyFourHours".equals(conditiontwo)){
        	givenTime=String.valueOf(24);
        }else if("thirtyDays".equals(conditionOne)||"thirtyDays".equals(conditiontwo)){
        	givenTime=String.valueOf(30);
        }
        if(ResourceType.APPLICATION.name().equals(conditionOne)||ResourceType.APPLICATION.name().equals(conditiontwo)){
        	givenType=ResourceType.APPLICATION.name();
        }else if(ResourceType.OS.name().equals(conditionOne)||ResourceType.OS.name().equals(conditiontwo)){
        	givenType=ResourceType.OS.name();
        }else if(ResourceType.DB.name().equals(conditionOne)||ResourceType.DB.name().equals(conditiontwo)){
        	givenType=ResourceType.DB.name();
        }
        if(SeverityLevel.CRITICAL.name().equals(conditionOne)||SeverityLevel.CRITICAL.name().equals(conditiontwo)){
        	givenLevel=SeverityLevel.CRITICAL.name();
        }else if(SeverityLevel.WARNING.name().equals(conditionOne)||SeverityLevel.WARNING.name().equals(conditiontwo)){
        	givenLevel=SeverityLevel.WARNING.name();
        }
        if(givenTime!=""&&givenType!=""){
        	allAlarmsWithGivenTimeAndType=alarmRepository.findAlarmsWithGivenTimeAndType(givenTime,givenType,pageRequest);
        }
        if(givenTime!=""&&givenLevel!=""){
        	allAlarmsWithGivenTimeAndType=alarmRepository.findAlarmsWithGivenTimeAndLevel(givenTime,givenLevel,pageRequest);
        }
        if(givenType!=""&&givenLevel!=""){
        	allAlarmsWithGivenTimeAndType=alarmRepository.findAlarmsWithGivenTypeAndLevel(givenType,givenLevel,pageRequest);
        }
        getAlarmListWithGivenCondition(allAlarmsWithGivenTimeAndType,inv);
    }

    @Post("threecondition/{conditionOne}/{conditiontwo}/{conditionthree}")
    public void getAlarmListWithGivenTimeAndTypeAndLevel(@Param("conditionOne")String conditionOne, @Param("conditiontwo") String conditiontwo,@Param("conditionthree") String conditionthree, Invocation inv) throws Exception {
        int currentPageNumber=Integer.valueOf(inv.getRequest().getParameter("pageNo"))-1;
        PageRequest pageRequest = new PageRequest(currentPageNumber,10);
        String givenTime="";
        String givenType="";
        String givenLevel="";
        Page<Alarm> allAlarmsWithGivenTimeAndType=null;
        if("twentyFourHours".equals(conditionOne)||"twentyFourHours".equals(conditiontwo)){
        	givenTime=String.valueOf(24);
        }else if("thirtyDays".equals(conditionOne)||"thirtyDays".equals(conditiontwo)){
        	givenTime=String.valueOf(30);
        }
        if(ResourceType.APPLICATION.name().equals(conditionOne)||ResourceType.APPLICATION.name().equals(conditiontwo)){
        	givenType=ResourceType.APPLICATION.name();
        }else if(ResourceType.OS.name().equals(conditionOne)||ResourceType.OS.name().equals(conditiontwo)){
        	givenType=ResourceType.OS.name();
        }else if(ResourceType.DB.name().equals(conditionOne)||ResourceType.DB.name().equals(conditiontwo)){
        	givenType=ResourceType.DB.name();
        }
        if(SeverityLevel.CRITICAL.name().equals(conditionOne)||SeverityLevel.CRITICAL.name().equals(conditionthree)){
        	givenLevel=SeverityLevel.CRITICAL.name();
        }else if(SeverityLevel.WARNING.name().equals(conditionOne)||SeverityLevel.WARNING.name().equals(conditionthree)){
        	givenLevel=SeverityLevel.WARNING.name();
        }
         allAlarmsWithGivenTimeAndType=alarmRepository.findAlarmsWithThreeCondition(givenTime,givenType,givenLevel,pageRequest);
        getAlarmListWithGivenCondition(allAlarmsWithGivenTimeAndType,inv);
    }
    
    //各种条件组个统一调用这个方法，获得告警列表
    private void getAlarmListWithGivenCondition(Page<Alarm> pageAlarms,Invocation inv) throws Exception {
        List<Alarm> newAlarms=new ArrayList<Alarm>();
        if(pageAlarms!=null&&pageAlarms.getContent()!=null&&pageAlarms.getContent().size()>0){
            List<Alarm> dbAlarms=pageAlarms.getContent();
                String statusStart="<div class='";
                /*String statusEnd="' onclick='viewRelevance()'></div>";*/
                String statusEnd="'</div>";
                String messageNameStart="<a href='javascript:void(0)' onclick='alarmDetailInfo(this)'>";
                String messageNameEnd="</a>";
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(int i=0;i<pageAlarms.getContent().size();i++){
                Alarm tempAlarm=pageAlarms.getContent().get(i);
                String statusMiddle="";
                String tempStatusMiddle=getStatusOfAlarm(tempAlarm.getSeverity());
                if(!StringUtils.isBlank(tempStatusMiddle)){
                    //设置状态
                    statusMiddle=tempStatusMiddle;
                }
                //拼接状态字符串
                tempAlarm.setStatus(statusStart+statusMiddle+statusEnd);
                //得到告警标题
                String allMessage=tempAlarm.getMessage();
                if(!StringUtils.isBlank(allMessage)){
                    String[] messageArray=allMessage.split("<br>");
                    tempAlarm.setMessage(messageNameStart+messageArray[0]+messageNameEnd);
                }else {
                    tempAlarm.setMessage(messageNameStart+messageNameEnd);
                }
                //拼接应用中文名
                if(ResourceType.APPLICATION.name().equals(tempAlarm.getMonitorType())){
                    Application application = applicationRepository.findOne(tempAlarm.getMonitorId());
                    if(application!=null){
                        tempAlarm.setAppName(application.getCnName());
                    }
                }else if(ResourceType.OS.name().equals(tempAlarm.getMonitorType())){
                    tempAlarm.setAppName(osRepository.findOne(tempAlarm.getMonitorId()).getName());
                }else if(ResourceType.DB.name().equals(tempAlarm.getMonitorType())){
                    tempAlarm.setAppName(infoRepository.findOne(tempAlarm.getMonitorId()).getName());
                }
                //获得类型对应的中文名
                tempAlarm.setMonitorType(ResourceType.valueOf(tempAlarm.getMonitorType()).cnName());
                //格式化时间，供页面显示
                tempAlarm.setRecordTime(formatter.format(tempAlarm.getCreateTime()));
            }
                getJsonDataOfAlarms(pageAlarms,inv);
                return;
            /*gridable.setCellStringField("status,message,appName,monitorType,recordTime,ownerName");*/
        }else {
            getJsonDataOfAlarms(pageAlarms,inv);
            return;
        }
    }

    //获得与前台页面相对应的状态
    private  String getStatusOfAlarm(SeverityLevel severityLevel){
        if(severityLevel!=null){
            if(severityLevel.name().equals("CRITICAL")){
                return "poor";
            }else if(severityLevel.name().equals("WARNING")){
                return "y_poor";
            }else if(severityLevel.name().equals("INFO")){
                return "fine";
            }
        }
        return "";
    }
    //获得与前台页面相对应的图片
    private String getImageOfAlarm(SeverityLevel severityLevel){
        if(severityLevel!=null){
            if(severityLevel.name().equals("CRITICAL")){
                return "bussinessY2.gif";
            }else if(severityLevel.name().equals("WARNING")){
                return "bussinessY3.gif";
            }else if(severityLevel.name().equals("INFO")){
                return "bussinessY.gif";
            }
        }
        return "";
    }

    //告警详细信息页面
    @Get("detail/{alarmId}")
    public String getAlarmDetail(@Param("alarmId") String alarmId,Invocation inv){
        Alarm dbAlarm=alarmRepository.findOne(alarmId);
        if(ResourceType.APPLICATION.name().equals(dbAlarm.getMonitorType())){
            inv.addModel("monitorName",applicationRepository.findOne(dbAlarm.getMonitorId()).getCnName());
            inv.addModel("monitorType",ResourceType.APPLICATION.name());
            UrlTraceLog urlTraceLog=urlTraceLogRepository.findByAlarmId(dbAlarm.getId());
            if(null==urlTraceLog){
                inv.addModel("urlTraceLogUrlId","-1");
                inv.addModel("urlTraceLogId","notExist");
            }else{
                String urlId = urlTraceLog.getUrlId();
                inv.addModel("urlTraceLogUrlId",StringUtils.isBlank(urlId)?"-1":urlId);
                inv.addModel("urlTraceLogId",urlTraceLog.getId());
            }
        }else if(ResourceType.OS.name().equals(dbAlarm.getMonitorType())){
            inv.addModel("monitorName",osRepository.findOne(dbAlarm.getMonitorId()).getName());
        }else if(ResourceType.DB.name().equals(dbAlarm.getMonitorType())){
            inv.addModel("monitorName",infoRepository.findOne(dbAlarm.getMonitorId()).getName());
        }
        inv.addModel("alarm",dbAlarm);
        inv.addModel("_cnName",dbAlarm.getSeverity().cnName());
        inv.addModel("alarmImage",getImageOfAlarm(dbAlarm.getSeverity()));
        //用以发送ajax，获得当前监视器的历史告警信息
        inv.addModel("monitorId",dbAlarm.getMonitorId());
        return "alarmDetail";
    }

    //当前监视器的历史告警信息
    @Post("history/{monitorId}")
    public void getPageOfHistoryAlarm(@Param("monitorId") String monitorId,Invocation inv) throws Exception {
        int currentPageNumber=Integer.valueOf(inv.getRequest().getParameter("pageNo"))-1;
        PageRequest pageRequest = new PageRequest(currentPageNumber,10);
        Page<Alarm> dbAlarms=alarmRepository.findByMonitorId(monitorId,pageRequest);
        if(null!=dbAlarms&&dbAlarms.getContent()!=null&&dbAlarms.getContent().size()>0){
            String statusStart="<div class='";
            String statusEnd="'></div>";
            String messageStart="<p class=\"magess\">";
            String messageEnd="</p>";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<Alarm> tempAlarms=new ArrayList<Alarm>();
            for(int i=0;i<dbAlarms.getContent().size();i++){
                Alarm tempAlarm=dbAlarms.getContent().get(i);
                tempAlarm.setStatus(statusStart+getStatusOfAlarm(tempAlarm.getSeverity())+statusEnd);
                tempAlarm.setRecordTime(formatter.format(tempAlarm.getCreateTime()));
                tempAlarm.setMessage(messageStart+tempAlarm.getMessage()+messageEnd);
            }

            /*for(Alarm alarm:dbAlarms){
                //拼接页面显示的状态
                alarm.setStatus(statusStart+getStatusOfAlarm(alarm.getSeverity())+statusEnd);
                alarm.setRecordTime(formatter.format(alarm.getCreateTime()));
                alarm.setMessage(messageStart+alarm.getMessage()+messageEnd);
                tempAlarms.add(alarm);
            }
            Page historyAlarmPage=new PageImpl(tempAlarms);*/
            Gridable<Alarm> gridable=new Gridable<Alarm>(dbAlarms);
            gridable.setIdField("id");
            gridable.setCellStringField("status,recordTime,message");
            try {
                UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
            } catch (Exception e) {
                throw new Exception("JSON数据转换出错！",e);
            }
        }
    }

    //批量删除告警详细信息
    @Post("batchdelete")
    public String batchDeleteAlarms(Invocation inv){
        String[] alarmIds=inv.getRequest().getParameterValues("alarmIds[]");
        //执行批量删除告警的SQL
        if(null!=alarmIds&&alarmIds.length>0){
            alarmRepository.batchDeleteAlarms(alarmIds);
            return "@successDeleted";
        }
        return "@failDeleted";
    }

    @Get("viewLogDetail/${applicationId}/${urlId}/${urlTraceLogId}")
    public String viewLogDetail(@Param("applicationId") String applicationId, @Param("urlId") String urlId,
                                @Param("urlTraceLogId") String urlTraceLogId, Invocation inv){
        String alarmDetailId=inv.getParameter("alarmDetailId");
        String alarmId = inv.getParameter("alarm_id");
        //alarmDetailId不为null，说明tracelogID为null
        if(null!=alarmDetailId){
            //alarmDetailId作为判断是有有tracelog用
            inv.addModel("alarmDetailId",alarmDetailId);
            inv.addModel("existLogId","notExist");
            //alarmId用以得到告警信息
            inv.addModel("alarmId",alarmDetailId);
        }else{
            inv.addModel("alarmDetailId",alarmId);
            inv.addModel("existLogId","Exist");
            inv.addModel("alarmId",urlTraceLogRepository.findOne(urlTraceLogId).getAlarmId());
        }
        inv.addModel("applicationId", applicationId);
        inv.addModel("urlId", urlId);
        inv.addModel("logId", urlTraceLogId);
        return "alarmInfo";
    }
}
