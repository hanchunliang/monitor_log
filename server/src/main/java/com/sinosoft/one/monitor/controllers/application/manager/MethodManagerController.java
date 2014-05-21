package com.sinosoft.one.monitor.controllers.application.manager;

import com.sinosoft.one.monitor.application.domain.BizScenarioService;
import com.sinosoft.one.monitor.application.domain.MethodService;
import com.sinosoft.one.monitor.application.domain.UrlFilter;
import com.sinosoft.one.monitor.application.domain.UrlService;
import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.application.model.Method;
import com.sinosoft.one.monitor.application.model.Url;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Method的增删改查Controller.
 * User: zfb
 * Date: 13-2-28
 * Time: 下午2:41
 */
@Path("methodmanager")
public class MethodManagerController {

    @Autowired
    MethodService methodService;
    @Autowired
    UrlService urlService;
    @Autowired
    BizScenarioService bizScenarioService;
    @Autowired
    UrlFilter urlFilter;

    /**
     * 管理Method页面.
     */
    @Get("methodlist/{bizScenarioId}/{urlId}")
    /*@Post("methodList")*/
    public String managerMethod(@Param("bizScenarioId") String bizScenarioId,@Param("urlId") String urlId,Invocation inv) {
        inv.getRequest().setAttribute("urlId",urlId);
        inv.addModel("bizScenarioId",bizScenarioId);
        Url url=urlService.findUrl(urlId);
        inv.getRequest().setAttribute("urlName",url.getDescription());
        inv.getRequest().setAttribute("urlAddress",url.getUrl());
        return "managerMethod";
    }

    /**
     * 获得Url下所有的Method.
     */
    @Post("methods/{urlId}")
    /*@Post("methodList")*/
    public void getAllMethodsOfUrl(@Param("urlId") String urlId,Invocation inv) throws Exception {
        inv.getRequest().setAttribute("urlId",urlId);
        Url url=urlService.findUrl(urlId);
/*        inv.getRequest().setAttribute("urlName",url.getDescription());
        inv.getRequest().setAttribute("urlAddress",url.getUrl());*/
        List<Method> methods=methodService.findAllMethodsOfUrl(url);
        Page page=new PageImpl(methods);
        Gridable<Method> gridable=new Gridable<Method>(page);
        String cellString=new String("className,methodName,description,operation");
        gridable.setIdField("id");
        gridable.setCellStringField(cellString);
        try {
            UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
        } catch (Exception e) {
            throw new Exception("json数据转换出错!",e);
        }
    }

    /**
     * 增加Method的表单页面(其中id是所属的Method的id).
     */
    @Get("createmethod/{bizScenarioId}/{urlId}")
    @Post("errorcreatemethod")
    public String createMethod(@Param("bizScenarioId") String bizScenarioId,@Param("urlId") String urlId,Invocation inv) {
        inv.getRequest().setAttribute("urlId",urlId);
        inv.addModel("bizScenarioId",bizScenarioId);
        inv.addModel("method", new Method());
        //页面所在路径application/manager/
        return "addMethod";
    }

