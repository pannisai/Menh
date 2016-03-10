package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.List;

import mfs.biller.persistence.bean.BillerRef;
import mfs.biller.persistence.bean.BillerRefParam;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Tab;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.Dialog;
import com.dtac.billerweb.common.EditPageController;
import com.dtac.billerweb.data.BillerBarcodeDetailSO;
import com.dtac.billerweb.data.BillerBarcodeSO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.listmodel.selectbox.BillerRefListModel;
import com.dtac.billerweb.service.BillerServiceService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.validation.Validation;

public class BW1415Controller extends EditPageController {

	public BW1415Controller() {
		super("EDIT Biller Service/Barcode", "1415");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(BW1415Controller.class);
	@WireVariable
	private Page updatePage;
	@Wire
	private Label bw1415Title;
	@Wire
	private Listbox lbInputId;
	@Wire
	private Label lInputId;
	@Wire
	private Button btAdd;
	@Wire
	private Button btBack;
	@Wire
	private Button btNext;

	@Wire
	protected Grid gdBarcode;
	@Wire
	protected Paging pagingBarcode;

	@Wire
	protected Grid gdDetail;
	@Wire
	protected Paging pagingDetail;

	private Integer billerServiceId;
	private Integer billerFormId;

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub
		String serviceID = getParameter("serviceId");
		if (!AppUtil.isEmpty(serviceID)) {
			billerServiceId = Integer.parseInt(serviceID);
		} else {
			billerServiceId = -1;
			showErrorMessage("Service ID not found!");
		}
		String formID = getParameter("formId");
		if (!AppUtil.isEmpty(formID)) {
			billerFormId = Integer.parseInt(formID);
		} else {
			billerFormId = -1;
			showErrorMessage("Form ID not found!");
		}

		if (billerServiceId != -1 && billerFormId != -1) {
			setupPage();
		}
	}

	private void setupPage() {
		log.info("Set up Page");

		try {
			chkInsertPermission(btAdd);
			setPageTitle();
			gdBarcode.setPaginal(pagingBarcode);
			pagingBarcode.setPageSize(Integer.parseInt(AppConfiguration.getValue(AppConfiguration.SERV_BARCODE_PAGE_SIZE)));
			gdDetail.setPaginal(pagingDetail);
			pagingDetail.setPageSize(Integer.parseInt(AppConfiguration.getValue(AppConfiguration.SERV_BARCODE_DETAIL_PAGE_SIZE)));
			/* set selectbox list */
			loadListBoxInputId();
			/* Set Barcode Grid */
			loadBarcodeResultData();
			/* Set Barcode Detail Grid */
			loadBarcodeDetailResultData();
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
			if (chkUpdatePermission(updatePage.getFellows(), btAdd)) {
				title.setValue(AppMessage.getMessage(AppMessage.BW1415_EDIT_TITLE));
				if (!getAuthorization().isInsert()) {
					btAdd.setVisible(false);
				}
			} else {
				title.setValue(AppMessage.getMessage(AppMessage.BW1415_VIEW_TITLE));
			}
			bw1415Title.setValue(title.getValue());
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			parent = null;
			title = null;
		}
	}

	private void loadBarcodeResultData() {
		log.info(getOperationLogMessage(pageName, "Load BarcodeResultData", ""));
		BillerServiceService billerServiceService = null;
		List<BillerBarcodeSO> billerBarcodeSOs = null;
		ListModel<BillerBarcodeSO> billerBarcodeListModel = null;
		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			billerBarcodeSOs = billerServiceService.searchBillerBarcodeByServiceId(billerServiceId, getUserInfo());
			billerBarcodeListModel = new SimpleListModel<BillerBarcodeSO>(billerBarcodeSOs);
			pagingBarcode.setTotalSize(billerBarcodeListModel.getSize());
			gdBarcode.setModel(billerBarcodeListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			billerBarcodeSOs = null;
			billerBarcodeListModel = null;
			log.info(getStopLogMessage(pageName));
		}
	}

	private void loadBarcodeDetailResultData() {
		log.info(getOperationLogMessage(pageName, "Load BarcodeResultData", ""));
		BillerServiceService billerServiceService = null;
		List<BillerBarcodeDetailSO> billerBarcodeDetailSOs = null;
		ListModel<BillerBarcodeDetailSO> billBarDetailListModel = null;
		try {
			billerServiceService = BillerwebServiceFactory.getBillerServiceService();
			billerBarcodeDetailSOs = billerServiceService.searchBillerBarcodeDetailByServiceId(billerServiceId, getUserInfo());
			billBarDetailListModel = new SimpleListModel<BillerBarcodeDetailSO>(billerBarcodeDetailSOs);
			pagingDetail.setTotalSize(billBarDetailListModel.getSize());
			gdDetail.setModel(billBarDetailListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			billerBarcodeDetailSOs = null;
			billBarDetailListModel = null;
			log.info(getStopLogMessage(pageName));
		}
	}

	private void loadListBoxInputId() {
		SelectBoxService selectBoxService = null;
		BillerRefListModel billerRefListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			BillerRefParam criteria = new BillerRefParam();
			criteria.setBLLR_SRVC_ID(billerServiceId);
			criteria.setBLLR_FORM_ID(billerFormId);
			billerRefListModel = selectBoxService.getBillerRefListModel(criteria, getUserInfo());
			billerRefListModel.addItemSelect();
			lbInputId.setModel(billerRefListModel);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerRefListModel = null;
		}
	}

