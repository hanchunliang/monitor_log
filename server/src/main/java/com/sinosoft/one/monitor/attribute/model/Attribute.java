package com.sinosoft.one.monitor.attribute.model;
// Generated 2013-3-1 10:54:16 by One Data Tools 1.0.0


import com.sinosoft.one.monitor.common.ResourceType;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

/**
 * Attribute.
* 属性信息表
 */
@Entity
@Table(name="GE_MONITOR_ATTRIBUTE")
public class Attribute  implements java.io.Serializable {

	public final static Attribute EMPTY = new Attribute();

    /**
    * 主键ID.
    */
    private String id = "";
    /**
    * 资源类型.
    */
    private ResourceType resourceType;
    /**
    * 属性.
    */
    private String attribute = "";
	/**
	 * 属性中文名称
	 */
	private String attributeCn = "";
    /**
    * 单位.
    */
    private String units = "";
    /**
     * 阈值.
     */
    private String threshold = "<a href='javascript:void(0)' onclick='setAttributeEmergency(this)'>关联</a>";
    /**
     * 动作.
     */
    private String action = "-";

    public Attribute() {
    }

	
    public Attribute(String id) {
        this.id = id;
    }
   
    @Id 
    
    @Column(name="id", unique=true, length=32)
    public String getId() {
    return this.id;
    }

    public void setId(String id) {
    this.id = id;
    }
    
    @Column(name="resource_type", length=32)
    @Enumerated(value = EnumType.STRING)
    public ResourceType getResourceType() {
    return this.resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
    this.resourceType = resourceType;
    }
    
    @Column(name="attribute")
    public String getAttribute() {
    return this.attribute;
    }

    public void setAttribute(String attribute) {
    this.attribute = attribute;
    }
    
    @Column(name="units", length=10)
    public String getUnits() {
    return StringUtils.stripToEmpty(this.units);
    }

    public void setUnits(String units) {
    this.units = units;
    }

	@Column(name = "attribute_cn", length = 255)
	public String getAttributeCn() {
		return StringUtils.stripToEmpty(attributeCn);
	}

	public void setAttributeCn(String attributeCn) {
		this.attributeCn = attributeCn;
	}

    @Transient
    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    @Transient
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


