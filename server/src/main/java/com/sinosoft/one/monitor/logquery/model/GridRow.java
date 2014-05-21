package com.sinosoft.one.monitor.logquery.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: king-bj
 * Date: 13-10-31
 * Time: 下午5:15
 * To change this template use File | Settings | File Templates.
 */
public class GridRow {

    public int total;
    public List<Object> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Object> getRows() {
        return rows;
    }

    public void setRows(List<Object> rows) {
        this.rows = rows;
    }
}
