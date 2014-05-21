package com.sinosoft.one.monitor.logquery.repository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.alarm.model.Alarm;
import com.sinosoft.one.monitor.logquery.model.UrlTraceLogEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: king-bj
 * Date: 13-11-2
 * Time: 下午10:36
 * To change this template use File | Settings | File Templates.
 */
public interface LogqueryRepository extends PagingAndSortingRepository<Alarm, String> {

    //从urlTraceLog中查询出信息
    @SQL("select t.ID, t.URL_ID, t.RECORD_TIME, t.USERNAME, t.APPLICATION_ID, t.USER_IP  from GE_MONITOR_URL_TRACE_LOG t" +
            " where t.APPLICATION_ID in (" +
            "       select b.id from ge_monitor_application b where b.status = '1'" +
            ") " +
            "#if(?1 != ''){ and t.APPLICATION_ID = ?1} #if(?2 != ''){ and t.USERNAME like ?2}" +
            "#if(?3 != ''){ and t.USER_IP like ?3} " +
            "#if(?4 != ''){ and t.RECORD_TIME >= to_date(?4, 'yyyy-mm-dd')}" +
            "#if(?5 != ''){ and t.RECORD_TIME <= to_date(?5, 'yyyy-mm-dd')}" +
            "#if(?6 != ''){ and t.AREA_CODE = ?6}")
    public List<UrlTraceLogEntity> getUrlTraceLogInfo(String appId, String userName, String userIp, String fromTime, String endTime, String areaCode);


    @SQL("select count(*) from GE_MONITOR_URL_TRACE_LOG")
    public int calDateTotal();

    @SQL("select t.application_name  from ge_monitor_application t where t.id = ?1")
    public String findAppNameById(String id);

    @SQL("select t.description from ge_monitor_url t where t.id = ?1")
    public String findOptInfoBy(String id);

    @SQL("update ge_monitor_url_trace_log t set t.app_status = 0 where t.application_id = ?1")
    public void delLogByAppId(String appId);

}
