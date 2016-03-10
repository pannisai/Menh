package mfs.exception;

import mfs.util.biller.BillerGWRespCode;

public class ValidateException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Validation occurred on Gateway, So it's gateway code to be set and this
	 * can be mean we may ignored the Outbound Code totally.
	 */
	protected int gatewayCode = BillerGWRespCode.SYSTEM_ERROR;

	public ValidateException() {
		super();
	}

	public ValidateException(String message) {
		super(message);
	}

	public ValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidateException(String message, int gatewayCode) {
		super(message);
		this.gatewayCode = gatewayCode;
	}

	public ValidateException(String message, int gatewayCode, Throwable cause) {
		super(message, cause);
		this.gatewayCode = gatewayCode;
	}

	public ValidateException(Throwable cause) {
		super(cause);
	}

	public int getGatewayCode() {
		return gatewayCode;
	}

	public void setGatewayCode(int gatewayCode) {
		this.gatewayCode = gatewayCode;
	}

}
