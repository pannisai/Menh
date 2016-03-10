package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BillerBarcode;
import mfs.biller.persistence.bean.BillerBarcodeRef;
import mfs.biller.persistence.bean.BillerRefDetail;
import mfs.exception.IsExistException;

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
import org.zkoss.zk.ui.util.Clients;
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
import com.dtac.billerweb.common.Dialog;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.exception.DialogException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1450Form;
import com.dtac.billerweb.listmodel.selectbox.BillerBarcodeListModel;
import com.dtac.billerweb.service.BillerServiceService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1450Controller extends EditPageController {

	public BW1450Controller() {
		super("Biller Service/Popup Barcode","1450");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW1450Controller.class);
	private BW1450Form bw1450Form = new BW1450Form();
	@Wire
	private Label title;
	@Wire
	private Intbox intInputId;
	@Wire
	private Textbox txInputName;
	@Wire
	private Textbox txLabelTH;
	@Wire
	private Textbox txLabelEng;
	@Wire
	private Intbox intBarcodeRefId;
	@Wire
	private Intbox intBarcodeLineId;
	@Wire
	private Listbox lbBarcodeType;
	@Wire
	private Label lBarcodeType;
	@Wire
	private Radiogroup rdPartialFlag;
	@Wire
	private Textbox txRemoveChar;
	@Wire
	private Spinner spPartialStart;
	@Wire
	private Listbox lbActiveFlag;
	@Wire
	private Spinner spPartialLength;
	@Wire
	private Button btSave;
	@Wire
	private Button btReset;
	@Wire
	private Button btCencel;

	private Integer billerServiceId;
	private Integer inputRefId;

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
		String inputRefIdStr = getArgument("inputRefId");
		if (!AppUtil.isEmpty(inputRefIdStr)) {
			inputRefId = Integer.parseInt(inputRefIdStr);
		} else {
			inputRefId = -1;
			showErrorMessage("Input Ref ID not found!");
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
			loadBarcodeTypeList();

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave);
			title.setValue(AppMessage.getMessage(AppMessage.BW1450_NEW_TITLE));
			loadFormData(inputRefId, oid);
			refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			 i = (Include) Path.getComponent("/bw1450Dialog/bw1450DialogInclude");
			 comps=i.getFellows();
			if (chkUpdatePermission(comps,new Listbox[]{lbBarcodeType},btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW1450_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW1450_VIEW_TITLE));				
			}

			loadFormData(inputRefId, oid);
			refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i=null;
			comps=null;
		}
	}

	private void loadBarcodeTypeList() {
		log.info(getOperationLogMessage(pageName, "Load BarcodeTypeList", ""));
		SelectBoxService selectBoxService = null;
		BillerBarcodeListModel billerBarcodeListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Biller Barcode list ## */
			billerBarcodeListModel = selectBoxService.getBillerBarcodeListModel(getUserInfo());
			billerBarcodeListModel.addItemSelect();
			billerBarcodeListModel.addItemCreateNew();
			lbBarcodeType.setModel(billerBarcodeListModel);

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerBarcodeListModel = null;
			log.info(getStopLogMessage(pageName));
		}
	}

	private void loadFormData(Integer inputRefId, Integer oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BillerServiceService billerServiceService = null;
		BillerRefDetail billerRefDetail = null;
		BillerBarcodeRef billerBarcodeRef = null;
		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			log.debug("inputRefId:" + inputRefId);
			billerRefDetail = billerServiceService.getBillerRefDetailByID(inputRefId, getUserInfo());
			billerBarcodeRef = billerServiceService.getBillerBarcodeRefByID(oid, getUserInfo());
			bw1450Form = bw1450Form.toBW1450Form(billerRefDetail, billerBarcodeRef);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
	}

	private void refreshForm() {
		BillerBarcodeListModel<BillerBarcode> billerBarcodeListModel = null;
		try {
			intInputId.setValue(bw1450Form.getInputId());
			txInputName.setValue(bw1450Form.getFormName());
			txLabelTH.setValue(bw1450Form.getLabelTH());
			txLabelEng.setValue(bw1450Form.getLabelEng());
			intBarcodeRefId.setValue(bw1450Form.getBarcodeRefId());
			intBarcodeLineId.setValue(bw1450Form.getBarcodeLineId());

			billerBarcodeListModel = (BillerBarcodeListModel) lbBarcodeType.getModel();
			int barcodeTypeIndex = billerBarcodeListModel.findIndexOfId(bw1450Form.getBarcodeTypeId());
			if (barcodeTypeIndex > -1 && lbBarcodeType.getItemCount() > barcodeTypeIndex) {
				billerBarcodeListModel.addDataToSelection(barcodeTypeIndex);
			} else {
				billerBarcodeListModel.addDataToSelection(0);
			}

			if (!AppUtil.isEmpty(bw1450Form.getPartialFlag()) && bw1450Form.getPartialFlag().equalsIgnoreCase("N")) {
				rdPartialFlag.setSelectedIndex(1);
			} else {
				rdPartialFlag.setSelectedIndex(0);
			}
			txRemoveChar.setValue(bw1450Form.getRemoveChar());
			spPartialStart.setValue(bw1450Form.getPartialStart() == null ? 0 : bw1450Form.getPartialStart());
			spPartialLength.setValue(bw1450Form.getPartialLength() == null ? 0 : bw1450Form.getPartialLength());

			if (!AppUtil.isEmpty(bw1450Form.getActiveFlag()) && bw1450Form.getActiveFlag().equalsIgnoreCase("I")) {
				lbActiveFlag.setSelectedIndex(1);
			} else {
				lbActiveFlag.setSelectedIndex(0);
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	public void updateBW1450Form() {
		try {
			bw1450Form.setInputId(intInputId.getValue());
			bw1450Form.setFormName(txInputName.getValue());
			bw1450Form.setLabelTH(txLabelTH.getValue());
			bw1450Form.setLabelEng(txLabelEng.getValue());
			bw1450Form.setBarcodeRefId(intBarcodeRefId.getValue());
			bw1450Form.setBarcodeLineId(intBarcodeLineId.getValue());
			BillerBarcode billerBarcode = lbBarcodeType.getSelectedItem().getValue();
			bw1450Form.setBarcodeTypeId(billerBarcode.getBARC_ID());
			bw1450Form.setPartialFlag(rdPartialFlag.getSelectedItem().getValue().toString());
			bw1450Form.setRemoveChar(txRemoveChar.getValue());
			bw1450Form.setPartialStart(spPartialStart.getValue());
			bw1450Form.setPartialLength(spPartialLength.getValue());
			bw1450Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
		}catch(WrongValueException wve){
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
			log.info(getOperationLogMessage(pageName, "Save Biller Service/Popup Barcode", ""));
			validateBarcodeType();
			updateBW1450Form();
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
			log.error(getErrorLogMessage(pageName, "Save Biller Service/Popup Barcode", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
	}

	private void save() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Save " + "Popup Barcode");
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerBarcodeRef billerBarcodeRef = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerBarcodeRef = bw1450Form.toBillerBarcodeRef();
						billerBarcodeRef.setCRTD_BY(getUserName());
						billerBarcodeRef.setLAST_CHNG_BY(getUserName());
						oid = billerServiceService.saveBillerBarcodeRef(billerBarcodeRef, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Popup Barcode", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BARC_REF_ID")) {
							Validation.isExist(intBarcodeRefId, true, AppUtil.toString(intBarcodeRefId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Biller Service/Popup Barcode", getErrorParames(), ex));
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1415_REDIRECT_PATH))) {
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
		checkSessionTimeOut("Update " + "Popup Barcode");
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerBarcodeRef billerBarcodeRef = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerBarcodeRef = bw1450Form.toBillerBarcodeRef();
						billerBarcodeRef.setLAST_CHNG_BY(getUserName());
						billerServiceService.updateBillerBarcodeRef(billerBarcodeRef, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Update Service/Popup Barcode", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BARC_REF_ID")) {
							Validation.isExist(intBarcodeRefId, true, AppUtil.toString(intBarcodeRefId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Service/Popup Barcode", getErrorParames(), ex));
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1415_REDIRECT_PATH))) {
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
			params.put("Input ID.", bw1450Form.getInputId() + "");
			params.put("Input Name.", bw1450Form.getFormName() + "");
			params.put("Label Thai", bw1450Form.getLabelTH() + "");
			params.put("Label English", bw1450Form.getLabelEng() + "");
			params.put("Barcode Ref ID.", bw1450Form.getBarcodeRefId() + "");
			params.put("Barcode Line ID.", bw1450Form.getBarcodeLineId() + "");
			params.put("Barcode Type", bw1450Form.getBarcodeTypeId() + "");
			params.put("Partial Flag", bw1450Form.getPartialFlag() + "");
			params.put("Remove Character", bw1450Form.getRemoveChar() + "");
			params.put("Partial Start", bw1450Form.getPartialStart() + "");
			params.put("Partial Length", bw1450Form.getPartialLength() + "");
			params.put("Status", bw1450Form.getActiveFlag() + "");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	@Listen("onClick = #btReset")
	public void reset() {
		log.info(getOperationLogMessage(pageName, "Reset", ""));
		EventListener resetConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {
						bw1450Form.setBarcodeLineId(null);
						bw1450Form.setBarcodeTypeId(null);
						bw1450Form.setPartialFlag("Y");
						bw1450Form.setRemoveChar(null);
						bw1450Form.setPartialStart(null);
						bw1450Form.setPartialLength(null);
						bw1450Form.setActiveFlag("A");
						refreshForm();
					} catch (Exception ex) {
					
						log.error(getErrorLogMessage(pageName, "Reset", ex));
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

	/* ## Biller Barcode Code Type Event ## */
	@Listen("onAfterRender=#lbBarcodeType")
	public void onAfterRenderBarcodeType(Event even) {
		try {
			Listitem createNew = lbBarcodeType.getItemAtIndex(lbBarcodeType.getItemCount() - 1);
			createNew.setSclass("listboxFooter");
		} catch (Exception ex) {

		}
	}

	@Listen("onSelect = #lbBarcodeType")
	public void onSelectBarcodeType() {
		log.info(getOperationLogMessage(pageName, "onSelectBarcodeType", ""));
		try {
			Clients.clearWrongValue(lbBarcodeType);
			/* ## Create New ## */
			BillerBarcode billerBarcode = lbBarcodeType.getSelectedItem().getValue();
			if (AppUtil.isNotNull(billerBarcode.getBARC_ID()) && billerBarcode.getBARC_ID() == -1) {
				processCreateNewBillerBarcode();
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectBarcodeType", ex));
			showErrorMessage(ex);
		}
		log.info(getStopLogMessage(pageName));
	}

	private void processCreateNewBillerBarcode() throws Exception {
		Dialog.openBw1510MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1450_REDIRECT_PATH), "-1");
	}

	@Listen("onRefresh = #lbBarcodeType")
	public void refreshBarcodeType() {
		log.info(getOperationLogMessage(pageName, "refreshBarcodeType", ""));
		try {
			loadBarcodeTypeList();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "refreshBarcodeType", ex));
			showErrorMessage(ex);
		}
	}

	/* ## Validation ## */
	private void validateBarcodeType() throws WrongValueException, Exception {
		BillerBarcode value = lbBarcodeType.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbBarcodeType, value.getBARC_ID(), lBarcodeType.getValue());
	}
}
