package com.sinosoft.one.monitor.db.oracle.domain;

import com.sinosoft.one.monitor.db.oracle.model.OracleTableSpaceModel;
import com.sinosoft.one.monitor.db.oracle.monitorSql.OracleMonitorSql;
import com.sinosoft.one.monitor.db.oracle.utils.DBUtil4Monitor;
import com.sinosoft.one.monitor.db.oracle.utils.db.DBUtil;
import com.sinosoft.one.monitor.db.oracle.utils.db.SqlObj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Chunliang.Han
 * Date: 13-3-1
 * Time: 下午6:10
 */
@Component
public class OracleTableSpaceServiceImpl implements OracleTableSpaceService {
	@Autowired
    private DBUtil4Monitor dbUtil4Monitor;
    @Override
    public List<OracleTableSpaceModel> listTableSpaceInfo(String monitorId) {
        List<OracleTableSpaceModel> rsList = new ArrayList<OracleTableSpaceModel>();
        if(dbUtil4Monitor.canConnect(monitorId)){
            DBUtil dbutil = dbUtil4Monitor.getDBUtil(monitorId);
            String sql = OracleMonitorSql.tableSpaceInfo;
            rsList = dbutil.queryBeans(SqlObj.newInstance(sql), OracleTableSpaceModel.class);
        }

        return rsList;
    }
}
