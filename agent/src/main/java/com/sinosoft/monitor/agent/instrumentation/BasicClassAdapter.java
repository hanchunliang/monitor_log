package com.sinosoft.monitor.agent.instrumentation;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.instrumentation.interceptor.InterceptorDefinition;
import com.sinosoft.monitor.agent.util.InstrumentedClass;
import com.sinosoft.monitor.org.objectweb.asm.ClassVisitor;
import com.sinosoft.monitor.org.objectweb.asm.MethodVisitor;
import com.sinosoft.monitor.org.objectweb.asm.Opcodes;
import com.sinosoft.monitor.org.objectweb.asm.Type;
import com.sinosoft.monitor.org.objectweb.asm.commons.JSRInlinerAdapter;

import java.util.List;
import java.util.logging.Level;

class BasicClassAdapter extends ClassVisitor {
	private String className;
	private List<InterceptorDefinition> interceptors;

	public BasicClassAdapter(ClassVisitor cv, String className,
	                         Class<?> classBeingRedefined,
	                         List<InterceptorDefinition> bestMatchingInterceptors,
	                         List<InterceptorDefinition> otherMatchingInterceptors) {
		super(Opcodes.ASM4,cv);
		this.className = className;
		this.interceptors = (bestMatchingInterceptors.size() > 0 ? bestMatchingInterceptors : otherMatchingInterceptors);
	}

	public MethodVisitor visitMethod(int access, String name, String description, String signature, String[] exceptions) {
		MethodVisitor mv = super.visitMethod(access, name, description, signature, exceptions);

		if (name.matches("<(cl)?init>")) {
			return mv;
		}
		for (InterceptorDefinition interceptor : this.interceptors) {
			if ((interceptor.excludeMethodMatcher != null) && (interceptor.excludeMethodMatcher.matches(name, description))) {
				return mv;
			}
			if (interceptor.includeMethodMatcher.matches(name, description)) {
				JavaAgent.logger.log(Level.FINE, "The method to be instrumented is {0}.{1}{2}",
						new Object[]{this.className, name, description});
				if( !name.startsWith("set") ) {
					BasicMethodAdapter ma = new BasicMethodAdapter(this, this.className, mv, access, name, description, interceptor);
					JSRInlinerAdapter jsrInlinerAdapter = new JSRInlinerAdapter(ma, access, name, description, signature, exceptions);
					return jsrInlinerAdapter;
				}
			}
		}
		return mv;
	}

	public void visitEnd() {
		super.visitEnd();
		this.cv.visitAnnotation(Type.getDescriptor(InstrumentedClass.class), true);
	}
}
