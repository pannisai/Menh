package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BankMasterBean;
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
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
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
import com.dtac.billerweb.form.BW4110Form;
import com.dtac.billerweb.service.BankMasterService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW4110Controller extends EditPageController {
	public BW4110Controller() {
		super("Edit Bank Master", "4110");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW4110Controller.class);
	private BW4110Form bw4110Form = new BW4110Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label title;
	@Wire
	private Textbox txBankCode;
	@Wire
	private Label lBankCode;
	@Wire
	private Textbox txBankName;
	@Wire
	private Textbox txBankNameEn;
	@Wire
	private Textbox txBankNameTh;
	@Wire
	private Listbox lbActiveFlag;
	@Wire
	private Button btSave;
	@Wire
	private Button btReset;
	@Wire
	private Button btCancel;

	private String oid;

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub
		oid = getParameter("oid");
		if (AppUtil.isEmpty(oid)) {
			oid = getArgument("oid");
		}
		if (AppUtil.isEmpty(oid)) {
			setupNewPage();
		} else {
			setupEditPage();
		}
	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave);
			title.setValue(AppMessage.getMessage(AppMessage.BW4110_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			i = (Include) Path.getComponent("/bw4110Dialog/bw4110DialogInclude");
			comps = i != null ? i.getFellows() : updatePage.getFellows();
			if (chkUpdatePermission(comps, btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW4110_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW4110_VIEW_TITLE));
			}

			txBankCode.setDisabled(true);
			loadFormData(oid);
			refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i = null;
			comps = null;
		}
	}

	private void loadFormData(String oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BankMasterService bankMasterService = null;
		BankMasterBean bankMasterBean = null;
		try {
			bankMasterService = BillerwebServiceFactory.getBankMasterService();
			bankMasterBean = bankMasterService.getByID(oid, getUserInfo());
			bw4110Form = bw4110Form.toBW4110Form(bankMasterBean);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		try {
			txBankCode.setValue(bw4110Form.getBankCode());
			txBankName.setValue(bw4110Form.getBankName());
			txBankNameEn.setValue(bw4110Form.getBankNameEn());
			txBankNameTh.setValue(bw4110Form.getBankNameTh());
			lbActiveFlag.setSelectedIndex(bw4110Form.getActiveFlag().equalsIgnoreCase("A") ? 0 : 1);

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void updateBW4110Form() {
		try {
			bw4110Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
			bw4110Form.setBankCode(txBankCode.getValue());
			bw4110Form.setBankName(txBankName.getValue());
			bw4110Form.setBankNameEn(txBankNameEn.getValue());
			bw4110Form.setBankNameTh(txBankNameTh.getValue());
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
			log.info(getOperationLogMessage(pageName, "Save Bank Master", ""));
			validateBankCode();
			validateBankNameEnglish();
			validateBankNameThai();
			updateBW4110Form();
			if (AppUtil.isEmpty(oid)) {
				save();
			} else {
				update();
			}
		} catch (BillerWebSessionTimeOutException bwto) {
			throw new BillerWebSessionTimeOutException(bwto);
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Save Bank Master", getErrorParames(), ex));
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
					BankMasterService bankMasterService = null;
					BankMasterBean bankMasterBean = null;
					try {
						bankMasterService = BillerwebServiceFactory.getBankMasterService();
						bankMasterBean = bw4110Form.toBankMaster();
						bankMasterBean.setCRTD_BY(getUserName());
						bankMasterBean.setLAST_CHNG_BY(getUserName());
						bankMasterService.save(bankMasterBean, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									if (isDialog()) {
										processSaveInDialogMode();
									} else {
										redirect(AppConfiguration.getValue(AppConfiguration.BW4110_REDIRECT_PATH) + "?oid=" + AppUtil.trim(txBankCode.getValue()));
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Bank Master", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BANK_CODE")) {
							Validation.isExist(txBankCode, true, txBankCode.getValue());
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Bank Master", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						bankMasterService = null;
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW4100_REDIRECT_PATH))) {
				Page pBw4100 = Executions.getCurrent().getDesktop().getPageIfAny("pBw4100");
				log.info("Page:" + pBw4100);
				Grid gdResult = null;
				if (pBw4100 != null) {
					gdResult = (Grid) pBw4100.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw4100Dialog/bw4100DialogInclude");
					gdResult = (Grid) i.getFellow("gdResult");
				}

				Event onRefresh = new Event("onRefresh", gdResult);
				Events.sendEvent(onRefresh);
			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW5110_REDIRECT_PATH))) {
				Page pBw5110 = Executions.getCurrent().getDesktop().getPageIfAny("pBw5110");
				log.info("Page:" + pBw5110);
				Listbox lbBankCode = null;
				if (pBw5110 != null) {
					lbBankCode = (Listbox) pBw5110.getFellow("lbBankCode");
				} else {
					Include i = (Include) Path.getComponent("/bw5110Dialog/bw5110DialogInclude");
					lbBankCode = (Listbox) i.getFellow("lbBankCode");
				}

				Event onRefresh = new Event("onRefreshBankCodeList", lbBankCode);
				Events.sendEvent(onRefresh);
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
					BankMasterService bankMasterService = null;
					BankMasterBean bankMasterBean = null;
					try {
						bankMasterService = BillerwebServiceFactory.getBankMasterService();
						bankMasterBean = bw4110Form.toBankMaster();
						bankMasterBean.setLAST_CHNG_BY(getUserName());
						bankMasterService.update(bankMasterBean, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Update Bank Master", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BANK_CODE")) {
							Validation.isExist(txBankCode, true, txBankCode.getValue());
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Bank Master", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						bankMasterService = null;
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW4100_REDIRECT_PATH))) {
				Page pBw4100 = Executions.getCurrent().getDesktop().getPageIfAny("pBw4100");
				log.info("Page:" + pBw4100);
				Grid gdResult = null;
				if (pBw4100 != null) {
					gdResult = (Grid) pBw4100.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw4100Dialog/bw4100DialogInclude");
					gdResult = (Grid) i.getFellow("gdResult");
				}

				Event onRefresh = new Event("onRefresh", gdResult);
				Events.sendEvent(onRefresh);
			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW5110_REDIRECT_PATH))) {
				Page pBw5110 = Executions.getCurrent().getDesktop().getPageIfAny("pBw5110");
				log.info("Page:" + pBw5110);
				Listbox lbBankCode = null;
				if (pBw5110 != null) {
					lbBankCode = (Listbox) pBw5110.getFellow("lbBankCode");
				} else {
					Include i = (Include) Path.getComponent("/bw5110Dialog/bw5110DialogInclude");
					lbBankCode = (Listbox) i.getFellow("lbBankCode");
				}

				Event onRefresh = new Event("onRefreshBankCodeList", lbBankCode, lbBankCode.getSelectedIndex());
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
			params.put("Bank Code", bw4110Form.getBankCode() + "");
			params.put("Bank Name", bw4110Form.getBankName() + "");
			params.put("Bank Name(English)", bw4110Form.getBankNameEn() + "");
			params.put("Bank Name(Thai)", bw4110Form.getBankNameTh() + "");
			params.put("ActiveFlag", bw4110Form.getActiveFlag() + "");
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
						if (AppUtil.isEmpty(oid)) {
							txBankCode.setValue(null);
						}
						txBankName.setValue(null);
						txBankNameEn.setValue(null);
						txBankNameTh.setValue(null);
						lbActiveFlag.setSelectedIndex(0);
					} catch (Exception ex) {
						log.error(ex.getMessage());
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
				redirect(AppConfiguration.getValue(AppConfiguration.BW4100_REDIRECT_PATH));
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}

	}

	@Listen("onBlur= #txBankCode")
	public void onBlurBankCode(Event event) {
		try {
			validateBankCode();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onBlur= #txBankNameEn")
	public void onBlurBankNameEn(Event event) {
		try {
			validateBankNameEnglish();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onBlur= #txBankNameTh")
	public void onBlurBankNameTh(Event event) {
		try {
			validateBankNameThai();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void validateBankCode() throws WrongValueException, Exception {
		Validation.isNotEmpty(txBankCode, txBankCode.getValue(), lBankCode.getValue());
		Validation.isStrNumber(txBankCode, txBankCode.getValue());
		isThreeOrFourDigit(txBankCode, txBankCode.getValue(), lBankCode.getValue());

		if (AppUtil.isEmpty(oid)) {
			BankMasterService bankMasterService = null;
			boolean result = false;
			bankMasterService = BillerwebServiceFactory.getBankMasterService();
			result = bankMasterService.isExistBankCode(txBankCode.getValue(), getUserInfo());
			if (result) {
				Validation.isExist(txBankCode, result, txBankCode.getValue());
			}
			bankMasterService = null;
		}
	}

	private void validateBankNameEnglish() throws WrongValueException, Exception {
		Validation.isNotThaiChar(txBankNameEn, txBankNameEn.getValue());
	}

	private void validateBankNameThai() throws WrongValueException, Exception {
		Validation.isNotEnglishChar(txBankNameTh, txBankNameTh.getValue());
	}

	public void isThreeOrFourDigit(Component comp, String value, String field) throws WrongValueException, Exception {
		String[] params = { field, "3 or 4" };
		if (!(value.length() >= 3 && value.length() <= 4)) {
			throw new WrongValueException(comp, AppMessage.getMessage(AppMessage.MSG_FIX_LENGTH, params));
		}
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

}
