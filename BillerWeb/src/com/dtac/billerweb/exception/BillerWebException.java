package com.dtac.billerweb.exception;
//
public class BillerWebException extends RuntimeException {
	public BillerWebException() {
		super();
	}

	public BillerWebException(String message) {
		super(message);
	}

	public BillerWebException(String message, Throwable cause) {
		super(message, cause);
	}

	public BillerWebException(Throwable cause) {
		super(cause);
	}
}
