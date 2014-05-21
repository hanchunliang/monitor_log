package com.sinosoft.monitor.agent.config;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.service.ApplicationService;
import com.sinosoft.monitor.agent.util.StringUtils;
import com.sinosoft.monitor.facade.ApplicationStore;

import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InterestedMethodsStore {
	private Map<String, List<String>> store = null;

	private static final Pattern INPUT_SYNTAX = Pattern.compile("([a-zA-Z_](\\w)*(\\/[a-zA-Z_](\\w)*)*):((([a-zA-Z_](\\w)*))(\\,[a-zA-Z_](\\w)*)*)?");

	private static final List<String> ALL_METHODS = Collections.emptyList();

	InterestedMethodsStore(String initSource) {
        //ApplicationService.init(javaAgentConfig);
	}

	public void reinitialize(String initSource) {
//		initialize(initSource);
	}

//	private void initialize(String initSource) {
//		if (StringUtils.isEmptyString(new String[]{initSource})) {
//			throw new IllegalArgumentException("invalid initialize param");
//		}
//		String[] fragments = initSource.split(";");
//		Map tempStore = new HashMap(fragments.length);
//		for (String entry : fragments) {
//			Matcher m = INPUT_SYNTAX.matcher(entry);
//			if (m.matches()) {
//				String className = m.group(1);
//				String methodNames = m.group(5);
//				if (!StringUtils.isEmptyString(new String[]{methodNames})) {
//					List methNames = Arrays.asList(methodNames.split(","));
//					tempStore.put(className, methNames);
//				} else {
//					tempStore.put(className, ALL_METHODS);
//				}
//			}
//		}
//		this.store = tempStore;
//	}

	public final boolean isExists(String className, String methodName) {

//		List methodNames = (List) this.store.get(className);
        List methodNames = ApplicationStore.getMethods(className);
		if (methodNames == null) {
            //JavaAgent.logger.log(Level.INFO,"InterestedMethodsStore check method is exists,the class name is {0}:{1},return false",new Object[]{className,methodName});
			return false;
		}
//		if (ALL_METHODS == methodNames) {
//			return true;
//		}
        boolean isExist = methodNames.contains(methodName);
        if (isExist){
            JavaAgent.logger.log(Level.INFO,"InterestedMethodsStore check method is exists,method {0}:{1} is exists",new Object[]{className,methodName});
        }
		return isExist;
	}

	public void clean() {
//		this.store.clear();
//		JavaAgent.logger.info("InterestedMethodsStore cleaned successfully");
	}

	public int getOccupiedSize() {
		//return this.store.size();
        return ApplicationStore.getSize();
	}

	public int getSize() {
		return ApplicationStore.getSize();
	}

	public void destroy() {
//		this.store = null;
//		JavaAgent.logger.info("InterestedMethodsStore destroyed successfully");
	}

	protected void finalize() {
	}

	public String toString() {
		return ApplicationStore.toMethodStoreString();
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("clone not supported for this store.");
	}

	public static void main(String[] args) {
//		InterestedMethodsStore i = new InterestedMethodsStore("com/xcompany/xproject/xmodule/DataCollector:doWork,checkTask;summa_dummy_hehehe");
	}
}
