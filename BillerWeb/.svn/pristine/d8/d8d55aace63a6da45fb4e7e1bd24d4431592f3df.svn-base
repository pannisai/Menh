//package com.dtac.billerweb.form;
//
//import java.net.URLEncoder;
//
//import org.apache.log4j.Logger;
//import org.zkoss.zk.ui.Executions;
//import org.zkoss.zk.ui.event.MouseEvent;
//import org.zkoss.zul.A;
//
//import com.dtac.billerweb.util.AppConfiguration;
//
//public class InboundGatewayALink extends A {
//	private Logger log = Logger.getLogger(InboundGatewayALink.class);
//	
//	public void onClick(MouseEvent event) {
//		log.info("Click Edit inbound gateway Link");
//		try {
//			String id = event.getTarget().getId();
//			String[] splits = id.split("#");
//			String bllr_SRVC_ID = "";
//			String srce_SRVC_ID = "";
//			String dest_SRVC_ID = "";
//			if (splits.length == 3) {
//				bllr_SRVC_ID = splits[0];
//				srce_SRVC_ID = splits[1];
//				dest_SRVC_ID = splits[2];
//			}
//			log.debug("Split length="+splits.length);
//			log.debug(bllr_SRVC_ID);
//			log.debug(srce_SRVC_ID);
//			log.debug(dest_SRVC_ID);
//			String queryString = "bllr_SRVC_ID=" + URLEncoder.encode(bllr_SRVC_ID)
//					+ "&srce_SRVC_ID=" + URLEncoder.encode(srce_SRVC_ID) + "&dest_SRVC_ID="
//					+ URLEncoder.encode(dest_SRVC_ID);
//			Executions.getCurrent().sendRedirect(AppConfiguration.getValue(AppConfiguration.BW2110_REDIRECT_PATH)+"?"+
//					queryString);
//		} catch (Exception ex) {
//			throw new RuntimeException(ex);
//		}
//	}
//}