    /**
     * 新增一个Method(其中id是所属的URL的id).
     * methods是新增加的method
     * methodIds是在已有的method列表中勾选的method的id
     */
    @Post("addmethod/{bizScenarioId}/{urlId}")
//    public String saveMethod(@Validation(errorPath = "a:errorcreatemethod") Method method,
//            @Param("bizScenarioId") String bizScenarioId,
//            @Param("urlId") String urlId, Invocation inv) throws IOException {
    public String saveMethod(Method method,
                             @Param("bizScenarioId") String bizScenarioId,
                             @Param("urlId") String urlId, Invocation inv) throws IOException {
        Url url = urlService.findUrl(urlId);
        List<Method> methods = methodService.findAllMethod();
        //获得当前用户id
        String creatorId = CurrentUserUtil.getCurrentUser().getId();
        //开发阶段固定用户id
        /*String creatorId = "4028921a3cfba342013cfba4623e0000";*/
        String methodAndClassName=findClassAndMethodName(method);
        if(methods.size()>0){
            for(Method dbMethod:methods){
                String dbMethodAndClassName=findClassAndMethodName(dbMethod);
                //如果新增加的method是库中已存在的，那么只需更新此Method所属的URL即可.
                if (dbMethodAndClassName.equals(methodAndClassName)) {
                    dbMethod.setUrl(url);
                    methodService.saveMethod(dbMethod);
                    return "r:/application/manager/methodmanager/methodlist/"+bizScenarioId+"/"+urlId;
                }
            }
        }
        //如果是新增加的method，保存method入库
        //当前Method所属的Url
        method.setUrl(url);
        method.setStatus(String.valueOf(1));
        if(StringUtils.isBlank(method.getDescription())){
            method.setDescription("-");
        }
        //保存当前创建method的用户
        method.setCreatorId(creatorId);
        method.setCreateTime(new Date());
        methodService.saveMethod(method);
        //synAgentOfAddOrUpdate(bizScenarioId,url,method);
        synMethodForAgent(urlId);
        return "r:/application/manager/methodmanager/methodlist/"+bizScenarioId+"/"+urlId;
    }

    /**
     * 增加或者修改METHOD时，同步agent端.
     */
    private void synAgentOfAddOrUpdate(String bizScenarioId,Url url,Method method) {
        Application application =bizScenarioService.findBizScenario(bizScenarioId).getApplication();
        List<String> addUrlArgs=new ArrayList<String>();
        addUrlArgs.add(url.getId());
        addUrlArgs.add(method.getId());
        addUrlArgs.add(method.getClassName());
        addUrlArgs.add(method.getMethodName());
        SynAgentUtil.httpClientOfSynAgent(application.getApplicationIp(),
                Integer.parseInt(application.getApplicationPort()),
                "/" + application.getApplicationName(),
                "addLogMethod", addUrlArgs);

    }

    /**
     * 得到的Method的全类名.
     */
    public String findClassAndMethodName(Method method) {
        if (method != null) {
            String methodAndClassName="";
            methodAndClassName = method.getMethodName() + "." + method.getClassName();
            return methodAndClassName;
        }
        return null;
    }

    /**
     * 新增一个Method(其中id是所属的URL的id).
     * methods是新增加的method
     * methodIds是在已有的method列表中勾选的method的id
     */
    /*@Post("addmethod/{urlId}/{methodIds}")
    public String saveMethod(@Validation(errorPath = "a:errorcreatemethod") List<Method> methods,
                             @Param("methodIds") List<String> methodIds, @Param("urlId") String urlId, Invocation inv) {
        Url url = urlService.findUrl(urlId);
        //如果是新增加的method
        if (methods != null) {
            List<Method> methodList = methodService.findAllMethod();
            String creatorId = CurrentUserUtil.getCurrentUser().getId();
            List<String> classAndMethodNames = methodService.findClassAndMethodName(methodList);
            for (Method method : methods) {
                //如果新增加的method是库中已存在的，那么只需更新此Method所属的URL即可.
                if (classAndMethodNames.contains(methodService.findClassAndMethodName(method))) {
                    method.setUrl(url);
                    //methods.remove(method);
                }
                //如果是新增加的method，保存method入库
                //当前Method所属的Url
                method.setUrl(url);
                //保存当前创建method的用户
                method.setCreatorId(creatorId);
                method.setCreateTime(new Date());
                methodService.saveMethod(method);
            }
        }
        //如果是勾选的已经存在的method，只需更新method所属的Url
        // 此处需要优化，否则或许会判断所有的method是否被勾选（应该只得到被勾选的就行）
        if (methodIds != null) {
            for (String methodId : methodIds) {
                if (!StringUtils.isBlank(methodId)) {
                    methodService.findMethod(methodId).setUrl(url);
                }
            }
        }
        //页面所在路径application/manager/
        return "业务场景列表页面（或Method列表页面）";
    }
*/
    /**
     * 更新Method的表单页面.
     */
    @Get("updatemethod/{bizScenarioId}/{urlId}/{methodId}")
    @Post("errorupdatemethod/{methodId}")
    public String methodForm(@Param("bizScenarioId") String bizScenarioId,@Param("urlId") String urlId,@Param("methodId") String methodId, Invocation inv) {
        inv.getRequest().setAttribute("urlId",urlId);
        inv.addModel("bizScenarioId",bizScenarioId);
        inv.addModel("method", methodService.findMethod(methodId));
        //页面所在路径application/manager/
        return "modifyMethod";
    }

