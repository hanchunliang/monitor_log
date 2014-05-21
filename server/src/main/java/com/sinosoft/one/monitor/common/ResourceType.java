package com.sinosoft.one.monitor.common;

/**
 * 资源类型枚举类.
 * User: carvin
 * Date: 13-3-1
 * Time: 下午2:43
 */
public enum ResourceType {
	APPLICATION("应用系统"),
	APPLICATION_SCENARIO("业务场景"),
	APPLICATION_SCENARIO_URL("业务场景URL"),
	APPLICATION_EUM_URL("业务仿真URL"),
	OS("操作系统"),
	DB("数据库"),
    ALL("所有"),
	NONE("");

	private String _cnName;

	private ResourceType(String cnName) {
		this._cnName = cnName;
	}

	public String cnName() {
		return _cnName;
	}

}
