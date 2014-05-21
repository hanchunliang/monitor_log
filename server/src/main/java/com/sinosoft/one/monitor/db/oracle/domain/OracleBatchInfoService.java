package com.sinosoft.one.monitor.db.oracle.domain;

import com.sinosoft.one.monitor.db.oracle.model.*;

import java.util.List;

/**
 * User: Chunliang.Han
 * Date: 13-2-28
 * Time: 下午5:55
 * 批量视图信息处理类
 */
public interface OracleBatchInfoService {

    /**
     * 可用性列表
     */
    List<OracleAvaInfoModel> avaInfoList(StaTimeEnum staTimeEnum);

    /**
     * 健康状况列表
     */
    List<OracleHealthInfoModel> healthInfoList(StaTimeEnum staTimeEnum);

    /**
     * 列表视图(名称，可用性，健康状况)
     */
    List<OracleStaBaseInfoModel> listStaBaseInfo();

    /**
     * 连接时间统计信息  、用户数统计信息  、命中率统计信息
     */
    List<StaGraphModel> listMonitorEventSta();

}
