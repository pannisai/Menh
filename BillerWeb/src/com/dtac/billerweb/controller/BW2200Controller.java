package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.GWService;
import mfs.biller.persistence.bean.OutboundId;
import mfs.biller.persistence.bean.ServiceGatewayResultParam;

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
import com.dtac.billerweb.form.BW2200Form;
import com.dtac.billerweb.listmodel.BW2200ListModel;
import com.dtac.billerweb.listmodel.selectbox.OutboundIdListModel;
import com.dtac.billerweb.listmodel.selectbox.ServiceGatewayListModel;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;

public class BW2200Controller extends SearchPageController {
	public BW2200Controller() {
		super("Service Gateway","2200");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW2200Controller.class);
	private static String SESS_SERVICE_GATEWAY_FROM = "SESS_SERVICE_GATEWAY_FROM";
	@Wire
	private Listbox lbBillerService;
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
		ServiceGatewayListModel<GWService> serviceGatewayListModel = null;
		OutboundIdListModel outboundIdListModel = null;
		try {
			/* ## Check Permission## */
			chkSearchPermission(btSearch);
			chkInsertPermission(btCreate);
			/* set selectbox id list */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			serviceGatewayListModel = selectBoxService.getServiceGatewayListModel();
			serviceGatewayListModel.addItemAll();
			lbBillerService.setModel(serviceGatewayListModel);
			/* set outbound id list */
			outboundIdListModel = selectBoxService.getOutboundIdListModel();
			outboundIdListModel.addItemAll();
			lbOutboundId.setModel(outboundIdListModel);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			selectBoxService = null;
			serviceGatewayListModel = null;
			outboundIdListModel = null;
		}
	}

	@Override
	protected void setupPageFromSession() {
		try {
			BW2200Form bw2200Form = (BW2200Form) getSession(SESS_SERVICE_GATEWAY_FROM);

			if (bw2200Form != null && bw2200Form.getBillerService() != null && bw2200Form.getOutboundId() != null&& bw2200Form.getStatus() != null) {
				try {
					lbBillerService.setSelectedIndex(lbBillerService.getIndexOfItem(bw2200Form.getBillerService()));
					lbOutboundId.setSelectedIndex(lbOutboundId.getIndexOfItem(bw2200Form.getOutboundId()));
					lbStatus.setSelectedIndex(lbStatus.getIndexOfItem(bw2200Form.getStatus()));
				} catch (ArrayIndexOutOfBoundsException aiobe) {

				}
				if (getAuthorization().isSearch()) {
					search();
				}
				removeSession(SESS_SERVICE_GATEWAY_FROM);
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
			BW2200Form bw2200Form = new BW2200Form();
			bw2200Form.setBillerService(lbBillerService.getSelectedItem());
			bw2200Form.setOutboundId(lbOutboundId.getSelectedItem());
			bw2200Form.setStatus(lbStatus.getSelectedItem());
			setSession(SESS_SERVICE_GATEWAY_FROM, bw2200Form);
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
		BW2200ListModel bw2200ListModel = null;
		GWService gwService = null;
		OutboundId outboundId = null;
		String status=null;
		try {
			ServiceGatewayResultParam criteria = new ServiceGatewayResultParam();

			try {
				gwService = lbBillerService.getSelectedItem().getValue();
				outboundId = lbOutboundId.getSelectedItem().getValue();
				status=lbStatus.getSelectedItem().getValue();
				criteria.setGW_SRVC_ID(gwService.getGW_SRVC_ID());
				criteria.setGW_OUTB_ID(outboundId.getGW_OUTB_ID());
				criteria.setACT_FLAG(status);
			} catch (NullPointerException npe) {

			}
			bw2200ListModel = new BW2200ListModel(paging.getActivePage() + 1, Integer.parseInt(AppConfiguration.getValue(AppConfiguration.PAGE_SIZE_KEY)), getUserInfo());
			bw2200ListModel.setCriteria(criteria);
			setGridResultModel(bw2200ListModel);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Service", AppUtil.toString(gwService.getGW_SRVC_ID()));
			params.put("Outbound Id", AppUtil.toString(outboundId.getGW_OUTB_ID()));
			params.put("Status", status);
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		} finally {
			bw2200ListModel = null;
			gwService = null;
			outboundId = null;
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
			redirect(AppConfiguration.getValue(AppConfiguration.BW2210_REDIRECT_PATH));
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
			// redirect(AppConfiguration.getValue(AppConfiguration.BW2210_REDIRECT_PATH)
			// + "?oid=" + row.getValue());
			Dialog.openBw2210MedalDialog(AppConfiguration.getValue(AppConfiguration.BW2200_REDIRECT_PATH), Integer.parseInt(row.getValue().toString()));
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
