package com.sinosoft.one.monitor.application.domain;


/**
 * 时间端可用性状态统计
 * User: cq
 * Date: 13-3-8
 * Time: PM4:33
 */
public class TimeQuantumAvailableStatistics {

    private int count;

    private String status;

    private String timeQuantum;


    public int getCount(){
        return this.count;
    }


    public void setCount(int count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeQuantum() {
        return timeQuantum;
    }

    public void setTimeQuantum(String timeQuantum) {
        this.timeQuantum = timeQuantum;
    }
}
