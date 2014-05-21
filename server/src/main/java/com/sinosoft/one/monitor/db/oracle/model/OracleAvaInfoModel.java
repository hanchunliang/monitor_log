package com.sinosoft.one.monitor.db.oracle.model;

import java.util.List;

/**
 * User: Chunliang.Han
 * Date: 13-2-28
 * Time: 下午5:29
 * 可用性基本信息
 */
public class OracleAvaInfoModel {
    /**
     *  监视器名称
     */
    private String monitorName;
    /**
     * 监视器ID
     */
    private String monitorID;
    /**
     * 可用性百分比
     */
    private String avaRate;
    /**
     * 统计总时间数(24小时)
     */
    private static final int ONE_DAY = 24;
    /**
     * 统计总时间数(30天)
     */
    private static final int THIRTY_DAY = 30;
    /**
     * 总统计时间数
     */
    private int totalTime;
    /**
     * 图信息
     */
   private List<String[]> graphInfo;

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

    public String getAvaRate() {
        return avaRate;
    }

    public void setAvaRate(String avaRate) {
        this.avaRate = avaRate;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public List<String[]> getGraphInfo() {
        return graphInfo;
    }

    public void setGraphInfo(List<String[]> graphInfo) {
        this.graphInfo = graphInfo;
    }
}
