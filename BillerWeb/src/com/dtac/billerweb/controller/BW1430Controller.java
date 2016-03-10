package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BillerServiceChannel;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.exception.DialogException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1413Form;
import com.dtac.billerweb.service.BillerServiceService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1430Controller extends EditPageController {

	public BW1430Controller() {
		super("EDIT Biller Service/Channel","1430");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW1430Controller.class);
	private BW1413Form bw1413Form = new BW1413Form();

	@Wire
	private Label title;
	@Wire
	private Textbox txServiceCode;
	@Wire
	private Radiogroup rdAllowedKeyInFlag;
	@Wire
	private Radiogroup rdAllowedBarcodeFlag;
	@Wire
	private Listbox lbActiveFlag;

	@Wire
	private Button btSave;
	@Wire
	private Button btCancel;

	private Integer billerServiceId;

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub
		String serviceID = getArgument("serviceId");
		if (!AppUtil.isEmpty(serviceID)) {
			billerServiceId = Integer.parseInt(serviceID);
		} else {
			billerServiceId = -1;
		}
		if (billerServiceId != -1 && oid != -1) {
			setupEditPage();
		} else {
			showErrorMessage("ID not found!");
		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			 i = (Include) Path.getComponent("/bw1430Dialog/bw1430DialogInclude");
			 comps = i.getFellows();
			if (chkUpdatePermission(comps, btSave)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW1430_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW1430_VIEW_TITLE));
			}
			loadFormData();
			refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i=null;
			comps=null;
		}
	}

	private void loadFormData() {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BillerServiceService billerServiceService = null;
		BillerServiceChannel billerServiceChannel = null;
		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			billerServiceChannel = billerServiceService.getBillerServiceChannelByID(oid, billerServiceId, getUserInfo());
			bw1413Form = bw1413Form.toBW1413Form(billerServiceChannel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
	}

	private void refreshForm() {

		try {
			txServiceCode.setValue(bw1413Form.getBillerChannelCode());
			if (!AppUtil.isEmpty(bw1413Form.getAllowedKeyIn()) && bw1413Form.getAllowedKeyIn().equalsIgnoreCase("N")) {
				rdAllowedKeyInFlag.setSelectedIndex(1);
			} else {
				rdAllowedKeyInFlag.setSelectedIndex(0);
			}
			if (!AppUtil.isEmpty(bw1413Form.getAllowedBarcode()) && bw1413Form.getAllowedBarcode().equalsIgnoreCase("N")) {
				rdAllowedBarcodeFlag.setSelectedIndex(1);
			} else {
				rdAllowedBarcodeFlag.setSelectedIndex(0);
			}

			if (!AppUtil.isEmpty(bw1413Form.getActiveFlag()) && bw1413Form.getActiveFlag().equalsIgnoreCase("A")) {
				lbActiveFlag.setSelectedIndex(0);
			} else {
				lbActiveFlag.setSelectedIndex(1);
			}

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	public void updateBW1413Form() {
		try {

			bw1413Form.setBillerChannelId(oid);
			bw1413Form.setBillerServiceId(billerServiceId);
			bw1413Form.setBillerChannelCode(txServiceCode.getValue());
			bw1413Form.setAllowedKeyIn(rdAllowedKeyInFlag.getSelectedItem().getValue().toString());
			bw1413Form.setAllowedBarcode(rdAllowedBarcodeFlag.getSelectedItem().getValue().toString());
			bw1413Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	/* ## Event ## */
	@Listen("onClick = #btSave")
	public void clickSave() {
		checkSessionTimeOut("Update " + "Channel");
		try {
			log.info(getOperationLogMessage(pageName, "Save Biller Service/Channel", ""));
			updateBW1413Form();
			if (billerServiceId != -1 && oid != -1) {
				update();
			} else {
				showErrorMessage("ID not found!");
			}
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Save Biller Service/Channel", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
	}

	private void update() throws WrongValueException, Exception {
		final Date requestDate = AppUtil.getCurrent();
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerServiceChannel billerServiceChannel = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerServiceChannel = bw1413Form.toBillerServiceChannel();
						billerServiceChannel.setLAST_CHNG_BY(getUserName());
						billerServiceService.updateBillerServiceChannel(billerServiceChannel, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("update OK");
									processUpdateInDialogMode();
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Update Service/Channel", getErrorParames(), ieex));
						Validation.isExist(txServiceCode, true, txServiceCode.getValue());
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Service/Channel", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						billerServiceService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Update", requestDate, "");
	}

	private void processUpdateInDialogMode() {
		Component parent = null;
		Window parentWindow = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1413_REDIRECT_PATH))) {
				Page pBw1413 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1413");
				log.info("Parent Page:" + pBw1413);
				Grid gdResult = null;
				if (pBw1413 != null) {
					gdResult = (Grid) pBw1413.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw1413Dialog/bw1413DialogInclude");
					gdResult = (Grid) i.getFellow("gdResult");
				}

				Event onRefreshBarcode = new Event("onRefresh", gdResult);
				Events.sendEvent(onRefreshBarcode);
			}
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "ProcessUpdateInDialogMode", "[Parrent:" + parent + ",Callback:" + callback, ex));
			throw new BillerWebException(ex);
		} finally {
			parent = null;
			parentWindow = null;

		}
	}

	private Map<String, String> getErrorParames() {
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("Channel ID", bw1413Form.getBillerChannelId() + "");
			params.put("Biller Service ID", bw1413Form.getBillerServiceId() + "");
			params.put("Allowed Key In Flag", bw1413Form.getAllowedKeyIn());
			params.put("Allowed Barcode Flag", bw1413Form.getAllowedBarcode() + "");
			params.put("Status", bw1413Form.getActiveFlag() + "");

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	@Listen("onClick = #btCancel")
	public void cancel() {
		log.info(getOperationLogMessage(pageName, "Cancel", ""));
		Component parent = null;
		Window parentWindow = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			parentWindow = (Window) parent;
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}

}
