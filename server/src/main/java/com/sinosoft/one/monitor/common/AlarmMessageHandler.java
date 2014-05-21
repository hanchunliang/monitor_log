package com.sinosoft.one.monitor.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.monitor.action.domain.ActionService;
import com.sinosoft.one.monitor.alarm.domain.AlarmService;
import com.sinosoft.one.monitor.alarm.model.Alarm;
import com.sinosoft.one.monitor.attribute.domain.AttributeCache;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.attribute.model.AttributeAction;
import com.sinosoft.one.monitor.resources.domain.ResourcesCache;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.threshold.domain.ThresholdService;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import com.sinosoft.one.monitor.threshold.model.Threshold;

/**
 * 告警信息处理类.
 * User: carvin
 * Date: 13-3-1
 * Time: 下午3:57
 */
@Component
public class AlarmMessageHandler {
	@Autowired
	private AlarmService alarmService;
	@Autowired
	private AttributeCache attributeCache;
	@Autowired
	private ResourcesCache resourcesCache;
	@Autowired
	private ThresholdService thresholdService;
	@Autowired
	private ActionService actionService;
	@Autowired
	private HealthStaCache healthStaCache;
	private static Logger logger = LoggerFactory.getLogger(AlarmMessageHandler.class);
	/**
	 * 处理告警消息
	 * @param messageBase
	 */
	public void doMessage(MessageBase messageBase, String alarmId) {
		ThresholdAlarmParams thresholdAlarmParams = new ThresholdAlarmParams();
		thresholdAlarmParams.alarmSource = messageBase.getAlarmSource();
		thresholdAlarmParams.alarmId = alarmId;
		thresholdAlarmParams.subResourceType = messageBase.getSubResourceType();
		thresholdAlarmParams.subResourceId = messageBase.getSubResourceId();
		List<AlarmAttribute> alarmMessageList = messageBase.getAlarmAttributes();

		String resourceId = messageBase.getResourceId();
		thresholdAlarmParams.resource = resourcesCache.getResource(resourceId);
		if(thresholdAlarmParams.resource == Resource.EMPTY) {
			return;
		}
		thresholdAlarmParams.healthAttributeId = attributeCache.getAttributeId(thresholdAlarmParams.resource.getResourceType(), AttributeName.Health.name());
		for(int i=0, len=alarmMessageList.size(); i<len; i++) {
			AlarmAttribute alarmAttribute = alarmMessageList.get(i);

			if(alarmAttribute.isAvailabilityAlarm()) {

				if(AvailabilityStatus.ERROR.value().equals(alarmAttribute.getAttributeValue())) {
					thresholdAlarmParams.isAvailabilityAlarmError = true;
					thresholdAlarmParams.severityLevel = SeverityLevel.CRITICAL;
					String availabilityAttributeId = attributeCache.getAttributeId(thresholdAlarmParams.resource.getResourceType(), AttributeName.Availability.name());
					thresholdAlarmParams.availabilityAttributeActions = actionService.queryAttributeActions(resourceId, availabilityAttributeId, SeverityLevel.CRITICAL);
					if(thresholdAlarmParams.healthAttributeActions == null) {
						thresholdAlarmParams.healthAttributeActions = actionService.queryAttributeActions(resourceId, thresholdAlarmParams.healthAttributeId, SeverityLevel.CRITICAL);
					}
				} else {
					thresholdAlarmParams.isAvailabilityAlarmNormal = true;
					thresholdAlarmParams.severityLevel = SeverityLevel.INFO;
				}
				break;
			}

			if(alarmAttribute.isExceptionAlarm()) {
				thresholdAlarmParams.isExceptionAlarm = true;
				thresholdAlarmParams.severityLevel = SeverityLevel.CRITICAL;
				String exceptionAttributeId = attributeCache.getAttributeId(thresholdAlarmParams.resource.getResourceType(), AttributeName.Exception.name());
				thresholdAlarmParams.exceptionAttributeActions = actionService.queryAttributeActions(resourceId, exceptionAttributeId, SeverityLevel.CRITICAL);
				break;
			}

			//获取属性
			Attribute attribute = attributeCache.getAttribute(thresholdAlarmParams.resource.getResourceType(), alarmAttribute.getAttributeName());
			if(attribute == Attribute.EMPTY) {
				continue;
			}

			//获取阈值
			Threshold threshold = thresholdService.queryThreshold(resourceId, attribute.getId());
			
			if(threshold == null) {
				continue;
			}
			//获取严重级别
			SeverityLevel severityLevel = threshold.evalSeverityLevel(alarmAttribute.getAttributeValue());

			if(severityLevel == SeverityLevel.UNKNOW) {
				continue;
			}
			if(thresholdAlarmParams.severityLevel == SeverityLevel.UNKNOW || severityLevel.ordinal() < thresholdAlarmParams.severityLevel.ordinal()) {
				thresholdAlarmParams.severityLevel = severityLevel;
			}

			ThresholdAlarmInfo thresholdAlarmInfo = new ThresholdAlarmInfo();
			thresholdAlarmInfo.setThreshold(threshold);
			thresholdAlarmInfo.setAttribute(attribute);

			if(thresholdAlarmParams.severityLevel.ordinal() < SeverityLevel.INFO.ordinal()) {
				if(thresholdAlarmParams.healthAttributeActions == null) {
					thresholdAlarmParams.healthAttributeActions = actionService.queryAttributeActions(resourceId, thresholdAlarmParams.healthAttributeId, severityLevel);
				}
				List<AttributeAction> thresholdAttributeActions = actionService.queryAttributeActions(resourceId, attribute.getId(), severityLevel);
				thresholdAlarmInfo.setThresholdAttributeActions(thresholdAttributeActions);
			}
			thresholdAlarmParams.thresholdAlarmInfos.add(thresholdAlarmInfo);
		}
		doAlarm(thresholdAlarmParams);
	}

