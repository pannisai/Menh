package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BankMasterBean;
import mfs.biller.persistence.bean.BankServicebean;
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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.Dialog;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.exception.DialogException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW5110Form;
import com.dtac.billerweb.listmodel.selectbox.BankCodeListModel;
import com.dtac.billerweb.service.BankServiceService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW5110Controller extends EditPageController {

	public BW5110Controller() {
		super("Edit Bank Service","5110");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW5110Controller.class);
	private BW5110Form bw5110Form = new BW5110Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label title;
	@Wire
	private Intbox intBankServiceId;
	@Wire
	private Listbox lbBankServiceCode;
	@Wire
	private Label lBankServiceName;
	@Wire
	private Textbox txBankServiceName;
	@Wire
	private Label lBankCode;
	@Wire
	private Listbox lbBankCode;
	@Wire
	private Button btEditBankCode;
	@Wire
	private Listbox lbBankServiceType;
	@Wire
	private Label lBankCutoffTime;
	@Wire
	private Timebox tbBankCutoffTime;
	@Wire
	private Listbox lbActiveFlag;
	@Wire
	private Button btSave;
	@Wire
	private Button btReset;
	@Wire
	private Button btCancel;

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub
		setupPage();
		if (oid == -1) {
			setupNewPage();
		} else {
			setupEditPage();
		}
	}

	private void setupPage() {
		try {
			/* ## Set Bank Code list ## */
			setupBankCodeList(-1);

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
		}
	}

	private void setupBankCodeList(Integer selectedIndex) {
		SelectBoxService selectBoxService = null;
		BankCodeListModel<BankMasterBean> bankCodeListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Bank Code list ## */
			bankCodeListModel = selectBoxService.getBankCodeListModel();
			bankCodeListModel.addItemSelect();
			bankCodeListModel.addItemCreateNew();
			if (AppUtil.isNotNull(selectedIndex) && selectedIndex != -1) {
				bankCodeListModel.addDataToSelection(selectedIndex);
			}
			lbBankCode.setModel(bankCodeListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			bankCodeListModel = null;
		}
	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave,btEditBankCode);
			title.setValue(AppMessage.getMessage(AppMessage.BW5110_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			 i = (Include) Path.getComponent("/bw5110Dialog/bw5110DialogInclude");
			 comps =i!=null? i.getFellows():updatePage.getFellows();
			if (chkUpdatePermission(comps,new Listbox[]{lbBankCode},btSave, btReset,btEditBankCode)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW5110_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW5110_VIEW_TITLE));				
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
		BankServiceService bankServiceService = null;
		BankServicebean bankServicebean = null;
		try {
			bankServiceService = BillerwebServiceFactory.getBankServiceService();
			bankServicebean = bankServiceService.getByID(oid, getUserInfo());
			bw5110Form = bw5110Form.toBW5110Form(bankServicebean);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		BankCodeListModel<BankMasterBean> bankCodeListModel = null;
		try {
			intBankServiceId.setValue(bw5110Form.getBankServiceId());
			lbBankServiceCode.setSelectedIndex(bw5110Form.getBankServiceCode().equalsIgnoreCase("M2C") ? 1 : 0);
			txBankServiceName.setValue(bw5110Form.getBankServiceName());
			bankCodeListModel = (BankCodeListModel) lbBankCode.getModel();
			int bankMasterIndex = bankCodeListModel.findIndexOfId(bw5110Form.getBankCode());
			if (bankMasterIndex > -1 && lbBankCode.getItemCount() > bankMasterIndex) {
				bankCodeListModel.addDataToSelection(bankMasterIndex);
			}
			lbBankServiceType.setSelectedIndex(bw5110Form.getBankServiceType().equalsIgnoreCase("04") ? 1 : 0);
			tbBankCutoffTime.setValue(bw5110Form.getBankCutoffTime());
			lbActiveFlag.setSelectedIndex(bw5110Form.getActiveFlag().equalsIgnoreCase("A") ? 0 : 1);

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			bankCodeListModel = null;
		}
	}

	private void updateBW5110Form() {
		try {
			bw5110Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
			bw5110Form.setBankServiceId(intBankServiceId.getValue());
			bw5110Form.setBankServiceCode(lbBankServiceCode.getSelectedItem().getValue().toString());
			bw5110Form.setBankServiceName(txBankServiceName.getValue());
			BankMasterBean bankMaster = lbBankCode.getSelectedItem().getValue();
			bw5110Form.setBankCode(bankMaster.getBANK_CODE());
			bw5110Form.setBankServiceType(lbBankServiceType.getSelectedItem().getValue().toString());
			bw5110Form.setBankCutoffTime(tbBankCutoffTime.getValue());
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	/* ## Event ## */
	@Listen("onClick = #btSave")
	public void clickSave() {

		try {
			log.info(getOperationLogMessage(pageName, "Save Bank Service", ""));
			validateBankCode();
			validateBankCutoffTime();
			updateBW5110Form();
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
			log.error(getErrorLogMessage(pageName, "Save Bank Service", getErrorParames(), ex));
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
					BankServiceService bankServiceService = null;
					BankServicebean bankServicebean = null;
					try {
						bankServiceService = BillerwebServiceFactory.getBankServiceService();
						bankServicebean = bw5110Form.toBankService();
						bankServicebean.setCRTD_BY(getUserName());
						bankServicebean.setLAST_CHNG_BY(getUserName());
						final Integer oid = bankServiceService.save(bankServicebean, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									if (isDialog()) {
										processSaveInDialogMode();
									} else {
										redirect(AppConfiguration.getValue(AppConfiguration.BW5110_REDIRECT_PATH) + "?oid=" + oid);
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Bank Service", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BANK_SRVC_ID")) {
							Validation.isExist(intBankServiceId, true, AppUtil.toString(intBankServiceId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Bank Service", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						bankServiceService = null;
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW5100_REDIRECT_PATH))) {
				Page pBw5100 = Executions.getCurrent().getDesktop().getPageIfAny("pBw5100");
				log.info("Page:" + pBw5100);
				Grid gdResult = null;
				if (pBw5100 != null) {
					gdResult = (Grid) pBw5100.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw5100Dialog/bw5100DialogInclude");
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
					BankServiceService bankServiceService = null;
					BankServicebean bankServicebean = null;
					try {
						bankServiceService = BillerwebServiceFactory.getBankServiceService();
						bankServicebean = bw5110Form.toBankService();
						bankServicebean.setLAST_CHNG_BY(getUserName());
						bankServiceService.update(bankServicebean, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Update Bank Service", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BANK_SRVC_ID")) {
							Validation.isExist(intBankServiceId, true, AppUtil.toString(intBankServiceId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Bank Service", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						bankServiceService = null;
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW5100_REDIRECT_PATH))) {
				Page pBw5100 = Executions.getCurrent().getDesktop().getPageIfAny("pBw5100");
				log.info("Page:" + pBw5100);
				Grid gdResult = null;
				if (pBw5100 != null) {
					gdResult = (Grid) pBw5100.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw5100Dialog/bw5100DialogInclude");
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
			params.put("Bank Service ID", bw5110Form.getBankServiceId() + "");
			params.put("Bank Service Code", bw5110Form.getBankServiceCode() + "");
			params.put("Bank Service Name", bw5110Form.getBankServiceName() + "");
			params.put("Bank Code", bw5110Form.getBankCode() + "");
			params.put("Bank Service Type", bw5110Form.getBankServiceType() + "");
			params.put("Bank Cutoff time", bw5110Form.getBankCutoffTime() + "");
			params.put("ActiveFlag", bw5110Form.getActiveFlag() + "");
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
						lbBankServiceCode.setSelectedIndex(0);
						txBankServiceName.setValue(null);
						lbBankCode.setSelectedIndex(0);
						lbBankServiceType.setSelectedIndex(0);
						tbBankCutoffTime.setValue(null);
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
				redirect(AppConfiguration.getValue(AppConfiguration.BW5100_REDIRECT_PATH));
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}

	@Listen("onSelect = #lbBankCode")
	public void onSelectBankCode() {
		log.info(getOperationLogMessage(pageName, "onSelectBankCode", ""));
		try {
			Clients.clearWrongValue(lbBankCode);
			/* ## Create New ## */
			BankMasterBean bankMaster = lbBankCode.getSelectedItem().getValue();
			if (AppUtil.isNotNull(bankMaster.getBANK_CODE()) && bankMaster.getBANK_CODE().equals("-1")) {
				processCreateNewBankCode();
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectBankCode", ex));
			showErrorMessage(ex);
		}
	}

	private void processCreateNewBankCode() throws Exception {
		Dialog.openBw4110MedalDialog(AppConfiguration.getValue(AppConfiguration.BW5110_REDIRECT_PATH), "");
	}

	@Listen("onClick = #btEditBankCode")
	public void editEditBankCode() {
		log.info(getOperationLogMessage(pageName, "editEditBankCode", ""));
		BankMasterBean bankMasterBean = new BankMasterBean();
		try {
			bankMasterBean = lbBankCode.getSelectedItem().getValue();
			if (AppUtil.isNotNull(bankMasterBean.getBANK_CODE()) && !bankMasterBean.getBANK_CODE().equals("-1")) {
				Dialog.openBw4110MedalDialog(AppConfiguration.getValue(AppConfiguration.BW5110_REDIRECT_PATH), bankMasterBean.getBANK_CODE());
			} else {
				Validation.isSelectDropdownList(lbBankCode, null, lBankCode.getValue());
			}
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "editEditBankCode", "OID:" + bankMasterBean.getBANK_CODE(), ex));
			showErrorMessage(ex);
		} finally {
			bankMasterBean = null;
		}
	}

	@Listen("onAfterRender=#lbBankCode")
	public void onAfterRenderBankCode(Event even) {
		try {
			Listitem createNew = lbBankCode.getItemAtIndex(lbBankCode.getItemCount() - 1);
			createNew.setSclass("listboxFooter");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	@Listen("onBlur= #tbBankCutoffTime")
	public void onBlurBankChanNameTh(Event event) {
		try {
			validateBankCutoffTime();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}
	
	/* ## Validation ## */
	private void validateBankCode() throws WrongValueException, Exception {
		BankMasterBean value = lbBankCode.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbBankCode, value.getBANK_CODE(), lBankCode.getValue());
	}

	private void validateBankCutoffTime() throws WrongValueException, Exception {
		Validation.isNotEmpty(tbBankCutoffTime, tbBankCutoffTime.getValue(), lBankCutoffTime.getValue());
	}

	@Listen("onRefreshBankCodeList = #lbBankCode")
	public void refreshBankCodeList(Event event) {
		log.info(getOperationLogMessage(pageName, "refreshBankCodeList", ""));
		try {
			Object currentIndex = event.getData();
			if (currentIndex != null) {
				setupBankCodeList((Integer) currentIndex);
			} else {
				setupBankCodeList(-1);
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "refreshBankCodeList", ex));
			showErrorMessage(ex);
		}
	}
}
