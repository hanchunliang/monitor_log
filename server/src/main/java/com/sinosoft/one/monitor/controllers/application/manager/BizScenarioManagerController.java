package com.sinosoft.one.monitor.controllers.application.manager;

import com.sinosoft.one.monitor.application.domain.ApplicationService;
import com.sinosoft.one.monitor.application.domain.BizScenarioService;
import com.sinosoft.one.monitor.application.model.BizScenario;
import com.sinosoft.one.monitor.application.model.BizScenarioGrade;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.monitor.resources.domain.ResourcesService;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.utils.CurrentUserUtil;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 业务场景的增删改查Controller.
 * User: zfb
 * Date: 13-2-28
 * Time: 下午2:43
 */
@Path("bsmanager")
public class BizScenarioManagerController {

    @Autowired
    BizScenarioService bizScenarioService;
    @Autowired
    ApplicationService applicationService;
    @Autowired
    ResourcesService resourcesService;

    /**
     * 管理业务场景页面.
     */
    @Get("list/{appId}")
    public String getAllApplication(@Param("appId") String appId,Invocation inv) {
        inv.getRequest().setAttribute("appId",appId);
        inv.getRequest().setAttribute("applicationName",applicationService.findApplication(appId).getApplicationName());
        //管理业务场景页面
        return "managerBizScenario";
    }

    /**
     * 获得所有的业务场景(响应按下“管理业务场景”按钮时的ajax请求).
     */
    @Post({"bizscenariolist/{appId}","bizscenariolist"})
    public void getAllBizScenario(@Param("appId") String appId,Invocation inv) throws Exception {
        List<BizScenario> bizScenarioList=bizScenarioService.findUserNameAndBizScenario(bizScenarioService.findBizScenarioByAppid(appId));
        rendJsonDataOfBizScenarios(bizScenarioList,inv);

    }

    /**
     * 获得所选择级别的业务场景(响应按下“级别”按钮时的ajax请求).
     */
    @Post("bizscenariolist/{appId}/{givenGrade}")
    public void getBizScenarioOfGivenGrade(@Param("appId") String appId,@Param("givenGrade") String givenGrade,Invocation inv) throws Exception {
        givenGrade=BizScenarioGrade.parseValue(givenGrade).value;
        List<BizScenario> bizScenarioList=bizScenarioService.findUserNameAndBizScenarioWithGivenGrade(applicationService.findApplication(appId).getBizScenarios(), givenGrade);
        rendJsonDataOfBizScenarios(bizScenarioList,inv);
    }

    /**
    *获得业务场景列表的JSON数据
    */
    private void rendJsonDataOfBizScenarios(List<BizScenario> bizScenarioList,Invocation inv) throws Exception {
        if(bizScenarioList!=null){
            List<BizScenario> bizScenarios=new ArrayList<BizScenario>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(BizScenario bizScenario:bizScenarioList){
                bizScenario.setRecodeCreateTime(formatter.format(bizScenario.getCreateTime()));
                bizScenario.setBizScenarioGrade(BizScenarioGrade.parseValue(bizScenario.getBizScenarioGrade()).getDisplayName());
                bizScenarios.add(bizScenario);
            }
            /*List<BizScenario> bizScenarios = bizScenarioService.findAllBizScenario();*/
            Page page=new PageImpl(bizScenarios);
            Gridable<BizScenario> gridable=new Gridable<BizScenario>(page);
            String cellString=new String("name,userName,recodeCreateTime,bizScenarioGrade,operation");
            gridable.setIdField("id");
            gridable.setCellStringField(cellString);
            try {
                UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
            } catch (Exception e) {
                throw new Exception("json数据转换出错!",e);
            }
        }
    }

    /**
     * 增加业务场景的表单页面(其中id是所属的应用的id).
     */
    @Get("createbizscenario/{appId}")
    @Post("errorcreatebizscenario")
    public String createBizScenario(@Param("appId") String appId,Invocation inv) {
        //将应用appId写回页面，保存应用时提交这个appId
        inv.getRequest().setAttribute("appId", appId);
        inv.addModel("bizScenario", new BizScenario());
        //页面所在路径application/manager/
        return "addBizScenario";
    }

