package com.dtac.billerweb.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
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
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.dtac.billerweb.common.ReportConstants;
import com.dtac.billerweb.data.BillerCollectionFee;
import com.dtac.billerweb.data.BillerCollectionFeeAbsorbDTO;
import com.dtac.billerweb.data.BillerCollectionFeeShareDTO;
import com.dtac.billerweb.exception.HttpClientException;

public class HttpClient {
	private static Logger log=Logger.getLogger(HttpClient.class);
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

//	public static void main(String[] args) {
//		HttpClient hc = new HttpClient();
//		Map<String, String> results = hc.get("http://10.64.119.4:7141/MFSBackOffcService/QueryPermissionServlet?userName=jose&refId=2013112810493203010.35.203.116&clientIpAddr=10.35.203.116&menuId=14");
//		System.out.println("REsults:"+results.size());
//	}
	
	public static String getBillerFee(String url) {
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
			
			log.info("Url : "+url);
		    HttpResponse response = httpClient.execute(httpPost);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
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
	
	public static BillerCollectionFee getBillerFeeReport(String url) {
		org.apache.http.client.HttpClient httpClient = null;
		HttpPost httpPost = null;
		HttpResponse response = null;
			
		BillerCollectionFee billerCollectionFee = new BillerCollectionFee();
		Map<String, String> mapResults = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		String responseBody = null;
		String contentType = null;
		String line = null;
		byte[] bytes = null;
		try {	
			// Create the request
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter("http.protocol.content-charset", "UTF-8");		
			httpPost = new HttpPost(url);
			// Send the request			
			response = httpClient.execute(httpPost);
			log.info("Url : "+url);
			log.info("AllHeaders : "+Arrays.toString(response.getAllHeaders()));
				
			if(AppUtil.isNotNull(response.getEntity().getContentType())){
				contentType = response.getEntity().getContentType().toString();			
				if(contentType.indexOf(ReportConstants.TYPE_XLS_CONTENT) > -1){
					for (Header header : response.getHeaders("Content-Disposition")) {
						for (HeaderElement element : header.getElements()) {
							NameValuePair filenameParameter = element.getParameterByName("filename");
							if (filenameParameter != null) {
								billerCollectionFee.setFileName(filenameParameter.getValue());
							}
						}
					}
					billerCollectionFee.setContentType(ReportConstants.TYPE_XLS_CONTENT);				
					ByteArrayOutputStream out = new ByteArrayOutputStream();
				    response.getEntity().writeTo(out);
				    bytes = out.toByteArray();
				    billerCollectionFee.setBytes(bytes);
				    out.close();
				}else if(contentType.indexOf(ReportConstants.TYPE_TEXT_PLAIN_CONTENT) > -1){
					billerCollectionFee.setContentType(ReportConstants.TYPE_TEXT_PLAIN_CONTENT);			
					BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
				    while ((line = reader.readLine()) != null) {
				    	responseBody = line;
				    }
				    reader.close();			    
				    mapResults = mapper.readValue(responseBody, new TypeReference<HashMap<String,String>>(){});							
					billerCollectionFee.setMapResults(mapResults);
				}
			}		
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
			
			mapResults = null;
			mapper = null;
			responseBody = null;
			contentType = null;
			line = null;
			bytes = null;
		}
		return billerCollectionFee;
	}

	public static BillerCollectionFeeAbsorbDTO getBillerFeeAbsorb(String url) throws Exception{
		String responseBody = "";
		ObjectMapper mapper = new ObjectMapper();
		BillerCollectionFeeAbsorbDTO billerCollectionFeeAbsorbDTO = new BillerCollectionFeeAbsorbDTO();	
		billerCollectionFeeAbsorbDTO.setDataList(null);
		try {
			responseBody = getBillerFee(url);			
			billerCollectionFeeAbsorbDTO = mapper.readValue(responseBody, BillerCollectionFeeAbsorbDTO.class);
		} catch (Exception e) {
			throw e;
		} finally {
			responseBody = null;
			mapper = null;
		}
		return billerCollectionFeeAbsorbDTO;
	}
	
	public static BillerCollectionFeeShareDTO getBillerFeeShare(String url) throws Exception{
		String responseBody = "";
		ObjectMapper mapper = new ObjectMapper();
		BillerCollectionFeeShareDTO billerCollectionFeeShareDTO = new BillerCollectionFeeShareDTO();
		billerCollectionFeeShareDTO.setDataList(null);
		try {
			responseBody = getBillerFee(url);			
			billerCollectionFeeShareDTO = mapper.readValue(responseBody, BillerCollectionFeeShareDTO.class);
		} catch (Exception e) {
			throw e;
		} finally {
			responseBody = null;
			mapper = null;
		}
		return billerCollectionFeeShareDTO;
	}
	
	@SuppressWarnings({ "unused", "static-access" })
	public static void main(String[] args) throws Exception{	
		HttpClient hc = new HttpClient();						
		StringBuilder params = null;	
		BillerCollectionFee billerCollectionFee = new BillerCollectionFee();

		try {
			params = new StringBuilder();
			params.append("reportType=A");
			params.append("&billerShortCode=0001");
			params.append("&fromDate=01-06-2015");
			params.append("&toDate=26-06-2015");
			params.append("&refId=2014042916221870010.35.206.75");
			params.append("&ipAddr=10.35.206.75");
			//### Test1 ###
//			BillerCollectionFeeAbsorbDTO billerCollectionFeeAbsorbDTO = hc.getBillerFeeAbsorb("http://10.64.119.3:7241/MFSBackOffcService/BillerCollectionFeeServlet?reportType=A&billerShortCode=0031&fromDate=01-06-2015&toDate=26-06-2015&userName=receipt&refId=2014042916221870010.35.206.75&ipAddr=10.35.206.75");		
//			if(AppUtil.isNotNull(billerCollectionFeeAbsorbDTO)){
//				if(AppUtil.isNotNull(billerCollectionFeeAbsorbDTO.getDataList())){					
//					System.out.println(billerCollectionFeeAbsorbDTO.getDataList().size());
//				}else{
//					System.out.println("ErrorCode : "+billerCollectionFeeAbsorbDTO.getErrorCode());
//					System.out.println("ErrorMssg : "+billerCollectionFeeAbsorbDTO.getErrorMssg());
//				}
//			}
			//### Test2 ###
			BillerCollectionFeeShareDTO billerCollectionFeeShareDTO = hc.getBillerFeeShare("http://10.64.119.3:7241/MFSBackOffcService/BillerCollectionFeeServlet?reportType=S&billerShortCode=0001&fromDate=01-06-2015&toDate=25-06-2015&userName=billert1&refId=2015072810371383010.35.200.181&ipAddr=10.35.200.181");		
			if(AppUtil.isNotNull(billerCollectionFeeShareDTO)){
				if(AppUtil.isNotNull(billerCollectionFeeShareDTO.getDataList())){					
					System.out.println(billerCollectionFeeShareDTO.getDataList().size());
				}else{
					System.out.println("ErrorCode : "+billerCollectionFeeShareDTO.getErrorCode());
					System.out.println("ErrorMssg : "+billerCollectionFeeShareDTO.getErrorMssg());
				}
			}			
			//### Test BL ###			
//			billerCollectionFee = hc.getBillerFeeReport("http://10.64.119.3:7241/MFSBackOffcService/BillerCollectionFeeExportServlet?reportType=A&billerShortCode=0001&fromDate=01-06-2015&toDate=26-06-2015&exportType=BL&userName=receipt&refId=2014042916221870010.35.206.75&ipAddr=10.35.206.75");			
			//### Test AP ###			
//			billerCollectionFee = hc.getBillerFeeReport("http://10.64.119.3:7241/MFSBackOffcService/BillerCollectionFeeExportServlet?reportType=A&billerShortCode=0001&fromDate=01-06-2015&toDate=26-06-2015&exportType=AP&userName=receipt&refId=2014042916221870010.35.206.75&ipAddr=10.35.206.75");			
//			System.out.println("errorCode : "+billerCollectionFee.getMapResults().get("errorCode"));
//			System.out.println("errorMssg : "+billerCollectionFee.getMapResults().get("errorMssg"));
		} catch (Exception ex) {
			throw ex;
		} finally {
		}		
	}
}
