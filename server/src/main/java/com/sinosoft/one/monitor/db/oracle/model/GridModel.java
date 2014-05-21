package com.sinosoft.one.monitor.db.oracle.model;

/**
 * 
 * @ClassName: GridModel 
 * @author yangzongbin
 * @date 2013-3-5 下午04:51:48
 * 这个类用来做grid数据
 */
public class GridModel {

	private String id;
	private String attribute;
	private String value;
	private String threshold;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
}
