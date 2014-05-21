package com.sinosoft.one.monitor.db.oracle.repository;
// Generated 2013-2-27 18:10:19 by One Data Tools 1.0.0

import java.util.Date;
import java.util.List;

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.db.oracle.model.AvaSta;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AvaStaRepository extends PagingAndSortingRepository<AvaSta, String> {

    @SQL("select a.* from " +
            "(select * from GE_MONITOR_ORACLE_AVA_STA  where database_id=?1 and record_time = ?2 ) a" +
            " where rownum=1")
    AvaSta findAvaSta(String monitorId, Date date);
    @SQL("select * from GE_MONITOR_ORACLE_AVA_STA a   where a.database_id=?1 and rownum=1 and a.record_time = ?2")
    AvaSta findAvaStaByTime(String monitorId,Date inserTime);
    @SQL("delete from GE_MONITOR_ORACLE_AVA_STA where database_id in (?1)")
    void deleteByMonitorIds(List<String> monitorId);
}

