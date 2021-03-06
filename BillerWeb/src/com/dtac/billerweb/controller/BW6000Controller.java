package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BankFdmCode;
import mfs.biller.persistence.bean.BankMasterBean;
import mfs.biller.persistence.bean.BankReportTransParam;
import mfs.biller.persistence.bean.BankServicebean;
import mfs.biller.persistence.bean.ReportTransDetail;
import mfs.biller.util.DateTimeUtil;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.SimpleDateConstraint;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.Dialog;
import com.dtac.billerweb.common.SearchPageController;
import com.dtac.billerweb.data.BankReportTranSO;
import com.dtac.billerweb.data.BankServiceSO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.listmodel.BW2300ListModel;
import com.dtac.billerweb.listmodel.BW6000ListModel;
import com.dtac.billerweb.listmodel.selectbox.BankCodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BankFDMCodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BankServiceListModel;
import com.dtac.billerweb.service.BankReportTranService;
import com.dtac.billerweb.service.BankServiceService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;

public class BW6000Controller extends SearchPageController {

	public BW6000Controller() {
		super("Bank Report Transaction", "6000");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW6000Controller.class);

	@Wire
	private Datebox dbFromDate;
	@Wire
	private Datebox dbToDate;
	@Wire
	private Timebox tbFromTime;
	@Wire
	private Timebox tbToTime;
	@Wire
	private Listbox lbBankCode;
	@Wire
	private Listbox lbBankService;
	@Wire
	private Textbox txTranId;
	@Wire
	private Listbox lbTransStatus;
	@Wire
	private Button btSearch;
	@Wire
	private Button btReset;

	@Override
	protected void init(Component com) throws Exception {
		// TODO Auto-generated method stub
		setupPage();
	}

	private void setupPage() throws BillerWebSessionTimeOutException {
		log.info("Set up Page");
		SelectBoxService selectBoxService = null;
		BankServiceListModel<BankServicebean> bankServiceListModel = null;
		BankCodeListModel<BankMasterBean> bankCodeListModel = null;
		BankFDMCodeListModel<BankFdmCode> bankFDMCodeListModel = null;
		try {
			/* ## Check Permission## */
			chkSearchPermission(btSearch);

			/* set Bank Service Dropdown list */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			bankServiceListModel = selectBoxService.getBankServiceListModel();
			bankServiceListModel.addItemAll();
			lbBankService.setModel(bankServiceListModel);
			/* set Bank Code Dropdown list */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			bankCodeListModel = selectBoxService.getBankCodeListModel();
			bankCodeListModel.addItemAll();
			lbBankCode.setModel(bankCodeListModel);

			/* set Transaction Status list */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			bankFDMCodeListModel = selectBoxService.getBankFDMCodeListModel(getUserInfo());
			bankFDMCodeListModel.addItemAll();
			lbTransStatus.setModel(bankFDMCodeListModel);
			/* ## Set default date time */
			setDefaultSearchDateTime();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			bankServiceListModel = null;
			bankCodeListModel = null;
		}
	}

	private void setDefaultSearchDateTime() {
		Date formDate = null;
		Date toDate = null;
		try {
			formDate = DateTimeUtil.minusDate(DateTimeUtil.getCurrent(), 1);
			formDate.setHours(0);
			formDate.setMinutes(0);
			formDate.setSeconds(0);
			toDate = DateTimeUtil.getCurrent();
			toDate.setHours(23);
			toDate.setMinutes(59);
			toDate.setSeconds(0);
			dbFromDate.setValue(formDate);
			dbToDate.setValue(toDate);
			tbFromTime.setValue(formDate);
			tbToTime.setValue(toDate);

			setDateConstraint();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			formDate = null;
			toDate = null;
		}
	}

