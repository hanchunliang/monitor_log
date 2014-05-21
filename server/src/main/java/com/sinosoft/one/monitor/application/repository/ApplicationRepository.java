package com.sinosoft.one.monitor.application.repository;
// Generated 2013-2-27 18:41:37 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.application.model.Method;
import com.sinosoft.one.monitor.application.model.Url;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ApplicationRepository extends PagingAndSortingRepository<Application, String> {

    Application findByApplicationName(String applicationName);

    List<Application> findByStatus(String status);

    @SQL("select * from GE_MONITOR_APPLICATION a where a.STATUS='1' order by a.APPLICATION_NAME")
    List<Application> findAllActiveApplication();

    @SQL("select * from GE_MONITOR_APPLICATION a where a.STATUS='1' and application_Name=?1 and application_Ip=?2 and application_Port=?3")
    Application findByApplicationNameAndApplicationIpAndApplicationPort(String applicationName, String applicationIp, String applicationPort);

    @SQL("select * from GE_MONITOR_URL a where a.id in (select distinct b.URL_ID from GE_MONITOR_BIZ_SCENARIO_URL b " +
            "right join GE_MONITOR_BIZ_SCENARIO c on b.BIZ_SCENARIO_ID in (?1))")
    List<Url> selectAllUrlsWithBizScenarioIds(List<String> bizScenarioIds);

	@SQL("select distinct u.*\n" +
			"  from ge_monitor_application       a,\n" +
			"       ge_monitor_biz_scenario      bs,\n" +
			"       ge_monitor_biz_scenario_url  bsu,\n" +
			"       ge_monitor_url               u\n" +
			" where a.id = bs.application_id\n" +
			"   and bs.id = bsu.biz_scenario_id\n" +
			"   and bsu.url_id = u.id\n" +
			"   and a.id = ?1\n" +
			"   and a.status = '1'\n" +
			"   and bs.status = '1'\n" +
			"   and u.status = '1'")
	List<Url> selectAllUrlsWithApplicationId(String applicationId);

    @SQL("select * from GE_MONITOR_METHOD a where a.id in (select b.METHOD_ID from GE_MONITOR_URL_METHOD b " +
            "right join GE_MONITOR_URL c on b.URL_ID=:urlId)")
    List<Method> selectAllMethodsWithUrlId(@Param("urlId") String urlId);

    @SQL("update GE_MONITOR_APPLICATION a set a.STATUS='0' where a.ID=?1")
    void deleteApplicationById(@Param("appId") String appId);

    @SQL("update GE_MONITOR_APPLICATION a set a.CN_NAME=?2 ,a.APPLICATION_IP=?3, a.APPLICATION_PORT=?4, a.MODIFIER_ID=?5, a.MODIFY_TIME=sysdate,a.INTERVAL=?6 where a.ID=?1")
    void updateApplication(String appId, String cnName, String applicationIp, String applicationPort, String modifierId, BigDecimal interval);

    @SQL("select * from GE_MONITOR_APPLICATION a where a.STATUS='1'")
    List<Application> findAllApplicationNames();

    @SQL("select * from GE_MONITOR_APPLICATION a where a.id= ?1")
    Application findApplicationbyId(String id);
    
    @SQL("select * from GE_MONITOR_APPLICATION a where a.id= (select APPLICATION_ID from GE_MONITOR_BIZ_SCENARIO where id=?1 )")
    Application findApplicationbyBizID(String BizID);

    @SQL("select * from GE_MONITOR_APPLICATION a where a.STATUS='1' and a.application_ip=?1 and a.application_port=?2")
    List<Application> findByIpAndPort(String ip, String port);
}

