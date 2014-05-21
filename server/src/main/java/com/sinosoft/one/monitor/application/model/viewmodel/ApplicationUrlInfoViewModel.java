package com.sinosoft.one.monitor.application.model.viewmodel;

/**
 * 应用系统URL展示信息类.
 * User: carvin
 * Date: 13-3-9
 * Time: 上午11:41
 */
public class ApplicationUrlInfoViewModel {
	private String health;
	private String availability;
	private String todayAvailability;
	private String todayRunningTime;
	private String latestFailTime;
	private String url;
	
	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getTodayAvailability() {
		return todayAvailability;
	}

	public void setTodayAvailability(String todayAvailability) {
		this.todayAvailability = todayAvailability;
	}

	public String getTodayRunningTime() {
		return todayRunningTime;
	}

	public void setTodayRunningTime(String todayRunningTime) {
		this.todayRunningTime = todayRunningTime;
	}

	public String getLatestFailTime() {
		return latestFailTime;
	}

	public void setLatestFailTime(String latestFailTime) {
		this.latestFailTime = latestFailTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
