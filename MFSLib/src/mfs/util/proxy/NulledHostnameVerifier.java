package mfs.util.proxy;

public class NulledHostnameVerifier implements javax.net.ssl.HostnameVerifier{
	
	public boolean verify(String urlHostname, javax.net.ssl.SSLSession session){
		return true;
	}
}
