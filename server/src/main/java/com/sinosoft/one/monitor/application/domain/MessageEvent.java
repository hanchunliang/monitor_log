package com.sinosoft.one.monitor.application.domain;

import java.util.ArrayList;
import com.sinosoft.monitor.agent.store.model.url.TraceLog;
import com.sinosoft.one.monitor.application.model.ExceptionInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class MessageEvent {

    public  static final String QUEUE_NAME="APP_AGENT_MSG";
    private String ip;
    private String port;
    private String notificationType;

    private TraceLog traceLog;

    private List<ExceptionInfo> exceptionInfos  =new ArrayList<ExceptionInfo>();
    private String data;

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


    public TraceLog getTraceLog() {
        return traceLog;
    }

    public void setTraceLog(TraceLog traceLog) {
        this.traceLog = traceLog;
    }

    public List<ExceptionInfo> getExceptionInfos() {
        return exceptionInfos;
    }

    public void setExceptionInfos(List<ExceptionInfo> exceptionInfos) {
        this.exceptionInfos = exceptionInfos;
    }

    public String getData() {
        return data;
    }
}
