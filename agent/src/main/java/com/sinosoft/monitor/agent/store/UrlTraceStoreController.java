package com.sinosoft.monitor.agent.store;

/**
 * User: morgan
 * Date: 13-8-22
 * Time: 上午10:25
 */
public class UrlTraceStoreController {

	private static UrlTraceStore activeUrlTraceStore = UrlTraceStoreAlpha.getInstence();
	/**
	 * 交换不同版本的urlTraceStore
	 */
	public static UrlTraceStore changeUrlTraceStore() {
		UrlTraceStore preStore = activeUrlTraceStore;
		if( UrlTraceStoreAlpha.NAME.equals( activeUrlTraceStore.getName() ) ){
			activeUrlTraceStore = UrlTraceStoreBeta.getInstence();
		} else {

			 activeUrlTraceStore = UrlTraceStoreAlpha.getInstence();
		}

		return preStore;
	}

	/**
	 * 获取当前活动的UrlTraceStore
	 * @return
	 */
	public static UrlTraceStore getUrlTraceStore() {
		return activeUrlTraceStore;
	}

	/**
	 * 获取全部的UrlTraceStore
	 * @return
	 */
	public static UrlTraceStore[] getAllUrlTraceStore() {
		return new UrlTraceStore[] { UrlTraceStoreAlpha.getInstence(), UrlTraceStoreBeta.getInstence() };
	}
}
