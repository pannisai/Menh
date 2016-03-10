package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.ejb.interfaces.BankChannelRemote;
import mfs.biller.persistence.bean.BankChannelBean;
import mfs.biller.persistence.bean.BankMasterBean;

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
import com.dtac.billerweb.data.BankChannelSO;
import com.dtac.billerweb.data.BankMasterSO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW4100Form;
import com.dtac.billerweb.form.BW4200Form;
import com.dtac.billerweb.listmodel.selectbox.BankChannelListModel;
import com.dtac.billerweb.listmodel.selectbox.BankCodeListModel;
import com.dtac.billerweb.service.BankChannelService;
import com.dtac.billerweb.service.BankMasterService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;

public class BW4200Controller extends SearchPageController {

	public BW4200Controller() {
		super("Bank Channel","4200");
		// TODO Auto-generated constructor stub
	}
	
	private Logger log = Logger.getLogger(BW4200Controller.class);
	private static String SESS_BANK_CHANNEL_FORM = "SESS_BANK_CHANNEL_FORM";	

	@Wire
	private Listbox lbBankChannel;
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

	private void setupPage() throws BillerWebSessionTimeOutException {
		log.info("Set up Page");
		SelectBoxService selectBoxService = null;
		BankChannelListModel<BankChannelBean> bankChannelListModel = null;
		try {
			/* ## Check Permission## */
			chkSearchPermission(btSearch);
			chkInsertPermission(btCreate);

			gdResult.setPaginal(paging);
			/* set Bank Code Dropdown list */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			bankChannelListModel = selectBoxService.getBankChannelListModel();
			bankChannelListModel.addItemAll();
			lbBankChannel.setModel(bankChannelListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			bankChannelListModel = null;
		}
	}

	@Override
	protected void setupPageFromSession() {
		// TODO Auto-generated method stub
		try {
			BW4200Form bw4200 = (BW4200Form) getSession(SESS_BANK_CHANNEL_FORM);

			if (bw4200 != null && bw4200.getBankChannel() != null && bw4200.getStatus() != null) {
				log.debug("InSetFormSession");
				try {
					lbBankChannel.setSelectedIndex(lbBankChannel.getIndexOfItem(bw4200.getBankChannel()));
					lbStatus.setSelectedIndex(lbStatus.getIndexOfItem(bw4200.getStatus()));
				} catch (ArrayIndexOutOfBoundsException aiobe) {

				}
				if (getAuthorization().isSearch()) {
					search();
				}
				removeSession(SESS_BANK_CHANNEL_FORM);
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
			BW4200Form bw4200Form = new BW4200Form();
			bw4200Form.setBankChannel(lbBankChannel.getSelectedItem());
			bw4200Form.setStatus(lbStatus.getSelectedItem());
			setSession(SESS_BANK_CHANNEL_FORM, bw4200Form);
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
		BankChannelService bankChannelService = null;
		List<BankChannelSO> bankChannelSOs = null;
		ListModel<BankChannelSO> bankChannelSOListModel = null;
		BankChannelBean bankChannelBean = null;
		String status = "";
		try {

			bankChannelService = BillerwebServiceFactory.getBankChannelService();
			BankChannelBean criteria = new BankChannelBean();

			try {
				bankChannelBean = lbBankChannel.getSelectedItem().getValue();
				criteria.setBANK_CHNL_CODE(bankChannelBean.getBANK_CHNL_CODE());
				status = lbStatus.getSelectedItem().getValue().toString();
				criteria.setACT_FLAG(status);
			} catch (NullPointerException npe) {

			}
			bankChannelSOs = bankChannelService.searchBankChannel(criteria, 0, 0, getUserInfo());
			bankChannelSOListModel = new SimpleListModel<BankChannelSO>(bankChannelSOs);

			setGridResultModel(bankChannelSOListModel);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Bank Channel", bankChannelBean.getBANK_CHNL_CODE());
			params.put("Status", status);
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		} finally {
			bankChannelService = null;
			bankChannelSOs = null;
			bankChannelSOListModel = null;
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
			redirect(AppConfiguration.getValue(AppConfiguration.BW4210_REDIRECT_PATH));
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
			Dialog.openBw4210MedalDialog(AppConfiguration.getValue(AppConfiguration.BW4200_REDIRECT_PATH), row.getValue().toString());
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
