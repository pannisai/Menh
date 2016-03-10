package mfs.util.http;

import weblogic.security.SSL.TrustManager;
import java.security.cert.X509Certificate;

public class NulledTrustManager implements TrustManager{ 
	
	public boolean certificateCallback(X509Certificate[] o, int validateErr) {
//		System.out.println(" --- Do Not Use In Production ---\n" + 
//                          " By using this NulledTrustManager, the trust in" +
//                            " the server's identity is completely lost.\n" +                           " --------------------------------");
//		for (int i=0; i<o.length; i++)
//			System.out.println(" certificate " + i + " -- " + o[i].toString());
		return true;
	}
}