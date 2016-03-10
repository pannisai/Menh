package com.dtac.billerweb.common;

import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.ReportTransDetail;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

import com.dtac.billerweb.data.BankReportTranSO;
import com.dtac.billerweb.exception.DialogException;
import com.dtac.billerweb.util.AppUtil;

public class Dialog {
	private static Logger log = Logger.getLogger(Dialog.class);

	public static void openBw1110MedalDialog(String callbackUrl, Integer oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw1110Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw1110Dialog/bw1110DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW1110_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW1110 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw1210MedalDialog(String callbackUrl, Integer oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw1210Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw1210Dialog/bw1210DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW1210_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW1210 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw1310MedalDialog(String callbackUrl, Integer oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw1310Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw1310Dialog/bw1310DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW1310_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW1210 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw1430MedalDialog(String callbackUrl, String oid, String serviceId) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw1430Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw1430Dialog/bw1430DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setDynamicProperty("serviceId", serviceId);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW1430_REDIRECT_PATH));
			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW1430 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw1440MedalDialog(String callbackUrl, Integer oid, Integer billerFormId, Integer serviceId) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw1440Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw1440Dialog/bw1440DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setDynamicProperty("serviceId", serviceId);
			include.setDynamicProperty("formId", billerFormId);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW1440_REDIRECT_PATH));
			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW1440 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw1450MedalDialog(String callbackUrl, Integer serviceId, Integer inputRefId, Integer oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw1450Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw1450Dialog/bw1450DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setDynamicProperty("inputRefId", inputRefId);
			include.setDynamicProperty("serviceId", serviceId);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW1450_REDIRECT_PATH));
			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW1450 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw1460MedalDialog(String callbackUrl, Integer serviceId,Integer oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw1460Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw1460Dialog/bw1460DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setDynamicProperty("serviceId", serviceId);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW1460_REDIRECT_PATH));
			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW1460 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw1510MedalDialog(String callbackUrl, String oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw1510Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw1510Dialog/bw1510DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW1510_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW1510 Dialog fail", ex);
		} finally {
			include = null;
		}
	}
	
	public static void openBw1610MedalDialog(String callbackUrl, String oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw1610Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw1610Dialog/bw1610DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW1610_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW1610 Dialog fail", ex);
		} finally {
			include = null;
		}
	}
	
	public static void openBw1710MedalDialog(String callbackUrl, Integer oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw1710Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw1710Dialog/bw1710DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW1710_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW1710 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw2110MedalDialog(String callback, String inbn_SRVC_ID, String srce_SRVC_ID, String dest_SRVC_ID) {
		log.info("Open openBw2110MedalDialog:[Callback:" + callback + ",inbn_SRVC_ID:" + inbn_SRVC_ID + ",srce_SRVC_ID:" + srce_SRVC_ID + ",dest_SRVC_ID:" + dest_SRVC_ID);
		Include include = null;
		try {
			Map<String, String> arg = new HashMap<String, String>();
			arg.put("dialog", "true");
			Window window = (Window) Executions.createComponents("/page/dialog/bw2110Dialog.zul", null, arg);
			include = (Include) Path.getComponent("/bw2110Dialog/bw2110DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("callback", callback);
			include.setDynamicProperty("inbn_SRVC_ID", inbn_SRVC_ID);
			include.setDynamicProperty("srce_SRVC_ID", srce_SRVC_ID);
			include.setDynamicProperty("dest_SRVC_ID", dest_SRVC_ID);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW2110_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW2110 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw2130MedalDialog(String callbackUrl, Integer oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw2130Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw2130Dialog/bw2130DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW2130_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW2130 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw2140MedalDialog(String callbackUrl, Integer oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw2140Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw2140Dialog/bw2140DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW2140_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {

			throw new DialogException("Open BW2140 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw2210MedalDialog(String callback, Integer oid) {
		log.info("Open openBw2210MedalDialog:[Callback:" + callback + ",OID:" + oid);
		Include include = null;
		try {
			Map<String, String> arg = new HashMap<String, String>();
			arg.put("dialog", "true");
			Window window = (Window) Executions.createComponents("/page/dialog/bw2210Dialog.zul", null, arg);
			include = (Include) Path.getComponent("/bw2210Dialog/bw2210DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("callback", callback);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW2210_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW2210 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw2230MedalDialog(String callbackUrl, Integer oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw2230Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw2230Dialog/bw2230DialogInclude");
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW2230_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW2230 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw2310MedalDialog(String callbackUrl, Integer oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw2310Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw2310Dialog/bw2310DialogInclude");
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW2310_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW2310 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw2330MedalDialog(String callbackUrl, Integer oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw2330Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw2330Dialog/bw2330DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW2330_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {

			throw new DialogException("Open BW2330 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw3030MedalDialog(String callbackUrl, ReportTransDetail reportTransDetail, Integer no) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw3030Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw3030Dialog/bw3030DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("reportTransDetail", reportTransDetail);
			include.setDynamicProperty("NO", AppUtil.toString(no));
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW3030_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW3030 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw4110MedalDialog(String callbackUrl, String oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw4110Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw4110Dialog/bw4110DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW4110_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW4110 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw4210MedalDialog(String callbackUrl, String oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw4210Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw4210Dialog/bw4210DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW4210_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW4210 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw5110MedalDialog(String callbackUrl, String oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw5110Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw5110Dialog/bw5110DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW5110_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW5110 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw5210MedalDialog(String callbackUrl, String oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw5210Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw5210Dialog/bw5210DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW5210_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW5210 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw5230MedalDialog(String callbackUrl, Integer oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw5230Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw5230Dialog/bw5230DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW5230_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {

			throw new DialogException("Open BW5230 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw5240MedalDialog(String callbackUrl, Integer oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw5240Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw5240Dialog/bw5240DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW5240_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {

			throw new DialogException("Open BW5240 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw5250MedalDialog(String callbackUrl, Integer oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw5250Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw5250Dialog/bw5250DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW5250_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {

			throw new DialogException("Open BW5250 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw5310MedalDialog(String callbackUrl, String oid) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw5310Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw5310Dialog/bw5310DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("oid", oid);
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW5310_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW5310 Dialog fail", ex);
		} finally {
			include = null;
		}
	}

	public static void openBw6030MedalDialog(String callbackUrl, BankReportTranSO bankReportTranSO, Integer no) {
		Include include = null;
		try {
			Window window = (Window) Executions.createComponents("/page/dialog/bw6030Dialog.zul", null, null);
			include = (Include) Path.getComponent("/bw6030Dialog/bw6030DialogInclude");
			log.debug("include::" + include);
			include.setDynamicProperty("dialog", "true");
			include.setDynamicProperty("callback", callbackUrl);
			include.setDynamicProperty("bankReportTranSO", bankReportTranSO);
			include.setDynamicProperty("NO", AppUtil.toString(no));
			include.setMode("instant");
			include.setSrc(AppConfiguration.getValue(AppConfiguration.BW6030_REDIRECT_PATH));

			window.doModal();

		} catch (Exception ex) {
			throw new DialogException("Open BW6030 Dialog fail", ex);
		} finally {
			include = null;
		}
	}
}
