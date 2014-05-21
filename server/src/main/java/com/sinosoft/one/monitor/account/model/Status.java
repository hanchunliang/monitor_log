package com.sinosoft.one.monitor.account.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Administrator
 */
public enum Status {
    NORMAL("0", "正常"), LOCK("1", "锁定");

    private static Map<String, Status> valueMap = Maps.newHashMap();

    public String statusValue;
    public String statusName;

    static {
        for (Status status : Status.values()) {
            valueMap.put(status.statusValue, status);
        }
    }

    Status(String statusValue, String statusName) {
        this.statusValue = statusValue;
        this.statusName = statusName;
    }

    public static Status parse(String statusValue) {
        return valueMap.get(statusValue);
    }

    public String getStatusValue() {
        return statusValue;
    }

    public String getStatusName() {
        return statusName;
    }
}
