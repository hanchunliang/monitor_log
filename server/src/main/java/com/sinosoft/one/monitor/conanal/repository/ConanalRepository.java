package com.sinosoft.one.monitor.conanal.repository;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.alarm.model.Alarm;
import com.sinosoft.one.monitor.conanal.model.TableModel;
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
public interface ConanalRepository extends PagingAndSortingRepository<Alarm, String> {

    //查出报表信息
    @SQL("select t.url_id , t.application_id, a2.url, a3.application_name, count(t.url_id) as url_count \n" +
            "from ge_monitor_url_trace_log t, (select id, url from ge_monitor_url) a2, \n" +
            "(select id, application_name  from ge_monitor_application) a3\n" +
            "where t.url_id = a2.id and t.application_id = a3.id \n" +
            "group by t.url_id, t.application_id, a2.url, a3.application_name\n" +
            "order by t.application_id")
    public List<TableModel> getCount();

}
