package com.dtac.billerweb.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.AppConfiguration;

public class BillerInquiryConfig {
	private static Logger log = Logger.getLogger(AppConfiguration.class);

	private static String FILE_PATH ;
//	private final static String FILE_PATH = "/usr/home/WEBADM/mfsappd/conf/biller/billerinquiryConfig.properties";
	static{
		FILE_PATH= "D:\\JoseWork\\Dtac\\config\\billerinquiryConfig.properties";
//		FILE_PATH=System.getProperty("user.home")+"/conf/biller/billerinquiryConfig.properties";
	}
	private static Properties prop = new Properties();
	
	public final static String MEA_COLUMN_KEY="MEA_COLUMN";
	public final static String PEA_COLUMN_KEY="PEA_COLUMN";
	public final static String MWA_COLUMN_KEY="MWA_COLUMN";
	public final static String PWA_COLUMN_KEY="PWA_COLUMN";
	public final static String SSO39_COLUMN_KEY="SSO39_COLUMN";
	public final static String SSO40_COLUMN_KEY="SSO40_COLUMN";
	public final static String GSB_COLUMN_KEY="GSB_COLUMN";
	public final static String TOT_TOT3G_COLUMN_KEY="TOT_TOT3G_COLUMN";
	public final static String DTAC_DTN_COLUMN_KEY="DTAC_DTN_COLUMN";
	
	public final static String MEA_VALUE_KEY="MEA_VALUE";
	public final static String PEA_VALUE_KEY="PEA_VALUE";
	public final static String MWA_VALUE_KEY="MWA_VALUE";
	public final static String PWA_VALUE_KEY="PWA_VALUE";
	public final static String SSO39_VALUE_KEY="SSO39_VALUE";
	public final static String SSO40_VALUE_KEY="SSO40_VALUE";
	public final static String GSB_VALUE_KEY="GSB_VALUE";
	public final static String TOT_TOT3G_VALUE_KEY="TOT_TOT3G_VALUE";
	public final static String DTAC_DTN_VALUE_KEY="DTAC_DTN_VALUE";


	public static String getValue(String key) {
		if (prop.isEmpty()) {
			loadConfig();
		}
		return prop.getProperty(key);
	}

	public static void loadConfig() {

		try {
			log.info("Load configuration file::"+FILE_PATH);
			prop.load(new FileInputStream(FILE_PATH));
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}

	
}
