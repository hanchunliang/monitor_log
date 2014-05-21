package com.sinosoft.one.monitor.common;

/**
 * 可用性状态枚举
 * User: carvin
 * Date: 13-3-24
 * Time: 下午5:19
 */
public enum AvailabilityStatus {
	ERROR,
	NORMAL;

	public String value() {
		return String.valueOf(this.ordinal());
	}
}
