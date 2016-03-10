package mfs.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

import mfs.util.proxy.ProxyBean;
import mfs.util.proxy.ProxyUtil;

import weblogic.net.http.HttpURLConnection;
import weblogic.net.http.HttpsURLConnection;

public class WeblogicHttp{
	
	private String url;
	private int timeout;
	private String proxyHost;
	private String proxyPort;
	private boolean disableCert;
	
	HttpURLConnection conn;
	
	public WeblogicHttp(String url, int timeout){
		init(url, timeout, null, null, false);
	}
	
	public WeblogicHttp(String url, int timeout, boolean disableCert){
		init(url, timeout, null, null, disableCert);
	}
	
	public WeblogicHttp(String url, int timeout, String proxyHost, String proxyPort){
		init(url, timeout, proxyHost, proxyPort, false);
	}
	
	public WeblogicHttp(String url, int timeout, String proxyHost, String proxyPort, boolean disableCert){
		init(url, timeout, proxyHost, proxyPort, disableCert);
	}
	
	private void init(String url, int timeout, String proxyHost, String proxyPort, boolean disableCert){
		this.url = url;
		this.timeout = timeout;
		this.disableCert = disableCert;
		
		if (proxyHost == null || proxyPort == null){
			ProxyBean proxy = ProxyUtil.getInstance().getProxy(this.url);
			if (proxy != null){
				this.proxyHost = proxy.getHost();
				this.proxyPort = proxy.getPort() + "";
			}
		}else{
			this.proxyHost = proxyHost;
			this.proxyPort = proxyPort;
		}
	}
	
	public Result post(Properties param)throws Exception{
		return post(param, "ISO-8859-1");
	}
	
	public Result postUTF8(Properties param)throws Exception{
		return post(param, "UTF-8");
	}
	
	private Result post(Properties param, String charset)throws Exception{
		Result result = new Result();
		try{
			initConfig();
			
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept-Charset", charset);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
			conn.connect();
			
			//Write Request
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(getParameter(param, charset));
            writer.flush();
			try{
				writer.close();
			}catch(IOException io){}
			
			//Get Response content
			String content = "";
			if (conn.getInputStream() != null){
				content = readData(conn.getInputStream());
			}
			
			result.setSuccess(true);
			result.setContent(content);
			result.setStatusCode(conn.getResponseCode());
		
		}catch(UnsupportedEncodingException e){
			result.setSuccess(false);
			result.setContent(e.getMessage());
			result.setException(e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setStatusCode(conn.getResponseCode());
			result.setContent(e.getMessage());
			result.setException(e);
		}finally{
			if (conn != null){
				try{
					conn.disconnect();
				}catch(Exception e){}
			}
		}
		return result;
	}
	
	private void initConfig()throws Exception{
		URL sUrl = new URL(url);
		if ( (isEmpty(proxyHost)) || (isEmpty(proxyPort)) ){
			if (isHttps()){
				conn = new HttpsURLConnection(sUrl);
			}else{
				conn = new HttpURLConnection(sUrl);
			}
		}else{
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, Integer.parseInt(proxyPort)));
			if (isHttps()){
				conn = new HttpsURLConnection(sUrl, proxy);
			}else{
				conn = new HttpURLConnection(sUrl, proxy);
			}
		}
		
		//Set Timeout
		conn.setConnectTimeout(timeout * 1000);		//milisecond
		conn.setReadTimeout(timeout * 1000);		//milisecond
		
		//Set Disable Certificate Validation
		if ( (conn instanceof HttpsURLConnection) && (disableCert) ){
			HttpsURLConnection tmp = (HttpsURLConnection)conn;
			tmp.setHostnameVerifier(new NulledHostnameVerifier());
			tmp.setTrustManager(new NulledTrustManager());
		}
	}
	
	private String readData(InputStream is)throws IOException{
		StringBuffer text = new StringBuffer();
		InputStreamReader in = new InputStreamReader(is);
	    BufferedReader buff = new BufferedReader(in);
	    String line;
		do{
			line = buff.readLine();
			if (line != null)
				text.append(line + "\n");
		}while (line != null);
		return text.toString();
	}
	
	private boolean isEmpty(String param){
		if ((param == null) || param.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}
	
	private String getParameter(Properties param)throws UnsupportedEncodingException{
		StringBuffer parameter = new StringBuffer();
		if (param != null && param.size() > 0){
			Enumeration e = param.propertyNames();
			while (e.hasMoreElements()){
				String key = (String)e.nextElement();
				String value = param.getProperty(key);
				
				parameter.append(key);
				parameter.append("=").append(value);
				parameter.append("&");
			}
		}
		return parameter.toString();
	}
	
	private String getParameter(Properties param, String charset)throws UnsupportedEncodingException{
		StringBuffer parameter = new StringBuffer();
		if (param != null && param.size() > 0){
			Enumeration e = param.propertyNames();
			while (e.hasMoreElements()){
				String key = (String)e.nextElement();
				String value = param.getProperty(key);
				
				parameter.append(key);
				parameter.append("=").append(URLEncoder.encode(value, charset));
				parameter.append("&");
			}
		}
		return parameter.toString();
	}

	private boolean isHttpSuccess(int status){
		return ((status / 100) == 2);
	}
	
	private boolean isHttps(){
		return (url.toLowerCase().indexOf("https") >= 0) ? true : false;
	}
}

