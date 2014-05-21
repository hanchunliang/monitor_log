package com.sinosoft.one.monitor.account.model;
// Generated 2013-9-27 13:57:19 by One Data Tools 1.0.0


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * Auth.
* 用户授权表
 */
@Entity
@Table(name="GE_MONITOR_ACCOUNT_AUTH"
)
public class Auth  implements java.io.Serializable {

    /**
    * 授权id.
    */
    private String id;
    /**
    * 用户ID.
    */
    private Account account;
    /**
    * 角色.
    */
    private String role;

    public Auth() {
    }

	
    public Auth(String id) {
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
    @ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ACCOUNT_ID")
    public Account getAccount() {
    return this.account;
    }

    public void setAccount(Account account) {
    this.account = account;
    }
    
    @Column(name="ROLE", length=10)
    public String getRole() {
    return this.role;
    }

    public void setRole(String role) {
    this.role = role;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


