package com.dtac.billerweb.common;

import java.io.File;

import org.zkoss.util.resource.Labels;

import com.dtac.billerweb.exception.BillerWebException;

public class AppMessage {

	/* ### For Reload Config File ### */
	private static long lastModified = 0;
	private static long latestLastModified = 0;

	public final static String SELECTBOX_ALL = "SELECTBOX_ALL";
	public final static String SELECTBOX_SELECT = "SELECTBOX_SELECT";
	public final static String SELECTBOX_CREATE_NEW = "SELECTBOX_CREATE_NEW";

	public final static String MSG_SAVE_CONFIRM = "SAVE_CONFIRM";
	public final static String MSG_RESET_CONFIRM = "RESET_CONFIRM";
	public final static String MSG_SAVE_SUCCESS = "SAVE_SUCCESS";
	public final static String MSG_SAVE_FAIL = "SAVE_FAIL";
	public final static String MSG_CORRECT = "MSG_CORRECT";
	public final static String MSG_SAVE_BILLER_SERVICE_BEFORE = "SAVE_BILLER_SERVICE_BEFORE";
	public final static String MSG_CREATE_INPUT_FORM_BEFORE = "CREATE_INPUT_FORM_BEFORE";
	public final static String MSG_PUBLISH_CONFIRM = "PUBLISH_CONFIRM";
	public final static String MSG_DELETE_CONFIRM = "DELETE_CONFIRM";
	public final static String MSG_DELETE_SUCCESS = "DELETE_SUCCESS";
	public final static String MSG_DELETE_FAIL = "DELETE_FAIL";

