package com.sinosoft.one.monitor.db.oracle.domain;

import com.sinosoft.one.monitor.db.oracle.model.OracleTableSpaceModel;

import java.util.List;

/**
 * User: Chunliang.Han
 * Date: 13-2-27
 * Time: 下午10:30
 * 表空间信息处理类
 */
public interface OracleTableSpaceService {
    /**
     * 获取空间信息
     */
    List<OracleTableSpaceModel> listTableSpaceInfo(String monitorId);
}
