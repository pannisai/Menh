package com.dtac.billerweb.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BillerFee;
import mfs.biller.persistence.bean.BillerFeeMast;
import mfs.biller.persistence.bean.BillerFeeParam;
import mfs.biller.persistence.bean.DropdownlistBillservice;
import mfs.biller.util.DateTimeUtil;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.exception.DialogException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1710Form;
import com.dtac.billerweb.listmodel.selectbox.BillerFeeMastListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.billerweb.service.BillerFeeService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1710Controller extends EditPageController {
	public BW1710Controller() {
		super("Edit Service Provider Fee", "1710");
	}

	private Logger log = Logger.getLogger(BW1710Controller.class);
	private BW1710Form bw1710Form = new BW1710Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label title;
	@Wire
	private Label lServiceName;
	@Wire
	private Label lFeeType;
	@Wire
	private Listbox lbBillerService;
	@Wire
	private Listbox lbBllrFeeMastId;
	@Wire
	private Textbox txServiceFeeAmount;
	@Wire
	private Textbox txFundamoFeeAmount;
	@Wire
	private Textbox txFeeAmount;
	@Wire
	private Datebox dbEfftDate;
	@Wire
	private Button btSave;
	@Wire
	private Button btExpired;
	@Wire
	private Button btReset;
	@Wire
	private Button btCancel;

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub
		setupPage();
		if (oid == -1) {
			setupNewPage();
		} else {
			setupEditPage();
		}
	}

	@SuppressWarnings("rawtypes")
	private void setupPage() {
		SelectBoxService selectBoxService = null;
		BillerServiceListModel billerServiceListModel = null;
		BillerFeeMastListModel billerFeeMastListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Biller service list ## */
			billerServiceListModel = selectBoxService.getBillerServiceListModel();
			billerServiceListModel.addItemSelect();
			lbBillerService.setModel(billerServiceListModel);		
			/* ## Set Biller Fee Mast list ## */
			billerFeeMastListModel = selectBoxService.getBillerFeeMastListModel();
			billerFeeMastListModel.addItemSelect();
			lbBllrFeeMastId.setModel(billerFeeMastListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerServiceListModel = null;
		}
	}
	
	private void setupNewPage() {
		try {
			chkInsertPermission(btSave);
			title.setValue(AppMessage.getMessage(AppMessage.BW1710_NEW_TITLE));
			dbEfftDate.setValue(new Date());
			txServiceFeeAmount.setDisabled(true);
			btExpired.setVisible(false);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			i = (Include) Path.getComponent("/bw1710Dialog/bw1710DialogInclude");
			comps = i != null ? i.getFellows() : updatePage.getFellows();
			if (chkUpdatePermission(comps, btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW1710_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW1710_VIEW_TITLE));
			}
			loadFormData(oid);
			refreshForm();
			
			lbBillerService.setDisabled(true);
			txServiceFeeAmount.setDisabled(true);
			btExpired.setVisible(true);				
			if(DateTimeUtil.getCurrent().before(bw1710Form.getEfftDate())){
				btExpired.setDisabled(true);
			}else{
				btExpired.setDisabled(false);
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i = null;
			comps = null;
		}
	}

	private void loadFormData(Integer oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BillerFeeService billerFeeService = null;
		BillerFee billerFee = null;
		try {
			billerFeeService = BillerwebServiceFactory.getBillerFeeService();
			billerFee = billerFeeService.getByID(oid, getUserInfo());
			bw1710Form.setBllrFeeId(billerFee.getBLLR_FEE_ID());
			bw1710Form.setBllrSrvcId(billerFee.getBLLR_SRVC_ID());
			bw1710Form.setBllrFeeMastId(billerFee.getBLLR_FEE_MAST_ID());
			bw1710Form.setSrvcFee(calSrvcFee(billerFee.getBLLR_FEE_MAST_ID().toString(), billerFee.getFEE_AMOUNT(), billerFee.getCUST_FEE_AMOUNT()));
			bw1710Form.setFeeAmount(billerFee.getFEE_AMOUNT());
			bw1710Form.setCustFeeAmount(billerFee.getCUST_FEE_AMOUNT());
			bw1710Form.setEfftDate(billerFee.getEFFT_DATE());
			bw1710Form.setExprDate(billerFee.getEXPR_DATE());
			bw1710Form.setActFlag(billerFee.getACT_FLAG());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void refreshForm() {
		BillerServiceListModel<DropdownlistBillservice> billerServiceListModel = null;
		BillerFeeMastListModel<DropdownlistBillservice> billerFeeMastListModel = null;
		try {
			billerServiceListModel = (BillerServiceListModel) lbBillerService.getModel();
			if(AppUtil.isNotNull(bw1710Form.getBllrSrvcId())){				
				int billerServiceIndex = billerServiceListModel.findIndexOfId(bw1710Form.getBllrSrvcId());
				if (billerServiceIndex > -1 && lbBillerService.getItemCount() > billerServiceIndex) {
					billerServiceListModel.addDataToSelection(billerServiceIndex);
				}
			}else{
				billerServiceListModel.addDataToSelection(0);
			}
			billerFeeMastListModel = (BillerFeeMastListModel) lbBllrFeeMastId.getModel();
			if(AppUtil.isNotNull(bw1710Form.getBllrFeeMastId())){				
				int billerFeeMastIndex = billerFeeMastListModel.findIndexOfId(bw1710Form.getBllrFeeMastId());
				if (billerFeeMastIndex > -1 && lbBllrFeeMastId.getItemCount() > billerFeeMastIndex) {
					billerFeeMastListModel.addDataToSelection(billerFeeMastIndex);
				}
			}else{
				billerFeeMastListModel.addDataToSelection(0);
			}
			txServiceFeeAmount.setValue(AppUtil.isNotNull(bw1710Form.getSrvcFee())?bw1710Form.getSrvcFee().toString():"");
			txFeeAmount.setValue(AppUtil.isNotNull(bw1710Form.getFeeAmount())?bw1710Form.getFeeAmount().toString():"");
			txFundamoFeeAmount.setValue(AppUtil.isNotNull(bw1710Form.getCustFeeAmount())?bw1710Form.getCustFeeAmount().toString():"");
			dbEfftDate.setValue(bw1710Form.getEfftDate());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void updateBW1710Form() {
		try {
			if (oid == -1) {
				DropdownlistBillservice billerService = lbBillerService.getSelectedItem().getValue();
				bw1710Form.setBllrSrvcId(AppUtil.isNotNull(billerService.getBLLR_SRVC_ID())?billerService.getBLLR_SRVC_ID():null);
			} 
			BillerFeeMast billerFeeMast = lbBllrFeeMastId.getSelectedItem().getValue();
			bw1710Form.setBllrFeeMastId(AppUtil.isNotNull(billerFeeMast.getBLLR_FEE_MAST_ID())?billerFeeMast.getBLLR_FEE_MAST_ID():null);
			bw1710Form.setFeeAmount(AppUtil.isNotNull(txFeeAmount.getValue())?new BigDecimal(txFeeAmount.getValue()):null);
			bw1710Form.setCustFeeAmount(AppUtil.isNotNull(txFundamoFeeAmount.getValue())?new BigDecimal(txFundamoFeeAmount.getValue()):null);
			bw1710Form.setEfftDate(dbEfftDate.getValue());
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	/* ## Event ## */
	@Listen("onClick = #btSave")
	public void clickSave() {	
		try {
			log.info(getOperationLogMessage(pageName, "Save Service Provider Fee", ""));			
			validateBillerFeeMast();
			validateFundamoFeeAmount();
			validateFeeAmount();
			validateEfftDate();
			validateAmountNotOver();
			if (oid == -1) {
				validateBillerService();				
				updateBW1710Form();
				save();
			} else {				
				updateBW1710Form();
				update();
			}
		} catch (BillerWebSessionTimeOutException bwto) {
			throw new BillerWebSessionTimeOutException(bwto);
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Save Service Provider Fee", getErrorParames(), ex));
			showErrorMessage(ex);			
		} finally {
		}
		log.info(getStopLogMessage(pageName));		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void save() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Save " + pageName);
		
		if(this.chkBillerFee("ADD")){
			showErrorMessage(AppMessage.getMessage(AppMessage.MSG_DATE_EXIST));
		}else{
			EventListener saveConfirmListener = new EventListener() {
				public void onEvent(Event evt) throws WrongValueException, Exception {
					if (evt.getName().equals("onOK")) {
						BillerFeeService billerFeeService;
						BillerFee billerFee;
						BillerFee billerFeeExpr = null;
						try {
							billerFeeService = BillerwebServiceFactory.getBillerFeeService();
							billerFee = bw1710Form.toBillerFee();
							billerFee.setACT_FLAG("A");
							try{
								if(AppUtil.isNotNull(billerFee.getBLLR_SRVC_ID())){
									billerFeeExpr = billerFeeService.getByServiceID(billerFee.getBLLR_SRVC_ID(), getUserInfo());
								}
							}catch (Exception e) {
							}
							final Integer oid = billerFeeService.save(billerFee, getUserInfo());
							if(AppUtil.isNotNull(oid) && AppUtil.isNotNull(billerFee.getEFFT_DATE()) && AppUtil.isNotNull(billerFeeExpr)){
								billerFeeExpr.setEXPR_DATE(DateTimeUtil.minusDate(billerFee.getEFFT_DATE(), 1));
								billerFeeService.update(billerFeeExpr, getUserInfo());					
							}
							EventListener okListener = new EventListener() {
								public void onEvent(Event evt) throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										log.debug("Save OK");
										if (isDialog()) {
											processSaveInDialogMode();
										} else {
											//redirect(AppConfiguration.getValue(AppConfiguration.BW1710_REDIRECT_PATH) + "?oid=" + oid);
											redirect(AppConfiguration.getValue(AppConfiguration.BW1700_REDIRECT_PATH));
										}
									}
								}
							};
							showSaveSuccessMessage(okListener);
						} catch (Exception ex) {
							log.error(getErrorLogMessage(pageName, "Save Biller Fee", getErrorParames(), ex));
							showSaveFailMessage();
							setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
						} finally {
							billerFeeService = null;
							billerFee = null;
							billerFeeExpr = null;
						}
					}
				}
			};
			showSaveConfirmMessage(saveConfirmListener);
			setTxLogMessage(pageCode + "#Save", requestDate, "");
		}		
	}
	
	private void processSaveInDialogMode() {
		Component parent = null;
		Window parentWindow = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1700_REDIRECT_PATH))) {
				Page pBw1700 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1700");
				log.info("Page:" + pBw1700);
				Grid gdResult = null;
				if (pBw1700 != null) {
					gdResult = (Grid) pBw1700.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw1700Dialog/bw1700DialogInclude");
					gdResult = (Grid) i.getFellow("gdResult");
				}
				Event onRefresh = new Event("onRefresh", gdResult);
				Events.sendEvent(onRefresh);
			}
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "ProcessSaveInDialogMode", "[Parrent:" + parent + ",Callback:" + callback, ex));
			throw new BillerWebException(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void update() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Update " + pageName);
		
		if(this.chkBillerFee("EDIT")){
			showErrorMessage(AppMessage.getMessage(AppMessage.MSG_DATE_EXIST));
		}else{
			EventListener saveConfirmListener = new EventListener() {
				public void onEvent(Event evt) throws WrongValueException, Exception {
					if (evt.getName().equals("onOK")) {
						BillerFeeService billerFeeService;
						BillerFee billerFee;
						try {
							billerFeeService = BillerwebServiceFactory.getBillerFeeService();
							billerFee = bw1710Form.toBillerFee();
							billerFeeService.update(billerFee, getUserInfo());
							EventListener okListener = new EventListener() {
								public void onEvent(Event evt) throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										log.debug("update OK");
										if (isDialog()) {
											processUpdateInDialogMode();
										}
									}
								}
							};
							showSaveSuccessMessage(okListener);
						} catch (Exception ex) {
							log.error(getErrorLogMessage(pageName, "Save Biller Fee", getErrorParames(), ex));
							showSaveFailMessage();
							setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
						} finally {
							billerFeeService = null;
							billerFee = null;
						}
					}
				}
			};
			showSaveConfirmMessage(saveConfirmListener);
			setTxLogMessage(pageCode + "#Update", requestDate, "");
		}
	}

	private void processUpdateInDialogMode() {
		Component parent = null;
		Window parentWindow = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1700_REDIRECT_PATH))) {
				Page pBw1700 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1700");
				log.info("Page:" + pBw1700);
				Grid gdResult = null;
				if (pBw1700 != null) {
					gdResult = (Grid) pBw1700.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw1700Dialog/bw1700DialogInclude");
					gdResult = (Grid) i.getFellow("gdResult");
				}
				Event onRefresh = new Event("onRefresh", gdResult);
				Events.sendEvent(onRefresh);
			}
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "ProcessUpdateInDialogMode", "[Parrent:" + parent + ",Callback:" + callback, ex));
			throw new BillerWebException(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}

	private Map<String, String> getErrorParames() {
		Map<String, String> params = new HashMap<String, String>();
		try {
			log.debug("GetErrorParames::" + bw1710Form.getBllrFeeId());
			params.put("BllrSrvcId", bw1710Form.getBllrSrvcId() + "");
			params.put("BllrFeeMastId", bw1710Form.getBllrFeeMastId() + "");
			params.put("FeeAmount", bw1710Form.getFeeAmount() + "");
			params.put("CustFeeAmount", bw1710Form.getCustFeeAmount() + "");
			params.put("EfftDate", bw1710Form.getEfftDate() + "");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick = #btReset")
	public void reset() {
		log.info(getOperationLogMessage(pageName, "Reset", ""));
		EventListener resetConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {
						if (oid == -1) {
							bw1710Form.setBllrSrvcId(null);
							bw1710Form.setBllrFeeMastId(null);
							bw1710Form.setSrvcFee(null);
							bw1710Form.setFeeAmount(null);
							bw1710Form.setCustFeeAmount(null);
							bw1710Form.setEfftDate(new Date());
						}					
						refreshForm();
					} catch (Exception ex) {
						log.error(ExceptionUtils.getStackTrace(ex));
						showErrorMessage(ex);
					} finally {

					}
				}
			}
		};
		showResetConfirmMessage(resetConfirmListener);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick = #btExpired")
	public void expired() {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Expired " + pageName);
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerFeeService billerFeeService;
					BillerFee billerFee;
					try {
						billerFeeService = BillerwebServiceFactory.getBillerFeeService();
						billerFee = bw1710Form.toBillerFee();
						billerFee.setEXPR_DATE(new Date());
						billerFeeService.update(billerFee, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("update OK");
									if (isDialog()) {
										processUpdateInDialogMode();
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Expired Date", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Expired", requestDate, ex);
					} finally {
						billerFeeService = null;
						billerFee = null;
					}
				}
			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Expired", requestDate, "");
	}

	@Listen("onClick = #btCancel")
	public void cancel() {
		log.info(getOperationLogMessage(pageName, "Cancel", ""));
		Component parent = null;
		Window parentWindow = null;
		try {
			if (isDialog()) {
				parent = super.getSelf().getParent().getParent();
				if (parent == null) {
					throw new DialogException("Parent is Null");
				}
				log.info("Parent::" + parent);
				parentWindow = (Window) parent;
				parentWindow.detach();
			} else {
				redirect(AppConfiguration.getValue(AppConfiguration.BW1700_REDIRECT_PATH));
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}
	
	@Listen("onSelect = #lbBllrFeeMastId")
	public void onSelectBllrFeeMastId() {
		log.info(getOperationLogMessage(pageName, "onSelectBllrFeeMastId", ""));
		try {
			Clients.clearWrongValue(lbBllrFeeMastId);
			BillerFeeMast billerFeeMast = lbBllrFeeMastId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(billerFeeMast.getBLLR_FEE_MAST_ID())) {
				if(AppUtil.isNotNull(txFeeAmount.getValue()) && AppUtil.isNotNull(txFundamoFeeAmount.getValue())){
					calSrvcFee(billerFeeMast.getBLLR_FEE_MAST_ID().toString(), new BigDecimal(txFeeAmount.getValue()), new BigDecimal(txFundamoFeeAmount.getValue()));
				}
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}
	
	@Listen("onBlur=#txFundamoFeeAmount")
	public void onBlurFundamoFeeAmount() {
		log.info(getOperationLogMessage(pageName, "onBlurFundamoFeeAmount", ""));
		try {
			onSelectBllrFeeMastId();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}
	
	@Listen("onBlur=#txFeeAmount")
	public void onBlurFeeAmount() {
		log.info(getOperationLogMessage(pageName, "onBlurFeeAmount", ""));
		try {
			onSelectBllrFeeMastId();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	/* ## Validation ## */
	private void validateBillerService() throws WrongValueException, Exception {
		DropdownlistBillservice value = lbBillerService.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbBillerService, value.getBLLR_SRVC_ID(), lServiceName.getValue());
	}
	
	private void validateBillerFeeMast() throws WrongValueException, Exception {
		BillerFeeMast value = lbBllrFeeMastId.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbBllrFeeMastId, value.getBLLR_FEE_MAST_ID(), lFeeType.getValue());
	}
	
	private void validateFundamoFeeAmount() throws WrongValueException, Exception {
		Validation.isNotEmpty(txFundamoFeeAmount, txFundamoFeeAmount.getValue(), "Customer Fee Amount");
		Validation.isStrNumber(txFundamoFeeAmount, txFundamoFeeAmount.getValue());
	}
	
	private void validateFeeAmount() throws WrongValueException, Exception {
		Validation.isNotEmpty(txFeeAmount, txFeeAmount.getValue(), "Biller Fee Amount");
		Validation.isStrNumber(txFeeAmount, txFeeAmount.getValue());
	}
	
	private void validateEfftDate() throws WrongValueException, Exception {
		Validation.isNotEmpty(dbEfftDate, dbEfftDate.getValue(), "Fee Effective Date");
	}
	
	private void validateAmountNotOver() throws WrongValueException, Exception {
		BillerFeeMast billerFeeMast = lbBllrFeeMastId.getSelectedItem().getValue();
		BigDecimal feeAmount=AppUtil.isNotNull(txFeeAmount.getValue())?new BigDecimal(txFeeAmount.getValue()):null;
		BigDecimal fdmFeeAmount=AppUtil.isNotNull(txFundamoFeeAmount.getValue())?new BigDecimal(txFundamoFeeAmount.getValue()):null;
		if(AppUtil.isNotNull(billerFeeMast)){
			if(AppUtil.isNotNull(billerFeeMast.getFEE_TYPE()) && 
				"SHARING".equalsIgnoreCase(billerFeeMast.getFEE_TYPE()) && 
				feeAmount!=null&&fdmFeeAmount!=null&&feeAmount.compareTo(fdmFeeAmount) > 0){
				throw new WrongValueException(txFeeAmount, AppMessage.getMessage(AppMessage.MSG_NOT_OVER, new String[]{"Customer Fee Amount"} ));
			}
		}
	}
		
	private boolean chkBillerFee(String mode) {		
		BillerFeeService billerFeeService = BillerwebServiceFactory.getBillerFeeService();
		DropdownlistBillservice dropdownlistBillservice=null;
		BillerFeeParam criteria = new BillerFeeParam();
		boolean validate = false;
		try {						
			try {
				dropdownlistBillservice = lbBillerService.getSelectedItem().getValue();
				criteria.setBLLR_SRVC_ID(dropdownlistBillservice.getBLLR_SRVC_ID().toString());
				if("ADD".equals(mode)){
					criteria.setFROM_DTTM(dbEfftDate.getValue());
				}else{
					criteria.setBLLR_FEE_ID(bw1710Form.getBllrFeeId());
					criteria.setTO_DTTM(dbEfftDate.getValue());
				}
			} catch (NullPointerException npe) {
			}		
			validate = billerFeeService.isExistBillerFee(criteria, getUserInfo());
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Service", dropdownlistBillservice.getBLLR_SRVC_ID().toString());
			log.error(getErrorLogMessage(pageName, "validateEfftDate", params, ex));
		} finally {
			billerFeeService = null;
			dropdownlistBillservice = null;
			criteria = null;
		}
		return validate;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private BigDecimal calSrvcFee(String bllrFeeMastId, BigDecimal feeAmount, BigDecimal fundamoFeeAmount) {
		BillerFeeMastListModel<BillerFeeMast> billerFeeMastListModel = null;
		BigDecimal srvcFee = new BigDecimal(0);
		String feeType = "";			
		try {
			if(AppUtil.isNotNull(lbBllrFeeMastId)){
				billerFeeMastListModel = (BillerFeeMastListModel) lbBllrFeeMastId.getModel();				
				if(AppUtil.isNotNull(billerFeeMastListModel)){
					feeType = billerFeeMastListModel.findFeeTypeOfId(Integer.parseInt(bllrFeeMastId));
					if(AppUtil.isNotNull(fundamoFeeAmount) && AppUtil.isNotNull(feeType)){
						if("ABSORB".equalsIgnoreCase(feeType) && AppUtil.isNotNull(feeAmount)){
							srvcFee = feeAmount.add(fundamoFeeAmount);
						}else if("SHARING".equalsIgnoreCase(feeType)){
							srvcFee = fundamoFeeAmount;
						}
					}
				}						
			}
			txServiceFeeAmount.setValue(AppUtil.isNotNull(srvcFee)?srvcFee.toString():"");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			billerFeeMastListModel = null;
			feeType = null;
		}
		return srvcFee;
	}
}
