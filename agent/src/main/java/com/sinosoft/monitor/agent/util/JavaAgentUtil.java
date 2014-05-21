package com.sinosoft.monitor.agent.util;

import com.sinosoft.monitor.agent.JavaAgent;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class JavaAgentUtil {
	public static void print(String text) {
		try {
			getDefaultPrintStream().println(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PrintStream getDefaultPrintStream() {
		return System.out;
	}

	public static File getAgentInstallDirectory() throws RuntimeException {
		String home = System.getProperty("apminsight.home");
		if (home != null) {
			return new File(home);
		}
		File agentInstallDir = getAgentJarDirPath();
		if (agentInstallDir != null) {
			home = agentInstallDir.getAbsolutePath();
			System.setProperty("apminsight.home", home);
			return agentInstallDir;
		}
		return null;
	}

	public static File getAgentJarDirPath() throws RuntimeException {
		try {
			URL agentJarUrl = getAgentJarUrl();
			if (agentJarUrl != null) {
				File file = new File(new URI(agentJarUrl.toExternalForm()));
				if (file.exists()) {
					return file.getParentFile();
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return null;
	}

	public static URL getAgentJarUrl()
			throws RuntimeException {
		ClassLoader classLoader = JavaAgent.class.getClassLoader();
		if ((classLoader instanceof URLClassLoader)) {
			URL[] urls = ((URLClassLoader) classLoader).getURLs();
			for (URL url : urls) {
				if (url.getFile().endsWith("monitor_agent.jar")) {
					return url;
				}

			}

			String agentClassName = JavaAgent.class.getName().replace('.', '/') + ".class";
			for (URL url : urls) {
				JarFile jarFile = null;
				try {
					jarFile = new JarFile(url.getFile());
					ZipEntry entry = jarFile.getEntry(agentClassName);
					if (entry != null) {
						URL localURL1 = url;
						return localURL1;
					}

					if (jarFile != null)
						try {
							jarFile.close();
						} catch (IOException e) {
						}
				} catch (IOException e) {
					if (jarFile != null)
						try {
							jarFile.close();
						} catch (IOException ee) {
						}
				} finally {
					if (jarFile != null) {
						try {
							jarFile.close();
						} catch (IOException e) {
						}
					}
				}
			}
		}

		return null;
	}
}
