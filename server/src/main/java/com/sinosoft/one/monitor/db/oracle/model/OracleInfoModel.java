package com.sinosoft.one.monitor.db.oracle.model;

/**
 * User: Chunliang.Han
 * Date: 13-2-27
 * Time: 下午7:48
 * Oracle监视器信息视图
 */
/*
名称 	kongyz
健康状况 	  健康状态为正常 . 没有出现严重的告警。
类型 	Oracle服务器
Oracle版本 	10.2.0.1.0
Oracle启动时间 	2013-02-27 10:48:08.0
端口 	1521
主机名 	192.168.18.151 (192.168.18.151)
操作系统 	未知的
最后轮询时间 	2013-2-27 下午9:13
下次轮询时间 	2013-2-27 下午9:18
 */
public class OracleInfoModel {
    /**
     * Oracle监视器名称
     */
    private String monitorName;
    /**
     * Oracle数据库健康状况
     */
    private String[] health;
    /**
     * 监控类型
     */
    public String  monitorType  = "Oracle服务器";

    public String getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(String monitorType) {
        this.monitorType = monitorType;
    }

    /**
     * Oracle版本
     */
    private String version;
    /**
     * Oracle启动时间
     */
    private  String startTime;
    /**
     * 端口
     */
    private  String port;
    /**
     * 主机名
     */
    private  String hostName;
    /**
     * 操作系统
     */
    private  String os;
    /**
     * 最后轮询时间
     */
    private String lastExecTime;
    /**
     * 下次轮询时间
     */
    private String nextExecTime;

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String[] getHealth() {
        return health;
    }

    public void setHealth(String[] health) {
        this.health = health;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getLastExecTime() {
        return lastExecTime;
    }

    public void setLastExecTime(String lastExecTime) {
        this.lastExecTime = lastExecTime;
    }

    public String getNextExecTime() {
        return nextExecTime;
    }

    public void setNextExecTime(String nextExecTime) {
        this.nextExecTime = nextExecTime;
    }
}
