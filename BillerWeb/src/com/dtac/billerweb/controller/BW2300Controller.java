package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.OutboundGatewayParam;
import mfs.biller.persistence.bean.OutboundId;

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
import com.dtac.billerweb.form.BW2300Form;
import com.dtac.billerweb.listmodel.BW2300ListModel;
import com.dtac.billerweb.listmodel.selectbox.OutboundIdListModel;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;

public class BW2300Controller extends SearchPageController {
	public BW2300Controller() {
		super("Outbound Gateway","2300");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW2300Controller.class);
	private static String SESS_OUTBOUND_GATEWAY_FROM = "SESS_OUTBOUND_GATEWAY_FROM";
	@Wire
	private Listbox lbOutboundId;
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
		OutboundIdListModel outboundIdListModel = null;
		try {
			/* ## Check Permission## */
			chkSearchPermission(btSearch);
			chkInsertPermission(btCreate);

			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			outboundIdListModel = selectBoxService.getOutboundIdListModel();
			outboundIdListModel.addItemAll();
			lbOutboundId.setModel(outboundIdListModel);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			selectBoxService = null;
		}
	}

	@Override
	protected void setupPageFromSession() {
		try {
			BW2300Form bw2300Form = (BW2300Form) getSession(SESS_OUTBOUND_GATEWAY_FROM);

			if (bw2300Form != null && bw2300Form.getOutboundId() != null&& bw2300Form.getStatus() != null) {
				try {
					lbOutboundId.setSelectedIndex(lbOutboundId.getIndexOfItem(bw2300Form.getOutboundId()));
					lbStatus.setSelectedIndex(lbStatus.getIndexOfItem(bw2300Form.getStatus()));
				} catch (ArrayIndexOutOfBoundsException aiobe) {

				}
				if (getAuthorization().isSearch()) {
					search();
				}
				removeSession(SESS_OUTBOUND_GATEWAY_FROM);
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
			BW2300Form bw2300Form = new BW2300Form();
			bw2300Form.setOutboundId(lbOutboundId.getSelectedItem());
			bw2300Form.setStatus(lbStatus.getSelectedItem());
			setSession(SESS_OUTBOUND_GATEWAY_FROM, bw2300Form);
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
		OutboundId outboundId = null;
		String status=null;
		try {
			OutboundGatewayParam condition = new OutboundGatewayParam();
			outboundId = lbOutboundId.getSelectedItem().getValue();
			status=lbStatus.getSelectedItem().getValue();
			condition.setGW_OUTB_ID(outboundId.getGW_OUTB_ID());
			condition.setACT_FLAG(status);
			BW2300ListModel results = new BW2300ListModel(paging.getActivePage() + 1, Integer.parseInt(AppConfiguration.getValue(AppConfiguration.PAGE_SIZE_KEY)), getUserInfo());
			results.setCondition(condition);
			setGridResultModel(results);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Outbound Id", AppUtil.toString(outboundId.getGW_OUTB_ID()));
			params.put("Status", status);
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		} finally {
			outboundId = null;
		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Search", requestDate, "");
	}

	@Listen("onClick = #btCreate")
	public void create() throws Exception {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Create", ""));
		checkSessionTimeOut("Create " + pageName);
		try {
			redirect(AppConfiguration.getValue(AppConfiguration.BW2310_REDIRECT_PATH));
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
			// redirect(AppConfiguration.getValue(AppConfiguration.BW2310_REDIRECT_PATH)
			// + "?oid=" + row.getValue());
			Dialog.openBw2310MedalDialog(AppConfiguration.getValue(AppConfiguration.BW2300_REDIRECT_PATH), Integer.parseInt(row.getValue().toString()));
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