	/* ## Event ## */
	@Listen("onClick = #btAdd")
	public void clickAdd() {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Add " + "Barcode");
		try {
			log.info(getOperationLogMessage(pageName, "Add Biller Service/ Barcode", ""));
			validateInputId();
			BillerRef billerRef = lbInputId.getSelectedItem().getValue();
			if (billerRef.getREF_ID() != -1) {
				Dialog.openBw1450MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1415_REDIRECT_PATH), billerServiceId, billerRef.getREF_ID(), -1);
			}
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Add Biller Service/ Barcode", ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Add", requestDate, ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
		setTxLogMessage(pageCode + "#Add", requestDate, "");
	}

	@Listen("onClick = #btBack")
	public void back() {
		log.info(getOperationLogMessage(pageName, "Back", ""));
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
			log.error(getErrorLogMessage(pageName, "Back", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			tabInputForm = null;
		}
	}
	
	@Listen("onClick = #btNext")
	public void next() {
		log.info(getOperationLogMessage(pageName, "Next", ""));
		Page parent = null;
		Tab tabImage = null;
		try {
			parent = this.getSelf().getDesktop().getPageIfAny("pBw1410");
			log.info("Parent Page:" + parent);
			if (parent == null) {
				return;
			}
			tabImage = (Tab) parent.getFellow("tabImage");
			Event event = new Event("onSelect");
			Events.sendEvent(tabImage, event);

		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Next", ex));
			showErrorMessage(ex);
		} finally {
			parent = null;
			tabImage = null;
		}
	}

	@Listen("onEdit= #gdBarcode")
	public void editBarcode(ForwardEvent event) {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Edit Barcode " + "Barcode");
		Row row = new Row();
		try {
			row = (Row) event.getOrigin().getTarget();
			log.info(getOperationLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue()));
			Dialog.openBw1510MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1415_REDIRECT_PATH), row.getValue().toString());
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Edit", "EDIT ID::" + row.getValue(), ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#EditBarcode", requestDate, ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
		setTxLogMessage(pageCode + "#EditBarcode", requestDate, "");
	}

	@Listen("onEditInput = #gdDetail")
	public void editInput(ForwardEvent event) {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Edit Input " + "Barcode");
		Row row = new Row();
		try {
			row = (Row) event.getOrigin().getTarget();
			log.info(getOperationLogMessage(pageName, "Edit Input", "EDIT ID::" + row.getValue()));
			Dialog.openBw1440MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1415_REDIRECT_PATH), Integer.parseInt(row.getValue().toString()), billerFormId, billerServiceId);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Edit Input", "EDIT ID::" + row.getValue(), ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#EditInput", requestDate, ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
		setTxLogMessage(pageCode + "#EditInput", requestDate, "");
	}

	@Listen("onEditBarcode = #gdDetail")
	public void editBarcodeDetail(ForwardEvent event) {
		final Date requestDate = AppUtil.getCurrent();
		checkSessionTimeOut("Edit Barcode Detail " + "Barcode");
		Row row = new Row();
		try {
			row = (Row) event.getOrigin().getTarget();
			log.info(getOperationLogMessage(pageName, "Edit Barcode Detail", "EDIT ID::" + row.getValue()));
			String[] oids = row.getValue().toString().split("#");
			String refId = "";
			String barRefId = "";
			if (oids.length == 2) {
				refId = oids[0];
				barRefId = oids[1];
				Dialog.openBw1450MedalDialog(AppConfiguration.getValue(AppConfiguration.BW1415_REDIRECT_PATH), billerServiceId, Integer.parseInt(refId), Integer.parseInt(barRefId));
			}

		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "Edit Barcode Detail", "EDIT ID::" + row.getValue(), ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#EditBarcodeDail", requestDate, ex);
		} finally {
			log.info(getStopLogMessage(pageName));
		}
		setTxLogMessage(pageCode + "#EditBarcodeDail", requestDate, "");
	}

	@Listen("onRefresh = #gdBarcode")
	public void refreshBarcode() {
		log.info(getOperationLogMessage(pageName, "refreshBarcode", ""));
		try {
			loadBarcodeResultData();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "refreshBarcode", ex));
			showErrorMessage(ex);
		}
	}

	@Listen("onRefresh = #gdDetail")
	public void refreshDetail() {
		log.info(getOperationLogMessage(pageName, "refreshDetail", ""));
		try {
			loadBarcodeDetailResultData();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "refreshDetail", ex));
			showErrorMessage(ex);
		}
	}

	/* ## Send Input ID Event ## */
	@Listen("onSelect = #lbInputId")
	public void onSelectInputId() {
		log.info(getOperationLogMessage(pageName, "onSelectInputId", ""));
		try {
			Clients.clearWrongValue(lbInputId);
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onSelectInputId", ex));
			showErrorMessage(ex);
		}
	}

	/* ## Validation ## */
	private void validateInputId() throws WrongValueException, Exception {
		BillerRef value = lbInputId.getSelectedItem().getValue();
		Validation.isSelectDropdownList(lbInputId, value.getREF_ID(), lInputId.getValue());
	}

	@Listen("onRefreshInputId = #lbInputId")
	public void onRefreshInputId() {
		log.info(getOperationLogMessage(pageName, "onRefreshInputId", ""));
		try {
			loadListBoxInputId();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "onRefreshInputId", ex));
			showErrorMessage(ex);
		}
	}

}
