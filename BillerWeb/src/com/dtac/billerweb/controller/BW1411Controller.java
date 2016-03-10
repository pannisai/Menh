package com.dtac.billerweb.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BillerCategory;
import mfs.biller.persistence.bean.BillerIntegration;
import mfs.biller.persistence.bean.BillerMaster;
import mfs.biller.persistence.bean.BillerService;
import mfs.exception.IsExistException;
import mfs.exception.NotFoundDataException;
import mfs.biller.util.DateTimeUtil;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppConstant;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.Dialog;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1411Form;
import com.dtac.billerweb.listmodel.selectbox.BillerCatalogListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerMasterListModel;
import com.dtac.billerweb.listmodel.selectbox.IntegrationListModel;
import com.dtac.billerweb.service.BillerServiceService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1411Controller extends EditPageController {

	public BW1411Controller() {
		super("Biller Service/Information","1411");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW1411Controller.class);
	private BW1411Form bw1411Form = new BW1411Form();
	
	@WireVariable
	private Page updatePage;
	@Wire
	private Label bw1411Title;
	@Wire
	private Intbox intBillerServiceId;
	@Wire
	private Listbox lbBillerCategoryId;
	@Wire
	private Label lBillerCategoryId;
	@Wire
	private Textbox txFeature;
	@Wire
	private Listbox lbIntegration;
	@Wire
	private Textbox txServiceCode;
	@Wire
	private Label lServiceCode;
	@Wire
	private Listbox lbBillerMasterId;
	@Wire
	private Label lBillerMasterId;
	@Wire
	private Textbox txServiceNameEng;
	@Wire
	private Textbox txServiceFullNameEng;
	@Wire
	private Textbox txServiceNameThai;
	@Wire
	private Textbox txServiceFullNameThai;
	@Wire
	private Datebox dbActiveDate;
	@Wire
	private Datebox dbExpireDate;
	@Wire
	private Spinner spMenuSeq;
	@Wire
	private Radiogroup rdReqGuestMobileNoFlag;
	@Wire
	private Radiogroup rdScanBarcodeFlag;
	@Wire
	private Doublespinner dspVat;
	@Wire
	private Button btSave;
	@Wire
	private Button btReset;
	@Wire
	private Button btCancel;
	@Wire
	private Button btNext;
	@Wire
	private Intbox intBillerFeature;
	@Wire
	private Radiogroup rdOnlineCancelFlag;
	@Wire
	private Checkbox cOffInq;
	@Wire
	private Checkbox cOffPay;
	@Wire
	private Checkbox cOnInq;
	@Wire
	private Checkbox cOnPay;
	@Wire
	private Checkbox cAdvRec;

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

	private void setupPage() {
		SelectBoxService selectBoxService = null;
		BillerCatalogListModel<BillerCategory> billerCatalogListModel = null;
		BillerMasterListModel<BillerMaster> billerMasterListModel = null;
		IntegrationListModel<BillerIntegration> integrationListModel = null;
		try {
			setSession(AppConstant.S_SERVICE_ID,oid);
			setPageTitle();
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Biller Category ID list ## */
			billerCatalogListModel = selectBoxService.getBillerCatalogListModel();
			billerCatalogListModel.addItemSelect();
			billerCatalogListModel.addItemCreateNew();
			lbBillerCategoryId.setModel(billerCatalogListModel);
			/* ## Set Integration list ## */
			integrationListModel = selectBoxService.getIntegrationListModel();
			integrationListModel.addItemSelect();
			lbIntegration.setModel(integrationListModel);
			/* ## Set Biller Master ID list ## */
			this.setBillerMasterIdSelectBox();			
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerCatalogListModel = null;
			billerMasterListModel = null;
		}
	}

	private void setBillerMasterIdSelectBox() {
		SelectBoxService selectBoxService = null;
		BillerMasterListModel<BillerMaster> billerMasterListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();

			/* ## Set Biller Master Id list ## */
			billerMasterListModel = selectBoxService.getBillerCodeListModel();
			billerMasterListModel.addItemSelect();
			billerMasterListModel.addItemCreateNew();
			lbBillerMasterId.setModel(billerMasterListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerMasterListModel = null;
		}
	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave);
			rdOnlineCancelFlag.setSelectedIndex(1);
			intBillerFeature.setValue(3);
			setCheckbox();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		try {

			chkUpdatePermission( updatePage.getFellows(),new Listbox[]{lbBillerMasterId,lbBillerCategoryId},btSave,btReset);
			loadFormData(oid);
			refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setPageTitle() {
		Page parent = null;
		Label title = null;
		try {
			parent = this.getSelf().getDesktop().getPageIfAny("pBw1410");
			log.info("Parent Page:" + parent);
			if (parent == null) {
				return;
			}
			title = (Label) parent.getFellow("title");
			if (oid == -1) {
				title.setValue(AppMessage.getMessage(AppMessage.BW1411_NEW_TITLE));
				chkInsertPermission(btSave);
			} else {				
				if (chkUpdatePermission( updatePage.getFellows(),new Listbox[]{lbBillerMasterId,lbBillerCategoryId},btSave,btReset)){
					title.setValue(AppMessage.getMessage(AppMessage.BW1411_EDIT_TITLE));
				}else{					
					title.setValue(AppMessage.getMessage(AppMessage.BW1411_VIEW_TITLE));
				}
			}
			bw1411Title.setValue(title.getValue());

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			parent = null;
			title = null;
		}
	}

	private void loadFormData(Integer oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BillerServiceService billerServiceService = null;
		BillerService billerService = null;
		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			try {
				billerService = billerServiceService.getBillerServiceInfoByID(oid, getUserInfo());
				bw1411Form = bw1411Form.toBW1411Form(billerService);
			} catch (NotFoundDataException nfde) {

			}

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		BillerCatalogListModel<BillerCategory> billerCatalogListModel = null;
		BillerMasterListModel<BillerMaster> billerMasterListModel = null;
		IntegrationListModel<BillerIntegration> integrationListModel = null;
		try {
			intBillerServiceId.setValue(bw1411Form.getBillerServiceId());
			// lbBillerCategoryId
			billerCatalogListModel = (BillerCatalogListModel) lbBillerCategoryId.getModel();
			int billerCatalogIndex = billerCatalogListModel.findIndexOfId(bw1411Form.getBillerCategoryId());
			if (billerCatalogIndex > -1 && lbBillerCategoryId.getItemCount() > billerCatalogIndex) {
				billerCatalogListModel.addDataToSelection(billerCatalogIndex);
			}
			txServiceCode.setValue(bw1411Form.getServiceCode());
			// lbBillerMasterId
			billerMasterListModel = (BillerMasterListModel) lbBillerMasterId.getModel();
			if (billerMasterListModel != null) {
				int billerMasterIndex = billerMasterListModel.findIndexOfId(bw1411Form.getBillerMasterId());
				if (billerMasterIndex > -1 && lbBillerMasterId.getItemCount() > billerMasterIndex) {
					billerMasterListModel.addDataToSelection(billerMasterIndex);
				}
			}
			txFeature.setValue(bw1411Form.getFeature());
			// lbIntegration
			integrationListModel = (IntegrationListModel) lbIntegration.getModel();
			if (integrationListModel != null) {
				int integrationIndex = integrationListModel.findIndexOfId(bw1411Form.getIntegrationId());
				if (integrationIndex > -1 && lbIntegration.getItemCount() > integrationIndex) {
					integrationListModel.addDataToSelection(integrationIndex);
				}
			}

			txServiceNameEng.setValue(bw1411Form.getServiceNameEng());
			txServiceFullNameEng.setValue(bw1411Form.getServiceFullNameEng());
			txServiceNameThai.setValue(bw1411Form.getServiceNameTH());
			txServiceFullNameThai.setValue(bw1411Form.getServiceFullNameTH());
			dbActiveDate.setValue(bw1411Form.getServiceActiveDate());
			dbExpireDate.setValue(bw1411Form.getServiceExpireDate());
			spMenuSeq.setValue(bw1411Form.getServiceMenuSeq() == null ? 0 : bw1411Form.getServiceMenuSeq());
			try {
				if (bw1411Form.getReqGuestMobileNoFlag().equalsIgnoreCase("N")) {
					rdReqGuestMobileNoFlag.setSelectedIndex(1);
				} else {
					rdReqGuestMobileNoFlag.setSelectedIndex(0);
				}

			} catch (NullPointerException npe) {
				rdReqGuestMobileNoFlag.setSelectedIndex(0);
			}
			try {
				if (bw1411Form.getScanBarcodeFlag().equalsIgnoreCase("N")) {
					rdScanBarcodeFlag.setSelectedIndex(1);
				} else {
					rdScanBarcodeFlag.setSelectedIndex(0);
				}

			} catch (NullPointerException npe) {
				rdScanBarcodeFlag.setSelectedIndex(0);
			}		
			dspVat.setValue(bw1411Form.getVat() == null ? 0.0 : bw1411Form.getVat().doubleValue());
			
			try {
				if (bw1411Form.getBillerOnlineCancelFlag().equalsIgnoreCase("N")) {
					rdOnlineCancelFlag.setSelectedIndex(1);
				} else {
					rdOnlineCancelFlag.setSelectedIndex(0);
				}

			} catch (NullPointerException npe) {
				rdScanBarcodeFlag.setSelectedIndex(0);
			}
			intBillerFeature.setValue(bw1411Form.getBillerFeature());	
			setCheckbox();

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			billerCatalogListModel = null;
		}
	}

	public void updateBW1411Form() {
		try {
			bw1411Form.setBillerServiceId(intBillerServiceId.getValue());
			BillerCategory billerCategory = lbBillerCategoryId.getSelectedItem().getValue();
			bw1411Form.setBillerCategoryId(billerCategory.getBLLR_CATG_ID());
			bw1411Form.setServiceCode(txServiceCode.getValue());
			BillerMaster billerMaster = lbBillerMasterId.getSelectedItem().getValue();
			bw1411Form.setBillerMasterId(billerMaster.getBLLR_MSTR_ID());
			bw1411Form.setFeature(txFeature.getValue());
			BillerIntegration billerIntegration = lbIntegration.getSelectedItem().getValue();
			bw1411Form.setIntegrationId(billerIntegration.getBLLR_INGT_ID());
			bw1411Form.setServiceNameEng(txServiceNameEng.getValue());
			bw1411Form.setServiceFullNameEng(txServiceFullNameEng.getValue());
			bw1411Form.setServiceNameTH(txServiceNameThai.getValue());
			bw1411Form.setServiceFullNameTH(txServiceFullNameThai.getValue());
			bw1411Form.setServiceActiveDate(dbActiveDate.getValue());
			bw1411Form.setServiceExpireDate(dbExpireDate.getValue());
			bw1411Form.setServiceMenuSeq(spMenuSeq.getValue());
			String reqGuestMobileNoFlag = rdReqGuestMobileNoFlag.getSelectedItem().getValue();
			bw1411Form.setReqGuestMobileNoFlag(reqGuestMobileNoFlag);
			String scanBarcodeFlag = rdScanBarcodeFlag.getSelectedItem().getValue();
			bw1411Form.setScanBarcodeFlag(scanBarcodeFlag);
			if (dspVat.getValue() != null) {
				bw1411Form.setVat(new BigDecimal(dspVat.getValue()));
			}
			bw1411Form.setBillerFeature(intBillerFeature.getValue());			
			String billerOnlineCancelFlag = rdOnlineCancelFlag.getSelectedItem().getValue();
			bw1411Form.setBillerOnlineCancelFlag(billerOnlineCancelFlag);
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception ex) {

		} finally {

		}
	}

	/* ## Event ## */
	@Listen("onClick = #btSave")
	public void clickSave() {
		try {
			log.info(getOperationLogMessage(pageName, "Save Biller Service/Infomation", ""));
			validateBillerCatagory();
			validateBillerCode();
			validateBillerMaster();
			validateServiceNameEnglish();
			validateServiceFullNameEnglish();
			validateServiceNameThai();
			validateServiceFullNameThai();
			validatServiceExpireDate();
			updateBW1411Form();
			if (oid == -1) {
				save();
			} else {
				update();
			}
		} catch (BillerWebSessionTimeOutException bwto) {
			throw new BillerWebSessionTimeOutException(bwto);
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Save Biller Service/Infomation", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void save() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Save " + "Information");
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerService billerService = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerService = bw1411Form.toBillerService();
						billerService.setCRTD_BY(getUserName());
						billerService.setLAST_CHNG_BY(getUserName());
						oid = billerServiceService.saveBillerServiceInfo(billerService, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.info("Save OK id:"+oid);
									setSession(AppConstant.S_SERVICE_ID,oid);
									setPageTitle();
									intBillerServiceId.setValue(oid);
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Infomation", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_SRVC_ID")) {
							Validation.isExist(intBillerServiceId, true, AppUtil.toString(intBillerServiceId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Infomation", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						billerServiceService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Save", requestDate, "");
	}

	private void update() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Update " + "Information");
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerService billerService = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerService = bw1411Form.toBillerService();
						billerService.setLAST_CHNG_BY(getUserName());
						billerServiceService.updateBillerServiceInfo(billerService, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("update OK");
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Update Service/Infomation", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_SRVC_ID")) {
							Validation.isExist(intBillerServiceId, true, AppUtil.toString(intBillerServiceId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Service/Infomation", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						billerServiceService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Update", requestDate, "");
	}

	private Map<String, String> getErrorParames() {
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("Biller Service ID", bw1411Form.getBillerServiceId() + "");
			params.put("Biller Category ID", bw1411Form.getBillerCategoryId() + "");
			params.put("Biller Service Code", bw1411Form.getServiceCode());
			params.put("Biller Master ID", bw1411Form.getBillerMasterId() + "");
			params.put("Feature", bw1411Form.getFeature() + "");
			params.put("Integration Id", bw1411Form.getIntegrationId() + "");
			params.put("Biller Service Name Eng", bw1411Form.getServiceNameEng() + "");
			params.put("Biller Service Full Name Eng", bw1411Form.getServiceFullNameEng() + "");
			params.put("Biller Service Name TH", bw1411Form.getServiceNameTH() + "");
			params.put("Biller Service Full Name TH", bw1411Form.getServiceFullNameTH() + "");
			params.put("Biller Service Menu Seq.", bw1411Form.getServiceMenuSeq() + "");
			params.put("Biller Request Guest No Mobile Flag", bw1411Form.getReqGuestMobileNoFlag() + "");
			params.put("Biller Vat", bw1411Form.getVat() + "");			
			params.put("Biller Feature", bw1411Form.getBillerFeature() + "");
			params.put("Biller Online Cancel Flag", bw1411Form.getBillerOnlineCancelFlag() + "");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}	

	private void setCheckbox() {
		if(intBillerFeature.getValue() != null){
			if((intBillerFeature.getValue()&1)==1){
				cOffInq.setChecked(true);
			}
			if((intBillerFeature.getValue()&2)==2){
				cOffPay.setChecked(true);
			}
			if((intBillerFeature.getValue()&4)==4){
				cOnInq.setChecked(true);
			}
			if((intBillerFeature.getValue()&8)==8){
				cOnPay.setChecked(true);
			}
			if((intBillerFeature.getValue()&16)==16){
				cAdvRec.setChecked(true);
			}
		}	
	}
	
	private void getCheckbox() {
		Integer x = 0;
    	if(cOffInq.isChecked()){
    		x +=1;
    	}
    	if(cOffPay.isChecked()){
    		x +=2;
    	}
		if(cOnInq.isChecked()){
			x +=4;						            		
		}
		if(cOnPay.isChecked()){
			x +=8;
		}
		if(cAdvRec.isChecked()){
			x +=16;
		}
		intBillerFeature.setValue(x>0?x:null);
	}

	@Listen("onClick = #btReset")
	public void reset() {
		log.info("Click Reset Edit Biller Service/Infomation");
		EventListener resetConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {
						bw1411Form.setBillerCategoryId(null);
						lbBillerCategoryId.setSelectedIndex(0);
						bw1411Form.setServiceCode(null);
						bw1411Form.setBillerMasterId(null);
						lbBillerMasterId.setSelectedIndex(0);
						txFeature.setValue(null);
						lbIntegration.setSelectedIndex(0);
						bw1411Form.setServiceNameEng(null);
						bw1411Form.setServiceFullNameEng(null);
						bw1411Form.setServiceNameTH(null);
						bw1411Form.setServiceFullNameTH(null);
						bw1411Form.setServiceActiveDate(DateTimeUtil.getCurrent());
						bw1411Form.setServiceExpireDate(DateTimeUtil.getCurrent());
						bw1411Form.setServiceMenuSeq(null);
						bw1411Form.setReqGuestMobileNoFlag("Y");
						bw1411Form.setScanBarcodeFlag("Y");
						bw1411Form.setVat(null);					
						bw1411Form.setBillerOnlineCancelFlag("N");
						bw1411Form.setBillerFeature(3);
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

	@Listen("onClick = #btCancel")
	public void cancel() {
		log.info(getOperationLogMessage(pageName, "Cancel", ""));
		try {
			redirect(AppConfiguration.getValue(AppConfiguration.BW1400_REDIRECT_PATH));
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {

		}
	}

	@Listen("onClick = #btNext")
	public void next() {
		log.info(getOperationLogMessage(pageName, "Next", ""));
		Page parent = null;
		Tab tabPaymentValidate = null;
		try {
			parent = this.getSelf().getDesktop().getPageIfAny("pBw1410");
			log.info("Parent Page:" + parent);
			if (parent == null) {
				return;
			}
			tabPaymentValidate = (Tab) parent.getFellow("tabPaymentValidate");
			Event event = new Event("onSelect");
			Events.sendEvent(tabPaymentValidate, event);

		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Next", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			tabPaymentValidate = null;
		}
	}

	/* ## Biller Category ID Event ## */
	@Listen("onAfterRender=#lbBillerCategoryId")
	public void onAfterRenderBillerCategoryId(Event even) {
		log.info(getOperationLogMessage(pageName, "onAfterRenderBillerCategoryId", ""));
		try {
			Listitem createNew = lbBillerCategoryId.getItemAtIndex(lbBillerCategoryId.getItemCount() - 1);
			createNew.setSclass("listboxFooter");
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onAfterRenderBillerCategoryId", ex));
			showErrorMessage(ex);
		}
	}

	@Listen("onSelect = #lbBillerCategoryId")
	public void onSelectBillerCategoryId() {
		log.info(getOperationLogMessage(pageName, "onSelectBillerCategoryId", ""));
		BillerCategory billerCategory = null;
		try {
			Clients.clearWrongValue(lbBillerCategoryId);
			/* ## Create New ## */
			billerCategory = lbBillerCategoryId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(billerCategory.getBLLR_CATG_ID()) && billerCategory.getBLLR_CATG_ID() == -1) {
				processCreateNewBillerCategoryId();
			}
			// /* Set biller Master ID */
			// this.setBillerMasterIdSelectBox();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectBillerCategoryId", ex));
			showErrorMessage(ex);
		} finally {
			billerCategory = null;
		}
		log.info(getStopLogMessage(pageName));
	}

	private void processCreateNewBillerCategoryId() throws Exception {
		Dialog.openBw1110MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1411_REDIRECT_PATH), -1);
	}

	/* ## Biller Master ID Event ## */
	@Listen("onAfterRender=#lbBillerMasterId")
	public void onAfterRenderBillerMasterId(Event even) {
		log.info(getOperationLogMessage(pageName, "onAfterRenderBillerMasterId", ""));
		BillerMasterListModel<BillerMaster> billerMasterListModel = null;
		try {
			Listitem createNew = lbBillerMasterId.getItemAtIndex(lbBillerMasterId.getItemCount() - 1);
			createNew.setSclass("listboxFooter");
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onAfterRenderBillerMasterId", ex));
			showErrorMessage(ex);
		}
	}

	@Listen("onSelect = #lbBillerMasterId")
	public void onSelectBillerMasterId() {
		log.info(getOperationLogMessage(pageName, "onSelectBillerMasterId", ""));
		try {
			Clients.clearWrongValue(lbBillerMasterId);
			/* ## Create New ## */
			BillerMaster billerMaster = lbBillerMasterId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(billerMaster.getBLLR_MSTR_ID()) && billerMaster.getBLLR_MSTR_ID() == -1) {
				processCreateNewBillerMasterId();
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectBillerMasterId", ex));
			showErrorMessage(ex);
		}
		log.info(getStopLogMessage(pageName));
	}

	private void processCreateNewBillerMasterId() throws Exception {
		Dialog.openBw1210MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1411_REDIRECT_PATH), -1);
	}

	@Listen("onRefreshMasterIdList = #lbBillerMasterId")
	public void refreshBillerMasterId(Event event) {
		log.info(getOperationLogMessage(pageName, "refreshBillerMasterId", ""));
		try {
			setBillerMasterIdSelectBox();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "refreshBillerMasterId", ex));
			showErrorMessage(ex);
		}
	}

	/* ## Validation ## */
	@Listen("onBlur=#txServiceCode")
	public void onBlurServiceCode() {
		log.info(getOperationLogMessage(pageName, "onBlurServiceCode", ""));
		try {
			validateBillerCode();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	@Listen("onBlur=#txServiceNameEng")
	public void onBlurServiceNameEng() {
		log.info(getOperationLogMessage(pageName, "onBlurServiceNameEng", ""));
		try {
			validateServiceNameEnglish();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	@Listen("onBlur=#txServiceFullNameEng")
	public void onBlurServiceFullNameEng() {
		log.info(getOperationLogMessage(pageName, "onBlurServiceFullNameEng", ""));
		try {
			validateServiceFullNameEnglish();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	@Listen("onBlur=#txServiceNameThai")
	public void onBlurServiceNameTH() {
		log.info(getOperationLogMessage(pageName, "onBlurServiceNameTH", ""));
		try {
			validateServiceNameThai();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	@Listen("onBlur=#txServiceFullNameThai")
	public void onBlurServiceFullNameTH() {
		log.info(getOperationLogMessage(pageName, "onBlurServiceFullNameTH", ""));
		try {
			validateServiceFullNameThai();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	@Listen("onBlur=#dbExpireDate")
	public void onBlurExpireDate() {
		log.info(getOperationLogMessage(pageName, "onBlurExpireDate", ""));
		try {
			validatServiceExpireDate();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}
	
	@Listen("onCheck=#cOffInq")
	public void onCheckOffInq() {
		log.info(getOperationLogMessage(pageName, "onCheckOffInq", ""));
		try {
			getCheckbox();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}
	
	@Listen("onCheck=#cOffPay")
	public void onCheckOffPay() {
		log.info(getOperationLogMessage(pageName, "onCheckOffPay", ""));
		try {
			getCheckbox();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}
	
	@Listen("onCheck=#cOnInq")
	public void onCheckOnInq() {
		log.info(getOperationLogMessage(pageName, "onCheckOnInq", ""));
		try {
			getCheckbox();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}	
	
	@Listen("onCheck=#cOnPay")
	public void onCheckOnPay() {
		log.info(getOperationLogMessage(pageName, "onCheckOnPay", ""));
		try {
			getCheckbox();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}
	
	@Listen("onCheck=#cAdvRec")
	public void onCheckAdvRec() {
		log.info(getOperationLogMessage(pageName, "onCheckAdvRec", ""));
		try {
			getCheckbox();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	private void validateBillerCatagory() throws WrongValueException, Exception {
		BillerCategory value = lbBillerCategoryId.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbBillerCategoryId, value.getBLLR_CATG_ID(), lBillerCategoryId.getValue());
	}

	private void validateBillerMaster() throws WrongValueException, Exception {
		BillerMaster value = lbBillerMasterId.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbBillerMasterId, value.getBLLR_MSTR_ID(), lBillerMasterId.getValue());
	}

	private void validatServiceExpireDate() throws WrongValueException, Exception {
		Validation.validateEndDate(dbExpireDate, dbActiveDate.getValue(), dbExpireDate.getValue());
	}

	private void validateServiceNameThai() throws WrongValueException, Exception {
		Validation.isNotEnglishChar(txServiceNameThai, txServiceNameThai.getValue());
	}

	private void validateServiceFullNameThai() throws WrongValueException, Exception {
		Validation.isNotEnglishChar(txServiceFullNameThai, txServiceFullNameThai.getValue());
	}

	private void validateServiceNameEnglish() throws WrongValueException, Exception {
		Validation.isNotThaiChar(txServiceNameEng, txServiceNameEng.getValue());
	}

	private void validateServiceFullNameEnglish() throws WrongValueException, Exception {
		Validation.isNotThaiChar(txServiceFullNameEng, txServiceFullNameEng.getValue());
	}

	private void validateBillerCode() throws WrongValueException, Exception {
		Validation.isNotEmpty(txServiceCode, txServiceCode.getValue(), lServiceCode.getValue());
		Validation.isStrNumber(txServiceCode, txServiceCode.getValue());
	}

}
