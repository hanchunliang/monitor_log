package com.sinosoft.one.monitor.common;

import com.sinosoft.one.monitor.threshold.model.SeverityLevel;

/**
 * 健康度统计
 * User: carvin
 * Date: 13-3-5
 * Time: 下午11:24
 */
public class HealthStaForMonitor {
	private SeverityLevel severity;
	private int count;

	public SeverityLevel getSeverity() {
		return severity;
	}

	public void setSeverity(SeverityLevel severity) {
		this.severity = severity;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
