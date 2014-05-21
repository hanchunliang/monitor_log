package com.sinosoft.one.monitor.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

public class HostNameUtil {

	public static String getLocalHostIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			try {
				for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
					List<InetAddress> addresses = Collections.list(networkInterface.getInetAddresses());
					if (addresses.size() > 0) {
						for (InetAddress inetAddress : addresses) {
							if ((!inetAddress.isLoopbackAddress()) && (!inetAddress.isLinkLocalAddress())) {
								return inetAddress.getHostAddress();
							}
						}
					}
				}
			} catch (SocketException se) {
			}
		}
		return "localhost";
	}

	public static String getLocalhostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			String osName = ManagementFactory.getOperatingSystemMXBean().getName();
			if ("Linux".equals(osName)) {
//				APMInsightLogger.getLogger().info("Please add a host entry for this machine in /etc/hosts file");
			}
			try {
				for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
					List<InetAddress> addresses = Collections.list(networkInterface.getInetAddresses());
					if (addresses.size() > 0) {
						for (InetAddress inetAddress : addresses) {
							if ((!inetAddress.isLoopbackAddress()) && (!inetAddress.isLinkLocalAddress())) {
								return inetAddress.getHostAddress();
							}
						}
					}
				}
			} catch (SocketException se) {
			}
		}
		return "localhost";
	}
}
