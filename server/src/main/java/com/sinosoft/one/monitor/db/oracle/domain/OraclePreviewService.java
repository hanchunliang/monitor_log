package com.sinosoft.one.monitor.db.oracle.domain;

import com.sinosoft.one.monitor.db.oracle.model.EventInfoModel;
import com.sinosoft.one.monitor.db.oracle.model.OracleDetailModel;

/**
 * User: Chunliang.Han
 * Date: 13-2-27
 * Time: 下午9:51
 * 数据库监控信息概览处理（连接时间概览，用户数概览，数据库明细）
 */
public interface OraclePreviewService {
    /**
     * 连接时间概览(横坐标，纵坐标，详细值)
     */
    EventInfoModel[] viewConnectInfo(String monitorId);
    /**
     *用户数概览(横坐标，纵坐标，详细值)
     */
    //EventInfoModel viewActiveInfo(String monitorId);

    /**
     * 数据库明细
     */
    OracleDetailModel viewDbDetail(String monitorId);
}
