package com.sinosoft.one.monitor.db.oracle.model;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.one.monitor.threshold.model.SeverityLevel;

/**
 * User: Chunliang.Han
 * Date: 13-2-28
 * Time: 下午8:20
 */
public class OracleHealthInfoModel {
    /**
     *  监视器名称
     */
    private String monitorName;
    /**
     * 监视器ID
     */
    private String monitorID;
    
    /**
     * 总统计时间数
     */
    private int totalTime;
    
    
    /**
     * 健康状态
     */
    private List<SeverityLevel> healths = new ArrayList<SeverityLevel>();
    
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

	public List<SeverityLevel> getHealths() {
		return healths;
	}

	public void addHealth(SeverityLevel health) {
		this.healths.add(health);
	}
}
