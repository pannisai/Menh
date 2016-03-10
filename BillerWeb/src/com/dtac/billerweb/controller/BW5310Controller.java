package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BankServicebean;
import mfs.biller.persistence.bean.BankSystemMATN;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.exception.DialogException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW5310Form;
import com.dtac.billerweb.listmodel.selectbox.BankServiceListModel;
import com.dtac.billerweb.service.BankMaintenanceService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW5310Controller extends EditPageController {

	public BW5310Controller() {
		super("Edit Bank Maintenance","5310");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW5310Controller.class);
	private BW5310Form bw5310Form = new BW5310Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label title;
	@Wire
	private Intbox intBankMaintenanceId;
	@Wire
	private Textbox txBankMaintenanceName;
	@Wire
	private Listbox lbBankServiceId;
	@Wire
	private Label lBankServiceId;
	@Wire
	private Textbox txBankServiceName;
	@Wire
	private Label lStartTime;
	@Wire
	private Timebox tbStartTime;
	@Wire
	private Label lEndTime;
	@Wire
	private Timebox tbEndTime;
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
			setupBankServiceIdList();

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
		}
	}

	private void setupBankServiceIdList() {
		SelectBoxService selectBoxService = null;
		BankServiceListModel<BankServicebean> bankServiceListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Bank Service Id list ## */
			bankServiceListModel = selectBoxService.getBankServiceListModel();
			bankServiceListModel.addItemSelect();
			lbBankServiceId.setModel(bankServiceListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			bankServiceListModel = null;
		}
	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave);
			title.setValue(AppMessage.getMessage(AppMessage.BW5310_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			i = (Include) Path.getComponent("/bw5310Dialog/bw5310DialogInclude");
			comps = i != null ? i.getFellows() : updatePage.getFellows();
			if (chkUpdatePermission(comps, btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW5310_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW5310_VIEW_TITLE));
			}

			loadFormData();
			refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i = null;
			comps = null;
		}
	}

	private void loadFormData() {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BankMaintenanceService bankMaintenanceService = null;
		BankSystemMATN bankSystemMATN = null;
		try {
			bankMaintenanceService = BillerwebServiceFactory.getBankMaintenanceService();
			bankSystemMATN = bankMaintenanceService.getByID(oid, getUserInfo());
			bw5310Form = bw5310Form.toBW5310Form(bankSystemMATN);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		BankServiceListModel<BankServicebean> bankServiceListModel = null;
		try {
			intBankMaintenanceId.setValue(bw5310Form.getBankMaintenanceId());
			txBankMaintenanceName.setValue(bw5310Form.getBankMaintenanceName());
			bankServiceListModel = (BankServiceListModel) lbBankServiceId.getModel();
			int bankServiceIndex = bankServiceListModel.findIndexOfId(bw5310Form.getBankServiceId());
			if (bankServiceIndex > -1 && lbBankServiceId.getItemCount() > bankServiceIndex) {
				bankServiceListModel.addDataToSelection(bankServiceIndex);
				txBankServiceName.setValue(bankServiceListModel.getElementAt(bankServiceIndex).getBANK_SRVC_NAME());
			}

			tbStartTime.setValue(bw5310Form.getStartTime());
			tbEndTime.setValue(bw5310Form.getEndTime());
			lbActiveFlag.setSelectedIndex(bw5310Form.getActiveFlag().equalsIgnoreCase("A") ? 0 : 1);

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			bankServiceListModel = null;
		}
	}

	private void updateBW5310Form() {
		try {
			bw5310Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
			bw5310Form.setBankMaintenanceId(intBankMaintenanceId.getValue());
			bw5310Form.setBankMaintenanceName(txBankMaintenanceName.getValue());
			BankServicebean bankService = lbBankServiceId.getSelectedItem().getValue();
			bw5310Form.setBankServiceId(bankService.getBANK_SRVC_ID());
			bw5310Form.setStartTime(tbStartTime.getValue());
			bw5310Form.setEndTime(tbEndTime.getValue());
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
			log.info(getOperationLogMessage(pageName, "Save Bank Maintenance", ""));
			validateBankServiceId();
			validateStartTime();
			validateEndTime();
			updateBW5310Form();
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
			log.error(getErrorLogMessage(pageName, "Save Bank Maintenance", getErrorParames(), ex));
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
					BankMaintenanceService bankMaintenanceService = null;
					BankSystemMATN bankSystemMATN = null;
					try {
						bankMaintenanceService = BillerwebServiceFactory.getBankMaintenanceService();
						bankSystemMATN = bw5310Form.toBankMaintenance();
						bankSystemMATN.setCRTD_BY(getUserName());
						bankSystemMATN.setLAST_CHNG_BY(getUserName());
						final Integer oid = bankMaintenanceService.save(bankSystemMATN, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									if (isDialog()) {
										processSaveInDialogMode();
									} else {
										redirect(AppConfiguration.getValue(AppConfiguration.BW5310_REDIRECT_PATH) + "?oid=" + oid);
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Bank Maintenance", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BANK_SYSM_MATN_ID")) {
							Validation.isExist(intBankMaintenanceId, true, AppUtil.toString(intBankMaintenanceId.getValue()));

						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Bank Maintenance", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						bankMaintenanceService = null;
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW5300_REDIRECT_PATH))) {
				Page pBw5300 = Executions.getCurrent().getDesktop().getPageIfAny("pBw5300");
				log.info("Page:" + pBw5300);
				Grid gdResult = null;
				if (pBw5300 != null) {
					gdResult = (Grid) pBw5300.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw5300Dialog/bw5300DialogInclude");
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
					BankMaintenanceService bankMaintenanceService = null;
					BankSystemMATN bankSystemMATN = null;
					try {
						bankMaintenanceService = BillerwebServiceFactory.getBankMaintenanceService();
						bankSystemMATN = bw5310Form.toBankMaintenance();
						bankSystemMATN.setLAST_CHNG_BY(getUserName());
						bankMaintenanceService.update(bankSystemMATN, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Update Bank Maintenance", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BANK_SYSM_MATN_ID")) {
							Validation.isExist(intBankMaintenanceId, true, AppUtil.toString(intBankMaintenanceId.getValue()));

						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Bank Maintenance", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						bankMaintenanceService = null;
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW5300_REDIRECT_PATH))) {
				Page pBw5300 = Executions.getCurrent().getDesktop().getPageIfAny("pBw5300");
				log.info("Page:" + pBw5300);
				Grid gdResult = null;
				if (pBw5300 != null) {
					gdResult = (Grid) pBw5300.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw5300Dialog/bw5300DialogInclude");
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
			params.put("Bank Maintenance ID", bw5310Form.getBankMaintenanceId() + "");
			params.put("Bank Maintenance Name", bw5310Form.getBankMaintenanceName() + "");
			params.put("Bank Service ID", bw5310Form.getBankServiceId() + "");
			params.put("Start Time", bw5310Form.getStartTime() == null ? "null" : bw5310Form.getStartTime().toString());
			params.put("End Time", bw5310Form.getEndTime() == null ? "null" : bw5310Form.getEndTime().toString());
			params.put("ActiveFlag", bw5310Form.getActiveFlag() + "");
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
						txBankMaintenanceName.setValue(null);
						lbBankServiceId.setSelectedIndex(0);
						txBankServiceName.setValue(null);
						tbStartTime.setValue(null);
						tbEndTime.setValue(null);
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
				redirect(AppConfiguration.getValue(AppConfiguration.BW5300_REDIRECT_PATH));
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}

	/* ### lbBankServiceId Event ### */
	@Listen("onSelect = #lbBankServiceId")
	public void onSelectBankServiceId() {
		log.info(getOperationLogMessage(pageName, "onSelectBankServiceId", ""));
		BankServicebean bankService = null;
		try {
			Clients.clearWrongValue(lbBankServiceId);

			bankService = lbBankServiceId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(bankService) && AppUtil.isNotNull(bankService.getBANK_SRVC_ID())) {
				txBankServiceName.setValue(bankService.getBANK_SRVC_NAME());
			} else {
				txBankServiceName.setValue(null);
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectInboundMapId", ex));
			showErrorMessage(ex);
		} finally {
			bankService = null;
		}
	}

	@Listen("onBlur= #tbStartTime")
	public void onBlurStartTime(Event event) {
		try {
			validateStartTime();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onBlur= #tbEndTime")
	public void onBlurEndTime(Event event) {
		try {
			validateEndTime();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	/* ## Validation ## */
	private void validateBankServiceId() throws WrongValueException, Exception {
		BankServicebean value = lbBankServiceId.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbBankServiceId, value.getBANK_SRVC_ID(), lBankServiceId.getValue());
	}

	private void validateStartTime() throws WrongValueException, Exception {
		Validation.isNotEmpty(tbStartTime, tbStartTime.getValue(), lStartTime.getValue());
	}

	private void validateEndTime() throws WrongValueException, Exception {
		Validation.isNotEmpty(tbEndTime, tbEndTime.getValue(), lEndTime.getValue());
		Validation.isEndTimeNotLessThanStartTime(tbEndTime, tbStartTime.getValue(), tbEndTime.getValue(), lStartTime.getValue(), lEndTime.getValue());
	}

}