	/**
	 * 告警
	 * @param thresholdAlarmParams 参数
	 */
	private void doAlarm(ThresholdAlarmParams thresholdAlarmParams) {

		if(thresholdAlarmParams.severityLevel == SeverityLevel.UNKNOW) {
			return;
		}

		StringBuilder alarmMessageBuilder = new StringBuilder();
		List<AttributeAction> allAttributeActions = new ArrayList<AttributeAction>();
		alarmMessageBuilder.append(thresholdAlarmParams.resource.getResourceName() + "的健康状况为" + thresholdAlarmParams.severityLevel.cnName());
		alarmMessageBuilder.append("<br>根本原因");

		if(thresholdAlarmParams.isAvailabilityAlarmError) {
			alarmMessageBuilder.append("<br> ").append(thresholdAlarmParams.resource.getResourceName());
			if(thresholdAlarmParams.subResourceType != null) {
				Resource subResource = resourcesCache.getResource(thresholdAlarmParams.subResourceId);
				alarmMessageBuilder.append("资源[").append(subResource.getResourceType()).append("][")
						.append(StringUtils.stripToEmpty(subResource.getResourceName())).append("]");
			}
			alarmMessageBuilder.append("不可用");
			if(thresholdAlarmParams.availabilityAttributeActions != null && thresholdAlarmParams.availabilityAttributeActions.size() > 0) {
				allAttributeActions.addAll(thresholdAlarmParams.availabilityAttributeActions);
			}
		} else if(thresholdAlarmParams.isAvailabilityAlarmNormal) {
			alarmMessageBuilder.append("<br> ").append(thresholdAlarmParams.resource.getResourceName());
			if(thresholdAlarmParams.subResourceType != null) {
				Resource subResource = resourcesCache.getResource(thresholdAlarmParams.subResourceId);
				alarmMessageBuilder.append("资源[").append(subResource.getResourceType()).append("][")
						.append(StringUtils.stripToEmpty(subResource.getResourceName())).append("]可用性为可用");
			}
		} else if(thresholdAlarmParams.isExceptionAlarm) {
			alarmMessageBuilder.append("<br> ").append(thresholdAlarmParams.resource.getResourceName()).append("发生异常");
			if(thresholdAlarmParams.exceptionAttributeActions != null && thresholdAlarmParams.exceptionAttributeActions.size() > 0) {
				allAttributeActions.addAll(thresholdAlarmParams.exceptionAttributeActions);
			}
		} else {
			int index = 1;
			if(thresholdAlarmParams.severityLevel == SeverityLevel.INFO) {
				alarmMessageBuilder.append("<br>").append(index++).append(".").append(thresholdAlarmParams.resource.getResourceName())
						.append("可用性为可用.");
			}
			for(ThresholdAlarmInfo thresholdAlarmInfo : thresholdAlarmParams.thresholdAlarmInfos) {
				Threshold threshold = thresholdAlarmInfo.getThreshold();
				Attribute attribute = thresholdAlarmInfo.getAttribute();

				alarmMessageBuilder.append("<br>").append(index++).append(".").append(attribute.getAttributeCn())
						.append(" ").append(threshold.getResultMessage().replaceAll("#U#", attribute.getUnits())).append(" ").append(attribute.getUnits())
						.append(" （阈值）. ");

				if(thresholdAlarmInfo.getThresholdAttributeActions() != null && thresholdAlarmInfo.getThresholdAttributeActions().size() > 0) {
					allAttributeActions.addAll(thresholdAlarmInfo.getThresholdAttributeActions());
				}
			}
		}

		if(thresholdAlarmParams.healthAttributeActions != null && thresholdAlarmParams.healthAttributeActions.size() > 0) {
			allAttributeActions.addAll(thresholdAlarmParams.healthAttributeActions);
		}

		// 保存告警信息
		Alarm alarm = new Alarm(thresholdAlarmParams.alarmId);
		Attribute attribute = attributeCache.getAttribute(thresholdAlarmParams.resource.getResourceType(), AttributeName.Health.name());
		alarm.setAttributeId(attribute.getId());
		alarm.setMonitorId(thresholdAlarmParams.resource.getResourceId());
		alarm.setAlarmSource(thresholdAlarmParams.alarmSource);
		alarm.setMonitorType(thresholdAlarmParams.resource.getResourceType());
		alarm.setSeverity(thresholdAlarmParams.severityLevel);
		alarm.setMessage(alarmMessageBuilder.toString());
		alarm.setCreateTime(new Date());
		alarm.setSubResourceId(thresholdAlarmParams.subResourceId);
		alarm.setSubResourceType(thresholdAlarmParams.subResourceType);

		Alarm prevAlarm = alarmService.queryLatestAlarm(thresholdAlarmParams.resource.getResourceId());
		
		logger.info("保存Alarm数据判断");
		if(thresholdAlarmParams.severityLevel == SeverityLevel.INFO && alarm.isSameAlarmInfo(prevAlarm)) {
			if(System.currentTimeMillis() - prevAlarm.getCreateTime().getTime() < 5 * 60 * 1000) {
				return;
			}
		}
		logger.info("保存Alarm数据");
		
		// 保存告警信息
		alarmService.saveAlarm(alarm);
		if(thresholdAlarmParams.subResourceType != ResourceType.NONE && StringUtils.isNotBlank(thresholdAlarmParams.subResourceId)) {
			healthStaCache.increase(thresholdAlarmParams.resource.getResourceId(), thresholdAlarmParams.subResourceId, thresholdAlarmParams.severityLevel);
		}

		// 处理动作
		if(allAttributeActions != null && allAttributeActions.size() > 0) {
			actionService.doActions(allAttributeActions, thresholdAlarmParams.resource, attribute, thresholdAlarmParams.severityLevel, alarmMessageBuilder.toString());
		}
	}


