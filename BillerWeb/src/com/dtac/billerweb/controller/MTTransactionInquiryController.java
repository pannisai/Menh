package com.dtac.billerweb.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import mfs.biller.util.DateTimeUtil;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleDateConstraint;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;

import com.dtac.billerweb.common.SearchPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.serviceImpl.ChannelManagementWS;
import com.dtac.billerweb.util.AppUtil;
import com.terrafive.ktb.webservice.TransactionInquiryResponseDTO;

/**
 * This class is used to perform operation on MT Transaction Inquiry(ktbInquiry.zul) page. 
 * Shiv
 *
 * 26-Feb-2015
 */
public class MTTransactionInquiryController extends SearchPageController {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4204769213880023425L;

	public MTTransactionInquiryController() {
		super("MT Transaction Inquiry", "7000");
	}

	private Logger log = Logger.getLogger(MTTransactionInquiryController.class);

	@Wire
	private Textbox fundamoTransactionRef;

	@Wire
	private Textbox bankTransactionRef;

	@Wire
	private Listbox transactionType;

	@Wire
	private Datebox fromDate;

	@Wire
	private Datebox toDate;

	@Wire
	private Button btSearch;

	@Wire
	private Button btReset;

	@Wire
	private Button btResendCode;

	@Override
	protected void init(Component com) throws Exception {
		setupPage();
	}

	private void setupPage() throws BillerWebSessionTimeOutException {
		log.info("Set up Page");
		try {
			/* ## Check Permission## */
			chkSearchPermission(btSearch);
			ChannelManagementWS service = new ChannelManagementWS();
			transactionType.setModel(service.getTransactionTypeList());
			transactionType.clearSelection();
			//this.setDefaultSearchDateTime();
			gdResult.setPaginal(paging);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} 
	}

	private void setDefaultSearchDateTime() {
		Date fromDateLocal = null;
		Date toDateLocal = null;
		try {
			fromDateLocal = DateTimeUtil.minusDate(DateTimeUtil.getCurrent(), 1);
			fromDateLocal.setHours(0);
			fromDateLocal.setMinutes(0);
			fromDateLocal.setSeconds(0);
			toDateLocal = DateTimeUtil.getCurrent();
			toDateLocal.setHours(23);
			toDateLocal.setMinutes(59);
			toDateLocal.setSeconds(59);
			fromDate.setValue(fromDateLocal);
			toDate.setValue(toDateLocal);

			this.setDateConstraint();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} 
	}

