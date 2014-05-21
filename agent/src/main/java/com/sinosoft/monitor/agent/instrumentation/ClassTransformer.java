package com.sinosoft.monitor.agent.instrumentation;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.instrumentation.interceptor.InterceptorDefinition;
import com.sinosoft.monitor.agent.instrumentation.interceptor.MethodInterceptorDefinition;
import com.sinosoft.monitor.agent.util.InstrumentationUtil;
import com.sinosoft.monitor.org.objectweb.asm.ClassReader;
import com.sinosoft.monitor.org.objectweb.asm.ClassWriter;
import com.sinosoft.monitor.org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ClassTransformer
		implements ClassFileTransformer, Opcodes {
	private final InstrumentationFilter iFilter;

	public ClassTransformer(JavaAgent agent) {
		this.iFilter = new InstrumentationFilter(InterceptorDefinitionFactory.getInterceptorDefinitions());
	}

	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
	                        ProtectionDomain protectionDomain, byte[] classfileBuffer)
			throws IllegalClassFormatException {
		try {
			JavaAgent.logger.finer("Loading Clz: " + className);
			if ((className == null) || (className.startsWith("$")) ||
					className.contains("$$") ||
					(this.iFilter.isExcludedPackage(className))) {

				JavaAgent.logger.log(Level.FINEST, "Loaded class is one of these - proxy, package excluded");
				return null;
			}
			ClassReader reader = new ClassReader(classfileBuffer);
			if ((reader == null) || ((reader.getAccess() & Opcodes.ACC_INTERFACE) != 0)) {//Opcodes.ACC_INTERFACE = 0x0200 接口访问
				JavaAgent.logger.log(Level.FINEST, "reader null or Loaded class is an interface");
				return null;
			}
//			System.out.println(className);
			List matchingInterceptors = this.iFilter.getMatchingInterceptorDefinitions(reader);
			//System.out.println(matchingInterceptors.size());
			if (matchingInterceptors.size() == 0) {
				JavaAgent.logger.log(Level.FINEST, "No matching point cuts for the class {0}", className);
				return null;
			}
			if (matchingInterceptors.size() > 1) {
				matchingInterceptors = chooseInterceptors(matchingInterceptors, reader);
			}
			ClassWriter writer = new ByteCodeWriter(reader, loader);
			try {
				BasicClassAdapter adapter = new BasicClassAdapter(writer, className, classBeingRedefined, matchingInterceptors, null);

				reader.accept(adapter, InstrumentationUtil.getClassReaderFlags());

				JavaAgent.logger.log(Level.INFO, "clz instrumented: {0}", className);
			} catch (Exception ex) {
				JavaAgent.logger.log(Level.WARNING, "Exception while creating BasicClassAdapter for class {0}", className);
				ex.printStackTrace();
				return null;
			}
//			String aaaaa = className.substring(className.lastIndexOf('/'));
//			File file = new File("E:/test/"+aaaaa+".class");
//			FileOutputStream fo = new FileOutputStream(file);
//			fo.write(writer.toByteArray());
//			fo.flush();
//			fo.close();
			return writer.toByteArray();
		} catch (Throwable th) {
			JavaAgent.logger.log(Level.SEVERE, "Exception occurred while transforming", th);
			JavaAgent.logger.warning("Arguments: ClassLoader: " + loader + " className: " + className);
		}
		return null;
	}

	private List<InterceptorDefinition> chooseInterceptors(List<InterceptorDefinition> matchedInterceptors, ClassReader reader) {
		List<InterceptorDefinition> bestMatched = this.iFilter.getBestMatchingInterceptorDefinitions(matchedInterceptors, reader);
		int bestMatchedSize = bestMatched.size();
		if (bestMatchedSize == 0) {
			return matchedInterceptors;
		}

		List<InterceptorDefinition> matchedByUserInputs = new ArrayList(2);
		for (InterceptorDefinition def : bestMatched) {
			if ((def instanceof MethodInterceptorDefinition)) {
				matchedByUserInputs.add(def);
			}
		}
		if (matchedByUserInputs.size() == bestMatchedSize) {
			if (matchedInterceptors.size() == bestMatchedSize) {
				return matchedInterceptors;
			}
			matchedInterceptors.removeAll(matchedByUserInputs);
			JavaAgent.logger.finer("BestMatch interceptors are removed and returning default interceptors");
			return matchedInterceptors;
		}

		bestMatched.removeAll(matchedByUserInputs);
		return bestMatched;
	}
}
