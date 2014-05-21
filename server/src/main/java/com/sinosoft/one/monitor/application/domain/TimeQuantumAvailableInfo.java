package com.sinosoft.one.monitor.application.domain;

/**
 * TimeQuantumAvailableInfo
 * User: ChengQi
 * Date: 13-3-8
 * Time: PM5:54
 */
public class TimeQuantumAvailableInfo {


    private int count;

    private int avaCount;

    private String timeQuantum;


    public int getCount() {
        return count;
    }

    public int getAvaCount() {
        return avaCount;
    }

    public String getTimeQuantum() {
        return timeQuantum;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setAvaCount(int avaCount) {
        this.avaCount = avaCount;
    }

    public void setTimeQuantum(String timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

	public int getFailCount() {
		return count - avaCount;
	}
}
