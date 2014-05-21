package com.sinosoft.one.monitor.attribute.model;
// Generated 2013-3-1 10:54:16 by One Data Tools 1.0.0


import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * AttributeThreshold.
* 属性阈值信息表
 */
@Entity
@Table(name="GE_MONITOR_ATTRIBUTE_THRESHOLD"
)
public class AttributeThreshold  implements java.io.Serializable {

    /**
    * 主键ID.
    */
    private String id;
    /**
    * 资源ID.
    */
    private String resourceId;
    /**
    * 属性ID.
    */
    private String attributeId;
    /**
    * 阈值ID.
    */
    private String thresholdId;

    public AttributeThreshold() {
    }

   
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name="id", unique=true, length=32)
    public String getId() {
    return this.id;
    }

    public void setId(String id) {
    this.id = id;
    }
    
    @Column(name="resource_id", length=32)
    public String getResourceId() {
    return this.resourceId;
    }

    public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
    }
    
    @Column(name="attribute_id", length=32)
    public String getAttributeId() {
    return this.attributeId;
    }

    public void setAttributeId(String attributeId) {
    this.attributeId = attributeId;
    }
    
    @Column(name="threshold_id", length=32)
    public String getThresholdId() {
    return this.thresholdId;
    }

    public void setThresholdId(String thresholdId) {
    this.thresholdId = thresholdId;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


