package com.sinosoft.monitor.agent;

import com.sinosoft.monitor.agent.util.StringUtils;

import java.util.regex.Pattern;

public class ExceptionMessages extends StringUtils {
	public static final Pattern EXCEPTION_FORMAT_PATTERN = Pattern.compile("\\{(\\d)\\}");
	public static final String UNKNOWNEXCEPTION = "Some error occurred";
	public static final String HTTPEXCEPTION = "HttpException:Message \" {0} \"received with response code {1}";
	public static final String SERVEREXCEPTION = "{0} occurred in the server with message \"{1}\"";

	public static String getMessage(String message, Object[] msgFragments) {
		return formatString(message, EXCEPTION_FORMAT_PATTERN, msgFragments);
	}
}
