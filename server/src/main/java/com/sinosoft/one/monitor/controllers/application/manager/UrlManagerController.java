package com.sinosoft.one.monitor.controllers.application.manager;

import com.sinosoft.one.monitor.application.domain.BizScenarioService;
import com.sinosoft.one.monitor.application.domain.BusinessEmulation;
import com.sinosoft.one.monitor.application.domain.UrlFilter;
import com.sinosoft.one.monitor.application.domain.UrlService;
import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.application.model.BizScenario;
import com.sinosoft.one.monitor.application.model.Url;
import com.sinosoft.one.monitor.application.repository.EumUrlRepository;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.monitor.resources.domain.ResourcesService;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.resources.repository.ResourcesRepository;
import com.sinosoft.one.monitor.utils.CurrentUserUtil;
import com.sinosoft.one.monitor.utils.SynAgentUtil;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.validation.annotation.Validation;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * URL的增删改查Controller.
 * User: zfb
 * Date: 13-2-28
 * Time: 下午3:26
 */
@Path("urlmanager")
public class UrlManagerController {

    @Autowired
    UrlService urlService;
    @Autowired
    BizScenarioService bizScenarioService;
    @Autowired
    ResourcesService resourcesService;
    @Autowired
    EumUrlRepository eumUrlRepository;
    @Autowired
    BusinessEmulation businessEmulation;
    @Autowired
    ResourcesRepository resourcesRepository;
    @Autowired
    UrlFilter urlFilter;
    /**
     * 管理url页面.
     */
    @Get("urllist/{bizScenarioId}")
    public String managerUrl(@Param("bizScenarioId") String bizScenarioId,Invocation inv) {
        inv.getRequest().setAttribute("bizScenarioId",bizScenarioId);
        inv.getRequest().setAttribute("bizScenarioName",bizScenarioService.findBizScenario(bizScenarioId).getName());
        //页面所在路径application/manager/
        return "managerUrl";
    }

    /**
     * 响应管理url页面发送的ajax请求.
     */
    @Post("urllist/{bizScenarioId}")
    public void getAllUrlOfBizScenario(@Param("bizScenarioId") String bizScenarioId,Invocation inv) throws Exception {
        BizScenario bizScenario=bizScenarioService.findBizScenario(bizScenarioId);
        inv.getRequest().setAttribute("bizScenarioId",bizScenarioId);
        /*inv.getRequest().setAttribute("bizScenarioName",bizScenario.getName());*/
        List<Url> urls=urlService.findAllUrlsOfBizScenario(bizScenario);
        Page page=new PageImpl(urls);
        Gridable<BizScenario> gridable=new Gridable<BizScenario>(page);
        /*String cellString=new String("url,description,status,threshold,operation");*/
        String cellString=new String("url,description,operation");
        gridable.setIdField("id");
        gridable.setCellStringField(cellString);
        try {
            UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
        } catch (Exception e) {
            throw new Exception("json数据转换出错!",e);
        }
    }

    /**
     * 增加Url的表单页面(其中id是所属的业务场景的id).
     */
    @Get("createurl/{bizScenarioId}")
    @Post("errorcreateurl")
    public String createUrl(@Param("bizScenarioId") String bizScenarioId,Invocation inv) {
        inv.getRequest().setAttribute("bizScenarioId",bizScenarioId);
        inv.addModel("url", new Url());
        //页面所在路径application/manager/
        return "addUrl";
    }

