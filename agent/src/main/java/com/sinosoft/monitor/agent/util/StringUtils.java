package com.sinosoft.monitor.agent.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public static final String EMPTY_STRING = "";

	public static boolean isEmptyString(String[] strings) {
		for (String s : strings) {
			if ((s == null) || (s.trim().equals("")) || (s.trim().equalsIgnoreCase("null"))) {
				return true;
			}
		}
		return false;
	}

	public static String formatString(String s, Pattern pattern, Object[] values) {
		StringBuffer sb = new StringBuffer();
		Matcher m = pattern.matcher(s);
		while (m.find()) {
			int idx = -1;
			idx = Integer.parseInt(m.group(1));
			m.appendReplacement(sb, "{" + idx + "}");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static String trim(String s) {
		if (s == null) {
			return "";
		}
		return s.trim();
	}

	public static long getAsLong(String s) {
		return isEmptyString(new String[]{s}) ? -1L : Long.parseLong(s);
	}

	public static int getAsInteger(String s) {
		return isEmptyString(new String[]{s}) ? -1 : Integer.parseInt(s);
	}

	public static float getAsFloat(String s) {
		return isEmptyString(new String[]{s}) ? -1.0F : Float.parseFloat(s);
	}

	public static String getAsCSV(Object[] objects, boolean quote) {
		if ((objects == null) || (objects.length == 0)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object o : objects) {
			if (o != null) {
				if (quote) {
					wrapWithQuote(sb, o.toString());
				} else {
					sb.append(o.toString());
				}
				sb.append(',');
			}
		}
		removeLastChar(sb, ',');
		return sb.toString();
	}

	public static void wrapWithQuote(StringBuilder sb, String s) {
		sb.append('\'').append(s).append('\'');
	}

	public static void removeLastChar(StringBuilder sb, char c) {
		if (sb.charAt(sb.length() - 1) == c) {
			sb.deleteCharAt(sb.length() - 1);
		}
	}

	public static Long[] getAsLongArrayFromCSV(String data) {
		return convertToLongArray(getAsArrayFromCSV(data));
	}

	public static Long[] convertToLongArray(String[] dataItems) {
		Long[] longArray = new Long[dataItems.length];
		int itemsIterator = 0;
		for (String dataItem : dataItems) {
			longArray[(itemsIterator++)] = Long.valueOf(Long.parseLong(dataItem));
		}
		return longArray;
	}

	public static String[] getAsArrayFromCSV(String data) {
		return data.split(",");
	}

	public static String[] convertToStringArray(Long[] dataItems) {
		String[] stringArray = new String[dataItems.length];
		int itemsIterator = 0;
		for (Long dataItem : dataItems) {
			stringArray[(itemsIterator++)] = String.valueOf(dataItem);
		}
		return stringArray;
	}
}
