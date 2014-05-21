package com.sinosoft.one.monitor.os.linux.model.viewmodel;

import java.util.List;

/**
 * 健康状况信息对象
 * User:chenxiongxi
 * Date: 13-2-28
 * Time: 下午8:20
 */
public class OsHealthModel {
    /**
     *  监视器名称
     */
    private String monitorName;
    /**
     * 监视器ID
     */
    private String monitorID;
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
