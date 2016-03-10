package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BillerCategory;
import mfs.biller.persistence.bean.BillerCategoryParam;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.SimpleListModel;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.Authorization;
import com.dtac.billerweb.common.Dialog;
import com.dtac.billerweb.common.SearchPageController;
import com.dtac.billerweb.data.BillerCategorySO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1100Form;
import com.dtac.billerweb.listmodel.selectbox.BillerCatalogListModel;
import com.dtac.billerweb.service.BillerCategoryService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;

public class BW1100Controller extends SearchPageController {
	public BW1100Controller() {
		super("Biller Category","1100");
	}

	private static String SESS_BILLER_CATALOG_FROM = "SESS_BILLER_CATALOG_FORM";

	private Logger log = Logger.getLogger(BW1100Controller.class);

	@Wire
	private Listbox lbBillerCatalog;
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

	private void setupPage() throws BillerWebSessionTimeOutException {
		log.info("Set up Page");
		SelectBoxService selectBoxService = null;
		BillerCatalogListModel billerCatalogListModel = null;

		try {

			/* ## Check Permission## */
			chkSearchPermission(btSearch);
			chkInsertPermission(btCreate);

			gdResult.setPaginal(paging);
			/* set selectbox id list */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			billerCatalogListModel = selectBoxService.getBillerCatalogListModel();
			billerCatalogListModel.addItemAll();
			lbBillerCatalog.setModel(billerCatalogListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerCatalogListModel = null;
		}
	}

	protected void setupPageFromSession() {
		try {
			BW1100Form bw1100 = (BW1100Form) getSession(SESS_BILLER_CATALOG_FROM);

			if (bw1100 != null && bw1100.getBillerCatalogName() != null && bw1100.getStatus() != null) {
				log.debug("InSetFormSession");
				try {
					lbBillerCatalog.setSelectedIndex(lbBillerCatalog.getIndexOfItem(bw1100.getBillerCatalogName()));
					lbStatus.setSelectedIndex(lbStatus.getIndexOfItem(bw1100.getStatus()));
				} catch (ArrayIndexOutOfBoundsException aiobe) {

				}
				if (getAuthorization().isSearch()) {
					search();
				}
				removeSession(SESS_BILLER_CATALOG_FROM);
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
			BW1100Form bw1100Form = new BW1100Form();
			bw1100Form.setBillerCatalogName(lbBillerCatalog.getSelectedItem());
			bw1100Form.setStatus(lbStatus.getSelectedItem());
			setSession(SESS_BILLER_CATALOG_FROM, bw1100Form);
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
		BillerCategoryService billerCategoryService = null;
		List<BillerCategorySO> billerCategorySOs = null;
		ListModel<BillerCategorySO> billerCategoryListModel = null;
		BillerCategory billerCategory = null;
		String status = "";
		try {

			billerCategoryService = BillerwebServiceFactory.getBillerCategoryService();
			BillerCategoryParam criteria = new BillerCategoryParam();

			try {
				billerCategory = (BillerCategory) lbBillerCatalog.getSelectedItem().getValue();

				if (billerCategory.getBLLR_CATG_ID() != null) {
					criteria.setBLLR_CATG_ID(billerCategory.getBLLR_CATG_ID() + "");
				}
				status = lbStatus.getSelectedItem().getValue().toString();
				criteria.setACT_FLAG(status);
			} catch (NullPointerException npe) {

			}
			billerCategorySOs = billerCategoryService.searchBillerCategory(criteria, 0, 0, getUserInfo());
			billerCategoryListModel = new SimpleListModel<BillerCategorySO>(billerCategorySOs);

			setGridResultModel(billerCategoryListModel);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Catalog", AppUtil.toString(billerCategory.getBLLR_CATG_ID()));
			params.put("Status", status);
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		} finally {
			billerCategoryService = null;
			billerCategorySOs = null;
			billerCategoryListModel = null;
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
			redirect(AppConfiguration.getValue(AppConfiguration.BW1110_REDIRECT_PATH));
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
			// redirect(AppConfiguration.getValue(AppConfiguration.BW1110_REDIRECT_PATH)
			// + "?oid=" + row.getValue());
			Dialog.openBw1110MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1100_REDIRECT_PATH), Integer.parseInt(row.getValue().toString()));
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue(), ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Edit", requestDate, ex);
		} finally {
			// row= null;
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
