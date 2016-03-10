package mfs.exception.validate;

import mfs.exception.ValidateException;

public class BillerLogicValidateException extends ValidateException {
	private static final long serialVersionUID = 1L;

	public BillerLogicValidateException(String message, int gatewayCode) {
		super(message, gatewayCode);
	}

	public BillerLogicValidateException(String message, int gatewayCode,
			Throwable cause) {
		super(message, cause);
		this.gatewayCode = gatewayCode;
	}
}
