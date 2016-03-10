package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BillerForm;
import mfs.biller.persistence.bean.BillerRef;
import mfs.biller.persistence.bean.BillerRefDataType;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Spinner;
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
import com.dtac.billerweb.form.BW1414Form;
import com.dtac.billerweb.form.BW1440Form;
import com.dtac.billerweb.listmodel.selectbox.BillerRefDataTypeListModel;
import com.dtac.billerweb.service.BillerServiceService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1440Controller extends EditPageController {

	public BW1440Controller() {
		super("EDIT Biller Service/Popup Input", "1440");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW1440Controller.class);
	private BW1414Form bw1414Form = new BW1414Form();
	private BW1440Form bw1440Form = new BW1440Form();
	@Wire
	private Label title;
	@Wire
	private Intbox intFormId;
	@Wire
	private Textbox txFormName;
	@Wire
	private Intbox intInputId;
	@Wire
	private Intbox intInputSeqId;
	@Wire
	private Textbox txLabelTH;
	@Wire
	private Textbox txLabelEng;
	@Wire
	private Listbox lbInputType;
	@Wire
	private Radiogroup rdPrimaryFlag;
	@Wire
	private Listbox lbDataType;
	@Wire
	private Radiogroup rdDuplicationFlag;
	@Wire
	private Textbox txDataFormat;
	@Wire
	private Spinner spMinLength;
	@Wire
	private Spinner spMaxLength;
	@Wire
	private Listbox lbActiveFlag;
	@Wire
	private Radiogroup rdHiddenFlag;
	@Wire
	private Radiogroup rdAllowKeyInFlag;
	@Wire
	private Radiogroup rdSearchFlag;
	@Wire
	private Radiogroup rdEnableDefaultValueFlag;
	@Wire
	private Radiogroup rdSmsFlag;
	@Wire
	private Textbox txBlindingFormat;
	@Wire
	private Textbox txDefaultblankvalue;
	
	@Wire
	private Button btSave;
	@Wire
	private Button btReset;
	@Wire
	private Button btCancel;

	private Integer billerFormId;

	private Integer billerServiceId;

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub
		String serviceID = getArgument("serviceId");
		if (!AppUtil.isEmpty(serviceID)) {
			billerServiceId = Integer.parseInt(serviceID);
		} else {
			billerServiceId = -1;
			showErrorMessage("Service ID not found!");
		}

		String formID = getArgument("formId");
		if (!AppUtil.isEmpty(formID)) {
			billerFormId = Integer.parseInt(formID);
		} else {
			billerFormId = -1;
			showErrorMessage("Form ID not found!");
		}
		setupPage();
		if (oid == -1) {
			setupNewPage();
		} else {
			setupEditPage();
		}

	}

	private void setupPage() {
		SelectBoxService selectBoxService = null;
		BillerRefDataTypeListModel<BillerRefDataType> billerRefDataTypeListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Biller service code list ## */
			billerRefDataTypeListModel = selectBoxService.getBillerRefDataTypeListModel(getUserInfo());
			billerRefDataTypeListModel.addDataToSelection(0);
			lbDataType.setModel(billerRefDataTypeListModel);

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerRefDataTypeListModel = null;
		}
	}

	private void setupNewPage() {
		Include i = null;
		try {
			chkInsertPermission(btSave);
			i = (Include) Path.getComponent("/bw1440Dialog/bw1440DialogInclude");
			title.setValue(AppMessage.getMessage(AppMessage.BW1440_NEW_TITLE));
			loadFormData(billerFormId, oid);			
			rdHiddenFlag.setSelectedIndex(1);
			rdAllowKeyInFlag.setSelectedIndex(0);
			rdSearchFlag.setSelectedIndex(1);
			rdEnableDefaultValueFlag.setSelectedIndex(1);
			rdSmsFlag.setSelectedIndex(0);
			//refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i = null;
		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			i = (Include) Path.getComponent("/bw1440Dialog/bw1440DialogInclude");
			comps = i.getFellows();
			if (chkUpdatePermission(comps, btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW1440_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW1440_VIEW_TITLE));
			}

			loadFormData(billerFormId, oid);
			refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i = null;
			comps = null;
		}
	}

	private void loadFormData(Integer billerFormId, Integer oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BillerServiceService billerServiceService = null;
		BillerForm billerForm = null;
		BillerRef billerRef = null;
		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			log.debug("billerFormId:" + billerFormId);
			billerForm = billerServiceService.getBillerFormByID(billerFormId, getUserInfo());
			bw1414Form = bw1414Form.toBW1414Form(billerForm);
			billerRef = billerServiceService.getBillerRefByID(oid, getUserInfo());
			bw1440Form = bw1440Form.toBW1440Form(billerRef);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			log.info(getStopLogMessage(pageName));
			;
		}
	}

	private void refreshForm() {
		BillerRefDataTypeListModel<BillerRefDataType> billerRefDataTypeListModel;
		try {
			intFormId.setValue(bw1414Form.getFormId());
			txFormName.setValue(bw1414Form.getFormName());
			intInputId.setValue(bw1440Form.getInputId());
			intInputSeqId.setValue(bw1440Form.getInputSeqId());
			txLabelTH.setValue(bw1440Form.getLabelTH());
			txLabelEng.setValue(bw1440Form.getLabelEng());

			if (!AppUtil.isEmpty(bw1440Form.getInputType())) {
				List<Listitem> items = lbInputType.getItems();
				for (Listitem item : items) {
					if (item.getValue().toString().equalsIgnoreCase(AppUtil.trim(bw1440Form.getInputType()))) {
						item.setSelected(true);
					}
				}
			}

			if (!AppUtil.isEmpty(bw1440Form.getPrimaryFlag()) && bw1440Form.getPrimaryFlag().equalsIgnoreCase("Y")) {
				rdPrimaryFlag.setSelectedIndex(0);
			} else {
				rdPrimaryFlag.setSelectedIndex(1);
			}

//			if (!AppUtil.isEmpty(bw1440Form.getDataType())) {
//				List<Listitem> dataTypeItems = lbDataType.getItems();
//				for (Listitem dataTypeItem : dataTypeItems) {
//					if (dataTypeItem.getValue().toString().equalsIgnoreCase(AppUtil.trim(bw1440Form.getDataType()))) {
//						dataTypeItem.setSelected(true);
//					}
//				}
//			}
			billerRefDataTypeListModel = (BillerRefDataTypeListModel)lbDataType.getModel();
			int billerRefDataTypeIndex = billerRefDataTypeListModel.findIndexByBillRefDataTypeName(bw1440Form.getDataType());
			if (billerRefDataTypeIndex > -1 && lbDataType.getItemCount() > billerRefDataTypeIndex) {
				billerRefDataTypeListModel.addDataToSelection(billerRefDataTypeIndex);
			}
			
			if (!AppUtil.isEmpty(bw1440Form.getDuplicationFlag()) && bw1440Form.getDuplicationFlag().equalsIgnoreCase("Y")) {
				rdDuplicationFlag.setSelectedIndex(0);
			} else {
				rdDuplicationFlag.setSelectedIndex(1);
			}

			txDataFormat.setValue(bw1440Form.getDataFormat());
			spMinLength.setValue(bw1440Form.getMinimumLength() == null ? 0 : bw1440Form.getMinimumLength());
			spMaxLength.setValue(bw1440Form.getMaximumLength() == null ? 0 : bw1440Form.getMaximumLength());

			if (!AppUtil.isEmpty(bw1440Form.getActiveFlag()) && bw1440Form.getActiveFlag().equalsIgnoreCase("I")) {
				lbActiveFlag.setSelectedIndex(1);
			} else {
				lbActiveFlag.setSelectedIndex(0);
			}
			if (!AppUtil.isEmpty(bw1440Form.getHiddenFlag()) && bw1440Form.getHiddenFlag().equalsIgnoreCase("Y")) {
				rdHiddenFlag.setSelectedIndex(0);
			} else {
				rdHiddenFlag.setSelectedIndex(1);
			}
			if (!AppUtil.isEmpty(bw1440Form.getAllowKeyInFlag()) && bw1440Form.getAllowKeyInFlag().equalsIgnoreCase("Y")) {
				rdAllowKeyInFlag.setSelectedIndex(0);
			} else {
				rdAllowKeyInFlag.setSelectedIndex(1);
			}
			if (!AppUtil.isEmpty(bw1440Form.getSearchFlag()) && bw1440Form.getSearchFlag().equalsIgnoreCase("Y")) {
				rdSearchFlag.setSelectedIndex(0);
			} else {
				rdSearchFlag.setSelectedIndex(1);
			}
			if (!AppUtil.isEmpty(bw1440Form.getEnableDefaultValueFlag()) && bw1440Form.getEnableDefaultValueFlag().equalsIgnoreCase("Y")) {
				rdEnableDefaultValueFlag.setSelectedIndex(0);
			} else {
				rdEnableDefaultValueFlag.setSelectedIndex(1);
			}
			if (!AppUtil.isEmpty(bw1440Form.getSmsFlag()) && bw1440Form.getSmsFlag().equalsIgnoreCase("Y")) {
				rdSmsFlag.setSelectedIndex(0);
			} else {
				rdSmsFlag.setSelectedIndex(1);
			}
			txBlindingFormat.setValue(bw1440Form.getBlindingFormat());
			txDefaultblankvalue.setValue(bw1440Form.getDefaultblankvalue());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	public void updateBW1440Form() {
		try {
			bw1440Form.setBillerServiceId(billerServiceId);
			bw1440Form.setFormId(intFormId.getValue());
			bw1440Form.setInputId(intInputId.getValue());
			bw1440Form.setInputSeqId(intInputSeqId.getValue());
			bw1440Form.setLabelTH(txLabelTH.getValue());
			bw1440Form.setLabelEng(txLabelEng.getValue());
			bw1440Form.setInputType(lbInputType.getSelectedItem().getValue().toString());
			bw1440Form.setPrimaryFlag(rdPrimaryFlag.getSelectedItem().getValue().toString());
			BillerRefDataType billerRefDataType = lbDataType.getSelectedItem().getValue();
			log.debug("BillerRefDataType:"+billerRefDataType);
			bw1440Form.setDataType(billerRefDataType.getBILL_REF_DATA_TYPE_NAME());
			bw1440Form.setDuplicationFlag(rdDuplicationFlag.getSelectedItem().getValue().toString());
			bw1440Form.setDataFormat(txDataFormat.getValue());
			bw1440Form.setMinimumLength(spMinLength.getValue());
			bw1440Form.setMaximumLength(spMaxLength.getValue());
			bw1440Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());		
			bw1440Form.setHiddenFlag(rdHiddenFlag.getSelectedItem().getValue().toString());
			bw1440Form.setAllowKeyInFlag(rdAllowKeyInFlag.getSelectedItem().getValue().toString());
			bw1440Form.setSearchFlag(rdSearchFlag.getSelectedItem().getValue().toString());
			bw1440Form.setEnableDefaultValueFlag(rdEnableDefaultValueFlag.getSelectedItem().getValue().toString());
			bw1440Form.setSmsFlag(rdSmsFlag.getSelectedItem().getValue().toString());
			bw1440Form.setBlindingFormat(txBlindingFormat.getValue());
			bw1440Form.setDefaultblankvalue(txDefaultblankvalue.getValue());
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
			log.info(getOperationLogMessage(pageName, "Save Biller Service/Popup Input", ""));
			validateLabelThai();
			validateLabelEnglish();
			updateBW1440Form();
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
			log.error(getErrorLogMessage(pageName, "Save Biller Service/Popup Input", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {
			log.info(getStopLogMessage(pageName));
			;
		}
	}

	private void save() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Save " + "Popup Input");
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerRef billerRef = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerRef = bw1440Form.toBillerRef();
						billerRef.setCRTD_BY(getUserName());
						billerRef.setLAST_CHNG_BY(getUserName());
						oid = billerServiceService.saveBillerRef(billerRef, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									processSaveInDialogMode();
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Popup Input", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("REF_ID")) {
							Validation.isExist(intFormId, true, AppUtil.toString(oid));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Popup Input", getErrorParames(), ex));
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1414_REDIRECT_PATH))) {
				Page pBw1414 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1414");
				log.info("Parent Page:" + pBw1414);
				Grid gdResult = null;
				if (pBw1414 != null) {
					gdResult = (Grid) pBw1414.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw1414Dialog/bw1414DialogInclude");
					gdResult = (Grid) i.getFellow("gdResult");
				}
				if (pBw1414 != null) {
					Event onRefresh = new Event("onRefresh", gdResult);
					Events.sendEvent(onRefresh);
				}
			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1415_REDIRECT_PATH))) {
				Page pBw1415 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1415");
				log.info("Parent Page:" + pBw1415);
				Grid gdBarcode = null;
				Grid gdDetail = null;
				if (pBw1415 != null) {
					gdBarcode = (Grid) pBw1415.getFellow("gdBarcode");
					gdDetail = (Grid) pBw1415.getFellow("gdDetail");
				} else {
					Include i = (Include) Path.getComponent("/bw1415Dialog/bw1415DialogInclude");
					gdBarcode = (Grid) i.getFellow("gdBarcode");
					gdDetail = (Grid) i.getFellow("gdDetail");
				}
				if (pBw1415 != null) {

					Event onRefreshBarcode = new Event("onRefresh", gdBarcode);
					Events.sendEvent(onRefreshBarcode);
					Event onRefreshDetail = new Event("onRefresh", gdDetail);
					Events.sendEvent(onRefreshDetail);
				}
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

	private void update() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Update " + "Popup Input");
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerRef billerRef = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerRef = bw1440Form.toBillerRef();
						billerRef.setLAST_CHNG_BY(getUserName());
						billerServiceService.updateBillerRef(billerRef, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("update OK");
									processUpdateInDialogMode();
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Update Service/Popup Input", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("REF_ID")) {
							Validation.isExist(intFormId, true, AppUtil.toString(oid));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Service/Popup Input", getErrorParames(), ex));
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1414_REDIRECT_PATH))) {
				Page pBw1414 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1414");
				log.info("Parent Page:" + pBw1414);
				Grid gdResult = null;
				if (pBw1414 != null) {
					gdResult = (Grid) pBw1414.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw1414Dialog/bw1414DialogInclude");
					gdResult = (Grid) i.getFellow("gdResult");
				}
				if (pBw1414 != null) {
					Event onRefresh = new Event("onRefresh", gdResult);
					Events.sendEvent(onRefresh);
				}
			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1415_REDIRECT_PATH))) {
				Page pBw1415 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1415");
				log.info("Parent Page:" + pBw1415);
				Grid gdBarcode = null;
				Grid gdDetail = null;
				if (pBw1415 != null) {
					gdBarcode = (Grid) pBw1415.getFellow("gdBarcode");
					gdDetail = (Grid) pBw1415.getFellow("gdDetail");
				} else {
					Include i = (Include) Path.getComponent("/bw1415Dialog/bw1415DialogInclude");
					gdBarcode = (Grid) i.getFellow("gdBarcode");
					gdDetail = (Grid) i.getFellow("gdDetail");
				}
				if (pBw1415 != null) {
					Event onRefreshBarcode = new Event("onRefresh", gdBarcode);
					Events.sendEvent(onRefreshBarcode);
					Event onRefreshDetail = new Event("onRefresh", gdDetail);
					Events.sendEvent(onRefreshDetail);
				}
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
			params.put("Form ID.", bw1414Form.getFormId() + "");
			params.put("Service ID.", bw1414Form.getBillerServiceId() + "");
			params.put("Input ID", bw1440Form.getInputId() + "");
			params.put("Form Name", bw1414Form.getFormName());
			params.put("Input Seq.ID.", bw1440Form.getInputSeqId() + "");
			params.put("Label Thai", bw1440Form.getLabelTH() + "");
			params.put("Label English", bw1440Form.getLabelEng() + "");
			params.put("Input Type", bw1440Form.getInputType() + "");
			params.put("Primary Flag", bw1440Form.getPrimaryFlag() + "");
			params.put("Data Type", bw1440Form.getDataType() + "");
			params.put("Duplication Flag", bw1440Form.getDuplicationFlag() + "");
			params.put("Data Format", bw1440Form.getDataFormat() + "");
			params.put("Minimum Length", bw1440Form.getMinimumLength() + "");
			params.put("Maximum Length", bw1440Form.getMaximumLength() + "");
			params.put("Status", bw1440Form.getActiveFlag() + "");			
			params.put("Hidden Flag", bw1440Form.getHiddenFlag() + "");
			params.put("Allow Key-in Flag", bw1440Form.getAllowKeyInFlag() + "");
			params.put("Blinding Format", bw1440Form.getBlindingFormat() + "");
			params.put("Search Flag", bw1440Form.getSearchFlag() + "");
			params.put("Enable Default Value Flag", bw1440Form.getEnableDefaultValueFlag() + "");
			params.put("Default Blank Value", bw1440Form.getDefaultblankvalue() + "");
			params.put("SMS Flag", bw1440Form.getSmsFlag() + "");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	@Listen("onClick = #btReset")
	public void reset() {
		log.info("Click Reset Edit Biller Service/Infomation");
		EventListener resetConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {
						bw1440Form.setInputSeqId(null);
						bw1440Form.setLabelTH(null);
						bw1440Form.setLabelEng(null);
						bw1440Form.setInputType("add.ref1");
						bw1440Form.setPrimaryFlag("N");
						bw1440Form.setDataType("String");
						bw1440Form.setDuplicationFlag("N");
						bw1440Form.setDataFormat(null);
						bw1440Form.setMinimumLength(0);
						bw1440Form.setMaximumLength(0);
						bw1440Form.setActiveFlag("A");					
						bw1440Form.setHiddenFlag("N");
						bw1440Form.setAllowKeyInFlag("Y");
						bw1440Form.setSearchFlag("N");
						bw1440Form.setEnableDefaultValueFlag("N");
						bw1440Form.setSmsFlag("Y");
						bw1440Form.setBlindingFormat(null);
						bw1440Form.setDefaultblankvalue(null);
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
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			parentWindow = (Window) parent;
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}

	@Listen("onSelect = #lbDataType")
	public void onSelectDataType() {
		log.info(getOperationLogMessage(pageName, "onSelectDataType", ""));
		BillerRefDataType billerRefDataType = null;
		try {
			if (oid == -1) {
				billerRefDataType = lbDataType.getSelectedItem().getValue();
				txDataFormat.setValue(billerRefDataType.getBILL_REF_DATA_TYPE_FOMT());
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectDataType", ex));
			showErrorMessage(ex);
		}
	}

	/* ## Validation ## */
	@Listen("onBlur=#txLabelTH")
	public void onBlurLabelTh() {
		log.info(getOperationLogMessage(pageName, "onBlurLabelTh", ""));
		try {
			validateLabelThai();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	@Listen("onBlur=#txLabelEng")
	public void onBlurLabelEng() {
		log.info(getOperationLogMessage(pageName, "onBlurLabelEng", ""));
		try {
			validateLabelEnglish();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	private void validateLabelThai() throws WrongValueException, Exception {
		Validation.isNotEnglishChar(txLabelTH, txLabelTH.getValue());
	}

	private void validateLabelEnglish() throws WrongValueException, Exception {
		Validation.isNotThaiChar(txLabelEng, txLabelEng.getValue());
	}

}
