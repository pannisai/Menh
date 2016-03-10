package mfs.biller.barcode.validate;

import mfs.exception.ValidateException;

public class CheckDigitException extends ValidateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7463112273833068908L;

	public CheckDigitException(String msg, Throwable e) {
		super(msg, e);
	}

	public CheckDigitException(Throwable ex) {
		super(ex);
	}

	public CheckDigitException(String string) {
		super(string);
	}

	public CheckDigitException(String message, int gatewayCode) {
		super(message, gatewayCode);
	}

	public CheckDigitException(String message, int gatewayCode, Throwable cause) {
		super(message, cause);
		this.gatewayCode = gatewayCode;
	}

}
