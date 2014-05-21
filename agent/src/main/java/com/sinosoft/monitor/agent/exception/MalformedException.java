package com.sinosoft.monitor.agent.exception;

public class MalformedException extends Exception {
	private static final long serialVersionUID = -3381124259734948L;

	public MalformedException(String message) {
		super(message);
	}

	public MalformedException(String message, Throwable cause) {
		super(message, cause);
	}
}
