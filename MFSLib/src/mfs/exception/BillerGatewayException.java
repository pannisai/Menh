package mfs.exception;

import mfs.util.biller.BillerGWRespCode;

public abstract class BillerGatewayException extends Exception {
	private static final long serialVersionUID = 1L;

	private int gatewayCode = BillerGWRespCode.SYSTEM_ERROR;

	public BillerGatewayException() {
		super();
	}

	public BillerGatewayException(String message) {
		super(message);
	}

	public BillerGatewayException(String message, Throwable cause) {
		super(message, cause);
	}

	public BillerGatewayException(String message, int gatewayCode) {
		super(message);
		this.gatewayCode = gatewayCode;
	}

	public BillerGatewayException(String message, int gatewayCode,
			Throwable cause) {
		super(message, cause);
		this.gatewayCode = gatewayCode;
	}

	public BillerGatewayException(Throwable cause) {
		super(cause);
	}

	public int getGatewayCode() {
		return gatewayCode;
	}

	public void setGatewayCode(int gatewayCode) {
		this.gatewayCode = gatewayCode;
	}

}
