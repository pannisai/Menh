package com.dtac.billerweb.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.util.AppUtil;

public class AppConfiguration {
	private static Logger log = Logger.getLogger(AppConfiguration.class);

	private static String FILE_PATH;
	/* ### For Reload Config File ### */
	private static long lastModified = 0;
	private static long latestLastModified = 0;
	static {
//		FILE_PATH=org.zkoss.lang.Library.getProperty("app.backofficewebConfig.location");
//		FILE_PATH = "D:\\JoseWork\\Dtac\\config\\backofficewebConfig.properties";
		 FILE_PATH=AppConstant.USER_HOME+org.zkoss.lang.Library.getProperty("app.backofficewebConfig.location");
	}

	private static Properties prop = new Properties();

	public final static String INITIAL_CONTEXT_FACTORY_KEY = "INITIAL_CONTEXT_FACTORY";
	public final static String PROVIDER_URL_KEY = "PROVIDER_URL_"+AppConstant.WEBLOGIC_NAME;
	public final static String SECURITY_PRINCIPAL_KEY = "SECURITY_PRINCIPAL_"+AppConstant.WEBLOGIC_NAME;
	public final static String SECURITY_CREDENTIALS_KEY = "SECURITY_CREDENTIALS_"+AppConstant.WEBLOGIC_NAME;
	public final static String BILLER_INQUIRY_COLUMN_KEY = "BILLER_INQUIRY_COLUMN";
	public final static String PAGE_SIZE_KEY = "PAGE_SIZE";
	public final static String SERV_BARCODE_PAGE_SIZE = "SERV_BARCODE_PAGE_SIZE";
	public final static String SERV_BARCODE_DETAIL_PAGE_SIZE = "SERV_BARCODE_DETAIL_PAGE_SIZE";
	public final static String CHK_SESS_TIMEOUT_URL = "CHK_SESS_TIMEOUT_URL_"+AppConstant.WEBLOGIC_NAME;
	public final static String CHK_SESS_TIMEOUT_ERR_CODE_KEY = "CHK_SESS_TIMEOUT_ERR_CODE_KEY";
	public final static String CHK_SESS_TIMEOUT_ERR_DESC_KEY = "CHK_SESS_TIMEOUT_ERR_DESC_KEY";
	public final static String CHK_SESS_TIMEOUT_SUCCESS_CODE = "CHK_SESS_TIMEOUT_SUCCESS_CODE";
	public final static String SESS_TIMEOUT_ERR_PAGE_URL = "SESS_TIMEOUT_ERR_PAGE_URL_"+AppConstant.WEBLOGIC_NAME;
	public final static String LOG_PRINT_STACK_TRACE = "LOG_PRINT_STACK_TRACE";
	public final static String SERV_DENOMINATE_PAGE_SIZE = "SERV_DENOMINATE_PAGE_SIZE";
	public final static String GET_PERMISSION_URL = "GET_PERMISSION_URL_"+AppConstant.WEBLOGIC_NAME;
	public final static String GET_PERMISSION_ERR_CODE_KEY = "GET_PERMISSION_ERR_CODE_KEY";
	public final static String GET_PERMISSION_ERR_DESC_KEY = "GET_PERMISSION_ERR_DESC_KEY";
	public final static String CHANNEL_MANAGEMENT_WEB_SERVICE_URL = "CHANNEL_MANAGEMENT_WEB_SERVICE_URL";
	public final static String MAX_ICON_FILE_SIZE = "MAX_ICON_FILE_SIZE";	
	public final static String WEBAGENT_PUBLISH_IMAGE_URL="WEBAGENT_PUBLISH_IMAGE_URL";
	public final static String SECURITY_DECRYPTED_ENDPOINT = "SECURITY_DECRYPTED_ENDPOINT_"+AppConstant.WEBLOGIC_NAME;
	public final static String BILLER_COLLECTION_FEE = "BILLER_COLLECTION_FEE_"+AppConstant.WEBLOGIC_NAME;
	public final static String BILLER_COLLECTION_FEE_EXPORT = "BILLER_COLLECTION_FEE_EXPORT_"+AppConstant.WEBLOGIC_NAME;
	public final static String SUCCESS_CODE = "SUCCESS_CODE";
	public final static String SESS_TIMEOUT_CODE = "SESS_TIMEOUT_CODE";