    /**
     * 新增一个业务场景(其中id是所属的应用的id).
     */
    @Post("addbizscenario/{appId}")
    public String saveBizScenario(BizScenario bizScenario,
                                  @Param("appId") String appId, Invocation inv) {
//    public String saveBizScenario(@Validation(errorPath = "a:errorcreatebizscenario") BizScenario bizScenario,
//                  				  @Param("appId") String appId, Invocation inv) {

    	//当前业务场景所属的应用
        bizScenario.setApplication(applicationService.findApplication(appId));
        bizScenario.setBizScenarioGrade(BizScenarioGrade.parseDisplayName(bizScenario.getBizScenarioGrade()).getValue());
        bizScenario.setStatus(String.valueOf(1));
        //获得当前用户
        bizScenario.setCreatorId(CurrentUserUtil.getCurrentUser().getId());
        //开发阶段，用户Id固定值
        /*bizScenario.setCreatorId("4028921a3cfb99be013cfb9ccf650000");*/
        bizScenario.setCreateTime(new Date());
        bizScenarioService.saveBizScenario(bizScenario);
        //资源表存入新建业务场景的信息
        Resource resource=new Resource();
        resource.setResourceId(bizScenario.getId());
        resource.setResourceName(bizScenario.getName());
        resource.setResourceType(ResourceType.APPLICATION_SCENARIO.name());
        resourcesService.saveResource(resource);
        return "r:/application/manager/bsmanager/list/"+appId;
    }

    /**
     * 更新业务场景的表单页面.
     */
    @Get("updateform/{appId}/{bizScenarioId}")
    @Post("errorupdatebizScenario")
    public String bizScenarioForm(@Param("appId") String appId, @Param("bizScenarioId") String bizScenarioId, Invocation inv) {
        inv.getRequest().setAttribute("appId",appId);
        inv.addModel("bizScenario", bizScenarioService.findBizScenario(bizScenarioId));
        //更新业务场景页面
        return "modifyBizScenario";
    }

    /**
     * 更新业务场景.
     */
    @Post("update/{appId}/{bizScenarioId}")
    public String updateBizScenario( BizScenario bizScenario,@Param("appId") String appId,
                                    @Param("bizScenarioId") String bizScenarioId, Invocation inv) {
//    	public String updateBizScenario(@Validation(errorPath = "a:errorupdatebizScenario") BizScenario bizScenario,@Param("appId") String appId,
//                @Param("bizScenarioId") String bizScenarioId, Invocation inv) {

    	//获得当前用户id
        String modifierId=CurrentUserUtil.getCurrentUser().getId();
        //开发阶段固定用户id
        /*String modifierId="4028921a3cfb99be013cfb9ccf650000";*/
        String bizScenarioGrade=BizScenarioGrade.parseDisplayName(bizScenario.getBizScenarioGrade()).getValue();
        bizScenarioService.updateBizScenarioWithModifyInfo(bizScenarioId,bizScenario.getName(),bizScenarioGrade,modifierId);
        //业务场景列表页面
        return "r:/application/manager/bsmanager/list/"+appId;
    }

    /**
     * 删除业务场景.
     */
    @Get("delete/{appId}/{bizScenarioId}")
    public String deleteBizScenario(@Param("appId") String appId, @Param("bizScenarioId") String bizScenarioId, Invocation inv) {
        //先删除中间表GE_MONITOR_BIZ_SCENARIO_URL的记录
        //bizScenarioService.deleteBizScenarioAndUrl(bizScenarioId);
        //删除GE_MONITOR_BIZ_SCENARIO的记录
        bizScenarioService.deleteBizScenario(bizScenarioId);
        //业务场景列表页面
        return "r:/application/manager/bsmanager/list/"+appId;
    }

    /**
     * 批量删除业务场景.
     */
    @Post("batchdelete/{appId}")
    public String batchDeleteBizScenario(@Param("appId") String appId, Invocation inv) {
        String[] bizScenarioIds=inv.getRequest().getParameterValues("bizScenarioIds[]");
        //先删除中间表GE_MONITOR_BIZ_SCENARIO_URL的记录
        //bizScenarioService.batchDeleteBizScenarioAndUrl(bizScenarioIds);
        //删除GE_MONITOR_BIZ_SCENARIO的记录
        bizScenarioService.batchDeleteBizScenario(bizScenarioIds);
        //业务场景列表页面
        return "r:/application/manager/bsmanager/list/"+appId;
    }
}
