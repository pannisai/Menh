package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BankMasterBean;
import mfs.biller.persistence.bean.DropdownlistBillservice;
import mfs.biller.persistence.bean.ERPBankAccount;
import mfs.exception.IsExistException;
import mfs.biller.util.DateTimeUtil;

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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.SimpleDateConstraint;
import org.zkoss.zul.Spinner;
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
import com.dtac.billerweb.form.BW1610Form;
import com.dtac.billerweb.listmodel.selectbox.BankCodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.billerweb.service.ERPBankAccountService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1610Controller extends EditPageController {

	public BW1610Controller() {
		super("Edit ERP Bank Acc", "1610");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW1610Controller.class);
	private BW1610Form bw1610Form = new BW1610Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label title;
	@Wire
	private Intbox intAccountId;
	@Wire
	private Listbox lbBillerShortCode;
	@Wire
	private Listbox lbBankCode;
	@Wire
	private Spinner spBankAccountSeq;
	@Wire
	private Textbox txBankAccount;
	@Wire
	private Textbox txBankAccountName;
	@Wire
	private Listbox lbBankAccountType;
	@Wire
	private Textbox txBankBranchName;
	@Wire
	private Textbox txERPSuplierCode;
	@Wire
	private Textbox txERPLineSuplierCode;
	@Wire
	private Textbox txERPSuplierSitCode;
	@Wire
	private Textbox txERPLineSuplierSitCode;
	@Wire
	private Textbox txERPCustomerCode;
	@Wire
	private Textbox txERPCustBillToCode;
	@Wire
	private Textbox txERPCustShipToCode;
	@Wire
	private Datebox dbActiveDate;
	@Wire
	private Datebox dbExpireDate;

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
		SelectBoxService selectBoxService = null;
		BillerServiceListModel<DropdownlistBillservice> billerServiceListModel = null;
		BankCodeListModel<BankMasterBean> bankCodeListModel = null;
		try {
			/* ## Set up Dropdrown list ## */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			billerServiceListModel = selectBoxService.getBillerServiceListModel();
			billerServiceListModel.addItemSelect();
			lbBillerShortCode.setModel(billerServiceListModel);
			/* ## Set Bank Code list ## */
			bankCodeListModel = selectBoxService.getBankCodeListModel();
			bankCodeListModel.addItemSelect();
			lbBankCode.setModel(bankCodeListModel);

			setDefaultDateTime();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
		}
	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave);
			title.setValue(AppMessage.getMessage(AppMessage.BW1610_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			i = (Include) Path.getComponent("/bw1610Dialog/bw1610DialogInclude");
			comps = i != null ? i.getFellows() : updatePage.getFellows();
			if (chkUpdatePermission(comps, btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW1610_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW1610_VIEW_TITLE));
			}

			loadFormData(oid);
			refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i = null;
			comps = null;
		}
	}

	private void loadFormData(Integer oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		ERPBankAccountService erpBankAccountService = null;
		ERPBankAccount erpBankAccount = null;
		try {
			erpBankAccountService = BillerwebServiceFactory.getERPBankAccountService();
			erpBankAccount = erpBankAccountService.getByID(oid, getUserInfo());
			bw1610Form = bw1610Form.toBW1610Form(erpBankAccount);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		BillerServiceListModel<DropdownlistBillservice> billerServiceListModel = null;
		BankCodeListModel<BankMasterBean> bankCodeListModel = null;
		try {
			intAccountId.setValue(bw1610Form.getAccountId());
			// lbBillerShortCode
			billerServiceListModel = (BillerServiceListModel) lbBillerShortCode.getModel();
			if (billerServiceListModel != null) {
				int billerShortCodeIndex = billerServiceListModel.findIndexOfId(bw1610Form.getBillerShotCode());
				if (billerShortCodeIndex > -1 && lbBillerShortCode.getItemCount() > billerShortCodeIndex) {
					billerServiceListModel.addDataToSelection(billerShortCodeIndex);
				}
			}

			// lbBankCode
			bankCodeListModel = (BankCodeListModel) lbBankCode.getModel();
			if (bankCodeListModel != null) {
				int bankCodeIndex = bankCodeListModel.findIndexOfId(bw1610Form.getBankCode());
				if (bankCodeIndex > -1 && lbBankCode.getItemCount() > bankCodeIndex) {
					bankCodeListModel.addDataToSelection(bankCodeIndex);
				}
			}
			spBankAccountSeq.setValue(bw1610Form.getBankAccountSeq());
			txBankAccount.setValue(bw1610Form.getBankAccount());
			txBankAccountName.setValue(bw1610Form.getBankAccountName());
			lbBankAccountType.setSelectedIndex((bw1610Form.getBankAccountType() != null && bw1610Form.getBankAccountType().equalsIgnoreCase("SA")) ? 0 : 1);
			txBankBranchName.setValue(bw1610Form.getBankBranchName());
			txERPSuplierCode.setValue(bw1610Form.getErpSuppCode());
			txERPSuplierSitCode.setValue(bw1610Form.getErpSuppSitCode());
			txERPLineSuplierCode.setValue(bw1610Form.getErpLineSuppCode());
			txERPLineSuplierSitCode.setValue(bw1610Form.getErpLineSuppSitCode());
			txERPCustomerCode.setValue(bw1610Form.getErpCustomerCode());
			txERPCustBillToCode.setValue(bw1610Form.getErpCustBillToCode());
			txERPCustShipToCode.setValue(bw1610Form.getErpCustShipToCode());
			dbActiveDate.setValue(bw1610Form.getActiveDate());
			dbExpireDate.setValue(bw1610Form.getExpireDate());
			lbActiveFlag.setSelectedIndex((bw1610Form.getActiveFlag() != null && bw1610Form.getActiveFlag().equalsIgnoreCase("A")) ? 0 : 1);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	public void updateBW1610Form() {
		try {
			bw1610Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
			bw1610Form.setAccountId(intAccountId.getValue());
			DropdownlistBillservice dropdownlistBillservice = lbBillerShortCode.getSelectedItem().getValue();
			bw1610Form.setBillerShotCode(dropdownlistBillservice.getBLLR_SRVC_ID());
			BankMasterBean bankMasterBean = lbBankCode.getSelectedItem().getValue();
			bw1610Form.setBankCode(bankMasterBean.getBANK_CODE());
			bw1610Form.setBankAccountSeq(spBankAccountSeq.getValue());
			bw1610Form.setBankAccount(txBankAccount.getValue());
			bw1610Form.setBankAccountName(txBankAccountName.getValue());
			bw1610Form.setBankAccountType(lbBankAccountType.getSelectedItem().getValue().toString());
			bw1610Form.setBankBranchName(txBankBranchName.getValue());
			bw1610Form.setErpSuppCode(txERPSuplierCode.getValue());
			bw1610Form.setErpLineSuppCode(txERPLineSuplierCode.getValue());
			bw1610Form.setErpSuppSitCode(txERPSuplierSitCode.getValue());
			bw1610Form.setErpLineSuppSitCode(txERPLineSuplierSitCode.getValue());
			bw1610Form.setErpCustomerCode(txERPCustomerCode.getValue());
			bw1610Form.setErpCustBillToCode(txERPCustBillToCode.getValue());
			bw1610Form.setErpCustShipToCode(txERPCustShipToCode.getValue());
			bw1610Form.setActiveDate(dbActiveDate.getValue());
			bw1610Form.setExpireDate(dbExpireDate.getValue());
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
			log.info(getOperationLogMessage(pageName, "Save ERP Bank Account", ""));
			validateBankAccount();
			validateERPSuplierCode();
			validateERPLineSuplierCode();
			validateERPCustomerCode();
			updateBW1610Form();
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
			log.error(getErrorLogMessage(pageName, "Save ERP Bank Account", getErrorParames(), ex));
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
					ERPBankAccountService erpBankAccountService = null;
					ERPBankAccount erpBankAccount = null;
					try {
						erpBankAccountService = BillerwebServiceFactory.getERPBankAccountService();
						erpBankAccount = bw1610Form.toERPBankAccount();
						erpBankAccount.setCRTD_BY(getUserName());
						erpBankAccount.setLAST_CHNG_BY(getUserName());
						final Integer oid = erpBankAccountService.save(erpBankAccount, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									if (isDialog()) {
										processSaveInDialogMode();
									} else {
										redirect(AppConfiguration.getValue(AppConfiguration.BW1610_REDIRECT_PATH) + "?oid=" + oid);
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save ERP Bank Account", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_SRVC_FIN_ID")) {
							Validation.isExist(txBankAccount, true, txBankAccount.getValue());
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save ERP Bank Account", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						erpBankAccountService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1600_REDIRECT_PATH))) {
				Page pBw1600 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1600");
				log.info("Page:" + pBw1600);
				Grid gdResult = null;
				if (pBw1600 != null) {
					gdResult = (Grid) pBw1600.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw1600Dialog/bw1600DialogInclude");
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
		checkSessionTimeOut("Update " + pageName);
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					ERPBankAccountService erpBankAccountService = null;
					ERPBankAccount erpBankAccount = null;
					try {
						erpBankAccountService = BillerwebServiceFactory.getERPBankAccountService();
						erpBankAccount = bw1610Form.toERPBankAccount();
						erpBankAccount.setLAST_CHNG_BY(getUserName());
						erpBankAccountService.update(erpBankAccount, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Update ERP Bank Account", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_SRVC_FIN_ID")) {
							Validation.isExist(txBankAccount, true, txBankAccount.getValue());
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update ERP Bank Account", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						erpBankAccountService = null;
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1600_REDIRECT_PATH))) {
				Page pBw1600 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1600");
				log.info("Page:" + pBw1600);
				Grid gdResult = null;
				if (pBw1600 != null) {
					gdResult = (Grid) pBw1600.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw1600Dialog/bw1600DialogInclude");
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
			params.put("Account ID", bw1610Form.getAccountId() + "");
			params.put("Biller Short Code", bw1610Form.getBillerShotCode() + "");
			params.put("Bank Code", bw1610Form.getBankCode() + "");
			params.put("Bank Account Seq.", bw1610Form.getBankAccountSeq() + "");
			params.put("Bank Account", bw1610Form.getBankAccount() + "");
			params.put("Bank Account Name", bw1610Form.getBankAccountName() + "");
			params.put("Bank Account Type", bw1610Form.getBankAccountType() + "");
			params.put("Bank Branch Name", bw1610Form.getBankBranchName() + "");
			params.put("Erp Supplier Code", bw1610Form.getErpSuppCode() + "");
			params.put("Erp Line Supplier Code", bw1610Form.getErpLineSuppCode() + "");
			params.put("Erp Supplier Sit Code", bw1610Form.getErpSuppSitCode() + "");
			params.put("Erp Line Supplier Sit Code", bw1610Form.getErpLineSuppSitCode() + "");
			params.put("ERP Customer Code", bw1610Form.getErpCustomerCode());
			params.put("ERP Customer bill to Code", bw1610Form.getErpCustBillToCode());
			params.put("ERP Customer Ship to Code", bw1610Form.getErpCustShipToCode());
			params.put("ActiveFlag", bw1610Form.getActiveFlag() + "");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	private void setDefaultDateTime() {
		Date activeDate = null;
		Date expireDate = null;
		try {
			activeDate = DateTimeUtil.getCurrent();
			activeDate.setHours(0);
			activeDate.setMinutes(0);
			activeDate.setSeconds(0);

			dbActiveDate.setValue(activeDate);

			setDateConstraint();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			activeDate = null;
			expireDate = null;
		}
	}

	private void setDateConstraint() {
		try {
			if (dbExpireDate.getValue() != null) {
				String expireDate = DateTimeUtil.getDateTime(dbExpireDate.getValue(), "yyyyMMdd");
				SimpleDateConstraint fromDateConstarint = new SimpleDateConstraint("before " + expireDate);
				dbActiveDate.setConstraint(fromDateConstarint);
			}
			String activeDateStr = DateTimeUtil.getDateTime(dbActiveDate.getValue(), "yyyyMMdd");
			SimpleDateConstraint toDateConstarint = new SimpleDateConstraint("after " + activeDateStr);
			dbExpireDate.setConstraint(toDateConstarint);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onClick = #btReset")
	public void reset() {
		log.info(getOperationLogMessage(pageName, "Reset", ""));
		EventListener resetConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {
						lbBillerShortCode.setSelectedIndex(0);
						lbBankCode.setSelectedIndex(0);
						spBankAccountSeq.setValue(1);
						txBankAccount.setValue(null);
						txBankAccountName.setValue(null);
						lbBankAccountType.setSelectedIndex(0);
						txBankBranchName.setValue(null);
						txERPSuplierCode.setValue(null);
						txERPLineSuplierCode.setValue(null);
						txERPSuplierSitCode.setValue(null);
						txERPLineSuplierSitCode.setValue(null);
						txERPCustomerCode.setValue(null);
						txERPCustBillToCode.setValue(null);
						txERPCustShipToCode.setValue(null);
						lbActiveFlag.setSelectedIndex(0);
						setDefaultDateTime();
						dbExpireDate.setValue(null);
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
				redirect(AppConfiguration.getValue(AppConfiguration.BW1600_REDIRECT_PATH));
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}

	@Listen("onBlur = #dbActiveDate")
	public void clickDbActiveDate() {
		log.info("Click DbActiveDate " + pageName);
		try {
			setDateConstraint();

		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		} finally {

		}
	}

	@Listen("onBlur = #dbExpireDate")
	public void clickDbExpireDate() {
		log.info("Click DbExpireDate " + pageName);
		try {
			setDateConstraint();
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		} finally {

		}
	}

	@Listen("onBlur= #txBankAccount")
	public void onBlurBankAccount(Event event) {
		try {
			validateBankAccount();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onBlur= #txERPSuplierCode")
	public void onBlurERPSuplierCode(Event event) {
		try {
			validateERPSuplierCode();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onBlur= #txERPLineSuplierCode")
	public void onBlurERPLineSuplierCode(Event event) {
		try {
			validateERPLineSuplierCode();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onBlur= #txERPCustomerCode")
	public void onBlurERPCustomerCode(Event event) {
		try {
			validateERPCustomerCode();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void validateBankAccount() throws WrongValueException, Exception {
		Validation.isStrNumber(txBankAccount, txBankAccount.getValue() + "");
	}

	private void validateERPSuplierCode() throws WrongValueException, Exception {
		Validation.isStrNumber(txERPSuplierCode, txERPSuplierCode.getValue() + "");
	}

	private void validateERPLineSuplierCode() throws WrongValueException, Exception {
		Validation.isStrNumber(txERPLineSuplierCode, txERPLineSuplierCode.getValue() + "");
	}

	private void validateERPCustomerCode() throws WrongValueException, Exception {
		Validation.isStrNumber(txERPCustomerCode, txERPCustomerCode.getValue() + "");
	}
}
