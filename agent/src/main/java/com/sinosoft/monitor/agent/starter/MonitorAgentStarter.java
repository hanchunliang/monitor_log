package com.sinosoft.monitor.agent.starter;

//import com.sun.tools.attach.VirtualMachine;

import java.net.URL;

/**
 * User: morgan
 * Date: 13-8-29
 * Time: 上午11:22
 */
public class MonitorAgentStarter {

	public static void main(String[] args) {

		System.out.println("MonitorAgentStarter ...");

//		if(args.length != 1) {
//			throw new RuntimeException("the args must be two (first is virtual machine 's pid and second is ...)");
//		}
		final String pid = "3428";//args[0];

		System.out.println("pid is : "+pid);

//		URL jarPathUrl = MonitorAgentStarter.class.getProtectionDomain().getCodeSource().getLocation();

		final String jarPath = "G:\\svn_platform\\monitor_agent_client\\target\\monitor_agent_client-3.0-SNAPSHOT.jar";//jarPathUrl.getPath();

		System.out.println("jar path is : "+jarPath);
//		VirtualMachine vm = null;
//		try {
//			vm = VirtualMachine.attach(pid.trim());
//			vm.loadAgent(jarPath);
//			vm.detach();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(vm != null) {
//				try {
//					vm.detach();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}


	}


}
