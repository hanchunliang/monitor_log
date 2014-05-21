package com.sinosoft.monitor.agent.instrumentation.interceptor.hibernate;

import com.sinosoft.monitor.agent.instrumentation.interceptor.TracingInterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.matchers.ClassMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.ManyMethodMatcher;
import com.sinosoft.monitor.agent.instrumentation.matchers.MethodMatcher;

public class HibernateSessionImplInterceptor extends TracingInterceptorDefinition {
	private static final String STRING_SERIALIZ_OBJ = "(Ljava/lang/String;Ljava/io/Serializable;)Ljava/lang/Object;";
	private static final String STRING_OBJ_SERIALIZ_VOID = "(Ljava/lang/String;Ljava/lang/Object;Ljava/io/Serializable;)V";
	private static final String STRING_OBJ_VOID = "(Ljava/lang/String;Ljava/lang/Object;)V";
	private static final String STRING_SERIALIZ_LOCKMODE_OBJ = "(Ljava/lang/String;Ljava/io/Serializable;Lorg/hibernate/LockMode;)Ljava/lang/Object";
	private static final String LOAD = "load";

	public HibernateSessionImplInterceptor() {
		super(HibernateSessionImplInterceptor.class.getName(),
				new ClassMatcher("org/hibernate/impl/SessionImpl"), null,
				new ManyMethodMatcher(new MethodMatcher[]
						{new MethodMatcher("get", "(Ljava/lang/String;Ljava/io/Serializable;)Ljava/lang/Object;"),
								new MethodMatcher("load", "(Ljava/lang/String;Ljava/io/Serializable;)Ljava/lang/Object;"),
								new MethodMatcher("save", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/io/Serializable;"),
								new MethodMatcher("update", "(Ljava/lang/String;Ljava/lang/Object;)V"),
								new MethodMatcher("saveOrUpdate", "(Ljava/lang/String;Ljava/lang/Object;)V"),
								new MethodMatcher("saveOrUpdate".intern(), "(Ljava/lang/Object;)V"),
								new MethodMatcher("delete", "(Ljava/lang/Object;)V"),
								new MethodMatcher("list", "(Lorg/hibernate/impl/CriteriaImpl;)Ljava/util/List;"),
								new MethodMatcher("save".intern(), "(Ljava/lang/String;Ljava/lang/Object;Ljava/io/Serializable;)V"),
								new MethodMatcher("update".intern(), "(Ljava/lang/String;Ljava/lang/Object;Ljava/io/Serializable;)V"),
								new MethodMatcher("get", "(Ljava/lang/String;Ljava/io/Serializable;Lorg/hibernate/LockMode;)Ljava/lang/Object"),
								new MethodMatcher("load", "(Ljava/lang/Object;Ljava/io/Serializable;)V"),
								new MethodMatcher("load", "(Ljava/lang/String;Ljava/io/Serializable;Lorg/hibernate/LockMode;)Ljava/lang/Object"),
								new MethodMatcher("delete", "(Ljava/lang/String;Ljava/lang/Object;)V")}));
	}

	public String getComponentName() {
		return HIBERNATE;
	}
}