    /**
     * 更新Method.
     */
    @Post("updatemethod/{bizScenarioId}/{urlId}/{methodId}")
    public String updateMethod( Method method,
                               @Param("bizScenarioId") String bizScenarioId,
                               @Param("urlId") String urlId, @Param("methodId") String methodId,Invocation inv) throws IOException {
//    	public String updateMethod(@Validation(errorPath = "a:errorupdatemethod/{methodId}") Method method,
//                @Param("bizScenarioId") String bizScenarioId,
//                @Param("urlId") String urlId, @Param("methodId") String methodId,Invocation inv) throws IOException {
        //将urlId写回，managerMethod页面发送ajax请求时会用到
        inv.getRequest().setAttribute("urlId",urlId);
        if(StringUtils.isBlank(method.getDescription())){
            method.setDescription("-");
        }
        //获得当前用户
        String modifierId=CurrentUserUtil.getCurrentUser().getId();
        methodService.updateMethodWithModifyInfo(methodId, method.getClassName(), method.getMethodName(), method.getDescription(), modifierId);
        //synAgentOfAddOrUpdate(bizScenarioId,urlService.findUrl(urlId),methodService.findMethod(methodId));
        synMethodForAgent(urlId);
        return "r:/application/manager/methodmanager/methodlist/"+bizScenarioId+"/"+urlId;
    }

    /**
     * 单个删除Method.
     */
    @Get("delete/{bizScenarioId}/{urlId}/{methodId}")
    public String deleteMethod(@Param("bizScenarioId") String bizScenarioId,@Param("urlId") String urlId, @Param("methodId") String methodId, Invocation inv) {
        methodService.deleteUrlAndMethod(urlId, methodId);
//        synAgentOfDelete(bizScenarioId,urlId,methodId);
        synMethodForAgent(urlId);
        //Method列表页面
        return "r:/application/manager/methodmanager/methodlist/"+bizScenarioId+"/"+urlId;
    }

    /**
     * 删除METHOD时，同步agent端.
     */
    private void synAgentOfDelete(String bizScenarioId,String urlId,String methodId){
        Application application =bizScenarioService.findBizScenario(bizScenarioId).getApplication();
        List<String> addUrlArgs=new ArrayList<String>();
        addUrlArgs.add(urlId);
        addUrlArgs.add(methodId);
        SynAgentUtil.httpClientOfSynAgent(application.getApplicationIp(),
                Integer.parseInt(application.getApplicationPort()),
                "/" + application.getApplicationName(),
                "removeLogMethod", addUrlArgs);
    }

    private void synMethodForAgent(String urlId){
        urlFilter.refreshUrlMethodCathe(urlId);
    }

    /**
     * 批量删除Method.
     */
    @Post("batchdelete/{bizScenarioId}/{urlId}")
    public String batchDeleteMethod(@Param("bizScenarioId") String bizScenarioId, @Param("urlId") String urlId, Invocation inv) throws IOException {
        String[] methodIds=inv.getRequest().getParameterValues("methodIds[]");
        for(int i=0;i<methodIds.length;i++){
            methodService.deleteUrlAndMethod(urlId,methodIds[i]);
//            synAgentOfDelete(bizScenarioId,urlId,methodIds[i]);
            synMethodForAgent(urlId);
        }
        //Method列表页面
        return "r:/application/manager/methodmanager/methodlist/"+bizScenarioId+"/"+urlId;
    }
}
