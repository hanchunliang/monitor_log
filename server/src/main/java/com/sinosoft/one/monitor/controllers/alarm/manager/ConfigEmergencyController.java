package com.sinosoft.one.monitor.controllers.alarm.manager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosoft.one.monitor.action.model.ActionType;
import com.sinosoft.one.monitor.action.model.MailAction;
import com.sinosoft.one.monitor.action.repository.MailActionRepository;
import com.sinosoft.one.monitor.application.domain.ApplicationService;
import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.attribute.domain.AttributeCache;
import com.sinosoft.one.monitor.attribute.domain.AttributeService;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.attribute.model.AttributeAction;
import com.sinosoft.one.monitor.attribute.model.AttributeThreshold;
import com.sinosoft.one.monitor.attribute.repository.AttributeActionRepository;
import com.sinosoft.one.monitor.attribute.repository.AttributeRepository;
import com.sinosoft.one.monitor.attribute.repository.AttributeThresholdRepository;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.monitor.db.oracle.model.Info;
import com.sinosoft.one.monitor.db.oracle.repository.InfoRepository;
import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.repository.OsRepository;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.resources.repository.ResourcesRepository;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import com.sinosoft.one.monitor.threshold.model.Threshold;
import com.sinosoft.one.monitor.threshold.repository.ThresholdRepository;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理配置告警的Controller.
 * User: zfb
 * Date: 13-3-5
 * Time: 上午10:56
 */
@Path("configemergency")
public class ConfigEmergencyController {
    @Autowired
    AttributeService attributeService;
    @Autowired
    ApplicationService applicationService;
    @Autowired
    InfoRepository infoRepository;
    @Autowired
    OsRepository osRepository;
    @Autowired
    AttributeCache attributeCache;
    @Autowired
    AttributeRepository attributeRepository;
    @Autowired
    MailActionRepository mailActionRepository;
    @Autowired
    AttributeActionRepository attributeActionRepository;
    @Autowired
    ThresholdRepository thresholdRepository;
    @Autowired
    AttributeThresholdRepository attributeThresholdRepository;
    @Autowired
    ResourcesRepository resourcesRepository;

    @Get("config")
    public String configEmergencyForm(Invocation inv){
        return "setEmergency";
    }

