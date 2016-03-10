package mfs.util.http;

public class NulledHostnameVerifier implements weblogic.security.SSL.HostnameVerifier {
	
	public boolean verify(String urlHostname, javax.net.ssl.SSLSession session) {
		return true;
	}
}