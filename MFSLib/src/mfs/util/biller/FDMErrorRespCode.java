//package mfs.util.biller;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Error Response Code which MFS will used to send back to FDM ( ISOMSG position
// * 39 ). Commonly will be used in case of Exceptions Or Validations which
// * occurred on the Gateway during processing.
// * 
// * Some of the error code may not be used directly but it will passed down by
// * the Biller/GW/FDM code mapping logic.
// * 
// * @author Apichart
// * 
// */
////public class FDMErrorRespCode {
//
//	public static final int SUCCESS = 00;
//	public static final int INVALID_CHANNEL = 11;
//	public static final int INVALID_BILLER = 12;
//	public static final int TRANSACTION_NOT_AUTHORIZED = 13;
//	public static final int INVALID_REF_1 = 14;
//	public static final int INVALID_REF_2 = 15;
//	public static final int INVALID_REF_3 = 16;
//	public static final int INVALID_REF_4 = 17;
//	public static final int INVALID_REF_5 = 18;
//	public static final int INVALID_REF_6 = 19;
//	public static final int REF_MISMATCH = 20;
//	public static final int INVALID_AMOUNT = 21;
//	public static final int INVALID_DUE_DATE = 22;
//	public static final int INVALID_DUE_MONTH = 23;
//	public static final int CANNOT_PAY_ADVANCE = 24;
//	public static final int CANNOT_PAY_PARTIAL = 25;
//	public static final int CANNOT_PAY_EXTRA = 26;
//	public static final int CANNOT_PAY_MORE_MAXIMUM_LIMIT = 27;
//	public static final int CANNOT_PAY_LOWER_MINIMUM_LIMIT = 28;
//	public static final int DATA_NOT_ENOUGH = 29;
//	public static final int SYSTEM_ERROR = 94;
//	public static final int EXTERNAL_ERROR = 95;
//	public static final int SYSTEM_IS_MAINTAIN = 96;
//	public static final int TRANSACTION_IS_DUPLICATION = 97;
//	public static final int TRANSACTION_IS_PROCESSING = 98;
//	public static final int TRANSACTION_TIMEOUT = 99;
//
//	private static final Map<Integer, String> FDM_RESP_MAP;
//	static {
//		final Map<Integer, String> aMap = new HashMap<Integer, String>();
//		aMap.put(SUCCESS, "SUCCESS");
//		aMap.put(INVALID_CHANNEL, "INVALID_CHANNEL");
//		aMap.put(INVALID_BILLER, "INVALID_BILLER");
//		aMap.put(TRANSACTION_NOT_AUTHORIZED, "TRANSACTION_NOT_AUTHORIZED");
//		aMap.put(INVALID_REF_1, "INVALID_REF_1");
//		aMap.put(INVALID_REF_2, "INVALID_REF_2");
//		aMap.put(INVALID_REF_3, "INVALID_REF_3");
//		aMap.put(INVALID_REF_4, "INVALID_REF_4");
//		aMap.put(INVALID_REF_5, "INVALID_REF_5_BARCODE");
//		aMap.put(INVALID_REF_6, "INVALID_REF_6_BARCODE");
//		aMap.put(REF_MISMATCH, "REF_MISMATCH");
//		aMap.put(INVALID_AMOUNT, "INVALID_AMOUNT");
//		aMap.put(INVALID_DUE_DATE, "INVALID_DUE_DATE");
//		aMap.put(INVALID_DUE_MONTH, "INVALID_DUE_MONTH");
//		aMap.put(CANNOT_PAY_ADVANCE, "CANNOT_PAY_ADVANCE");
//		aMap.put(CANNOT_PAY_PARTIAL, "CANNOT_PAY_PARTIAL");
//		aMap.put(CANNOT_PAY_EXTRA, "CANNOT_PAY_EXTRA");
//		aMap.put(CANNOT_PAY_MORE_MAXIMUM_LIMIT, "CANNOT_PAY_MORE_MAXIMUM_LIMIT");
//		aMap.put(CANNOT_PAY_LOWER_MINIMUM_LIMIT,
//				"CANNOT_PAY_LOWER_MINIMUM_LIMIT");
//		aMap.put(DATA_NOT_ENOUGH, "DATA_NOT_ENOUGH");
//		aMap.put(SYSTEM_ERROR, "SYSTEM_ERROR");
//		aMap.put(EXTERNAL_ERROR, "EXTERNAL_ERROR");
//		aMap.put(SYSTEM_IS_MAINTAIN, "SYSTEM_IS_MAINTAIN");
//		aMap.put(TRANSACTION_IS_DUPLICATION, "TRANSACTION_IS_DUPLICATION");
//		aMap.put(TRANSACTION_IS_PROCESSING, "TRANSACTION_IS_PROCESSING");
//		aMap.put(TRANSACTION_TIMEOUT, "TRANSACTION_TIMEOUT");
//
//		FDM_RESP_MAP = Collections.unmodifiableMap(aMap);
//	}
//
//	public static String getRespName(final int errorCode) {
//		return FDM_RESP_MAP.get(errorCode);
//	}
//
//	public static Map<Integer, String> getRespMap() {
//		return FDM_RESP_MAP;
//	}
//}
