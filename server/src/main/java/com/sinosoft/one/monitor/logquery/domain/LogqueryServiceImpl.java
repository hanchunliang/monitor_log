package com.sinosoft.one.monitor.logquery.domain;

import com.sinosoft.one.monitor.logquery.model.UrlTraceLogEntity;
import com.sinosoft.one.monitor.logquery.repository.LogqueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: king-bj
 * Date: 13-11-2
 * Time: 下午10:36
 * To change this template use File | Settings | File Templates.
 */
@Service
public class LogqueryServiceImpl implements LogqueryService {

    @Autowired
    private LogqueryRepository logqueryRepository;

    @Override
    public List<UrlTraceLogEntity> getUrlTraceLogInfo(String appId, String userName, String userIp, String fromTime, String endTime, String areaCode) {
        return logqueryRepository.getUrlTraceLogInfo(appId, userName, userIp, fromTime, endTime, areaCode);
    }

    @Override
    public int calDateTotal() {
        return logqueryRepository.calDateTotal();
    }

    @Override
    public String findOptInfoById(String id) {
        return logqueryRepository.findOptInfoBy(id);
    }

    @Override
    public String findAppNameById(String id) {
        return logqueryRepository.findAppNameById(id);
    }

    @Override
    public void delLogByAppId(String appId) {
        logqueryRepository.delLogByAppId(appId);
    }
}
