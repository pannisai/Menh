package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BillerMaster;
import mfs.biller.persistence.bean.BillerMasterParam;

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
import com.dtac.billerweb.data.BillerMasterSO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1200Form;
import com.dtac.billerweb.listmodel.selectbox.BillerMasterListModel;
import com.dtac.billerweb.service.BillerMasterService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;

public class BW1200Controller extends SearchPageController {
	private Logger log = Logger.getLogger(BW1200Controller.class);

	public BW1200Controller() {
		super("Biller Master","1200");
	}

	private static String SESS_BILLER_MASTER_FROM = "SESS_BILLER_MASTER_FORM";
	@Wire
	private Listbox lbBillerMaster;
	@Wire
	private Listbox lbStatus;
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
		BillerMasterListModel billerMasterListModel = null;
		try {
			/* ## Check Permission## */
			chkSearchPermission(btSearch);
			chkInsertPermission(btCreate);
			gdResult.setPaginal(paging);
			/* set selectbox id list */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			billerMasterListModel = selectBoxService.getBillerCodeListModel();
			billerMasterListModel.addItemAll();
			lbBillerMaster.setModel(billerMasterListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerMasterListModel = null;
		}
	}

	@Override
	protected void setupPageFromSession() {
		try {
			BW1200Form bw1200Form = (BW1200Form) getSession(SESS_BILLER_MASTER_FROM);

			if (bw1200Form != null && bw1200Form.getBillerMaster() != null && bw1200Form.getStatus() != null) {
				try {
					lbBillerMaster.setSelectedIndex(lbBillerMaster.getIndexOfItem(bw1200Form.getBillerMaster()));
					lbStatus.setSelectedIndex(lbStatus.getIndexOfItem(bw1200Form.getStatus()));
				} catch (ArrayIndexOutOfBoundsException aiobe) {

				}
				if (getAuthorization().isSearch()) {
					search();
				}
				removeSession(SESS_BILLER_MASTER_FROM);
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
			BW1200Form bw1200Form = new BW1200Form();
			bw1200Form.setBillerMaster(lbBillerMaster.getSelectedItem());
			bw1200Form.setStatus(lbStatus.getSelectedItem());
			setSession(SESS_BILLER_MASTER_FROM, bw1200Form);
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
		BillerMasterService billerMasterService = null;
		List<BillerMasterSO> billerMasterSOs = null;
		ListModel<BillerMasterSO> billerMasterListModel = null;
		BillerMaster billerMaster = null;
		String status = "";
		try {
			billerMasterService = BillerwebServiceFactory.getBillerMasterService();
			BillerMasterParam criteria = new BillerMasterParam();

			try {
				billerMaster = (BillerMaster) lbBillerMaster.getSelectedItem().getValue();
				criteria.setBLLR_MSTR_ID(billerMaster.getBLLR_MSTR_ID());

				status = lbStatus.getSelectedItem().getValue().toString();
				criteria.setACT_FLAG(status);
			} catch (NullPointerException npe) {

			}
			billerMasterSOs = billerMasterService.searchBillerMaster(criteria, 0, 0, getUserInfo());
			billerMasterListModel = new SimpleListModel<BillerMasterSO>(billerMasterSOs);

			setGridResultModel(billerMasterListModel);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Master ID", AppUtil.toString(billerMaster.getBLLR_MSTR_ID()));
			params.put("Status", status);
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		} finally {
			billerMasterService = null;
			billerMasterSOs = null;
			billerMasterListModel = null;
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
			redirect(AppConfiguration.getValue(AppConfiguration.BW1210_REDIRECT_PATH));
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
		Row row = new Row();
		checkSessionTimeOut("Edit " + pageName);
		try {
			row = (Row) event.getOrigin().getTarget();
			log.info(getOperationLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue()));
			// redirect(AppConfiguration.getValue(AppConfiguration.BW1210_REDIRECT_PATH)
			// + "?oid=" + row.getValue());
			Dialog.openBw1210MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1200_REDIRECT_PATH), Integer.parseInt(row.getValue().toString()));
			
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
