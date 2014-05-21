package com.sinosoft.one.monitor.logquery.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: king-bj
 * Date: 13-10-31
 * Time: 下午5:29
 * To change this template use File | Settings | File Templates.
 */
public class RowEntity {
    public String id;
    public List<String> cell;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getCell() {
        return cell;
    }

    public void setCell(List<String> cell) {
        this.cell = cell;
    }
}