	private void setDateConstraint() {
		try {
			if (toDate.getValue() != null) {
				String toDateStr = DateTimeUtil.getDateTime(toDate.getValue(), "yyyyMMdd");
				//SimpleDateConstraint fromDateConstarint = new SimpleDateConstraint("before " + toDateStr + ",no future");
				Date fromDateStartRange = DateTimeUtil.minusDate(toDate.getValue(), 90);
				String fromDateStartRangeStr = DateTimeUtil.getDateTime(fromDateStartRange, "yyyyMMdd");
				SimpleDateConstraint fromDateConstarint = new SimpleDateConstraint("between " + fromDateStartRangeStr + " and " + toDateStr + ",no future");
				fromDate.setConstraint(fromDateConstarint);
			}
			
			if (fromDate.getValue() != null) {
				String fromDateStr = DateTimeUtil.getDateTime(fromDate.getValue(), "yyyyMMdd");
				Date toDateEndRange = this.addDate(fromDate.getValue(), 90);
				String toDateEndRangeStr = DateTimeUtil.getDateTime(toDateEndRange, "yyyyMMdd");
				//SimpleDateConstraint toDateConstarint = new SimpleDateConstraint("after " + fromDateStr + ",no future");
				SimpleDateConstraint toDateConstarint = new SimpleDateConstraint("between " + fromDateStr + " and " + toDateEndRangeStr + ",no future");
				toDate.setConstraint(toDateConstarint);
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	/**
	 * Method to add days in date.
	 * @param date
	 * @param day
	 * @return
	 */
	private Date addDate(Date date, int day) {
		Calendar c = Calendar.getInstance(Locale.US);
		c.setTime(date);
		c.set(Calendar.DATE, c.get(Calendar.DATE) + day);
		return c.getTime();
	}

	/* ## Event ## */
	@Listen("onClick = #btSearch")
	public void search() {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Search", ""));

		checkSessionTimeOut("Search " + pageName);

		String fundamoTransRef = fundamoTransactionRef.getValue();
		String bankTransRef = bankTransactionRef.getValue();
		Date fromDateValue = fromDate.getValue();
		Date toDateValue = toDate.getValue();
		
		String transactionTypeValue = null;
		if(transactionType.getSelectedCount() > 0){
			transactionTypeValue = transactionType.getSelectedItem().getValue();
		}
		
		log.info("fundamoTransRef:"+fundamoTransRef);
		log.info("bankTransRef:"+bankTransRef);
		log.info("transactionTypeValue:"+transactionTypeValue);
		log.info("From Date:"+fromDateValue);
		log.info("To Date:"+toDateValue);
		try {
			List<TransactionInquiryResponseDTO> responseList = new ArrayList<TransactionInquiryResponseDTO>();
			
			boolean isValidSearchCriteria = true;
			
			if (fromDateValue == null && toDateValue == null 
					&& transactionType.getSelectedCount() == 0 
					&& fundamoTransRef.isEmpty() 
					&& bankTransRef.isEmpty()) {
				transactionType.clearSelection();
				isValidSearchCriteria = false;
				Messagebox.show("กรุณาระบุเงื่อนไขการค้นหา", "", Messagebox.OK, Messagebox.EXCLAMATION);
			} else if ((fromDateValue != null && toDateValue == null) || (fromDateValue == null && toDateValue != null)) {
				isValidSearchCriteria = false;
				Messagebox.show("กรุณาเลือกช่วงวันที่", "", Messagebox.OK, Messagebox.EXCLAMATION);
			} else if(fundamoTransRef.isEmpty() && bankTransRef.isEmpty()){
				if (transactionType.getSelectedCount() > 0 && (fromDateValue == null || toDateValue == null)) {
					isValidSearchCriteria = false;
					Messagebox.show("กรุณาเลือกช่วงวันที่", "", Messagebox.OK, Messagebox.EXCLAMATION);
				}
				
				if (fromDateValue != null && toDateValue != null && transactionType.getSelectedCount() == 0) {
					transactionType.clearSelection();
					isValidSearchCriteria = false;
					Messagebox.show("กรุณาเลือกประเภทบริการ", "", Messagebox.OK, Messagebox.EXCLAMATION);
				}
			}
			
			if (isValidSearchCriteria) {
				ChannelManagementWS service = new ChannelManagementWS();
				// Call the KTB adaptor Inquiry service and fetch results
				// from database.
				responseList = service.getOTCTransactions(fromDateValue, toDateValue, transactionTypeValue, fundamoTransRef, bankTransRef);

				if (responseList == null || responseList.isEmpty()) {
					Messagebox.show("ไม่พบข้อมูลที่ค้นหา", "", Messagebox.OK, Messagebox.EXCLAMATION);
				}
			}

			ListModel<TransactionInquiryResponseDTO> ktbInquiryResultSOListModel = new SimpleListModel<TransactionInquiryResponseDTO>(responseList);
			this.setGridResultModel(ktbInquiryResultSOListModel);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Search", requestDate, "");
	}

	/**
	 * Set result as empty after reset button is pressed
	 */
	@Listen("onClick = #btReset")
	public void reset() {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Reset", ""));
		checkSessionTimeOut("Reset " + pageName);
		try {
			fromDate.setConstraint(new SimpleDateConstraint(""));
			toDate.setConstraint(new SimpleDateConstraint(""));
			fromDate.setValue(null);
			toDate.setValue(null);
			fundamoTransactionRef.setValue("");
			bankTransactionRef.setValue("");
			if (transactionType.getItemCount() > 0) {
				transactionType.clearSelection();
			}
			//this.setDefaultSearchDateTime();
			List<TransactionInquiryResponseDTO> ktbInquiryResultSOList = new ArrayList<TransactionInquiryResponseDTO>();
			ListModel<TransactionInquiryResponseDTO> ktbInquiryResultSOListModel = new SimpleListModel<TransactionInquiryResponseDTO>(ktbInquiryResultSOList);
			setGridResultModel(ktbInquiryResultSOListModel);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Reset", ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Reset", requestDate, ex);
		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Reset", requestDate, "");
	}

	@Listen("onBlur = #fromDate")
	public void clickDbFromDate() {
		log.info("Click Reset " + pageName);
		try {
			this.setDateConstraint();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		} finally {

		}
	}

	@Listen("onBlur = #toDate")
	public void clickDbToDate() {
		log.info("Click Reset " + pageName);
		try {
			this.setDateConstraint();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		} finally {

		}
	}

	@Override
	protected void setupPageFromSession() {
		// TODO Auto-generated method stub
	}
}
