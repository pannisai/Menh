package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BankServicebean;
import mfs.biller.persistence.bean.GWBankBean;
import mfs.biller.persistence.bean.GWBankDetail;
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
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
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
import com.dtac.billerweb.form.BW5210Form;
import com.dtac.billerweb.listmodel.selectbox.BankGWInboundMapListModel;
import com.dtac.billerweb.listmodel.selectbox.BankGWOutboundMapListModel;
import com.dtac.billerweb.listmodel.selectbox.BankGWServiceMapListModel;
import com.dtac.billerweb.listmodel.selectbox.BankServiceListModel;
import com.dtac.billerweb.service.BankGatewayService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW5210Controller extends EditPageController {

	public BW5210Controller() {
		super("Edit Bank Gateway","5210");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW5110Controller.class);
	private BW5210Form bw5210Form = new BW5210Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label title;
	@Wire
	private Listbox lbBankServiceId;
	@Wire
	private Label lBankServiceId;
	@Wire
	private Textbox txBankServiceName;
	@Wire
	private Label lInboundMapId;
	@Wire
	private Listbox lbInboundMapId;
	@Wire
	private Button btEditInboundMapId;
	@Wire
	private Label lServiceMapId;
	@Wire
	private Listbox lbServiceMapId;
	@Wire
	private Button btEditServiceMapId;
	@Wire
	private Label lOutboundMapId;
	@Wire
	private Listbox lbOutboundMapId;
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
		try {

			setupInboundMapList(-1);
			setupServiceMapList(-1);
			setupOutboundMapList(-1);

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
		}
	}

	private void setupBankServiceIdList(boolean isEditPage) {
		SelectBoxService selectBoxService = null;
		BankServiceListModel<BankServicebean> bankServiceListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Bank Service Id list ## */
			if (isEditPage) {
				bankServiceListModel = selectBoxService.getBankServiceListModel();
				lbBankServiceId.setDisabled(true);
			} else {
				bankServiceListModel = selectBoxService.getBankServiceGatewayListModel();
				bankServiceListModel.addItemSelect();
			}
			lbBankServiceId.setModel(bankServiceListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			bankServiceListModel = null;
		}
	}

	private void setupInboundMapList(Integer selectedIndex) {
		SelectBoxService selectBoxService = null;
		BankGWInboundMapListModel<GWBankDetail> bankGWInMapListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Bank Code list ## */
			bankGWInMapListModel = selectBoxService.getBankGWInboundMapListModel();
			bankGWInMapListModel.addItemSelect();
			bankGWInMapListModel.addItemCreateNew();
			if (AppUtil.isNotNull(selectedIndex) && selectedIndex != -1) {
				bankGWInMapListModel.addDataToSelection(selectedIndex);
			}
			lbInboundMapId.setModel(bankGWInMapListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			bankGWInMapListModel = null;
		}
	}

	private void setupServiceMapList(Integer selectedIndex) {
		SelectBoxService selectBoxService = null;
		BankGWServiceMapListModel<GWBankDetail> bankGWSVMapListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Bank Code list ## */
			bankGWSVMapListModel = selectBoxService.getBankGWServiceMapListModel();
			bankGWSVMapListModel.addItemSelect();
			bankGWSVMapListModel.addItemCreateNew();
			if (AppUtil.isNotNull(selectedIndex) && selectedIndex != -1) {
				bankGWSVMapListModel.addDataToSelection(selectedIndex);
			}
			lbServiceMapId.setModel(bankGWSVMapListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			bankGWSVMapListModel = null;
		}
	}

	private void setupOutboundMapList(Integer selectedIndex) {
		SelectBoxService selectBoxService = null;
		BankGWOutboundMapListModel<GWBankDetail> bankGWOutMapListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Bank Code list ## */
			bankGWOutMapListModel = selectBoxService.getBankGWOutboundMapListModel();
			bankGWOutMapListModel.addItemSelect();
			bankGWOutMapListModel.addItemCreateNew();
			if (AppUtil.isNotNull(selectedIndex) && selectedIndex != -1) {
				bankGWOutMapListModel.addDataToSelection(selectedIndex);
			}
			lbOutboundMapId.setModel(bankGWOutMapListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			bankGWOutMapListModel = null;
		}
	}

	private void setupNewPage() {
		try {
			setupBankServiceIdList(false);
			chkInsertPermission(btSave, btEditInboundMapId, btEditOutboundMapId, btEditServiceMapId);
			title.setValue(AppMessage.getMessage(AppMessage.BW5210_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			setupBankServiceIdList(true);
			i = (Include) Path.getComponent("/bw5210Dialog/bw5210DialogInclude");
			comps = i != null ? i.getFellows() : updatePage.getFellows();
			if (chkUpdatePermission(comps, new Listbox[] { lbInboundMapId, lbServiceMapId, lbOutboundMapId },new Button[]{ btEditInboundMapId, btEditOutboundMapId, btEditServiceMapId}, btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW5210_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW5210_VIEW_TITLE));
			}

			loadFormData();
			refreshForm();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			i = null;
			comps = null;
		}
	}

	private void loadFormData() {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BankGatewayService bankGatewayService = null;
		GWBankBean gwBankBean = null;
		try {
			bankGatewayService = BillerwebServiceFactory.getBankGatewayService();
			gwBankBean = bankGatewayService.getByID(oid, getUserInfo());
			bw5210Form = bw5210Form.toBW5210Form(gwBankBean);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		BankServiceListModel<BankServicebean> bankServiceListModel = null;
		BankGWInboundMapListModel<GWBankDetail> bankGWInMapListModel = null;
		BankGWServiceMapListModel<GWBankDetail> bankGWSVMapListModel = null;
		BankGWOutboundMapListModel<GWBankDetail> bankGWOutMapListModel = null;
		try {

			bankServiceListModel = (BankServiceListModel) lbBankServiceId.getModel();
			int bankServiceIndex = bankServiceListModel.findIndexOfId(bw5210Form.getBankServiceId());
			if (bankServiceIndex > -1 && lbBankServiceId.getItemCount() > bankServiceIndex) {
				bankServiceListModel.addDataToSelection(bankServiceIndex);
				txBankServiceName.setValue(bankServiceListModel.getElementAt(bankServiceIndex).getBANK_SRVC_NAME());
			}

			bankGWInMapListModel = (BankGWInboundMapListModel) lbInboundMapId.getModel();
			int bankGWInMapIndex = bankGWInMapListModel.findIndexOfId(bw5210Form.getInboundMapId());
			if (bankGWInMapIndex > -1 && lbInboundMapId.getItemCount() > bankGWInMapIndex) {
				bankGWInMapListModel.addDataToSelection(bankGWInMapIndex);
			}

			bankGWSVMapListModel = (BankGWServiceMapListModel) lbServiceMapId.getModel();
			int bankGWSVMapIndex = bankGWSVMapListModel.findIndexOfId(bw5210Form.getServiceMapId());
			if (bankGWSVMapIndex > -1 && lbServiceMapId.getItemCount() > bankGWSVMapIndex) {
				bankGWSVMapListModel.addDataToSelection(bankGWSVMapIndex);
			}

			bankGWOutMapListModel = (BankGWOutboundMapListModel) lbOutboundMapId.getModel();
			int bankGWOutMapIndex = bankGWOutMapListModel.findIndexOfId(bw5210Form.getOutboundMapId());
			if (bankGWOutMapIndex > -1 && lbOutboundMapId.getItemCount() > bankGWOutMapIndex) {
				bankGWOutMapListModel.addDataToSelection(bankGWOutMapIndex);
			}

			lbActiveFlag.setSelectedIndex(bw5210Form.getActiveFlag().equalsIgnoreCase("A") ? 0 : 1);

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			bankServiceListModel = null;
			bankGWInMapListModel = null;
			bankGWSVMapListModel = null;
			bankGWOutMapListModel = null;
		}
	}

	private void updateBW5210Form() {
		try {
			bw5210Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
			BankServicebean bankService = lbBankServiceId.getSelectedItem().getValue();
			bw5210Form.setBankServiceId(bankService.getBANK_SRVC_ID());
			GWBankDetail inboundMap = lbInboundMapId.getSelectedItem().getValue();
			bw5210Form.setInboundMapId(inboundMap.getINB_MAP_ID());
			GWBankDetail serviceMap = lbServiceMapId.getSelectedItem().getValue();
			bw5210Form.setServiceMapId(serviceMap.getGW_MAP_ID());
			GWBankDetail outboundMap = lbOutboundMapId.getSelectedItem().getValue();
			bw5210Form.setOutboundMapId(outboundMap.getOUTB_MAP_ID());
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	/* ## Event ## */
	@Listen("onClick = #btSave")
	public void clickSave() {

		try {
			log.info(getOperationLogMessage(pageName, "Save Bank Gateway", ""));
			validateBankServiceId();
			// validateInboundMapId();
			// validateServiceMapId();
			// validateOutboundMapId();
			updateBW5210Form();
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
			log.error(getErrorLogMessage(pageName, "Save Bank Gateway", getErrorParames(), ex));
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
					BankGatewayService bankGatewayService = null;
					GWBankBean gwBankBean = null;
					try {
						bankGatewayService = BillerwebServiceFactory.getBankGatewayService();
						gwBankBean = bw5210Form.toBankGatewayService();
						gwBankBean.setCRTD_BY(getUserName());
						gwBankBean.setLAST_CHNG_BY(getUserName());
						final Integer oid = bankGatewayService.save(gwBankBean, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									if (isDialog()) {
										processSaveInDialogMode();
									} else {
										redirect(AppConfiguration.getValue(AppConfiguration.BW5210_REDIRECT_PATH) + "?oid=" + oid);
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Bank Gateway", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BANK_SRVC_ID")) {
							BankServicebean bankService = lbBankServiceId.getSelectedItem().getValue();
							Validation.isExist(lbBankServiceId, true, AppUtil.toString(bankService.getBANK_SRVC_ID()));
							bankService = null;
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Bank Gateway", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						bankGatewayService = null;
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW5200_REDIRECT_PATH))) {
				Page pBw5200 = Executions.getCurrent().getDesktop().getPageIfAny("pBw5200");
				log.info("Page:" + pBw5200);
				Grid gdResult = null;
				if (pBw5200 != null) {
					gdResult = (Grid) pBw5200.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw5200Dialog/bw5200DialogInclude");
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
					BankGatewayService bankGatewayService = null;
					GWBankBean gwBankBean = null;
					try {
						bankGatewayService = BillerwebServiceFactory.getBankGatewayService();
						gwBankBean = bw5210Form.toBankGatewayService();
						gwBankBean.setLAST_CHNG_BY(getUserName());
						bankGatewayService.update(gwBankBean, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Update Bank Gateway", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("BANK_SRVC_ID")) {
							BankServicebean bankService = lbBankServiceId.getSelectedItem().getValue();
							Validation.isExist(lbBankServiceId, true, AppUtil.toString(bankService.getBANK_SRVC_ID()));
							bankService = null;
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Bank Service", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						bankGatewayService = null;
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW5200_REDIRECT_PATH))) {
				Page pBw5200 = Executions.getCurrent().getDesktop().getPageIfAny("pBw5200");
				log.info("Page:" + pBw5200);
				Grid gdResult = null;
				if (pBw5200 != null) {
					gdResult = (Grid) pBw5200.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw5200Dialog/bw5200DialogInclude");
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
			params.put("Bank Service ID", bw5210Form.getBankServiceId() + "");
			params.put("Inbound Map ID", bw5210Form.getInboundMapId() + "");
			params.put("Service Map ID", bw5210Form.getServiceMapId() + "");
			params.put("Outbound Map ID", bw5210Form.getOutboundMapId() + "");
			params.put("ActiveFlag", bw5210Form.getActiveFlag() + "");
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
						if (oid == -1) {
							lbServiceMapId.setSelectedIndex(0);
							txBankServiceName.setValue(null);
						}

						lbInboundMapId.setSelectedIndex(0);
						lbServiceMapId.setSelectedIndex(0);
						lbOutboundMapId.setSelectedIndex(0);
						lbActiveFlag.setSelectedIndex(0);
					} catch (Exception ex) {
						log.error(ex.getMessage());
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
				redirect(AppConfiguration.getValue(AppConfiguration.BW5200_REDIRECT_PATH));
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			parentWindow = null;
		}
	}

	/* ### lbBankServiceId Event ### */
	@Listen("onSelect = #lbBankServiceId")
	public void onSelectBankServiceId() {
		log.info(getOperationLogMessage(pageName, "onSelectBankServiceId", ""));
		BankServicebean bankService = null;
		try {
			Clients.clearWrongValue(lbBankServiceId);

			bankService = lbBankServiceId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(bankService) && AppUtil.isNotNull(bankService.getBANK_SRVC_ID())) {
				txBankServiceName.setValue(bankService.getBANK_SRVC_NAME());
			} else {
				txBankServiceName.setValue(null);
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectInboundMapId", ex));
			showErrorMessage(ex);
		} finally {
			bankService = null;
		}
	}

	/* ### lbInboundMapId Event ### */
	@Listen("onSelect = #lbInboundMapId")
	public void onSelectInboundMapId() {
		log.info(getOperationLogMessage(pageName, "onSelectInboundMapId", ""));
		try {
			Clients.clearWrongValue(lbInboundMapId);
			/* ## Create New ## */
			GWBankDetail inbound = lbInboundMapId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(inbound.getINB_MAP_ID()) && inbound.getINB_MAP_ID() == -1) {
				processCreateNewInboundMapId();
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectInboundMapId", ex));
			showErrorMessage(ex);
		}
	}

	private void processCreateNewInboundMapId() throws Exception {
		Dialog.openBw5230MedalDialog(AppConfiguration.getValue(AppConfiguration.BW5210_REDIRECT_PATH), -1);
	}

	@Listen("onClick = #btEditInboundMapId")
	public void editInboundMapId() {
		log.info(getOperationLogMessage(pageName, "editInboundMapId", ""));
		GWBankDetail inbound = new GWBankDetail();
		try {
			inbound = lbInboundMapId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(inbound.getINB_MAP_ID()) && inbound.getINB_MAP_ID() != -1) {
				Dialog.openBw5230MedalDialog(AppConfiguration.getValue(AppConfiguration.BW5210_REDIRECT_PATH), inbound.getINB_MAP_ID());
			} else {
				Validation.isSelectDropdownList(lbInboundMapId, null, lInboundMapId.getValue());
			}
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "editInboundMapId", "OID:" + inbound.getINB_MAP_ID(), ex));
			showErrorMessage(ex);
		} finally {
			inbound = null;
		}
	}

	@Listen("onAfterRender=#lbInboundMapId")
	public void onAfterRenderInboundMapId(Event even) {
		try {
			Listitem createNew = lbInboundMapId.getItemAtIndex(lbInboundMapId.getItemCount() - 1);
			createNew.setSclass("listboxFooter");
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	/* ### lbServiceMapId Event ### */
	@Listen("onSelect = #lbServiceMapId")
	public void onSelectServiceMapId() {
		log.info(getOperationLogMessage(pageName, "onSelectServiceMapId", ""));
		try {
			Clients.clearWrongValue(lbServiceMapId);
			/* ## Create New ## */
			GWBankDetail serviceMap = lbServiceMapId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(serviceMap.getGW_MAP_ID()) && serviceMap.getGW_MAP_ID() == -1) {
				processCreateNewServiceMapId();
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectServiceMapId", ex));
			showErrorMessage(ex);
		}
	}

	private void processCreateNewServiceMapId() throws Exception {
		Dialog.openBw5240MedalDialog(AppConfiguration.getValue(AppConfiguration.BW5210_REDIRECT_PATH), -1);
	}

	@Listen("onClick = #btEditServiceMapId")
	public void editServiceMapId() {
		log.info(getOperationLogMessage(pageName, "editServiceMapId", ""));
		GWBankDetail serviceMap = new GWBankDetail();
		try {
			serviceMap = lbServiceMapId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(serviceMap.getGW_MAP_ID()) && serviceMap.getGW_MAP_ID() != -1) {
				Dialog.openBw5240MedalDialog(AppConfiguration.getValue(AppConfiguration.BW5210_REDIRECT_PATH), serviceMap.getGW_MAP_ID());
			} else {
				Validation.isSelectDropdownList(lbServiceMapId, null, lServiceMapId.getValue());
			}
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "editServiceMapId", "OID:" + serviceMap.getGW_MAP_ID(), ex));
			showErrorMessage(ex);
		} finally {
			serviceMap = null;
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

	/* ### lbOutboundMapId Event ### */
	@Listen("onSelect = #lbOutboundMapId")
	public void onSelectOutboundMapId() {
		log.info(getOperationLogMessage(pageName, "onSelectOutboundMapId", ""));
		try {
			Clients.clearWrongValue(lbOutboundMapId);
			/* ## Create New ## */
			GWBankDetail outboundMap = lbOutboundMapId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(outboundMap.getOUTB_MAP_ID()) && outboundMap.getOUTB_MAP_ID() == -1) {
				processCreateNewOutboundMapId();
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectOutboundMapId", ex));
			showErrorMessage(ex);
		}
	}

	private void processCreateNewOutboundMapId() throws Exception {
		Dialog.openBw5250MedalDialog(AppConfiguration.getValue(AppConfiguration.BW5210_REDIRECT_PATH), -1);
	}

	@Listen("onClick = #btEditOutboundMapId")
	public void editOutboundMapId() {
		log.info(getOperationLogMessage(pageName, "editOutboundMapId", ""));
		GWBankDetail outboundMap = new GWBankDetail();
		try {
			outboundMap = lbOutboundMapId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(outboundMap.getOUTB_MAP_ID()) && outboundMap.getOUTB_MAP_ID() != -1) {
				Dialog.openBw5250MedalDialog(AppConfiguration.getValue(AppConfiguration.BW5210_REDIRECT_PATH), outboundMap.getOUTB_MAP_ID());
			} else {
				Validation.isSelectDropdownList(lbOutboundMapId, null, lOutboundMapId.getValue());
			}
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "editOutboundMapId", "OID:" + outboundMap.getOUTB_MAP_ID(), ex));
			showErrorMessage(ex);
		} finally {
			outboundMap = null;
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
	private void validateBankServiceId() throws WrongValueException, Exception {
		BankServicebean value = lbBankServiceId.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbBankServiceId, value.getBANK_SRVC_ID(), lBankServiceId.getValue());
	}

	private void validateInboundMapId() throws WrongValueException, Exception {
		GWBankDetail value = lbInboundMapId.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbInboundMapId, value.getINB_MAP_ID(), lInboundMapId.getValue());
	}

	private void validateServiceMapId() throws WrongValueException, Exception {
		GWBankDetail value = lbServiceMapId.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbServiceMapId, value.getGW_MAP_ID(), lServiceMapId.getValue());
	}

	private void validateOutboundMapId() throws WrongValueException, Exception {
		GWBankDetail value = lbOutboundMapId.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbOutboundMapId, value.getOUTB_MAP_ID(), lOutboundMapId.getValue());
	}

	@Listen("onRefreshInboundMapList = #lbInboundMapId")
	public void refreshInboundMapList(Event event) {
		log.info(getOperationLogMessage(pageName, "refreshInboundMapList", ""));
		try {
			Object currentIndex = event.getData();
			if (currentIndex != null) {
				setupInboundMapList((Integer) currentIndex);
			} else {
				setupInboundMapList(-1);
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "refreshInboundMapList", ex));
			showErrorMessage(ex);
		}
	}

	@Listen("onRefreshServiceMapList = #lbServiceMapId")
	public void refreshServiceMapList(Event event) {
		log.info(getOperationLogMessage(pageName, "refreshServiceMapList", ""));
		try {
			Object currentIndex = event.getData();
			if (currentIndex != null) {
				setupServiceMapList((Integer) currentIndex);
			} else {
				setupServiceMapList(-1);
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "refreshServiceMapList", ex));
			showErrorMessage(ex);
		}
	}

	@Listen("onRefreshOutboundMapList = #lbOutboundMapId")
	public void refreshOutboundMapList(Event event) {
		log.info(getOperationLogMessage(pageName, "refreshOutboundMapList", ""));
		try {
			Object currentIndex = event.getData();
			if (currentIndex != null) {
				setupOutboundMapList((Integer) currentIndex);
			} else {
				setupOutboundMapList(-1);
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "refreshOutboundMapList", ex));
			showErrorMessage(ex);
		}
	}
}
