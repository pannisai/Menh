package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BankChannelBean;
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
import com.dtac.billerweb.form.BW4210Form;
import com.dtac.billerweb.service.BankChannelService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW4210Controller extends EditPageController {

	public BW4210Controller() {
		super("Edit Bank Channel","4210");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW4210Controller.class);
	private BW4210Form bw4210Form = new BW4210Form();

	@WireVariable
	private Page updatePage;	
	@Wire
	private Label title;
	@Wire
	private Textbox txBankChanCode;
	@Wire
	private Label lBankChanCode;
	@Wire
	private Textbox txBankChanName;
	@Wire
	private Textbox txBankChanNameEn;
	@Wire
	private Textbox txBankChanNameTh;
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
			title.setValue(AppMessage.getMessage(AppMessage.BW4210_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			 i = (Include) Path.getComponent("/bw4210Dialog/bw4210DialogInclude");
			 comps =i!=null? i.getFellows():updatePage.getFellows();
			if (chkUpdatePermission(comps,btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW4210_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW4210_VIEW_TITLE));				
			}
			
			txBankChanCode.setDisabled(true);
			loadFormData(oid);
			refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i=null;
			comps=null;
		}
	}

	private void loadFormData(String oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BankChannelService bankChannelService = null;
		BankChannelBean bankChannelBean = null;
		try {
			bankChannelService = BillerwebServiceFactory.getBankChannelService();
			bankChannelBean = bankChannelService.getByID(oid, getUserInfo());
			bw4210Form = bw4210Form.toBW4210Form(bankChannelBean);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		try {
			txBankChanCode.setValue(bw4210Form.getBankChanCode());
			txBankChanName.setValue(bw4210Form.getBankChanName());
			txBankChanNameEn.setValue(bw4210Form.getBankChanNameEn());
			txBankChanNameTh.setValue(bw4210Form.getBankChanNameTh());
			lbActiveFlag.setSelectedIndex(bw4210Form.getActiveFlag().equalsIgnoreCase("A") ? 0 : 1);

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void updateBW4210Form() {
		try {
			bw4210Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
			bw4210Form.setBankChanCode(txBankChanCode.getValue());
			bw4210Form.setBankChanName(txBankChanName.getValue());
			bw4210Form.setBankChanNameEn(txBankChanNameEn.getValue());
			bw4210Form.setBankChanNameTh(txBankChanNameTh.getValue());
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
			log.info(getOperationLogMessage(pageName, "Save Bank Channel", ""));
			validateBankChanCode();
			validateBankChanNameEnglish();
			validateBankChanNameThai();
			updateBW4210Form();
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
			log.error(getErrorLogMessage(pageName, "Save Bank Channel", getErrorParames(), ex));
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
					BankChannelService bankChannelService = null;
					BankChannelBean bankChannelBean = null;
					try {
						bankChannelService = BillerwebServiceFactory.getBankChannelService();
						bankChannelBean = bw4210Form.toBankChannel();
						bankChannelBean.setCRTD_BY(getUserName());
						bankChannelBean.setLAST_CHNG_BY(getUserName());
						bankChannelService.save(bankChannelBean, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									if (isDialog()) {
										processSaveInDialogMode();
									} else {
										redirect(AppConfiguration.getValue(AppConfiguration.BW4210_REDIRECT_PATH) + "?oid=" + AppUtil.trim(txBankChanCode.getValue()));
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Bank Channel", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BANK_CHNL_CODE")) {
							Validation.isExist(txBankChanCode, true, txBankChanCode.getValue());
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Bank Channel", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						bankChannelService = null;
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW4200_REDIRECT_PATH))) {
				Page pBw4200 = Executions.getCurrent().getDesktop().getPageIfAny("pBw4200");
				log.info("Page:" + pBw4200);
				Grid gdResult = null;
				if (pBw4200 != null) {
					gdResult = (Grid) pBw4200.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw4200Dialog/bw4200DialogInclude");
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
					BankChannelService bankChannelService = null;
					BankChannelBean bankChannelBean = null;
					try {
						bankChannelService = BillerwebServiceFactory.getBankChannelService();
						bankChannelBean = bw4210Form.toBankChannel();
						bankChannelBean.setLAST_CHNG_BY(getUserName());
						bankChannelService.update(bankChannelBean, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Update Bank Channel", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BANK_CHNL_CODE")) {
							Validation.isExist(txBankChanCode, true, txBankChanCode.getValue());
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Bank Channel", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						bankChannelService = null;
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW4200_REDIRECT_PATH))) {
				Page pBw4200 = Executions.getCurrent().getDesktop().getPageIfAny("pBw4200");
				log.info("Page:" + pBw4200);
				Grid gdResult = null;
				if (pBw4200 != null) {
					gdResult = (Grid) pBw4200.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw4200Dialog/bw4200DialogInclude");
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
			params.put("Bank Channel Code", bw4210Form.getBankChanCode() + "");
			params.put("Bank Channel Name", bw4210Form.getBankChanName() + "");
			params.put("Bank Channel Name(English)", bw4210Form.getBankChanNameEn() + "");
			params.put("Bank Channel Name(Thai)", bw4210Form.getBankChanNameTh() + "");
			params.put("ActiveFlag", bw4210Form.getActiveFlag() + "");
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
							txBankChanCode.setValue(null);
						}
						txBankChanName.setValue(null);
						txBankChanNameEn.setValue(null);
						txBankChanNameTh.setValue(null);
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
				redirect(AppConfiguration.getValue(AppConfiguration.BW4200_REDIRECT_PATH));
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}

	}

	@Listen("onBlur= #txBankChanCode")
	public void onBlurBankChanCode(Event event) {
		try {
			validateBankChanCode();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onBlur= #txBankChanNameEn")
	public void onBlurBankChanNameEn(Event event) {
		try {
			validateBankChanNameEnglish();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onBlur= #txBankChanNameTh")
	public void onBlurBankChanNameTh(Event event) {
		try {
			validateBankChanNameThai();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void validateBankChanCode() throws WrongValueException, Exception {
		Validation.isNotEmpty(txBankChanCode, txBankChanCode.getValue(), lBankChanCode.getValue());
		Validation.isStrNumber(txBankChanCode, txBankChanCode.getValue());

		if (AppUtil.isEmpty(oid)) {
			BankChannelService bankChannelService = null;
			boolean result = false;
			bankChannelService = BillerwebServiceFactory.getBankChannelService();
			result = bankChannelService.isExistBankChannelCode(txBankChanCode.getValue(), getUserInfo());
			if (result) {
				Validation.isExist(txBankChanCode, result, txBankChanCode.getValue());
			}
			bankChannelService = null;
		}
	}

	private void validateBankChanNameEnglish() throws WrongValueException, Exception {
		Validation.isNotThaiChar(txBankChanNameEn, txBankChanNameEn.getValue());
	}

	private void validateBankChanNameThai() throws WrongValueException, Exception {
		Validation.isNotEnglishChar(txBankChanNameTh, txBankChanNameTh.getValue());
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
}
