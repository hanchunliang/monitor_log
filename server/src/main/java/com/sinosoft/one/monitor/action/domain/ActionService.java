package com.sinosoft.one.monitor.action.domain;

import com.google.common.collect.Lists;
import com.sinosoft.one.monitor.action.model.ActionType;
import com.sinosoft.one.monitor.action.model.MailAction;
import com.sinosoft.one.monitor.action.model.MailInfo;
import com.sinosoft.one.monitor.action.model.SmsAction;
import com.sinosoft.one.monitor.action.repository.MailActionRepository;
import com.sinosoft.one.monitor.action.repository.SmsActionRepository;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.attribute.model.AttributeAction;
import com.sinosoft.one.monitor.attribute.repository.AttributeActionRepository;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 处理动作业务逻辑类.
 * User: carvin
 * Date: 13-3-1
 * Time: 上午11:30
 */
@Service
public class ActionService {
	@Autowired
	private MailActionRepository mailActionRepository;
	@Autowired
	private SmsActionRepository smsActionRepository;
	@Autowired
	private AttributeActionRepository attributeActionRepository;
	@Autowired
	private MailService mailService;



	/**
	 * 根据邮件ID列表查询邮件列表
	 * @param ids 邮件ID列表
	 * @return 邮件列表
	 */
	public Iterable<MailAction> queryMailActions(Set<String> ids) {
		return mailActionRepository.findAll(ids);
	}

	/**
	 * 根据短信ID列表查询短信列表
	 * @param ids 短信ID列表
	 * @return 短信列表
	 */
	public Iterable<SmsAction> querySmsActions(Set<String> ids) {
		return smsActionRepository.findAll(ids);
	}

	/**
	 * 根据资源ID、属性ID、严重程度查询动作信息
	 * @param resourceId 资源ID
	 * @param attributeId 属性ID
	 * @param severity 严重程度
	 * @return 动作信息
	 */
	public List<AttributeAction> queryAttributeActions(String resourceId, String attributeId, SeverityLevel severity) {
		return attributeActionRepository.findByResourceIdAndAttributeIdAndSeverity(resourceId, attributeId, severity);
	}

	/**
	 * 根据资源ID、属性ID、严重程度查询动作信息
	 * @param resourceId 资源ID
	 * @param attributeId 属性ID
	 * @return 动作信息
	 */
	public List<AttributeAction> queryAttributeActions(String resourceId, String attributeId) {
		return attributeActionRepository.findByResourceIdAndAttributeId(resourceId, attributeId);
	}

	/**
	 * 处理动作
	 * @param attributeActionList 属性动作列表
	 * @param resource 资源
	 * @param attribute 属性
	 * @param severityLevel 严重级别
	 * @param message 错误原因消息
	 */
	public void doActions(List<AttributeAction> attributeActionList, Resource resource,
	                       Attribute attribute,
	                       SeverityLevel severityLevel,
	                       String message) {

		Set<String> mailActionIds = new HashSet<String>();
		Set<String> smsActionIds = new HashSet<String>();

		for(AttributeAction attributeAction : attributeActionList) {
			if(attributeAction.getActionType() == ActionType.MAIL) {
				mailActionIds.add(attributeAction.getActionId());
			} else if(attributeAction.getActionType() == ActionType.SMS) {
				smsActionIds.add(attributeAction.getActionId());
			}
		}

		if(mailActionIds.size() > 0) {
			String cause = resource.getResourceName() + "的健康状况为" + severityLevel.cnName();
			String title = "来自监控系统的告警 - [ "+ cause + " ]";

			Iterable<MailAction> mailActions = mailActionRepository.findAll(Lists.newArrayList(mailActionIds));
			MailInfo mailInfo = new MailInfo();
			mailInfo.setCause(cause);
			mailInfo.setTitle(title);
			mailInfo.setAttributeName(attribute.getAttributeCn());
			mailInfo.setMonitorName(resource.getResourceName());
			mailInfo.setRootCause(message);
			for(MailAction mailAction : mailActions) {
				mailInfo.addMailAction(mailAction);
			}
			mailService.sendMail(mailInfo);
		}

		if(smsActionIds.size() > 0) {
			//TODO 发送短信
		}
	}
}
