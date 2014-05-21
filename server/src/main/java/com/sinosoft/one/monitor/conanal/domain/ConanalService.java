package com.sinosoft.one.monitor.conanal.domain;

import com.sinosoft.one.monitor.conanal.model.TableModel;
import com.sinosoft.one.monitor.logquery.model.UrlTraceLogEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: king-bj
 * Date: 13-11-2
 * Time: 下午10:35
 * To change this template use File | Settings | File Templates.
 */
public interface ConanalService {

    public List<TableModel> getCount();

}
