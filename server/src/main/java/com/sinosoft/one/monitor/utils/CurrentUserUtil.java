package com.sinosoft.one.monitor.utils;

import com.sinosoft.one.monitor.account.model.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-2-27
 * Time: 下午8:40
 * To change this template use File | Settings | File Templates.
 */
public class CurrentUserUtil {

    public static Account getCurrentUser(){
        Subject currentUser= SecurityUtils.getSubject();
        Account account= (Account) currentUser.getPrincipals().getPrimaryPrincipal();
        return account;
    }
}
