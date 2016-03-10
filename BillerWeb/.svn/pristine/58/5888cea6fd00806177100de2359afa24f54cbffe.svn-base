package com.dtac.billerweb.form;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;

import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.serviceImpl.ChannelManagementWS;

/**
 * This class is used to resend release code of OTC to KTB cash transaction.
 * Shiv
 * 
 * 26-Feb-2015
 */
public class ResendCodeButton extends Button {
	private Logger log = Logger.getLogger(ResendCodeButton.class);

	private static final long serialVersionUID = -4204769213880023425L;

	/**
	 * On click get the id of the button, from id we get fundamo transaction ref
	 * to send release code.
	 * 
	 * @param event
	 */
	public void onClick(MouseEvent event) {
		try {
			log.info("ResendCodeButton:" + event.getTarget().getId());
			String btID = event.getTarget().getId();
			String BT_RESEND_CODE_PREFIX = "btResendCode_";
			if (btID.indexOf(BT_RESEND_CODE_PREFIX) > -1) {
				String fundamoTransactionId = btID.substring(BT_RESEND_CODE_PREFIX.length());
				log.info("Fundamo Transaction Ref::" + fundamoTransactionId);
				ChannelManagementWS service = new ChannelManagementWS();
				boolean isSuccess = service.resendReleaseCode(fundamoTransactionId);
				if (isSuccess) {
					Messagebox.show("Resend Code Success!", "", Messagebox.OK, Messagebox.INFORMATION);
				} else {
					Messagebox.show("Resend Code Failure. Please try later!", "", Messagebox.OK, Messagebox.EXCLAMATION);
				}
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}
}
