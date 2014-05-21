package com.sinosoft.one.monitor.logquery.domain;

import com.sinosoft.one.monitor.logquery.model.UrlTraceLogEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: king-bj
 * Date: 13-11-2
 * Time: 下午10:35
 * To change this template use File | Settings | File Templates.
 */
public interface LogqueryService {

    public List<UrlTraceLogEntity> getUrlTraceLogInfo(String appId, String userName, String userIp, String fromTime, String endTime, String areaCode);

    //查询URL_TRACE_LOG表中一共有多少条数据
    public int calDateTotal();

    //根据URL_ID查询操作信息
    public String findOptInfoById(String id);

    //根据app_id查询应用名称
    public String findAppNameById(String id);

    //根据应用的id逻辑删除log
    public void delLogByAppId(String appId);
}
