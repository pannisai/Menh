package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BankMasterBean;
import mfs.biller.persistence.bean.DropdownlistBillservice;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.SimpleListModel;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.Dialog;
import com.dtac.billerweb.common.SearchPageController;
import com.dtac.billerweb.data.ERPBankAccountSO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1600Form;
import com.dtac.billerweb.listmodel.selectbox.BankCodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.billerweb.service.ERPBankAccountService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;

public class BW1600Controller extends SearchPageController {

	private Logger log = Logger.getLogger(BW1600Controller.class);

	public BW1600Controller() throws BillerWebSessionTimeOutException {
		super("ERP Bank Acc","1600");
		// TODO Auto-generated constructor stub
	}

	private static String SESS_ERP_BANK_ACCT_FORM = "SESS_ERP_BANK_ACCT_FORM";

	@Wire
	private Listbox lbBillerShortCode;
	@Wire
	private Listbox lbBankName;
	@Wire
	private Listbox lbStatus;
	@Wire
	private Button btSearch;
	@Wire
	private Button btCreate;

	@Override
	protected void init(Component com) throws Exception {
		// TODO Auto-generated method stub		
		setupPage();
	}

	private void setupPage() {
		log.info("Set up Page");
		SelectBoxService selectBoxService = null;
		BillerServiceListModel<DropdownlistBillservice> billerServiceListModel = null;
		BankCodeListModel<BankMasterBean> bankCodeListModel = null;
		try {
			/* ## Check Permission## */
			chkSearchPermission(btSearch);
			chkInsertPermission(btCreate);
			gdResult.setPaginal(paging);
			/* set selectbox list */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			billerServiceListModel = selectBoxService.getBillerServiceListModel();
			billerServiceListModel.addItemAll();
			lbBillerShortCode.setModel(billerServiceListModel);

			bankCodeListModel = selectBoxService.getBankCodeListModel();
			bankCodeListModel.addItemAll();
			lbBankName.setModel(bankCodeListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerServiceListModel = null;
		}
	}

	@Override
	protected void setupPageFromSession() {
		// TODO Auto-generated method stub
		try {
			BW1600Form bw1600Form = (BW1600Form) getSession(SESS_ERP_BANK_ACCT_FORM);

			if (bw1600Form != null && bw1600Form.getBillerShortCode() != null && bw1600Form.getBankName() != null && bw1600Form.getStatus() != null) {
				try {
					lbBillerShortCode.setSelectedIndex(lbBillerShortCode.getIndexOfItem(bw1600Form.getBillerShortCode()));
					lbBankName.setSelectedIndex(lbBankName.getIndexOfItem(bw1600Form.getBankName()));
					lbStatus.setSelectedIndex(lbStatus.getIndexOfItem(bw1600Form.getStatus()));
				} catch (ArrayIndexOutOfBoundsException aiobe) {

				}
				if (getAuthorization().isSearch()) {
					search();
				}
				removeSession(SESS_ERP_BANK_ACCT_FORM);
			}
		} catch (BillerWebSessionTimeOutException stex) {
			throw stex;
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setCurrentFormSession() {
		log.info(getOperationLogMessage(pageName, "setCurrentFormSession", ""));
		try {
			BW1600Form bw1600Form = new BW1600Form();
			bw1600Form.setBillerShortCode(lbBillerShortCode.getSelectedItem());
			bw1600Form.setBankName(lbBankName.getSelectedItem());
			bw1600Form.setStatus(lbStatus.getSelectedItem());
			setSession(SESS_ERP_BANK_ACCT_FORM, bw1600Form);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "setCurrentFormSession", ex));
			showErrorMessage(ex);
		} finally {

		}
	}

	/* ## Event ## */
	@Listen("onClick = #btSearch")
	public void clickSearch() {
		setCurrentFormSession();
		search();
	}

	private void search() {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Search", ""));
		checkSessionTimeOut("Search " + pageName);
		ERPBankAccountService erpBankAccountService = null;

		List<ERPBankAccountSO> erpBankAccountSOs = null;
		ListModel<ERPBankAccountSO> erpBankAccSOListModel = null;
		DropdownlistBillservice dropdownlistBillservice = null;
		BankMasterBean bankMasterBean = null;
		String status = "";
		try {
			erpBankAccountService = BillerwebServiceFactory.getERPBankAccountService();
			try {
				dropdownlistBillservice = lbBillerShortCode.getSelectedItem().getValue();
				bankMasterBean = lbBankName.getSelectedItem().getValue();
				status = lbStatus.getSelectedItem().getValue().toString();

			} catch (NullPointerException npe) {

			}
			erpBankAccountSOs = erpBankAccountService.searchERPBankAccount(dropdownlistBillservice.getBLLR_SRVC_ID(), bankMasterBean.getBANK_CODE(), status, 0, 0, getUserInfo());
			erpBankAccSOListModel = new SimpleListModel<ERPBankAccountSO>(erpBankAccountSOs);

			setGridResultModel(erpBankAccSOListModel);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Service ID", AppUtil.toString(dropdownlistBillservice.getBLLR_SRVC_ID()));
			params.put("Bank Name",bankMasterBean.getBANK_CODE());
			params.put("Status", status);
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		} finally {
			erpBankAccountService = null;
			erpBankAccountSOs = null;
			erpBankAccSOListModel = null;
			status = null;
		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Search", requestDate, "");
	}

	@Listen("onClick = #btCreate")
	public void create() {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Create", ""));
		checkSessionTimeOut("Create " + pageName);
		try {
			redirect(AppConfiguration.getValue(AppConfiguration.BW1610_REDIRECT_PATH));
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Create", ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Create", requestDate, ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Create", requestDate, "");
	}

	@Listen("onEdit = #gdResult")
	public void edit(ForwardEvent event) {
		Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Edit " + pageName);
		Row row = new Row();
		try {
			row = (Row) event.getOrigin().getTarget();
			log.info(getOperationLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue()));
			Dialog.openBw1610MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1600_REDIRECT_PATH), row.getValue().toString());
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue(), ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Edit", requestDate, ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Edit", requestDate, "");
	}

	@Listen("onRefresh = #gdResult")
	public void refreshResultGrid() {
		log.info(getOperationLogMessage(pageName, "refreshResultGrid", ""));
		try {
			if (getAuthorization().isSearch()) {
				search();
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "refreshResultGrid", ex));
			showErrorMessage(ex);
		}
	}
}
