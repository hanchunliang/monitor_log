package com.sinosoft.one.monitor.db.oracle.domain;

import com.sinosoft.one.monitor.db.oracle.model.AvaSta;
import com.sinosoft.one.monitor.db.oracle.model.OraclePowerOffTimeModel;

import java.util.List;

/**
 * User: Chunliang.Han
 * Date: 13-2-27
 * Time: 下午8:27
 * Oracle数据库可用性相关信息处理
 */
public interface OracleAvaService {
    /**
     * 获取可用性详细信息
     */
    AvaSta findAvaSta(String monitorId, StaTimeEnum avaStaTimeEnum);

    /**
     * 获取可用性信息列表（监听时间历史）
     */
    List<AvaSta> listAvaSta(String monitorId);

    /**
     * 监视器停机时间明细查询
     */
    List<OraclePowerOffTimeModel> listPowerOffTime(String monitorId);
}
