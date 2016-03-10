package com.dtac.billerweb.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import com.dtac.billerweb.data.BillerCollectionFeeAbsorbDTO;
import com.dtac.billerweb.data.BillerCollectionFeeAbsorbData;
import com.dtac.billerweb.data.BillerCollectionFeeShareDTO;
import com.dtac.billerweb.data.BillerCollectionFeeShareData;
import com.dtac.billerweb.exception.HttpClientException;
import com.dtac.billerweb.util.AppUtil;

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

//	public static Map<String, String> get(String url) {
//		String responseBody = "";
//		Map<String, String> results = new HashMap<String, String>();
//		try {
//			responseBody = getResponseString(url);
//			String[] splitAnd = responseBody.split("&");
//			for (String tmp : splitAnd) {
//				String[] splitEqual = tmp.split("=");
//				if (splitEqual.length > 1) {
//					results.put(splitEqual[0], splitEqual[1]);
//				}
//			}
//		} catch (Exception ex) {
//			throw new HttpClientException("HttpClient Get Fail.", ex);
//		} finally {
//		}
//		return results;
//	}

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
	
	public static String getJSONListFromHttpPost(String url) {
		org.apache.http.client.HttpClient httpClient = null;
		String responseBody = "";
		HttpPost httpPost = null;
		String line = "";
		try {			
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
			httpPost = new HttpPost(url);			
//			ResponseHandler<String> responseHandler = new BasicResponseHandler();
//			responseBody = httpClient.execute(httpPost, responseHandler);
			
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

	public static List<BillerCollectionFeeAbsorbData> getBillerFeeAbsorb(String url) throws Exception{
		String responseBody = "";
		ObjectMapper mapper = new ObjectMapper();
		BillerCollectionFeeAbsorbDTO billerCollectionFeeAbsorbDTO = null;
		List<BillerCollectionFeeAbsorbData> billerCollectionFeeAbsorbData = new ArrayList<BillerCollectionFeeAbsorbData>();		
		try {
			responseBody = getJSONListFromHttpPost(url);			
			billerCollectionFeeAbsorbDTO = mapper.readValue(responseBody, BillerCollectionFeeAbsorbDTO.class);
			if(AppUtil.isNotNull(billerCollectionFeeAbsorbDTO)){
				billerCollectionFeeAbsorbData = billerCollectionFeeAbsorbDTO.getDataList();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			responseBody = null;
			mapper = null;
			billerCollectionFeeAbsorbDTO = null;
		}
		return billerCollectionFeeAbsorbData;
	}
	
	public static List<BillerCollectionFeeShareData> getBillerFeeShare(String url) throws Exception{
		String responseBody = "";
		ObjectMapper mapper = new ObjectMapper();
		BillerCollectionFeeShareDTO billerCollectionFeeShareDTO = null;
		List<BillerCollectionFeeShareData> billerCollectionFeeShareData = new ArrayList<BillerCollectionFeeShareData>();		
		try {
			responseBody = getJSONListFromHttpPost(url);			
			billerCollectionFeeShareDTO = mapper.readValue(responseBody, BillerCollectionFeeShareDTO.class);
			if(AppUtil.isNotNull(billerCollectionFeeShareDTO)){
				billerCollectionFeeShareData = billerCollectionFeeShareDTO.getDataList();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			responseBody = null;
			mapper = null;
			billerCollectionFeeShareDTO = null;
		}
		return billerCollectionFeeShareData;
	}
	
	@SuppressWarnings({ "static-access" })
	public static void main(String[] args) throws Exception{	
		HttpClient hc = new HttpClient();						
		StringBuilder params = null;		
		try {
			params = new StringBuilder();
			params.append("reportType=A");
			params.append("&billerShortCode=0001");
			params.append("&fromDate=01-06-2015");
			params.append("&toDate=26-06-2015");
			params.append("&refId=2014042916221870010.35.206.75");
			params.append("&ipAddr=10.35.206.75");
			List<BillerCollectionFeeAbsorbData> billerCollectionFeeAbsorbData = hc.getBillerFeeAbsorb("http://10.64.119.3:7241/MFSBackOffcService/BillerCollectionFeeServlet?reportType=A&billerShortCode=0001&fromDate=01-06-2015&toDate=26-06-2015&userName=receipt&refId=2014042916221870010.35.206.75&ipAddr=10.35.206.75");		
			if(AppUtil.isNotNull(billerCollectionFeeAbsorbData)){
				System.out.println("BillerCollectionFeeAbsorbData : "+billerCollectionFeeAbsorbData);
				System.out.println(billerCollectionFeeAbsorbData.size());
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
		}		
	}
}