    /**
     * 新增一个URL(其中id是所属的业务场景的id).
     */
    @Post("addurl/{bizScenarioId}")
//    public String saveUrl(@Validation(errorPath = "a:errorcreateurl") Url url,
//                          @Param("bizScenarioId") String bizScenarioId, Invocation inv) throws IOException {
    	 public String saveUrl(Url url,
                 @Param("bizScenarioId") String bizScenarioId, Invocation inv) throws IOException {	
        BizScenario bizScenario = bizScenarioService.findBizScenario(bizScenarioId);
        List<Url> urls=urlService.findAllUrl();

        if(urls.size()>0){
            for(Url dbUrl:urls){
                //如果新增加的url是库中已经存在的，那么只需更新此url所属的业务场景即可
                if (dbUrl.getUrl().equals(url.getUrl())) {
                    dbUrl.setBizScenario(bizScenario);
                    //已经关联的中间表，不用再保存URL
                    /*urlService.saveUrl(dbUrl);*/
                    //向EUM_URL表中插入记录（url的application信息）
                    /*eumUrl.setUrlId(dbUrl.getId());
                    eumUrl.setRecordTime(new Date());
                    eumUrlRepository.save(eumUrl);*/
                    //资源表中已经有当前的URL，那么不需要再保存
                    urlService.saveUrl(dbUrl);
                    return "r:/application/manager/urlmanager/urllist/"+bizScenarioId;
                }
            }
        }
        urlService.saveUrlWithTransactional(url,bizScenario);
        Application application =bizScenario.getApplication();
        List<String> addUrlArgs=new ArrayList<String>();
        addUrlArgs.add(url.getId());
        addUrlArgs.add(url.getUrl());
//        SynAgentUtil.httpClientOfSynAgent(application.getApplicationIp(),
//                Integer.parseInt(application.getApplicationPort()),
//                "/" + application.getApplicationName(),
//                "addLogUrl", addUrlArgs);
        synUrlForAgent(application.getId());
        businessEmulation.restart(bizScenario.getApplication().getId());
        return "r:/application/manager/urlmanager/urllist/"+bizScenarioId;
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
     * 新增一个URL(其中id是所属的业务场景的id).
     */
    //@todo 一次添加多个url，留待将来扩展
    /*@Post({"addurl/{bizScenarioId}","addUrl/{bizScenarioId}/{urlIds}"})
    public String saveUrl(@Validation(errorPath = "a:errorcreateurl") List<Url> urls,
                          @Param("urlIds") List<String> urlIds, @Param("bizScenarioId") String bizScenarioId, Invocation inv) {
        BizScenario bizScenario = bizScenarioService.findBizScenario(bizScenarioId);
        //如果是新增加的url
        if (urls != null) {
            List<Url> urlList = urlService.findAllUrl();
            List<String> urlAddresses = urlService.findAllUrlAddresses(urlList);
            //获得当前用户id
            *//*String creatorId = CurrentUserUtil.getCurrentUser().getId();*//*
            //开发阶段固定用户id
            String creatorId = "4028921a3cfba342013cfba4623e0000";
            for (Url url : urls) {
                //如果新增加的url是库中已经存在的，那么只需更新此url所属的业务场景即可
                if (urlAddresses.contains(url.getUrl())) {
                    url.setBizScenario(bizScenario);
                }
                //如果新增加的url是库中没有的，那么入库
                //当前Url所属的业务场景
                url.setBizScenario(bizScenario);
                //保存当前创建url的用户
                url.setCreatorId(creatorId);
                url.setCreateTime(new Date());
                urlService.saveUrl(url);
            }
        }
        //如果勾选的已经存在的url，只需更新url所属的业务场景
        // 此处需要优化，否则或许会判断所有的url是否被勾选（应该只得到被勾选的就行）
        if (urlIds != null) {
            for (String urlId : urlIds) {
                if (!StringUtils.isBlank(urlId)) {
                    urlService.findUrl(urlId).setBizScenario(bizScenario);
                }
            }
        }
        //页面所在路径application/manager/
        return "@新增Method页面（或URL列表页面）";
    }*/

    /**
     * 更新URL的表单页面.
     */
    @Get("updateform/{bizScenarioId}/{urlId}")
    @Post("errorupdateurl")
    public String urlForm(@Param("bizScenarioId") String bizScenarioId,@Param("urlId") String urlId, Invocation inv) {
        inv.setAttribute("bizScenarioId",bizScenarioId);
        inv.addModel("url", urlService.findUrl(urlId));
        return "modifyUrl";
    }

    /**
     * 更新URL.
     */
    @Post("update/{bizScenarioId}/{urlId}")
//    public String updateUrl(@Validation(errorPath = "a:errorupdateurl") Url url, @Param("bizScenarioId") String bizScenarioId,
//            @Param("urlId") String urlId, Invocation inv) throws IOException {
    public String updateUrl( Url url, @Param("bizScenarioId") String bizScenarioId,
                            @Param("urlId") String urlId, Invocation inv) throws IOException {
        String modifierId=CurrentUserUtil.getCurrentUser().getId();
        urlService.updateUrlWithModifyInfo(urlId,url.getUrl(),url.getDescription(),modifierId);
        //更新GE_MONITOR_EUM_URL表中的URL地址
        eumUrlRepository.updateEumUrlsWithUrlId(url.getUrl(),urlId);
        Application application =bizScenarioService.findBizScenario(bizScenarioId).getApplication();
        List<String> addUrlArgs=new ArrayList<String>();
        addUrlArgs.add(urlId);
        addUrlArgs.add(url.getUrl());
//        SynAgentUtil.httpClientOfSynAgent(application.getApplicationIp(),
//                Integer.parseInt(application.getApplicationPort()),
//                "/" + application.getApplicationName(),
//                "addLogUrl", addUrlArgs);
        synUrlForAgent(application.getId());
        businessEmulation.restart(bizScenarioService.findBizScenario(bizScenarioId).getApplication().getId());
        //Url列表页面
        return "r:/application/manager/urlmanager/urllist/"+bizScenarioId;
    }

    /**
     * 删除url.
     */
    @Get("delete/{bizScenarioId}/{urlId}")
    public String deleteUrl(@Param("bizScenarioId") String bizScenarioId,@Param("urlId") String urlId, Invocation inv) throws IOException {
        urlService.deleteUrl(bizScenarioId, urlId);
        Application application =bizScenarioService.findBizScenario(bizScenarioId).getApplication();
        List<String> addUrlArgs=new ArrayList<String>();
        addUrlArgs.add(urlId);
//        SynAgentUtil.httpClientOfSynAgent(application.getApplicationIp(),
//                Integer.parseInt(application.getApplicationPort()),
//                "/" + application.getApplicationName(),
//                "removeLogUrl", addUrlArgs);
        synUrlForAgent(application.getId());
        businessEmulation.restart(bizScenarioService.findBizScenario(bizScenarioId).getApplication().getId());
        //url列表页面
        return "r:/application/manager/urlmanager/urllist/"+bizScenarioId;
    }

    /**
     * 批量删除url.
     */
    @Post("batchdelete/{bizScenarioId}")
    public String batchDeleteUrl(@Param("bizScenarioId") String bizScenarioId, Invocation inv) {
        //写回bizScenarioId，返回url列表页面时用到
        String[] urlIds=inv.getRequest().getParameterValues("urlIds[]");
        for(int i=0;i<urlIds.length;i++){
            urlService.deleteUrl(bizScenarioId,urlIds[i]);
        }
        Application application =bizScenarioService.findBizScenario(bizScenarioId).getApplication();
        SynAgentUtil.httpClientOfSynAgent(application.getApplicationIp(),
                Integer.parseInt(application.getApplicationPort()),
                "/" + application.getApplicationName(),
                "removeLogUrl", urlIds);
        businessEmulation.restart(bizScenarioService.findBizScenario(bizScenarioId).getApplication().getId());
        //url列表页面
        return "r:/application/manager/urlmanager/urllist/"+bizScenarioId;
    }
    private void synUrlForAgent(String applicationId){
        urlFilter.refreshApplicationUrlCache(applicationId);
    }
}
