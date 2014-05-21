package com.sinosoft.one.monitor.resources.repository;
// Generated 2013-3-1 10:54:17 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.resources.model.Resource;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface ResourcesRepository extends PagingAndSortingRepository<Resource, String> {

    @SQL("delete from GE_MONITOR_RESOURCES where RESOURCE_ID in (?1)")
    void deleteByMoitorIds(List<String> monitorId);

    @SQL("delete from GE_MONITOR_RESOURCES where RESOURCE_ID = ?1")
    void deleteByMoitorId(String monitorId);

    Resource findByResourceId(String resourceId);

    @SQL("select * from GE_MONITOR_RESOURCES a where a.RESOURCE_ID in (?1)")
    List<Resource> findAllResourcesWithUrlIds(String[] urlIds);
}

