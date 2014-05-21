package com.sinosoft.one.monitor.db.oracle.model;

import java.util.List;

/**
 * User: Chunliang.Han
 * Date: 13-2-28
 * Time: 下午3:25
 * 统计基本信息模板
 */
/*
今天的每小时连接时间
监视器名称 	oracle   一览报表
属性 	连接时间 ms
从 	2013-2-28 上午9:00
到 	2013-2-28 下午3:22
 */
public class OracleStaInfoDetailModel {
    /**
     *  标题
     */
    private String title;
    /**
     * 监视器名称
     */
    private String monitorName;
    /**
     * 属性
     */
    private String eventName;
    /**
     * 开始时间
     */
    private String begin;
    /**
     * 结束时间
     */
    private String end;
    /**
     * 横轴名称
     */
    private String xName;
    /**
     * 纵轴名称
     */
    private String yName;
    /**
     * 最小平均值
     */
    private double minAvg;
    /**
     * 最大平均值
     */
    private double maxAvg;
    /**
     * 平均值
     */
    private double avg;
    /**
     * 阈值（严重）
     */
    private String error;
    /**
     * 阈值（警告）
     */
    private String warn;
    /**
     * 阈值（正常）
     */
    private String normal;
    /**
     * 记录点（<时间，值>）
     */
    private List<Point> points;
    /**
     * 查询统计结果集合
     */
    private List<EventSta> recordItems;

    public String getxName() {
        return xName;
    }

    public void setxName(String xName) {
        this.xName = xName;
    }

    public String getyName() {
        return yName;
    }

    public void setyName(String yName) {
        this.yName = yName;
    }

    public double getMinAvg() {
        return minAvg;
    }

    public void setMinAvg(double minAvg) {
        this.minAvg = minAvg;
    }

    public double getMaxAvg() {
        return maxAvg;
    }

    public void setMaxAvg(double maxAvg) {
        this.maxAvg = maxAvg;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<EventSta> getRecordItems() {
        return recordItems;
    }

    public void setRecordItems(List<EventSta> recordItems) {
        this.recordItems = recordItems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String attribute) {
        this.eventName = attribute;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
