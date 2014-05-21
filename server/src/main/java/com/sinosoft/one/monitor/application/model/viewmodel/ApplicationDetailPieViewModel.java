package com.sinosoft.one.monitor.application.model.viewmodel;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 应用明细页饼图信息展示实体
 * User: carvin
 * Date: 13-3-6
 * Time: 下午9:51
 */
public class ApplicationDetailPieViewModel {
	private int availabilityCount = 0;
	private int unavailabilityCount = 0;

	private int criticalCount = 0;
	private int warningCount = 0;
	private int normalCount = 0;

	public String getAvailabilityValue() {
		int total = availabilityCount + unavailabilityCount;
		availabilityCount = total == 0 ? 1 : availabilityCount;
		total = total == 0 ? 1 : total;

		BigDecimal totalBigDecimal = BigDecimal.valueOf(total);
		BigDecimal hundredBigDecimal = BigDecimal.valueOf(100);

		return BigDecimal.valueOf(availabilityCount).divide(totalBigDecimal, 2, RoundingMode.HALF_UP).multiply(hundredBigDecimal).intValue() + ":" +
				BigDecimal.valueOf(unavailabilityCount).divide(totalBigDecimal, 2, RoundingMode.HALF_UP).multiply(hundredBigDecimal).intValue();
	}

	public String getHealthValue() {
		int total = criticalCount + warningCount + normalCount;
		normalCount = total == 0 ? 1 : normalCount;
		total = total == 0 ? 1 : total;

		BigDecimal totalBigDecimal = BigDecimal.valueOf(total);
		BigDecimal hundredBigDecimal = BigDecimal.valueOf(100);
		return BigDecimal.valueOf(criticalCount).divide(totalBigDecimal, 2, RoundingMode.HALF_UP).multiply(hundredBigDecimal).intValue() + ":" +
				BigDecimal.valueOf(warningCount).divide(totalBigDecimal, 2, RoundingMode.HALF_UP).multiply(hundredBigDecimal).intValue() + ":" +
				BigDecimal.valueOf(normalCount).divide(totalBigDecimal, 2, RoundingMode.HALF_UP).multiply(hundredBigDecimal).intValue();
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

	public int getAvailabilityCount() {
		return availabilityCount;
	}

	public void setAvailabilityCount(int availabilityCount) {
		this.availabilityCount = availabilityCount;
	}

	public int getUnavailabilityCount() {
		return unavailabilityCount;
	}

	public void setUnavailabilityCount(int unavailabilityCount) {
		this.unavailabilityCount = unavailabilityCount;
	}
}
