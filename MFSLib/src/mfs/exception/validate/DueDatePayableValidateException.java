package mfs.exception.validate;

import mfs.exception.ValidateException;

public class DueDatePayableValidateException extends ValidateException {
	private static final long serialVersionUID = 1L;

	public DueDatePayableValidateException(String message, int gatewayCode) {
		super(message, gatewayCode);
	}

	public DueDatePayableValidateException(String message, int gatewayCode,
			Throwable cause) {
		super(message, cause);
		this.gatewayCode = gatewayCode;
	}
}
