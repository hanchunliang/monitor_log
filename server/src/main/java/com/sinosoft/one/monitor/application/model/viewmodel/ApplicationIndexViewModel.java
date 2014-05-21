package com.sinosoft.one.monitor.application.model.viewmodel;

/**
 * 应用首页模型.
 * User: carvin
 * Date: 13-3-5
 * Time: 下午10:00
 */
public class ApplicationIndexViewModel {
	private String applicationId;
	private String applicationName;
	private String applicationCnName;
	private int greenBarLength;
	private int yellowBarLength;
	private int redBarLength;
	private int avgResponseTime;
	private double rpm;

	private MaxBar maxBar = MaxBar.NONE;
	private int max;

	private enum MaxBar {
		GREEN,
		YELLOW,
		RED,
		NONE
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationCnName() {
		return applicationCnName;
	}

	public void setApplicationCnName(String applicationCnName) {
		this.applicationCnName = applicationCnName;
	}

	public int getGreenBarLength() {
		return greenBarLength;
	}

	public void setGreenBarLength(int greenBarLength) {
		this.greenBarLength = greenBarLength;
		setMaxBar(greenBarLength, MaxBar.GREEN);
	}

	public int getYellowBarLength() {
		return yellowBarLength;
	}

	public void setYellowBarLength(int yellowBarLength) {
		this.yellowBarLength = yellowBarLength;
		setMaxBar(yellowBarLength, MaxBar.YELLOW);

	}

	public int getRedBarLength() {
		return redBarLength;
	}

	public void setRedBarLength(int redBarLength) {
		this.redBarLength = redBarLength;
		setMaxBar(redBarLength, MaxBar.RED);
	}

	public int getAvgResponseTime() {
		return avgResponseTime;
	}

	public void setAvgResponseTime(int avgResponseTime) {
		this.avgResponseTime = avgResponseTime;
	}

	public double getRpm() {
		return rpm;
	}

	public void setRpm(double rpm) {
		this.rpm = rpm;
	}

	private void setMaxBar(int barLength, MaxBar maxBar) {
		if(max < barLength) {
			max = barLength;
			this.maxBar = maxBar;
		}
	}
	public void reconculate() {
		int totalLength = redBarLength + greenBarLength + yellowBarLength;
		if( totalLength > 100) {
			int minus = totalLength - 100;
			switch(this.maxBar) {
				case GREEN: greenBarLength = greenBarLength - minus; break;
				case YELLOW: yellowBarLength = yellowBarLength - minus; break;
				case RED: redBarLength = redBarLength - minus; break;
			}

		}
	}
}