	/* ### Title ### */
	public static final String BW2100_TITLE = "BW2100_TITLE";
	public static final String BW2110_NEW_TITLE = "BW2110_NEW_TITLE";
	public static final String BW2110_EDIT_TITLE = "BW2110_EDIT_TITLE";
	public static final String BW2110_VIEW_TITLE = "BW2110_VIEW_TITLE";
	public static final String BILLER_INQUIRY_SEARCH_TITLE = "BILLER_INQUIRY_SEARCH_TITLE";
	public static final String FILEDOWNLOAD_INQUIRY_TITLE = "FILEDOWNLOAD_INQUIRY_TITLE";
	public static final String BW2200_TITLE = "BW2200_TITLE";
	public static final String BW2210_NEW_TITLE = "BW2210_NEW_TITLE";
	public static final String BW2210_EDIT_TITLE = "BW2210_EDIT_TITLE";
	public static final String BW2210_VIEW_TITLE = "BW2210_VIEW_TITLE";
	public static final String BW2300_TITLE = "BW2300_TITLE";
	public static final String BW2310_NEW_TITLE = "BW2310_NEW_TITLE";
	public static final String BW2310_EDIT_TITLE = "BW2310_EDIT_TITLE";
	public static final String BW2310_VIEW_TITLE = "BW2310_VIEW_TITLE";
	public static final String BW3000_TITLE = "BW3000_TITLE";
	public static final String BW2130_NEW_TITLE = "BW2130_NEW_TITLE";
	public static final String BW2130_EDIT_TITLE = "BW2130_EDIT_TITLE";
	public static final String BW2130_VIEW_TITLE = "BW2130_VIEW_TITLE";
	public static final String BW2140_NEW_TITLE = "BW2140_NEW_TITLE";
	public static final String BW2140_EDIT_TITLE = "BW2140_EDIT_TITLE";
	public static final String BW2140_VIEW_TITLE = "BW2140_VIEW_TITLE";
	public static final String BW2230_NEW_TITLE = "BW2230_NEW_TITLE";
	public static final String BW2230_EDIT_TITLE = "BW2230_EDIT_TITLE";
	public static final String BW2230_VIEW_TITLE = "BW2230_VIEW_TITLE";
	public static final String BW2330_NEW_TITLE = "BW2330_NEW_TITLE";
	public static final String BW2330_EDIT_TITLE = "BW2330_EDIT_TITLE";
	public static final String BW2330_VIEW_TITLE = "BW2330_VIEW_TITLE";
	public static final String BW1100_TITLE = "BW1100_TITLE";
	public static final String BW1110_NEW_TITLE = "BW1110_NEW_TITLE";
	public static final String BW1110_EDIT_TITLE = "BW1110_EDIT_TITLE";
	public static final String BW1110_VIEW_TITLE = "BW1110_VIEW_TITLE";	
	public static final String BW1200_TITLE = "BW1200_TITLE";
	public static final String BW1210_NEW_TITLE = "BW1210_NEW_TITLE";
	public static final String BW1210_EDIT_TITLE = "BW1210_EDIT_TITLE";
	public static final String BW1210_VIEW_TITLE = "BW1210_VIEW_TITLE";
	public static final String BW1300_TITLE = "BW1300_TITLE";
	public static final String BW1310_NEW_TITLE = "BW1310_NEW_TITLE";
	public static final String BW1310_EDIT_TITLE = "BW1310_EDIT_TITLE";
	public static final String BW1310_VIEW_TITLE = "BW1310_VIEW_TITLE";
	public static final String BW1400_TITLE = "BW1400_TITLE";
	public static final String BW1411_NEW_TITLE = "BW1411_NEW_TITLE";
	public static final String BW1411_EDIT_TITLE = "BW1411_EDIT_TITLE";
	public static final String BW1411_VIEW_TITLE = "BW1411_VIEW_TITLE";
	public static final String BW1412_NEW_TITLE = "BW1412_NEW_TITLE";
	public static final String BW1412_EDIT_TITLE = "BW1412_EDIT_TITLE";
	public static final String BW1412_VIEW_TITLE = "BW1412_VIEW_TITLE";
	public static final String BW1413_NEW_TITLE = "BW1413_NEW_TITLE";
	public static final String BW1413_VIEW_TITLE = "BW1413_VIEW_TITLE";	
	public static final String BW1414_NEW_TITLE = "BW1414_NEW_TITLE";
	public static final String BW1414_EDIT_TITLE = "BW1414_EDIT_TITLE";
	public static final String BW1414_VIEW_TITLE = "BW1414_VIEW_TITLE";
	public static final String BW1415_NEW_TITLE = "BW1415_NEW_TITLE";
	public static final String BW1415_EDIT_TITLE = "BW1415_EDIT_TITLE";
	public static final String BW1415_VIEW_TITLE = "BW1415_VIEW_TITLE";	
	public static final String BW1416_NEW_TITLE = "BW1416_NEW_TITLE";
	public static final String BW1416_EDIT_TITLE = "BW1416_EDIT_TITLE";
	public static final String BW1416_VIEW_TITLE = "BW1416_VIEW_TITLE";	
	public static final String BW1430_TITLE = "BW1430_TITLE";
	public static final String BW1430_VIEW_TITLE = "BW1430_VIEW_TITLE";
	public static final String BW1440_NEW_TITLE = "BW1440_NEW_TITLE";
	public static final String BW1440_EDIT_TITLE = "BW1440_EDIT_TITLE";
	public static final String BW1440_VIEW_TITLE = "BW1440_VIEW_TITLE";
	public static final String BW1450_NEW_TITLE = "BW1450_NEW_TITLE";
	public static final String BW1450_EDIT_TITLE = "BW1450_EDIT_TITLE";
	public static final String BW1450_VIEW_TITLE = "BW1450_VIEW_TITLE";
	public static final String BW1460_NEW_TITLE = "BW1460_NEW_TITLE";
	public static final String BW1460_EDIT_TITLE = "BW1460_EDIT_TITLE";
	public static final String BW1460_VIEW_TITLE = "BW1460_VIEW_TITLE";
	public static final String BW1500_TITLE = "BW1500_TITLE";
	public static final String BW1510_NEW_TITLE = "BW1510_NEW_TITLE";
	public static final String BW1510_EDIT_TITLE = "BW1510_EDIT_TITLE";
	public static final String BW1510_VIEW_TITLE = "BW1510_VIEW_TITLE";
	public static final String BW1600_TITLE = "BW1600_TITLE";
	public static final String BW1610_NEW_TITLE = "BW1610_NEW_TITLE";
	public static final String BW1610_EDIT_TITLE = "BW1610_EDIT_TITLE";
	public static final String BW1610_VIEW_TITLE = "BW1610_VIEW_TITLE";
	public static final String BW1700_TITLE = "BW1700_TITLE";
	public static final String BW1710_NEW_TITLE = "BW1710_NEW_TITLE";
	public static final String BW1710_EDIT_TITLE = "BW1710_EDIT_TITLE";
	public static final String BW1710_VIEW_TITLE = "BW1710_VIEW_TITLE";
	public static final String BW1800_TITLE = "BW1800_TITLE";
	public static final String BW4100_TITLE = "BW4100_TITLE";
	public static final String BW4110_NEW_TITLE = "BW4110_NEW_TITLE";
	public static final String BW4110_EDIT_TITLE = "BW4110_EDIT_TITLE";
	public static final String BW4110_VIEW_TITLE = "BW4110_VIEW_TITLE";
	public static final String BW4200_TITLE = "BW4200_TITLE";
	public static final String BW4210_NEW_TITLE = "BW4210_NEW_TITLE";
	public static final String BW4210_EDIT_TITLE = "BW4210_EDIT_TITLE";
	public static final String BW4210_VIEW_TITLE = "BW4210_VIEW_TITLE";
	public static final String BW5100_TITLE = "BW5100_TITLE";
	public static final String BW5110_NEW_TITLE = "BW5110_NEW_TITLE";
	public static final String BW5110_EDIT_TITLE = "BW5110_EDIT_TITLE";
	public static final String BW5110_VIEW_TITLE = "BW5110_VIEW_TITLE";
	public static final String BW5200_TITLE = "BW5200_TITLE";
	public static final String BW5210_NEW_TITLE = "BW5210_NEW_TITLE";
	public static final String BW5210_EDIT_TITLE = "BW5210_EDIT_TITLE";
	public static final String BW5210_VIEW_TITLE = "BW5210_VIEW_TITLE";
	public static final String BW5230_NEW_TITLE = "BW5230_NEW_TITLE";
	public static final String BW5230_EDIT_TITLE = "BW5230_EDIT_TITLE";
	public static final String BW5230_VIEW_TITLE = "BW5230_VIEW_TITLE";
	public static final String BW5240_NEW_TITLE = "BW5240_NEW_TITLE";
	public static final String BW5240_EDIT_TITLE = "BW5240_EDIT_TITLE";
	public static final String BW5240_VIEW_TITLE = "BW5240_VIEW_TITLE";
	public static final String BW5250_NEW_TITLE = "BW5250_NEW_TITLE";
	public static final String BW5250_EDIT_TITLE = "BW5250_EDIT_TITLE";
	public static final String BW5250_VIEW_TITLE = "BW5250_VIEW_TITLE";
	public static final String BW5300_TITLE = "BW5300_TITLE";
	public static final String BW5310_NEW_TITLE = "BW5310_NEW_TITLE";
	public static final String BW5310_EDIT_TITLE = "BW5310_EDIT_TITLE";
	public static final String BW5310_VIEW_TITLE = "BW5310_VIEW_TITLE";
	public static final String BW6000_TITLE = "BW6000_TITLE";
	public static final String BW6030_TITLE = "BW6030_TITLE";

