package com.sinosoft.monitor.agent.util;

import com.sinosoft.monitor.agent.JavaAgent;

import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SequenceURINormalizer {

	private static int options = 10;

	private static Pattern uriPattern = Pattern.compile("([\\/\\w\\-\\.]+[^#?\\s]+)(\\?([^#]*))?(#(.*))?", options);

	private static Pattern staticResourcePattern = Pattern.compile("(.*/)?(.*).(css|js|tiff|gif|png|ico|bmp|jpg|jpeg|webp|svg|svgz)$", options);

	private static Pattern endResourcePattern = Pattern.compile("(/)[^/]*$", options);

	private static Pattern tidyUriMatcherPattern = Pattern.compile("\\d+", options);

	public static String normalizeURI(String uri) {
		JavaAgent.logger.log(Level.FINE, "URI to normalize is {0}", uri);
		if ("/".equals(uri)) {
			return uri;
		}
		uri = getURIPath(uri);
		if (uri.startsWith("/")) {
			uri = uri.substring(uri.indexOf('/') + 1);
		}
		Matcher staticResourceMatcher = staticResourcePattern.matcher(uri);
		if (staticResourceMatcher.matches()) {
			StringBuilder sb = new StringBuilder();
			sb.append(StringUtils.trim(staticResourceMatcher.group(1))).append("*.").append(staticResourceMatcher.group(3));
			uri = sb.toString();
		}
//		Matcher tidyMatcher = tidyUriMatcherPattern.matcher(uri);
//		uri = tidyMatcher.replaceAll("*");
		JavaAgent.logger.log(Level.FINE, "Normalized URI is {0}", uri);
		return uri;
	}

	public static String getURIPath(String uri) {
		Matcher uriMatcher = uriPattern.matcher(uri);
		return uriMatcher.matches() ? uriMatcher.group(1) : uri;
	}
}
