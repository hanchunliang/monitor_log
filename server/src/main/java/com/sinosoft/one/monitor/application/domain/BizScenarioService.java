package com.sinosoft.one.monitor.application.domain;

import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.application.model.BizScenario;
import com.sinosoft.one.monitor.application.repository.ApplicationRepository;
import com.sinosoft.one.monitor.application.repository.BizScenarioRepository;
import com.sinosoft.one.monitor.resources.repository.ResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zfb
 * Date: 13-2-27
 * Time: 下午8:13
 * To change this template use File | Settings | File Templates.
 */
@Component
@Transactional(readOnly = true)
public class BizScenarioService {

    @Autowired
    BizScenarioRepository bizScenarioRepository;
    
    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ResourcesRepository resourcesRepository;
    /**
     * 新增一个业务场景.
     */
    @Transactional(readOnly = false)
    public void saveBizScenario(BizScenario bizScenario) {
        bizScenarioRepository.save(bizScenario);
    }

    /**
     * 删除一个业务场景.
     */
    @Transactional(readOnly = false)
    public void deleteBizScenario(BizScenario bizScenario) {
        bizScenarioRepository.delete(bizScenario);
    }

    /**
     * 删除一个应用，通过应用id.
     */
    @Transactional(readOnly = false)
    public void deleteBizScenario(String id) {
        bizScenarioRepository.deleteBizScenarioAndUrl(id);
        resourcesRepository.deleteByMoitorId(id);
        bizScenarioRepository.delete(id);
    }

    /**
     * 查询一个应用，通过应用id.
     */
    public BizScenario findBizScenario(String id) {
        BizScenario bizScenario = bizScenarioRepository.findBizById(id);
        Application application=  applicationRepository.findApplicationbyBizID(id);
        bizScenario.setApplication(application);
        return bizScenario;
    }

    /**
     * 查询一个应用，通过应用名.
     */
    public BizScenario findBizScenarioByName(String name) {
        BizScenario bizScenario = bizScenarioRepository.findByName(name);
        return bizScenario;
    }

    /**
     * 查询所有的应用.
     */
    public List<BizScenario> findAllBizScenario() {
        List<BizScenario> bizScenarios = (List<BizScenario>) bizScenarioRepository.findAll(new Sort(Sort.Direction.ASC, "id", "bizScenarioGrade"));
        return bizScenarios;
    }

    /**
     * 查询业务场景以及创建人.
     */
    public List<BizScenario> findUserNameAndBizScenario(List<BizScenario> bizScenarios){
        List<String> bizScenarioIds=new ArrayList<String>();
        for(BizScenario bizScenario:bizScenarios){
            bizScenarioIds.add(bizScenario.getId());
        }
        List<BizScenario> bizScenarioList=bizScenarioRepository.selectUserNameOfBizScenarioByIds(bizScenarioIds);
        return bizScenarioList;
    }

    /**
     * 查询指定级别的业务场景以及创建人.
     */
    public List<BizScenario> findUserNameAndBizScenarioWithGivenGrade(List<BizScenario> bizScenarios,String givenGrade){
        List<String> bizScenarioIds=new ArrayList<String>();
        for(BizScenario bizScenario:bizScenarios){
            bizScenarioIds.add(bizScenario.getId());
        }
        List<BizScenario> bizScenarioList=bizScenarioRepository.selectUserNameOfBizScenarioByIdsAndGivenGrade(bizScenarioIds,givenGrade);
        return bizScenarioList;
    }

    /**
     * 更新一个业务场景.
     */
    @Transactional(readOnly = false)
    public void updateBizScenarioWithModifyInfo(String bizScenarioId, String name, String bizScenarioGrade, String modifierId) {
        bizScenarioRepository.updateBizScenario(bizScenarioId, name, bizScenarioGrade, modifierId);
    }

    /**
     * 删除一个业务场景的中间表.
     */
    @Transactional(readOnly = false)
    public void deleteBizScenarioAndUrl(String bizScenarioId) {
        bizScenarioRepository.deleteBizScenarioAndUrl(bizScenarioId);
    }

    /**
     * 批量删除一个业务场景的中间表.
     */
    @Transactional(readOnly = false)
    public void batchDeleteBizScenarioAndUrl(String[] bizScenarioIds) {
        bizScenarioRepository.batchDeleteBizScenarioAndUrl(bizScenarioIds);
    }

    /**
     * 批量删除一个业务场景.
     */
    @Transactional(readOnly = false)
    public void batchDeleteBizScenario(String[] bizScenarioIds) {
        if(bizScenarioIds!=null){
            bizScenarioRepository.batchDeleteBizScenarioAndUrl(bizScenarioIds);
            List<String> ids = Arrays.asList(bizScenarioIds);
            resourcesRepository.deleteByMoitorIds(ids);
            bizScenarioRepository.batchDeleteBizScenario(bizScenarioIds);
        }
    }
    
    public List<BizScenario> findBizScenarioByAppid(String appid){
    	return bizScenarioRepository.findBizScenarioByAppid(appid);
    }
}
