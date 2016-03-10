package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BillerMaster;
import mfs.exception.IsExistException;

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
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
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
import com.dtac.billerweb.form.BW1210Form;
import com.dtac.billerweb.service.BillerMasterService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1210Controller extends EditPageController {
	public BW1210Controller() {
		super("Edit Biller Master","1210");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW1210Controller.class);
	private BW1210Form bw1210Form = new BW1210Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label title;
	@Wire
	private Intbox intBillerMasterId;
	@Wire
	private Label lbBillerMasterId;
	@Wire
	private Textbox txBillerMasterName;
	@Wire
	private Textbox txBillerMasterNameTH;
	@Wire
	private Textbox txBillerCode;
	@Wire
	private Label lbBillerCode;
	@Wire
	private Textbox txBillerTaxId;
	@Wire
	private Textbox txSecondaryBillerTaxId;	
	@Wire
	private Label lbBillerTaxID;
	@Wire
	private Textbox txCompanyCode;
	@Wire
	private Listbox lbActiveFlag;
	@Wire
	private Button btSave;
	@Wire
	private Button btReset;
	@Wire
	private Button btCancel;

	@Override
	protected void init() throws Exception {
		if (oid == -1) {
			setupNewPage();
		} else {
			setupEditPage();
		}

	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave);
			title.setValue(AppMessage.getMessage(AppMessage.BW1210_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			i = (Include) Path.getComponent("/bw1210Dialog/bw1210DialogInclude");
			comps = i != null ? i.getFellows() : updatePage.getFellows();
			if (chkUpdatePermission(comps, btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW1210_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW1210_VIEW_TITLE));
			}
			loadFormData(oid);
			refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i = null;
			comps = null;
		}
	}

	private void loadFormData(Integer oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BillerMasterService billerMasterService = null;
		BillerMaster billerMaster = null;
		try {
			billerMasterService = BillerwebServiceFactory.getBillerMasterService();
			billerMaster = billerMasterService.getByID(oid, getUserInfo());
			bw1210Form = bw1210Form.toBW1210Form(billerMaster);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		try {
			intBillerMasterId.setValue(bw1210Form.getBillerMasterId());
			txBillerMasterName.setValue(bw1210Form.getBillerMasterName());
			txBillerMasterNameTH.setValue(bw1210Form.getBillerMasterNameTH());
			txBillerCode.setValue(bw1210Form.getBillerCode());
			txBillerTaxId.setValue(bw1210Form.getBillerTaxId());
			txSecondaryBillerTaxId.setValue(bw1210Form.getSecondaryBillerTaxId());
			txCompanyCode.setValue(bw1210Form.getCompanyCode());
			if (bw1210Form.getActiveFlag().equalsIgnoreCase("A")) {
				lbActiveFlag.setSelectedIndex(0);
			} else {
				lbActiveFlag.setSelectedIndex(1);
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	public void updateBW1210Form() {
		try {
			bw1210Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
			bw1210Form.setBillerMasterId(intBillerMasterId.getValue());
			bw1210Form.setBillerMasterName(txBillerMasterName.getValue());
			bw1210Form.setBillerMasterNameTH(txBillerMasterNameTH.getValue());
			bw1210Form.setBillerCode(txBillerCode.getValue());
			bw1210Form.setBillerTaxId(txBillerTaxId.getValue());
			bw1210Form.setSecondaryBillerTaxId(txSecondaryBillerTaxId.getValue());
			bw1210Form.setCompanyCode(txCompanyCode.getValue());
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
			log.info(getOperationLogMessage(pageName, "Save Biller Category", ""));
			validateBillerNameEnglish();
			validateBillerNameThai();
			validateBillerCode();
			validateTaxId();			
			if(AppUtil.isNotNull(txSecondaryBillerTaxId.getValue())){
				validateSecondaryTaxId();
			}
			updateBW1210Form();
			if (oid == -1) {
				validateBillerMasterID();
				save();
			} else {
				update();
			}
		} catch (BillerWebSessionTimeOutException bwto) {
			throw new BillerWebSessionTimeOutException(bwto);
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Save Biller Category", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void save() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Save " + pageName);
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerMasterService billerMasterService;
					BillerMaster billerMaster;
					try {
						billerMasterService = BillerwebServiceFactory.getBillerMasterService();
						billerMaster = bw1210Form.toBillerMaster();
						billerMaster.setCRTD_BY(getUserName());
						billerMaster.setLAST_CHNG_BY(getUserName());
						final Integer oid = billerMaster.getBLLR_MSTR_ID();
						billerMasterService.save(billerMaster, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									if (isDialog()) {
										processSaveInDialogMode();
									} else {
										redirect(AppConfiguration.getValue(AppConfiguration.BW1210_REDIRECT_PATH) + "?oid=" + oid);
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Biller Master", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_MSTR_ID")) {
							Validation.isExist(intBillerMasterId, true, AppUtil.toString(intBillerMasterId.getValue()));
						} else if (errorMessage.equalsIgnoreCase("BLLR_CODE")) {
							Validation.isExist(txBillerCode, true, txBillerCode.getValue());
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Master", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						billerMasterService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Save", requestDate, "");
	}

	private void processSaveInDialogMode() {
		SelectBoxService selectBoxService = null;
		Component parent = null;
		Window parentWindow = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1411_REDIRECT_PATH))) {
				Page pBw1411 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1411");
				log.info("Page:" + pBw1411);
				Listbox lbBillerMasterId = null;
				if (pBw1411 != null) {
					lbBillerMasterId = (Listbox) pBw1411.getFellow("lbBillerMasterId");
				} else {
					Include i = (Include) Path.getComponent("/bw1411Dialog/bw1411DialogInclude");
					lbBillerMasterId = (Listbox) i.getFellow("lbBillerMasterId");
				}
				Event onRefreshMasterIdList = new Event("onRefreshMasterIdList", lbBillerMasterId, -1);
				Events.sendEvent(onRefreshMasterIdList);
			}
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "ProcessSaveInDialogMode", "[Parrent:" + parent + ",Callback:" + callback, ex));
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			parent = null;
			parentWindow = null;
		}
	}

	private void update() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Update " + pageName);
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerMasterService billerMasterService;
					BillerMaster billerMaster;
					try {
						billerMasterService = BillerwebServiceFactory.getBillerMasterService();
						billerMaster = bw1210Form.toBillerMaster();
						billerMaster.setLAST_CHNG_BY(getUserName());
						billerMasterService.update(billerMaster, getUserInfo());
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
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Update Biller Master", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BLLR_MSTR_ID")) {
							Validation.isExist(intBillerMasterId, true, AppUtil.toString(intBillerMasterId.getValue()));
						} else if (errorMessage.equalsIgnoreCase("BLLR_CODE")) {
							Validation.isExist(txBillerCode, true, txBillerCode.getValue());
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Master", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						billerMasterService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Update", requestDate, "");
	}

	private void processUpdateInDialogMode() {
		SelectBoxService selectBoxService = null;
		Component parent = null;
		Window parentWindow = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1411_REDIRECT_PATH))) {
				Page pBw1411 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1411");
				log.info("Page:" + pBw1411);
				Listbox lbBillerMasterId = null;
				if (pBw1411 != null) {
					lbBillerMasterId = (Listbox) pBw1411.getFellow("lbBillerMasterId");
				} else {
					Include i = (Include) Path.getComponent("/bw1411Dialog/bw1411DialogInclude");
					lbBillerMasterId = (Listbox) i.getFellow("lbBillerMasterId");
				}
				int currentBillerMaster = lbBillerMasterId.getSelectedIndex();
				Event onRefreshMasterIdList = new Event("onRefreshMasterIdList", lbBillerMasterId, currentBillerMaster);
				Events.sendEvent(onRefreshMasterIdList);

			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1200_REDIRECT_PATH))) {
				Page pBw1200 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1200");
				log.info("Page:" + pBw1200);
				Grid gdResult = null;
				if (pBw1200 != null) {
					gdResult = (Grid) pBw1200.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw1200Dialog/bw1200DialogInclude");
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
			selectBoxService = null;
			parent = null;
			parentWindow = null;
			// billerMasterListModel = null;
		}
	}

	private Map<String, String> getErrorParames() {
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("Biller Master ID", bw1210Form.getBillerMasterId() + "");
			params.put("Biller Master Name", bw1210Form.getBillerMasterName());
			params.put("Biller Master NameTh", bw1210Form.getBillerMasterNameTH() + "");
			params.put("Bille Code", bw1210Form.getBillerCode());
			params.put("Biller Tax ID", bw1210Form.getBillerTaxId());
			params.put("Secondary Biller Tax ID", bw1210Form.getSecondaryBillerTaxId());
			params.put("Company Code", bw1210Form.getCompanyCode());
			params.put("ActiveFlag", bw1210Form.getActiveFlag());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	@Listen("onClick = #btReset")
	public void reset() {
		log.info("Click Reset Edit Biller Master");
		EventListener resetConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {
						bw1210Form.setActiveFlag("A");
						bw1210Form.setBillerMasterId(null);
						bw1210Form.setBillerMasterName(null);
						bw1210Form.setBillerMasterNameTH(null);
						bw1210Form.setBillerCode(null);
						bw1210Form.setBillerTaxId(null);
						bw1210Form.setSecondaryBillerTaxId(null);
						bw1210Form.setCompanyCode(null);
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
				redirect(AppConfiguration.getValue(AppConfiguration.BW1200_REDIRECT_PATH));
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}

	@Listen("onBlur= #intBillerMasterId")
	public void onBlurBillerMasterId(Event event) {
		try {
			if (oid == -1) {
				validateBillerMasterID();
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onBlur= #txBillerCode")
	public void onBlurBillerCode(Event event) {
		try {
			txBillerCode.setValue(txBillerCode.getValue().toUpperCase());
			validateBillerCode();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onBlur= #txBillerTaxId")
	public void onBlurBillerTaxId(Event event) {
		try {
			validateTaxId();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onBlur= #txBillerMasterName")
	public void onBlurBillerMasterName(Event event) {
		try {
			validateBillerNameEnglish();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	@Listen("onBlur= #txBillerMasterNameTH")
	public void onBlurBillerMasterNameTH(Event event) {
		try {
			validateBillerNameThai();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void validateBillerMasterID() throws WrongValueException, Exception {
		BillerMasterService billerMasterService = null;
		boolean result = false;
		Validation.isNotEmpty(intBillerMasterId, intBillerMasterId.getValue(), lbBillerMasterId.getValue());
		Validation.isStrNumber(intBillerMasterId, intBillerMasterId.getValue() + "");
		Validation.isFixLength(intBillerMasterId, intBillerMasterId.getValue() + "", 6, lbBillerMasterId.getValue());
		billerMasterService = BillerwebServiceFactory.getBillerMasterService();
		result = billerMasterService.isExistBillerMasterId(intBillerMasterId.getValue(), getUserInfo());
		if (result) {
			Validation.isExist(intBillerMasterId, result, AppUtil.toString(intBillerMasterId.getValue()));
		}
		billerMasterService = null;
	}

	private void validateBillerCode() throws WrongValueException, Exception {
		BillerMasterService billerMasterService = null;
		boolean result = false;
		Validation.isNotEmpty(txBillerCode, txBillerCode.getValue(), lbBillerCode.getValue());
		billerMasterService = BillerwebServiceFactory.getBillerMasterService();
		result = billerMasterService.isExistBlllerCode(oid, txBillerCode.getValue(), getUserInfo());
		if (result) {
			Validation.isExist(txBillerCode, result, txBillerCode.getValue());
		}
		billerMasterService = null;
	}

	private void validateTaxId() throws WrongValueException, Exception {
		Validation.isNotEmpty(txBillerTaxId, txBillerTaxId.getValue(), lbBillerTaxID.getValue());
		Validation.isStrNumber(txBillerTaxId, txBillerTaxId.getValue());
		if ((txBillerTaxId.getValue().length() != 10) && (txBillerTaxId.getValue().length() != 13)) {
			throw new WrongValueException(txBillerTaxId, AppMessage.getMessage(AppMessage.MSG_TAX_ID_FORMAT_ONLY));
		}
	}
	
	private void validateSecondaryTaxId() throws WrongValueException, Exception {
		Validation.isStrNumber(txSecondaryBillerTaxId, txSecondaryBillerTaxId.getValue());
		if ((txSecondaryBillerTaxId.getValue().length() != 10) && (txSecondaryBillerTaxId.getValue().length() != 13)) {
			throw new WrongValueException(txSecondaryBillerTaxId, AppMessage.getMessage(AppMessage.MSG_TAX_ID_FORMAT_ONLY));
		}
	}

	private void validateBillerNameEnglish() throws WrongValueException, Exception {
		Validation.isNotThaiChar(txBillerMasterName, txBillerMasterName.getValue());
	}

	private void validateBillerNameThai() throws WrongValueException, Exception {
		Validation.isNotEnglishChar(txBillerMasterNameTH, txBillerMasterNameTH.getValue());
	}
}
