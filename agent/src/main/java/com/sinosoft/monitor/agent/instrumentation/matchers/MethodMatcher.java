package com.sinosoft.monitor.agent.instrumentation.matchers;

import com.sinosoft.monitor.agent.JavaAgent;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class MethodMatcher
		implements TypeMatcher {
	protected String methodName;
	protected String[] methodDescription;
	private static final String IS_REGEX = "/.*/";

	public MethodMatcher() {
	}

	public MethodMatcher(String methodName, String methodDescription) {
		this(methodName, new String[]{methodDescription});
	}

	public MethodMatcher(String methodName, String[] methodDescription) {
		this.methodName = (isRegex(methodName) ? extractPattern(methodName) : Pattern.quote(methodName));
		this.methodDescription = new String[methodDescription.length];
		for (int i = 0; i < methodDescription.length; i++) {
			String description = methodDescription[i];
			this.methodDescription[i] = (isRegex(description) ? extractPattern(description) : Pattern.quote(description));
		}
	}

	public boolean isRegex(String s) {
		return s.matches("/.*/");
	}

	public String extractPattern(String regex) {
		return regex.substring(1, regex.length() - 1);
	}

	public boolean matches(String name, String description) {
		JavaAgent.logger.log(Level.FINEST, "MethodMatcher Matching {0}{1} with {2}{3} ",
				new Object[]{name, description, this.methodName, Arrays.asList(this.methodDescription)});
		boolean methodNameMatches = name.matches(this.methodName);
		if (!methodNameMatches) {
			return false;
		}
		JavaAgent.logger.finest("Method name matches");
		for (String desc : this.methodDescription) {
			if ((desc != null) && (description.matches(desc))) {
				JavaAgent.logger.finest("Method description matches");
				return true;
			}
		}
		return false;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		String thisClassName = getClass().getName();
		sb.append(thisClassName.substring(thisClassName.lastIndexOf('.') + 1));
		sb.append("[").append(this.methodName).append(" ").append(Arrays.asList(this.methodDescription)).append("]");
		return sb.toString();
	}
}
