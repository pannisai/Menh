package com.dtac.billerweb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BillerMaster;
import mfs.biller.persistence.bean.DropdownlistBillservice;
import mfs.biller.persistence.bean.GWMasterTransParam;
import mfs.biller.util.DateTimeUtil;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.SimpleDateConstraint;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.SearchPageController;
import com.dtac.billerweb.data.BillerInquiryDO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.listmodel.BillerInquiryListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerMasterListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.util.BillerInquiryConfig;

public class BillerInquiryController extends SearchPageController {
	private Logger log = Logger.getLogger(BillerInquiryController.class);

	@Wire
	private Datebox dbPaymentDateFrom;

	@Wire
	private Datebox dbPaymentDateTo;

	@Wire
	private Listbox lbBillerCode;

	@Wire
	private Listbox lbBillerService;

	// @Wire
	// private Grid gdResult;
	// @Wire
	// private Paging paging;

	public final String testBillerColumn;
	public final String testBillerValue;

	public BillerInquiryController() {
		super("Biller Inquiry","");
		testBillerColumn = BillerInquiryConfig.DTAC_DTN_COLUMN_KEY;
		testBillerValue = BillerInquiryConfig.DTAC_DTN_VALUE_KEY;
	}

	@Override
	protected void init(Component com) throws Exception {
		// TODO Auto-generated method stub
		setupPage();
		gridRender();
		generateGridColumn();
	}

	@Override
	protected void setupPageFromSession() {
		// TODO Auto-generated method stub

	}

