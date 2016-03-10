package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BillerChannel;
import mfs.exception.IsExistException;

import org.apache.commons.lang3.exception.ExceptionUtils;
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
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.exception.DialogException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1310Form;
import com.dtac.billerweb.service.BillerChannelService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1310Controller extends EditPageController {

	public BW1310Controller() {
		super("Edit Biller Channel","1310");
	}

	private Logger log = Logger.getLogger(BW1310Controller.class);
	private BW1310Form bw1310Form = new BW1310Form();
	
	@WireVariable
	private Page updatePage;
	@Wire
	private Label title;
	@Wire
	private Intbox intBillerChannelId;
	@Wire
	private Textbox txBillerChannelName;
	@Wire
	private Textbox txBillerChannelCode;
	@Wire
	private Listbox lbActiveFlag;
	@Wire
	private Label lbChannelName;
	@Wire
	private Label lbChannelCode;
	@Wire
	private Button btSave;
	@Wire
	private Button btReset;
	@Wire
	private Button btCancel;

	@Override
	protected void init() throws Exception {
		if (oid == -1) {
			setupNewPage();
		} else {
			setupEditPage();
		}
	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave);
			title.setValue(AppMessage.getMessage(AppMessage.BW1310_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			 i = (Include) Path.getComponent("/bw1310Dialog/bw1310DialogInclude");
			 comps =i!=null? i.getFellows():updatePage.getFellows();
			if (chkUpdatePermission(comps,btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW1310_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW1310_VIEW_TITLE));				
			}

			loadFormData(oid);
			refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i=null;
			comps=null;
		}
	}

	private void loadFormData(Integer oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BillerChannelService billerChannelService = null;
		BillerChannel billerChannel = null;
		try {
			billerChannelService = BillerwebServiceFactory.getBillerChannelService();
			billerChannel = billerChannelService.getByID(oid, getUserInfo());
			bw1310Form = bw1310Form.toBW1310Form(billerChannel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		try {
			intBillerChannelId.setValue(bw1310Form.getBillerChannelId());
			txBillerChannelName.setValue(bw1310Form.getBillerChannelName());
			txBillerChannelCode.setValue(bw1310Form.getBillerChannelCode());
			try {
				if (bw1310Form.getActiveFlag().equalsIgnoreCase("A")) {
					lbActiveFlag.setSelectedIndex(0);
				} else {
					lbActiveFlag.setSelectedIndex(1);
				}
			} catch (NullPointerException npe) {
				lbActiveFlag.setSelectedIndex(0);
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	public void updateBW1310Form() {
		try {
			bw1310Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
			bw1310Form.setBillerChannelId(intBillerChannelId.getValue());
			bw1310Form.setBillerChannelName(txBillerChannelName.getValue());
			bw1310Form.setBillerChannelCode(txBillerChannelCode.getValue());
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception ex) {

		} finally {

		}
	}

	/* ## Event ## */
	@Listen("onClick = #btSave")
	public void clickSave() {
		try {
			log.info(getOperationLogMessage(pageName, "Save Biller Channel", ""));
			updateBW1310Form();
			validateBillerChannelName();
			validateBillerChannelCode();
			if (oid == -1) {
				save();
			} else {
				update();
			}
		} catch (BillerWebSessionTimeOutException bwto) {
			throw new BillerWebSessionTimeOutException(bwto);
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Save Biller Channel", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void save() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Save " + pageName);
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerChannelService billerChannelService = null;
					BillerChannel billerChannel = null;
					try {
						billerChannelService = BillerwebServiceFactory.getBillerChannelService();
						billerChannel = bw1310Form.toBillerChannel();
						billerChannel.setCRTD_BY(getUserName());
						billerChannel.setLAST_CHNG_BY(getUserName());
						final Integer oid = billerChannelService.save(billerChannel, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									if (isDialog()) {
										processSaveInDialogMode();
									} else {
										redirect(AppConfiguration.getValue(AppConfiguration.BW1310_REDIRECT_PATH) + "?oid=" + oid);
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Biller Channel", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_CHNL_ID")) {
							Validation.isExist(intBillerChannelId, true, AppUtil.toString(intBillerChannelId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Channel", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						billerChannelService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Save", requestDate, "");
	}

	private void processSaveInDialogMode() {
		SelectBoxService selectBoxService = null;
		Component parent = null;
		Window parentWindow = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1413_REDIRECT_PATH))) {
				Page pBw1413 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1413");
				log.info("Page:" + pBw1413);
				Listbox lbChannelCode = null;
				if (pBw1413 != null) {
					lbChannelCode = (Listbox) pBw1413.getFellow("lbChannelCode");
				} else {
					Include i = (Include) Path.getComponent("/bw1413Dialog/bw1413DialogInclude");
					lbChannelCode = (Listbox) i.getFellow("lbChannelCode");
				}

				Event onRefreshChannelCode = new Event("onRefresh", lbChannelCode);
				Events.sendEvent(onRefreshChannelCode);
			}
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "ProcessSaveInDialogMode", "[Parrent:" + parent + ",Callback:" + callback, ex));
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			parent = null;
			parentWindow = null;
		}
	}

	private void update() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Update " + pageName);
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerChannelService billerChannelService = null;
					BillerChannel billerChannel = null;
					try {
						billerChannelService = BillerwebServiceFactory.getBillerChannelService();
						billerChannel = bw1310Form.toBillerChannel();
						billerChannel.setLAST_CHNG_BY(getUserName());
						billerChannelService.update(billerChannel, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("update OK");
									if (isDialog()) {
										processUpdateInDialogMode();
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Update Biller Channel", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_CHNL_ID")) {
							Validation.isExist(intBillerChannelId, true, AppUtil.toString(intBillerChannelId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Biller Channel", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						billerChannelService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Update", requestDate, "");
	}

	private void processUpdateInDialogMode() {
		SelectBoxService selectBoxService = null;
		Component parent = null;
		Window parentWindow = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1413_REDIRECT_PATH))) {
				Page pBw1413 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1413");
				log.info("Page:" + pBw1413);
				Listbox lbChannelCode = null;
				if (pBw1413 != null) {
					lbChannelCode = (Listbox) pBw1413.getFellow("lbChannelCode");
				} else {
					Include i = (Include) Path.getComponent("/bw1413Dialog/bw1413DialogInclude");
					lbChannelCode = (Listbox) i.getFellow("lbChannelCode");
				}
				Event onRefreshChannelCode = new Event("onRefresh", lbChannelCode);
				Events.sendEvent(onRefreshChannelCode);
			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1300_REDIRECT_PATH))) {
				Page pBw1300 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1300");
				log.info("Page:" + pBw1300);
				Grid gdResult = null;
				if (pBw1300 != null) {
					gdResult = (Grid) pBw1300.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw1300Dialog/bw1300DialogInclude");
					gdResult = (Grid) i.getFellow("gdResult");
				}

				Event onRefresh = new Event("onRefresh", gdResult);
				Events.sendEvent(onRefresh);
			}
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "ProcessUpdateInDialogMode", "[Parrent:" + parent + ",Callback:" + callback, ex));
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			parent = null;
			parentWindow = null;
		}
	}

	private Map<String, String> getErrorParames() {
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("Biller Channel ID", bw1310Form.getBillerChannelId() + "");
			params.put("Biller Channel Name", bw1310Form.getBillerChannelName());
			params.put("Biller Channel Code", bw1310Form.getBillerChannelCode());
			params.put("ActiveFlag", bw1310Form.getActiveFlag());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	@Listen("onClick = #btReset")
	public void reset() {
		log.info("Click Reset Edit Biller Channel");
		EventListener resetConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {
						bw1310Form.setActiveFlag("A");
						bw1310Form.setBillerChannelName(null);
						bw1310Form.setBillerChannelCode(null);
						refreshForm();
					} catch (Exception ex) {
						log.error(ExceptionUtils.getStackTrace(ex));
						showErrorMessage(ex);
					} finally {

					}
				}
			}
		};
		showResetConfirmMessage(resetConfirmListener);
	}

	@Listen("onClick = #btCancel")
	public void cancel() {
		log.info(getOperationLogMessage(pageName, "Cancel", ""));
		Component parent = null;
		Window parentWindow = null;
		try {
			if (isDialog()) {
				parent = super.getSelf().getParent().getParent();
				if (parent == null) {
					throw new DialogException("Parent is Null");
				}
				log.info("Parent::" + parent);
				parentWindow = (Window) parent;
				parentWindow.detach();
			} else {
				redirect(AppConfiguration.getValue(AppConfiguration.BW1300_REDIRECT_PATH));
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}

	@Listen("onBlur= #txBillerChannelName")
	public void onBlurBillerChannelName(Event event) {
		try {
			validateBillerChannelName();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onBlur= #txBillerChannelCode")
	public void onBlurBillerChannelCode(Event event) {
		try {
			txBillerChannelCode.setValue(txBillerChannelCode.getValue().toUpperCase());
			validateBillerChannelCode();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void validateBillerChannelName() throws WrongValueException, Exception {
		BillerChannelService billerChannelService = null;
		boolean result = false;
		Validation.isNotEmpty(txBillerChannelName, txBillerChannelName.getValue(), lbChannelName.getValue());
		billerChannelService = BillerwebServiceFactory.getBillerChannelService();
		result = billerChannelService.isExistBillerChannelName(oid, txBillerChannelName.getValue(), getUserInfo());
		if (result) {
			Validation.isExist(txBillerChannelName, result, txBillerChannelName.getValue());
		}
		billerChannelService = null;
	}

	private void validateBillerChannelCode() throws WrongValueException, Exception {
		BillerChannelService billerChannelService = null;
		boolean result = false;
		Validation.isNotEmpty(txBillerChannelCode, txBillerChannelCode.getValue(), lbChannelCode.getValue());
		billerChannelService = BillerwebServiceFactory.getBillerChannelService();
		result = billerChannelService.isExistBillerChannelCode(oid, txBillerChannelCode.getValue(), getUserInfo());
		if (result) {
			Validation.isExist(txBillerChannelCode, result, txBillerChannelCode.getValue());
		}
		billerChannelService = null;
	}

}
