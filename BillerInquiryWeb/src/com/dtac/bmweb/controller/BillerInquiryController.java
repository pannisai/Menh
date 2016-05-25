package com.dtac.bmweb.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import mfs.biller.persistence.bean.BillerService;
import mfs.biller.persistence.bean.DropdownlistBillservice;
import mfs.biller.persistence.bean.GWMasterTransParam;
import mfs.biller.util.DateTimeUtil;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.SimpleDateConstraint;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;

import com.dtac.bmweb.common.AppConfig;
import com.dtac.bmweb.common.Authorization;
import com.dtac.bmweb.common.BillerInquiryConfig;
import com.dtac.bmweb.common.ReportConstants;
import com.dtac.bmweb.common.SearchPageController;
import com.dtac.bmweb.data.BillerInquirySO;
import com.dtac.bmweb.exception.BillerManageWebException;
import com.dtac.bmweb.factory.BillerwebServiceFactory;
import com.dtac.bmweb.listmodel.BillerInquiryListModel;
import com.dtac.bmweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.bmweb.service.BillerInquiryService;
import com.dtac.bmweb.service.SelectBoxService;
import com.dtac.bmweb.util.AppUtil;

public class BillerInquiryController extends SearchPageController {
	private Logger log = Logger.getLogger(BillerInquiryController.class);

	@Wire
	private Datebox dbPaymentDateFrom;
	@Wire
	private Datebox dbPaymentDateTo;
	@Wire
	private Listbox lbBillerService;	
	@Wire
	private Button btSearch;
	@Wire
	private Button btExport;
	@Wire
	private Timebox dbPaymentTimeFrom;
	@Wire
	private Timebox dbPaymentTimeTo;
	@Wire
	private Listbox lbChannelService;
	@Wire
	private Textbox lTotalAmount;
	@Wire
	private Listbox lbNetworkService;
	@Wire
	private Row rNetworkService;
	
	public String billerColumn;
	public String billerValue;
	
	private static DecimalFormat numberFormat = new DecimalFormat("#,###,###,##0.00");

	private GWMasterTransParam criteria = new GWMasterTransParam();
	private final ListModel<BillerInquirySO> emptyListModel = new SimpleListModel<BillerInquirySO>(new ArrayList<BillerInquirySO>());
	private final Map<Integer, Integer> billerChannelMap = new HashMap<Integer, Integer>();
	
	public BillerInquiryController() {
		super("Biller Inquiry");
	}

