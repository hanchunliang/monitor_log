package com.sinosoft.monitor.agent.instrumentation;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.instrumentation.interceptor.InterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.matchers.AnyClassTypeMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.ClassMatcher;
import com.sinosoft.monitor.org.objectweb.asm.ClassReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class InstrumentationFilter {
	private final List<InterceptorDefinition> interceptorDefns;
	private Pattern packageExclusionPattern;
	public Pattern classInclusionPattern;

	public InstrumentationFilter(List<InterceptorDefinition> interceptorDefns) {
		this.interceptorDefns = interceptorDefns;
		initialize();
	}

	public void initialize() {
		String[] packageFilters = {"javax/.*", "java/.*", "sun/.*", "org/w3c/dom/.*",
				"org/xml/sax/.*", "com/sun/.*", "org/jboss/.*", "org/apache/jasper/.*",
				"com/sinosoft/monitor/.*", "org/springframework/.*"};

		StringBuilder sb = new StringBuilder();
		for (String pkgName : packageFilters) {
			sb.append(pkgName).append("|");
		}
		this.packageExclusionPattern = Pattern.compile(sb.toString());
		JavaAgent.logger.info("Package exclusion pattern to be used: " + this.packageExclusionPattern.pattern());
	}

	public boolean isExcludedPackage(String className) {
		return className.matches(this.packageExclusionPattern.pattern());
	}

	public List<InterceptorDefinition> getMatchingInterceptorDefinitions(ClassReader cr) {

		List matchingInterceptors = new ArrayList(1);
		for (InterceptorDefinition intercepDef : this.interceptorDefns) {
			if ((intercepDef.classTypeMatcher != null) && (intercepDef.classTypeMatcher.matches(cr))) {
				matchingInterceptors.add(intercepDef);
			}
		}
		return matchingInterceptors;
	}

	public List<InterceptorDefinition> getBestMatchingInterceptorDefinitions(List<InterceptorDefinition> matchingInterceptors, ClassReader reader) {
		List bestMatches = new ArrayList();
		for (InterceptorDefinition intercepDef : matchingInterceptors) {
			if (((intercepDef.classTypeMatcher instanceof ClassMatcher)) && (intercepDef.classTypeMatcher.matches(reader))) {
				bestMatches.add(intercepDef);
			} else if ((intercepDef.classTypeMatcher instanceof AnyClassTypeMatcher)) {
				AnyClassTypeMatcher anyClassType = (AnyClassTypeMatcher) intercepDef.classTypeMatcher;
				if (anyClassType.hasExactClassMatches(reader)) {
					bestMatches.add(intercepDef);
				}
			}
		}
		return bestMatches;
	}
}
