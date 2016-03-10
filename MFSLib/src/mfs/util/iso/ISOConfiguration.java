package mfs.util.iso;

import java.util.HashMap;
import java.util.Map;

import org.jpos.core.Configuration;

public class ISOConfiguration implements Configuration {

	private Map<String, Object> hashMap = new HashMap<String, Object>();

	public static String HOST = "host";
	public static String PORT = "port";
	public static String TIMEOUT = "timeout";
	public static String CONNECT_TIMEOUT = "connect-timeout";
	public static String KEEP_ALIVE = "keep-alive";

	public ISOConfiguration(String host, int port, int timeout, int con_timeout) {
		hashMap.put("host", host);
		hashMap.put("port", port);
		hashMap.put("timeout", timeout);
		hashMap.put("connect-timeout", con_timeout);
	}

	public ISOConfiguration(String host, int port) {
		hashMap.put("host", host);
		hashMap.put("port", port);
	}

	public ISOConfiguration() {
	}

	public String get(String arg0) {

		if (null == hashMap.get(arg0))
			return "";
		return (String) hashMap.get(arg0);
	}

	public String get(String arg0, String arg1) {

		if (null == hashMap.get(arg0))
			return arg1;
		return (String) hashMap.get(arg0);
	}

	public String[] getAll(String arg0) {
		if (null == hashMap.get(arg0))
			return new String[] { arg0 };
		return new String[] {};
	}

	public boolean getBoolean(String arg0) {

		if (null == hashMap.get(arg0))
			return false;
		return (Boolean) hashMap.get(arg0);
	}

	public boolean getBoolean(String arg0, boolean arg1) {
		if (null == hashMap.get(arg0))
			return arg1;
		else if ("true".equals(hashMap.get(arg0)))
			return true;
		return false;
	}

	public boolean[] getBooleans(String arg0) {

		return null;
	}

	public double getDouble(String arg0) {
		if (null == hashMap.get(arg0))
			return 0;
		return (Double) hashMap.get(arg0);
	}

	public double getDouble(String arg0, double arg1) {
		if (null == hashMap.get(arg0))
			return arg1;
		return (Double) hashMap.get(arg0);
	}

	public double[] getDoubles(String arg0) {

		return null;
	}

	public int getInt(String arg0) {

		if (null == hashMap.get(arg0))
			return 0;
		return (Integer) hashMap.get(arg0);
	}

	public int getInt(String arg0, int arg1) {
		if (null == hashMap.get(arg0))
			return arg1;
		return (Integer) hashMap.get(arg0);
	}

	public int[] getInts(String arg0) {

		if (null == hashMap.get(arg0))
			return new int[] {};
		return new int[] {};

	}

	public long getLong(String arg0) {

		if (null == hashMap.get(arg0))
			return 0;
		return (Long) hashMap.get(arg0);
	}

	public long getLong(String arg0, long arg1) {
		if (null == hashMap.get(arg0))
			return arg1;
		return (Long) hashMap.get(arg0);
	}

	public long[] getLongs(String arg0) {

		return null;
	}

	public void put(String key, Object value) {
		hashMap.put(key, value);
	}

}
