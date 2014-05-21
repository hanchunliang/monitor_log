package com.sinosoft.monitor.agent.instrumentation.matchers;

import com.sinosoft.monitor.org.objectweb.asm.ClassReader;

public class AllClassTypeMatcher extends AnyClassTypeMatcher {
	public AllClassTypeMatcher(ClassTypeMatcher[] matchers) {
		super(matchers);
	}

	public boolean matches(ClassReader cr) {
		for (ClassTypeMatcher typeMatcher : this.matchers) {
			if (!typeMatcher.matches(cr)) {
				return false;
			}
		}
		return true;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		String thisClassName = getClass().getName();
		sb.append(thisClassName.substring(thisClassName.lastIndexOf('.') + 1));
		sb.append("[");
		for (ClassTypeMatcher classMatcher : this.matchers) {
			sb.append(classMatcher.toString()).append("&");
		}
		sb.append("]");
		return sb.toString();
	}
}
