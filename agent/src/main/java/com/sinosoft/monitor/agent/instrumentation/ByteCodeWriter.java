package com.sinosoft.monitor.agent.instrumentation;

import com.sinosoft.monitor.agent.util.InstrumentationUtil;
import com.sinosoft.monitor.org.objectweb.asm.ClassReader;
import com.sinosoft.monitor.org.objectweb.asm.ClassWriter;

public class ByteCodeWriter extends ClassWriter {
	static final String OBJECT_REPRESENTATION = "java/lang/Object";
	ClassLoader classLoader;

	public ByteCodeWriter(ClassReader classReader, ClassLoader loader) {
		super(classReader, InstrumentationUtil.getClassWriterFlags());
//		super(classReader, COMPUTE_MAXS);
		this.classLoader = loader;
	}

	protected String getCommonSuperClass(String className1, String className2) {
		Class class1;
		Class class2;
		try {
			class1 = Class.forName(className1.replace('/', '.'), false, this.classLoader);
			class2 = Class.forName(className2.replace('/', '.'), false, this.classLoader);
		} catch (Exception th) {
			throw new RuntimeException(th.getMessage());
		}

		if (class1.isAssignableFrom(class2)) {
			return className1;
		}
		if (class2.isAssignableFrom(class1)) {
			return className2;
		}

		if ((class1.isInterface()) || (class2.isInterface())) {
			return OBJECT_REPRESENTATION;
		}
		do {
			class1 = class1.getSuperclass();
		}
		while (!class1.isAssignableFrom(class2));
		return class1.getName().replace('.', '/');
	}
}
