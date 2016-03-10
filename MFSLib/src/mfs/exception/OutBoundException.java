package mfs.exception;

import mfs.util.biller.BillerGWRespCode;

public class OutBoundException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Validation occurred on Gateway, So it's gateway code to be set and this
	 * can be mean we may ignored the Outbound Code totally.
	 */
	protected int gatewayCode = BillerGWRespCode.EXTERNAL_ERROR;

	public OutBoundException() {
		super();
	}

	public OutBoundException(String message) {
		super(message);
	}

	public OutBoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public OutBoundException(String message, int gatewayCode) {
		super(message);
		this.gatewayCode = gatewayCode;
	}

	public OutBoundException(String message, int gatewayCode, Throwable cause) {
		super(message, cause);
		this.gatewayCode = gatewayCode;
	}

	public OutBoundException(Throwable cause) {
		super(cause);
	}

	public int getGatewayCode() {
		return gatewayCode;
	}

	public void setGatewayCode(int gatewayCode) {
		this.gatewayCode = gatewayCode;
	}

}
