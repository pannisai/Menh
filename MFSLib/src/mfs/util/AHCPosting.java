package mfs.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

public class AHCPosting {
	private HttpClient conn;
	private String url;
	private int timeOut;
	private String logOwner;
	private Logger log;

	public AHCPosting(String url, int timeOut, String logOwner, Logger log,
			String user, String pass) {
		this.log = log;
		this.url = url;
		this.timeOut = timeOut;
		this.logOwner = logOwner;

		HttpConnectionManager connMgr = new SimpleHttpConnectionManager();
		connMgr.getParams().setConnectionTimeout(timeOut);

		conn = new HttpClient();
		conn.setHttpConnectionManager(connMgr);

		if (!user.equals("") && user != null && !pass.equals("")
				&& pass != null) {
			UsernamePasswordCredentials userPass = new UsernamePasswordCredentials(
					user, pass);
			conn.getState().setCredentials(AuthScope.ANY, userPass);
		}

	}
	
	public AHCPosting(String url, int timeOut, String logOwner, Logger log, String user, String pass, String proxyIp, int proxyPort) {
		this.log = log;
		this.url = url;
		this.timeOut = timeOut;
		this.logOwner = logOwner;

		HttpConnectionManager connMgr = new SimpleHttpConnectionManager();
		connMgr.getParams().setConnectionTimeout(timeOut);

		conn = new HttpClient();
		conn.setHttpConnectionManager(connMgr);
		
		conn.getHostConfiguration().setProxy(proxyIp, proxyPort);

		if (!user.equals("") && user != null && !pass.equals("")
				&& pass != null) {
			UsernamePasswordCredentials userPass = new UsernamePasswordCredentials(
					user, pass);
			conn.getState().setCredentials(AuthScope.ANY, userPass);
		}

	}

	public Result postBody(Properties param) throws IOException {
		PostMethod method = new PostMethod(url);
		method.getParams().setSoTimeout(timeOut);

		if (param != null) {
			NameValuePair[] params = new NameValuePair[param.size()];
			int i = 0;
			Enumeration key = param.keys();
			while (key.hasMoreElements()) {
				String paramName = (String) key.nextElement();
				String paramValue = (String) param.getProperty(paramName);

				NameValuePair nvp = new NameValuePair(paramName, paramValue);
				params[i++] = nvp;
			}
			method.setRequestBody(params);
		}

		logDebug("Request POST-BODY " + url + " [timeout:" + timeOut
				+ " ms.] "  );
		return executeMethod(method);
	}

	public Result postMessage(Properties header, String msg) throws IOException {
		PostMethod method = new PostMethod(url);
		method.getParams().setSoTimeout(timeOut);

		// Add header
		if (header != null) {
			Enumeration key = header.keys();
			while (key.hasMoreElements()) {
				String hdName = (String) key.nextElement();
				String hdValue = (String) header.getProperty(hdName);

				method.addRequestHeader(hdName, hdValue);
			}
		}

		// Add message
		if (msg != null) {
			StringRequestEntity reqEntity = new StringRequestEntity(msg,
					"text/xml", "UTF-8");
			method.setRequestEntity(reqEntity);
		}

		logDebug("Request POST-MSG " + url + " [timeout:" + timeOut
				+ " ms.] param " + msg);
		return executeMethod(method);
	}

	public Result postMessage(String msg) throws IOException {
		PostMethod method = new PostMethod(url);
		method.getParams().setSoTimeout(timeOut);

		if (msg != null) {
			StringRequestEntity reqEntity = new StringRequestEntity(msg,
					"text/xml", "UTF-8");
			method.setRequestEntity(reqEntity);
		}

		logDebug("Request POST-MSG " + url + " [timeout:" + timeOut
				+ " ms.] param " + msg);
		return executeMethod(method);
	}

	public Result postMessage(String msg, String encode) throws IOException {
		PostMethod method = new PostMethod(url);
		method.getParams().setSoTimeout(timeOut);

		if (msg != null) {
			StringRequestEntity reqEntity = new StringRequestEntity(msg,
					"text/xml", encode);
			method.setRequestEntity(reqEntity);
		}

		logDebug("Request POST-MSG " + url + " [timeout:" + timeOut
				+ " ms.] param " + msg);
		return executeMethod(method);
	}

	public Result post(Properties param) throws IOException {
		PostMethod method = new PostMethod(url);
		method.getParams().setSoTimeout(timeOut);

		// Add parameter
		if (param != null) {
			NameValuePair[] params = new NameValuePair[param.size()];
			int i = 0;
			Enumeration key = param.keys();
			while (key.hasMoreElements()) {
				String paramName = (String) key.nextElement();
				String paramValue = (String) param.getProperty(paramName);

				NameValuePair nvp = new NameValuePair(paramName, paramValue);
				params[i++] = nvp;
			}
			method.setQueryString(params);
		}

		logDebug("Request POST " + url + " [timeout:" + timeOut
				+ " ms.] param " + param);
		return executeMethod(method);
	}

	public Result postUTF8(Properties param) throws IOException {
		PostMethod method = new UTF8PostMethod(url);
		method.getParams().setSoTimeout(timeOut);
		method.setRequestHeader("ContentType",
				"application/x-www-form-urlencoded;charset=UTF-8");

		// Add parameter
		if (param != null) {
			NameValuePair[] params = new NameValuePair[param.size()];
			int i = 0;
			Enumeration key = param.keys();
			while (key.hasMoreElements()) {
				String paramName = (String) key.nextElement();
				String paramValue = (String) param.getProperty(paramName);

				NameValuePair nvp = new NameValuePair(paramName, paramValue);
				params[i++] = nvp;
			}
			method.setRequestBody(params);
		}

		logDebug("Request POST " + url + " [timeout:" + timeOut
				+ " ms.] param " + param);
		return executeMethod(method);
	}

