package com.dtac.billerweb.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.DropdownlistBillservice;
import mfs.biller.util.DateTimeUtil;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.SimpleDateConstraint;
import org.zkoss.zul.SimpleListModel;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppConstant;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.Authorization;
import com.dtac.billerweb.common.ReportConstants;
import com.dtac.billerweb.common.SearchPageController;
import com.dtac.billerweb.data.BillerCollectionFee;
import com.dtac.billerweb.data.BillerCollectionFeeAbsorbDTO;
import com.dtac.billerweb.data.BillerCollectionFeeAbsorbData;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.exception.BillerWebValidationException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;
import com.dtac.billerweb.util.HttpClient;
import com.dtac.billerweb.validation.Validation;

@SuppressWarnings("serial")
public class BW1800Controller extends SearchPageController {
	private final ListModel<BillerCollectionFeeAbsorbData> emptyListModel = new SimpleListModel<BillerCollectionFeeAbsorbData>(new ArrayList<BillerCollectionFeeAbsorbData>());
	
	public BW1800Controller() {
		super("Absorb Fee Report","1800");
	}

	private Logger log = Logger.getLogger(BW1800Controller.class);

	@Wire
	private Datebox dbToDate;
	@Wire
	private Datebox dbFromDate;
	@Wire
	private Listbox lbBillerService;
	@Wire
	private Button btSearch;
	@Wire
	private Button btReset;
	@Wire
	private Button btExportToAP;
	@Wire
	private Button btExportToBiller;  

	@Override
	protected void init(Component com) throws Exception {
		setupPage();
	}

	@SuppressWarnings("rawtypes")
	private void setupPage() throws BillerWebSessionTimeOutException {
		log.info("Set up Page");
		
		SelectBoxService selectBoxService = null;
		BillerServiceListModel billerServiceListModel = null;
		try {
			/* ## Check Permission## */
			chkSearchPermission(btSearch);
			gdResult.setPaginal(paging);
			
			/* ## Check Permission## */
			chkExportPermission(btExportToAP);
			btExportToAP.setDisabled(true);
			chkExportPermission(btExportToBiller);
			btExportToBiller.setDisabled(true);
			
			/* set selectbox  list */
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			billerServiceListModel=selectBoxService.getBillerServiceListModel();
			billerServiceListModel.addItemSelect();
			lbBillerService.setModel(billerServiceListModel);
			/* ## Set default date time */
			setDefaultSearchDateTime();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerServiceListModel = null;
		}
	}
	
	private void setDefaultSearchDateTime() {
		try {
			dbFromDate.setValue(DateTimeUtil.minusDate(DateTimeUtil.getCurrent(), 2));
			dbToDate.setValue(DateTimeUtil.minusDate(DateTimeUtil.getCurrent(), 1));
			setDateConstraint();
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
		}
	}
	
	private void setDateConstraint() {
		String dateStr = null;
		SimpleDateConstraint sd = null;
		try {
			int searchbackdate = 0;
			if(AppConfiguration.getValue("SEARCH_BACK_DATE") != null && AppUtil.isNumber(AppConfiguration.getValue("SEARCH_BACK_DATE"))){
				searchbackdate = Integer.parseInt(AppConfiguration.getValue("SEARCH_BACK_DATE"));		
			}					
			if(searchbackdate > 0){
				dateStr = DateTimeUtil.getDateTime(DateTimeUtil.minusDate(DateTimeUtil.getCurrent(), searchbackdate), "yyyyMMdd");
				sd = new SimpleDateConstraint("after " + dateStr + ",no future, no today");				
			}else{
				sd = new SimpleDateConstraint("no past, no future, no today");
			}
			dbFromDate.setConstraint(sd);
			dbToDate.setConstraint(sd);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			dateStr = null;
			sd = null;
		}
	}

	protected void setupPageFromSession() {
		// Not Implement, because not redirect page to create or edit
	}

	@SuppressWarnings("unused")
	private void setCurrentFormSession() {
		// Not Implement, because not redirect page to create or edit
	}
	
