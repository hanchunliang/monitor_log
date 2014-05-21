package com.sinosoft.one.monitor.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class LoginToken implements AuthenticationToken{

	private static final long serialVersionUID = 1L;
	
	private String loginName;
	
	private String password;
	
	public LoginToken(){
	}  
	  
    public LoginToken(String loginName,String password){
        this.loginName=loginName;
        this.password=password;
    }  
	
	public Object getPrincipal() {
		return getLoginName();
	}

	public Object getCredentials() {
		return getPassword();
	}
	
	public void clear() {
		this.loginName = null;
		this.password = null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getName());
		sb.append(" - ");
		sb.append(loginName);
		sb.append(" - ");
		sb.append(password);
		return sb.toString();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setUserName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
