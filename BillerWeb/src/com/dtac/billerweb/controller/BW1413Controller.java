package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BillerChannel;
import mfs.biller.persistence.bean.BillerServiceChannel;
import mfs.exception.IsExistException;

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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Tab;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.Dialog;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.data.BillerServiceChannelSO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.form.BW1413Form;
import com.dtac.billerweb.listmodel.selectbox.BillerChannelListModel;
import com.dtac.billerweb.service.BillerServiceService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1413Controller extends EditPageController {

	public BW1413Controller() {
		super("Biller Service/Channel","1413");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW1413Controller.class);
	private BW1413Form bw1413Form = new BW1413Form();

	@WireVariable
	private Page updatePage;
	@Wire
	private Label bw1413Title;
	@Wire
	private Hlayout hLayoutAdd;
	@Wire
	private Listbox lbChannelCode;
	@Wire
	private Label lChannelCode;
	@Wire
	private Radiogroup rdAllowedKeyInFlag;
	@Wire
	private Radiogroup rdAllowedBarcodeFlag;
	@Wire
	private Listbox lbActiveFlag;

	@Wire
	private Button btBack;
	@Wire
	private Button btAdd;
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
		setupPage();
	}

	private void setupPage() {
		try {
			/* ## Check Permission## */
			chkInsertPermission(btAdd);
			/* ## Set Biller Channel list ## */
			loadBillerChannelCodeList();
			setPageTitle();
			/* ## Set Result Grid ## */
			gdResult.setPaginal(paging);
			paging.setPageSize(Integer.parseInt(AppConfiguration.getValue(AppConfiguration.PAGE_SIZE_KEY)));
			loadResultData();
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
			if (chkUpdatePermission(updatePage.getFellows(), new Listbox[] { lbChannelCode }, btAdd)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW1413_NEW_TITLE));
				if (!getAuthorization().isInsert()) {
					btAdd.setVisible(false);
				}
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW1413_VIEW_TITLE));
			}
			bw1413Title.setValue(title.getValue());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			parent = null;
			title = null;
		}
	}

	private void loadBillerChannelCodeList() {
		log.info(getOperationLogMessage(pageName, "Load BillerChannelCodeList ", ""));
		SelectBoxService selectBoxService = null;
		BillerChannelListModel billerChannelListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			/* ## Set Biller Channel list ## */
			billerChannelListModel = selectBoxService.getBillerChannelListModel(billerServiceId, getUserInfo());
			if (billerChannelListModel.getSize() > 0) {
				hLayoutAdd.setVisible(true);
			} else {
				hLayoutAdd.setVisible(false);
			}
			billerChannelListModel.addItemSelect();
			billerChannelListModel.addItemCreateNew();
			lbChannelCode.setModel(billerChannelListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerChannelListModel = null;
		}
		log.info(getStopLogMessage(pageName));
	}

	private void loadResultData() {
		log.info(getOperationLogMessage(pageName, "Load Data", ""));
		BillerServiceService billerServiceService = null;
		List<BillerServiceChannelSO> billerServiceChannelSOs = null;
		ListModel<BillerServiceChannelSO> billerSrvChannelListModel = null;
		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			billerServiceChannelSOs = billerServiceService.searchBillerServiceChannel(billerServiceId, getUserInfo());
			billerSrvChannelListModel = new SimpleListModel<BillerServiceChannelSO>(billerServiceChannelSOs);
			paging.setTotalSize(billerSrvChannelListModel.getSize());
			gdResult.setModel(billerSrvChannelListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			billerServiceChannelSOs = null;
			billerSrvChannelListModel = null;
		}
		log.info(getStopLogMessage(pageName));
	}

	public void updateBW1413Form() {
		try {
			BillerChannel billerChannel = lbChannelCode.getSelectedItem().getValue();
			bw1413Form.setBillerChannelId(billerChannel.getBLLR_CHNL_ID());
			bw1413Form.setBillerChannelCode(billerChannel.getBLLR_CHNL_CODE());
			bw1413Form.setBillerServiceId(billerServiceId);
			bw1413Form.setAllowedKeyIn(rdAllowedKeyInFlag.getSelectedItem().getValue().toString());
			bw1413Form.setAllowedBarcode(rdAllowedBarcodeFlag.getSelectedItem().getValue().toString());
			bw1413Form.setActiveFlag(lbActiveFlag.getSelectedItem().getValue().toString());
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {

		}
	}

	/* ## Event ## */
	@Listen("onClick = #btAdd")
	public void clickAdd() {
		log.info(getOperationLogMessage(pageName, "Add Biller Service/Channel", ""));
		checkSessionTimeOut("Add " + "Channel");
		try {
			updateBW1413Form();
			validateChannelCode();
			add();
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Add Biller Service/Validate", getErrorParames(), ex));
			showErrorMessage(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void add() throws WrongValueException {
		final Date requestDate = AppUtil.getCurrent();
		EventListener saveConfirmListener = new EventListener() {
			public void onEvent(Event evt) throws WrongValueException, Exception {
				if (evt.getName().equals("onOK")) {
					BillerServiceService billerServiceService = null;
					BillerServiceChannel billerServiceChannel = null;
					try {
						billerServiceService = BillerwebServiceFactory.getBillerServiceService();
						billerServiceChannel = bw1413Form.toBillerServiceChannel();
						billerServiceChannel.setCRTD_BY(getUserName());
						billerServiceChannel.setLAST_CHNG_BY(getUserName());
						billerServiceService.saveBillerServiceChannel(billerServiceChannel, getUserInfo());
						EventListener okListener = new EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									log.info("Save OK");
									loadBillerChannelCodeList();
									loadResultData();
								}
							}
						};
						showSaveSuccessMessage(okListener);
					} catch (IsExistException ieex) {
						String errorMessage = ieex.getMessage();
						log.error(getErrorLogMessage(pageName, "Add Biller Service/Channel", getErrorParames(), ieex));
						BillerChannel select = lbChannelCode.getSelectedItem().getValue();
						Validation.isExist(lbChannelCode, true, AppUtil.toString(select.getBLLR_CHNL_ID()));

					} catch (Exception ex) {
						log.error(getErrorLogMessage(pageName, "Add Biller Service/Channel", getErrorParames(), ex));
						showSaveFailMessage();
						setTxErrLogMessage(pageCode + "#Add", requestDate, ex);
					} finally {
						billerServiceService = null;
					}
				}
			}
		};
		showSaveConfirmMessage(saveConfirmListener);
		setTxLogMessage(pageCode + "#Add", requestDate, "");
	}

	private Map<String, String> getErrorParames() {
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("Channel ID", bw1413Form.getBillerChannelId() + "");
			params.put("Biller Service ID", bw1413Form.getBillerServiceId() + "");
			params.put("Allowed Key In Flag", bw1413Form.getAllowedKeyIn());
			params.put("Allowed Barcode Flag", bw1413Form.getAllowedBarcode() + "");
			params.put("Status", bw1413Form.getActiveFlag() + "");

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
		return params;
	}

	@Listen("onClick = #btBack")
	public void back() {
		log.info(getOperationLogMessage(pageName, "Back", ""));
		Page parent = null;
		Tab tabPaymentValidate = null;
		try {
			parent = this.getSelf().getDesktop().getPageIfAny("pBw1410");
			log.info("Parent Page:" + parent);
			if (parent == null) {
				return;
			}
			tabPaymentValidate = (Tab) parent.getFellow("tabPaymentValidate");
			Event event = new Event("onSelect");
			Events.sendEvent(tabPaymentValidate, event);

		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Back", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			tabPaymentValidate = null;
		}
	}

	@Listen("onClick = #btNext")
	public void next() {
		log.info(getOperationLogMessage(pageName, "Next", ""));
		Page parent = null;
		Tab tabInputForm = null;
		try {
			parent = this.getSelf().getDesktop().getPageIfAny("pBw1410");
			log.info("Parent Page:" + parent);
			if (parent == null) {
				return;
			}
			tabInputForm = (Tab) parent.getFellow("tabInputForm");
			Event event = new Event("onSelect");
			Events.sendEvent(tabInputForm, event);

		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Next", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			tabInputForm = null;
		}
	}

	/* ## Biller Channel Code Event ## */
	@Listen("onAfterRender=#lbChannelCode")
	public void onAfterRenderBillerChannelCode(Event even) {
		log.info(getOperationLogMessage(pageName, "onAfterRenderBillerChannelCode", ""));
		try {
			Listitem createNew = lbChannelCode.getItemAtIndex(lbChannelCode.getItemCount() - 1);
			createNew.setSclass("listboxFooter");
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onAfterRenderBillerChannelCode", ex));
			showErrorMessage(ex);
		}
	}

	@Listen("onSelect = #lbChannelCode")
	public void onSelectBillerChannelCode() {
		log.info(getOperationLogMessage(pageName, "onSelectBillerChannelCode", ""));
		try {
			Clients.clearWrongValue(lbChannelCode);
			/* ## Create New ## */
			BillerChannel billerChannel = lbChannelCode.getSelectedItem().getValue();
			if (AppUtil.isNotNull(billerChannel.getBLLR_CHNL_ID()) && billerChannel.getBLLR_CHNL_ID() == -1) {
				processCreateNewBillerChannel();
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectBillerChannelCode", ex));
			showErrorMessage(ex);
		}
		log.info(getStopLogMessage(pageName));
	}

	private void processCreateNewBillerChannel() throws Exception {
		Dialog.openBw1310MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1413_REDIRECT_PATH), -1);
	}

	@Listen("onRefresh = #lbChannelCode")
	public void refreshChannelCode() {
		log.info(getOperationLogMessage(pageName, "refreshChannelCode", ""));
		try {
			loadBillerChannelCodeList();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "refreshChannelCode", ex));
			showErrorMessage(ex);
		}
	}

	@Listen("onEdit = #gdResult")
	public void edit(ForwardEvent event) {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Edit " + "Channel");
		Row row = new Row();
		try {
			row = (Row) event.getOrigin().getTarget();
			log.info(getOperationLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue()));
			String[] oids = row.getValue().toString().split("#");
			String oid = "";
			String serviceId = "";
			if (oids.length == 2) {
				oid = oids[0];
				serviceId = oids[1];
			}
			Dialog.openBw1430MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1413_REDIRECT_PATH), oid, serviceId);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue(), ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Edit", requestDate, ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Edit", requestDate, "");
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
	private void validateChannelCode() throws WrongValueException, Exception {
		BillerChannel value = lbChannelCode.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbChannelCode, value.getBLLR_CHNL_ID(), lChannelCode.getValue());
	}
}
