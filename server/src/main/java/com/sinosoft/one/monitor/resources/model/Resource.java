package com.sinosoft.one.monitor.resources.model;
// Generated 2013-3-1 10:54:16 by One Data Tools 1.0.0


import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Resource.
* 资源表
 */
@Entity
@Table(name="GE_MONITOR_RESOURCES"
)
public class Resource implements java.io.Serializable {

    /**
    * 资源ID.
    */
    private String resourceId = "";
    /**
    * 资源名称.
    */
    private String resourceName = "";
    /**
    * 资源类型.
    */
    private String resourceType = "";

    public Resource() {
    }

	
    public Resource(String resourceId, String resourceType) {
        this.resourceId = resourceId;
        this.resourceType = resourceType;
    }
   
    @Id 
    @Column(name="resource_id", unique=true, length=32)
    public String getResourceId() {
    return this.resourceId;
    }

    public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
    }
    
    @Column(name="resource_name", length=300)
    public String getResourceName() {
    return this.resourceName;
    }

    public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
    }
    
    @Column(name="resource_type", length=100)
    public String getResourceType() {
    return this.resourceType;
    }

    public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public static Resource EMPTY = new Resource();

}


