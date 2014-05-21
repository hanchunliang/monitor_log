package com.sinosoft.one.monitor.action.domain;

import com.sinosoft.one.monitor.action.model.MailAction;
import com.sinosoft.one.monitor.action.model.MailInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邮件服务类
 * User: carvin
 * Date: 13-3-1
 * Time: 下午8:30
 */
@Component
public class MailService {
	private JavaMailSender mailSender;
	private String template;
	private FreeMarkerConfigurer freeMarkerConfigurer;

	private static Logger logger = LoggerFactory.getLogger(MailService.class);

	/**
	 * 发送邮件
	 * @param mailInfo 邮件信息
	 */
	public void sendMail(MailInfo mailInfo) {
		// 发送邮件

		Map<String, String> map = new HashMap<String, String>();

		map.put("monitorName", mailInfo.getMonitorName());
		map.put("attributeName", mailInfo.getAttributeName());
		map.put("cause", mailInfo.getCause());
		map.put("rootCause", mailInfo.getRootCause());

		String appendMessage = null;
		List<MimeMessage> messageList = new ArrayList<MimeMessage>();
		for (MailAction mailAction : mailInfo.getMailActionList()) {
			if(appendMessage == null) {
				map.put("appendMessage", mailAction.getAppendMessage());
			}

			String content = generateMailContent(template, map);
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper;
			try {
				helper = new MimeMessageHelper(message, true, "utf-8");
				helper.setFrom(mailAction.getFromAddress().trim());
				helper.setTo(mailAction.getToAddress().split("[,;]"));
				helper.setSubject(mailInfo.getTitle());
				helper.setText(content, true);
				messageList.add(message);
			} catch (MessagingException e) {
				logger.error("create MimeMessage exception.", e);
			}

		}
		mailSender.send(messageList.toArray(new MimeMessage[0]));
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	private String generateMailContent(String templateName,
	                                    Map<String, String> map) {
		try {
			Template temp = freeMarkerConfigurer.getConfiguration()
					.getTemplate(templateName);
			return FreeMarkerTemplateUtils.processTemplateIntoString(temp, map);
		} catch (TemplateException e) {
			logger.error("get mail template exception.", e);
		} catch (IOException e) {
			logger.error("get mail template exception.", e);
		}
		return StringUtils.EMPTY;
	}
}
