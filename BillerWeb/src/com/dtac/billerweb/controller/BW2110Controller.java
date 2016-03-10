package com.dtac.billerweb.controller;

import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.DropdownlistBillservice;
import mfs.biller.persistence.bean.INBOUNDMAPID;
import mfs.biller.persistence.bean.MFSServiceId;
import mfs.biller.persistence.bean.OBJGW_INBOUND;
import mfs.biller.persistence.bean.SendReceipt;
import mfs.biller.persistence.bean.SendReceiptId;
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
import org.zkoss.zul.Datebox;
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
import com.dtac.billerweb.data.InboundGatewaySOPK;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.exception.DialogException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW2110Form;
import com.dtac.billerweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.billerweb.listmodel.selectbox.InboundMapIdListmodel;
import com.dtac.billerweb.listmodel.selectbox.MFSServiceIdListModel;
import com.dtac.billerweb.listmodel.selectbox.SendReceiptIdListModel;
import com.dtac.billerweb.listmodel.selectbox.SendReceiptListModel;
import com.dtac.billerweb.service.InboundGatewayService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW2110Controller extends EditPageController {
	public BW2110Controller() {
		super("Edit Inbound","2110");
		oid = new InboundGatewaySOPK();
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW2100Controller.class);
	private BW2110Form bw2110Form = new BW2110Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label title;
	@Wire
	private Listbox lbBillerServiceCode;
	@Wire
	private Label lBillerServiceCode;
	@Wire
	private Textbox txBillerCode;
//	@Wire
//	private Intbox intBillerId;
	@Wire
	private Listbox lbFunctionId;
	@Wire
	private Label lFunctionId;
	@Wire
	private Textbox txInboundName;
	@Wire
	private Listbox lbMFSServiceId;
	@Wire
	private Label lMFSServiceId;
	@Wire
	private Button btEditMFSServiceId;
	@Wire
	private Listbox lbInboundMapId;
	@Wire
	private Label lInboundMapId;
	@Wire
	private Button btEditInboundMapId;
	@Wire
	private Listbox lbSendReceipt;
	@Wire
	private Label lSendReceipt;
	@Wire
	private Button btEditSendReceiptId;
	@Wire
	private Datebox dbStartDate;
	@Wire
	private Datebox dbEndDate;
	@Wire
	private Listbox lbActiveFlag;
	@Wire
	private Button btSave;
	@Wire
	private Button btReset;
	@Wire
	private Button btCancel;

	private String inbn_SRVC_ID;
	private String srce_SRVC_ID;
	private String dest_SRVC_ID;
	private InboundGatewaySOPK oid;

	public void doBeforeComposeChildren(Component comp) throws Exception {
		super.doBeforeComposeChildren(comp);
		try {
			inbn_SRVC_ID = getParameter("inbn_SRVC_ID");
			srce_SRVC_ID = getParameter("srce_SRVC_ID");
			dest_SRVC_ID = getParameter("dest_SRVC_ID");
			if (AppUtil.isEmpty(inbn_SRVC_ID) && AppUtil.isEmpty(srce_SRVC_ID) && AppUtil.isEmpty(dest_SRVC_ID)) {
				inbn_SRVC_ID = getArgument("inbn_SRVC_ID");
				srce_SRVC_ID = getArgument("srce_SRVC_ID");
				dest_SRVC_ID = getArgument("dest_SRVC_ID");
			}
			if (!AppUtil.isEmpty(inbn_SRVC_ID)) {
				oid.setInbn_SRVC_ID(inbn_SRVC_ID);
			}
			if (!AppUtil.isEmpty(srce_SRVC_ID)) {
				oid.setSrce_SRVC_ID(Integer.parseInt(srce_SRVC_ID));
			}
			if (!AppUtil.isEmpty(dest_SRVC_ID)) {
				oid.setDest_SRVC_ID(Integer.parseInt(dest_SRVC_ID));
			}

		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		} finally {

		}

	}

	@Override
	protected void init() throws Exception {
		setupPage();
		if (AppUtil.isEmpty(inbn_SRVC_ID) || AppUtil.isEmpty(srce_SRVC_ID) || AppUtil.isEmpty(dest_SRVC_ID)) {
			setupNewPage();
		} else {
			setupEditPage();
		}
	}

	private void setupPage() {
		SelectBoxService selectBoxService = null;
		MFSServiceIdListModel mfsServiceIdListModel = null;
		BillerServiceListModel billerServiceListModel = null;
		InboundMapIdListmodel inboundMapIdListModel = null;
		SendReceiptIdListModel sendReceiptIdListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Biller service code list ## */
			billerServiceListModel = selectBoxService.getBillerServiceListModel();
			billerServiceListModel.addItemSelect();
			lbBillerServiceCode.setModel(billerServiceListModel);
			/* ## Set Biller MFSServiceID list ## */
			mfsServiceIdListModel = selectBoxService.getMFSServiceIdListModel();
			mfsServiceIdListModel.addItemSelect();
			mfsServiceIdListModel.addItemCreateNew();
			lbMFSServiceId.setModel(mfsServiceIdListModel);
			/* ## Set lbInboundMapId list ## */
			inboundMapIdListModel = selectBoxService.getInboundMapIdListModel();
			inboundMapIdListModel.addItemSelect();
			inboundMapIdListModel.addItemCreateNew();
			lbInboundMapId.setModel(inboundMapIdListModel);

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			mfsServiceIdListModel = null;
			billerServiceListModel = null;
			inboundMapIdListModel = null;
			// sendReceiptListModel = null;
			sendReceiptIdListModel = null;
		}
	}

	private void setupNewPage() {
		try {
			chkInsertPermission(btSave,btEditInboundMapId,btEditMFSServiceId);
			title.setValue(AppMessage.getMessage(AppMessage.BW2110_NEW_TITLE));
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void setupEditPage() {
		Include i = null;
		Collection<Component> comps = null;
		try {
			 i = (Include) Path.getComponent("/bw2110Dialog/bw2110DialogInclude");
			 comps =i!=null? i.getFellows():updatePage.getFellows();
			if (chkUpdatePermission(comps,new Listbox[]{lbMFSServiceId,lbInboundMapId},new Button[]{btEditInboundMapId,btEditMFSServiceId}, btSave, btReset)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW2110_EDIT_TITLE));
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW2110_VIEW_TITLE));
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

	private void loadFormData(InboundGatewaySOPK oid) {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		InboundGatewayService inboundGatewayService = null;
		OBJGW_INBOUND inboundGateway = null;
		try {
			inboundGatewayService = BillerwebServiceFactory.getInboundGatewayService();
			inboundGateway = inboundGatewayService.getByID(oid, getUserInfo());
			bw2110Form = bw2110Form.toBW2110Form(inboundGateway);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Load Data", "OID::" + oid.getInbn_SRVC_ID() + "#" + oid.getDest_SRVC_ID() + "#" + oid.getSrce_SRVC_ID(), ex));
			throw new BillerWebException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void refreshForm() {
		try {
			MFSServiceIdListModel<MFSServiceId> mfsServiceIdListModel = null;
			BillerServiceListModel<DropdownlistBillservice> billerServiceListModel = null;
			InboundMapIdListmodel<INBOUNDMAPID> inboundMapIdListModel = null;
			SendReceiptListModel<SendReceipt> sendReceiptListModel = null;
			SendReceiptIdListModel<SendReceiptId> sendReceiptIdListModel = null;
			try {
				billerServiceListModel = (BillerServiceListModel) lbBillerServiceCode.getModel();
				int billerServiceCodeIndex = billerServiceListModel.findIndexByServiceCode(bw2110Form.getBillerServiceCode());
				if (billerServiceCodeIndex > -1 && lbBillerServiceCode.getItemCount() > billerServiceCodeIndex) {
					billerServiceListModel.addDataToSelection(billerServiceCodeIndex);
					DropdownlistBillservice dropdownlistBillservice = billerServiceListModel.getElementAt(billerServiceCodeIndex);
					txBillerCode.setValue(dropdownlistBillservice.getBLLR_SRVC_CODE());
					
					dbStartDate.setValue(dropdownlistBillservice.getBLLR_SRVC_STAT_DATE());
					dbEndDate.setValue(dropdownlistBillservice.getBLLR_SRVC_EXPR_DATE());
					dropdownlistBillservice = null;
				}

				if (bw2110Form.getFunctionId() != null) {
					if (bw2110Form.getFunctionId().intValue() == 720000) {
						lbFunctionId.setSelectedIndex(1);
					} else if (bw2110Form.getFunctionId().intValue() == 730000) {
						lbFunctionId.setSelectedIndex(2);
					} else if (bw2110Form.getFunctionId().intValue() == 910000) {
						lbFunctionId.setSelectedIndex(3);
					} else if (bw2110Form.getFunctionId().intValue() == 920000) {
						lbFunctionId.setSelectedIndex(4);
					} else if (bw2110Form.getFunctionId().intValue() == 930000) {
						lbFunctionId.setSelectedIndex(5);
					}
				}

				txInboundName.setValue(bw2110Form.getInboundName());
				mfsServiceIdListModel = (MFSServiceIdListModel) lbMFSServiceId.getModel();
				int mfsServiceIdIndex = mfsServiceIdListModel.findIndexOfId(bw2110Form.getMfsServiceId());
				if (mfsServiceIdIndex > -1 && lbMFSServiceId.getItemCount() > mfsServiceIdIndex) {
					mfsServiceIdListModel.addDataToSelection(mfsServiceIdIndex);
				}
				inboundMapIdListModel = (InboundMapIdListmodel) lbInboundMapId.getModel();
				int inboundMapIndex = inboundMapIdListModel.findIndexOfId(bw2110Form.getInboundMapId());
				if (inboundMapIndex > -1 && lbInboundMapId.getItemCount() > inboundMapIndex) {
					inboundMapIdListModel.addDataToSelection(inboundMapIndex);
				}
				if (!AppUtil.isEmpty(bw2110Form.getSendReceipt()) && bw2110Form.getSendReceipt().equalsIgnoreCase("A")) {
					lbSendReceipt.setSelectedIndex(0);
				} else {
					lbSendReceipt.setSelectedIndex(1);
				}

				if (!AppUtil.isEmpty(bw2110Form.getActiveFlag()) && bw2110Form.getActiveFlag().equalsIgnoreCase("A")) {
					lbActiveFlag.setSelectedIndex(0);
				} else {
					lbActiveFlag.setSelectedIndex(1);
				}

			} catch (ArrayIndexOutOfBoundsException aioex) {

			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	private void updateBW2110Form() {
		try {
			bw2110Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
			DropdownlistBillservice billerService = lbBillerServiceCode.getSelectedItem().getValue();
			bw2110Form.setBillerServiceCode(Integer.parseInt(billerService.getBLLR_SRVC_CODE()));
			bw2110Form.setBillerCode(txBillerCode.getValue());
			
			String mfsFunctionId = lbFunctionId.getSelectedItem().getValue();
			try {
				bw2110Form.setFunctionId(Integer.parseInt(mfsFunctionId));
			} catch (NumberFormatException nfe) {

			}
			bw2110Form.setInboundName(txInboundName.getValue());

			MFSServiceId mfsServiceId = lbMFSServiceId.getSelectedItem().getValue();
			bw2110Form.setMfsServiceId(mfsServiceId.getGW_SRVC_ID());

			INBOUNDMAPID inboundMapId = lbInboundMapId.getSelectedItem().getValue();
			bw2110Form.setInboundMapId(inboundMapId.getDATA_MAP_ID());

			bw2110Form.setSendReceipt(lbSendReceipt.getSelectedItem().getValue().toString());

			bw2110Form.setStartDate(dbStartDate.getValue());
			bw2110Form.setEndDate(dbEndDate.getValue());
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
			log.info(getOperationLogMessage(pageName, "Save Inbound Gateway", ""));
			Validation.validateEndDate(dbEndDate, dbStartDate.getValue(), dbEndDate.getValue());
			validateBillerServiceCode();
			validateFunctionId();
			validateMFSServiceID();
			validateInboundMapID();
			updateBW2110Form();

			if (AppUtil.isEmpty(oid.getInbn_SRVC_ID()) || AppUtil.isEmpty(oid.getSrce_SRVC_ID()) || AppUtil.isEmpty(oid.getDest_SRVC_ID())) {
				save();
			} else {
				update();
			}
		} catch (BillerWebSessionTimeOutException bwto) {
			throw new BillerWebSessionTimeOutException(bwto);
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Save Biller Channel", getErrorParames(), ex));
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
					InboundGatewayService inboundGatewayService = null;
					OBJGW_INBOUND inboundGateway = null;
					try {
						inboundGatewayService = BillerwebServiceFactory.getInboundGatewayService();
						inboundGateway = bw2110Form.toOBJGW_INBOUND();
						inboundGateway.setCRTD_BY(getUserName());
						inboundGateway.setLAST_CHNG_BY(getUserName());
						final InboundGatewaySOPK oid = inboundGatewayService.save(inboundGateway, getUserInfo());

						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.debug("Save OK");
									String queryString = "inbn_SRVC_ID=" + URLEncoder.encode(oid.getInbn_SRVC_ID()) + "&srce_SRVC_ID=" + URLEncoder.encode(AppUtil.toString(oid.getSrce_SRVC_ID())) + "&dest_SRVC_ID=" + URLEncoder.encode(AppUtil.toString(oid.getDest_SRVC_ID()));
									redirect(AppConfiguration.getValue(AppConfiguration.BW2110_REDIRECT_PATH) + "?" + queryString);
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Save Inbound Gateway", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("INBN_SRVC_ID")) {
							Validation.isExist(lbBillerServiceCode, true, "Biller Service Code");
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Inbound Gateway", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Save", requestDate, ex);
					} finally {
						inboundGatewayService = null;
					}

				}

			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Save", requestDate, "");
	}

	private void update() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Update " + pageName);
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					InboundGatewayService inboundGatewayService = null;
					OBJGW_INBOUND inboundGateway = null;
					try {
						inboundGatewayService = BillerwebServiceFactory.getInboundGatewayService();
						inboundGateway = bw2110Form.toOBJGW_INBOUND();
						inboundGateway.setLAST_CHNG_BY(getUserName());
						inboundGatewayService.update(inboundGateway, getUserInfo());
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
						log.error(getErrorLogMessage(pageName, "Update Inbound Gateway", getErrorParames(), ieex));
						if (errorMessage.equalsIgnoreCase("INBN_SRVC_ID")) {
							Validation.isExist(lbBillerServiceCode, true, "Biller Service Code");
						}
					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Save Inbound Gateway", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Update", requestDate, ex);
					} finally {
						inboundGatewayService = null;
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
			if (callback.equals(AppConfiguration.getValue(AppConfiguration.BW2100_REDIRECT_PATH))) {
				Page pBw2100 = Executions.getCurrent().getDesktop().getPageIfAny("pBw2100");
				log.info("Page:" + pBw2100);
				Grid gdResult = null;
				if (pBw2100 != null) {
					gdResult = (Grid) pBw2100.getFellow("gdResult");
				} else {
					Include i = (Include) Path.getComponent("/bw2100Dialog/bw2100DialogInclude");
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
			parent = null;
			parentWindow = null;
		}
	}

	private Map<String, String> getErrorParames() {
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("Biller Service Code", bw2110Form.getBillerServiceCode() + "");
			params.put("Biller Code", bw2110Form.getBillerCode());
			params.put("Biller ID", bw2110Form.getBillerId() + "");
			params.put("Function ID", bw2110Form.getFunctionId() + "");
			params.put("Inbound Name", bw2110Form.getInboundName() + "");
			params.put("MFSService ID", bw2110Form.getMfsServiceId() + "");
			params.put("Inbound Map ID", bw2110Form.getInboundMapId() + "");
			params.put("Send Receipt", bw2110Form.getSendReceipt() + "");
			params.put("Send Receipt ID", bw2110Form.getSendReceiptId() + "");
			params.put("ActiveFlag", bw2110Form.getActiveFlag());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	@Listen("onClick = #btReset")
	public void reset() {
		log.info("Click Reset EditSetupINbound");
		EventListener resetConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					try {

						lbBillerServiceCode.setSelectedIndex(0);
						txBillerCode.setValue(null);
						
						lbFunctionId.setSelectedIndex(0);
						txInboundName.setValue(null);
						lbMFSServiceId.setSelectedIndex(0);
						lbInboundMapId.setSelectedIndex(0);
						lbSendReceipt.setSelectedIndex(0);
						// lbSendReceiptId.setSelectedIndex(0);
						dbStartDate.setValue(null);
						dbEndDate.setValue(null);
						lbActiveFlag.setSelectedIndex(0);

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
				redirect(AppConfiguration.getValue(AppConfiguration.BW2100_REDIRECT_PATH));
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Cancel", ex));
			showErrorMessage(ex);
		} finally {

		}
	}

	/* ## Biller Service Code Event ## */
	@Listen("onSelect = #lbBillerServiceCode")
	public void onSelectBillerServiceCode() {
		log.info(getOperationLogMessage(pageName, "OnSelectBillerServiceCode", ""));
		try {
			Clients.clearWrongValue(lbBillerServiceCode);
			DropdownlistBillservice dropdownlistBillservice = lbBillerServiceCode.getSelectedItem().getValue();
			txBillerCode.setValue(dropdownlistBillservice.getBLLR_SRVC_CODE());
		
			dbStartDate.setValue(dropdownlistBillservice.getBLLR_SRVC_STAT_DATE());
			dbEndDate.setValue(dropdownlistBillservice.getBLLR_SRVC_EXPR_DATE());
		} catch (Exception ex) {
			String msg = "Biller Code:" + txBillerCode.getValue();
			log.error(getErrorLogMessage(pageName, "OnSelectBillerServiceCode", msg, ex));
			showErrorMessage(ex);
		}
	}

	/* ## MFSService ID Event ## */
	@Listen("onAfterRender=#lbMFSServiceId")
	public void onAfterRenderMFSServiceId(Event even) {
		log.info(getOperationLogMessage(pageName, "onAfterRenderMFSServiceId", ""));
		try {
			Listitem createNew = lbMFSServiceId.getItemAtIndex(lbMFSServiceId.getItemCount() - 1);
			createNew.setSclass("listboxFooter");
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onAfterRenderMFSServiceId", ex));
			showErrorMessage(ex);
		}
	}

	@Listen("onSelect = #lbMFSServiceId")
	public void onSelectMFSServiceId() {
		log.info(getOperationLogMessage(pageName, "onSelectMFSServiceId", ""));
		try {
			Clients.clearWrongValue(lbMFSServiceId);
			/* ## Create New ## */
			MFSServiceId mfsServiceId = lbMFSServiceId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(mfsServiceId.getGW_SRVC_ID()) && mfsServiceId.getGW_SRVC_ID() == -1) {
				processCreateNewMFSServiceId();
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectMFSServiceId", ex));
			showErrorMessage(ex);
		}
	}

	private void processCreateNewMFSServiceId() throws Exception {
		Dialog.openBw2210MedalDialog(AppConfiguration.getValue(AppConfiguration.BW2110_REDIRECT_PATH), -1);
	}

	@Listen("onClick = #btEditMFSServiceId")
	public void editMFSServiceId() {
		log.info(getOperationLogMessage(pageName, "editMFSServiceId", ""));
		MFSServiceId mfsServiceId = new MFSServiceId();
		try {
			mfsServiceId = lbMFSServiceId.getSelectedItem().getValue();
			if (mfsServiceId.getGW_SRVC_ID() != null && mfsServiceId.getGW_SRVC_ID() != -1) {
				Dialog.openBw2210MedalDialog(AppConfiguration.getValue(AppConfiguration.BW2110_REDIRECT_PATH), mfsServiceId.getGW_SRVC_ID());
			} else {
				Validation.isSelectDropdownList(lbMFSServiceId, null, lMFSServiceId.getValue());
			}
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "editMFSServiceId", "OID:" + mfsServiceId.getGW_SRVC_ID(), ex));
			showErrorMessage(ex);
		} finally {
			mfsServiceId = null;
		}
	}

	/* ## Function ID Event ## */

	@Listen("onSelect = #lbFunctionId")
	public void onSelectFunctionId() {
		Clients.clearWrongValue(lbFunctionId);
	}

	/* ## Inbound Map ID Event ## */
	@Listen("onAfterRender=#lbInboundMapId")
	public void onAfterRenderInboundMapId(Event even) {
		log.info(getOperationLogMessage(pageName, "onAfterRenderInboundMapId", ""));
		try {
			Listitem createNew = lbInboundMapId.getItemAtIndex(lbInboundMapId.getItemCount() - 1);
			createNew.setSclass("listboxFooter");
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onAfterRenderInboundMapId", ex));
		}
	}

	@Listen("onSelect = #lbInboundMapId")
	public void onSelectInboundMapId() {
		log.info(getOperationLogMessage(pageName, "onSelectInboundMapId", ""));
		try {
			Clients.clearWrongValue(lbInboundMapId);

			/* ## Create New ## */
			INBOUNDMAPID inboundMapId = lbInboundMapId.getSelectedItem().getValue();
			if (AppUtil.isNotNull(inboundMapId.getDATA_MAP_ID()) && inboundMapId.getDATA_MAP_ID() == -1) {
				processCreateNewInboundMapId();
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectInboundMapId", ex));
			showErrorMessage(ex);
		}
	}

	private void processCreateNewInboundMapId() throws Exception {
		Dialog.openBw2130MedalDialog(AppConfiguration.getValue(AppConfiguration.BW2110_REDIRECT_PATH), -1);
	}

	@Listen("onClick = #btEditInboundMapId")
	public void editInboundMapId() {
		log.info(getOperationLogMessage(pageName, "editInboundMapId", ""));
		INBOUNDMAPID inboundMapId = new INBOUNDMAPID();
		try {
			inboundMapId = lbInboundMapId.getSelectedItem().getValue();
			if (inboundMapId.getDATA_MAP_ID() != null && inboundMapId.getDATA_MAP_ID() != -1) {
				Dialog.openBw2130MedalDialog(AppConfiguration.getValue(AppConfiguration.BW2110_REDIRECT_PATH), inboundMapId.getDATA_MAP_ID());
			} else {
				Validation.isSelectDropdownList(lbInboundMapId, null, lInboundMapId.getValue());
			}
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "editInboundMapId", "OID:" + inboundMapId.getDATA_MAP_ID(), ex));
			showErrorMessage(ex);
		} finally {
			inboundMapId = null;
		}
	}

	/* ## Validation ## */
	private void validateBillerServiceCode() throws WrongValueException, Exception {
		DropdownlistBillservice value = lbBillerServiceCode.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbBillerServiceCode, value.getBLLR_SRVC_ID(), lBillerServiceCode.getValue());
	}

	private void validateFunctionId() throws WrongValueException, Exception {
		Validation.isSelectDropdownList(lbFunctionId, lbFunctionId.getSelectedItem().getValue(), lFunctionId.getValue());
	}

	private void validateMFSServiceID() throws WrongValueException, Exception {
		MFSServiceId value = lbMFSServiceId.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbMFSServiceId, value.getGW_SRVC_ID(), lMFSServiceId.getValue());
	}

	private void validateInboundMapID() throws WrongValueException, Exception {
		INBOUNDMAPID value = lbInboundMapId.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbInboundMapId, value.getDATA_MAP_ID(), lInboundMapId.getValue());
	}

}
