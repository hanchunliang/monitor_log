package com.sinosoft.monitor.agent.logging;

import com.sinosoft.monitor.agent.util.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class LogFormatter extends Formatter {
	Date date;
	private MessageFormat formatter;
	private Object[] args;
	private String lineSeparator;

	public LogFormatter() {
		this.date = new Date();
		this.args = new Object[1];
		this.lineSeparator = System.getProperty("line.separator");
	}

	public synchronized String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
		this.date.setTime(record.getMillis());
		this.args[0] = this.date;
		StringBuffer text = new StringBuffer();
		if (this.formatter == null) {
			this.formatter = new MessageFormat("{0,date} {0,time}");
		}
		this.formatter.format(this.args, text, null);
		sb.append("[").append(text).append("]");
		sb.append("[").append(Thread.currentThread().getName()).append("]");
		sb.append("[").append(record.getLevel().getLocalizedName()).append("]");
		if (record.getLoggerName().equalsIgnoreCase(LoggingConstants.LOGGER_NAME_DV+"-Client")) {
			String className = record.getSourceClassName();
			className = className.substring(className.lastIndexOf('.') + 1);
			String methodName = record.getSourceMethodName();
			if (!StringUtils.isEmptyString(new String[]{className}))
				if (!StringUtils.isEmptyString(new String[]{methodName})) {
					sb.append("(").append(className).append(".").append(methodName).append(")");
				}
		}
		sb.append(":");
		String message = formatMessage(record);
		sb.append(message);
		sb.append(this.lineSeparator);
		if (record.getThrown() != null) {
			try {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				record.getThrown().printStackTrace(pw);
				pw.close();
				sb.append(sw.toString());
			} catch (Exception ex) {
			}
		}
		return sb.toString();
	}
}
