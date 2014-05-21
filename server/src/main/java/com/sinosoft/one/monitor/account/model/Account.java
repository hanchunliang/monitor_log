package com.sinosoft.one.monitor.account.model;
// Generated 2013-9-27 13:57:19 by One Data Tools 1.0.0


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.google.common.base.Strings;

/**
 * Account.

 */
@Entity
@Table(name="GE_MONITOR_ACCOUNT"
)
public class Account  implements java.io.Serializable {

    /**
    * 用户ID.
    */
    private String id;
    /**
    * 登录名.
    */
    private String loginName;
    /**
    * 密码.
    */
    private String password;
    /**
    * 用户名.
    */
    private String name;
    /**
    * 邮箱.
    */
    private String email;
    /**
    * 电话.
    */
    private String phone;
    /**
    * 更新时间.
    */
    private Date createTime;
    /**
    * 状态.
    */
    private String status;
    /**
        */
    private List<Auth> auths = new ArrayList<Auth>(0) ;
    
    
    private String createTimeStr;

    private String authflag;
    
    private String operation="<a  href='javascript:void(0)' onclick='updRow(this)' class='eid'>编辑</a> <a href='javascript:void(0)' class='del' onclick='delRow(this)'>删除</a>";
    
    private String statusStr;
    
    
 

    public Account() {
    }

	
    public Account(String id) {
        this.id = id;
    }
   
    @Id
   	@GeneratedValue(generator = "system-uuid")
   	@GenericGenerator(name = "system-uuid", strategy = "uuid")
   	@Column(name="ID", unique=true, length=32)
    public String getId() {
    return this.id;
    }

    public void setId(String id) {
    this.id = id;
    }
    
    @Column(name="LOGIN_NAME", length=100)
    public String getLoginName() {
    return this.loginName;
    }

    public void setLoginName(String loginName) {
    this.loginName = loginName;
    }
    
    @Column(name="PASSWORD", length=50)
    public String getPassword() {
    return this.password;
    }

    public void setPassword(String password) {
    this.password = password;
    }
    
    @Column(name="NAME", length=50)
    public String getName() {
    return this.name;
    }

    public void setName(String name) {
        this.name = Strings.nullToEmpty(name);
    }
    
    @Column(name="EMAIL", length=100)
    public String getEmail() {
    return this.email;
    }

    public void setEmail(String email) {
        this.email = Strings.nullToEmpty(email);
    }
    
    @Column(name="PHONE", length=30)
    public String getPhone() {
    return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = Strings.nullToEmpty(phone);
    }
    @Temporal(TemporalType.DATE)
    @Column(name="CREATE_TIME", length=7)
    public Date getCreateTime() {
    return this.createTime;
    }

    public void setCreateTime(Date createTime) {
    this.createTime = createTime;
    }
    
    @Column(name="STATUS", length=50)
    public String getStatus() {
    return this.status;
    }

    public void setStatus(String status) {
	    this.status = status;
	    if("1".equals(status)) {
	    	this.statusStr =  "正常";
	    } else if("0".equals(status)) {
	    	this.statusStr = "锁定";
	    }
    }
    @OneToMany(fetch=FetchType.EAGER, mappedBy="account")
    public List<Auth> getAuths() {
    return this.auths;
    }

    public void setAuths(List<Auth> auths) {
    this.auths = auths;
    }
    @Transient
  	public String getStatusStr() {
  		return statusStr;
  	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Transient
	public String getAuthflag() {
		return authflag;
	}


	public void setAuthflag(String authflag) {
		this.authflag = authflag;
	}

	
	
}