	public Result get(Properties param) throws IOException {
		GetMethod method = new GetMethod(url);
		method.getParams().setSoTimeout(timeOut);
		method.setRequestHeader("ContentType",
				"text/xml;charset=UTF-8"); // http-equiv="Content-Type" content="text/html;charset=utf-8" Content-Type: text/html; charset=ISO-8859-1
		// Add parameter
		if (param != null) {
			NameValuePair[] params = new NameValuePair[param.size()];
			int i = 0;
			Enumeration key = param.keys();
			while (key.hasMoreElements()) {
				String paramName = (String) key.nextElement();
				String paramValue = (String) param.getProperty(paramName);

				NameValuePair nvp = new NameValuePair(paramName, paramValue);
				params[i++] = nvp;
			}

			method.setQueryString(params);
		}

		logDebug("Request GET " + url + " [timeout:" + timeOut + " ms.] param "
				+ param);

		return executeMethod(method);
	}

	public Result get(Properties header, Properties param) throws IOException {
		GetMethod method = new GetMethod(url);
		method.getParams().setSoTimeout(timeOut);

		// Add header
		if (header != null) {
			Enumeration key = header.keys();
			while (key.hasMoreElements()) {
				String hdName = (String) key.nextElement();
				String hdValue = (String) header.getProperty(hdName);

				method.addRequestHeader(hdName, hdValue);
			}
		}

		// Add parameter
		if (param != null) {
			NameValuePair[] params = new NameValuePair[param.size()];
			int i = 0;
			Enumeration key = param.keys();
			while (key.hasMoreElements()) {
				String paramName = (String) key.nextElement();
				String paramValue = (String) param.getProperty(paramName);

				NameValuePair nvp = new NameValuePair(paramName, paramValue);
				params[i++] = nvp;
			}

			method.setQueryString(params);
		}

		logDebug("Request GET-HEAD " + url + " [timeout:" + timeOut
				+ " ms.] head " + header + " param " + param);
		return executeMethod(method);
	}

	public Result post(Properties header, Properties param) throws IOException {
		PostMethod method = new PostMethod(url);
		method.getParams().setSoTimeout(timeOut);

		// Add header
		if (header != null) {
			Enumeration key = header.keys();
			while (key.hasMoreElements()) {
				String hdName = (String) key.nextElement();
				String hdValue = (String) header.getProperty(hdName);

				method.addRequestHeader(hdName, hdValue);
			}
		}

		// Add parameter
		if (param != null) {
			NameValuePair[] params = new NameValuePair[param.size()];
			int i = 0;
			Enumeration key = param.keys();
			while (key.hasMoreElements()) {
				String paramName = (String) key.nextElement();
				String paramValue = (String) param.getProperty(paramName);

				NameValuePair nvp = new NameValuePair(paramName, paramValue);
				params[i++] = nvp;
			}

			method.setQueryString(params);
		}

		logDebug("Request POST-HEAD " + url + " [timeout:" + timeOut
				+ " ms.] head " + header + " param " + param);
		return executeMethod(method);
	}

	public static Properties stringToProperties(String message, String seperator) {
		Properties p = new Properties();

		if (message == null && message.indexOf(seperator) < 0)
			return p;

		String[] messageArr = message.split(seperator);

		for (int i = 0; i < messageArr.length; i++) {
			String[] pairValue = messageArr[i].split("=");
			p.put(pairValue[0], pairValue[1]);
		}

		return p;
	}

	public static String getResultFromXmlTag(String reply, String xmlTag) {
		String result = null;
		int idx = reply.indexOf("<" + xmlTag + ">");

		if (idx >= 0) {
			int endIdx = reply.indexOf("</" + xmlTag, idx);
			result = reply.substring(idx + xmlTag.length() + 2, endIdx);
		}

		return result;
	}

	private Result executeMethod(HttpMethod method) throws IOException {
		Result result = new Result();
		try {
			conn.executeMethod(method);

			result.setStatusCode(method.getStatusCode());
			result.setSuccess(method.getStatusCode() == HttpStatus.SC_OK);

			// String responseMsg = new String(method.getResponseBody());
			String responseMsg = new String(
					StringUtil.convertStreamToString(method
							.getResponseBodyAsStream()));
			// logDebug("Response :" + responseMsg);

			result.setContent(responseMsg);
		} finally {
			if (method != null)
				try {
					method.releaseConnection();
				} catch (Exception e) {
				}
		}

		return result;
	}

	private void logError(String msg) {
		log.error(logOwner + "|" + msg);
	}

	private void logDebug(String msg) {
		log.debug(logOwner + "|" + msg);
	}

	public static class Result {
		private boolean success = false;
		private String content;
		private int StatusCode;

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public boolean isSuccess() {
			return success;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getContent() {
			return content;
		}

		public void setStatusCode(int StatusCode) {
			this.StatusCode = StatusCode;
		}

		public int getStatusCode() {
			return StatusCode;
		}
	}

	public static class UTF8PostMethod extends PostMethod {
		public UTF8PostMethod(String url) {
			super(url);
		}

		@Override
		public String getRequestCharSet() {
			// return super.getRequestCharSet();
			return "UTF-8";
		}
	}
}
