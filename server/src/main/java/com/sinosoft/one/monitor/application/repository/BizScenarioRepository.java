package com.sinosoft.one.monitor.application.repository;
// Generated 2013-2-27 18:41:37 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.sinosoft.one.monitor.application.model.BizScenario;
import com.sinosoft.one.mvc.web.annotation.Param;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BizScenarioRepository extends PagingAndSortingRepository<BizScenario, String> {

    BizScenario findByName(String name);

    @SQL("select a.*,b.NAME as userName from GE_MONITOR_BIZ_SCENARIO a left join GE_MONITOR_ACCOUNT b on a.CREATOR_ID=b.ID where a.ID in (?1) order by a.BIZ_SCENARIO_GRADE")
    List<BizScenario> selectUserNameOfBizScenarioByIds(@Param("bizScenarioIds") List<String> bizScenarioIds);

    @SQL("update GE_MONITOR_BIZ_SCENARIO a set a.NAME=?2,a.BIZ_SCENARIO_GRADE=?3,a.MODIFIER_ID=?4,a.MODIFY_TIME=sysdate where a.ID=?1")
    void updateBizScenario(String bizScenarioId, String name, String bizScenarioGrade, String modifierId);

    @SQL("delete GE_MONITOR_BIZ_SCENARIO_URL a where a.BIZ_SCENARIO_ID=?1")
    void deleteBizScenarioAndUrl(String bizScenarioId);

    @SQL("delete GE_MONITOR_BIZ_SCENARIO_URL a where a.BIZ_SCENARIO_ID in (?1)")
    void batchDeleteBizScenarioAndUrl(String[] bizScenarioIds);

    @SQL("delete GE_MONITOR_BIZ_SCENARIO a where a.ID in (?1)")
    void batchDeleteBizScenario(String[] bizScenarioIds);

    @SQL("select a.*,b.NAME as userName from GE_MONITOR_BIZ_SCENARIO a left join GE_MONITOR_ACCOUNT b on a.CREATOR_ID=b.ID where a.ID in (?1) and a.BIZ_SCENARIO_GRADE=?2")
    List<BizScenario> selectUserNameOfBizScenarioByIdsAndGivenGrade(List<String> bizScenarioIds, String givenGrade);
    
    @SQL("select * from GE_MONITOR_BIZ_SCENARIO a where a.ID = ?1")
    BizScenario findBizById(String  bizScenarioId);
    
    @SQL("select * from GE_MONITOR_BIZ_SCENARIO a where a.APPLICATION_ID = ?1")
    List<BizScenario> findBizScenarioByAppid(String  appid);
}

