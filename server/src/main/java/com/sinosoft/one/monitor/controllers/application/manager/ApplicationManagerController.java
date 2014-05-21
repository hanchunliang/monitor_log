package com.sinosoft.one.monitor.controllers.application.manager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosoft.one.monitor.application.domain.*;
import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.application.model.BizScenario;
import com.sinosoft.one.monitor.application.model.Method;
import com.sinosoft.one.monitor.application.model.Url;
import com.sinosoft.one.monitor.application.model.viewmodel.ApplicationIndexViewModel;
import com.sinosoft.one.monitor.logquery.domain.LogqueryService;
import com.sinosoft.one.monitor.utils.CurrentUserUtil;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.validation.annotation.Validation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用的增删改查Controller.
 * User: zfb
 * Date: 13-2-27
 * Time: 下午10:14
 */
@Path("appmanager")
public class ApplicationManagerController {

    Log logger= LogFactory.getLog(ApplicationManagerController.class);

    @Autowired
    ApplicationService applicationService;
    @Autowired
    BizScenarioService bizScenarioService;
    @Autowired
    BusinessEmulation businessEmulation;
    @Autowired
    ApplicationDetailService applicationDetailService;
    @Autowired
    ApplicationEmuService applicationEmuService;
    @Autowired
    AsynAgentMessageService agentMessageService;
    @Autowired
    LogqueryService logqueryService;
    /**
     * 获得所有的应用.
     */
    @Get("applist/{recentHour}")
    public String getAllApplication(@Param("recentHour") int recentHour, Invocation inv) {
//        List<Application> applications = applicationService.findAllApplication();

		List<ApplicationIndexViewModel> applicationIndexViewModels = applicationService.generateIndexViewModels(recentHour);
        inv.addModel("applicationIndexViewModels", applicationIndexViewModels);
	    inv.addModel("recentHour", recentHour);
        //应用性能列表页面
        return "performance";
    }

    /**
     * 增加应用的表单页面.
     */
    @Get("create")
    @Post("errorcreate")
    public String createApplication(Invocation inv) {
        inv.addModel("application", new Application());
        //页面所在路径application/manager/
        /*return "applicationForm";*/
        return "r:/addapplication/add";
    }

    /**
     * 新增一个应用.
     *//*
    @Post("add")
    public String saveApplication(@Validation(errorPath = "a:errorcreate") Application application, Invocation inv) {
        //获得当前用户
        application.setCreatorId(CurrentUserUtil.getCurrentUser().getId());
        //测试时固定CreatorId
        *//*application.setCreatorId("4028921a3cfb99be013cfb9ccf650000");*//*
        application.setCreateTime(new Date());
        application.setStatus(String.valueOf('1'));
        applicationService.saveApplication(application);
        //页面所在路径application/manager/@应用性能页面
        return "a:/appperformance";
    }*/

    /**
     * 应用性能.
     */
    @Get("appperformance")
    @Post("appperformance")
    public String applicationPerformance(Invocation inv){
        inv.addModel("applications",applicationService.findAllApplication());
        return "performance";
    }

    /**
     * 更新应用的表单页面.
     */
    @Get("update/{appId}")
    @Post("errorupdateapp")
    public String applicationForm(@Param("appId") String appId, Invocation inv) {
        inv.addModel("application", applicationService.findApplication(appId));
        //页面所在路径application/manager/
        return "modifyApplication";
    }

    /**
     * 更新应用.
     */
    @Post("update/{appId}")
//    public String updateApplication(@Validation(errorPath = "a:errorupdateapp") Application application, @Param("appId") String appId, Invocation inv) {
    	public String updateApplication(Application application, @Param("appId") String appId, Invocation inv) {
        //获得当前用户id
        String modifierId=CurrentUserUtil.getCurrentUser().getId();
        //更新时间使用sysdate
        applicationService.updateApplicationWithModifyInfo(appId,application.getCnName(),
                application.getApplicationIp(),application.getApplicationPort(),modifierId,application.getInterval());
        //更新缓存
        agentMessageService.refreshApplicationCache(application);
        businessEmulation.restart(appId);
        //应用列表页面
        return "r:/application/manager/appmanager/applist/1";
    }

