package com.sinosoft.monitor.agent.store.model.url;

import com.sinosoft.monitor.agent.store.model.exception.ExceptionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * TraceLog
 * User: ChengQi
 * Date: 9/18/13
 * Time: 10:19 AM
 */
public class TraceLog {

    private String ip;

    private String port;

    private String notificationType;

    private List<UrlTraceLog> urlTraceLogs = new ArrayList<UrlTraceLog>(0);

    private List<ExceptionInfo> allExciptionInfos  = new ArrayList<ExceptionInfo>(0);


   public void setUrlTraceLogs(List<UrlTraceLog> urlTraceLogs){
        this.urlTraceLogs =    urlTraceLogs;
   }


    public List<UrlTraceLog> getUrlTraceLogs(){
        return this.urlTraceLogs;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public List<ExceptionInfo> getAllExciptionInfos() {
        return allExciptionInfos;
    }

    public void setAllExciptionInfos(List<ExceptionInfo> allExciptionInfos) {
        this.allExciptionInfos = allExciptionInfos;
    }
}
