package com.sinosoft.monitor.agent.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkerThreadFactory
		implements ThreadFactory {
	static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

	final AtomicInteger threadNumber = new AtomicInteger(1);
	final String namePrefix;
	final boolean createDaemonThread;

	public WorkerThreadFactory(String namePart) {
		this(namePart, false);
	}

	public WorkerThreadFactory(String namePart, boolean createDaemonThread) {
		if (namePart == null) {
			namePart = "worker";
		}
		this.namePrefix = (namePart + "-" + POOL_NUMBER.getAndIncrement() + "-thread-");
		this.createDaemonThread = createDaemonThread;
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, this.namePrefix + this.threadNumber.getAndIncrement());
		t.setDaemon(this.createDaemonThread);

		if (t.getPriority() != 5) {
			t.setPriority(5);
		}
		return t;
	}
}
