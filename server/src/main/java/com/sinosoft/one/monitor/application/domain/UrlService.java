package com.sinosoft.one.monitor.application.domain;

import com.sinosoft.one.monitor.application.model.BizScenario;
import com.sinosoft.one.monitor.application.model.EumUrl;
import com.sinosoft.one.monitor.application.model.Url;
import com.sinosoft.one.monitor.application.repository.EumUrlRepository;
import com.sinosoft.one.monitor.application.repository.UrlRepository;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.monitor.resources.domain.ResourcesService;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.resources.repository.ResourcesRepository;
import com.sinosoft.one.monitor.utils.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zfb
 * Date: 13-2-27
 * Time: 下午8:28
 * To change this template use File | Settings | File Templates.
 */
@Component
@Transactional(readOnly = true)
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

	@Autowired
	private ResourcesRepository resourcesRepository;

	@Autowired
	private EumUrlRepository eumUrlRepository;

    @Autowired
    private ResourcesService resourcesService;
    /**
     * 新增一个URL.
     */
    @Transactional(readOnly = false)
    public void saveUrl(Url url) {
        urlRepository.save(url);
    }

    @Transactional(readOnly = false)
    public void saveUrlWithTransactional(Url url,BizScenario bizScenario){
        EumUrl eumUrl=new EumUrl();
        eumUrl.setUrl(url.getUrl());
        eumUrl.setApplication(bizScenario.getApplication());
        //获得当前用户id
        /*String creatorId = CurrentUserUtil.getCurrentUser().getId();*/
        //如果新增加的url是库中没有的，那么入库
        //当前Url所属的业务场景
        url.setBizScenario(bizScenario);
        url.setStatus(String.valueOf(1));
        //保存当前创建url的用户
        url.setCreatorId(CurrentUserUtil.getCurrentUser().getId());
        //开发阶段固定用户id
        /*url.setCreatorId("4028921a3cfba342013cfba4623e0000");*/
        url.setCreateTime(new Date());
        saveUrl(url);
        //向EUM_URL表中插入记录（url的application信息）
        eumUrl.setUrlId(url.getId());
        eumUrl.setRecordTime(new Date());
        eumUrlRepository.save(eumUrl);
        saveResourceWithUrl(url);
    }

    private void saveResourceWithUrl(Url url){
        //资源表存入新建业务场景的信息
        Resource resource=new Resource();
        resource.setResourceId(url.getId());
        resource.setResourceName(url.getDescription());
        resource.setResourceType(ResourceType.APPLICATION_SCENARIO_URL.name());
        resourcesService.saveResource(resource);
    }

    /**
     * 删除一个URL.
     */
    @Transactional(readOnly = false)
    public void deleteUrl(Url url) {
        urlRepository.delete(url);
    }

    /**
     * 删除一个URL，通过URL的id.
     */
    @Transactional(readOnly = false)
    public void deleteUrl(String bizScenarioId, String id) {
	    //先删除中间表GE_MONITOR_BIZ_SCENARIO_URL的记录
	    urlRepository.deleteBizScenarioAndUrl(bizScenarioId,id);
        //如果当前url有关联的其它业务场景，那么不能删除
        List<String> bizScenarioIds=urlRepository.selectUrlsWithUrlId(id);
        if(null!=bizScenarioIds&&bizScenarioIds.size()>0){
            return;
            //如果当前url没有关联其它的业务场景，那么删除当前url
        }else if(null==bizScenarioIds||bizScenarioIds.size()==0){
            //先删除中间表GE_MONITOR_URL_METHOD的记录
            urlRepository.deleteUrlAndMethod(id);
            //删除Resources表中的记录
            resourcesRepository.delete(resourcesRepository.findByResourceId(id));
            // 删除eumUrl表中的记录
            eumUrlRepository.delete(eumUrlRepository.findByUrlId(id));
            //删除GE_MONITOR_URL的记录
            urlRepository.delete(id);
        }
    }

    /**
     * 批量删除URL，通过URL的id.
     */
    @Transactional(readOnly = false)
    public void batchDeleteUrl(String[] ids) {
        urlRepository.batchDelete(ids);
    }

    /**
     * 查询一个URL，通过URL的id.
     */
    public Url findUrl(String id) {
        Url url = urlRepository.findUrlByID(id);
        return url;
    }

    /**
     * 查询一个URL，通过URL的地址.
     */
    public Url findUrlByUrlAddress(String urlAddress) {
        Url url = urlRepository.findByUrl(urlAddress);
        return url;
    }

    /**
     * 查询所有的URL.
     */
    public List<Url> findAllUrl() {
        List<Url> urls = (List<Url>) urlRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        return urls;
    }

    /**
     * 得到业务场景下所有的URL的地址.
     */
    public List<Url> findAllUrlsOfBizScenario(BizScenario bizScenario){
        return urlRepository.selectUrlsOfBizScenarioByIds(bizScenario.getId());
    }

    /**
     * 更新url.
     */
    @Transactional(readOnly = false)
    public void updateUrlWithModifyInfo(String urlId, String url, String description,String modifierId) {
        urlRepository.updateUrl(urlId, url, description,modifierId);
    }

    /**
     * 删除中间表数据.
     */
    @Transactional(readOnly = false)
    public void deleteBizScenarioAndUrl(String bizScenarioId,String urlId) {
        urlRepository.deleteBizScenarioAndUrl(bizScenarioId,urlId);
    }

    /**
     * 删除中间表数据.
     */
    @Transactional(readOnly = false)
    public void deleteUrlAndMethod(String urlId) {
        urlRepository.deleteUrlAndMethod(urlId);
    }

    /**
     * 批量删除中间表数据.
     */
    @Transactional(readOnly = false)
    public void batchDeleteBizScenarioAndUrl(String bizScenarioId,String[] urlIds) {
        urlRepository.batchDeleteBizScenarioAndUrl(bizScenarioId,urlIds);
    }

    /**
     * 批量删除中间表数据.
     */
    @Transactional(readOnly = false)
    public void batchDeleteUrlAndMethod(String[] urlIds) {
        urlRepository.batchDeleteUrlAndMethod(urlIds);
    }
}
