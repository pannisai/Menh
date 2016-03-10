package com.dtac.billerweb.common;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;

import com.dtac.billerweb.data.InboundGatewaySOPK;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.util.AppUtil;

public abstract class EditPageController extends BaseControllor {
	private Logger log = Logger.getLogger(EditPageController.class);
	private boolean dialog = false;
	protected String callback = "";
	protected Integer oid;

	public EditPageController(String pageName,String pageCode) {
		super.pageName = pageName;
		super.pageCode = pageCode;
	}

	public void doAfterCompose(Component com) throws Exception {
		Date requestDate = AppUtil.getCurrent();
		super.doAfterCompose(com);
		try {
			log.info(getOperationLogMessage(pageName, "Init Page", "Start Do AffterCompose"));
			dialog = isDialog();
			callback = getDialogCallBackUrl();
			String oidStr = "";
			oidStr = getArgument("oid");
			if (AppUtil.isEmpty(oidStr)) {
				oidStr = getParameter("oid");
			}
			if (!AppUtil.isEmpty(oidStr)) {
				oid = Integer.parseInt(oidStr);
			} else {
				oid = -1;
			}
			log.info("oid::" + oid);
			log.info("dialog ::" + dialog);
			init();

		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Init Page", "OID::" + oid, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#init", requestDate, ex);
		} finally {

		}
		log.info("Stop:" + System.currentTimeMillis());
		setTxLogMessage(pageCode + "#init", requestDate, "");
	}

	public void doFinally() throws Exception {
		super.doFinally();
		checkSessionTimeOut("Initial " + pageName);
	}

	protected abstract void init() throws Exception;

	protected boolean isDialog() {
		if (!dialog) {
			String dialogStr = getArgument("dialog");
			if (dialogStr.equalsIgnoreCase("true")) {
				dialog = true;
			}
		}
		return dialog;

	}

	private String getDialogCallBackUrl() {
		String callback = "";
		try {
			callback = getArgument("callback");
			log.debug("Callback::" + callback);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return callback;
	}

}