	/* ## Validation Message ## */
	public static final String MSG_STARTDATE_MUST_BEFORE_ENDDATE = "STARTDATE_MUST_BEFORE_ENDDATE";
	public static final String MSG_ENDDATE_MUST_AFTER_STARTDATE = "ENDDATE_MUST_AFTER_STARTDATE";
	public static final String MSG_DATA_DUPLICATE = "DATA_DUPLICATE";
	public static final String MSG_DATA_EMPTY = "DATA_EMPTY";
	public static final String MSG_NUMBER_ONLY = "NUMBER_ONLY";
	public static final String MSG_MUST_SELECT = "MUST_SELECT";
	public static final String MSG_TAX_ID_FORMAT_ONLY = "TAX_ID_FORMAT_ONLY";
	public static final String MSG_INVALID_XML_FORMAT = "INVALID_XML_FORMAT";
	public static final String MSG_TODATE_MUST_AFTER_FROMDATE = "TODATE_MUST_AFTER_FROMDATE";
	public static final String MSG_DATE_PERIOD = "DATE_PERIOD";
	public static final String MSG_NOT_OVER = "NOT_OVER";
	public static final String MSG_NOT_LESS_THAN = "NOT_LESS_THAN";
	public static final String MSG_FIX_LENGTH = "FIX_LENGTH";
	public static final String MSG_INVALID_DATE_FORMAT = "INVALID_DATE_FORMAT";
	public static final String MSG_ILLEGAL_INPUT = "ILLEGAL_INPUT";
	public static final String MSG_INVALID_NEW_LINE_POSITION = "INVALID_NEW_LINE_POSITION";
	public static final String MSG_INVALID_SERVICE_CODE = "INVALID_SERVICE_CODE";
	public static final String MSG_INVALID_FILE_SIZE = "INVALID_FILE_SIZE";
	public static final String MSG_INVALID_FILE_FORMAT = "INVALID_FILE_FORMAT";
	public static final String MSG_DATE_EXIST = "DATE_EXIST";		

	private static void checkFileForUpdate() {
		try {
			String filePath = AppConstant.USER_HOME+org.zkoss.lang.Library.getProperty("app.message.location");
			File singlePropsFile = new File(filePath);
			latestLastModified = singlePropsFile.lastModified();
			if (latestLastModified != lastModified) {
				Labels.reset();
				lastModified = latestLastModified;
			}
		} catch (Exception e) {
		}
	}

	public static String getMessage(String key) {
		checkFileForUpdate();

		String message = "";
		try {
			message = Labels.getLabel(key);
		} catch (Exception ex) {
			throw new BillerWebException("Get Message From i18n fail", ex);
		}
		return message;
	}

	public static String getMessage(String key, String def) {
		checkFileForUpdate();

		String message = null;
		try {
			message = Labels.getLabel(key);
		} catch (Exception ex) {
			throw new BillerWebException("Get Message From i18n fail", ex);
		}
		return (message == null) ? def : message;
	}

	public static String getMessage(String key, String... params) {
		checkFileForUpdate();

		String message = "";
		try {
			message = Labels.getLabel(key, params);
		} catch (Exception ex) {
			throw new BillerWebException("Get Message From i18n fail", ex);
		}
		return message;
	}
}