	private void setupPage() {
		SelectBoxService selectBoxService = null;
		BillerMasterListModel billerMasterListModel = null;
		BillerServiceListModel billerServiceListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			billerMasterListModel = selectBoxService.getBillerCodeListModel();
			billerMasterListModel.addItemAll();
			lbBillerCode.setModel(billerMasterListModel);
			billerServiceListModel = selectBoxService.getBillerServiceListModel();
			billerServiceListModel.addItemAll();
			lbBillerService.setModel(billerServiceListModel);
			String dateStr = DateTimeUtil.getDateTime(DateTimeUtil.minusDate(DateTimeUtil.getCurrent(), 5), "yyyyMMdd");
			SimpleDateConstraint sd = new SimpleDateConstraint("after " + dateStr + ",no future");
			dbPaymentDateFrom.setConstraint(sd);
			dbPaymentDateTo.setConstraint(sd);

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerMasterListModel = null;
			billerServiceListModel = null;
		}
	}

	private void gridRender() {
		try {
			gdResult.setRowRenderer(new RowRenderer<Object>() {
				public void render(Row row, Object data, int index) throws Exception {
					if (data == null)
						return;
					Columns columns = row.getGrid().getColumns();
					if (columns.getChildren() == null)
						return;

					createRowData(row, (BillerInquiryDO) data, index);

				}
			});
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			throw new RuntimeException(ex);
		} finally {

		}
	}

	private void generateGridColumn() {
		try {
			Columns columns = gdResult.getColumns();
			if (columns.getChildren() == null)
				return;
			columns.detach();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("columns", createColumnStringList(testBillerColumn));
			gdResult.appendChild(Executions.createComponents("/page/billerinquiry/billerInquiryColumns.zul", gdResult, params));
			columns = gdResult.getColumns();
			if (!AppUtil.isEmpty(columns.getChildren())) {

				Column column = (Column) columns.getChildren().get(0);
				column.setWidth("50px");
			}
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			throw new RuntimeException(ex);
		} finally {

		}
	}

	private List<String> createColumnStringList(String billerColumnKey) {
		String columnStr = null;
		String[] columns = null;
		List<String> result = new ArrayList<String>();
		try {
			columnStr = BillerInquiryConfig.getValue(billerColumnKey);
			if (AppUtil.isEmpty(columnStr)) {
				return new ArrayList<String>();
			}

			columns = columnStr.split("\\|");
			for (String column : columns) {
				result.add(AppUtil.trim(column));
			}
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			throw new RuntimeException(ex);
		} finally {
			columnStr = null;
			columns = null;
		}

		return result;
	}

	private List<String> createValueStringList(String billerValueKey) {
		return createColumnStringList(billerValueKey);
	}

	private void createRowData(Row row, BillerInquiryDO billerInquiryForm, int index) {
		try {
			List<String> columns = createColumnStringList(testBillerColumn);
			List<String> values = createValueStringList(testBillerValue);
			if (AppUtil.isEmpty(columns) || AppUtil.isEmpty(values)) {
				return;
			}
			if (columns.size() != values.size()) {
				return;
			}
			for (int i = 0; i < values.size(); i++) {

				String varName = values.get(i);
				log.debug("varName::" + varName);
				if (varName.equalsIgnoreCase("NO.")) {
					log.debug("Set NO." + index + 1);
					row.appendChild(new Label((index + 1) + (paging.getActivePage() * paging.getPageSize()) + ""));
				} else if (varName.equalsIgnoreCase("TRNS_ID")) {
					log.debug("Set TRNS_ID" + i);
					row.appendChild(new Label(billerInquiryForm.getTransId()));
				} else if (varName.equalsIgnoreCase("TRNS_DTTM")) {
					log.debug("Set TRNS_DTTM." + i);
					try {
						row.appendChild(new Label(AppUtil.toString(billerInquiryForm.getTransDate())));
					} catch (Exception npe) {

					}
				} else if (varName.equalsIgnoreCase("TRNS_REF1")) {
					log.debug("Set TRNS_REF1" + i);
					row.appendChild(new Label(billerInquiryForm.getRef1()));
				} else if (varName.equalsIgnoreCase("TRNS_REF2")) {
					log.debug("Set TRNS_REF2" + i);
					row.appendChild(new Label(billerInquiryForm.getRef2()));

				} else if (varName.equalsIgnoreCase("TRNS_REF3")) {
					log.debug("Set TRNS_REF3" + i);
					row.appendChild(new Label(billerInquiryForm.getRef3()));
				} else if (varName.equalsIgnoreCase("TRNS_REF4")) {
					log.debug("Set TRNS_REF4" + i);
					row.appendChild(new Label(billerInquiryForm.getRef4()));

				} else if (varName.equalsIgnoreCase("TRNS_REF5")) {
					log.debug("Set TRNS_REF5" + i);
					row.appendChild(new Label(billerInquiryForm.getRef5()));
				} else if (varName.equalsIgnoreCase("TRNS_PAID_AMNT")) {
					log.debug("Set TRNS_PAID_AMNT" + i);
					try {
						row.appendChild(new Label(AppUtil.toString(billerInquiryForm.getPaidAmount())));
					} catch (Exception ex) {
						log.info(ex.getMessage());
					}
				} else if (varName.equalsIgnoreCase("TRNS_CUST_NAME_TH")) {
					log.debug("Set TRNS_CUST_NAME_TH" + i);
					row.appendChild(new Label(billerInquiryForm.getCustNameTH()));
				} else if (varName.equalsIgnoreCase("TRNS_CRNT_BLNC")) {
					log.debug("Set TRNS_CRNT_BLNC" + i);
					row.appendChild(new Label(AppUtil.toString(billerInquiryForm.getCRNCBLNC())));
				} else if (varName.equalsIgnoreCase("TRNS_EXTR_AMNT")) {
					log.debug("Set TRNS_EXTR_AMNT" + i);
					row.appendChild(new Label(billerInquiryForm.getExtraAmount()));
				} else if (varName.equalsIgnoreCase("TRNS_SRVC_CODE")) {
					log.debug("Set TRNS_SRVC_CODE" + i);
					row.appendChild(new Label(billerInquiryForm.getServiceCode()));
				} else {
					row.appendChild(new Label(""));

				}
			}
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			throw new RuntimeException(ex);
		} finally {
		}
	}

	@Listen("onClick = #btSearch")
	public void search() throws Exception {
		try {

			Date fromDate = dbPaymentDateFrom.getValue();
			Date toDate = dbPaymentDateTo.getValue();
			BillerMaster billercode = new BillerMaster();
			DropdownlistBillservice billerService = new DropdownlistBillservice();

			billercode = lbBillerCode.getSelectedItem().getValue();
			billerService = lbBillerService.getSelectedItem().getValue();

			GWMasterTransParam condition = new GWMasterTransParam();
			condition.setFROM_DTTM(fromDate);
			condition.setTO_DTTM(toDate);
			String billMstrId = "";
			if (billercode.getBLLR_MSTR_ID() != null) {
				billMstrId = billercode.getBLLR_MSTR_ID() + "";
			}

			// condition.setTRNS_DEST_CODE(billMstrId);
			String transServiceId = "";
			if (billerService.getBLLR_SRVC_ID() != null) {
				transServiceId = billerService.getBLLR_SRVC_ID() + "";
			}
			condition.setTRNS_ID(transServiceId);

			BillerInquiryListModel results = new BillerInquiryListModel(1, Integer.parseInt(AppConfiguration.getValue(AppConfiguration.PAGE_SIZE_KEY)), getUserInfo());
			results.setCondition(condition);
			setGridResultModel(results);
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			showErrorMessage(ex);
		} finally {

		}
	}

	@Listen("onClick = #btReset")
	public void onReset() {
		log.info("Reset");
		dbPaymentDateFrom.setValue(null);
		dbPaymentDateTo.setValue(null);
		lbBillerCode.setSelectedIndex(0);
		lbBillerService.setSelectedIndex(0);
	}

	@Listen("onChange=#dbPaymentDateFrom")
	public void onChangePaymentDate() {
		Date date = dbPaymentDateFrom.getValue();
		log.debug("Date:" + date.toString());

		String dateStr;
		try {
			dateStr = DateTimeUtil.getDateTime(date, "yyyyMMdd");
			SimpleDateConstraint sd = new SimpleDateConstraint("after " + dateStr + ",no future");
			dbPaymentDateTo.setConstraint(sd);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		}

	}

}
