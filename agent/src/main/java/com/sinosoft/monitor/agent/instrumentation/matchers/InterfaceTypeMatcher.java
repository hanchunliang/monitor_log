package com.sinosoft.monitor.agent.instrumentation.matchers;

import com.sinosoft.monitor.org.objectweb.asm.ClassReader;

import java.util.Arrays;

public class InterfaceTypeMatcher extends ClassTypeMatcher {
	public InterfaceTypeMatcher(String className) {
		super(className);
	}

	public boolean matches(ClassReader cr) {
		String loadingClassName = cr.getClassName();
		if (isExcluded(loadingClassName)) {
			return false;
		}
		String[] interfaces = cr.getInterfaces();
		boolean match = Arrays.asList(interfaces).contains(this.className);

		return match;
	}
}
