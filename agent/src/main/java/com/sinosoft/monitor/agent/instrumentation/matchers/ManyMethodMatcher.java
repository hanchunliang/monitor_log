package com.sinosoft.monitor.agent.instrumentation.matchers;

public class ManyMethodMatcher extends MethodMatcher {
	protected MethodMatcher[] matchers;

	public ManyMethodMatcher(MethodMatcher[] matchers) {
		this.matchers = matchers;
	}

	public boolean matches(String name, String description) {
		for (MethodMatcher matcher : this.matchers) {
			if (matcher.matches(name, description)) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		String thisClassName = getClass().getName();
		sb.append(thisClassName.substring(thisClassName.lastIndexOf('.') + 1));
		sb.append("[");
		for (MethodMatcher matcher : this.matchers) {
			sb.append(matcher.toString()).append("|");
		}
		sb.append("]");
		return sb.toString();
	}
}
