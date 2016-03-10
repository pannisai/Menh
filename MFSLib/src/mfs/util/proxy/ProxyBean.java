package mfs.util.proxy;

public class ProxyBean {
	
	private String partnerHost;
	private String host;
	private int port;
	private String detail;
	
	public String toString(){
		return partnerHost + "|" + host + "|" + port + "|" + detail;
	}
	
	public String getPartnerHost() {
		return partnerHost;
	}
	public void setPartnerHost(String partnerHost) {
		this.partnerHost = partnerHost;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
