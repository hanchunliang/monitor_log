package com.sinosoft.monitor.agent.store;

/**
 * User: morgan
 * Date: 13-8-22
 * Time: 上午10:15
 */
public class UrlTraceStoreBeta extends AbstractUrlTraceStore {

	public static final String NAME = "BETA";

	private static volatile UrlTraceStoreBeta singleton = new UrlTraceStoreBeta();

	@Override
	public String getName() {
		return NAME;
	}

	public static UrlTraceStore getInstence(){
		return singleton;
	}
}
