package com.sinosoft.one.monitor.common;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.Executors;

/**
 * 告警消息事件支持类
 * User: carvin
 * Date: 13-3-2
 * Time: 上午10:21
 */
@Component
public class MessageBaseEventSupport {
	@Autowired
	private AlarmMessageHandler alarmMessageHandler;
	private int ringSize = 1024;
	private RingBuffer<MessageBaseEvent> ringBuffer;

	public void setRingSize(int ringSize) {
		this.ringSize = ringSize;
	}

	@PostConstruct
	public void init() {
		MessageBaseEventHandler messageBaseEventHandler = new MessageBaseEventHandler();
		messageBaseEventHandler.setAlarmMessageHandler(alarmMessageHandler);
		Disruptor<MessageBaseEvent> disruptor = new Disruptor<MessageBaseEvent>(MessageBaseEvent.EVENT_FACTORY, Executors.newSingleThreadExecutor(),
						new SingleThreadedClaimStrategy(ringSize),
						new BlockingWaitStrategy());
		disruptor.handleEventsWith(messageBaseEventHandler);
		ringBuffer = disruptor.start();
	}

	public void doMessageBase(MessageBase messageBase) {
		long sequence = ringBuffer.next();
		MessageBaseEvent messageBaseEvent = ringBuffer.get(sequence);
		messageBaseEvent.setMessageBase(messageBase);
		String alarmId = messageBase.getAlarmId();
		if(StringUtils.isBlank(alarmId)) {
			alarmId = UUID.randomUUID().toString().replaceAll("-", "");
		}
		messageBaseEvent.setAlarmId(alarmId);
		ringBuffer.publish(sequence);
	}
}
