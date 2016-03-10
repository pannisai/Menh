package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BankMasterBean;
import mfs.biller.persistence.bean.BankServicebean;
import mfs.biller.persistence.bean.GWBankDetail;

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
import com.dtac.billerweb.data.BankGatewaySO;
import com.dtac.billerweb.data.BankServiceSO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW5100Form;
import com.dtac.billerweb.form.BW5200Form;
import com.dtac.billerweb.listmodel.selectbox.BankCodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BankServiceListModel;
import com.dtac.billerweb.service.BankGatewayService;
import com.dtac.billerweb.service.BankServiceService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;

public class BW5200Controller extends SearchPageController{
	
	public BW5200Controller() {
		super("Bank Gateway","5200");
		// TODO Auto-generated constructor stub
	}
	private Logger log = Logger.getLogger(BW5200Controller.class);
	private static String SESS_BANK_GATEWAY_FROM = "SESS_BANK_GATEWAY_FROM";
	
	@Wire
	private Listbox lbBankService;
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
	private void setupPage() throws BillerWebSessionTimeOutException {
		log.info("Set up Page");
		SelectBoxService selectBoxService = null;
		BankServiceListModel<BankServicebean> bankServiceListModel = null;
	
		try {
			/* ## Check Permission## */
			chkSearchPermission(btSearch);
			chkInsertPermission(btCreate);

			gdResult.setPaginal(paging);
			/* set Bank Service Dropdown list */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			bankServiceListModel = selectBoxService.getBankServiceListModel();
			bankServiceListModel.addItemAll();
			lbBankService.setModel(bankServiceListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			bankServiceListModel = null;
		}
	}
	@Override
	protected void setupPageFromSession() {
		// TODO Auto-generated method stub
		try {
			BW5200Form bw5200 = (BW5200Form) getSession(SESS_BANK_GATEWAY_FROM);

			if (bw5200 != null && bw5200.getBankService() != null && bw5200.getStatus() != null) {
				log.debug("InSetFormSession");
				try {
					lbBankService.setSelectedIndex(lbBankService.getIndexOfItem(bw5200.getBankService()));
					lbStatus.setSelectedIndex(lbStatus.getIndexOfItem(bw5200.getStatus()));
				} catch (ArrayIndexOutOfBoundsException aiobe) {

				}
				if (getAuthorization().isSearch()) {
					search();
				}
				removeSession(SESS_BANK_GATEWAY_FROM);
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
			BW5200Form bw5200Form = new BW5200Form();
			bw5200Form.setBankService(lbBankService.getSelectedItem());
			bw5200Form.setStatus(lbStatus.getSelectedItem());
			setSession(SESS_BANK_GATEWAY_FROM, bw5200Form);
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
		BankGatewayService bankGatewayService = null;
		List<BankGatewaySO> bankGatewaySOs = null;
		ListModel<BankGatewaySO> bankGatewaySOListModel = null;
		BankServicebean bankServicebean = null;
		String status = "";
		try {

			bankGatewayService = BillerwebServiceFactory.getBankGatewayService();
			GWBankDetail criteria = new GWBankDetail();

			try {
				bankServicebean = lbBankService.getSelectedItem().getValue();
				criteria.setBANK_SRVC_ID(bankServicebean.getBANK_SRVC_ID());
				status = lbStatus.getSelectedItem().getValue().toString();
				criteria.setACT_FLAG(status);
			} catch (NullPointerException npe) {

			}
			bankGatewaySOs = bankGatewayService.searchBankGateway(criteria, 0, 0, getUserInfo());
			bankGatewaySOListModel = new SimpleListModel<BankGatewaySO>(bankGatewaySOs);

			setGridResultModel(bankGatewaySOListModel);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Bank Service Id", bankServicebean.getBANK_SRVC_ID() + "");
			params.put("Status", status);
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		} finally {
			bankGatewayService = null;
			bankGatewaySOs = null;
			bankGatewaySOListModel = null;
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
			redirect(AppConfiguration.getValue(AppConfiguration.BW5210_REDIRECT_PATH));
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
			Dialog.openBw5210MedalDialog(AppConfiguration.getValue(AppConfiguration.BW5200_REDIRECT_PATH),row.getValue().toString());
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