	private class ThresholdAlarmInfo {
		private Threshold threshold;
		private Attribute attribute;
		private List<AttributeAction> thresholdAttributeActions;

		public Threshold getThreshold() {
			return threshold;
		}

		public void setThreshold(Threshold threshold) {
			this.threshold = threshold;
		}


		public Attribute getAttribute() {
			return attribute;
		}

		public void setAttribute(Attribute attribute) {
			this.attribute = attribute;
		}

		public List<AttributeAction> getThresholdAttributeActions() {
			return thresholdAttributeActions;
		}

		public void setThresholdAttributeActions(List<AttributeAction> thresholdAttributeActions) {
			this.thresholdAttributeActions = thresholdAttributeActions;
		}
	}

	private class ThresholdAlarmParams {
		private String alarmId;
		private Resource resource;
		private AlarmSource alarmSource;
		private boolean isAvailabilityAlarmError;
		private boolean isAvailabilityAlarmNormal;
		private boolean isExceptionAlarm;
		private SeverityLevel severityLevel = SeverityLevel.UNKNOW;
		private String healthAttributeId;
		private ResourceType subResourceType;
		private String subResourceId;
		private List<AttributeAction> healthAttributeActions;
		private List<AttributeAction> availabilityAttributeActions;
		private List<AttributeAction> exceptionAttributeActions;
		private List<ThresholdAlarmInfo> thresholdAlarmInfos = new ArrayList<ThresholdAlarmInfo>();
	}
}
