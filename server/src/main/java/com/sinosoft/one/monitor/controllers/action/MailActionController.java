package com.sinosoft.one.monitor.controllers.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.sinosoft.one.monitor.action.domain.MailActionService;
import com.sinosoft.one.monitor.action.model.MailAction;
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

@Path("email")
public class MailActionController {

	private MailActionService mailActionService;

	@Get("list")
	public String list() {
		return "showMotion";
	}
	@Post("data")
	public void list(Invocation inv) throws Exception{
		List<MailAction> mailactionList = mailActionService.getAllMailAction();
		List<MailAction> mailactions = new ArrayList<MailAction>();
		for (MailAction mail : mailactionList) {
			mailactions.add(mail);
		}
		Page<MailAction> page = new PageImpl<MailAction>(mailactions);
		Gridable<MailAction>  gridable = new Gridable<MailAction> (page);
		String cellString = new String(
				"name,fromAddress,toAddress,subject,appendMessage,operation");
		gridable.setIdField("id");
		gridable.setCellStringField(cellString);
		try {
			UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
		} catch (Exception e) {
			throw new Exception("json数据转换出错!", e);
		}
	}
	@Get("create")
	public String create(Invocation inv){
		return "mail";
	}
	
	@Get("update/{id}")
	public String update(@Param("id")String id,Invocation inv){
		inv.addModel("mail", mailActionService.getMailAction(id));
		return "mail";
	}
	@Post("save")
	public Reply save(MailAction action) {
		mailActionService.saveEntity(action);
		return Replys.simple().success();
	}

	@Post("batchDelete/{ids}")
	public Reply batchDelete(@Param("ids") String ids) {
		String str[]=ids.split(",");
        try{
        	for (String string : str) {
        		mailActionService.deleteEntity(string);
    		}
        }catch(Exception e){
			return Replys.simple().fail();
		}
		return Replys.simple().success();
	}

	@Post("delete/{id}")
	public Reply delete(@Param("id") String id, Invocation inv) {
		try{
			mailActionService.deleteEntity(id);
		}catch(Exception e){
			return Replys.simple().fail();
		}
		return Replys.simple().success();
	}
	
	private static String array2String(final String[] params) {
		StringBuilder ids = new StringBuilder();
		for (int i = params.length - 1; i > 0; i--) {
			ids.append(params[i]);
			ids.append(",");
		}
		ids.append(params[0]);
		return ids.toString();
	}

	@Autowired
	public void setMailActionService(MailActionService mailActionService) {
		this.mailActionService = mailActionService;
	}
}
