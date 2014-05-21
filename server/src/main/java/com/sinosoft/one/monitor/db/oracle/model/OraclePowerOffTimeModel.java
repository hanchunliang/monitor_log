package com.sinosoft.one.monitor.db.oracle.model;

/**
 * User: Chunliang.Han
 * Date: 13-2-27
 * Time: 下午9:08
 * oracle数据库停机时间明细
 */
public class OraclePowerOffTimeModel {
    /**
     * 开始时间
     */
    String startTime;
    /**
     * 结束时间
     */
    String endTime;
    /**
     * 持续时间
     */
    String during;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuring() {
        return during;
    }

    public void setDuring(String during) {
        this.during = during;
    }
}
