package com.dtac.billerweb.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BillerPaymentValidate;
import mfs.exception.IsExistException;
import mfs.exception.NotFoundDataException;

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
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.Dialog;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.data.BillerDenominateSO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1412Form;
import com.dtac.billerweb.service.BillerServiceService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1412Controller extends EditPageController {

	public BW1412Controller() {
		super("Biller Service/Validate","1412");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW1412Controller.class);
	private BW1412Form bw1412Form = new BW1412Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label bw1412Title;
	@Wire
	private Radiogroup rdPaymentPresentmentFlag;
	@Wire
	private Radiogroup rdPaymentPartialFlag;
	@Wire
	private Radiogroup rdAmtAvailableFlag;
	@Wire
	private Radiogroup rdPaymentOverFlag;
	@Wire
	private Radiogroup rdOverdueFlag;
	@Wire
	private Doublebox douPaymentMin;
	@Wire
	private Label lPaymentMin;
	@Wire
	private Doublebox douPaymentMax;
	@Wire
	private Label lPaymentMax;
	@Wire
	private Radiogroup rdNonFundamoFeeFlag;
	@Wire
	private Radiogroup rdComNonFundamoFeeFlag;
	@Wire
	private Radiogroup rdDenominateFlag;
	@Wire
	private Spinner spDenoMaxMonth;
	@Wire
	private Row rowDenoMaxMonthNo;

	@Wire
	private Button btBack;
	@Wire
	private Button btSave;
	@Wire
	private Button btReset;
	@Wire
	private Button btAddDenominate;
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

			loadFormData(billerServiceId);
			if (bw1412Form.getPaymentValidateId() != null) {
				oid = bw1412Form.getPaymentValidateId();
			} else {
				oid = -1;
			}
		}
		setupPage();
		setPageTitle();
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
			paging.setPageSize(Integer.parseInt(AppConfiguration.getValue(AppConfiguration.SERV_DENOMINATE_PAGE_SIZE)));
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
				title.setValue(AppMessage.getMessage(AppMessage.BW1412_NEW_TITLE));
				chkInsertPermission(btSave);
			} else {

				if (chkUpdatePermission(updatePage.getFellows(), btSave, btReset, btAddDenominate)) {
					title.setValue(AppMessage.getMessage(AppMessage.BW1412_EDIT_TITLE));
//					btAddDenominate.setVisible(false);
				} else {					
					title.setValue(AppMessage.getMessage(AppMessage.BW1412_VIEW_TITLE));
				}
			}
			bw1412Title.setValue(title.getValue());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			parent = null;
			title = null;
		}
	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		try {
			chkUpdatePermission(updatePage.getFellows(), btSave, btReset);
			refreshForm();
			loadResultData();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void loadFormData(Integer oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BillerServiceService billerServiceService = null;
		BillerPaymentValidate billerPaymentVal = null;
		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			try {
				billerPaymentVal = billerServiceService.getBillerPaymentValidateByID(oid, getUserInfo());
				bw1412Form = bw1412Form.toBW1412Form(billerPaymentVal);
			} catch (NotFoundDataException nfde) {

			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void loadResultData() {
		log.info(getOperationLogMessage(pageName, "Load ResultData", ""));
		BillerServiceService billerServiceService = null;
		List<BillerDenominateSO> billerDenominateSOs = null;
		ListModel<BillerDenominateSO> billerDenominateSoListModel = null;

		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			billerDenominateSOs = billerServiceService.searchBillerDenominate(billerServiceId, getUserInfo());
			billerDenominateSoListModel = new SimpleListModel<BillerDenominateSO>(billerDenominateSOs);
			paging.setTotalSize(billerDenominateSoListModel.getSize());
			gdResult.setModel(billerDenominateSoListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			billerDenominateSOs = null;
			billerDenominateSoListModel = null;
		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		try {

			if (!AppUtil.isEmpty(bw1412Form.getDenominateFlag()) && bw1412Form.getDenominateFlag().equalsIgnoreCase("N")) {
				rdDenominateFlag.setSelectedIndex(1);
			} else {
				rdDenominateFlag.setSelectedIndex(0);
			}
			if (!AppUtil.isEmpty(bw1412Form.getPaymentPresentFlag()) && bw1412Form.getPaymentPresentFlag().equalsIgnoreCase("N")) {
				rdPaymentPresentmentFlag.setSelectedIndex(1);
			} else {
				rdPaymentPresentmentFlag.setSelectedIndex(0);
			}
			if (!AppUtil.isEmpty(bw1412Form.getPaymentPartialFlag()) && bw1412Form.getPaymentPartialFlag().equalsIgnoreCase("N")) {
				rdPaymentPartialFlag.setSelectedIndex(1);
			} else {
				rdPaymentPartialFlag.setSelectedIndex(0);
			}
			if (!AppUtil.isEmpty(bw1412Form.getAmtAvaliableFlag()) && bw1412Form.getAmtAvaliableFlag().equalsIgnoreCase("N")) {
				rdAmtAvailableFlag.setSelectedIndex(1);
			} else {
				rdAmtAvailableFlag.setSelectedIndex(0);
			}
			if (!AppUtil.isEmpty(bw1412Form.getPaymentOverFlag()) && bw1412Form.getPaymentOverFlag().equalsIgnoreCase("N")) {
				rdPaymentOverFlag.setSelectedIndex(1);
			} else {
				rdPaymentOverFlag.setSelectedIndex(0);
			}
			if (!AppUtil.isEmpty(bw1412Form.getOverdueFlag()) && bw1412Form.getOverdueFlag().equalsIgnoreCase("N")) {
				rdOverdueFlag.setSelectedIndex(1);
			} else {
				rdOverdueFlag.setSelectedIndex(0);
			}
			if (bw1412Form.getPaymentMin() != null) {
				douPaymentMin.setValue(bw1412Form.getPaymentMin().doubleValue());
			}
			if (bw1412Form.getPaymentMax() != null) {
				douPaymentMax.setValue(bw1412Form.getPaymentMax().doubleValue());
			}

			if (!AppUtil.isEmpty(bw1412Form.getNonFundamoFeeFlag()) && bw1412Form.getNonFundamoFeeFlag().equalsIgnoreCase("N")) {
				rdNonFundamoFeeFlag.setSelectedIndex(1);
			} else {
				rdNonFundamoFeeFlag.setSelectedIndex(0);
			}
			if (!AppUtil.isEmpty(bw1412Form.getComNonFundamoFeeFlag()) && bw1412Form.getComNonFundamoFeeFlag().equalsIgnoreCase("N")) {
				rdComNonFundamoFeeFlag.setSelectedIndex(1);
			} else {
				rdComNonFundamoFeeFlag.setSelectedIndex(0);
			}
			if (!AppUtil.isEmpty(bw1412Form.getDenominateFlag()) && bw1412Form.getDenominateFlag().equalsIgnoreCase("N")) {
				rdDenominateFlag.setSelectedIndex(1);
			} else {
				rdDenominateFlag.setSelectedIndex(0);
			}
			spDenoMaxMonth.setValue(bw1412Form.getDenoMaxMonth());
			determineShowDenoMaxMonthNo();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void determineShowDenoMaxMonthNo() {
		String value = "";
		try {
			value = rdDenominateFlag.getSelectedItem().getValue().toString();
			if (value.equalsIgnoreCase("N")) {
				rowDenoMaxMonthNo.setVisible(false);
				btAddDenominate.setDisabled(true);
			} else {
				rowDenoMaxMonthNo.setVisible(true);
				btAddDenominate.setDisabled(false);
			}

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			value = null;
		}
	}

	public void updateBW1412Form() {
		try {
			bw1412Form.setPaymentValidateId(billerServiceId);
			bw1412Form.setBillerServiceId(billerServiceId);
			bw1412Form.setDenominateFlag(rdDenominateFlag.getSelectedItem().getValue().toString());
			if (rdDenominateFlag.getSelectedItem().getValue().toString().equalsIgnoreCase("N")) {
				spDenoMaxMonth.setValue(0);
			}
			bw1412Form.setDenoMaxMonth(spDenoMaxMonth.getValue());
			bw1412Form.setPaymentPresentFlag(rdPaymentPresentmentFlag.getSelectedItem().getValue().toString());
			bw1412Form.setPaymentPartialFlag(rdPaymentPartialFlag.getSelectedItem().getValue().toString());
			bw1412Form.setAmtAvaliableFlag(rdAmtAvailableFlag.getSelectedItem().getValue().toString());
			bw1412Form.setPaymentOverFlag(rdPaymentOverFlag.getSelectedItem().getValue().toString());
			bw1412Form.setOverdueFlag(rdOverdueFlag.getSelectedItem().getValue().toString());
			if (douPaymentMin.getValue() != null) {
				bw1412Form.setPaymentMin(new BigDecimal(douPaymentMin.getValue()));
			}
			if (douPaymentMax.getValue() != null) {
				bw1412Form.setPaymentMax(new BigDecimal(douPaymentMax.getValue()));
			}
			bw1412Form.setNonFundamoFeeFlag(rdNonFundamoFeeFlag.getSelectedItem().getValue().toString());
			bw1412Form.setComNonFundamoFeeFlag(rdComNonFundamoFeeFlag.getSelectedItem().getValue().toString());
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
			log.info(getOperationLogMessage(pageName, "Save Biller Service/Validation", ""));
			updateBW1412Form();
			validatePaymentMinAllowed();
			validatePaymentMaxAllowed();
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

		}
		log.info(getStopLogMessage(pageName));
	}

	private void save() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Save " + "Validate");
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerPaymentValidate billerPaymentVal = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerPaymentVal = bw1412Form.toBillerPaymentValidate();
						billerPaymentVal.setCRTD_BY(getUserName());
						billerPaymentVal.setLAST_CHNG_BY(getUserName());
						oid = billerServiceService.saveBillerPaymentValidate(billerPaymentVal, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									setPageTitle();

								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Validate", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_PMNT_VALD_ID")) {
							Validation.isExist(rdPaymentPartialFlag, true, AppUtil.toString(oid));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Validate", getErrorParames(), ex));
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
		checkSessionTimeOut("Update " + "Validate");
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerPaymentValidate billerPaymentVal = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerPaymentVal = bw1412Form.toBillerPaymentValidate();
						billerPaymentVal.setLAST_CHNG_BY(getUserName());
						billerServiceService.updateBillerPaymentValidate(billerPaymentVal, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Update Service/Validation", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_PMNT_VALD_ID")) {
							Validation.isExist(rdPaymentPartialFlag, true, AppUtil.toString(oid));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Service/Validation", getErrorParames(), ex));
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
			params.put("Payment Validate ID", bw1412Form.getPaymentValidateId() + "");
			params.put("Biller Service ID", bw1412Form.getBillerServiceId() + "");
			params.put("Denominate Flag", bw1412Form.getDenominateFlag());
			params.put("Denominate Max Month No.", bw1412Form.getDenoMaxMonth() + "");
			params.put("Payment Present Flag", bw1412Form.getPaymentPresentFlag() + "");
			params.put("Payment Pratial Flag", bw1412Form.getPaymentPartialFlag() + "");
			params.put("Amount Avaliable Flag", bw1412Form.getAmtAvaliableFlag() + "");
			params.put("Payment Over Flag", bw1412Form.getPaymentOverFlag() + "");
			params.put("Payment Overdue Flag", bw1412Form.getOverdueFlag() + "");
			params.put("Payment Amount Min", bw1412Form.getPaymentMin() + "");
			params.put("Payment Amount Max", bw1412Form.getPaymentMax() + "");
			params.put("Non Fundamo Fee", bw1412Form.getNonFundamoFeeFlag() + "");
			params.put("Commission Non Fundamo Fee", bw1412Form.getComNonFundamoFeeFlag() + "");

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	@Listen("onClick = #btReset")
	public void reset() {
		log.info("Click Reset Edit Biller Service/Validate");
		EventListener resetConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {
						bw1412Form.setDenominateFlag("Y");
						bw1412Form.setDenoMaxMonth(0);
						bw1412Form.setPaymentPresentFlag("Y");
						bw1412Form.setPaymentPartialFlag("Y");
						bw1412Form.setAmtAvaliableFlag("Y");
						bw1412Form.setPaymentOverFlag("Y");
						bw1412Form.setOverdueFlag("Y");
						bw1412Form.setPaymentMin(new BigDecimal(0.00));
						bw1412Form.setPaymentMax(new BigDecimal(0.00));
						bw1412Form.setNonFundamoFeeFlag("Y");
						bw1412Form.setComNonFundamoFeeFlag("Y");
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
		Tab tabPaymentInfo = null;
		try {
			parent = this.getSelf().getDesktop().getPageIfAny("pBw1410");
			log.info("Parent Page:" + parent);
			if (parent == null) {
				return;
			}
			tabPaymentInfo = (Tab) parent.getFellow("tabPaymentInfo");
			tabPaymentInfo.setSelected(true);

		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Back", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			tabPaymentInfo = null;
		}
	}

	@Listen("onClick = #btAddDenominate")
	public void addDenominate() {
		checkSessionTimeOut("Add Denominate");
		log.info(getOperationLogMessage(pageName, "addDenominate", ""));
		try {
			Dialog.openBw1460MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1412_REDIRECT_PATH), billerServiceId, -1);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Add Denominate", ex));
			showErrorMessage(ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
	}

	@Listen("onClick = #btNext")
	public void next() {
		log.info(getOperationLogMessage(pageName, "Next", ""));
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
			log.error(getErrorLogMessage(pageName, "Next", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			tabPaymentChannel = null;
		}
	}

	@Listen("onCheck = #rdDenominateFlag")
	public void onDenominateFlag(Event event) {
		try {
			determineShowDenoMaxMonthNo();

		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onDenominateFlag", ex));
		} finally {

		}
	}

	@Listen("onBlur= #douPaymentMin")
	public void onBlurPaymentMin(Event event) {
		try {
			validatePaymentMinAllowed();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onBlurPaymentMin", ex));
		} finally {

		}
	}

	@Listen("onBlur= #douPaymentMax")
	public void onBlurPaymentMax(Event event) {
		try {
			validatePaymentMaxAllowed();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onBlurPaymentMax", ex));
		} finally {

		}
	}

	@Listen("onEdit = #gdResult")
	public void edit(ForwardEvent event) {
		checkSessionTimeOut("Edit Denominate");
		Row row = new Row();
		try {
			row = (Row) event.getOrigin().getTarget();
			log.info(getOperationLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue()));
			Dialog.openBw1460MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1412_REDIRECT_PATH), billerServiceId, Integer.parseInt(row.getValue().toString()));
		} catch (Exception ex) {

			log.error(getErrorLogMessage(pageName, "Edit Denominate", "EDIT ID::" + row.getValue(), ex));
			showErrorMessage(ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
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
	private void validatePaymentMinAllowed() throws WrongValueException, Exception {
		if (douPaymentMin.getValue() == null) {
			douPaymentMin.setValue(0.00);
		}
		Validation.isNotOver(douPaymentMin, douPaymentMin.getValue(), 99999999.99);
	}

	private void validatePaymentMaxAllowed() throws WrongValueException, Exception {
		if (douPaymentMax.getValue() == null) {
			douPaymentMax.setValue(0.00);
		}
		Validation.isNotOver(douPaymentMax, douPaymentMax.getValue(), 99999999.99);
		Validation.isNotLessThan(douPaymentMax, douPaymentMax.getValue(), douPaymentMin.getValue(), lPaymentMax.getValue(), lPaymentMin.getValue());
	}

}
