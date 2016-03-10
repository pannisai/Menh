package mfs.exception.validate;

import mfs.exception.ValidateException;

public class BuddhaDateValidateException extends ValidateException {
	private static final long serialVersionUID = 1L;

	public BuddhaDateValidateException(String message, int gatewayCode) {
		super(message, gatewayCode);
	}

	public BuddhaDateValidateException(String message, int gatewayCode,
			Throwable cause) {
		super(message, cause);
		this.gatewayCode = gatewayCode;
	}
}
