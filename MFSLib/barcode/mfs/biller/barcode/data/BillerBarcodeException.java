package mfs.biller.barcode.data;

public class BillerBarcodeException  extends Exception{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7610261021474324100L;

	public BillerBarcodeException(String msg, Throwable e){
	    super(msg, e);
	}

	
	   public BillerBarcodeException(Throwable ex) {
	        super(ex);
	    }

	    public BillerBarcodeException(String string) {
	        super(string);
	    }
}
