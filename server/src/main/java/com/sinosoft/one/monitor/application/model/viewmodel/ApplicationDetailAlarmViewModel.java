package com.sinosoft.one.monitor.application.model.viewmodel;

import com.sinosoft.one.monitor.threshold.model.SeverityLevel;

/**
 * 应用明细页告警信息展示实体
 * User: carvin
 * Date: 13-3-6
 * Time: 下午8:29
 */
public class ApplicationDetailAlarmViewModel {
	private String availability = "idown";
	private SeverityLevel severityLevel;
	private int criticalCount = 0;
	private StringBuilder alarmInfoes = new StringBuilder();


	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getSeverityLevel() {
		if(severityLevel == SeverityLevel.INFO) {
			return "ican";
		} else {
			return "icannot";
		}
	}

	public void setSeverityLevel(SeverityLevel severityLevel) {
		this.severityLevel = severityLevel;
	}

	public int getCriticalCount() {
		return criticalCount;
	}

	public void setCriticalCount(int criticalCount) {
		this.criticalCount = criticalCount;
	}

	public void addAlarmInfo(String alarmInfo) {
		alarmInfoes.append("<br/>------").append(alarmInfo).append("<br/>");
	}

	public String getAlarmInfoes() {
		return alarmInfoes.toString();
	}
}
