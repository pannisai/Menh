package mfs.util.biller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Example Use String tmp = ConfigUtil.getInstance().getConfig("xxxx");
 * 
 * 
 */
public class ConfigUtil {

	private static String fileConfig = ConstantUtil.PROXY_CONF_FILE;

	private static ConfigUtil instance = null;
	private Properties singleProps = null;
	private Hashtable configs = new Hashtable();

	/* For use in checking single.properties file update */
	private String singlePropsFilePath;
	private long lastModified;
	private long latestLastModified;

	public static synchronized ConfigUtil getInstance() {
		if (instance == null) {
			instance = new ConfigUtil();
		}
		return instance;
	}

	private ConfigUtil() {
		singlePropsFilePath = fileConfig;
		loadConfig();
	}

	private void loadConfig() {
		try {
			if (singleProps == null) {
				singleProps = new Properties();
			} else {
				singleProps.clear();
			}
			singleProps.load(new FileInputStream(singlePropsFilePath));
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		loadProps();
	}

	private void loadProps() {
		updateLastModified();
		Enumeration propNames = singleProps.propertyNames();
		while (propNames.hasMoreElements()) {
			String config = (String) propNames.nextElement();
			if (!configs.containsKey(config)) {
				configs.put(config, singleProps.getProperty(config));
			}
		}
	}

	private void updateLastModified() {
		File singlePropsFile = new File(singlePropsFilePath);
		lastModified = singlePropsFile.lastModified();
	}

	private boolean isUpdated() {
		File singlePropsFile = new File(singlePropsFilePath);
		latestLastModified = singlePropsFile.lastModified();
		if (latestLastModified != lastModified) {
			return true;
		} else {
			return false;
		}
	}

	private void checkSinglePropsFileForUpdate() {
		if (isUpdated()) {
			try {
				System.out.println("file config is change, reload config file");
				singleProps.clear();
				singleProps.load(new FileInputStream(singlePropsFilePath));
			} catch (Exception ex) {
				ex.printStackTrace();
				return;
			}
			configs.clear();
			loadProps();
		}
	}

	public String getConfig(String config) {
		/* Check single.properties for update */
		checkSinglePropsFileForUpdate(); // comment for no refresh config file

		if (configs.containsKey(config)) {
			return (String) configs.get(config);
		} else {
			return "";
		}
	}
}