package mfs.util.biller;

/**
 * Constants declaration class for MFS.
 * 
 * @author Apichart
 * 
 */
public class ConstantUtil {
	// ISO Header token in the ISOMSG.
	public static final String ISO_HEADER = "ISO011000000";

	// Process code
	public static final String PROCESS_CODE_VERIFY = "720000";
	public static final String PROCESS_CODE_CONFIRM = "730000";
	// reverse code is from assumption.
	// 16/07/2013 : reverse said not to be implemented.
	// public static final String PROCESS_CODE_REVERSE = "740000";

	// mti code
//	public static final int MTI_VERIFY = 200;
//	public static final int MTI_CONFIRM = 220;
//	public static final int MTI_REVERSE = 400;
	public static final String MTI_NETWORK_TEST_REQUEST = "0800";
	public static final String MTI_VERIFY_RESPONSE = "0210";
	public static final String MTI_CONFIRM_RESPONSE = "0230";
	public static final String MTI_REVERSE_RESPONSE = "0410";
	public static final String TRNS_FUNC_CODE_VERFIFY = "VER";
	public static final String TRNS_FUNC_CODE_CONFIRM = "CON";
	public static final String TRNS_FUNC_CODE_REVERT = "REV";

	// Configuration path
	public static final String USER_HOME = System.getProperty("user.home");
	public static final String CONFIG_PATH = USER_HOME + "/conf";

	// Biller
	public static final String BILL_CONF_PATH = CONFIG_PATH + "/biller";
	public static final String TEMP_CONF_PATH = BILL_CONF_PATH + "/temp";
	public static final String BILL_LOG_FILE = BILL_CONF_PATH + "/log/billerLog4j.properties";
	
	
	// Bank
	public static final String BANK_CONF_PATH = CONFIG_PATH + "/bank";
	public static final String BANK_LOG_FILE = BANK_CONF_PATH + "/log/bankLog4j.properties";
	
	// Files
//	public static final String BILL_CONF_FILE = BILL_CONF_PATH
//			+ "/billerConfig.properties";
	
	//Proxy
	public static final String PROXY_CONF_PATH = CONFIG_PATH + "/proxy";
	public static final String PROXY_LOG_PATH = PROXY_CONF_PATH + "/log";
	public static final String PROXY_CONF_FILE = PROXY_CONF_PATH + "/proxyConfig.properties";
	public static final String PROXY_LOG_FILE = PROXY_LOG_PATH + "/proxyLog4j.properties";
	
	public static final String ISO_PATH = PROXY_CONF_PATH + "/iso";
	public static final String ISO_XML_FILE = ISO_PATH + "/iso8583fdm.xml";


	
//	public static final String SIMULATE_LOG_FILE = LOG_PATH
//			+ "/simulateLog4j.properties";
	

	/*--Validation--*/
	// -- ObjectName used in Validation, eg. in the Obj Map.
	// These string mainly referenced by the Validation XML in Database.
	public static final String VALIDATE_OBJ_FUNDAMO = "fdm";
	public static final String VALIDATE_OBJ_BILLER_DATA = "biller";
	public static final String VALIDATE_OBJ_GATEWAY_BEAN = "gwbean";
	public static final String VALIDATE_OBJ_GATEWAY_TRAN = "gwtran";

}
