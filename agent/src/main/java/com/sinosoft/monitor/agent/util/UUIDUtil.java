package com.sinosoft.monitor.agent.util;

import java.util.UUID;

/**
 * User: morgan
 * Date: 13-8-26
 * Time: 上午10:27
 */
public final class UUIDUtil {

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}

}
