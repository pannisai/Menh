package mfs.util.proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

public class CustomProxySelector extends ProxySelector{
	
	private final ProxySelector def;
	private Hashtable<String, ProxyBean> ht = new Hashtable<String, ProxyBean>();
	final private static Logger log = Logger.getLogger(CustomProxySelector.class);
	
	public CustomProxySelector(ProxySelector aDefault){
		this.def = aDefault;
		
		List<ProxyBean> list = ProxyUtil.getInstance().getProxyList("JAX-WS");
		log.debug("Load Config JAX-WS Size : " + list.size());
		if (list != null && !list.isEmpty()){
			ProxyBean proxy;
			for (int i = 0; i < list.size(); i++){
				proxy = (ProxyBean)list.get(i);
				log.debug(proxy.getDetail() + "|" + proxy.getHost() + "|" + proxy.getPort());
				ht.put(proxy.getPartnerHost(), proxy);
			}
		}
		log.debug("Customer Proxy List Size : " + ht.size());
	}

	@Override
	public List<Proxy> select(URI uri) {
		//System.out.println("URL : " + uri);
		//System.out.println("HOST : " + uri.getHost());
		log.debug("URL:" + uri);
		if (ht.containsKey(uri.getHost())){
			ProxyBean bean = (ProxyBean)ht.get(uri.getHost());
			
			Proxy proxy;
			if ("socket".equalsIgnoreCase(uri.getScheme())){
				proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(bean.getHost(), bean.getPort()));
			}else{
				proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(bean.getHost(), bean.getPort()));
			}
			
			log.debug("use proxy:" + bean.getHost() + ":" + bean.getPort());
			
			List<Proxy> proxyList = new ArrayList<Proxy>();
			proxyList.add(proxy);
			return proxyList;
			
		}else{
			//return default proxy
			log.debug("use proxy default");
			List<Proxy> select = def.select(uri);
			return select;
		}
	}

	@Override
	public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}

