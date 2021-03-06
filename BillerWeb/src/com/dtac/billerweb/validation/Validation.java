package com.dtac.billerweb.validation;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mfs.config.xml.ReadXmlConfig;
import mfs.config.xml.util.XmlUtil;
import mfs.biller.util.DateTimeUtil;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;

import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.util.DateCompareResult;

public class Validation {
	public static void isExist(Component comp, boolean result, String value) throws WrongValueException, Exception {
		String[] params = { value };
		if (result) {
			throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_DATA_DUPLICATE, params));
		}
	}

	public static <V> void isNotEmpty(Component comp, V value, String field) throws WrongValueException, Exception {
		String[] params = { field };
		if (value instanceof Number) {
			if (AppUtil.isNull(value)) {
				throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_DATA_EMPTY, params));
			}
		} else if (AppUtil.isEmpty(value)) {
			throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_DATA_EMPTY, params));
		}
	}

	public static void isStrNumber(Component comp, String value) throws WrongValueException, Exception {
		if (AppUtil.isEmpty(value)) {
			return;
		}
		String regex = "^[0-9]+$";
		Pattern pat = Pattern.compile(regex);
		Matcher match = pat.matcher(value);
		if (!match.find()) {
			throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_NUMBER_ONLY));
		}
	}

	public static void isNotThaiChar(Component comp, String value) throws WrongValueException, Exception {
		if (AppUtil.isEmpty(value)) {
			return;
		}

		String regex = "^[^ก-๙]+$";
		Pattern pat = Pattern.compile(regex);
		Matcher match = pat.matcher(value);
		if (!match.find()) {
			throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_ILLEGAL_INPUT));
		}
	}

	public static void isNotEnglishChar(Component comp, String value) throws WrongValueException, Exception {
		return;//Disable this function
//		if (AppUtil.isEmpty(value)) {
//			return;
//		}
//		String regex = "^[^A-z]+$";
//		Pattern pat = Pattern.compile(regex);
//		Matcher match = pat.matcher(value);
//		if (!match.find()) {
//			throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_ILLEGAL_INPUT));
//		}
	}

	public static void isFixLength(Component comp, String value, int length, String field) throws WrongValueException, Exception {
		String[] params = { field, length + "" };
		if (value.length() != length) {
			throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_FIX_LENGTH, params));
		}
	}

	public static <V> void isSelectDropdownList(Component comp, V value, String field) throws WrongValueException, Exception {

		String[] params = { field };
		if (value instanceof Number) {
			if (AppUtil.isNull(value)||((Number)value).doubleValue()==-1) {
				throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_MUST_SELECT, params));
			}
		} else if (AppUtil.isEmpty(value)) {
			throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_MUST_SELECT, params));
		}
	}

	public static void isXMLFormat(Component comp, String xml) throws WrongValueException, Exception {
		try {
			if (AppUtil.isEmpty(xml)) {
				return;
			}
			XmlUtil.convertXmlToObj(xml, ReadXmlConfig.class);
		} catch (Exception ex) {
			throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_INVALID_XML_FORMAT));
		}
	}

	public static void validateEndDateTime(Component comp, Date startDate, Date endDate, String errorMsg) throws WrongValueException, Exception {
		if ((!AppUtil.isNull(startDate)) && (!AppUtil.isNull(endDate))) {
			if (endDate.compareTo(startDate) < 0) { // endDate before startDate
				throw new WrongValueException(comp, errorMsg);
			}
		}
	}

	public static void isNotOver(Component comp, Double value, Double maxValue) throws WrongValueException, Exception {
		if (value == null) {
			return;
		}
		DecimalFormat df = new DecimalFormat("#,##0.00");
		if (value > maxValue) {
			String[] params = { df.format(maxValue) };
			df = null;
			throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_NOT_OVER, params));
		}
	}

	public static <V extends Number> void isNotLessThan(Component comp, V value1, V value2, String field1, String field2) throws WrongValueException, Exception {
		if (value1 == null || value2 == null) {
			return;
		}
		if (value1 instanceof Number&&value1.doubleValue() < value2.doubleValue()) {
			String[] params = { field2, field1 };
			throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_NOT_LESS_THAN, params));
		}else if(Date.class.isAssignableFrom(value1.getClass())){
			
		}
	}
	
	public static  void isEndTimeNotLessThanStartTime(Component comp,Date startTime, Date endTime, String field1, String field2) throws WrongValueException, Exception {
		if (startTime == null || endTime == null) {
			return;
		}
		startTime.setDate(0);
		startTime.setMonth(0);
		startTime.setYear(0);
		startTime.setSeconds(0);
		endTime.setDate(0);
		endTime.setMonth(0);
		endTime.setYear(0);
		endTime.setSeconds(0);
		if (endTime.compareTo(startTime)<0) {
			String[] params = { field2, field1 };
			throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_NOT_LESS_THAN, params));
		}
	}

	public static void isDateFormat(Component comp, String value) throws WrongValueException, Exception {
		if (AppUtil.isEmpty(value)) {
			return;
		}

		isNotThaiChar(comp, value);

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(value);
		} catch (IllegalArgumentException iae) {
			throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_INVALID_DATE_FORMAT));
		}
	}

	public static void validateEndDate(Component comp, Date startDate, Date endDate) {
		if ((!AppUtil.isNull(startDate)) && (!AppUtil.isNull(endDate))) {
			if (AppUtil.compareDate(endDate, startDate) == DateCompareResult.LESS_THAN) {
				throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_ENDDATE_MUST_AFTER_STARTDATE));
			}
		}
	}
	
	public static void validateAddOneMonth(Component comp, Date startDate, Date endDate, String errorMsg) throws WrongValueException, Exception {
		if ((!AppUtil.isNull(startDate)) && (!AppUtil.isNull(endDate))) {			
			Calendar cal = Calendar.getInstance();
	        cal.setTime(startDate);
	        cal.add(Calendar.MONTH, 1); 
	        Date maxDate =  DateTimeUtil.minusDate(cal.getTime(), 1);			
			if (maxDate.compareTo(endDate) < 0) {
				throw new WrongValueException(comp, errorMsg);
			}
		}
	}
}
