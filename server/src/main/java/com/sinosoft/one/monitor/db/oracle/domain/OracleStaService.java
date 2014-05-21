package com.sinosoft.one.monitor.db.oracle.domain;

import com.sinosoft.one.monitor.db.oracle.model.OracleStaInfoDetailModel;

import java.util.Date;

/**
 * User: Chunliang.Han
 * Date: 13-2-28
 * Time: 下午2:54
 * 统计信息处理类（缓冲区命中率，用户数，连接时间）
 */
public interface OracleStaService {
    /**
     * 统计信息获取
     */
    OracleStaInfoDetailModel getBaseInfo(String monitorId, int eventType, String title, Date now, StaTimeEnum staTimeEnum, TimeGranularityEnum timeGranularityEnum);
}