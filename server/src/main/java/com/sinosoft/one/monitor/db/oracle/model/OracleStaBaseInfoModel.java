package com.sinosoft.one.monitor.db.oracle.model;

/**
 * User: Chunliang.Han
 * Date: 13-2-28
 * Time: 下午7:38
 * 数据库监控基本统计信息包括：可用性、健康状况
 */
public class OracleStaBaseInfoModel {
    /**
     * 监视器名称
     */
     private String monitorName;
    /**
     * 监视器ID
     */
    private String monitorID;
    /**
     * 可用性
     */
    private String usability;
    /**
     * 健康状况
     */
    private String[] healthy;

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getMonitorID() {
        return monitorID;
    }

    public void setMonitorID(String monitorID) {
        this.monitorID = monitorID;
    }

    public String getUsability() {
        return usability;
    }

    public void setUsability(String usability) {
        this.usability = usability;
    }

    public String[] getHealthy() {
        return healthy;
    }

    public void setHealthy(String[] healthy) {
        this.healthy = healthy;
    }
}
