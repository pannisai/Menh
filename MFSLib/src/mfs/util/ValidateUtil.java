package mfs.util;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

//import mfs.bcm.validation.bean.BaseValidationBean;

import org.apache.log4j.Logger;

public class ValidateUtil {
	
	final private static Logger _log = Logger.getLogger(ValidateUtil.class);

	private static final Pattern NUMERIC_PATTERN = Pattern.compile("^[0-9]+$");
	private static final Pattern EMAIL_PATTERN = Pattern
			.compile("^[a-zA-Z0-9_\\.\\-]+@[a-zA-Z0-9_\\.\\-]+\\.[a-zA-Z]{2,4}$");
	private static final Pattern SCRIPT_PATTERN = Pattern.compile(
			"\\<script\\>.*\\<\\/script\\>", Pattern.CASE_INSENSITIVE);
	private static final Pattern NO_SPECIAL_CHAR_PATTERN = Pattern
			.compile(".*[!#$&*=\';/|\"<>].*");
	private static final Pattern ID_PATTERN = Pattern
			.compile(".*[!#$&*=\\-\';/|\"<>].*");
	private static final Pattern DATE_SYS_PATTERN = Pattern
			.compile("\\d{4}-\\d{2}-\\d{2} \\d{1,2}:\\d{2}:\\d{2}");

	public final static int TEXT_NUMBER_ONLY = 1;
	public final static int TEXT_NO_SPECIAL_CHARS = 2;
	public final static int TEXT_ID = 3;
	public final static int TEXT_ALL = 4;

	public static boolean isValidNumeric(String param) {
		return NUMERIC_PATTERN.matcher(param).matches();
	}

	public static boolean isValidIDCardCheckSum(String param) {
		if (param == null || param.length() < 13) {
			return false;
		}
		int sum = 0;
		for (int i = 0; i < 12; i++) {
			sum += Integer.parseInt("" + param.charAt(i)) * (13 - i);
		}
		return Integer.parseInt("" + param.charAt(12)) == ((11 - (sum % 11)) % 10);
	}

	public static boolean checkText(String param, int textType) {
		switch (textType) {
		case TEXT_NUMBER_ONLY:
			return isValidNumeric(param);
		case TEXT_NO_SPECIAL_CHARS:
			return !NO_SPECIAL_CHAR_PATTERN.matcher(param).matches();
		case TEXT_ID:
			return !ID_PATTERN.matcher(param).matches();
		case TEXT_ALL:
			return true;
		default:
			return false;
		}
	}

	public static boolean isEmpty(String param) {
		if ((param == null) || param.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isValidEmail(String param, boolean optional) {
		return EMAIL_PATTERN.matcher(param).matches();
	}

	public static boolean hasJavaScript(String param, boolean optional) {
		if (param == null) {
			return false;
		}
		return SCRIPT_PATTERN.matcher(param).matches();
	}

	public static boolean isDateSysFormat(String param) {
		if (param == null) {
			return false;
		}
		return DATE_SYS_PATTERN.matcher(param).matches();
	}

	public static boolean isValidPattern(String pattern, String value) {
		return (Pattern.matches(pattern, value));
	}

	public static boolean isValidDateTime(String inDate, String format) {

		return isValidDateTime(inDate, format, Locale.US);
	}

	public static boolean isValidDateTime(String inDate, String format,
			Locale locale) {
		if (inDate == null) {
			return false;
		}

		// set the format to use as a constructor argument
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, locale);
		if (inDate.trim().length() != dateFormat.toPattern().length()) {
			return false;
		}

		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (Exception pe) {
			return false;
		}
		return true;
	}

	public static boolean isValidBuddhaDateTime(String inDate, String format) {

		return isValidDateTime(inDate, format, DateTimeUtil.LOCALE_TH);
	}

	public static boolean isValidChristDateTime(String inDate, String format) {

		return isValidDateTime(inDate, format, Locale.US);
	}
	
	public static boolean isValidDueDate(String dueDate, String format) {
		
		String[] arrs = format.split(",");
		
		if(null!= arrs && 3 == arrs.length){
		
			String dateFormat = arrs[0];
			String year = arrs[1];
			
			int payableDays;
			try {
				payableDays = Integer.parseInt(arrs[2]);
			} catch (Exception ex) {
				payableDays = 0;
			}
			
			Locale lc =null ;
			
			if( "BE".equals(year)){
				lc = DateTimeUtil.LOCALE_TH;
			}else{
				lc = Locale.US;
			}
	
			_log.debug(String.format("Duedate[%s(%s)] payableDays[%d]", dueDate,
					dateFormat, payableDays));
	
			return DateTimeUtil.checkDueDate(dueDate, payableDays, dateFormat,lc);
		
		}else{
			return false;
		}
	}
	
//	public static BaseValidationBean populateToBaseValidationBean(FundamoRequestBean fundamoBean){
//		if( fundamoBean == null ){
//			return null;
//		}
//		
//		BaseValidationBean bean = new BaseValidationBean();
//		
//		bean.setBillerId(fundamoBean.getBillerID());
//		bean.setReference1(fundamoBean.getRef1());
//		bean.setReference2(fundamoBean.getRef2());
//		bean.setReference3(fundamoBean.getRef3());
//		bean.setReference4(fundamoBean.getRef4());
//		bean.setReference5(fundamoBean.getRef5());
//		bean.setReference6(fundamoBean.getRef6());
//		
//		return bean;
//	}
	
}
