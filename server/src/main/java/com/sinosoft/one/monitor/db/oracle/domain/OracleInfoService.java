package com.sinosoft.one.monitor.db.oracle.domain;

import com.sinosoft.one.monitor.db.oracle.model.Info;
import com.sinosoft.one.monitor.db.oracle.model.OracleInfoModel;

import java.util.List;

/**
 * User: Chunliang.Han
 * Date: 13-2-27
 * Time: 下午7:39
 * Oracle监视器的处理类 ：包括添加Oracle监视器，修改Oracle监视器，删除Oracle监视器，展现Oracle监视器信息
 */
public interface OracleInfoService {
    /**
     * 添加Oracle监视器
     */
    void saveMonitor(Info info) throws Exception;

    /**
     * 修改Oracle监视器
     */
    void editMonitor(Info info);

    /**
     * 删除Oracle监视器
     * @param monitorId
     */
    void deleteMonitor(List<String> monitorId);
    
    Info getInfo(String monitorId);

    /**
     * 展现监视器详细信息
     */
    OracleInfoModel getMonitorInfo(String monitorId);
}
