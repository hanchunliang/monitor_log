package com.sinosoft.one.monitor.application.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zfb
 * Date: 13-3-2
 * Time: 下午6:02
 * To change this template use File | Settings | File Templates.
 */
public enum BizScenarioGrade {
    HIGH("HIGH","高"),INTERMEDIATE("INTERMEDIATE","中"),LOW("LOW","低");

    private static Map<String, BizScenarioGrade> displayNameMap = Maps.newHashMap();
    private static Map<String, BizScenarioGrade> valueMap = Maps.newHashMap();
    public String value;
    public String displayName;
    static {
        for (BizScenarioGrade bizScenarioGrade : BizScenarioGrade.values()) {
            displayNameMap.put(bizScenarioGrade.displayName, bizScenarioGrade);
            valueMap.put(bizScenarioGrade.value,bizScenarioGrade);

        }
    }

    private BizScenarioGrade(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }
 
    public static BizScenarioGrade parseDisplayName(String displayName) {
        return displayNameMap.get(displayName);
    }

    public static BizScenarioGrade parseValue(String value) {
        return valueMap.get(value);
    }

    public String getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }
}
