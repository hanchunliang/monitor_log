package com.sinosoft.test.test1;

/**
 * User: morgan
 * Date: 13-8-27
 * Time: 下午6:48
 */
public class Test1 {

	private static int count = 0;

	private String firstName;

	public String getFirstName() {
		System.out.println("call getter");
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		for(int i = 0;i < 10000; i++) {
			add();
		}
		long end = System.currentTimeMillis();
		System.out.println(" time use:" + (end-begin) );
	}

	public static void add() {
		System.out.println("invokeTracker : point:com.sinosoft.monitor.agent.instrumentation.interceptor.MethodInterceptorDefinition  className:com/sinosoft/test/test2/Test2  methodName:add  methodDesc:()V  thisObj:null  args:[Ljava.lang.Object;@677875");
		System.out.println("new DefaultTracker :com/sinosoft/test/test2/Test2  add  1377601728174");
		System.out.println("new BackgroundRootTracker: className:com/sinosoft/test/test2/Test2methName:add");
		count ++;

	}

}
