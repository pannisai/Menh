package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BillerMaster;
import mfs.biller.persistence.bean.GWService;
import mfs.biller.persistence.bean.GWServiceMap;
import mfs.biller.persistence.bean.OutboundId;
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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
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
import com.dtac.billerweb.form.BW2210Form;
import com.dtac.billerweb.listmodel.selectbox.BillerMasterListModel;
import com.dtac.billerweb.listmodel.selectbox.MFSServiceIdListModel;
import com.dtac.billerweb.listmodel.selectbox.OutboundIdListModel;
import com.dtac.billerweb.listmodel.selectbox.ServiceMapIdListModel;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.service.ServiceGatewayService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW2210Controller extends EditPageController {
	public BW2210Controller() {
		super("Edit Service","2210");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW2210Controller.class);
	private BW2210Form bw2210Form = new BW2210Form();
	@WireVariable
	private Page updatePage;
	@Wire
	private Label title;
	@Wire
	private Intbox intServiceId;
	@Wire
	private Textbox txServiceName;
	@Wire
	private Label lServiceName;
	@Wire
	private Listbox lbServiceMapId;
	@Wire
	private Label lServiceMapId;
	@Wire
	private Button btEditServiceMapId;
	@Wire
	private Listbox lbOutboundId;
	@Wire
	private Label lOutboundId;
	@Wire
	private Button btEditOutboundId;
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
		setupPage();
		if (oid == -1) {
			setupNewPage();
		} else {
			setupEditPage();
		}
	}

	private void setupPage() {
		SelectBoxService selectBoxService = null;
		OutboundIdListModel<OutboundId> outboundIdListModel = null;
		ServiceMapIdListModel<GWServiceMap> serviceMapIdListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Service Map id list ## */
			serviceMapIdListModel = selectBoxService.getServiceMapIdListModel();
			serviceMapIdListModel.addItemSelect();
			serviceMapIdListModel.addItemCreateNew();
			lbServiceMapId.setModel(serviceMapIdListModel);
			/* ## Set Outbound id list ## */
			outboundIdListModel = selectBoxService.getOutboundIdListModel();
			outboundIdListModel.addItemSelect();
			outboundIdListModel.addItemCreateNew();
			lbOutboundId.setModel(outboundIdListModel);

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			outboundIdListModel = null;
		}
	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave,btEditOutboundId,btEditServiceMapId);
			title.setValue(AppMessage.getMessage(AppMessage.BW2210_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			 i = (Include) Path.getComponent("/bw2210Dialog/bw2210DialogInclude");
			 comps =i!=null? i.getFellows():updatePage.getFellows();
			if (chkUpdatePermission(comps,new Listbox[]{lbServiceMapId,lbOutboundId},new Button[]{btEditOutboundId,btEditServiceMapId}, btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW2210_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW2210_VIEW_TITLE));				
			}
			loadFormData(oid);
			refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i=null;
			comps=null;
		}
	}

	private void loadFormData(Integer oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		ServiceGatewayService serviceGatewayService = null;
		GWService serviceGateway = null;
		try {
			serviceGatewayService = BillerwebServiceFactory.getServiceGatewayService();
			serviceGateway = serviceGatewayService.getByID(oid, getUserInfo());
			bw2210Form = bw2210Form.toBW2210Form(serviceGateway);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Load Data", "OID::" + oid, ex));
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		OutboundIdListModel<OutboundId> outboundIdListModel = null;
		ServiceMapIdListModel<GWServiceMap> serviceMapIdListModel = null;

		try {
			intServiceId.setValue(bw2210Form.getServiceId());
			txServiceName.setValue(bw2210Form.getServiceName());
			try {
				serviceMapIdListModel = (ServiceMapIdListModel) lbServiceMapId.getModel();
				int serviceMapIdIndex = serviceMapIdListModel.findIndexOfId(bw2210Form.getServiceMapId());
				if (serviceMapIdIndex != -1 && lbServiceMapId.getItemCount() > serviceMapIdIndex) {
					serviceMapIdListModel.addDataToSelection(serviceMapIdIndex);
				} else if (lbServiceMapId.getItemCount() > 0) {
					serviceMapIdListModel.addDataToSelection(0);
				}

				outboundIdListModel = (OutboundIdListModel) lbOutboundId.getModel();
				int outBoundIndex = outboundIdListModel.findIndexOfId(bw2210Form.getOutboundId());
				if (outBoundIndex != -1 && lbOutboundId.getItemCount() > outBoundIndex) {
					outboundIdListModel.addDataToSelection(outBoundIndex);
				} else if (lbOutboundId.getItemCount() > 0) {
					outboundIdListModel.addDataToSelection(0);
				}

				if (bw2210Form.getActiveFlag().equalsIgnoreCase("A")) {
					lbActiveFlag.setSelectedIndex(0);
				} else {
					lbActiveFlag.setSelectedIndex(1);
				}
			} catch (NullPointerException npe) {
				lbActiveFlag.setSelectedIndex(0);
			}

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			outboundIdListModel = null;
			serviceMapIdListModel = null;
		}
	}

	private void updateBW2110Form() {
		OutboundId outboundId = null;
		GWServiceMap gwServiceMap = null;
		try {
			bw2210Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
			bw2210Form.setServiceId(intServiceId.getValue());
			bw2210Form.setServiceName(txServiceName.getValue());
			outboundId = lbOutboundId.getSelectedItem().getValue();
			bw2210Form.setOutboundId(outboundId.getGW_OUTB_ID());
			gwServiceMap = lbServiceMapId.getSelectedItem().getValue();
			bw2210Form.setServiceMapId(gwServiceMap.getDATA_MAP_ID());
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			outboundId = null;
		}
	}

	/* ## Event ## */
	@Listen("onClick = #btSave")
	public void clickSave() {
		try {
			log.info(getOperationLogMessage(pageName, "Save Service Gateway", ""));
			updateBW2110Form();
			validateServiceName();
			validateServiceMapId();
			validateOutboundId();
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
			log.error(getErrorLogMessage(pageName, "Save Service Gateway", getErrorParames(), ex));
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
					ServiceGatewayService serviceGatewayService = null;
					GWService serviceGateway = null;
					try {
						serviceGatewayService = BillerwebServiceFactory.getServiceGatewayService();
						serviceGateway = bw2210Form.toServiceGateway();
						serviceGateway.setCRTD_BY(getUserName());
						serviceGateway.setLAST_CHNG_BY(getUserName());
						final Integer oid = serviceGatewayService.save(serviceGateway, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									if (isDialog()) {
										processSaveInDialogMode();
									} else {
										redirect(AppConfiguration.getValue(AppConfiguration.BW2210_REDIRECT_PATH) + "?oid=" + oid);
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Service Gateway", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("GW_SRVC_ID")) {
							Validation.isExist(intServiceId, true, AppUtil.toString(intServiceId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Service Gateway", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						serviceGatewayService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Save", requestDate, "");
	}

	private void processSaveInDialogMode() {
		SelectBoxService selectBoxService = null;
		Component parent = null;
		Window parentWindow = null;
		MFSServiceIdListModel mfsServiceIdListModel = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW2110_REDIRECT_PATH))) {
				Page pBw2110 = Executions.getCurrent().getDesktop().getPageIfAny("pBw2110");
				log.info("Page:" + pBw2110);
				Listbox lbMFSServiceId = null;
				if (pBw2110 != null) {
					lbMFSServiceId = (Listbox) pBw2110.getFellow("lbMFSServiceId");
				} else {
					Include i = (Include) Path.getComponent("/bw2110Dialog/bw2110DialogInclude");
					lbMFSServiceId = (Listbox) i.getFellow("lbMFSServiceId");
				}

				mfsServiceIdListModel = selectBoxService.getMFSServiceIdListModel();
				mfsServiceIdListModel.addItemSelect();
				mfsServiceIdListModel.addItemCreateNew();
				lbMFSServiceId.setModel(mfsServiceIdListModel);
			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1411_REDIRECT_PATH))) {
				Page pBw1411 = Executions.getCurrent().getDesktop().getPageIfAny("pBw1411");
				log.info("Page:" + pBw1411);
				Listbox lbBillerMasterId = null;
				if (pBw1411 != null) {
					lbBillerMasterId = (Listbox) pBw1411.getFellow("lbBillerMasterId");
				} else {
					Include i = (Include) Path.getComponent("/bw1411Dialog/bw1411DialogInclude");
					lbBillerMasterId = (Listbox) i.getFellow("lbBillerMasterId");
				}

				BillerMasterListModel<BillerMaster> billerMasterListModel = selectBoxService.getBillerCodeListModel();
				billerMasterListModel.addItemSelect();
				billerMasterListModel.addItemCreateNew();
				lbBillerMasterId.setModel(billerMasterListModel);
			}
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "ProcessSaveInDialogMode", "[Parrent:" + parent + ",Callback:" + callback, ex));
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			parent = null;
			parentWindow = null;
			mfsServiceIdListModel = null;
		}
	}

	private void update() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Update " + pageName);
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					ServiceGatewayService serviceGatewayService = null;
					GWService serviceGateway = null;
					try {
						serviceGatewayService = BillerwebServiceFactory.getServiceGatewayService();
						serviceGateway = bw2210Form.toServiceGateway();
						serviceGateway.setLAST_CHNG_BY(getUserName());
						serviceGatewayService.update(serviceGateway, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Update Service Gateway", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("GW_SRVC_ID")) {
							Validation.isExist(intServiceId, true, AppUtil.toString(intServiceId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Service Gateway", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						serviceGatewayService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		log.info(getStopLogMessage(pageName));
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW2110_REDIRECT_PATH))) {
				Page pBw2110 = Executions.getCurrent().getDesktop().getPageIfAny("pBw2110");
				log.info("Page:" + pBw2110);
				Listbox lbMFSServiceId = null;
				if (pBw2110 != null) {
					lbMFSServiceId = (Listbox) pBw2110.getFellow("lbMFSServiceId");
				} else {
					Include i = (Include) Path.getComponent("/bw2110Dialog/bw2110DialogInclude");
					lbMFSServiceId = (Listbox) i.getFellow("lbMFSServiceId");
				}
				int currentMFsServiceId = lbMFSServiceId.getSelectedIndex();
				MFSServiceIdListModel mfsServiceIdListModel = selectBoxService.getMFSServiceIdListModel();
				mfsServiceIdListModel.addItemSelect();
				mfsServiceIdListModel.addItemCreateNew();
				mfsServiceIdListModel.addDataToSelection(currentMFsServiceId);
				lbMFSServiceId.setModel(mfsServiceIdListModel);
			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW1411_REDIRECT_PATH))) {
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
				BillerMasterListModel<BillerMaster> billerMasterListModel = selectBoxService.getBillerCodeListModel();
				billerMasterListModel.addItemSelect();
				billerMasterListModel.addItemCreateNew();
				billerMasterListModel.addDataToSelection(currentBillerMaster);
				lbBillerMasterId.setModel(billerMasterListModel);
			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW2200_REDIRECT_PATH))) {
				Page pBw2200 = Executions.getCurrent().getDesktop().getPageIfAny("pBw2200");
				log.info("Page:" + pBw2200);
				Grid gdResult = null;
				if (pBw2200 != null) {
					gdResult = (Grid) pBw2200.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw2210Dialog/bw2210DialogInclude");
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
		}
	}

	private Map<String, String> getErrorParames() {
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("Service ID", bw2210Form.getServiceId() + "");
			params.put("Service Name", bw2210Form.getServiceName());
			params.put("Servicef Map ID", bw2210Form.getServiceMapId() + "");
			params.put("Outbound ID", bw2210Form.getOutboundId() + "");
			params.put("ActiveFlag", bw2210Form.getActiveFlag());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	@Listen("onClick = #btReset")
	public void reset() {
		log.info("Click Reset Edit Biller Channel");
		EventListener resetConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {
						bw2210Form.setActiveFlag("A");
						bw2210Form.setServiceName(null);
						bw2210Form.setServiceMapId(null);
						bw2210Form.setOutboundId(null);
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
				redirect(AppConfiguration.getValue(AppConfiguration.BW2200_REDIRECT_PATH));
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}

	/* ## Service Map ID Event ## */
	@Listen("onSelect = #lbServiceMapId")
	public void onSelectServiceMapId() {
		log.info(getOperationLogMessage(pageName, "onSelectServiceMapId", ""));
		try {
			Clients.clearWrongValue(lbServiceMapId);
			/* ## Create New ## */
			GWServiceMap gwServiceMap = lbServiceMapId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(gwServiceMap.getDATA_MAP_ID()) && gwServiceMap.getDATA_MAP_ID() == -1) {
				processCreateNewServiceMapId();
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectServiceMapId", ex));
			showErrorMessage(ex);
		}
	}

	private void processCreateNewServiceMapId() throws Exception {
		Dialog.openBw2230MedalDialog(AppConfiguration.getValue(AppConfiguration.BW2210_REDIRECT_PATH), -1);
	}

	@Listen("onClick = #btEditServiceMapId")
	public void editServiceMapId() {

		log.info(getOperationLogMessage(pageName, "editServiceMapId", ""));
		GWServiceMap gwServiceMap = new GWServiceMap();
		try {
			gwServiceMap = lbServiceMapId.getSelectedItem().getValue();
			if (gwServiceMap.getDATA_MAP_ID() != null && gwServiceMap.getDATA_MAP_ID() != -1) {
				Dialog.openBw2230MedalDialog(AppConfiguration.getValue(AppConfiguration.BW2210_REDIRECT_PATH), gwServiceMap.getDATA_MAP_ID());
			} else {
				Validation.isSelectDropdownList(lbServiceMapId, null, lServiceMapId.getValue());
			}
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "editServiceMapId", "OID:" + gwServiceMap.getDATA_MAP_ID(), ex));
			showErrorMessage(ex);
		} finally {
			gwServiceMap = null;
		}
	}

	@Listen("onAfterRender=#lbServiceMapId")
	public void onAfterRenderServiceMapId(Event even) {
		try {
			Listitem createNew = lbServiceMapId.getItemAtIndex(lbServiceMapId.getItemCount() - 1);
			createNew.setSclass("listboxFooter");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	/* ## Outbound ID Event ## */
	@Listen("onSelect = #lbOutboundId")
	public void onSelectOutboundId() {
		log.info(getOperationLogMessage(pageName, "onSelectOutboundId", ""));
		try {
			Clients.clearWrongValue(lbOutboundId);
			/* ## Create New ## */
			OutboundId outboundId = lbOutboundId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(outboundId.getGW_OUTB_ID()) && outboundId.getGW_OUTB_ID() == -1) {
				processCreateNewOutboundId();
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectOutboundId", ex));
			showErrorMessage(ex);
		}
	}

	private void processCreateNewOutboundId() throws Exception {
		Dialog.openBw2310MedalDialog(AppConfiguration.getValue(AppConfiguration.BW2210_REDIRECT_PATH), -1);
	}

	@Listen("onClick = #btEditOutboundId")
	public void editOutboundId() {

		log.info(getOperationLogMessage(pageName, "editOutboundId", ""));
		OutboundId outboundId = new OutboundId();
		try {
			outboundId = lbOutboundId.getSelectedItem().getValue();
			if (outboundId.getGW_OUTB_ID() != null && outboundId.getGW_OUTB_ID() != -1) {
				Dialog.openBw2310MedalDialog(AppConfiguration.getValue(AppConfiguration.BW2210_REDIRECT_PATH), outboundId.getGW_OUTB_ID());
			} else {
				Validation.isSelectDropdownList(lbOutboundId, null, lOutboundId.getValue());
			}
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "editOutboundId", "OID:" + outboundId.getGW_OUTB_ID(), ex));
			showErrorMessage(ex);
		} finally {
			outboundId = null;
		}
	}

	@Listen("onAfterRender=#lbOutboundId")
	public void onAfterRenderOutboundId(Event even) {
		try {
			Listitem createNew = lbOutboundId.getItemAtIndex(lbOutboundId.getItemCount() - 1);
			createNew.setSclass("listboxFooter");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	/* ## Validation ## */
	private void validateServiceMapId() throws WrongValueException, Exception {
		GWServiceMap value = lbServiceMapId.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbServiceMapId, value.getDATA_MAP_ID(), lServiceMapId.getValue());
	}

	private void validateOutboundId() throws WrongValueException, Exception {
		OutboundId value = lbOutboundId.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbOutboundId, value.getGW_OUTB_ID(), lOutboundId.getValue());
	}

	private void validateServiceName() throws WrongValueException, Exception {
		Validation.isNotEmpty(txServiceName, txServiceName.getValue(), lServiceName.getValue());
	}
}
