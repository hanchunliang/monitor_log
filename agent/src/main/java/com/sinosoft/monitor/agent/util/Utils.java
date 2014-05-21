package com.sinosoft.monitor.agent.util;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

public class Utils {
	public static final Object[] EMPTY_ARR = new Object[0];

	private static final String[] TABS = {"", "\t", "\t\t", "\t\t\t", "\t\t\t\t", "\t\t\t\t\t"};

	public static String tabs(int noOfTabs) {
		return TABS[noOfTabs];
	}

	public static String getContentAsString(String filepath)
			throws IOException {
		StringBuffer buff = new StringBuffer();
		BufferedReader buffReader = null;
		try {
			buffReader = new BufferedReader(new FileReader(filepath));
			String line;
			while ((line = buffReader.readLine()) != null) {
				if (!line.startsWith("#")) {
					buff.append(line);
				}
			}
			return buff.toString();
		} finally {
			if (buffReader != null)
				try {
					buffReader.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	public static Properties getContentAsProps(File file)
			throws IOException {
		Properties props = new Properties();
		InputStream bi = null;
		try {
			bi = new BufferedInputStream(new FileInputStream(file));
			props.load(bi);
		} finally {
			if (bi != null)
				try {
					bi.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

		}
		return props;
	}

	public static void writePropsAsFile(Properties props, String filePath) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			props.store(fos, "Agent info get from the server. !! Do not edit or delete this file. !!");
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

		}
	}

	public static <T> T[] arrayCopy(T[] src) {
		Object[] dest = (Object[]) Array.newInstance(src.getClass().getComponentType(), src.length);
		System.arraycopy(src, 0, dest, 0, src.length);
		return (T[]) dest;
	}

	public static int[] arrayCopy(int[] src) {
		int[] dest = new int[src.length];
		System.arraycopy(src, 0, dest, 0, src.length);
		return dest;
	}

	public static boolean[] arrayCopy(boolean[] src) {
		boolean[] dest = new boolean[src.length];
		System.arraycopy(src, 0, dest, 0, src.length);
		return dest;
	}

	public static Object[] mergeArrays(Object[] array1, Object[] array2) {
		array1 = array1 != null ? array1 : EMPTY_ARR;
		array2 = array2 != null ? array2 : EMPTY_ARR;
		Object[] newArr = new Object[array1.length + array2.length];
		System.arraycopy(array1, 0, newArr, 0, array1.length);
		System.arraycopy(array2, 0, newArr, array1.length, array2.length);
		return newArr;
	}

	public static Object[] spliceArray(Object[] src, int srcPos, int length) {
		Object[] splice = new Object[length];
		System.arraycopy(src, srcPos, splice, 0, length);
		return splice;
	}

	public static <T> void writeElements(ObjectOutput out, Collection<T> collection)
			throws IOException {
		out.writeInt(collection.size());
		for (Iterator i$ = collection.iterator(); i$.hasNext(); ) {
			Object element = i$.next();

			out.writeObject(element);
		}
	}

	public static String findOperation(String uri, String patStr) {
		int beginIndex = uri.indexOf(patStr) + patStr.length();
		int endIndex = uri.indexOf('?') == -1 ? uri.length() : uri.indexOf('?', beginIndex);
		String operation = uri.substring(beginIndex, endIndex);
		return operation;
	}

	public static boolean isAllNulls(Object[] row, int rowLength) {
		int nullCount = 0;
		for (int i = 0; i < row.length; i++) {
			if (row[i] == null) {
				nullCount++;
			}
		}
		return nullCount == row.length;
	}
}
