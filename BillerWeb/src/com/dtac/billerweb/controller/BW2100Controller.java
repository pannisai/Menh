package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BillerCategory;
import mfs.biller.persistence.bean.DropdownlistBillMSRT;
import mfs.biller.persistence.bean.DropdownlistBillservice;
import mfs.biller.persistence.bean.getInboundGatewayResultParam;
import mfs.exception.NotFoundDataException;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.Dialog;
import com.dtac.billerweb.common.SearchPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW2100Form;
import com.dtac.billerweb.listmodel.InboundGatewayListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerCatalogListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;

public class BW2100Controller extends SearchPageController {
	private Logger log = Logger.getLogger(BW2100Controller.class);

	public BW2100Controller() {
		super("Inbound Gateway","2100");
		// TODO Auto-generated constructor stub
	}

	private static String SESS_INBOUND_GATEWAY_FROM = "SESS_INBOUND_GATEWAY_FROM";
	@Wire
	private Listbox lbFunction;
	@Wire
	private Listbox lbStatus;

	@Wire
	private Listbox lbBillerServiceCode;

	@Wire
	private Button btSearch;
	@Wire
	private Button btCreate;

	@Override
	protected void init(Component com) throws Exception {
		setupPage();

	}

	private void setupPage() {
		log.info("Set up Page");
		SelectBoxService selectBoxService = null;
		BillerCatalogListModel billerCatalogListModel = null;
		try {
			/* ## Check Permission## */
			chkSearchPermission(btSearch);
			chkInsertPermission(btCreate);
			/* set selectbox list */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			this.setBillerServiceSelectBox(new BillerCategory(), new DropdownlistBillMSRT());

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
		}
	}

	@Override
	protected void setupPageFromSession() {
		try {
			BW2100Form bw2100Form = (BW2100Form) getSession(SESS_INBOUND_GATEWAY_FROM);

			if (bw2100Form != null && bw2100Form.getFunction() != null && bw2100Form.getStatus() != null && bw2100Form.getBillerServiceCode() != null) {
				try {
					lbFunction.setSelectedIndex(lbFunction.getIndexOfItem(bw2100Form.getFunction()));
					lbStatus.setSelectedIndex(lbStatus.getIndexOfItem(bw2100Form.getStatus()));
					lbBillerServiceCode.setSelectedIndex(lbBillerServiceCode.getIndexOfItem(bw2100Form.getBillerServiceCode()));
				} catch (ArrayIndexOutOfBoundsException aiobe) {

				}
				if (getAuthorization().isSearch()) {
					search();
				}
				removeSession(SESS_INBOUND_GATEWAY_FROM);
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
			BW2100Form bw2100Form = new BW2100Form();
			bw2100Form.setFunction(lbFunction.getSelectedItem());
			bw2100Form.setStatus(lbStatus.getSelectedItem());
			bw2100Form.setBillerServiceCode(lbBillerServiceCode.getSelectedItem());
			setSession(SESS_INBOUND_GATEWAY_FROM, bw2100Form);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "setCurrentFormSession", ex));
			showErrorMessage(ex);
		} finally {

		}
	}

	@Listen("onClick = #btSearch")
	public void clickSearch() {
		setCurrentFormSession();
		search();
	}

	private void search() {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Search", ""));
		checkSessionTimeOut("Search " + pageName);
		String function = null;
		String status = null;
		DropdownlistBillservice dropdownlistBillservice = null;
		getInboundGatewayResultParam condition = new getInboundGatewayResultParam();
		try {
			try {
				function = lbFunction.getSelectedItem().getValue();
				status = lbStatus.getSelectedItem().getValue();
				dropdownlistBillservice = lbBillerServiceCode.getSelectedItem().getValue();
				condition.setSRCE_SRVC_ID(function == null ? null : Integer.parseInt(function));
				condition.setACT_FLAG(status);
				condition.setBLLR_SRVC_CODE(dropdownlistBillservice.getBLLR_SRVC_CODE());
			} catch (NullPointerException npe) {

			}
			InboundGatewayListModel results = new InboundGatewayListModel(paging.getActivePage() + 1, Integer.parseInt(AppConfiguration.getValue(AppConfiguration.PAGE_SIZE_KEY)), getUserInfo());
			results.setCondition(condition);
			setGridResultModel(results);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Service Code", dropdownlistBillservice.getBLLR_SRVC_CODE());
			params.put("Function", function);
			params.put("Status", status);
			
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Search", requestDate, "");
	}

	private void setBillerServiceSelectBox(BillerCategory billerCategory, DropdownlistBillMSRT dropdownlistBillMSRT) {
		SelectBoxService selectBoxService = null;
		BillerServiceListModel billerServiceListModel = null;

		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* Set biller Service */
			billerServiceListModel = selectBoxService.getBillerServiceListModel(billerCategory, dropdownlistBillMSRT);
			billerServiceListModel.addItemAll();
			lbBillerServiceCode.setModel(billerServiceListModel);
		} catch (NotFoundDataException nfd) {

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerServiceListModel = null;
		}
	}

	@Listen("onClick = #btCreate")
	public void create() {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Create", ""));
		checkSessionTimeOut("Create " + pageName);
		try {
			redirect(AppConfiguration.getValue(AppConfiguration.BW2110_REDIRECT_PATH));
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
			String id = row.getValue();
			String[] splits = id.split("#");
			String inbn_SRVC_ID = "";
			String srce_SRVC_ID = "";
			String dest_SRVC_ID = "";
			if (splits.length == 3) {
				inbn_SRVC_ID = splits[0];
				srce_SRVC_ID = splits[1];
				dest_SRVC_ID = splits[2];
			}
			log.debug("Split length=" + splits.length);
			log.debug(inbn_SRVC_ID);
			log.debug(srce_SRVC_ID);
			log.debug(dest_SRVC_ID);

			Dialog.openBw2110MedalDialog(AppConfiguration.getValue(AppConfiguration.BW2100_REDIRECT_PATH), inbn_SRVC_ID, srce_SRVC_ID, dest_SRVC_ID);
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
