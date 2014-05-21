package com.sinosoft.one.monitor.os.linux.model.viewmodel;

import java.util.List;

/**
 * 可用性图形传输对象
 * @author Administrator
 *
 */
public class OsAvailableLineModel {
	
private String osid;
	
	private List<String> view;

	public String getOsid() {
		return osid;
	}

	public void setOsid(String osid) {
		this.osid = osid;
	}

	public List<String> getView() {
		return view;
	}

	public void setView(List<String> view) {
		this.view = view;
	}
	/**
	 * 颜色序号
	 */
	private int index;
	
	/**
	 * 状态值
	 */
	private String status;
	
	/**
	 * 百分比
	 */
	private String percentage;

	/**
	 * 时间段
	 */
	private String timeSpan;
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(String timeSpan) {
		this.timeSpan = timeSpan;
	}
	
	
}
