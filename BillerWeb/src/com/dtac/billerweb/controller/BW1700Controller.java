package com.dtac.billerweb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BillerFee;
import mfs.biller.persistence.bean.BillerFeeParam;
import mfs.biller.persistence.bean.DropdownlistBillservice;
import mfs.biller.util.DateTimeUtil;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.SimpleListModel;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.Dialog;
import com.dtac.billerweb.common.SearchPageController;
import com.dtac.billerweb.data.BillerFeeSO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1700Form;
import com.dtac.billerweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.billerweb.service.BillerFeeService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

@SuppressWarnings("serial")
public class BW1700Controller extends SearchPageController {
	public BW1700Controller() {
		super("Service Provider Fee","1700");
	}

	private static String SESS_SERVICE_PROVIDER_FEE_FROM = "SESS_SERVICE_PROVIDER_FEE_FROM";
	private final ListModel<BillerFeeSO> emptyListModel = new SimpleListModel<BillerFeeSO>(new ArrayList<BillerFeeSO>());
	private Logger log = Logger.getLogger(BW1700Controller.class);

	@Wire
	private Datebox dbToDate;
	@Wire
	private Datebox dbFromDate;
	@Wire
	private Listbox lbBillerService;
	@Wire
	private Button btSearch;
	@Wire
	private Button btCreate;

	@Override
	protected void init(Component com) throws Exception {
		setupPage();
	}

	@SuppressWarnings("rawtypes")
	private void setupPage() throws BillerWebSessionTimeOutException {
		log.info("Set up Page");
		
		SelectBoxService selectBoxService = null;
		BillerServiceListModel billerServiceListModel = null;
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
			/* ## Set default date time */
			setDefaultSearchDateTime();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerServiceListModel = null;
		}
	}
	
	private void setDefaultSearchDateTime() {
		try {
			dbFromDate.setValue(DateTimeUtil.minusDate(DateTimeUtil.getCurrent(), 1));
			dbToDate.setValue(DateTimeUtil.getCurrent());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
		}
	}

	protected void setupPageFromSession() {
		try {
			BW1700Form bw1700 = (BW1700Form) getSession(SESS_SERVICE_PROVIDER_FEE_FROM);

			if (bw1700 != null && bw1700.getBillerService() != null && bw1700.getFromDate() != null && bw1700.getToDate() != null) {
				log.debug("InSetFormSession");
				try {
					dbFromDate.setValue(dbFromDate.getValue());
					dbToDate.setValue(dbToDate.getValue());					
					lbBillerService.setSelectedIndex(lbBillerService.getIndexOfItem(bw1700.getBillerService()));					
				} catch (ArrayIndexOutOfBoundsException aiobe) {

				}
				if (getAuthorization().isSearch()) {
					search();
				}
				removeSession(SESS_SERVICE_PROVIDER_FEE_FROM);
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
			BW1700Form bw1700Form = new BW1700Form();
			bw1700Form.setFromDate(dbFromDate.getValue());
			bw1700Form.setFromDate(dbFromDate.getValue());
			bw1700Form.setBillerService(lbBillerService.getSelectedItem());
			setSession(SESS_SERVICE_PROVIDER_FEE_FROM, bw1700Form);
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
		BillerFeeService billerFeeService = null;
		List<BillerFeeSO> billerFeeSO = null;
		ListModel<BillerFeeSO> billerFeeListModel = null;
		DropdownlistBillservice dropdownlistBillservice=null;
		billerFeeService = BillerwebServiceFactory.getBillerFeeService();
		BillerFeeParam criteria = new BillerFeeParam();
		try {
			// Validate
			if ((dbFromDate.getValue() != null) & (dbToDate.getValue() != null)) {
				Date dtFrom = dbFromDate.getValue();
				Date dtTo = dbToDate.getValue();
				Validation.validateEndDateTime(dbToDate, dtFrom, dtTo, AppMessage.getMessage(AppMessage.MSG_TODATE_MUST_AFTER_FROMDATE, "To Date must equal or after From Date"));
				
				criteria.setFROM_DTTM(dtFrom);
				criteria.setTO_DTTM(dtTo);
			}			
			try {
				dropdownlistBillservice = lbBillerService.getSelectedItem().getValue();
				criteria.setBLLR_SRVC_ID(dropdownlistBillservice.getBLLR_SRVC_ID().toString());
			} catch (NullPointerException npe) {
			}
			
			billerFeeSO = billerFeeService.searchBillerFee(criteria, 0, 0, getUserInfo());
			billerFeeListModel = new SimpleListModel<BillerFeeSO>(billerFeeSO);
			setGridResultModel(billerFeeListModel);
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);	
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Service", dropdownlistBillservice.getBLLR_SRVC_ID().toString());
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		} finally {
			billerFeeService = null;
			billerFeeSO = null;
			billerFeeListModel = null;
			criteria = null;
		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Search", requestDate, "");
	}

	@Listen("onClick = #btReset")
	public void onReset() {
		log.info(getOperationLogMessage(pageName, "Reset", ""));
		dbToDate.setValue(null);
		dbFromDate.setValue(null);
		lbBillerService.setSelectedIndex(0);
		/* ## Set default date time */
		setDefaultSearchDateTime();
		clearGrid();
		log.info(getStopLogMessage(pageName));
	}
	
	@Listen("onClick = #btCreate")
	public void create() {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Create", ""));
		checkSessionTimeOut("Create " + pageName);
		try {
			redirect(AppConfiguration.getValue(AppConfiguration.BW1710_REDIRECT_PATH));
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
			row = (Row) event.getOrigin().getTarget().getParent().getParent();
			log.info(getOperationLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue()));
			Dialog.openBw1710MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1700_REDIRECT_PATH), Integer.parseInt(row.getValue().toString()));
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onDelete = #gdResult")
	public void delete(ForwardEvent event) {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Delete " + pageName);

		final Row row = (Row) event.getOrigin().getTarget().getParent().getParent();	
		EventListener deleteConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerFeeService billerFeeService;
					BillerFee billerFee;
					try {						
						if(AppUtil.isNotNull(row)){
							log.info(getOperationLogMessage(pageName, "Delete", "DELETE ID::" + row.getValue()));
							billerFeeService = BillerwebServiceFactory.getBillerFeeService();
							billerFee = billerFeeService.getByID(Integer.parseInt(row.getValue().toString()), getUserInfo());
							if(AppUtil.isNotNull(billerFee)){
								billerFee.setACT_FLAG("I");
								billerFeeService.update(billerFee, getUserInfo());
							}
						}
						
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("delete OK");
									refreshResultGrid();
								}
							}
						};
						showDeleteSuccessMessage(okListener);
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Delete", "DELETE ID::" + row.getValue(), ex));
						showDeleteFailMessage();
						setTxErrLogMessage(pageCode + "#Delete", requestDate, ex);
					} finally {
						billerFeeService = null;
						billerFee = null;
					}
				}
			}
		};
		showDeleteConfirmMessage(deleteConfirmListener);
		setTxLogMessage(pageCode + "#Delete", requestDate, "");
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
	
	private void clearGrid(){
		try {
			setGridResultModel(emptyListModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