	/* ## Event ## */
	@SuppressWarnings("deprecation")
	@Listen("onClick = #btSearch")
	public void search() {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Search", ""));
		checkSessionTimeOut("Search " + pageName);
			
		BillerCollectionFeeAbsorbDTO billerCollectionFeeAbsorbDTO = null;
		ListModel<BillerCollectionFeeAbsorbData> billerCollectionFeeAbsorbDataModel = null;
		DropdownlistBillservice dropdownlistBillservice=null;
		
		Authorization auth = null;
		StringBuilder url = null;
		StringBuilder param = null;
		auth = getAuthorization();
		
		Date fromDate = null;
		Date toDate = null;
		String billerShortCode = null;
		
		try {
			// Validate
			clearErrorMessage();
			if ((dbFromDate.getValue() != null) & (dbToDate.getValue() != null)) {
				fromDate = dbFromDate.getValue();
				toDate = dbToDate.getValue();
				Validation.validateEndDateTime(dbToDate, fromDate, toDate, AppMessage.getMessage(AppMessage.MSG_TODATE_MUST_AFTER_FROMDATE, "To Date must equal or after From Date"));
				Validation.validateAddOneMonth(dbToDate, fromDate, toDate, AppMessage.getMessage(AppMessage.MSG_DATE_PERIOD, "The date period is not over than 1 month"));
			}
			dropdownlistBillservice = lbBillerService.getSelectedItem().getValue();
			if(AppUtil.isNotNull(dropdownlistBillservice) && AppUtil.isNotNull(dropdownlistBillservice.getBLLR_SRVC_CODE())){
				billerShortCode = dropdownlistBillservice.getBLLR_SRVC_CODE().toString();
//				if(AppUtil.isNotNull(billerShortCode)){
//					billerShortCode = AppUtil.addPadding(billerShortCode, "0", 4);
//				}
			}else{
				Validation.isSelectDropdownList(lbBillerService, dropdownlistBillservice.getBLLR_SRVC_CODE(), "Service Name");
			}
		
			url = new StringBuilder();
			url.append(AppConfiguration.getValue(AppConfiguration.BILLER_COLLECTION_FEE));
			param = new StringBuilder();
			param.append("reportType=A");
			param.append("&billerShortCode=" + billerShortCode);
			param.append("&fromDate=" + URLEncoder.encode(AppUtil.isNotNull(fromDate)?DateTimeUtil.getDateTime(fromDate, "dd-MM-yyyy"):""));
			param.append("&toDate=" + URLEncoder.encode(AppUtil.isNotNull(toDate)?DateTimeUtil.getDateTime(toDate, "dd-MM-yyyy"):""));	
			param.append("&userName=" + URLEncoder.encode(auth.getUsername()));
			param.append("&refId=" + URLEncoder.encode(auth.getRefId()));
			param.append("&ipAddr=" + URLEncoder.encode(auth.getClientIP()));
			
			//### Test ###
			//param.append("reportType=A&billerShortCode=0001&fromDate=01-06-2015&toDate=26-06-2015&userName=receipt&refId=2014042916221870010.35.206.75&ipAddr=10.35.206.75");
			billerCollectionFeeAbsorbDTO = HttpClient.getBillerFeeAbsorb(url.toString() + "?" + param.toString());
			if(AppUtil.isNotNull(billerCollectionFeeAbsorbDTO)){				
				if(AppUtil.isNotNull(billerCollectionFeeAbsorbDTO.getDataList()) && 
					billerCollectionFeeAbsorbDTO.getDataList().size() > 0 &&
					billerCollectionFeeAbsorbDTO.getErrorCode().equals(AppConfiguration.getValue(AppConfiguration.SUCCESS_CODE))){
					billerCollectionFeeAbsorbDataModel = new SimpleListModel<BillerCollectionFeeAbsorbData>(billerCollectionFeeAbsorbDTO.getDataList());
					setGridResultModel(billerCollectionFeeAbsorbDataModel);	
					btExportToAP.setDisabled(false);
					btExportToBiller.setDisabled(false);
				}else{					
					if(billerCollectionFeeAbsorbDTO.getErrorCode().equals(AppConfiguration.getValue(AppConfiguration.SESS_TIMEOUT_CODE))){
						log.info("Session Timeout Authorization is null");
						goToSessionTimeOutPage();
					}else if(!billerCollectionFeeAbsorbDTO.getErrorCode().equals(AppConfiguration.getValue(AppConfiguration.SESS_TIMEOUT_CODE)) && 
							AppUtil.isNotNull(billerCollectionFeeAbsorbDTO.getErrorMssg())){
						btExportToAP.setDisabled(true);
						btExportToBiller.setDisabled(true);
						showInfomationMessage(billerCollectionFeeAbsorbDTO.getErrorMssg());
						log.debug("errorMssg::" + billerCollectionFeeAbsorbDTO.getErrorMssg());
					}										
				}			
			}
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Service", dropdownlistBillservice.getBLLR_SRVC_CODE().toString());
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Search", requestDate, ex);
		} finally {
			billerCollectionFeeAbsorbDTO = null;
			billerCollectionFeeAbsorbDataModel = null;
			
			auth = null;
			url = null;
			param = null;		
			
			fromDate = null;
			toDate = null;
			billerShortCode = null;
		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Search", requestDate, "");
	}

	@Listen("onSelect = #lbBillerService")
	public void onSelectBillerService() {
		Clients.clearWrongValue(lbBillerService);
	}
	
	@Listen("onClick = #btReset")
	public void reset() {
		log.info("Click Reset " + pageName);
		try {			
			clearGrid();
			clearErrorMessage();
			setDefaultSearchDateTime();
			btExportToAP.setDisabled(true);
			btExportToBiller.setDisabled(true);
			lbBillerService.setSelectedIndex(0);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		} finally {
		}
	}
	
	@Listen("onClick = #btExportToAP")
	public void ExportToAP() {
		this.Export("AP");
	}
	
	@Listen("onClick = #btExportToBiller")
	public void ExportToBiller() {
		this.Export("BL");
	}
	
	@SuppressWarnings("deprecation")
	public void Export(String exportType) {
		Date requestDate = AppUtil.getCurrent();
		log.info(getOperationLogMessage(pageName, "Export", ""));
		checkSessionTimeOut("Export " + pageName);
		
		String filename = null;
		DropdownlistBillservice dropdownlistBillservice=null;
		
		Authorization auth = null;
		StringBuilder url = null;
		StringBuilder param = null;
		auth = getAuthorization();
		
		Date fromDate = null;
		Date toDate = null;
		String billerShortCode = null;
		BillerCollectionFee billerCollectionFee = null;
		
		try {
			// Validate
			clearErrorMessage();
			if ((dbFromDate.getValue() != null) & (dbToDate.getValue() != null)) {
				fromDate = dbFromDate.getValue();
				toDate = dbToDate.getValue();
				Validation.validateEndDateTime(dbToDate, fromDate, toDate, AppMessage.getMessage(AppMessage.MSG_TODATE_MUST_AFTER_FROMDATE, "To Date must equal or after From Date"));
				Validation.validateAddOneMonth(dbToDate, fromDate, toDate, AppMessage.getMessage(AppMessage.MSG_DATE_PERIOD, "The date period is not over than 1 month"));
			}		
			dropdownlistBillservice = lbBillerService.getSelectedItem().getValue();
			if(AppUtil.isNotNull(dropdownlistBillservice) && AppUtil.isNotNull(dropdownlistBillservice.getBLLR_SRVC_CODE())){
				billerShortCode = dropdownlistBillservice.getBLLR_SRVC_CODE().toString();
			}else{
				Validation.isSelectDropdownList(lbBillerService, dropdownlistBillservice.getBLLR_SRVC_CODE(), "Service Name");
			}
		
			url = new StringBuilder();
			url.append(AppConfiguration.getValue(AppConfiguration.BILLER_COLLECTION_FEE_EXPORT));
			param = new StringBuilder();
			param.append("reportType=A");
			param.append("&billerShortCode=" + billerShortCode);
			param.append("&fromDate=" + URLEncoder.encode(AppUtil.isNotNull(fromDate)?DateTimeUtil.getDateTime(fromDate, "dd-MM-yyyy"):""));
			param.append("&toDate=" + URLEncoder.encode(AppUtil.isNotNull(toDate)?DateTimeUtil.getDateTime(toDate, "dd-MM-yyyy"):""));
			param.append("&exportType=" + URLEncoder.encode(exportType));			
			param.append("&userName=" + URLEncoder.encode(auth.getUsername()));
			param.append("&refId=" + URLEncoder.encode(auth.getRefId()));
			param.append("&ipAddr=" + URLEncoder.encode(auth.getClientIP()));
			
			//### Test ###
			//param.append("reportType=A&billerShortCode=0001&fromDate=01-06-2015&toDate=26-06-2015&exportType=BL&userName=receipt&refId=2014042916221870010.35.206.75&ipAddr=10.35.206.75");			
			//param.append("reportType=A&billerShortCode=0001&fromDate=01-06-2015&toDate=26-06-2015&exportType=AP&userName=receipt&refId=2014042916221870010.35.206.75&ipAddr=10.35.206.75");	
			//billerCollectionFee = HttpClient.getBillerFeeReport("http://10.64.119.3:7241/MFSBackOffcService/BillerCollectionFeeExportServlet?reportType=A&billerShortCode=0001&fromDate=01-06-2015&toDate=26-06-2015&exportType=BL&userName=receipt&refId=2014042916221870010.35.206.75&ipAddr=10.35.206.75");
			
			billerCollectionFee = HttpClient.getBillerFeeReport(url.toString()+"?"+param.toString());			
			if(AppUtil.isNotNull(billerCollectionFee)){	
				if(AppUtil.isNotNull(billerCollectionFee.getContentType()) && 
					billerCollectionFee.getContentType().equals(ReportConstants.TYPE_XLS_CONTENT) && 
					AppUtil.isNotNull(billerCollectionFee.getBytes())){															
					if(AppUtil.isNotNull(billerCollectionFee.getFileName())){
						filename = billerCollectionFee.getFileName();	
					}else{
						filename = "Absorb_Fee_Report."+ReportConstants.TYPE_XLSX;	
					}
							
					Filedownload.save(billerCollectionFee.getBytes(), ReportConstants.TYPE_XLS_CONTENT, filename);
					log.debug("File Size::" + billerCollectionFee.getBytes().length);					
				}else if(AppUtil.isNotNull(billerCollectionFee.getContentType()) && 
					billerCollectionFee.getContentType().equals(ReportConstants.TYPE_TEXT_PLAIN_CONTENT) && 
					AppUtil.isNotNull(billerCollectionFee.getMapResults())){				
					if(billerCollectionFee.getMapResults().get("errorCode").equals(AppConfiguration.getValue(AppConfiguration.SESS_TIMEOUT_CODE))){
						log.info("Session Timeout Authorization is null");
						goToSessionTimeOutPage();
					}else if(!billerCollectionFee.getMapResults().get("errorCode").equals(AppConfiguration.getValue(AppConfiguration.SESS_TIMEOUT_CODE)) && 
							AppUtil.isNotNull(billerCollectionFee.getMapResults().get("errorMssg"))){
						btExportToAP.setDisabled(true);
						btExportToBiller.setDisabled(true);
						showInfomationMessage(billerCollectionFee.getMapResults().get("errorMssg"));
						log.debug("errorMssg::" + billerCollectionFee.getMapResults().get("errorMssg"));										
					}
				}
			}		
		} catch (WrongValueException wve) {
			throw new BillerWebValidationException(wve);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Service", dropdownlistBillservice.getBLLR_SRVC_CODE().toString());
			log.error(getErrorLogMessage(pageName, "Export", params, ex));
			showErrorMessage(ex);
			setTxErrLogMessage(pageCode + "#Export", requestDate, ex);
		} finally {
			filename = null;
			
			auth = null;
			url = null;
			param = null;		
			
			fromDate = null;
			toDate = null;
			billerShortCode = null;
			billerCollectionFee = null;
		}
		log.info(getStopLogMessage(pageName));
		setTxLogMessage(pageCode + "#Export", requestDate, "");
	}
	
	private void clearGrid(){
		try {
			setGridResultModel(emptyListModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void clearErrorMessage(){	
		Clients.clearWrongValue(dbToDate);
		Clients.clearWrongValue(dbFromDate);
		Clients.clearWrongValue(lbBillerService);		
	}
	
	private void goToSessionTimeOutPage() {
		try {
			removeSession(AppConstant.S_AUTHORIZATION);
			Executions.getCurrent().sendRedirect(AppConfiguration.getValue(AppConfiguration.SESS_TIMEOUT_ERR_PAGE_URL));
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}
}
