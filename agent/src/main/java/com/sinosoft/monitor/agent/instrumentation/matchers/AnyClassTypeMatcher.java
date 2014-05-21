package com.sinosoft.monitor.agent.instrumentation.matchers;

import com.sinosoft.monitor.org.objectweb.asm.ClassReader;

public class AnyClassTypeMatcher extends ClassTypeMatcher {
	protected ClassTypeMatcher[] matchers;

	public AnyClassTypeMatcher(ClassTypeMatcher[] matchers) {
		this.matchers = matchers;
	}

	public boolean matches(ClassReader cr) {
		for (ClassTypeMatcher matcher : this.matchers) {
			if (matcher.matches(cr)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasExactClassMatches(ClassReader cr) {
		for (ClassTypeMatcher classMatcher : this.matchers) {
			if (((classMatcher instanceof ClassMatcher)) && (classMatcher.matches(cr))) {
				return true;
			}
		}
		return false;
	}
}
