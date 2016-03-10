package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BillerChannel;
import mfs.biller.persistence.bean.BillerChannelParam;

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
import com.dtac.billerweb.data.BillerChannelSO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1300Form;
import com.dtac.billerweb.listmodel.selectbox.BillerChannelListModel;
import com.dtac.billerweb.service.BillerChannelService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;

public class BW1300Controller extends SearchPageController {
	private Logger log = Logger.getLogger(BW1300Controller.class);

	public BW1300Controller() {
		super("Biller Channel","1300");
	}

	private static String SESS_BILLER_CHANNEL_FROM = "SESS_BILLER_CHANNEL_FROM";
	@Wire
	private Listbox lbBillerChannel;
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
		BillerChannelListModel billerChannelListModel = null;
		try {
			/* ## Check Permission## */
			chkSearchPermission(btSearch);
			chkInsertPermission(btCreate);
			gdResult.setPaginal(paging);
			/* set selectbox list */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			billerChannelListModel = selectBoxService.getBillerChannelListModel();
			billerChannelListModel.addItemAll();
			lbBillerChannel.setModel(billerChannelListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerChannelListModel = null;
		}
	}

	@Override
	protected void setupPageFromSession() {
		try {
			BW1300Form bw1300Form = (BW1300Form) getSession(SESS_BILLER_CHANNEL_FROM);

			if (bw1300Form != null && bw1300Form.getBillerChannel() != null && bw1300Form.getStatus() != null) {
				try {
					lbBillerChannel.setSelectedIndex(lbBillerChannel.getIndexOfItem(bw1300Form.getBillerChannel()));
					lbStatus.setSelectedIndex(lbStatus.getIndexOfItem(bw1300Form.getStatus()));
				} catch (ArrayIndexOutOfBoundsException aiobe) {

				}
				if (getAuthorization().isSearch()) {
					search();
				}
				removeSession(SESS_BILLER_CHANNEL_FROM);
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
			BW1300Form bw1300Form = new BW1300Form();
			bw1300Form.setBillerChannel(lbBillerChannel.getSelectedItem());
			bw1300Form.setStatus(lbStatus.getSelectedItem());
			setSession(SESS_BILLER_CHANNEL_FROM, bw1300Form);
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
		BillerChannelService billerChannelService = null;
		List<BillerChannelSO> billerChannelSOs = null;
		ListModel<BillerChannelSO> billerChannelListModel = null;
		BillerChannel billerChannel = null;
		String status = "";
		try {
			billerChannelService = BillerwebServiceFactory.getBillerChannelService();
			BillerChannelParam criteria = new BillerChannelParam();

			try {
				billerChannel = (BillerChannel) lbBillerChannel.getSelectedItem().getValue();
				criteria.setBLLR_CHNL_ID(billerChannel.getBLLR_CHNL_ID());

				status = lbStatus.getSelectedItem().getValue().toString();
				criteria.setACT_FLAG(status);
			} catch (NullPointerException npe) {

			}
			billerChannelSOs = billerChannelService.searchBillerChannel(criteria, 0, 0, getUserInfo());
			billerChannelListModel = new SimpleListModel<BillerChannelSO>(billerChannelSOs);

			setGridResultModel(billerChannelListModel);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Channel ID", AppUtil.toString(billerChannel.getBLLR_CHNL_ID()));
			params.put("Status", status);
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		} finally {
			billerChannelService = null;
			billerChannelSOs = null;
			billerChannelListModel = null;
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
			redirect(AppConfiguration.getValue(AppConfiguration.BW1310_REDIRECT_PATH));
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
			// redirect(AppConfiguration.getValue(AppConfiguration.BW1310_REDIRECT_PATH)
			// + "?oid=" + row.getValue());
			Dialog.openBw1310MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1300_REDIRECT_PATH), Integer.parseInt(row.getValue().toString()));
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
