package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BillerBarcode;
import mfs.biller.persistence.bean.BillerBarcodeParam;
import mfs.biller.persistence.bean.DropdownlistBillservice;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.SimpleListModel;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.Dialog;
import com.dtac.billerweb.common.SearchPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1500Form;
import com.dtac.billerweb.listmodel.selectbox.BillerBarcodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.billerweb.service.BillerBarcodeService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;

public class BW1500Controller extends SearchPageController {

	public BW1500Controller() {
		super("Biller Barcode","1500");
	}

	private static String SESS_BILLER_BARCODE_FORM = "SESS_BILLER_BARCODE_FORM";

	private Logger log = Logger.getLogger(BW1500Controller.class);
	@Wire
	private Listbox lbBillerService;
	@Wire
	private Listbox lbBillerBarcode;
	@Wire
	private Listbox lbStatus;
	@Wire
	private Button btSearch;
	@Wire
	private Button btCreate;

	@Override
	protected void init(Component com) throws Exception {
		setupPage();
//		log.debug("ManageName:"+System.getProperty("weblogic.Name"));
	}

	private void setupPage() {
		log.info("Set up Page");
		SelectBoxService selectBoxService = null;
		BillerBarcodeService billerBarcodeService = null;
		BillerServiceListModel billerServiceListModel = null;
		BillerBarcodeListModel billerBarcodeListModel = null;		
		try {
			/* ## Check Permission## */
			chkSearchPermission(btSearch);
			chkInsertPermission(btCreate);
			gdResult.setPaginal(paging);
			/* set selectbox  list */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			billerServiceListModel=selectBoxService.getBillerServiceListModel();
			billerServiceListModel.addItemAll();
			lbBillerService.setModel(billerServiceListModel);
			billerBarcodeService = BillerwebServiceFactory.getBillerBarcodeService();
			billerBarcodeListModel = billerBarcodeService.getBillerBarcodeListModel(getUserInfo());
			billerBarcodeListModel.addItemAll();
			lbBillerBarcode.setModel(billerBarcodeListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			billerBarcodeService = null;
			billerBarcodeListModel = null;
		}
	}

	protected void setupPageFromSession() {
		try {
			BW1500Form bw1500 = (BW1500Form) getSession(SESS_BILLER_BARCODE_FORM);

			if (bw1500 != null &&bw1500.getBillerService()!=null&& bw1500.getBillerBarcodeName() != null && bw1500.getStatus() != null) {
				log.debug("InSetFormSession");
				try {
					lbBillerService.setSelectedIndex(lbBillerService.getIndexOfItem(bw1500.getBillerService()));
					lbBillerBarcode.setSelectedIndex(lbBillerBarcode.getIndexOfItem(bw1500.getBillerBarcodeName()));
					lbStatus.setSelectedIndex(lbStatus.getIndexOfItem(bw1500.getStatus()));
				} catch (ArrayIndexOutOfBoundsException aiobe) {

				}
				if (getAuthorization().isSearch()) {
					search();
				}
				removeSession(SESS_BILLER_BARCODE_FORM);
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
			BW1500Form bw1500Form = new BW1500Form();
			bw1500Form.setBillerService(lbBillerService.getSelectedItem());
			bw1500Form.setBillerBarcodeName(lbBillerBarcode.getSelectedItem());
			bw1500Form.setStatus(lbStatus.getSelectedItem());
			setSession(SESS_BILLER_BARCODE_FORM, bw1500Form);
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
		BillerBarcodeService billerBarcodeService = null;
		List<BillerBarcode> billerBarcodeList = null;
		ListModel<BillerBarcode> billerBarcodeListModel = null;
		DropdownlistBillservice dropdownlistBillservice=null;
		BillerBarcode billerBarcode = null;
		String status = "";
		try {
			billerBarcodeService = BillerwebServiceFactory.getBillerBarcodeService();
			BillerBarcodeParam criteria = new BillerBarcodeParam();

			try {
				dropdownlistBillservice=lbBillerService.getSelectedItem().getValue();
				billerBarcode = (BillerBarcode) lbBillerBarcode.getSelectedItem().getValue();

				criteria.setBLLR_SRVC_ID(dropdownlistBillservice.getBLLR_SRVC_ID());
				
				if (billerBarcode.getBARC_ID() != null) {
					criteria.setBARC_ID(billerBarcode.getBARC_ID());
				}
				
				status = lbStatus.getSelectedItem().getValue().toString();
				criteria.setACT_FLAG(status);
			} catch (NullPointerException npe) {

			}
			billerBarcodeList = billerBarcodeService.searchBillerBarcode(criteria, 0, 0, getUserInfo());
			billerBarcodeListModel = new SimpleListModel<BillerBarcode>(billerBarcodeList);

			setGridResultModel(billerBarcodeListModel);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Service", AppUtil.toString(billerBarcode.getBLLR_SRVC_ID()));
			params.put("Biller Barcode", AppUtil.toString(billerBarcode.getBARC_ID()));
			params.put("Status", status);
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		} finally {
			billerBarcodeService = null;
			billerBarcodeList = null;
			billerBarcodeListModel = null;
			status = null;
			log.info(getStopLogMessage(pageName));
		}
		setTxLogMessage(pageCode + "#Search", requestDate, "");
	}

	@Listen("onClick = #btCreate")
	public void create() {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Create", ""));
		checkSessionTimeOut("Create " + pageName);
		try {
			redirect(AppConfiguration.getValue(AppConfiguration.BW1510_REDIRECT_PATH));
		} catch (Exception ex) {
	
			log.error(getErrorLogMessage(pageName, "Create", ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Create", requestDate, ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
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
			// redirect(AppConfiguration.getValue(AppConfiguration.BW1510_REDIRECT_PATH)
			// + "?oid=" + row.getValue());
			Dialog.openBw1510MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1500_REDIRECT_PATH), row.getValue().toString());
		} catch (Exception ex) {
		
			log.error(getErrorLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue(), ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Edit", requestDate, ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
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
