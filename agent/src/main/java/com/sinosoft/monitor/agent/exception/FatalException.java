package com.sinosoft.monitor.agent.exception;

public class FatalException extends Exception {
	private static final long serialVersionUID = -3343712404375629537L;

	public FatalException(String message) {
		super(message);
	}

	public FatalException(String message, Throwable cause) {
		super(message, cause);
	}
}
