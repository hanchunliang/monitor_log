package com.sinosoft.monitor.agent.instrumentation.matchers;

import com.sinosoft.monitor.org.objectweb.asm.ClassReader;

public class ClassMatcher extends ClassTypeMatcher {
	public ClassMatcher(String className) {
		super(className);
	}

	public boolean matches(ClassReader cr) {
		String loadingClzName = cr.getClassName();
		if (loadingClzName != null) {
			return loadingClzName.matches(this.className);
		}
		return false;
	}
}
