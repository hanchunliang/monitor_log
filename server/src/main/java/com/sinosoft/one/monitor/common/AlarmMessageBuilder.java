package com.sinosoft.one.monitor.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 告警消息构建类.
 * User: carvin
 * Date: 13-3-8
 * Time: 上午10:27
 */
@Component
public class AlarmMessageBuilder {
	@Autowired
	private MessageBaseEventSupport messageBaseEventSupport;

	public MessageBase newMessageBase(String monitorId) {
		MessageBase messageBase = new MessageBase(monitorId);
		messageBase.messageBaseEventSupport(messageBaseEventSupport);
		return messageBase;
	}
}
