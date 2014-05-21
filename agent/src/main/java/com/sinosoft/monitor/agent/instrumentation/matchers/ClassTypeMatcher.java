package com.sinosoft.monitor.agent.instrumentation.matchers;

import com.sinosoft.monitor.org.objectweb.asm.ClassReader;

public class ClassTypeMatcher
		implements TypeMatcher {
	protected String className;

	public ClassTypeMatcher() {
	}

	public ClassTypeMatcher(String className) {
		this.className = className;
	}

	public boolean matches(ClassReader cr) {
		String loadingClassName = cr.getClassName();
		if (isExcluded(loadingClassName)) {
			return false;
		}
		String superClassName = cr.getSuperName();
		boolean match = superClassName.matches(this.className);

		return match;
	}

	public boolean isExcluded(String loadingClassName) {
		return loadingClassName.matches(this.className);
	}

	public String getClassName() {
		return this.className;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		String thisClassName = getClass().getName();
		sb.append(thisClassName.substring(thisClassName.lastIndexOf('.') + 1));
		sb.append("[").append(this.className).append("]");
		return sb.toString();
	}
}
