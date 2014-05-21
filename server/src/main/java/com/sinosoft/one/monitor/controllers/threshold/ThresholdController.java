package com.sinosoft.one.monitor.controllers.threshold;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.sinosoft.one.monitor.threshold.domain.ThresholdService;
import com.sinosoft.one.monitor.threshold.model.Threshold;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;

@Path("")
public class ThresholdController {

	private ThresholdService thresholdService;

	@Get("list")
	public String list(){
		return "thresholdFile";
	}
	
	@Post("data")
	public void list(Invocation inv) throws Exception {
		List<Threshold> thresholdList = thresholdService.getAllThreshold();
		List<Threshold> thresholds = new ArrayList<Threshold>();
		for (Threshold threshold : thresholdList) {
			thresholds.add(threshold);
		}
		Page<Threshold> page = new PageImpl<Threshold>(thresholds);
		Gridable<Threshold>  gridable = new Gridable<Threshold> (page);
		String cellString = new String(
				"name,criticalThresholdConditionStr,criticalThresholdValue,criticalThresholdMessage,warningThresholdConditionStr,warningThresholdValue,warningThresholdMessage,infoThresholdConditionStr,infoThresholdValue,infoThresholdMessage,operation");
		gridable.setIdField("id");
		gridable.setCellStringField(cellString);
		try {
			UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
		} catch (Exception e) {
			throw new Exception("json数据转换出错!", e);
		}
	}

	@Post("save")
	public Reply save(Threshold entity) {
		thresholdService.saveEntity(entity);
		return Replys.simple().success();
	}
	
	@Get("update/{id}")
	public String update(@Param("id")String id,Invocation inv){
		inv.addModel("threshold", thresholdService.getThreshold(id));
		return "editThreshold";
	}
	@Post("batchDelete/{ids}")
	public Reply batchDelete(@Param("ids") String ids) {
		String str[]=ids.split(",");
        try{
        	for (String string : str) {
        		thresholdService.deleteEntity(string);
    		}
        }catch(Exception e){
			return Replys.simple().fail();
		}
		return Replys.simple().success();
	}

	@Post("delete/{id}")
	public Reply delete(@Param("id") String id, Invocation inv) {
		try{
			thresholdService.deleteEntity(id);
		}catch(Exception e){
			return Replys.simple().fail();
		}
		return Replys.simple().success();
	}

	@Get("create")
	public String create(Invocation inv) {
		return "addThreshold";
	}

//	private static String array2String(final String[] params) {
//		StringBuilder ids = new StringBuilder();
//		for (int i = params.length - 1; i > 0; i--) {
//			ids.append(params[i]);
//			ids.append(",");
//		}
//		ids.append(params[0]);
//		return ids.toString();
//	}

	@Autowired
	public void setThresholdService(ThresholdService thresholdService) {
		this.thresholdService = thresholdService;
	}

}
