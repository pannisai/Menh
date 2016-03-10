package com.dtac.billerweb.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.ObjMapGWxml;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
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
import com.dtac.billerweb.form.BW2230Form;
import com.dtac.billerweb.listmodel.selectbox.ServiceMapIdListModel;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.service.ServiceMapIdService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW2230Controller extends EditPageController {
	public BW2230Controller() {
		super("Edit Service Map ID","2230");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW2230Controller.class);
	private BW2230Form bw2230Form = new BW2230Form();

	@Wire
	private Label title;
	@Wire
	private Intbox intMapId;
	@Wire
	private Textbox txMapName;
	@Wire
	private Label lMapName;
	@Wire
	private Textbox txMapDesc;
	@Wire
	private Textbox txMapXMLData;
	@Wire
	private Button btSave;
	@Wire
	private Button btReset;
	@Wire
	private Button btValidateXml;
	@Wire
	private Button btCancel;

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub
		if (oid == -1) {
			setupNewPage();
		} else {
			setupEditPage();
		}
	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave);
			title.setValue(AppMessage.getMessage(AppMessage.BW2230_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			i = (Include) Path.getComponent("/bw2230Dialog/bw2230DialogInclude");
			comps = i.getFellows();
			if (chkUpdatePermission(comps, btSave, btReset, btValidateXml)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW2230_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW2230_VIEW_TITLE));
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
		ServiceMapIdService serviceMapIdService = null;
		ObjMapGWxml serviceMapId = null;
		try {
			serviceMapIdService = BillerwebServiceFactory.getServiceMapIdService();
			serviceMapId = serviceMapIdService.getByID(oid, getUserInfo());
			bw2230Form = bw2230Form.toBW2230Form(serviceMapId);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Load Data", "OID::" + oid, ex));
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {

		try {
			intMapId.setValue(bw2230Form.getMapId());
			txMapName.setValue(bw2230Form.getMapName());
			txMapDesc.setValue(bw2230Form.getMapDesc());
			txMapXMLData.setValue(bw2230Form.getMapXmlData());

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void updateBW2230Form() {
		try {
			bw2230Form.setMapId(intMapId.getValue());
			bw2230Form.setMapName(txMapName.getValue());
			bw2230Form.setMapDesc(txMapDesc.getValue());
			bw2230Form.setMapXmlData(txMapXMLData.getValue());
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
			log.info(getOperationLogMessage(pageName, "Save Service Map Id", ""));
			updateBW2230Form();
			validateMapName();
			validateMapXMLData();
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
			log.error(getErrorLogMessage(pageName, "Save Service Map Id", getErrorParames(), ex));
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
					ServiceMapIdService serviceMapIdService = null;
					ObjMapGWxml serviceMapId = null;
					try {
						serviceMapIdService = BillerwebServiceFactory.getServiceMapIdService();
						serviceMapId = bw2230Form.toMapGWXml();

						final Integer oid = serviceMapIdService.save(serviceMapId, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									if (isDialog()) {
										processSaveInDialogMode();
									} else {
										redirect(AppConfiguration.getValue(AppConfiguration.BW2230_REDIRECT_PATH) + "?oid=" + oid);
									}
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Service Map Id", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("DATA_MAP_ID")) {
							Validation.isExist(intMapId, true, AppUtil.toString(intMapId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Service Map Id", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						serviceMapIdService = null;
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
		ServiceMapIdListModel serviceMapIdListModel = null;
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
				Listbox lbServiceMapId = null;
				if (pBw2210 != null) {
					lbServiceMapId = (Listbox) pBw2210.getFellow("lbServiceMapId");
				} else {
					Include i = (Include) Path.getComponent("/bw2210Dialog/bw2210DialogInclude");
					lbServiceMapId = (Listbox) i.getFellow("lbServiceMapId");
				}
				serviceMapIdListModel = selectBoxService.getServiceMapIdListModel();
				serviceMapIdListModel.addItemSelect();
				serviceMapIdListModel.addItemCreateNew();
				lbServiceMapId.setModel(serviceMapIdListModel);
			}
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "ProcessSaveInDialogMode", "[Parrent:" + parent + ",Callback:" + callback, ex));
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			parent = null;
			parentWindow = null;
			serviceMapIdListModel = null;
		}
	}

	private void update() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Update " + pageName);
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					ServiceMapIdService serviceMapIdService = null;
					ObjMapGWxml serviceMapId = null;
					try {
						serviceMapIdService = BillerwebServiceFactory.getServiceMapIdService();
						serviceMapId = bw2230Form.toMapGWXml();
						serviceMapIdService.update(serviceMapId, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Update Service Map Id", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("DATA_MAP_ID")) {
							Validation.isExist(intMapId, true, AppUtil.toString(intMapId.getValue()));
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Update Service Map Id", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						serviceMapIdService = null;
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
		ServiceMapIdListModel serviceMapIdListModel = null;
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
				log.info("Parent Page:" + pBw2210);
				Listbox lbServiceMapId = null;
				if (pBw2210 != null) {
					lbServiceMapId = (Listbox) pBw2210.getFellow("lbServiceMapId");
				} else {
					Include i = (Include) Path.getComponent("/bw2210Dialog/bw2210DialogInclude");
					lbServiceMapId = (Listbox) i.getFellow("lbServiceMapId");
				}

				int currentServiceMapId = lbServiceMapId.getSelectedIndex();
				serviceMapIdListModel = selectBoxService.getServiceMapIdListModel();
				serviceMapIdListModel.addItemSelect();
				serviceMapIdListModel.addItemCreateNew();
				serviceMapIdListModel.addDataToSelection(currentServiceMapId);
				lbServiceMapId.setModel(serviceMapIdListModel);
			}
			parentWindow.detach();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "ProcessUpdateInDialogMode", "[Parrent:" + parent + ",Callback:" + callback, ex));
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			parent = null;
			parentWindow = null;
			serviceMapIdListModel = null;
		}
	}

	private Map<String, String> getErrorParames() {
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("Map ID", bw2230Form.getMapId() + "");
			params.put("Map Name", bw2230Form.getMapName());
			params.put("Map Description", bw2230Form.getMapDesc() + "");
			params.put("Map XML Data", bw2230Form.getMapXmlData());
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
						bw2230Form.setMapName(null);
						bw2230Form.setMapDesc(null);
						bw2230Form.setMapXmlData(null);
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

	@Listen("onClick = #btValidateXml")
	public void validateXml() {
		log.info(getOperationLogMessage(pageName, "Validate XML", ""));
		try {
			validateMapXMLData();
			showInfomationMessage(AppMessage.getMessage(AppMessage.MSG_CORRECT));
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Validate XML", ex));
			showErrorMessage(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
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

	/* ## Validation ## */
	private void validateMapName() throws WrongValueException, Exception {
		Validation.isNotEmpty(txMapName, txMapName.getValue(), lMapName.getValue());
	}

	private void validateMapXMLData() throws WrongValueException, Exception {
		Validation.isXMLFormat(txMapXMLData, txMapXMLData.getValue());
	}

}
