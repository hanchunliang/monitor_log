package com.sinosoft.one.monitor.application.domain;

import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.application.model.ExceptionInfo;
import com.sinosoft.one.monitor.common.*;
import com.sinosoft.one.monitor.utils.ApplicationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * 异常代理端消息处理服务类.
 * User: carvin
 * Date: 13-3-4
 * Time: 下午5:06
 */
@Service("exceptionAgentMessageService")
public class ExceptionAgentMessageService implements AgentMessageService{
	@Autowired
	private AlarmMessageBuilder alarmMessageBuilder;
    @Autowired
    private MessageProcessor messageProcessor;

    /**
     * 第三版的处理方法
     * 目前认为monitorserver扫描的URL为“//”开头，该方法对monitorserver扫描的URL进行了过滤
     * @param ip
     * @param port
     * @param applicationMapByName
     * @param exceptionInfoList
     */
    public void handleMessage(String ip,String port,Map<String, Application> applicationMapByName, List<ExceptionInfo> exceptionInfoList) {
        //需要入库的异常信息列表
        List<ExceptionInfo> exceptionInfos = new ArrayList<ExceptionInfo>(exceptionInfoList.size());
        for(ExceptionInfo exceptionInfo:exceptionInfoList){
            String url = exceptionInfo.getUrl();
            //基本过滤，过滤掉服务端主动扫描的url（已两个以上‘//’开头的）
            if(url.startsWith("//")){
                continue;
            }
            //过滤掉没有配置过的应用
            String applicationId  = ApplicationUtil.getAppId(ip,port,applicationMapByName, url);
            if(applicationId==null){
                continue;
            }
            //更换URL为没有‘/’的形式
            if(url.startsWith("/")){
                url = url.substring(1);
                exceptionInfo.setUrl(url);
            }
            //如果是background的异常则将其ID拼为IP:PORT的形式
            if("background".equals(applicationId)){
                applicationId = ip+":"+port;
                exceptionInfo.setApplicationId(applicationId);
            }
            exceptionInfo.setApplicationId(applicationId);
            //将最终的异常信息加入需要入库的异常信息列表
            exceptionInfos.add(exceptionInfo);
            //告警
            alarmMessageBuilder.newMessageBase(applicationId)
                    .alarmSource(AlarmSource.EXCEPTION)
                    .addAlarmAttribute(AttributeName.Exception, "0")
                    .subResourceId(exceptionInfo.getUrlId())
                    .subResourceType(ResourceType.APPLICATION_SCENARIO_URL)
                    .alarmId(exceptionInfo.getAlarmId())
                    .alarm();
        }
        messageProcessor.executeExceptionInfo(exceptionInfos);
    }
}
