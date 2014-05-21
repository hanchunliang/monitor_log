package com.sinosoft.one.monitor.db.oracle.model;

import java.util.Date;

/**
 * User: Chunliang.Han
 * Date: 13-3-1
 * Time: 下午7:34
 */
public class Point {
    /**
     * x轴
     */
    private Date xAxis;
    /**
     * y轴
     */
    private double yAxis;
    /**
     * 描述
     */
    private String description;

    public Date getxAxis() {
        return xAxis;
    }

    public void setxAxis(Date xAxis) {
        this.xAxis = xAxis;
    }

    public double getyAxis() {
        return yAxis;
    }

    public void setyAxis(double yAxis) {
        this.yAxis = yAxis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
