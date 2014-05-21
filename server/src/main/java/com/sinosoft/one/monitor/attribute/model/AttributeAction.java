package com.sinosoft.one.monitor.attribute.model;
// Generated 2013-3-1 10:54:16 by One Data Tools 1.0.0


import com.sinosoft.one.monitor.action.model.ActionType;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * AttributeAction.
* 属性动作信息表
 */
@Entity
@Table(name="GE_MONITOR_ATTRIBUTE_ACTION")
public class AttributeAction  implements java.io.Serializable {

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
    * 动作ID.
    */
    private String actionId;
    /**
    * 动作类型.
    */
    private ActionType actionType;
    /**
    * 严重程度.
    */
    private SeverityLevel severity;

    public AttributeAction() {
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
    
    @Column(name="action_id", length=32)
    public String getActionId() {
    return this.actionId;
    }

    public void setActionId(String actionId) {
    this.actionId = actionId;
    }
    
    @Column(name="action_type", length=20)
    @Enumerated(value = EnumType.STRING)
    public ActionType getActionType() {
    return this.actionType;
    }

    public void setActionType(ActionType actionType) {
    this.actionType = actionType;
    }
    
    @Column(name="severity", length=32)
    @Enumerated(value = EnumType.STRING)
    public SeverityLevel getSeverity() {
    return this.severity;
    }

    public void setSeverity(SeverityLevel severity) {
    this.severity = severity;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


