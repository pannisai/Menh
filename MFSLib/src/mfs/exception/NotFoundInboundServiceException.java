package mfs.exception;

public class NotFoundInboundServiceException extends Exception {
	  public NotFoundInboundServiceException() { super(); }
	  public NotFoundInboundServiceException(String message) { super(message); }
	  public NotFoundInboundServiceException(String message, Throwable cause) { super(message, cause); }
	  public NotFoundInboundServiceException(Throwable cause) { super(cause); }
}
