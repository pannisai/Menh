package mfs.exception.validate;

import mfs.exception.ValidateException;

public class StringPatternValidateException extends ValidateException {

	private static final long serialVersionUID = 1L;

	public StringPatternValidateException(String message, int gatewayCode) {
		super(message, gatewayCode);
	}

	public StringPatternValidateException(String message, int gatewayCode,
			Throwable cause) {
		super(message, cause);
		this.gatewayCode = gatewayCode;
	}
}
