package com.sinosoft.one.monitor.application.domain;

import com.sinosoft.one.monitor.common.Trend;

/**
 * 应用可用性
 * User: ChengQi
 * Date: 13-3-8
 * Time: AM1:01
 */
public class ApplicationAvailableInf {

    private Trend trend;

    private int count;

    private int availableCount;

    public ApplicationAvailableInf(Trend trend, int count, int availableCount) {
        this.trend = trend;
        this.count = count;
        this.availableCount = availableCount;
    }

    public Trend getTrend() {
        return trend;
    }

    public int getCount() {
        return count;
    }

    public int getAvailableCount() {
        return availableCount;
    }
}
