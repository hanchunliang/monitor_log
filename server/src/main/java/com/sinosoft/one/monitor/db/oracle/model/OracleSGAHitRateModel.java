package com.sinosoft.one.monitor.db.oracle.model;

/**
 * User: Chunliang.Han
 * Date: 13-2-28
 * Time: 下午12:04
 */
public class OracleSGAHitRateModel {

    /**
     * 缓冲区命中率
     */
    private String bufferHitRate;
    /**
     * 数据字典命中率
     */
    private String dictHitRate;
    /**
     * 缓存库命中率
     */
    private String libHitRate;
    /**
     * 时间
     */
    private String recordTime;

    /**
     * 开始时间
     *
     */
    private String startTime;
    /**
     * 开始时间
     *
     */
    private String endTime;

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

    public String getBufferHitRate() {
        return bufferHitRate;
    }

    public void setBufferHitRate(String bufferHitRate) {
        this.bufferHitRate = bufferHitRate;
    }

    public String getDictHitRate() {
        return dictHitRate;
    }

    public void setDictHitRate(String dictHitRate) {
        this.dictHitRate = dictHitRate;
    }

    public String getLibHitRate() {
        return libHitRate;
    }

    public void setLibHitRate(String libHitRate) {
        this.libHitRate = libHitRate;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}
