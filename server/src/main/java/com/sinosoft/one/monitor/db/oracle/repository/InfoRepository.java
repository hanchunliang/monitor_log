package com.sinosoft.one.monitor.db.oracle.repository;
// Generated 2013-2-27 18:10:19 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.db.oracle.model.Info;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface InfoRepository extends PagingAndSortingRepository<Info, String> {
    @SQL("delete from ge_monitor_oracle_info where id in (?1)")
    void deleteByIds(List<String> monitorId);
}

