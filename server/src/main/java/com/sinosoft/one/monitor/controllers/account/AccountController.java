package com.sinosoft.one.monitor.controllers.account;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.sinosoft.one.monitor.account.domain.AccountService;
import com.sinosoft.one.monitor.account.model.Account;
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

/**
 * @author Administrator
 */
@Path("user")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Get("list")
    public String list() {
        return "userManager";
    }

    @Post("data")
	public void list(Invocation inv) throws Exception{
		List<Account> accountList = accountService.getAllAccount();
		List<Account> accounts = new ArrayList<Account>();
		for (Account account : accountList) {
			account.setAuthflag("普通用户");
			if(account.getAuths()!=null&&account.getAuths().size()>0&&account.getAuths().get(0).getRole().equals("admin")){
				account.setAuthflag("管理员用户");
			}
			accounts.add(account);
		}
		Page<Account> page = new PageImpl<Account>(accounts);
		Gridable<Account>  gridable = new Gridable<Account> (page);
//		名称 发件人 到 主题 一直运行 操作
		String cellString = new String(
				"loginName,name,statusStr,authflag,phone,email,createTimeStr,operation");
		gridable.setIdField("id");
		gridable.setCellStringField(cellString);
		try {
			UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
		} catch (Exception e) {
			throw new Exception("json数据转换出错!", e);
		}
	}
    
    @Get("create")
    @Post("errorCreate")
    public String createForm(Invocation inv) {
        inv.addModel("user", new Account());
        return "adduser";
    }

    @Post("save")
    public Reply save(Account account, Invocation inv) {
    	Account alreadyhas=accountService.findUserByLoginName(account.getLoginName());
    	if (alreadyhas!=null){
    		return Replys.simple().fail("用户名已存在");
    	}
        account.setCreateTime(new Date());
        if("".equals(account.getStatus())||account.getStatus()==null){
        	account.setStatus(String.valueOf(1));
        }
        accountService.saveAccount(account);
        return Replys.simple().success();
    }
    
    @Post("edit")
    public Reply edit(Account account, Invocation inv) {
    	Account alreadyhas=accountService.getAccount(account.getId());
    	alreadyhas.setCreateTime(new Date());
        accountService.updateAccount(alreadyhas,account.getAuthflag());
        return Replys.simple().success();
    }
    
    
    @Get("update/{id}")
	public String update(@Param("id")String id,Invocation inv){
    	Account account= accountService.getAccount(id);
    	account.setAuthflag("1");
    	if(account!=null&&account.getAuths().size()>0){
    		if(account.getAuths().get(0).getRole().toString().equals("admin")){
    			account.setAuthflag("0");
    		}
    	}
		inv.addModel("user", account);
		return "edituser";
	}
    @Post("delete/{id}")
    public Reply delete(@Param("id") String id, Invocation inv) {
        try{
        	accountService.deleteAccount(id);
		}catch(Exception e){
			return Replys.simple().fail();
		}
        return Replys.simple().success();
    }
    
    @Post("batchDelete/{ids}")
    public Reply batchDelete(@Param("ids") String ids) {
    	String str[]=ids.split(",");
        try{
        	for (String string : str) {
        		accountService.deleteAccount(string);
    		}
		}catch(Exception e){
			return Replys.simple().fail();
		}
        return Replys.simple().success();
    }

    @Post("view/{id}")
    public Object view(@Param("id") String id, Invocation inv) {
        Account account = accountService.getAccount(id);
        return Replys.with(account).as(Json.class);
    }
}
