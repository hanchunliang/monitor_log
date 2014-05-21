package com.sinosoft.one.monitor.db.oracle.model;

/**
 * User: Chunliang.Han
 * Date: 13-2-27
 * Time: 下午10:23
 * Oracle数据库明细信息
 */

/*
数据库创建时间 	2012-10-17 10:41:58.0
Open模式 	READ WRITE
Log模式 	NOARCHIVELOG
 */
public class OracleDetailModel {
	/**
	 * 连接时间
	 */
	private String connectTime;
	/**
	 * 用户数
	 */
	private String activeCount;
    /**
     *  数据库创建时间
     */
     private String dbCreateTime ;
    /**
     * Open模式
     */
    private String openType;
    /**
     * Log模式
     */
    private String logType;
public String getActiveCount() {
	return activeCount;
}
public String getConnectTime() {
	return connectTime;
}
public void setActiveCount(String activeCount) {
	this.activeCount = activeCount;
}
public void setConnectTime(String connectTime) {
	this.connectTime = connectTime;
}
    public String getDbCreateTime() {
        return dbCreateTime;
    }

    public void setDbCreateTime(String dbCreateTime) {
        this.dbCreateTime = dbCreateTime;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }
}