	public void doBeforeComposeChildren(Component comp) {
		try {
			super.doBeforeComposeChildren(comp);
			readBillerConfig();
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "doBeforeComposeChildren", ex));
			showErrorMessage(ex);
		}
	}

	private void readBillerConfig() {
		log.info(getOperationLogMessage(pageName, "Read Biller Config", ""));
		BillerInquiryService billerInquiryService = null;
		BillerService billerService = new BillerService();
		Integer serviceId = 1;
		try {
			billerInquiryService = BillerwebServiceFactory.getBillerInquiryService();
			if (getServiceIds().size() > 0) {
				serviceId = getServiceIds().get(0);
			}
			billerService = billerInquiryService.getBillerServiceByID(serviceId, getUserInfo());
			if (billerService != null && !AppUtil.isEmpty(billerService.getBLLR_SRVC_CODE())) {
//				log.info("Column::" + billerService.getBLLR_SRVC_CODE() + "_COLUMN");
//				log.info("Value::" + billerService.getBLLR_SRVC_CODE() + "_VALUE");
				billerColumn = BillerInquiryConfig.searchValue(billerService.getBLLR_SRVC_CODE() + "_COLUMN");
				billerValue = BillerInquiryConfig.searchValue(billerService.getBLLR_SRVC_CODE() + "_VALUE");
				log.info("billerColumn::" + billerColumn);
				log.info("billerValue::" + billerValue);
			}
			if (AppUtil.isEmpty(billerColumn) || AppUtil.isEmpty(billerValue)) {
				throw new BillerManageWebException("Can't find biller config:" + "Service Name=" + billerService.getBLLR_SRVC_NAME_EN());
			}
			setBillerChannelMap(billerInquiryService,getServiceIds());
		} catch (Exception ex) {
			throw new BillerManageWebException(ex);
		} finally {
			billerInquiryService = null;
			billerService = null;
		}
		log.info(getStopLogMessage(pageName));

	}

	@Override
	protected void init(Component com) throws Exception {
		// TODO Auto-generated method stub
		setupPage();
		gridRender();
		generateGridColumn();
	}

	@Override
	protected void setupPageFromSession() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("rawtypes")
	private void setupPage() {
		log.info(getOperationLogMessage(pageName, "Setup Page", ""));
		SelectBoxService selectBoxService = null;
		BillerServiceListModel billerServiceListModel =null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			billerServiceListModel = selectBoxService.getBillerServiceListModel(getServiceIds());
			if (getServiceIds().size() != 1 ) {
				billerServiceListModel.addItemAll();
			}
			billerServiceListModel.addDataToSelection(0);
			lbBillerService.setModel(billerServiceListModel);		
			
			// add channel service list data
			genChannelListBox();
			genNetworkService();
			/* ## Check Permission## */
			chkExportPermission(btExport);
			btExport.setDisabled(true);
			/* ## Set default date time */
			setDefaultSearchDateTime();
			/* ## Set default Total Amount */
			lTotalAmount.setValue(numberFormat.format(new BigDecimal("0")).toString());
		} catch (Exception ex) {
			throw new BillerManageWebException(ex);
		} finally {
			selectBoxService = null;
			billerServiceListModel = null;
		}
		log.info(getStopLogMessage(pageName));
	}

	private void setDefaultSearchDateTime() {
		try {
			dbPaymentDateFrom.setValue(DateTimeUtil.minusDate(DateTimeUtil.getCurrent(), 1));
			dbPaymentDateTo.setValue(DateTimeUtil.getCurrent());							
			Date fromDate = new Date();
			fromDate.setHours(0);
			fromDate.setMinutes(0);
			fromDate.setSeconds(0);
			Date toDate = new Date();
//			toDate.setHours(23);
//			toDate.setMinutes(59);
//			toDate.setSeconds(59);
			dbPaymentTimeFrom.setValue(fromDate);
			dbPaymentTimeTo.setValue(toDate);
			
			setDateConstraint();
		} catch (Exception ex) {
			throw new BillerManageWebException(ex);
		} finally {
		}
	}

	private void setDateConstraint() {
		String serviceCode = null;
		String dateStr = null;
		SimpleDateConstraint sd = null;
		try {
			int searchbackdate = 0;
			if(AppConfig.getValue("searchbackdate") != null && AppUtil.isNumber(AppConfig.getValue("searchbackdate"))){
				searchbackdate = Integer.parseInt(AppConfig.getValue("searchbackdate"));		
			}				
			Authorization auth = getAuthorization();			
			if (auth != null) {
				serviceCode = auth.getServiceCode();
				if(AppConfig.getValue(serviceCode+"_searchbackdate") != null && AppUtil.isNumber(AppConfig.getValue(serviceCode+"_searchbackdate"))){
					searchbackdate = Integer.parseInt(AppConfig.getValue(serviceCode+"_searchbackdate"));			
				}
			}		
			if(searchbackdate > 0){
				dateStr = DateTimeUtil.getDateTime(DateTimeUtil.minusDate(DateTimeUtil.getCurrent(), searchbackdate-1), "yyyyMMdd");
				sd = new SimpleDateConstraint("after " + dateStr + ",no future");				
			}else{
				sd = new SimpleDateConstraint("no past, no future, no today");
			}
			dbPaymentDateFrom.setConstraint(sd);
			dbPaymentDateTo.setConstraint(sd);
		} catch (Exception ex) {
			throw new BillerManageWebException(ex);
		} finally {
			serviceCode = null;
			dateStr = null;
			sd = null;
		}
	}

	private void gridRender() {
		log.info(getOperationLogMessage(pageName, "Grid Render", ""));
		try {
			gdResult.setRowRenderer(new RowRenderer<Object>() {
				public void render(Row row, Object data, int index) throws Exception {
					if (data == null)
						return;
					Columns columns = row.getGrid().getColumns();
					if (columns.getChildren() == null)
						return;

					createRowData(row, (BillerInquirySO) data, index);
				}
			});
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private void generateGridColumn() {
		log.info(getOperationLogMessage(pageName, "Generate Grid Column", ""));
		try {
			Columns columns = gdResult.getColumns();
			if (columns.getChildren() == null)
				return;
			columns.detach();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("columns", createStringList(billerColumn));
			gdResult.appendChild(Executions.createComponents("/page/billerinquiry/billerInquiryColumns.zul", gdResult, params));
			columns = gdResult.getColumns();
			if (!AppUtil.isEmpty(columns.getChildren())) {

				Column column = (Column) columns.getChildren().get(0);
				column.setWidth("50px");
				Column lastColumn=(Column)columns.getLastChild();
//				log.debug("LastColumn:"+lastColumn.getLabel());
				if(lastColumn.getLabel().equals("Amount")){
					lastColumn.setWidth("180px");
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {

		}
		log.info(getStopLogMessage(pageName));
	}

	private List<String> createStringList(String configValue) {
		String columnStr = null;
		String[] columns = null;
		List<String> result = new ArrayList<String>();
		try {
			// columnStr = BillerInquiryConfig.getValue(billerColumnKey);
			columnStr = configValue;
			if (AppUtil.isEmpty(columnStr)) {
				return new ArrayList<String>();
			}

			columns = columnStr.split("\\|");
			for (String column : columns) {
				result.add(AppUtil.trim(column));
			}
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			throw new RuntimeException(ex);
		} finally {
			columnStr = null;
			columns = null;
		}

		return result;
	}

	// private List<String> createColumnStringList(String billerValueConfig) {
	// return createStringList(billerValueConfig);
	// }
	// private List<String> createValueStringList(String billerValueConfig) {
	// return createStringList(billerValueConfig);
	// }

	private void createRowData(Row row, BillerInquirySO billerInquiryForm, int index) {
		log.info(getOperationLogMessage(pageName, "Create Row Data", ""));
		try {
			List<String> columns = createStringList(billerColumn);
			List<String> values = createStringList(billerValue);
			if (AppUtil.isEmpty(columns) || AppUtil.isEmpty(values)) {
				return;
			}
			if (columns.size() != values.size()) {
				return;
			}
			for (int i = 0; i < values.size(); i++) {
				String varName = values.get(i);
				log.debug("varName::" + varName);
				if (varName.equalsIgnoreCase("NO.")) {
//					log.debug("Set NO." + index + 1);
					Hlayout hlayout = new Hlayout();
					hlayout.appendChild(new Label((index + 1) + (paging.getActivePage() * paging.getPageSize()) + ""));
					hlayout.setSclass("center");
					row.appendChild(hlayout);
				} else if (varName.equalsIgnoreCase("TRNS_ID")) {
//					log.debug("Set TRNS_ID" + i);
					row.appendChild(new Label(billerInquiryForm.getTransId()));
				} else if (varName.equalsIgnoreCase("TRNS_DTTM")) {
//					log.debug("Set TRNS_DTTM." + i);
					try {
						row.appendChild(new Label(AppUtil.toString(billerInquiryForm.getTransDate())));
					} catch (Exception npe) {

					}
				} else if (varName.equalsIgnoreCase("TRNS_REF1")) {
//					log.debug("Set TRNS_REF1" + i);
					row.appendChild(new Label(billerInquiryForm.getRef1()));
				} else if (varName.equalsIgnoreCase("TRNS_REF2")) {
//					log.debug("Set TRNS_REF2" + i);
					row.appendChild(new Label(billerInquiryForm.getRef2()));
				} else if (varName.equalsIgnoreCase("TRNS_REF3")) {
//					log.debug("Set TRNS_REF3" + i);
					row.appendChild(new Label(billerInquiryForm.getRef3()));
				} else if (varName.equalsIgnoreCase("TRNS_REF4")) {
//					log.debug("Set TRNS_REF4" + i);
					row.appendChild(new Label(billerInquiryForm.getRef4()));
				} else if (varName.equalsIgnoreCase("TRNS_REF5")) {
//					log.debug("Set TRNS_REF5" + i);
					row.appendChild(new Label(billerInquiryForm.getRef5()));
				} else if (varName.equalsIgnoreCase("TRNS_PAID_AMNT")) {
//					log.debug("Set TRNS_PAID_AMNT" + i);
					try {
						Hlayout hlayout = new Hlayout();
						hlayout.appendChild(new Label(AppUtil
								.toString(billerInquiryForm.getPaidAmount())));
						hlayout.setSclass("right");
						row.appendChild(hlayout);
					} catch (Exception ex) {
						log.info(ex.getMessage());
					}
				} else if (varName.equalsIgnoreCase("TRNS_CUST_NAME_TH")) {
//					log.debug("Set TRNS_CUST_NAME_TH" + i);
					row.appendChild(new Label(billerInquiryForm.getCustNameTH()));
				} else if (varName.equalsIgnoreCase("TRNS_FIELD2")) {
//					log.debug("Set TRNS_FIELD2" + i);
					row.appendChild(new Label(billerInquiryForm.getTransField2()));
				} else if (varName.equalsIgnoreCase("TRNS_CRNT_BLNC")) {
//					log.debug("Set TRNS_CRNT_BLNC" + i);
					row.appendChild(new Label(AppUtil.toString(billerInquiryForm.getCRNCBLNC())));
				} else if (varName.equalsIgnoreCase("TRNS_EXTR_AMNT")) {
//					log.debug("Set TRNS_EXTR_AMNT" + i);
					row.appendChild(new Label(billerInquiryForm.getExtraAmount()));
				} else if (varName.equalsIgnoreCase("TRNS_SRVC_CODE")) {
//					log.debug("Set TRNS_SRVC_CODE" + i);
					row.appendChild(new Label(billerInquiryForm.getServiceName()));
				} else if (varName.equalsIgnoreCase("CHANNEL_SERVICE")) {
//					log.debug("Set TRNS_SRVC_CODE" + i);
					row.appendChild(new Label(billerInquiryForm.getChannelService()));
				}  else if (varName.equalsIgnoreCase("REVERSAL_FLAG")) {
//					log.debug("Set REVERSAL_FLAG" + i);
					row.appendChild(new Label(billerInquiryForm.getReversalFlag()));
				} else {
					row.appendChild(new Label(""));
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
		}
		log.info(getStopLogMessage(pageName));
	}

	@Listen("onClick = #btSearch")
	public void search() throws Exception {
		log.info(getOperationLogMessage(pageName, "Search", ""));
		checkSessionTimeOut("Search " + pageName);
		clearGrid();
		BillerInquiryService billerInqService = null;
		DropdownlistBillservice billerService = new DropdownlistBillservice();
		List<String> billerServiceCodes = new ArrayList<String>();
		Authorization auth = null;
		String serviceCode = null;	
		try {
			billerService = lbBillerService.getSelectedItem().getValue();
			Date fromDate = dbPaymentDateFrom.getValue();
			Date toDate = dbPaymentDateTo.getValue();			
			Date fromTime = dbPaymentTimeFrom.getValue();
			Date toTime = dbPaymentTimeTo.getValue();		
			if(fromTime != null){
				fromDate.setHours(fromTime.getHours());
				fromDate.setMinutes(fromTime.getMinutes());
				fromDate.setSeconds(fromTime.getSeconds());
			}
			if(toTime != null){
				toDate.setHours(toTime.getHours());
				toDate.setMinutes(toTime.getMinutes());
				toDate.setSeconds(toTime.getSeconds());
			}

			criteria = new GWMasterTransParam();
			criteria.setFROM_DTTM(fromDate);
			criteria.setTO_DTTM(toDate);
			if (billerService.getBLLR_SRVC_ID() != null) {
				checkBllrSrvcCode(billerService.getBLLR_SRVC_CODE());
			} else {
				for (Listitem item : lbBillerService.getItems()) {
					billerService = item.getValue();
					if (billerService.getBLLR_SRVC_ID() != null) {
						billerServiceCodes.add(billerService.getBLLR_SRVC_CODE());
					}
				}
				criteria.setListparam(billerServiceCodes);
			}
			
			//Configurable implementation : file (billerInquiryWebConfig.properties)
			auth = getAuthorization();			
			if (auth != null) {
				serviceCode = auth.getServiceCode();
				if(AppConfig.getValue(serviceCode+"_enableAuthorize") != null && "true".equalsIgnoreCase(AppConfig.getValue(serviceCode+"_enableAuthorize"))){					
					if(auth.getBranch() != null && !"".equalsIgnoreCase(auth.getBranch())){
						criteria.setBRANCH(auth.getBranch());
					}									
				}
			}
			
			DropdownlistBillservice channelService = lbChannelService
					.getSelectedItem().getValue();
			if (null != channelService.getBLLR_SRVC_CODE()) {
				criteria.setCHANNEL_SERVICE(channelService.getBLLR_SRVC_CODE());
			}
			
			billerInqService = BillerwebServiceFactory.getBillerInquiryService();			
			BigDecimal totalAmount=billerInqService.getBillerInquiryTotalAmount(criteria);
			log.info("Total amount:"+totalAmount);
			lTotalAmount.setValue(AppUtil.isNotNull(totalAmount)?numberFormat.format(totalAmount).toString():numberFormat.format(new BigDecimal("0")).toString());
			Event lTotalAmountEvent = new Event("onRefleshTotalAmount", lTotalAmount);
			Events.sendEvent(lTotalAmountEvent);
			
			BillerInquiryListModel results = new BillerInquiryListModel(paging.getActivePage() + 1, Integer.parseInt(AppConfig.getValue(AppConfig.PAGE_SIZE_KEY)), getUserInfo());
			results.setCriteria(criteria);
			setGridResultModel(results);	
			
	
			if(results != null && results.getItems().size() > 0){
				btExport.setDisabled(false);
			}else{
				btExport.setDisabled(true);
			}
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Service Id", AppUtil.toString(billerService.getBLLR_SRVC_ID()));

			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
		} finally {
			billerService = null;
			billerServiceCodes=null;
		}
		log.info(getStopLogMessage(pageName));
	}
	//Check for TOT
	private void checkBllrSrvcCode(String srvcCode){
		List<String> billerServiceCodes = new ArrayList<String>();
		
		if(srvcCode.equals("0005")){
			DropdownlistBillservice networkService = lbNetworkService.getSelectedItem().getValue();
			if(networkService.getBLLR_SRVC_CODE() == null){
				billerServiceCodes.add("0005");
				billerServiceCodes.add("0052");
				criteria.setListparam(billerServiceCodes);
			}else{
				criteria.setNETWORK_SERVICE(networkService.getBLLR_SRVC_CODE());
				criteria.setTRNS_SRVC_CODE(srvcCode);
			}	
		}else{
			criteria.setTRNS_SRVC_CODE(srvcCode);
		}
		
	}
	@Listen("onRefleshTotalAmount = #lTotalAmount")
	public void refreshTotalAmount() {
		log.info("refreshResultGrid:"+lTotalAmount.getValue());
		paging.invalidate();
	}
	@Listen("onClick = #btReset")
	public void onReset() {
		log.info(getOperationLogMessage(pageName, "Reset", ""));
		dbPaymentDateFrom.setValue(null);
		dbPaymentDateTo.setValue(null);
		dbPaymentTimeFrom.setValue(null);
		dbPaymentTimeTo.setValue(null);
		lbBillerService.setSelectedIndex(0);
		genChannelListBox();
		/* ## Set default date time */
		setDefaultSearchDateTime();
		clearGrid();
		btExport.setDisabled(true);
		lTotalAmount.setValue(numberFormat.format(new BigDecimal("0")).toString());
		log.info(getStopLogMessage(pageName));
	}
	
	@Listen("onClick = #btExport")
	public void onExport() {
		log.info(getOperationLogMessage(pageName, "Export", ""));
		checkSessionTimeOut("Export " + pageName);
		
		BillerInquiryService billerInqService = null;
		List<BillerInquirySO> billerInquirySOs = null;
		
		String header = "No.|Payment Ref.|Payment Date|Biller Service (Biller name)|Channel|Agent Type|Contact Account|Invoice No.|Due Date|Paid Amount|Customer Name|Channel Service|Reversal flag";
		String field = "row|transId|crtdDttm|serviceName|channel|agentType|ref1|ref2|ref3|paidAmount|custNameTH|channelService|reversalFlag";		
		String criterias = "";
		byte[] bytes= null;
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy", new Locale("en", "EN"));
		String filename = null;
		String contentType = null;
				
		try{	
			billerInqService = BillerwebServiceFactory.getBillerInquiryService();
			criteria.setEXPORT("Y");
			billerInquirySOs = billerInqService.searchBillerInquiry(criteria, 0, 0);
			
			if(!AppUtil.isEmpty(billerInquirySOs) && billerInquirySOs.size() > 0){
				if(!AppUtil.isEmpty(criteria.getFROM_DTTM())){
					criterias += "Payment Date From : "+sdf.format(criteria.getFROM_DTTM());
				}
				if(!AppUtil.isEmpty(criteria.getTO_DTTM())){
					criterias += (!AppUtil.isEmpty(criterias)?"|":"")+"Payment Date To : "+sdf.format(criteria.getTO_DTTM());
				}
				if(!AppUtil.isEmpty(criteria.getTRNS_SRVC_CODE())){
					criterias += (!AppUtil.isEmpty(criterias)?"|":"")+"Biller Service : "+criteria.getTRNS_SRVC_CODE();
				}
				
				bytes = generateCsvFile(billerInquirySOs, BillerInquirySO.class, criterias, header, field);
				if(bytes != null){															
					filename = sdf.format(new Date())+"_Biller_inquiry_Export."+ReportConstants.TYPE_CSV;
					contentType = ReportConstants.TYPE_CSV_CONTENT;
					
					Filedownload.save(bytes, contentType, filename);
					log.debug("File Size::" + bytes.length);
					//internetProfileBean.setDownloadFile("_export('export_excel');");
				}
			}else{
				//internetProfileBean.setDownloadFile("");
			}
			criteria.setEXPORT(null);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			criterias = null;
		}

		log.info(getStopLogMessage(pageName));
	}

	@Listen("onChange=#dbPaymentDateFrom")
	public void onChangePaymentDate() {
		Date date = dbPaymentDateFrom.getValue();
		log.debug("Date:" + date.toString());

		String dateStr;
		try {
			dateStr = DateTimeUtil.getDateTime(date, "yyyyMMdd");
			SimpleDateConstraint sd = new SimpleDateConstraint("after " + dateStr + ",no future");
			dbPaymentDateTo.setConstraint(sd);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		}
	}
	
	
//	@Listen("onLastInputRender=listbox")
//	public void onAfterRender() {
//		log.info(getOperationLogMessage(pageName, "Start page from session", ""));
//		try {
//			setupPageFromSession();
//		} catch (BillerWebSessionTimeOutException stex) {
//			throw stex;
//		} catch (Exception ex) {
//			log.error(getErrorLogMessage(pageName, "Start page from session", ex));
//			showErrorMessage(ex);
//		} finally {
//
//		}
//	}
	
	

	
	private void clearGrid(){
		try {
			setGridResultModel(emptyListModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Listen("onSelect = #lbBillerService")
	public void onBillerServiceSelect() throws Exception {
		genChannelListBox();
		genNetworkService();
		
	}

	public Textbox getlTotalAmount() {
		return lTotalAmount;
	}

	public void setlTotalAmount(Textbox lTotalAmount) {
		this.lTotalAmount = lTotalAmount;
	}
	
	private void setBillerChannelMap(BillerInquiryService billerInquiryService,
			List<Integer> billerServiceIds) {
		BillerService billerService = new BillerService();
		try {
			for (Integer serviceId : billerServiceIds) {
				billerService = billerInquiryService.getBillerServiceByID(
						serviceId, getUserInfo());
				if (billerService != null) {
					billerChannelMap.put(serviceId,
							billerService.getADDT_CHNL_SRVC());
				}
			}
		} catch (Exception ex) {
			throw new BillerManageWebException(ex);
		}
	}
	
	
	private void genChannelListBox() {
		try {
			DropdownlistBillservice billerService = null;
			BillerServiceListModel channelServiceListModel = null;
			if (null == lbBillerService.getSelectedItem()) {
				int serviceId = 1;
				if (billerChannelMap.size() == 1) {
					for (Map.Entry<Integer, Integer> tempMap : billerChannelMap
							.entrySet()) {
						serviceId = tempMap.getValue();
					}
				}
				channelServiceListModel = BillerwebServiceFactory.getSelectBoxService().getChannelServiceListModel(serviceId);
			} else {
				billerService = lbBillerService.getSelectedItem().getValue();
				Integer serviceId = billerChannelMap.get(billerService
						.getBLLR_SRVC_ID());
				if(null == serviceId){
					serviceId = 1;
				}
				channelServiceListModel = BillerwebServiceFactory
						.getSelectBoxService().getChannelServiceListModel(
								serviceId);
			}
			lbChannelService.setModel(channelServiceListModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void genNetworkService(){
		try{
			DropdownlistBillservice billerService = null;
			BillerServiceListModel networkServiceListModel = BillerwebServiceFactory.getSelectBoxService().getNetworkServiceListModel();
			lbNetworkService.setModel(networkServiceListModel);
			if (null == lbBillerService.getSelectedItem()) {
				rNetworkService.setVisible(false);
				if(getServiceIds().size()==1){
					if(getServiceIds().get(0)==5){
						rNetworkService.setVisible(true);
					}
				}
			} else {
				billerService = lbBillerService.getSelectedItem().getValue();
				if(billerService.getBLLR_SRVC_CODE() != null && billerService.getBLLR_SRVC_CODE().equals("0005")){
					rNetworkService.setVisible(true);
				}
				else{
					rNetworkService.setVisible(false);
				}			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
