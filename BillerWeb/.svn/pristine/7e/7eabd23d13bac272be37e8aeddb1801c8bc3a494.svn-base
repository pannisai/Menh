package com.dtac.billerweb.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BillerDenominate;
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
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.exception.DialogException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1460Form;
import com.dtac.billerweb.service.BillerServiceService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1460Controller extends EditPageController {
	public BW1460Controller() {
		super("Biller Service/Popup Denominate","1460");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW1460Controller.class);
	private BW1460Form bw1460Form = new BW1460Form();
	@Wire
	private Label title;
	@Wire
	private Intbox intBillerDenoId;
	@Wire
	private Doublebox douDenominateRate;
	@Wire
	private Listbox lbActiveFlag;

	@Wire
	private Button btSave;
	@Wire
	private Button btReset;
	@Wire
	private Button btCencel;

	private Integer billerServiceId;

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub
		String serviceID = getArgument("serviceId");
		if (!AppUtil.isEmpty(serviceID)) {
			billerServiceId = Integer.parseInt(serviceID);
		} else {
			billerServiceId = -1;
			showErrorMessage("Service ID not found!");
		}
		if (oid == -1) {
			setupNewPage();
		} else {
			setupEditPage();
		}
	}

	private void setupNewPage() {
		try {
			//chkInsertPermission(btSave);
			title.setValue(AppMessage.getMessage(AppMessage.BW1460_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			 i = (Include) Path.getComponent("/bw1460Dialog/bw1460DialogInclude");
			 comps = i.getFellows();
			if (chkUpdatePermission(comps, btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW1460_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW1460_VIEW_TITLE));
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
		BillerDenominate billerDenominate = null;
		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			billerDenominate = billerServiceService.getBillerDenominateByID(oid, getUserInfo());
			bw1460Form = bw1460Form.toBW1460Form(billerDenominate);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Load Data", "OID::" + oid, ex));
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {

		try {
			intBillerDenoId.setValue(bw1460Form.getBillerDenominateId());
			douDenominateRate.setValue(bw1460Form.getDenominateRate() == null ? 0.00 : bw1460Form.getDenominateRate().doubleValue());
			if (!AppUtil.isEmpty(bw1460Form.getActiveFlag()) && bw1460Form.getActiveFlag().equalsIgnoreCase("I")) {
				lbActiveFlag.setSelectedIndex(1);
			} else {
				lbActiveFlag.setSelectedIndex(0);
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void updateBW1460Form() {
		try {
			bw1460Form.setBillerDenominateId(intBillerDenoId.getValue());
			bw1460Form.setBillerServiceId(billerServiceId);
			bw1460Form.setDenominateRate(douDenominateRate.getValue() != null ? new BigDecimal(douDenominateRate.getValue()) : new BigDecimal(0.00));
			bw1460Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
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

		try {
			log.info(getOperationLogMessage(pageName, "Save Biller Service/Popup Denominate", ""));

			updateBW1460Form();
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
			log.error(getErrorLogMessage(pageName, "Save Biller Service/Popup Denominate", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void save() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Save " + "Popup Denominate");
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerDenominate billerDenominate = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerDenominate = bw1460Form.toBillerDenominate();

						final Integer oid = billerServiceService.saveBillerDenominate(billerDenominate, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									if (isDialog()) {
										processSaveInDialogMode();
									} else {
										redirect(AppConfiguration.getValue(AppConfiguration.BW1460_REDIRECT_PATH) + "?oid=" + oid);
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Popup Denominate", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_DENM_ID")) {
							Validation.isExist(intBillerDenoId, true, AppUtil.toString(intBillerDenoId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Popup Denominate", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						billerServiceService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Save", requestDate, "");
	}

	private void processSaveInDialogMode() {
		Component parent = null;
		Window parentWindow = null;

		try {
			parent = super.getSelf().getParent().getParent();

			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1412_REDIRECT_PATH))) {
				Page pBw1412 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1412");
				log.info("Page:" + pBw1412);
				Grid gdResult = null;
				if (pBw1412 != null) {
					gdResult = (Grid) pBw1412.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw1412Dialog/bw1412DialogInclude");
					gdResult = (Grid) i.getFellow("gdResult");
				}

				Event onRefresh = new Event("onRefresh", gdResult);
				Events.sendEvent(onRefresh);
			}
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "ProcessSaveInDialogMode", "[Parrent:" + parent + ",Callback:" + callback, ex));
			throw new BillerWebException(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}

	private void update() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Update " + "Popup Denominate");
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerDenominate billerDenominate = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerDenominate = bw1460Form.toBillerDenominate();
						billerDenominate.setLAST_CHNG_BY(getUserName());
						billerServiceService.updateBillerDenominate(billerDenominate, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Popup Denominate", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_DENM_ID")) {
							Validation.isExist(intBillerDenoId, true, AppUtil.toString(intBillerDenoId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Popup Denominate", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						billerServiceService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		log.info(getStopLogMessage(pageName));
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1412_REDIRECT_PATH))) {
				Page pBw1412 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1412");
				log.info("Page:" + pBw1412);
				Grid gdResult = null;
				if (pBw1412 != null) {
					gdResult = (Grid) pBw1412.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw1412Dialog/bw1412DialogInclude");
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
			parent = null;
			parentWindow = null;
		}
	}

	private Map<String, String> getErrorParames() {
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("Biller Denominate ID", bw1460Form.getBillerDenominateId() + "");
			params.put("Denominate Rate", bw1460Form.getDenominateRate() + "");
			params.put("Status", bw1460Form.getActiveFlag() + "");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	@Listen("onClick = #btReset")
	public void reset() {
		log.info(getOperationLogMessage(pageName, "Reset", ""));
		EventListener resetConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {
						bw1460Form.setDenominateRate(null);
						bw1460Form.setActiveFlag("A");
						refreshForm();
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Reset", ex));
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
