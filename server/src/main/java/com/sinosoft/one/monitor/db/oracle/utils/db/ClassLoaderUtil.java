package com.sinosoft.one.monitor.db.oracle.utils.db;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public final class ClassLoaderUtil {
	
	private ClassLoaderUtil(){}
	
	public static void loadClass(String className){
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static<T> Class<T> getExternalClass(String className){
		try {
			return (Class<T>) getClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	private static ClassLoader getClassLoader() {
		return ClassLoaderUtil.class.getClassLoader();
	}
	
	/**
	 * getStream(String relativePath)的拓展方法
	 * 提供相对于classPath的资源路径，返回属性对象，它是一个散列表
	 */
	public static Properties getProperties(String resource) {
		Properties properties = new Properties();
		try {
			properties.load(getStream(resource));
		} catch (IOException e) {
			throw new RuntimeException("couldn't load properties file '"+ resource + "'", e);
		}
		return properties;
	}
	
	/**
	 * 提供相对于classPath的资源路径，返回文件的输入流
	 * 必须传递资源的相对路径。是相对于classPath的路径。
	 */
	public static InputStream getStream(String relativePath)
			throws MalformedURLException, IOException {
		if (!relativePath.contains("../")) {
			return getClassLoader().getResourceAsStream(relativePath);
		} else {
			return ClassLoaderUtil.getResourceAsStream(relativePath);
		}
	}

	/**
	 * 必须传递资源的相对路径。是相对于classPath的路径。
	 */
	private static InputStream getResourceAsStream(String relativePath)
			throws MalformedURLException, IOException {
		return ClassLoaderUtil.getStream(ClassLoaderUtil.getAbsoluteUrl(relativePath));
	}
	
	private static InputStream getStream(URL url) throws IOException {
		return url!=null ? url.openStream():null;
	}

	/**
	 * 得到本Class所在的ClassLoader的classPath的绝对路径。 URL形式的
	 */
	private static String getAbsoluteClassPath() {
		return ClassLoaderUtil.getClassLoader().getResource("").toString();
	}

	/**
	 *  必须传递资源的相对路径。是相对于classPath的路径。如果需要查找classPath外部的资源，需要使用../来查找
	 *  返回资源的绝对URL
	 */
	public static URL getAbsoluteUrl(String relativePath)
			throws MalformedURLException {
		if (!relativePath.contains("../")) {
			return ClassLoaderUtil.getResource(relativePath);
		}
		String absoluteClassPath = ClassLoaderUtil.getAbsoluteClassPath();
		if (relativePath.substring(0, 1).equals("/")) {
			relativePath = relativePath.substring(1);
		}
		String wildcardString = relativePath.substring(0,relativePath.lastIndexOf("../") + 3);
		relativePath = relativePath.substring(relativePath.lastIndexOf("../") + 3);
		int containSum = ClassLoaderUtil.containSum(wildcardString, "../");
		absoluteClassPath = ClassLoaderUtil.cutLastString(absoluteClassPath, "/", containSum);
		String resourceAbsolutePath = absoluteClassPath + relativePath;
		URL resourceAbsoluteURL = new URL(resourceAbsolutePath);
		return resourceAbsoluteURL;
	}
	/**
	 *  获取资源URL
	 */
	private static URL getResource(String resource) {
		return ClassLoaderUtil.getClassLoader().getResource(resource);
	}
	private static int containSum(String source, String dest) {
		int containSum = 0;
		int destLength = dest.length();
		while (source.contains(dest)) {
			containSum = containSum + 1;
			source = source.substring(destLength);
		}
		return containSum;
	}
	private static String cutLastString(String source, String dest, int num) {
		
		for (int i = 0; i < num; i++) {
			source = source.substring(0,
					source.lastIndexOf(dest, source.length() - 2) + 1);
		}
		return source;
	}

}
