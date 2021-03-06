package com.dtac.billerweb.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import mfs.biller.persistence.bean.BillerChannel;
import mfs.biller.persistence.bean.BillerFdmCode;
import mfs.biller.persistence.bean.BillerPymtCode;
import mfs.biller.persistence.bean.DropdownlistBillservice;
import mfs.biller.persistence.bean.ReportTransDetail;
import mfs.biller.persistence.bean.ReportTransParam;
import mfs.biller.util.DateTimeUtil;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.Dialog;
import com.dtac.billerweb.common.SearchPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.listmodel.ReportTransDetailListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerChannelListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerFdmCodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerPymtCodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.billerweb.service.ReportTransService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW3000Controller extends SearchPageController {

	public BW3000Controller() {
		super("Biller Report Transaction","3000");
	}

	private static String SESS_REPORT_TRANS_FORM = "SESS_REPORT_TRANS_FORM";
	private Logger log = Logger.getLogger(BW3000Controller.class);

	@Wire
	private Datebox dbFROM_DATE;
	@Wire
	private Datebox dbTO_DATE;
	@Wire
	private Timebox tbFROM_TIME;
	@Wire
	private Timebox tbTO_TIME;
	@Wire
	private Listbox lbBillerServiceCode;
	@Wire
	private Listbox lbBillerChannelCode;
	@Wire
	private Textbox txTRNS_ID;
	@Wire
	private Listbox lbTRNS_FUNC_CODE;
	@Wire
	private Listbox lbBillerFdmCode;
	@Wire
	private Listbox lbBillerPymtCode;
	@Wire
	private Button btSearch;
	@Wire
	private Button btReset;

	@Override
	protected void init(Component com) throws Exception {
		setupPage();
	}

	private void setupPage() {
		log.info("Set up Page");
		/* ## Check Permission## */
		chkSearchPermission(btSearch);
		SelectBoxService selectBoxService = null;
		BillerServiceListModel billerServiceListModel = null;
		BillerChannelListModel billerChannelListModel = null;
		ReportTransService reportTransService = null;
		BillerFdmCodeListModel billerFdmCodeListModel = null;
		BillerPymtCodeListModel billerPymtCodeListModel = null;
		try {
			gdResult.setPaginal(paging);

			/* ## Set Biller Service list ## */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			billerServiceListModel = selectBoxService.getBillerServiceListModel();
			billerServiceListModel.addItemAll();
			lbBillerServiceCode.setModel(billerServiceListModel);

			/* ## Set Biller Channel list ## */
			billerChannelListModel = selectBoxService.getBillerChannelListModel();
			billerChannelListModel.addItemAll();
			lbBillerChannelCode.setModel(billerChannelListModel);

			/* ## Set Transaction Status list ## */
			reportTransService = BillerwebServiceFactory.getReportTransService();
			billerFdmCodeListModel = reportTransService.getBillerFdmCodeListModel(getUserInfo());
			billerFdmCodeListModel.addItemAll();
			lbBillerFdmCode.setModel(billerFdmCodeListModel);

			/* ## Set Payment Status list ## */
			billerPymtCodeListModel = reportTransService.getBillerPymtCodeListModel(getUserInfo());
			billerPymtCodeListModel.addItemAll();
			lbBillerPymtCode.setModel(billerPymtCodeListModel);
			/* ## Set default date time */
			setDefaultSearchDateTime();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerServiceListModel = null;
			billerChannelListModel = null;
			reportTransService = null;
			billerFdmCodeListModel = null;
			billerPymtCodeListModel = null;
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
			dbFROM_DATE.setValue(formDate);
			dbTO_DATE.setValue(toDate);
			tbFROM_TIME.setValue(formDate);
			tbTO_TIME.setValue(toDate);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			formDate = null;
			toDate = null;
		}
	}

	protected void setupPageFromSession() {
		// Not Implement, because not redirect page to create or edit
	}

	private void setCurrentFormSession() {
		// Not Implement, because not redirect page to create or edit
	}

	@Listen("onClick = #btSearch")
	public void search() {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Search", ""));
		checkSessionTimeOut("Search " + "Report Tran");
		DropdownlistBillservice billerServiceCode = null;
		BillerChannel billerChannel = null;
		BillerFdmCode billerFdmCode = null;
		BillerPymtCode billerPymtCode = null;
		ReportTransParam condition = new ReportTransParam();
		try {

			// Validate
			if ((dbFROM_DATE.getValue() != null) && (tbFROM_TIME.getValue() != null) && (dbTO_DATE.getValue() != null) && (tbTO_TIME.getValue() != null)) {
				Date dtFrom = getDateTime(dbFROM_DATE.getValue(), tbFROM_TIME.getValue());
				Date dtTo = getDateTime(dbTO_DATE.getValue(), tbTO_TIME.getValue());
				Validation.validateEndDateTime(dbTO_DATE, dtFrom, dtTo, AppMessage.getMessage(AppMessage.MSG_TODATE_MUST_AFTER_FROMDATE, "To Date must equal or after From Date"));
			}

			try {
				billerServiceCode = lbBillerServiceCode.getSelectedItem().getValue();
				billerChannel = lbBillerChannelCode.getSelectedItem().getValue();
				billerFdmCode = lbBillerFdmCode.getSelectedItem().getValue();
				billerPymtCode = lbBillerPymtCode.getSelectedItem().getValue();

				if ((dbFROM_DATE.getValue() != null) && (tbFROM_TIME.getValue() != null) && (dbTO_DATE.getValue() != null) && (tbTO_TIME.getValue() != null)) {
					condition.setFROM_TRNS_DTTM(getDateTime(dbFROM_DATE.getValue(), tbFROM_TIME.getValue()));
					condition.setTO_TRNS_DTTM(getDateTime(dbTO_DATE.getValue(), tbTO_TIME.getValue()));
				}
				condition.setTRNS_SRVC_CODE(billerServiceCode.getBLLR_SRVC_CODE());
				if (billerChannel.getBLLR_CHNL_ID() != null) {
					condition.setTRNS_SRCE_CHNL_CODE(billerChannel.getBLLR_CHNL_NAME());// Requirement
																						// change
																						// from
																						// code
																						// to
																						// name
				}
				condition.setTRNS_ID(txTRNS_ID.getValue());
				condition.setTRNS_FUNC_CODE(lbTRNS_FUNC_CODE.getSelectedItem().getValue().toString());
				condition.setTRNS_STTS_CODE(billerFdmCode.getBLLR_MSGE_CODE());
				condition.setTRNS_PYMT_STTS_CODE(billerPymtCode.getBLLR_MSGE_CODE());

				log.info(getOperationLogMessage(pageName, "Search", "Param|" + condition.toString()));

			} catch (NullPointerException npe) {

			}
			ReportTransDetailListModel results = new ReportTransDetailListModel(paging.getActivePage() + 1, Integer.parseInt(AppConfiguration.getValue(AppConfiguration.PAGE_SIZE_KEY)), getUserInfo());
			results.setCondition(condition);
			setGridResultModel(results);

		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("FROM_TRNS_DTTM", AppUtil.toString(getDateTime(dbFROM_DATE.getValue(), tbFROM_TIME.getValue())));
			params.put("TO_TRNS_DTTM", AppUtil.toString(getDateTime(dbTO_DATE.getValue(), tbTO_TIME.getValue())));
			params.put("TRNS_SRVC_CODE", billerServiceCode.getBLLR_SRVC_CODE());
			params.put("TRNS_SRCE_CHNL_CODE", billerChannel.getBLLR_CHNL_CODE());
			params.put("TRNS_ID", txTRNS_ID.getValue());
			params.put("TRNS_FUNC_CODE", lbTRNS_FUNC_CODE.getSelectedItem().getValue().toString());
			params.put("TRNS_STTS_CODE", billerFdmCode.getBLLR_MSGE_CODE());
			params.put("TRNS_PYMT_STTS_CODE", billerPymtCode.getBLLR_MSGE_CODE());
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
			setDefaultSearchDateTime();

			if (lbBillerServiceCode.getItemCount() > 0) {
				lbBillerServiceCode.setSelectedIndex(0);
			}
			if (lbBillerChannelCode.getItemCount() > 0) {
				lbBillerChannelCode.setSelectedIndex(0);
			}
			if (lbBillerFdmCode.getItemCount() > 0) {
				lbBillerFdmCode.setSelectedIndex(0);
			}
			if (lbTRNS_FUNC_CODE.getItemCount() > 0) {
				lbTRNS_FUNC_CODE.setSelectedIndex(0);
			}
			if (lbBillerPymtCode.getItemCount() > 0) {
				lbBillerPymtCode.setSelectedIndex(0);
			}
			txTRNS_ID.setValue(null);

		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		} finally {

		}
	}

	private Date getDateTime(Date date, Date time) {
		try {
			if ((date == null) || (time == null)) {
				return null;
			}
			String d = DateTimeUtil.getDateTime(date, "yyyyMMdd");
			String t = DateTimeUtil.getDateTime(time, "HHmm");
			return parseToDate(d + t + "00", "yyyyMMddHHmmss");
		} catch (Exception e) {
			return null;
		}
	}

	private Date parseToDate(String strDate, String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.US);
		Date date = null;
		try {
			date = formatter.parse(strDate);
		} catch (Exception e) {
	
		}
		return date;
	}

	@Listen("onEdit = #gdResult")
	public void edit(ForwardEvent event) {
		Date requestDate = AppUtil.getCurrent();
		// checkSessionTimeOut("Edit " + pageName);
		Row row = new Row();
		ReportTransDetail reportTransDetail = null;
		try {
			row = (Row) event.getOrigin().getTarget();
			reportTransDetail = row.getValue();
			log.info(getOperationLogMessage(pageName, "View", "View ID::" + reportTransDetail.getTRNS_ID()));
			Dialog.openBw3030MedalDialog(AppConfiguration.getValue(AppConfiguration.BW3000_REDIRECT_PATH), reportTransDetail, ((row.getIndex() + 1) + (paging.getActivePage() * paging.getPageSize())));
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Edit", "View ID::" + reportTransDetail, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Edit", requestDate, ex);
		} finally {
		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Edit", requestDate, "");
	}
}
