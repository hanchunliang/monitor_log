package com.sinosoft.one.monitor.common;


import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 告警信息接口.
 * User: carvin
 * Date: 13-3-1
 * Time: 下午2:24
 */
public class MessageBase {
	private String resourceId;
	private List<AlarmAttribute> alarmAttributes;
	private AlarmSource alarmSource;
	private ResourceType subResourceType;
	private String subResourceId;
	private MessageBaseEventSupport messageBaseEventSupport;
	private String alarmId;

	MessageBase(String resourceId) {
		Assert.hasText(resourceId);
		this.resourceId = resourceId;
		alarmAttributes = new ArrayList<AlarmAttribute>();
	}

	void messageBaseEventSupport(MessageBaseEventSupport messageBaseEventSupport) {
		this.messageBaseEventSupport = messageBaseEventSupport;
	}

	public String getResourceId() {
		return resourceId;
	}

	public List<AlarmAttribute> getAlarmAttributes() {
		return alarmAttributes;
	}

	public AlarmSource getAlarmSource() {
		return alarmSource;
	}

	public MessageBase alarmSource(AlarmSource alarmSource) {
		this.alarmSource = alarmSource;
		return this;
	}

	public ResourceType getSubResourceType() {
		return subResourceType;
	}

	public MessageBase subResourceType(ResourceType subResourceType) {
		this.subResourceType = subResourceType;
		return this;
	}

	public String getSubResourceId() {
		return subResourceId;
	}

	public MessageBase subResourceId(String subResourceId) {
		this.subResourceId = subResourceId;
		return this;
	}

	public String getAlarmId() {
		return alarmId;
	}

	public MessageBase alarmId(String alarmId) {
		this.alarmId = alarmId;
		return this;
	}

	public MessageBase addAlarmAttribute(AttributeName attributeName, String attributeValue) {
		Assert.notNull(attributeName);
		Assert.hasText(attributeValue);
		alarmAttributes.add(AlarmAttribute.valueOf(attributeName, attributeValue));
		return this;
	}

	public void alarm() {
		messageBaseEventSupport.doMessageBase(this);
	}

}
