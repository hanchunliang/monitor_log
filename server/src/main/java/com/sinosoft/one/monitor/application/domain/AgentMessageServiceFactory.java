package com.sinosoft.one.monitor.application.domain;

import com.sinosoft.one.monitor.common.AlarmSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 代理端消息服务类工厂.
 * User: carvin
 * Date: 13-3-4
 * Time: 下午5:16
 */
@Component
public class AgentMessageServiceFactory {
	private final static AgentMessageService EMPTY = new AgentMessageService() {};
	@Autowired
	private LogAgentMessageService logAgentMessageService;
	@Autowired
	private ExceptionAgentMessageService exceptionAgentMessageService;

	public AgentMessageService buildAgentMessageService(String notificationType) {
		if(AlarmSource.LOG.name().equals(notificationType)) {
			return logAgentMessageService;
		}
		if(AlarmSource.EXCEPTION.name().equals(notificationType)) {
			return exceptionAgentMessageService;
		}
		return EMPTY;
	}
 }
