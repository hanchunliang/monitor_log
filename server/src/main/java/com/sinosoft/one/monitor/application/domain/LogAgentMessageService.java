package com.sinosoft.one.monitor.application.domain;

import com.sinosoft.one.data.jade.parsers.util.StringUtil;
import com.sinosoft.one.monitor.application.model.*;
import com.sinosoft.one.monitor.application.repository.MethodResponseTimeRepository;
import com.sinosoft.one.monitor.common.*;
import com.sinosoft.one.monitor.utils.DateUtil;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 日志代理端消息处理服务类.
 * User: carvin
 * Date: 13-3-4
 * Time: 下午5:06
 * To change this template use File | Settings | File Templates.
 */
@Service("logAgentMessageService")
public class LogAgentMessageService implements AgentMessageService {
	@Autowired
	private AlarmMessageBuilder alarmMessageBuilder;
    @Autowired
    private MessageProcessor messageProcessor;

    //第二版
    public void handleMessage(String applicationId, List<UrlTraceLog> urlTraceLogList,List<UrlTraceLog> urlTraceLogCache,List<UrlVisitsSta> urlVisitsStaCathe) {
        if(urlTraceLogList==null||urlTraceLogList.size()==0){
             return;
        }
        for (UrlTraceLog urlTraceLog:urlTraceLogList){
            execute(applicationId,urlTraceLog,urlTraceLogCache,urlVisitsStaCathe);
        }
    }
    //第三版
    @Transactional
    public void handleMessage(Map<String, List<UrlTraceLog>> newUrlTraceLogTable) {
        List<UrlTraceLog> urlTraceLogCache = new ArrayList<UrlTraceLog>();
        List<UrlVisitsSta> urlVisitsStaCathe = new ArrayList<UrlVisitsSta>();
        Set<String> keySet = newUrlTraceLogTable.keySet();
        if(keySet==null||keySet.size()==0){
             return;
        }
        for(String monitorId:keySet){
            handleMessage(monitorId,newUrlTraceLogTable.get(monitorId),urlTraceLogCache,urlVisitsStaCathe);
        }
        messageProcessor.executeUrlTraceLogInfo(urlTraceLogCache);
    }
    private void execute(String applicationId,UrlTraceLog urlTraceLog,List<UrlTraceLog> urlTraceLogCache,List<UrlVisitsSta> urlVisitsStaCathe){
        urlTraceLog.setApplicationId(applicationId);
        //添加alarmId
        if(StringUtil.isEmpty(urlTraceLog.getAlarmId())){
            urlTraceLog.setAlarmId(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        if(!urlTraceLog.getHasException()) {
            alarmMessageBuilder.newMessageBase(applicationId)
                    .alarmSource(AlarmSource.LOG)
                    .subResourceType(ResourceType.APPLICATION_SCENARIO_URL)
                    .subResourceId(urlTraceLog.getUrlId())
                    .addAlarmAttribute(AttributeName.ResponseTime, urlTraceLog.getConsumeTime() + "")
                    .alarmId(urlTraceLog.getAlarmId())
                    .alarm();
        }
        urlTraceLog.setRecordTime(new Date());
        urlTraceLogCache.add(urlTraceLog);
    }
}
