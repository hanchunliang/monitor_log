package com.sinosoft.monitor.agent.sequence;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.agent.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SequenceFilterByUrl {
	public static void initialize(String regEx) {
		SequenceFilterByUrlExtension.initialize(regEx);
	}

	public static void reInitialize(String regEx) {
		initialize(regEx);
	}

	public static boolean isSkip(String url) {
		return SequenceFilterByUrlExtension.isSkip(url);
	}

	static class SequenceFilterByUrlExtension {
		static Set<String> xtensions = Collections.emptySet();

		static void initialize(String regEx) {
			if (StringUtils.isEmptyString(new String[]{regEx})) {
				return;
			}
			String[] temp = regEx.replaceAll("\\s*\\*.", "").split(",");
			if (temp.length != 0) {
				xtensions = new HashSet(Arrays.asList(temp));
				//JavaAgent.logger.info("SequenceFilterByUrlExtension initilized with values: " + xtensions);
			}
		}

		static void reInitialize(String regEx) {
			initialize(regEx);
		}

		static boolean isSkip(String url) {
			int position = url.lastIndexOf('.');
			if (position == -1)
				return false;
			try {
				return xtensions.contains(url.substring(position + 1));
			} catch (IndexOutOfBoundsException e) {
			}
			return false;
		}
	}
}
