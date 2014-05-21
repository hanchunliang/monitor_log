package com.sinosoft.test.test2;

/**
 * User: morgan
 * Date: 13-8-27
 * Time: 下午6:48
 */
public class Test2 {

	private static int count = 0;

	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		for(int i = 0;i < 10000; i++) {
			add();
		}
		long end = System.currentTimeMillis();
		System.out.println(" time use:" + (end-begin) );
	}

	public static void add() {
		count ++;
	}

}
