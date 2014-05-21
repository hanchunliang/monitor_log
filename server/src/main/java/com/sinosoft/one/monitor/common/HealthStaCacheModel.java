package com.sinosoft.one.monitor.common;

import com.sinosoft.one.monitor.threshold.model.SeverityLevel;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 健康度缓存对象.
 * User: carvin
 * Date: 13-3-7
 * Time: 下午10:43
 */
public class HealthStaCacheModel {
	private int criticalCount;
	private int warningCount;
	private int normalCount;

	public HealthStaCacheModel() {

	}

	public HealthStaCacheModel(SeverityLevel severityLevel) {
		increase(severityLevel);
	}

	public HealthStaCacheModel(SeverityLevel severityLevel, int count) {
		increase(severityLevel, count);
	}

	public void increase(HealthStaForMonitor healthStaForMonitor) {
		increase(healthStaForMonitor.getSeverity(), healthStaForMonitor.getCount());
	}

	public void increase(SeverityLevel severityLevel) {
		increase(severityLevel, 1);
	}
	public void increase(SeverityLevel severityLevel, int count) {
		if(severityLevel == SeverityLevel.CRITICAL) {
			criticalCount += count;
		} else if(severityLevel == SeverityLevel.WARNING) {
			warningCount += count;
		} else if(severityLevel == SeverityLevel.INFO) {
			normalCount += count;
		}
	}

	public String getHealthBar() {
		int total = criticalCount + warningCount + normalCount;
		normalCount = total == 0 ? 1 : normalCount;
		total = total == 0 ? 1 : total;

		BigDecimal totalBigDecimal = BigDecimal.valueOf(total);
		BigDecimal hundredBigDecimal = BigDecimal.valueOf(100);
		int criticalPercent = BigDecimal.valueOf(criticalCount).divide(totalBigDecimal, 2, RoundingMode.HALF_UP).multiply(hundredBigDecimal).intValue();
		int warningPercent = BigDecimal.valueOf(warningCount).divide(totalBigDecimal, 2, RoundingMode.HALF_UP).multiply(hundredBigDecimal).intValue();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<div class='green_bar'><div class='red_bar' style='width:").append(criticalPercent)
				.append("%'></div><div class='yellow_bar' style='width:").append(warningPercent).append("%'></div>");
		return stringBuilder.toString();
	}
}
