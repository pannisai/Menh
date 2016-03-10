package mfs.util.proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.axis.AxisProperties;

import mfs.util.biller.ConstantUtil;

public class ProxyUtil{
	
	private static final String fileProxy = ConstantUtil.CONFIG_PATH + "/proxy.properties";
	private static final String fileProxyDetail = ConstantUtil.CONFIG_PATH + "/proxy_detail.properties";
	private static ProxyUtil instance = null;
	
	private List<String> listProxy = new ArrayList<String>();
	private long lastModified;
	private long latestLastModified;
	
	private List<String> listProxyDetail = new ArrayList<String>();
	private long lastModifiedDetail;
	private long latestLastModifiedDetail;
	
	public static synchronized ProxyUtil getInstance() {
		if (instance == null){
			instance = new ProxyUtil();
		}
		return instance;
	}
	
	public List<ProxyBean> getProxyList(String type){
		List<ProxyBean> listProx = new ArrayList<ProxyBean>();
		Hashtable<String, ProxyBean> ht = getProxyDetail();
		List<String> list = readFileProxy();
		if (list != null && !list.isEmpty()){
			ProxyBean proxy;
			ProxyBean proxyTmp;
			for (int i = 0; i < list.size(); i++){
				String[] tmp = ((String)list.get(i)).split("\\|");
				if (tmp.length==4 && type.equalsIgnoreCase(tmp[3])){
					proxyTmp = (ProxyBean)ht.get(tmp[2]);
					proxy = new ProxyBean();
					proxy.setPartnerHost(tmp[0]);
					proxy.setHost(proxyTmp.getHost());
					proxy.setPort(proxyTmp.getPort());
					proxy.setDetail(tmp[1]);
					
					listProx.add(proxy);
				}
			}
		}
		return listProx;
	}
	
	public ProxyBean getProxy(String url){
		try{
			URI uri = new URI(url);
			String host = uri.getHost();
			
			Hashtable<String, ProxyBean> ht = getProxyDetail();
			List<String> list = readFileProxy();
			if (list != null && !list.isEmpty()){
				for (int i = 0; i < list.size(); i++){
					String[] tmp = ((String)list.get(i)).split("\\|");
					if (tmp.length==4 && host.equalsIgnoreCase(tmp[0])){
						ProxyBean proxyTmp = (ProxyBean)ht.get(tmp[2]);
						ProxyBean proxy = new ProxyBean();
						proxy.setPartnerHost(tmp[0]);
						proxy.setHost(proxyTmp.getHost());
						proxy.setPort(proxyTmp.getPort());
						proxy.setDetail(tmp[1]);
						
						//System.out.println("getProxy : " + proxy.toString());
						
						return proxy;
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void setAxisProxy(String url){
		ProxyBean proxy = getProxy(url);
		if (proxy != null){
			AxisProperties.setProperty("http.proxyHost", proxy.getHost());
			AxisProperties.setProperty("http.proxyPort", proxy.getPort() + "");
		}
	}
	
	public ProxyUtil(){
		lastModified = 1;
		lastModifiedDetail = 1;
	}
	
	private Hashtable<String, ProxyBean> getProxyDetail(){
		Hashtable<String, ProxyBean> ht = new Hashtable<String, ProxyBean>();
		List<String> list = readFileProxyDetail();
		if (list != null && !list.isEmpty()){
			ProxyBean proxy;
			for (int i = 0; i < list.size(); i++){
				String[] tmp = ((String)list.get(i)).split("\\|");
				if (tmp.length==4 && isNumeric(tmp[2])){
					proxy = new ProxyBean();
					proxy.setHost(tmp[1]);
					proxy.setPort(Integer.parseInt(tmp[2]));
					proxy.setDetail(tmp[3]);
					ht.put(tmp[0], proxy);
				}
			}
		}
		return ht;
	}
	
	private List<String> readFileProxy(){
		if (isUpdated()){
			//System.out.println("Reload File Proxy");
			updateLastModified();
			listProxy.clear();
			
			try{
				BufferedReader br = new BufferedReader(new FileReader(fileProxy));
				String line;
				while ((line = br.readLine()) != null) {
					listProxy.add(line);
				}
				br.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return listProxy;
	}
	
	private List<String> readFileProxyDetail(){
		if (isUpdatedDetail()){
			//System.out.println("Reload File ProxyDetail");
			updateLastModifiedDetail();
			listProxyDetail.clear();
			
			try{
				BufferedReader br = new BufferedReader(new FileReader(fileProxyDetail));
				String line;
				while ((line = br.readLine()) != null) {
					listProxyDetail.add(line);
				}
				br.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return listProxyDetail;
	}
	
	private void updateLastModified() {
		File file = new File(fileProxy);
		lastModified = file.lastModified();
	}
	
	private void updateLastModifiedDetail() {
		File file = new File(fileProxyDetail);
		lastModifiedDetail = file.lastModified();
	}
	
	private boolean isUpdated(){
		File file = new File(fileProxy);
		latestLastModified = file.lastModified();
		return (latestLastModified != lastModified) ? true : false;
	}
	
	private boolean isUpdatedDetail(){
		File file = new File(fileProxyDetail);
		latestLastModifiedDetail = file.lastModified();
		return (latestLastModifiedDetail != lastModifiedDetail) ? true : false;
	}
	
	private boolean isNumeric(String param) {
		return Pattern.compile("^[0-9]+$").matcher(param).matches();
	}
}
