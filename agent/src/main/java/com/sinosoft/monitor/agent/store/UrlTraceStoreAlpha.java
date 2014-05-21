package com.sinosoft.monitor.agent.store;

import com.sinosoft.monitor.agent.store.model.url.UrlTraceLog;

import java.util.List;

/**
 * User: morgan
 * Date: 13-8-22
 * Time: 上午10:08
 */
public class UrlTraceStoreAlpha extends AbstractUrlTraceStore {

	public static final String NAME = "ALPHA";

	private static volatile UrlTraceStoreAlpha singleton = new UrlTraceStoreAlpha();

	@Override
	public String getName() {
		return NAME;
	}

	public static UrlTraceStore getInstence() {
		return singleton;
	}
}
