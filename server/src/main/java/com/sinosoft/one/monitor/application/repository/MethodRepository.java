package com.sinosoft.one.monitor.application.repository;
// Generated 2013-2-27 18:41:37 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.application.model.Method;
import com.sinosoft.one.monitor.application.model.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MethodRepository extends PagingAndSortingRepository<Method, String> {

    Method findByMethodName(String methodName);

    @SQL("select * from GE_MONITOR_METHOD a where a.ID in (select m.METHOD_ID from GE_MONITOR_URL_METHOD m where m.url_id=?2)")
    Page<Method> selectMethodsOfUrlById(Pageable pageable, String urlId);

    @SQL("select * from GE_MONITOR_METHOD a where a.ID in (select m.METHOD_ID from GE_MONITOR_URL_METHOD m where m.url_id=?1)")
    List<Method> selectMethodsOfUrlById(String urlId);



    @SQL("update GE_MONITOR_METHOD set CLASS_NAME=?2,METHOD_NAME=?3,DESCRIPTION=?4,MODIFIER_ID=?5,MODIFY_TIME=sysdate where id=?1")
    void updateMethod(String methodId, String className, String methodName, String description, String modifierId);

    @SQL("delete GE_MONITOR_URL_METHOD a where a.URL_ID=?1 and a.METHOD_ID=?2")
    void deleteUrlAndMethod(String urlId, String methodId);

    //查询中间表GE_MONITOR_URL_METHOD中是否还有其它url与当前method关联
    @SQL("select a.URL_ID from GE_MONITOR_URL_METHOD a where a.METHOD_ID=?1")
    List<String> selectAllUrlIdsWithMethodId(String methodId);

    @SQL("delete GE_MONITOR_URL_METHOD a where a.URL_ID=?1 and a.METHOD_ID in (?2)")
    void batchDeleteUrlAndMethod(String urlId, String[] methodIds);

    @SQL("delete GE_MONITOR_METHOD a where a.ID in (?1)")
    void batchDeleteMethod(String[] methodIds);
    
    @SQL("select * from GE_MONITOR_METHOD a where a.ID =?1")
    Method findMethodByid(String  methodId);
}