    /**
     * 删除应用.
     */
    @Get("delete/{id}")
    public String deleteApplication(@Param("id") String id, Invocation inv) {
        applicationService.deleteApplication(id);
        //applicationDetailService.cancelApplicationThread(id);
        //applicationEmuService.cancelApplicationThread(id);
        //Application application = applicationService.findApplication(id);
        //更新缓存
        //agentMessageService.refreshApplicationCache(application);
        //businessEmulation.restart(id);
        //应用列表页面
        logqueryService.delLogByAppId(id);
        return "r:/application/manager/appmanager/applist/1";
    }

    /**
     * agent首次启动时，校验agent发送来的应用信息
     * 获得应用id，应用下的所有Url和Method，供agent匹配所监控的应用系统.
     */
    @Post("match")
    public Reply initMatchMap(Invocation inv){
        try {
            String appName=inv.getParameter("appName");
            String ip=inv.getParameter("ip");
            String port=inv.getParameter("port");
	        String applicationId = inv.getParameter("applicationId");
	        Application application = null;
	        if(applicationId != null && !"".equals(applicationId)) {
		        application = applicationService.findApplication(applicationId);
	        } else {
		        application = applicationService.findApplicationWithAppInfo(appName,ip,port);
	        }
            if(application!=null){
//                    List<String> bizScenarioIds=new ArrayList<String>();
//                    List<BizScenario> bizScenarios = bizScenarioService.findBizScenarioByAppid(application.getId());
//                    for(BizScenario bizScenario:bizScenarios){
//                        bizScenarioIds.add(bizScenario.getId());
//                    }
//                    List<Url> urls=applicationService.findAllUrlsOfApplication(bizScenarioIds);
//                    //URL.id,URL.url(address),URL.Method
//                    //Method.id,Method.className,Method.methodName
//                    String jsonString=getJsonDataOfUrlsAndMethods(application.getId(),urls);
//                    return Replys.with(jsonString);
                String reply =  "{\"applicationId\":\"" +
                        application.getId() +
                        "\"}";
                return Replys.with(reply);
            }
            return Replys.simple().fail("NotExist");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return Replys.simple().fail("Exception");
        }
    }

    /**
     * 返回url和method所对应的json数据.
     * url包括id和地址
     * method包括id，className，methodName
     */
    public String getJsonDataOfUrlsAndMethods(String applicationId,List<Url> urls) {
        JSONObject jsonUrlsObject=new JSONObject();
        JSONArray jsonUrlArray=new JSONArray();
        //返回的json数据包含当前应用的id，这个数据会写入代理端的notification.info文件中
        jsonUrlsObject.put("applicationId",applicationId);
        for(Url url:urls){
            JSONObject jsonUrlObject=new JSONObject();
            //处理当前url
            jsonUrlObject.put("urlId",url.getId());
            jsonUrlObject.put("urlAddress",url.getUrl());
            List<Method> methods=applicationService.findAllMethodsOfUrl(url.getId());
            JSONArray methodArray = new JSONArray();
            //循环处理url中所有的method
            for(Method method:methods){
                JSONObject jsonMethodObject=new JSONObject();
                jsonMethodObject.put("methodId",method.getId());
                jsonMethodObject.put("className",method.getClassName());
                jsonMethodObject.put("methodName",method.getMethodName());
                //此method作为当前url的一个节点
                methodArray.add(jsonMethodObject);
            }
            jsonUrlObject.put("methods", methodArray);
            jsonUrlArray.add(jsonUrlObject);
        }
        jsonUrlsObject.put("urls", jsonUrlArray);
        return jsonUrlsObject.toString();
    }
}
