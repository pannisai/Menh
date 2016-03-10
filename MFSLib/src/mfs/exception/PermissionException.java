package mfs.exception;

public class PermissionException extends Exception {
	  public PermissionException() { super(); }
	  public PermissionException(String message) { super(message); }
	  public PermissionException(String message, Throwable cause) { super(message, cause); }
	  public PermissionException(Throwable cause) { super(cause); }
}