    //配置告警页面，选择监视器类型时，得到相应类型下的所有可用的监视器
    @Post("monitornames/{resourceType}")
    public Reply getMonitorNames(@Param("resourceType") String resourceType, Invocation inv) throws Exception {
        JSONArray jsonArray = new JSONArray();
        String jsonMonitorNames="";
        if (ResourceType.APPLICATION.name().equals(resourceType)) {
            List<Application> applications = applicationService.findAllApplicationNames();
            if (applications != null) {
                for (Application application : applications) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("monitorId",application.getId());
                    //获取应用中文名
                    jsonObject.put("monitorName", application.getCnName());
                    jsonArray.add(jsonObject);
                }
                jsonMonitorNames = jsonArray.toJSONString();
                return Replys.with(jsonMonitorNames).as(Json.class);
            }
            return null;
        }else if(ResourceType.DB.name().equals(resourceType)){
            List<Info> dbInfos= (List<Info>) infoRepository.findAll();
            if (dbInfos!=null){
                for (Info dbInfo : dbInfos) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("monitorId",dbInfo.getId());
                    //获取GE_MONITOR_ORACLE_INFO表的NAME字段值
                    jsonObject.put("monitorName", dbInfo.getName());
                    jsonArray.add(jsonObject);
                }
                jsonMonitorNames = jsonArray.toJSONString();
                return Replys.with(jsonMonitorNames).as(Json.class);
            }
            return null;
        }else if(ResourceType.OS.name().equals(resourceType)){
            List<Os> oses= (List<Os>) osRepository.findAll();
            if (oses!=null){
                for (Os os : oses) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("monitorId",os.getOsInfoId());
                    //获取GE_MONITOR_OS表的NAME字段值
                    jsonObject.put("monitorName", os.getName());
                    jsonArray.add(jsonObject);
                }
                jsonMonitorNames = jsonArray.toJSONString();
                return Replys.with(jsonMonitorNames).as(Json.class);
            }
            return null;
        }else {
            return null;
        }
    }

    //获得配置告警页面底部的属性列表
    @Post("attributenames/{resourceType}/{monitorId}")
    public void getAttributeNames(@Param("resourceType") String resourceType,@Param("monitorId") String monitorId,Invocation inv) throws Exception {
        /*String dbResourceType=getResourceEnumString(resourceType);*/
        if(!StringUtils.isBlank(resourceType)){
            //所有的属性，通过attributeId+monitorId获取所需要的数据
            List<Attribute> dbAttributes=attributeService.findAllAttributesWithResourceType(resourceType);
            //供页面显示用的属性List
            List<Attribute> newAttributes=new ArrayList<Attribute>();
            if(dbAttributes!=null&&dbAttributes.size()>0){
                String thresholdOfAttributeStart="<a href='javascript:void(0)' onclick='setAttributeEmergency(this)'>";
                String thresholdOfAttributeEnd="</a>";
                AttributeThreshold dbAttributeThreshold=new AttributeThreshold();
                List<AttributeAction> dbAttributeAction=new ArrayList<AttributeAction>();
                for(Attribute attribute:dbAttributes){
                    List<String> actionNames=new ArrayList<String>();
                    String thresholdOfAttributeMiddle="关联";
                    String actionsOfAttribute="-";
                    //得到属性关联的阈值
                    dbAttributeThreshold=attributeThresholdRepository.findByResourceIdAndAttributeId(monitorId,attribute.getId());
                    if(dbAttributeThreshold!=null){
                        thresholdOfAttributeMiddle=thresholdRepository.findOne(dbAttributeThreshold.getThresholdId()).getName();
                    }
                    //得到属性关联的所有动作(需要去重)
                    dbAttributeAction=attributeActionRepository.findByResourceIdAndAttributeId(monitorId,attribute.getId());
                    if(dbAttributeAction!=null&&dbAttributeAction.size()>0){
                        for(AttributeAction attributeAction:dbAttributeAction){
                            String newActonName=mailActionRepository.findOne(attributeAction.getActionId()).getName();
                            if(!actionNames.contains(newActonName)){
                                actionNames.add(newActonName);
                            }
                        }
                        StringBuffer stringBuffer=new StringBuffer();
                        for(String actionName:actionNames){
                            stringBuffer.append(actionName+",");
                        }
                        //删除最后一个逗号
                        stringBuffer=stringBuffer.delete(stringBuffer.length()-1,stringBuffer.length());
                        actionsOfAttribute= stringBuffer.toString();
                    }
                    //拼接属性关联的阈值字符串
                    attribute.setThreshold(thresholdOfAttributeStart+thresholdOfAttributeMiddle+thresholdOfAttributeEnd);
                    //拼接属性关联的动作字符串
                    attribute.setAction(actionsOfAttribute);
                    newAttributes.add(attribute);
                }
            }
            Page page=new PageImpl(newAttributes);
            Gridable<Attribute> gridable=new Gridable<Attribute>(page);
            gridable.setIdField("id");
            gridable.setCellStringField("attributeCn,threshold,action");
            try {
                UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
            } catch (Exception e) {
                throw new Exception("Json数据转换出错!",e);
            }
        }
    }

    //获得监视器名称，属性名称
    @Get("sub/{resourceType}/{monitorId}/{attribute}")
    public String setHealthOrAvailableForm(@Param("resourceType") String resourceType,@Param("monitorId") String monitorId,
                                           @Param("attribute") String attribute,Invocation inv){
        //获得监视器名称（也就是应用中文名）
        if(ResourceType.APPLICATION.name().equals(resourceType)){
            inv.addModel("monitorName",applicationService.findApplication(monitorId).getCnName());
        }else if(ResourceType.OS.name().equals(resourceType)){
            inv.addModel("monitorName",osRepository.findOne(monitorId).getName());
        }else if(ResourceType.DB.name().equals(resourceType)){
            inv.addModel("monitorName",infoRepository.findOne(monitorId).getName());
        }
        //写回应用id
        inv.getRequest().setAttribute("monitorId",monitorId);
        /*inv.addModel("monitorId",monitorId);*/
        String attributeId="";
        if("Health".equals(attribute)||"Availability".equals(attribute)){
            attributeId= attributeCache.getAttributeId(ResourceType.valueOf(resourceType).name(),attribute);
            inv.getRequest().setAttribute("attributeId",attributeId);
            inv.addModel("attributeName",attributeRepository.findOne(attributeId).getAttributeCn());
        }else {
            //写回属性id
            inv.getRequest().setAttribute("attributeId",attribute);
            /*inv.addModel("attributeId",attributeId);*/
            //获得属性名字
            inv.addModel("attributeName",attributeRepository.findOne(attribute).getAttributeCn());
            attributeId=attribute;
        }
        inv.getRequest().setAttribute("resourceType",resourceType);
        String dbAttributeCnName=attributeRepository.findOne(attributeId).getAttribute();
        if("Health".equals(dbAttributeCnName)){
            return "setHealth";
        }else if("Availability".equals(dbAttributeCnName)){
            return "setAvailable";
        }else {
            //返回包含关联阈值的页面
            return "setAttributeThreshold";
        }

    }

    //获得所有动作,如果当前属性有关联的动作，放入右边框中
    @Post("actions/{monitorId}/{attributeId}")
    public Reply getAllActions(@Param("monitorId") String monitorId,@Param("attributeId") String attributeId,Invocation inv){
        List<MailAction> mailActions= (List<MailAction>) mailActionRepository.findAll();
        if (mailActions!=null){
            JSONArray jsonArray = new JSONArray();
            String jsonActionNames="";
            for (MailAction mailAction : mailActions) {
                JSONObject jsonObject = new JSONObject();
                List<String> severityLevels=new ArrayList<String>();
                severityLevels=attributeActionRepository.findAllSeverityWithCurrentAttribute(monitorId,attributeId,mailAction.getId());
                JSONArray jsonSeverityArray=new JSONArray();
                if(severityLevels!=null&&severityLevels.size()>0){
                    for(String severityLevel:severityLevels){
                        JSONObject jsonSeverityObject=new JSONObject();
                        String severityLevelName=SeverityLevel.CRITICAL.name();
                        if(SeverityLevel.CRITICAL.name().equals(severityLevel)){
                            jsonSeverityObject.put("several","严重");
                        }else if(SeverityLevel.WARNING.name().equals(severityLevel)){
                            jsonSeverityObject.put("several","警告");
                        }else if(SeverityLevel.INFO.name().equals(severityLevel)){
                            jsonSeverityObject.put("several","正常");
                        }
                        jsonSeverityArray.add(jsonSeverityObject);
                    }
                }
                //获取动作关联属性的严重程度
                jsonObject.put("actionSeverity",jsonSeverityArray);
                jsonObject.put("actionId",mailAction.getId());
                jsonObject.put("actionName", mailAction.getName());
                jsonArray.add(jsonObject);
            }
            jsonActionNames = jsonArray.toJSONString();
            return Replys.with(jsonActionNames).as(Json.class);
        }
        return null;
    }

    //通用保存方法
    @Post("save/{monitorId}/{attributeId}")
    public Reply saveConfigEmergency(@Param("monitorId") String monitorId,
                                     @Param("attributeId") String attributeId,Invocation inv){
        //相应的动作的id
        String[] criticalIds=inv.getRequest().getParameterValues("CRITICAL[]");
        String[] warningIds=inv.getRequest().getParameterValues("WARNING[]");
        String[] infoIds=inv.getRequest().getParameterValues("INFO[]");
        String attributeThresholdId=inv.getRequest().getParameter("THRESHOLDID");

        //如果null，说明页面没有这个元素;如果是""或者真实值，说明有这个元素
        if(null==attributeThresholdId){
            //保存属性动作
            return saveAllAttributeActions(criticalIds,warningIds,infoIds,monitorId,attributeId);
            //""说明要删除属性关联的阈值
        }else if("".equals(attributeThresholdId)){
            //查询属性阈值表记录
            AttributeThreshold attributeThreshold=attributeThresholdRepository.findByResourceIdAndAttributeId(monitorId,attributeId);
            if(null!=attributeThreshold){
                if(null!=attributeThresholdId){
                    //删除上面查询的记录
                    attributeThresholdRepository.delete(attributeThreshold);
                }
            }

            //保存属性动作
            return saveAllAttributeActions(criticalIds,warningIds,infoIds,monitorId,attributeId);
        }else if(!StringUtils.isBlank(attributeThresholdId)){
            //查询属性阈值表记录
            AttributeThreshold dbAttributeThreshold=attributeThresholdRepository.findByResourceIdAndAttributeId(monitorId,attributeId);
            if(null!=dbAttributeThreshold){
                //删除上面查询的记录
                attributeThresholdRepository.delete(dbAttributeThreshold);
            }
            //构造数据，入库
            AttributeThreshold newAttributeThreshold=new AttributeThreshold();
            newAttributeThreshold.setResourceId(monitorId);
            newAttributeThreshold.setAttributeId(attributeId);
            newAttributeThreshold.setThresholdId(attributeThresholdId);
            attributeThresholdRepository.save(newAttributeThreshold);
            //保存属性动作
            return saveAllAttributeActions(criticalIds,warningIds,infoIds,monitorId,attributeId);
        }
        return Replys.with("");
    }

    //返回JSON数据，供页面底部刷新属性列表使用
    public String getJsonStringOfMonitorTypeAndId(String monitorId){
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        String jsonStringOfMonitorTypeAndId="";
        String resourceType=resourcesRepository.findOne(monitorId).getResourceType();
        jsonObject.put("resourceTypeAfterUpdate",resourceType);
        jsonObject.put("monitorIdAfterUpdate",monitorId);
        jsonArray.add(jsonObject);
        return jsonArray.toString();
    }

    public Reply saveAllAttributeActions(String[] criticalIds, String[] warningIds, String[] infoIds, String monitorId, String attributeId){
        List<AttributeAction> dbAttributeActions=new ArrayList<AttributeAction>();
        dbAttributeActions=attributeActionRepository.findAllAttributeActionsWithAttributeId(attributeId,monitorId);
        List<AttributeAction> attributeActions=new ArrayList<AttributeAction>();

        if(null==criticalIds&&null==warningIds&&null==infoIds){
            //如果关联的动作为null，那么DB中将这些记录全部删除
            if(null!=dbAttributeActions&&dbAttributeActions.size()>0){
                attributeActionRepository.delete(dbAttributeActions);
            }
            return Replys.with(getJsonStringOfMonitorTypeAndId(monitorId));
        }
        if(criticalIds!=null&&criticalIds.length>0){
            for(String criticalId:criticalIds){
                AttributeAction attributeAction=new AttributeAction();
                attributeAction.setResourceId(monitorId);
                //需要得到属性id
                attributeAction.setAttributeId(attributeId);
                attributeAction.setActionId(criticalId);
                attributeAction.setSeverity(SeverityLevel.CRITICAL);
                attributeActions.add(attributeAction);
            }
        }
        if(warningIds!=null&&warningIds.length>0){
            for(String warningId:warningIds){
                AttributeAction attributeAction=new AttributeAction();
                attributeAction.setResourceId(monitorId);
                //需要得到属性id
                attributeAction.setAttributeId(attributeId);
                attributeAction.setActionId(warningId);
                attributeAction.setSeverity(SeverityLevel.WARNING);
                attributeActions.add(attributeAction);
            }
        }
        if(infoIds!=null&&infoIds.length>0){
            for(String infoId:infoIds){
                AttributeAction attributeAction=new AttributeAction();
                attributeAction.setResourceId(monitorId);
                //需要得到属性id
                attributeAction.setAttributeId(attributeId);
                attributeAction.setActionId(infoId);
                attributeAction.setSeverity(SeverityLevel.INFO);
                attributeActions.add(attributeAction);
            }
        }
        if(attributeActions!=null&&attributeActions.size()>0){
            //如果db中已经有当前属性关联的记录，那么将这些记录全部删除
            if(null!=dbAttributeActions&&dbAttributeActions.size()>0){
                attributeActionRepository.delete(dbAttributeActions);
            }
            //之后，保存当前属性新关联的动作
            for(AttributeAction attributeAction:attributeActions){
                // TODO 到时候需要修改类型的设置
                attributeAction.setActionType(ActionType.MAIL);
                attributeActionRepository.save(attributeAction);
            }
            return Replys.with(getJsonStringOfMonitorTypeAndId(monitorId)).as(Json.class);
        }
        return Replys.with(getJsonStringOfMonitorTypeAndId(monitorId)).as(Json.class);
    }


    @Post("delete/{monitorId}/{attributeId}")
    public Reply deleteHealthConfig(@Param("monitorId") String monitorId,
                                    @Param("attributeId") String attributeId,Invocation inv){
        List<AttributeAction> attributeActions=attributeActionRepository.findByResourceIdAndAttributeId(monitorId,attributeId);
        attributeActionRepository.delete(attributeActions);
        AttributeThreshold attributeThreshold=attributeThresholdRepository.findByResourceIdAndAttributeId(monitorId,attributeId);
        if(attributeThreshold!=null){
            attributeThresholdRepository.delete(attributeThreshold);
        }
        return Replys.with(getJsonStringOfMonitorTypeAndId(monitorId)).as(Json.class);
    }

    @Get("available")
    public String setAvailableForm(Invocation inv){
        return "setAvailable";
    }

    @Post("threshold/{monitorId}/{attributeId}")
    public Reply getAllThresholds(@Param("monitorId") String monitorId, @Param("attributeId") String attributeId, Invocation inv) {
        JSONArray jsonArray = new JSONArray();
        String jsonThresholdNames = "";
        String dbThresholdId = null;
        AttributeThreshold dbAttributeThreshold = attributeThresholdRepository.findByResourceIdAndAttributeId(monitorId, attributeId);
        if (dbAttributeThreshold != null) {
            dbThresholdId = dbAttributeThreshold.getThresholdId();
        }
        List<Threshold> thresholds = (List<Threshold>) thresholdRepository.findAll();
        if (thresholds != null && thresholds.size() > 0) {
            for (Threshold threshold : thresholds) {
                JSONObject jsonObject = new JSONObject();
                String matchThresholdId=threshold.getId();
                if(matchThresholdId.equals(dbThresholdId)){
                    jsonObject.put("alreadySelected", true);
                }else {
                    jsonObject.put("alreadySelected", false);
                }
                jsonObject.put("thresholdId", matchThresholdId);
                //获取应用中文名
                jsonObject.put("thresholdName", threshold.getName());
                jsonArray.add(jsonObject);
            }
            jsonThresholdNames = jsonArray.toJSONString();
            return Replys.with(jsonThresholdNames).as(Json.class);
        }
        return null;
    }


}
