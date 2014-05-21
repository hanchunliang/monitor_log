package com.sinosoft.monitor.agent.instrumentation.interceptor.ejb;

import com.sinosoft.monitor.agent.instrumentation.interceptor.TracingInterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.matchers.*;

public class EjbInterceptorDefinition extends TracingInterceptorDefinition {
	private static final String METHOD_SIGN = "()V".intern();

	public EjbInterceptorDefinition() {
		super(EjbInterceptorDefinition.class.getName(),
				new AnyClassTypeMatcher(new ClassTypeMatcher[]
						{new InterfaceTypeMatcher("javax/ejb/EntityBean"),
								new InterfaceTypeMatcher("javax/ejb/SessionBean"),
								new InterfaceTypeMatcher("javax/ejb/MessageDrivenBean")}),
				new ManyMethodMatcher(new MethodMatcher[]
						{new MethodMatcher("ejbActivate", METHOD_SIGN),
								new MethodMatcher("ejbPassivate", METHOD_SIGN),
								new MethodMatcher("ejbLoad", METHOD_SIGN),
								new MethodMatcher("ejbStore", METHOD_SIGN),
								new MethodMatcher("ejbRemove", METHOD_SIGN),
								new MethodMatcher("setEntityContext", "(Ljavax/ejb/EntityContext;)V"),
								new MethodMatcher("unsetEntityContext", METHOD_SIGN),
								new MethodMatcher("setMessageDrivenContext", "(Ljavax/ejb/MessageDrivenContext;)V")}),
				new MethodMatcher("/.*/", "/.*/"));
	}

	public String getInterceptorName() {
		return EjbInterceptorDefinition.class.getName();
	}

	public String getComponentName() {
		return EJB;
	}
}
