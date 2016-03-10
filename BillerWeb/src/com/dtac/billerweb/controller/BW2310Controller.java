package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.GWOutbound;
import mfs.biller.persistence.bean.GWOutboundMap;
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
import com.dtac.billerweb.form.BW2310Form;
import com.dtac.billerweb.listmodel.selectbox.OutboundIdListModel;
import com.dtac.billerweb.listmodel.selectbox.OutboundMapIdListModel;
import com.dtac.billerweb.service.OutboundGatewayService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW2310Controller extends EditPageController {
	public BW2310Controller() {
		super("Edit Outbound","2310");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW2310Controller.class);
	private BW2310Form bw2310Form = new BW2310Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label title;
	@Wire
	private Intbox intOutboundId;
	@Wire
	private Label lOutboundId;
	@Wire
	private Textbox txOutboundName;
	@Wire
	private Label lOutboundName;
	@Wire
	private Listbox lbOutboundMapId;
	@Wire
	private Label lOutboundMapId;
	@Wire
	private Button btEditOutboundMapId;
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
		OutboundMapIdListModel<GWOutboundMap> outboundMapIdListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Outbound Map id list ## */
			outboundMapIdListModel = selectBoxService.getOutboundMapIdListModel();
			outboundMapIdListModel.addItemSelect();
			outboundMapIdListModel.addItemCreateNew();
			lbOutboundMapId.setModel(outboundMapIdListModel);

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			outboundMapIdListModel = null;
		}
	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave,btEditOutboundMapId);
			title.setValue(AppMessage.getMessage(AppMessage.BW2310_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			 i = (Include) Path.getComponent("/bw2310Dialog/bw2310DialogInclude");
			 comps =i!=null? i.getFellows():updatePage.getFellows();
			if (chkUpdatePermission(comps,new Listbox[]{lbOutboundMapId},new Button[]{btEditOutboundMapId},btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW2310_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW2310_VIEW_TITLE));				
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
		OutboundGatewayService outboundGatewayService = null;
		GWOutbound outbound = null;
		try {
			outboundGatewayService = BillerwebServiceFactory.getOutboundGatewayService();
			outbound = outboundGatewayService.getByID(oid, getUserInfo());
			bw2310Form = bw2310Form.toBW2310Form(outbound);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Load Data", "OID::" + oid, ex));
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		OutboundMapIdListModel<GWOutboundMap> outboundMapIdListModel = null;

		try {
			intOutboundId.setValue(bw2310Form.getOutboundId());
			txOutboundName.setValue(bw2310Form.getOutboundName());
			try {
				outboundMapIdListModel = (OutboundMapIdListModel) lbOutboundMapId.getModel();
				int outboundMapIdIndex = outboundMapIdListModel.findIndexOfId(bw2310Form.getOutboundMapId());
				if (outboundMapIdIndex != -1 && lbOutboundMapId.getItemCount() > outboundMapIdIndex) {
					outboundMapIdListModel.addDataToSelection(outboundMapIdIndex);
				} else {
					outboundMapIdListModel.addDataToSelection(0);
				}

				if (bw2310Form.getActiveFlag().equalsIgnoreCase("A")) {
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
			outboundMapIdListModel = null;
		}
	}

	private void updateBW2310Form() {
		GWOutboundMap gwOutboundMap = null;
		try {
			bw2310Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
			bw2310Form.setOutboundId(intOutboundId.getValue());
			bw2310Form.setOutboundName(txOutboundName.getValue());
			gwOutboundMap = lbOutboundMapId.getSelectedItem().getValue();
			bw2310Form.setOutboundMapId(gwOutboundMap.getDATA_MAP_ID());
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			gwOutboundMap = null;
		}
	}

	/* ## Event ## */
	@Listen("onClick = #btSave")
	public void clickSave() {
		try {
			log.info(getOperationLogMessage(pageName, "Save Outbound Gateway", ""));
			updateBW2310Form();
			validateOutboundName();
			validateOutboundMapId();
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
			log.error(getErrorLogMessage(pageName, "Save Outbound Gateway", getErrorParames(), ex));
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
					OutboundGatewayService outboundGatewayService = null;
					GWOutbound gwOutbound = null;
					try {
						outboundGatewayService = BillerwebServiceFactory.getOutboundGatewayService();
						gwOutbound = bw2310Form.toGwOutbound();
						gwOutbound.setCRTD_BY(getUserName());
						gwOutbound.setLAST_CHNG_BY(getUserName());
						final Integer oid = outboundGatewayService.save(gwOutbound, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									if (isDialog()) {
										processSaveInDialogMode();
									} else {
										redirect(AppConfiguration.getValue(AppConfiguration.BW2310_REDIRECT_PATH) + "?oid=" + oid);
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Outbound Gateway", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("GW_OUTB_ID")) {
							Validation.isExist(intOutboundId, true, AppUtil.toString(intOutboundId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Outbound Gateway", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						outboundGatewayService = null;
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
		OutboundIdListModel outboundIdListModel = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW2210_REDIRECT_PATH))) {

				Page pBw2210 = Executions.getCurrent().getDesktop().getPageIfAny("pBw2210");
				log.info("Page:" + pBw2210);
				Listbox lbOutboundId = null;
				if (pBw2210 != null) {
					lbOutboundId = (Listbox) pBw2210.getFellow("lbOutboundId");
				} else {
					Include i = (Include) Path.getComponent("/bw2210Dialog/bw2210DialogInclude");
					lbOutboundId = (Listbox) i.getFellow("lbOutboundId");
				}
				outboundIdListModel = selectBoxService.getOutboundIdListModel();
				outboundIdListModel.addItemSelect();
				outboundIdListModel.addItemCreateNew();
				lbOutboundId.setModel(outboundIdListModel);
			}
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "ProcessSaveInDialogMode", "[Parrent:" + parent + ",Callback:" + callback, ex));
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			parent = null;
			parentWindow = null;
			outboundIdListModel = null;
		}
	}

	private void update() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Update " + pageName);
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					OutboundGatewayService outboundGatewayService = null;
					GWOutbound gwOutbound = null;
					try {
						outboundGatewayService = BillerwebServiceFactory.getOutboundGatewayService();
						gwOutbound = bw2310Form.toGwOutbound();
						gwOutbound.setLAST_CHNG_BY(getUserName());
						outboundGatewayService.update(gwOutbound, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Update Outbound Gateway", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("GW_OUTB_ID")) {
							Validation.isExist(intOutboundId, true, AppUtil.toString(intOutboundId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Outbound Gateway", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						outboundGatewayService = null;
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
		OutboundIdListModel outboundIdListModel = null;
		try {
			parent = super.getSelf().getParent().getParent();
			if (parent == null) {
				throw new DialogException("Parent is Null");
			}
			log.info("Parent::" + parent);
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			parentWindow = (Window) parent;
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW2210_REDIRECT_PATH))) {
				Page pBw2210 = Executions.getCurrent().getDesktop().getPageIfAny("pBw2210");
				log.info("Page:" + pBw2210);
				Listbox lbOutboundId = null;
				if (pBw2210 != null) {
					lbOutboundId = (Listbox) pBw2210.getFellow("lbOutboundId");
				} else {
					Include i = (Include) Path.getComponent("/bw2210Dialog/bw2210DialogInclude");
					lbOutboundId = (Listbox) i.getFellow("lbOutboundId");
				}
				int currentOutboundId = lbOutboundId.getSelectedIndex();
				outboundIdListModel = selectBoxService.getOutboundIdListModel();
				outboundIdListModel.addItemSelect();
				outboundIdListModel.addItemCreateNew();
				outboundIdListModel.addDataToSelection(currentOutboundId);
				lbOutboundId.setModel(outboundIdListModel);
			} else if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW2300_REDIRECT_PATH))) {
				Page pBw2300 = Executions.getCurrent().getDesktop().getPageIfAny("pBw2300");
				log.info("Page:" + pBw2300);
				Grid gdResult = null;
				if (pBw2300 != null) {
					gdResult = (Grid) pBw2300.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw2300Dialog/bw2300DialogInclude");
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
			params.put("Outbound ID", bw2310Form.getOutboundId() + "");
			params.put("Outbound Name", bw2310Form.getOutboundName());
			params.put("Outbound Map ID", bw2310Form.getOutboundMapId() + "");
			params.put("ActiveFlag", bw2310Form.getActiveFlag());
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
						bw2310Form.setActiveFlag("A");
						bw2310Form.setOutboundName(null);
						bw2310Form.setOutboundMapId(null);
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
				redirect(AppConfiguration.getValue(AppConfiguration.BW2300_REDIRECT_PATH));
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}

	/* ## Outbound Map ID Event ## */
	@Listen("onSelect = #lbOutboundMapId")
	public void onSelectOutboundMapId() {
		log.info(getOperationLogMessage(pageName, "onSelectOutboundMapId", ""));
		try {
			Clients.clearWrongValue(lbOutboundMapId);
			/* ## Create New ## */
			GWOutboundMap gwOutboundMap = lbOutboundMapId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(gwOutboundMap.getDATA_MAP_ID()) && gwOutboundMap.getDATA_MAP_ID() == -1) {
				processCreateNewOutboundMapId();
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectOutboundMapId", ex));
			showErrorMessage(ex);
		}
	}

	private void processCreateNewOutboundMapId() throws Exception {
		Dialog.openBw2330MedalDialog(AppConfiguration.getValue(AppConfiguration.BW2310_REDIRECT_PATH), -1);
	}

	@Listen("onClick = #btEditOutboundMapId")
	public void editOutboundMapId() {

		log.info(getOperationLogMessage(pageName, "editOutboundMapId", ""));
		GWOutboundMap gwOutboundMap = new GWOutboundMap();
		try {
			gwOutboundMap = lbOutboundMapId.getSelectedItem().getValue();
			if (gwOutboundMap.getDATA_MAP_ID() != null && gwOutboundMap.getDATA_MAP_ID() != -1) {
				Dialog.openBw2330MedalDialog(AppConfiguration.getValue(AppConfiguration.BW2310_REDIRECT_PATH), gwOutboundMap.getDATA_MAP_ID());
			} else {
				Validation.isSelectDropdownList(lbOutboundMapId, null, lOutboundMapId.getValue());
			}
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "editOutboundMapId", "OID:" + gwOutboundMap.getDATA_MAP_ID(), ex));
			showErrorMessage(ex);
		} finally {
			gwOutboundMap = null;
		}
	}

	@Listen("onAfterRender=#lbOutboundMapId")
	public void onAfterRenderOutboundMapId(Event even) {
		try {
			Listitem createNew = lbOutboundMapId.getItemAtIndex(lbOutboundMapId.getItemCount() - 1);
			createNew.setSclass("listboxFooter");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	/* ## Validation ## */
	private void validateOutboundMapId() throws WrongValueException, Exception {
		GWOutboundMap value = lbOutboundMapId.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbOutboundMapId, value.getDATA_MAP_ID(), lOutboundMapId.getValue());
	}

	private void validateOutboundName() throws WrongValueException, Exception {
		Validation.isNotEmpty(txOutboundName, txOutboundName.getValue(), lOutboundName.getValue());
	}

}