	/* ### Redirect page path### */
	public final static String BW2100_REDIRECT_PATH = "BW2100_REDIRECT_PATH";
	public final static String BW2110_REDIRECT_PATH = "BW2110_REDIRECT_PATH";
	public final static String BW2200_REDIRECT_PATH = "BW2200_REDIRECT_PATH";
	public final static String BW2210_REDIRECT_PATH = "BW2210_REDIRECT_PATH";
	public final static String BW2300_REDIRECT_PATH = "BW2300_REDIRECT_PATH";
	public final static String BW2310_REDIRECT_PATH = "BW2310_REDIRECT_PATH";
	public final static String BW2130_REDIRECT_PATH = "BW2130_REDIRECT_PATH";
	public final static String BW2140_REDIRECT_PATH = "BW2140_REDIRECT_PATH";
	public final static String BW2230_REDIRECT_PATH = "BW2230_REDIRECT_PATH";
	public final static String BW2330_REDIRECT_PATH = "BW2330_REDIRECT_PATH";
	public final static String BW1100_REDIRECT_PATH = "BW1100_REDIRECT_PATH";
	public final static String BW1110_REDIRECT_PATH = "BW1110_REDIRECT_PATH";
	public final static String BW1200_REDIRECT_PATH = "BW1200_REDIRECT_PATH";
	public final static String BW1210_REDIRECT_PATH = "BW1210_REDIRECT_PATH";
	public final static String BW1300_REDIRECT_PATH = "BW1300_REDIRECT_PATH";
	public final static String BW1310_REDIRECT_PATH = "BW1310_REDIRECT_PATH";
	public final static String BW1400_REDIRECT_PATH = "BW1400_REDIRECT_PATH";
	public final static String BW1410_REDIRECT_PATH = "BW1410_REDIRECT_PATH";
	public final static String BW1411_REDIRECT_PATH = "BW1411_REDIRECT_PATH";
	public final static String BW1412_REDIRECT_PATH = "BW1412_REDIRECT_PATH";
	public final static String BW1413_REDIRECT_PATH = "BW1413_REDIRECT_PATH";
	public final static String BW1414_REDIRECT_PATH = "BW1414_REDIRECT_PATH";
	public final static String BW1415_REDIRECT_PATH = "BW1415_REDIRECT_PATH";
	public final static String BW1430_REDIRECT_PATH = "BW1430_REDIRECT_PATH";
	public final static String BW1440_REDIRECT_PATH = "BW1440_REDIRECT_PATH";
	public final static String BW1450_REDIRECT_PATH = "BW1450_REDIRECT_PATH";
	public final static String BW1460_REDIRECT_PATH = "BW1460_REDIRECT_PATH";	
	public final static String BW1500_REDIRECT_PATH = "BW1500_REDIRECT_PATH";
	public final static String BW1510_REDIRECT_PATH = "BW1510_REDIRECT_PATH";
	public final static String BW1600_REDIRECT_PATH = "BW1600_REDIRECT_PATH";
	public final static String BW1610_REDIRECT_PATH = "BW1610_REDIRECT_PATH";
	public final static String BW3000_REDIRECT_PATH = "BW3000_REDIRECT_PATH";
	public final static String BW3030_REDIRECT_PATH = "BW3030_REDIRECT_PATH";
	public final static String BW4100_REDIRECT_PATH = "BW4100_REDIRECT_PATH";
	public final static String BW4110_REDIRECT_PATH = "BW4110_REDIRECT_PATH";
	public final static String BW4200_REDIRECT_PATH = "BW4200_REDIRECT_PATH";
	public final static String BW4210_REDIRECT_PATH = "BW4210_REDIRECT_PATH";
	public final static String BW5100_REDIRECT_PATH = "BW5100_REDIRECT_PATH";
	public final static String BW5110_REDIRECT_PATH = "BW5110_REDIRECT_PATH";
	public final static String BW5200_REDIRECT_PATH = "BW5200_REDIRECT_PATH";
	public final static String BW5210_REDIRECT_PATH = "BW5210_REDIRECT_PATH";
	public final static String BW5230_REDIRECT_PATH = "BW5230_REDIRECT_PATH";
	public final static String BW5240_REDIRECT_PATH = "BW5240_REDIRECT_PATH";
	public final static String BW5250_REDIRECT_PATH = "BW5250_REDIRECT_PATH";
	public final static String BW5300_REDIRECT_PATH = "BW5300_REDIRECT_PATH";
	public final static String BW5310_REDIRECT_PATH = "BW5310_REDIRECT_PATH";
	public final static String BW6000_REDIRECT_PATH = "BW6000_REDIRECT_PATH";
	public final static String BW6030_REDIRECT_PATH = "BW6030_REDIRECT_PATH";
	public final static String BW1700_REDIRECT_PATH = "BW1700_REDIRECT_PATH";
	public final static String BW1710_REDIRECT_PATH = "BW1710_REDIRECT_PATH";
	public final static String BW1800_REDIRECT_PATH = "BW1800_REDIRECT_PATH";
	
	private static void checkFileForUpdate() {
		try {
			
			File singlePropsFile = new File(FILE_PATH);
			latestLastModified = singlePropsFile.lastModified();
			if (latestLastModified != lastModified) {
				loadConfig();
				lastModified = latestLastModified;
			}
		} catch (Exception e) {
		}
	}
	
	public static String getValue(String key) {
		checkFileForUpdate();
		if (prop.isEmpty()) {
			loadConfig();
		}
		return AppUtil.trim(prop.getProperty(key));
	}

	public static void loadConfig() {

		try {
			log.info("Load configuration file::" + FILE_PATH);
			prop.load(new FileInputStream(FILE_PATH));
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
			throw new BillerWebException("File config not found", e);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new BillerWebException("Can't load file config", e);
		}

	}
	
}
