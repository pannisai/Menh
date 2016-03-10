package com.dtac.bmweb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.dtac.bmweb.exception.HttpClientException;

public class HttpClient {
	
	public static String getResponseString(String url) {
		org.apache.http.client.HttpClient httpClient = null;
		String responseBody = "";
		HttpGet httpGet = null;
		
		try {
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
			httpGet = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			responseBody = httpClient.execute(httpGet, responseHandler);

		} catch (ClientProtocolException e) {
			throw new HttpClientException("HttpClient Get Fail.", e);
		} catch (IOException e) {
			throw new HttpClientException("HttpClient Get Fail.", e);
		} catch (Exception ex) {
			throw new HttpClientException("HttpClient Get Fail.", ex);
		} finally {
			httpClient = null;
			httpGet = null;
		}

		return responseBody;
	}

	public static Map<String, String> get(String url) {
		String responseBody = "";
		Map<String, String> results = new HashMap<String, String>();
		try {
			responseBody = getResponseString(url);
			String[] splitAnd = responseBody.split("&");
			for (String tmp : splitAnd) {
				String[] splitEqual = tmp.split("=");
				if (splitEqual.length > 1) {
					results.put(splitEqual[0], splitEqual[1]);
				}
			}
		} catch (Exception ex) {
			throw new HttpClientException("HttpClient Get Fail.", ex);
		} finally {
		}
		return results;
	}
	
	public static String getResponseString(String url, String param) {
		org.apache.http.client.HttpClient httpClient = null;
		HttpPost httpPost = null;
		String responseBody = "";
		String line = "";
		
		try {
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
			httpPost = new HttpPost(url);
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		    nameValuePairs.add(new BasicNameValuePair("decodingParam",param));
		    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    
		    HttpResponse response = httpClient.execute(httpPost);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		    while ((line = reader.readLine()) != null) {
		    	responseBody = line;
		    }
		    reader.close();
		    httpClient.getConnectionManager().shutdown();
		} catch (ClientProtocolException e) {
			throw new HttpClientException("HttpClient Post Fail.", e);
		} catch (IOException e) {
			throw new HttpClientException("HttpClient Post Fail.", e);
		} catch (Exception ex) {
			throw new HttpClientException("HttpClient Post Fail.", ex);
		} finally {
			httpClient = null;
			httpPost = null;
			line = null;
		}

		return responseBody;
	}
	
	public static Map<String, String> getParameter(String url, String param) {
		String responseBody = "";
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> results = new HashMap<String, String>();			
		try {
			responseBody = getResponseString(url, param);
			results = mapper.readValue(responseBody, new TypeReference<HashMap<String,String>>(){});		
			if(results.get("param") != null && !"".equals(results.get("param"))){
				String[] splitAnd = results.get("param").split("&");
				for (String tmp : splitAnd) {
					String[] splitEqual = tmp.split("=");
					if (splitEqual.length > 1) {
						results.put(splitEqual[0], splitEqual[1]);
					}
				}
			}			
		} catch (Exception ex) {
			throw new HttpClientException("HttpClient Post Fail.", ex);
		} finally {
		}
		return results;
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void main(String[] args){	
		HttpClient hc = new HttpClient();				
		String url = "http://10.64.119.3:7241/MFSBackOffcService/DecryptSecureMessageServlet";
		String urlParamEncode = URLEncoder.encode("cBU6QCh05W1KNXVsfPS5m+/iXTQ1JCaiLWClbxZZanTTVtT3ck6bbEEkybwhbPVg+JPn3Qc1GiDQTKMM3bG9f9Ch8f8aJze0vC8TJGavFzDPEoxPFwXkw4nK6A3cW2f+9UO9uj2O+GRzRvSgL2McmOKpdWuENQaFfzeftcMYMIg267NPqgV3+0Yo/+vcfxUIm/LKp2ZtG4il/6lGBGxqgZL/K8+PkeXmm5OOnDlpIOPcbByFJ4XWzeDB+GWjV96x");
		System.out.println("urlParamEncode : "+urlParamEncode);

		Map<String, String> map = hc.getParameter(url, urlParamEncode);
		System.out.println("map : "+map);
	}
}
