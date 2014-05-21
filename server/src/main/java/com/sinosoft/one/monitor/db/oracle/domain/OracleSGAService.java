package com.sinosoft.one.monitor.db.oracle.domain;

import com.sinosoft.one.monitor.db.oracle.model.EventInfoModel;
import com.sinosoft.one.monitor.db.oracle.model.OracleSGAModel;
import com.sinosoft.one.monitor.db.oracle.model.SGAStateModel;

/**
 * User: Chunliang.Han
 * Date: 13-2-28
 * Time: 上午9:54
 * SGA信息处理类
 */
public interface OracleSGAService {
    /**
     * SGA详细信息获取
     */
    OracleSGAModel viewSGAInfo(String monitorId);

    /**
     * SGA状态数据获取  (缓冲区击中率,数据字典击中率,缓存库击中率,可用内存)
     */
    SGAStateModel viewSGAStateInfo(String monitorId);

    /**
     * 获取命中率一小时统计信息
     */
    EventInfoModel viewHitRateStaInfo(String monitorId);
}