	private void setDateConstraint() {
		try {
			String toDateStr = DateTimeUtil.getDateTime(dbToDate.getValue(), "yyyyMMdd");
			SimpleDateConstraint fromDateConstarint = new SimpleDateConstraint("before " + toDateStr + ",no future");
			dbFromDate.setConstraint(fromDateConstarint);
			String fromDateStr = DateTimeUtil.getDateTime(dbFromDate.getValue(), "yyyyMMdd");
			SimpleDateConstraint toDateConstarint = new SimpleDateConstraint("after " + fromDateStr + ",no future");
			dbToDate.setConstraint(toDateConstarint);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Override
	protected void setupPageFromSession() {
		// TODO Auto-generated method stub

	}

	/* ## Event ## */
	@Listen("onClick = #btSearch")
	public void clickSearch() {
		search();
	}

	private void search() {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Search", ""));
		checkSessionTimeOut("Search " + pageName);
		BankServicebean bankServicebean = null;
		BankMasterBean bankMasterBean = null;
		Date fromDate = null;
		Date toDate = null;
		Date fromTime = null;
		Date toTime = null;
		String transId = null;
		BankFdmCode bankFdmCode = null;
		try {
			BankReportTransParam criteria = new BankReportTransParam();

			bankServicebean = lbBankService.getSelectedItem().getValue();
			bankMasterBean = lbBankCode.getSelectedItem().getValue();
			fromDate = dbFromDate.getValue();
			toDate = dbToDate.getValue();
			fromTime = tbFromTime.getValue();
			toTime = tbToTime.getValue();
			transId = txTranId.getValue();
			bankFdmCode = lbTransStatus.getSelectedItem().getValue();
			if (fromDate != null && fromTime != null) {
				fromDate.setHours(fromTime.getHours());
				fromDate.setMinutes(fromTime.getMinutes());
			}
			if (toDate != null && toTime != null) {
				toDate.setHours(toTime.getHours());
				toDate.setMinutes(toTime.getMinutes());
			}
			criteria.setBANK_SRVC_ID(AppUtil.toString(bankServicebean.getBANK_SRVC_ID()));
			criteria.setTRNS_BANK_CODE(bankMasterBean.getBANK_CODE());
			criteria.setFROM_TRNS_DTTM(fromDate);
			criteria.setTO_TRNS_DTTM(toDate);
			criteria.setTRNS_ID(transId);
			criteria.setTRNS_STTS_FLAG(bankFdmCode.getBANK_MSGE_CODE());
			BW6000ListModel results = new BW6000ListModel(paging.getActivePage() + 1, Integer.parseInt(AppConfiguration.getValue(AppConfiguration.PAGE_SIZE_KEY)), getUserInfo());
			results.setCriteria(criteria);
			setGridResultModel(results);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("From Date", fromDate == null ? "null" : fromDate.toString());
			params.put("To Date", toDate == null ? "null" : toDate.toString());
			params.put("From Time", fromTime == null ? "null" : fromTime.toString());
			params.put("To Time", toTime == null ? "null" : toTime.toString());
			params.put("Bank Code", bankMasterBean.getBANK_CODE() + "");
			params.put("Bank Service Id", bankServicebean.getBANK_SRVC_ID() + "");
			params.put("Transaction ID", transId + "");
			params.put("Transaction Status", bankFdmCode.getBANK_MSGE_CODE());
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Search", requestDate, "");
	}

	@Listen("onClick = #btReset")
	public void reset() {
		log.info("Click Reset " + pageName);
		try {
			dbFromDate.setConstraint(new SimpleDateConstraint(""));
			dbToDate.setConstraint(new SimpleDateConstraint(""));
			setDefaultSearchDateTime();

			if (lbBankCode.getItemCount() > 0) {
				lbBankCode.setSelectedIndex(0);
			}
			if (lbBankService.getItemCount() > 0) {
				lbBankService.setSelectedIndex(0);
			}

			txTranId.setValue(null);
			if (lbTransStatus.getItemCount() > 0) {
				lbTransStatus.setSelectedIndex(0);
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		} finally {

		}
	}

	@Listen("onBlur = #dbFromDate")
	public void clickDbFromDate() {
		log.info("Click Reset " + pageName);
		try {
			setDateConstraint();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		} finally {

		}
	}

	@Listen("onBlur = #dbToDate")
	public void clickDbToDate() {
		log.info("Click Reset " + pageName);
		try {
			setDateConstraint();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		} finally {

		}
	}

	@Listen("onEdit = #gdResult")
	public void edit(ForwardEvent event) {
		// checkSessionTimeOut("Edit " + pageName);
		BankReportTranService bankReportTranService = null;
		bankReportTranService = BillerwebServiceFactory.getBankReportTranService();

		Row row = new Row();
		BankReportTranSO bankReportTranSO = null;
		try {
			row = (Row) event.getOrigin().getTarget();
			bankReportTranSO = row.getValue();
			log.info(getOperationLogMessage(pageName, "View", "View ID::" + bankReportTranSO.getTranID()));
			bankReportTranSO = bankReportTranService.getBankReportTranByID(bankReportTranSO.getTranID(), getUserInfo());
			Dialog.openBw6030MedalDialog(AppConfiguration.getValue(AppConfiguration.BW6000_REDIRECT_PATH), bankReportTranSO, ((row.getIndex() + 1) + (paging.getActivePage() * paging.getPageSize())));
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Edit", "View ID::" + bankReportTranSO.getTranID(), ex));
			showErrorMessage(ex);
		} finally {
		}
		log.info(getStopLogMessage(pageName));
	}
}
