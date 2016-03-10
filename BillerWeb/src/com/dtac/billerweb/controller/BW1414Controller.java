package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BillerForm;
import mfs.biller.persistence.bean.BillerRefParam;
import mfs.exception.IsExistException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.Dialog;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.data.BillerServiceInputSO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1414Form;
import com.dtac.billerweb.service.BillerServiceService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1414Controller extends EditPageController {

	public BW1414Controller() {
		super("Biller Service/Input Form","1414");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW1414Controller.class);
	private BW1414Form bw1414Form = new BW1414Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label bw1414Title;
	@Wire
	private Textbox txFormName;
	@Wire
	private Textbox txFormCaptionEng;
	@Wire
	private Textbox txFormCaptionTH;
	@Wire
	private Button btBack;
	@Wire
	private Button btSave;
	@Wire
	private Button btAddInput;
	@Wire
	private Button btNext;
	@Wire
	protected Grid gdResult;
	@Wire
	protected Paging paging;

	private Integer billerServiceId;

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub
		String serviceID = getParameter("serviceId");
		if (!AppUtil.isEmpty(serviceID)) {
			billerServiceId = Integer.parseInt(serviceID);
		} else {
			billerServiceId = -1;
		}
		if (billerServiceId != -1) {
			loadFormData();
			if (bw1414Form.getFormId() != null) {
				oid = bw1414Form.getFormId();
			} else {
				oid = -1;
			}

		}
		setupPage();
		if (oid == -1) {
			setupNewPage();
		} else {
			setupEditPage();
		}
	}

	private void setupPage() {
		try {
			setPageTitle();
			/* ## Set Result Grid ## */
			gdResult.setPaginal(paging);
			paging.setPageSize(Integer.parseInt(AppConfiguration.getValue(AppConfiguration.PAGE_SIZE_KEY)));
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
				chkInsertPermission(btSave);
				title.setValue(AppMessage.getMessage(AppMessage.BW1414_NEW_TITLE));
			} else {
				if (chkUpdatePermission(updatePage.getFellows(), btSave, btAddInput)) {
					title.setValue(AppMessage.getMessage(AppMessage.BW1414_EDIT_TITLE));
					if (!getAuthorization().isInsert()) {
						btAddInput.setVisible(false);
					}
				} else {
					title.setValue(AppMessage.getMessage(AppMessage.BW1414_VIEW_TITLE));
				}

			}
			bw1414Title.setValue(title.getValue());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			parent = null;
			title = null;
		}
	}

	private void setupNewPage() {
		try {
			// title.setValue(AppMessage.getMessage(AppMessage.BW1310_NEW_TITLE));
			btAddInput.setDisabled(true);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		try {
			// title.setValue(AppMessage.getMessage(AppMessage.BW1310_EDIT_TITLE));
			refreshForm();
			loadResultData();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void loadFormData() {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BillerServiceService billerServiceService = null;
		BillerForm billerForm = null;
		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			billerForm = billerServiceService.getBillerFormByServiceID(billerServiceId, getUserInfo());
			bw1414Form = bw1414Form.toBW1414Form(billerForm);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		try {
			txFormName.setValue(bw1414Form.getFormName());
			txFormCaptionEng.setValue(bw1414Form.getFormCaptionEng());
			txFormCaptionTH.setValue(bw1414Form.getFormCaptionTH());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	public void updateBW1414Form() {
		try {
			bw1414Form.setFormId(billerServiceId);
			bw1414Form.setBillerServiceId(billerServiceId);
			bw1414Form.setFormName(txFormName.getValue());
			bw1414Form.setFormCaptionEng(txFormCaptionEng.getValue());
			bw1414Form.setFormCaptionTH(txFormCaptionTH.getValue());
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void loadResultData() {
		log.info(getOperationLogMessage(pageName, "Load ResultData", ""));
		BillerServiceService billerServiceService = null;
		List<BillerServiceInputSO> billerServiceInputSOs = null;
		ListModel<BillerServiceInputSO> billerSrvInputListModel = null;
		BillerRefParam criteria = null;
		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			criteria = new BillerRefParam();
			criteria.setBLLR_FORM_ID(oid);
			criteria.setBLLR_SRVC_ID(billerServiceId);
			billerServiceInputSOs = billerServiceService.searchBillerServiceInput(criteria, getUserInfo());
			billerSrvInputListModel = new SimpleListModel<BillerServiceInputSO>(billerServiceInputSOs);
			paging.setTotalSize(billerSrvInputListModel.getSize());
			gdResult.setModel(billerSrvInputListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			billerServiceInputSOs = null;
			billerSrvInputListModel = null;
			criteria = null;
		}
		log.info(getStopLogMessage(pageName));
	}

	/* ## Event ## */
	@Listen("onClick = #btSave")
	public void clickSave() {
		try {
			log.info(getOperationLogMessage(pageName, "Save Biller Service/Validation", ""));
			validateCaptionEnglish();
			validateCaptionThai();
			updateBW1414Form();
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
			log.error(getErrorLogMessage(pageName, "Save Biller Service/Validate", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}

	}

	private void save() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Save " + "Input Form");
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerForm billerForm = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerForm = bw1414Form.toBillerForm();
						billerForm.setCRTD_BY(getUserName());
						billerForm.setLAST_CHNG_BY(getUserName());
						oid = billerServiceService.saveBillerForm(billerForm, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									loadResultData();
									setPageTitle();
									btAddInput.setDisabled(false);
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Input Form", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_FORM_ID")) {
							Validation.isExist(btSave, true, AppUtil.toString(oid));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Input Form", getErrorParames(), ex));
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
		checkSessionTimeOut("Update " + "Input Form");
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerForm billerForm = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerForm = bw1414Form.toBillerForm();
						billerForm.setLAST_CHNG_BY(getUserName());
						billerServiceService.updateBillerForm(billerForm, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Update Service/Input Form", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_PMNT_VALD_ID")) {
							Validation.isExist(btSave, true, AppUtil.toString(oid));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Service/Input Form", getErrorParames(), ex));
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
			params.put("Form ID", bw1414Form.getFormId() + "");
			params.put("Biller Service ID", bw1414Form.getBillerServiceId() + "");
			params.put("Form Name", bw1414Form.getFormName());
			params.put("Form Caption Eng.", bw1414Form.getFormCaptionEng() + "");
			params.put("Form Caption Thai", bw1414Form.getFormCaptionTH() + "");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	@Listen("onClick = #btAddInput")
	public void addInput() {
		checkSessionTimeOut("Add Input " + "Input Form");
		log.info(getOperationLogMessage(pageName, "Add Input", ""));
		try {
			Dialog.openBw1440MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1414_REDIRECT_PATH), -1, oid, billerServiceId);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Add Input", ex));
			showErrorMessage(ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
	}

	@Listen("onClick = #btReset")
	public void reset() {
		log.info("Click Reset Edit Biller Service/Validate");
		EventListener resetConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {
						bw1414Form.setFormName(null);
						bw1414Form.setFormCaptionEng(null);
						bw1414Form.setFormCaptionTH(null);
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

	@Listen("onClick = #btBack")
	public void back() {
		log.info(getOperationLogMessage(pageName, "Back", ""));
		Page parent = null;
		Tab tabPaymentChannel = null;
		try {
			parent = this.getSelf().getDesktop().getPageIfAny("pBw1410");
			log.info("Parent Page:" + parent);
			if (parent == null) {
				return;
			}
			tabPaymentChannel = (Tab) parent.getFellow("tabPaymentChannel");
			Event event = new Event("onSelect");
			Events.sendEvent(tabPaymentChannel, event);

		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Back", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			tabPaymentChannel = null;
		}
	}

	@Listen("onClick = #btNext")
	public void next() {
		log.info(getOperationLogMessage(pageName, "Next", ""));
		Page parent = null;
		Tab tabBarcode = null;
		try {
			parent = this.getSelf().getDesktop().getPageIfAny("pBw1410");
			log.info("Parent Page:" + parent);
			if (parent == null) {
				return;
			}
			tabBarcode = (Tab) parent.getFellow("tabBarcode");
			Event event = new Event("onSelect");
			Events.sendEvent(tabBarcode, event);

		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Next", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			tabBarcode = null;
		}
	}

	@Listen("onEdit = #gdResult")
	public void edit(ForwardEvent event) {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Edit Input " + "Input Form");
		Row row = new Row();
		try {
			row = (Row) event.getOrigin().getTarget();
			log.info(getOperationLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue()));
			Dialog.openBw1440MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1414_REDIRECT_PATH), Integer.parseInt(row.getValue().toString()), oid, billerServiceId);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue(), ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Edit", requestDate, ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
		setTxLogMessage(pageCode + "#Save", requestDate, "");
	}

	@Listen("onRefresh = #gdResult")
	public void refreshResultGrid() {
		log.info(getOperationLogMessage(pageName, "refreshResultGrid", ""));
		try {
			loadResultData();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "refreshResultGrid", ex));
			showErrorMessage(ex);
		}
	}

	/* ## Validation ## */
	@Listen("onBlur=#txFormCaptionTH")
	public void onBlurCaptonTH() {
		log.info(getOperationLogMessage(pageName, "onBlurCaptonTH", ""));
		try {
			validateCaptionThai();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	@Listen("onBlur=#txFormCaptionEng")
	public void onBlurCaptonEng() {
		log.info(getOperationLogMessage(pageName, "onBlurCaptonEng", ""));
		try {
			validateCaptionEnglish();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	private void validateCaptionThai() throws WrongValueException, Exception {
		Validation.isNotEnglishChar(txFormCaptionTH, txFormCaptionTH.getValue());
	}

	private void validateCaptionEnglish() throws WrongValueException, Exception {
		Validation.isNotThaiChar(txFormCaptionEng, txFormCaptionEng.getValue());
	}
	
	
}
