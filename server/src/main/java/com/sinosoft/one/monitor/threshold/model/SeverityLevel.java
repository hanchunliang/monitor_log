package com.sinosoft.one.monitor.threshold.model;

/**
 * 严重级别枚举类.
 * User: carvin
 * Date: 13-3-1
 * Time: 下午5:33
 *
 */
public enum SeverityLevel {
	CRITICAL("严重"),
	WARNING("警告"),
	INFO("正常"),
	UNKNOW("未知");

	private String _cnName;
	private SeverityLevel(String cnName) {
		this._cnName = cnName;
	}
	public String cnName() {
		return _cnName;
	}
}
