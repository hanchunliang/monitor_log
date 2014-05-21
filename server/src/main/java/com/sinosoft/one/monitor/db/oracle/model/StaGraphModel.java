package com.sinosoft.one.monitor.db.oracle.model;

import java.util.List;

/**
 * User: Chunliang.Han
 * Date: 13-3-2
 * Time: 下午9:54
 * 一个数据库的统计数据
 */
public class StaGraphModel {

    private String id;
    private String name;
    List<Lastevent> lasteventList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lastevent> getLasteventList() {
        return lasteventList;
    }

    public void setLasteventList(List<Lastevent> lasteventList) {
        this.lasteventList = lasteventList;
    }
}
