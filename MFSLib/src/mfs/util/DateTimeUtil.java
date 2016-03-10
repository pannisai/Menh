package mfs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mfs.exception.ValidateException;
import mfs.exception.validate.DueDatePayableValidateException;
import mfs.util.biller.BillerGWRespCode;

import org.apache.log4j.Logger;

public class DateTimeUtil {
	final private static Logger _log = Logger.getLogger(DateTimeUtil.class);
	public final static Locale LOCALE_TH = new Locale("th", "TH");

	private static boolean isValidDateTime(String inDate, String format) {
		if (inDate == null) {
			return false;
		}

		// set the format to use as a constructor argument
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
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

	public static Date getDateTimeFromISO8583(String datetime) throws Exception {
		SimpleDateFormat sdfYear = new SimpleDateFormat("yy", Locale.US);
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyMMddHHmmss",
				Locale.US);

		String year = sdfYear.format(new Date());
		if (!isValidDateTime(year + datetime, "yyMMddHHmmss")) {
			throw new Exception("Invalid DataTime");
		}
		return sdfDateTime.parse(year + datetime);
	}

	public static String getDateTime(Date date, String format) throws Exception {
		SimpleDateFormat sdfDateTime = new SimpleDateFormat(format, Locale.US);
		return sdfDateTime.format(date);
	}
	
	public static Date getDateTime(String date, String format) throws Exception {
		SimpleDateFormat sdfDateTime = new SimpleDateFormat(format, Locale.US);
		return sdfDateTime.parse(date);
	}

	public static String getDateStringForTag120Resp(Date date) {

		return new SimpleDateFormat("yyyyMMdd", Locale.US).format(date);

	}

	/**
	 * Check DueDate with allowed payableDays. Raise exception if today is not
	 * acceptable.
	 * 
	 * @param dueDate
	 * @param payableDays
	 * @param dateFormat
	 * @param locale
	 * @throws ValidateException
	 */
	public static void checkDueDateWithToday(String dueDate, int payableDays,
			String dateFormat, Locale locale) throws ValidateException {

		try {
			// If Today is after dueDate + payableDays = Invalid Payment.
			final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat,
					locale);

			// Make formatter not accept, something like 123455, which will
			// round to 121057 in BD Year.
			formatter.setLenient(false);

			// Cutoff time within current date.
			final Calendar todayCal = Calendar.getInstance();
			todayCal.setTime(formatter.parse(formatter.format(new Date())));

			final Calendar payableDateCal = Calendar.getInstance();
			payableDateCal.setTime(formatter.parse(dueDate));
			payableDateCal.add(Calendar.DATE, payableDays);

			if (todayCal.after(payableDateCal)) {
				throw new DueDatePayableValidateException(
						"Today is too late to make this payment. Today is ["
								+ formatter.format(todayCal.getTime())
								+ "] Last day is ["
								+ formatter.format(payableDateCal.getTime())
								+ "]. dueDate[" + dueDate + "] + payableDays["
								+ payableDays + "]",
						BillerGWRespCode.INVALID_DUE_DATE);
			}

		} catch (ParseException e) {

			throw new ValidateException("Check DueDate: Invalid DueDate["
					+ dueDate + "] format [" + dateFormat + "]",
					BillerGWRespCode.INVALID_DUE_DATE);

		}
	}
	
	
	public static Date covertBillerDueDate(String dueDate, String format) {
		
		String[] arrs = format.split(",");
		try {
			// If Today is after dueDate + payableDays = Invalid Payment.

		
			if(null!= arrs && 3 == arrs.length){
			
				String dateFormat = arrs[0];
				String year = arrs[1];
								
				Locale lc =null ;
				
				if( "BE".equals(year)){
					lc = DateTimeUtil.LOCALE_TH;
				}else{
					lc = Locale.US;
				}
				
				final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat,lc);
				_log.debug(String.format("Duedate[%s] dateformat[%s]", dueDate,
						dateFormat));
				
				return formatter.parse(dueDate);
			}
		} catch (Exception e) {

		

//			throw new ValidateException("Check DueDate: Invalid DueDate["
//					+ dueDate + "] format [" + dateFormat + "]",
//					GWErrorRespCode.INVALID_DUE_DATE);

		}
		return null;
	}
	
	
	
	public static boolean checkDueDate(String dueDate, int payableDays,
			String dateFormat, Locale locale){

		try {
			// If Today is after dueDate + payableDays = Invalid Payment.
			final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat,
					locale);

			// Make formatter not accept, something like 123455, which will
			// round to 121057 in BD Year.
			formatter.setLenient(false);

			// Cutoff time within current date.
			final Calendar todayCal = Calendar.getInstance();
			todayCal.setTime(formatter.parse(formatter.format(new Date())));

			final Calendar payableDateCal = Calendar.getInstance();
			payableDateCal.setTime(formatter.parse(dueDate));
			payableDateCal.add(Calendar.DATE, payableDays);

			if (todayCal.after(payableDateCal)) {
				
				_log.debug("Today is too late to make this payment. Today is ["
								+ formatter.format(todayCal.getTime())
								+ "] Last day is ["
								+ formatter.format(payableDateCal.getTime())
								+ "]. dueDate[" + dueDate + "] + payableDays["
								+ payableDays + "] code =" + 
						BillerGWRespCode.INVALID_DUE_DATE);
				
				return false;
				
//				throw new DueDatePayableValidateException(
//						"Today is too late to make this payment. Today is ["
//								+ formatter.format(todayCal.getTime())
//								+ "] Last day is ["
//								+ formatter.format(payableDateCal.getTime())
//								+ "]. dueDate[" + dueDate + "] + payableDays["
//								+ payableDays + "]",
//						GWErrorRespCode.INVALID_DUE_DATE);
			}
			
			return true;

		} catch (ParseException e) {
		
			
			_log.debug("Check DueDate: Invalid DueDate["
					+ dueDate + "] format [" + dateFormat +  "] code =" + 
			BillerGWRespCode.INVALID_DUE_DATE);
			return false;

//			throw new ValidateException("Check DueDate: Invalid DueDate["
//					+ dueDate + "] format [" + dateFormat + "]",
//					GWErrorRespCode.INVALID_DUE_DATE);

		}
	}

	public static Date getCurrent() {
		Calendar c = Calendar.getInstance(Locale.US);
		return c.getTime();
	}

	public static Date minusDate(Date date, int day) {
		Calendar c = Calendar.getInstance(Locale.US);
		c.setTime(date);
		c.set(Calendar.DATE, c.get(Calendar.DATE) - day);
		return c.getTime();
	}
}
