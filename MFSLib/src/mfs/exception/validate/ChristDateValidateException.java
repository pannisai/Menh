package mfs.exception.validate;

import mfs.exception.ValidateException;

public class ChristDateValidateException extends ValidateException {

	private static final long serialVersionUID = 1L;

	public ChristDateValidateException(String message, int gatewayCode) {
		super(message, gatewayCode);
	}

	public ChristDateValidateException(String message, int gatewayCode,
			Throwable cause) {
		super(message, cause);
		this.gatewayCode = gatewayCode;
	}
}
