package com.sinosoft.one.monitor.db.oracle.model;

import java.util.List;

/**
 * User: Chunliang.Han
 * Date: 13-3-1
 * Time: 下午4:25
 */
public class EventInfoModel {
    private String title;

    /**
     * 属性ID
     */
    private String eventType;

    /**
     * 属性名称
     */
     private String eventName;

    /**
     * 属性值
     */
    private String eventValue;
    /**
     * 起始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 数据点列
     */
    private List<Point> points;

    /**
     * SGA命中率统计点列
     */
    private List<OracleSGAHitRateModel> sgaHitRateModels;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<OracleSGAHitRateModel> getSgaHitRateModels() {
        return sgaHitRateModels;
    }

    public void setSgaHitRateModels(List<OracleSGAHitRateModel> sgaHitRateModels) {
        this.sgaHitRateModels = sgaHitRateModels;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventValue() {
        return eventValue;
    }

    public void setEventValue(String eventValue) {
        this.eventValue = eventValue;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

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
}
