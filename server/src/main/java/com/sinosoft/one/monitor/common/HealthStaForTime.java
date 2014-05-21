package com.sinosoft.one.monitor.common;

import com.sinosoft.one.monitor.threshold.model.SeverityLevel;

/**
 * Created with IntelliJ IDEA.
 * User: carvin
 * Date: 13-3-8
 * Time: 上午11:46
 */
public class HealthStaForTime {
	private int timeIndex ;
	private SeverityLevel severity;
	private int count;
	private int criticalCount;
	private int warningCount;
	private int normalCount;

	public int getTimeIndex() {
		return timeIndex;
	}

	public void setTimeIndex(int timeIndex) {
		this.timeIndex = timeIndex;
	}

	public int getCriticalCount() {
		return criticalCount;
	}

	public void setCriticalCount(int criticalCount) {
		this.criticalCount = criticalCount;
	}

	public int getWarningCount() {
		return warningCount;
	}

	public void setWarningCount(int warningCount) {
		this.warningCount = warningCount;
	}

	public int getNormalCount() {
		return normalCount;
	}

	public void setNormalCount(int normalCount) {
		this.normalCount = normalCount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		if(severity == SeverityLevel.CRITICAL) {
			criticalCount = count;
		} else if(severity == SeverityLevel.WARNING) {
			warningCount = count;
		} else if(severity == SeverityLevel.INFO) {
			normalCount = count;
		}
		this.count = count;
	}

	public SeverityLevel getSeverity() {
		return severity;
	}

	public void setSeverity(SeverityLevel severity) {
		this.severity = severity;
	}

	public SeverityLevel getSeverityLevel() {
		if(criticalCount > warningCount && criticalCount > normalCount) {
			return SeverityLevel.CRITICAL;
		}
		if(warningCount > criticalCount && warningCount > normalCount) {
			return SeverityLevel.WARNING;
		}
		if(normalCount > criticalCount && normalCount > warningCount) {
			return SeverityLevel.INFO;
		}
		if(criticalCount == warningCount && criticalCount == normalCount) {
			if(criticalCount == 0) {
				return SeverityLevel.UNKNOW;
			}
			return SeverityLevel.CRITICAL;
		}
		return SeverityLevel.UNKNOW;
	}
}
