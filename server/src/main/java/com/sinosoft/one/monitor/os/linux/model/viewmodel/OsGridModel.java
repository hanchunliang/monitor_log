package com.sinosoft.one.monitor.os.linux.model.viewmodel;


/**
 * 表格建立对象
 * @author Administrator
 *
 */
public class OsGridModel {

	
	private String id;
	
	private String name;
	
	private String utilzation;
	
	private String link;
	
	private String used;
	
	private String value;
	
	private String stuts;
	
	private String time;
	
	private String minValue;
	
	private String maxValue;
	
	private String averageValue;
	
	private String normalRun;
	
	private String crashTime;
	
	private String aveRepairTime;
	
	private String aveFaultTime;

	
	
	
	public String getName() {
		return name;
	}

	public String getNormalRun() {
		return normalRun;
	}

	public void setNormalRun(String normalRun) {
		this.normalRun = normalRun;
	}

	public String getCrashTime() {
		return crashTime;
	}

	public void setCrashTime(String crashTime) {
		this.crashTime = crashTime;
	}

	public String getAveRepairTime() {
		return aveRepairTime;
	}

	public void setAveRepairTime(String aveRepairTime) {
		this.aveRepairTime = aveRepairTime;
	}

	public String getAveFaultTime() {
		return aveFaultTime;
	}

	public void setAveFaultTime(String aveFaultTime) {
		this.aveFaultTime = aveFaultTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUtilzation() {
		return utilzation;
	}

	public void setUtilzation(String utilzation) {
		this.utilzation = utilzation;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStuts() {
		return stuts;
	}

	public void setStuts(String stuts) {
		this.stuts = stuts;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getAverageValue() {
		return averageValue;
	}

	public void setAverageValue(String averageValue) {
		this.averageValue = averageValue;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	
}
