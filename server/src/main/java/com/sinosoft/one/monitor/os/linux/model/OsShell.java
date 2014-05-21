package com.sinosoft.one.monitor.os.linux.model;
// Generated 2013-2-27 21:43:48 by One Data Tools 1.0.0


import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * OsShell.

 */
@Entity
@Table(name="GE_MONITOR_OS_SHELL"
)
public class OsShell  implements java.io.Serializable {

    /**
        */
    private String id;
    /**
        */
    private String type;
    /**
        */
    private String template;

    public OsShell() {
    }

	
    public OsShell(String id) {
        this.id = id;
    }
   
    @Id 
    @Column(name="ID", unique=true, length=32)
    @GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    public String getId() {
    return this.id;
    }

    public void setId(String id) {
    this.id = id;
    }
    
    @Column(name="TYPE", length=2)
    public String getType() {
    return this.type;
    }

    public void setType(String type) {
    this.type = type;
    }
    
    @Column(name="TEMPLATE")
    public String getTemplate() {
    return this.template;
    }

    public void setTemplate(String template) {
    this.template = template;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


