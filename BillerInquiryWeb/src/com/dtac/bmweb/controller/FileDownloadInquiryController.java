package com.dtac.bmweb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.BatchMastFile;
import mfs.biller.persistence.bean.BatchMastFileParam;
import mfs.biller.persistence.bean.DropdownlistBillservice;
import mfs.biller.util.DateTimeUtil;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.SimpleDateConstraint;

import com.dtac.bmweb.common.AppConfig;
import com.dtac.bmweb.common.Authorization;
import com.dtac.bmweb.common.SearchPageController;
import com.dtac.bmweb.exception.BillerManageWebException;
import com.dtac.bmweb.factory.BillerwebServiceFactory;
import com.dtac.bmweb.listmodel.FileDownloadListModel;
import com.dtac.bmweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.bmweb.service.FileDownloadInquiryService;
import com.dtac.bmweb.service.SelectBoxService;
import com.dtac.bmweb.util.AppUtil;

public class FileDownloadInquiryController extends SearchPageController {
	public FileDownloadInquiryController() {
		super("File Download Inquiry");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(FileDownloadInquiryController.class);

	@Wire
	private Datebox dbToDate;

	@Wire
	private Datebox dbFromDate;

	@Wire
	private Listbox lbBillerService;

	@Wire
	private Button btSearch;

	private Map<String,String> serviceNameMap=new HashMap<String, String>();

	@Override
	protected void init(Component com) throws Exception {
		// TODO Auto-generated method stub
		setupPage();
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
			if (getServiceIds().size() > 1) {
				billerServiceListModel.addItemAll();
			}
			billerServiceListModel.addDataToSelection(0);
			lbBillerService.setModel(billerServiceListModel);		
			
			/* ## Set default date time */
			setDefaultSearchDateTime();
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
			dbFromDate.setValue(DateTimeUtil.minusDate(DateTimeUtil.getCurrent(), 1));
			dbToDate.setValue(DateTimeUtil.getCurrent());
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
			dbFromDate.setConstraint(sd);
			dbToDate.setConstraint(sd);
		} catch (Exception ex) {
			throw new BillerManageWebException(ex);
		} finally {
			serviceCode = null;
			dateStr = null;
			sd = null;
		}
	}

	/* ## Event ## */
	@Listen("onClick = #btSearch")
	public void clickSearch() {
		search();
	}

	@SuppressWarnings({ "unused", "deprecation" })
	private void search() {
		log.info(getOperationLogMessage(pageName, "Search", ""));
		checkSessionTimeOut("Search " + pageName);
		FileDownloadInquiryService fileDownloadInquiryService = null;
		FileDownloadListModel result = null;
		DropdownlistBillservice billerService = null;
		List<String> billerServiceCodes = new ArrayList<String>();
		Authorization auth = null;
		String serviceCode = null;
		try {
			fileDownloadInquiryService = BillerwebServiceFactory.getFileDownloadInquiryService();

			billerService = lbBillerService.getSelectedItem().getValue();
			Date fromDate = dbFromDate.getValue();
//			if(fromDate != null){
//				fromDate.setHours(0);
//				fromDate.setMinutes(0);
//				fromDate.setSeconds(0);
//			}
			Date toDate = dbToDate.getValue();
//			if(toDate != null){
//				toDate.setHours(23);
//				toDate.setMinutes(59);
//				toDate.setSeconds(59);
//			}			
			
			BatchMastFileParam criteria = new BatchMastFileParam();
			criteria.setFROM_DTTM(fromDate);
			criteria.setTO_DTTM(toDate);
			String transServiceCode = "";
			if (billerService.getBLLR_SRVC_ID() != null) {
				transServiceCode = billerService.getBLLR_SRVC_CODE() + "";
			} else {
				for (Listitem item : lbBillerService.getItems()) {
					billerService = item.getValue();
					if (billerService.getBLLR_SRVC_ID() != null) {
						billerServiceCodes.add(billerService.getBLLR_SRVC_CODE());
					}
				}
				criteria.setListparam(billerServiceCodes);
			}
			criteria.setBTCH_DEST_CODE(transServiceCode);

			//Configurable implementation : file (billerInquiryWebConfig.properties)
			auth = getAuthorization();			
			if (auth != null) {
				serviceCode = auth.getServiceCode();
				if(AppConfig.getValue(serviceCode+"_enableAuthorize") != null && "true".equalsIgnoreCase(AppConfig.getValue(serviceCode+"_enableAuthorize"))){
					criteria.setEnableAuthorize("Y");					
					if(auth.getAccessTextFileFlag() != null && "Y".equalsIgnoreCase(auth.getAccessTextFileFlag())){
						if(AppConfig.getValue(serviceCode+"_textFilePattern") != null){
							criteria.setTextFilePattern(AppConfig.getValue(serviceCode+"_textFilePattern"));
						}
					}
					if(auth.getAccessReportFileFlag() != null && "Y".equalsIgnoreCase(auth.getAccessReportFileFlag())){
						if(AppConfig.getValue(serviceCode+"_summaryReportFilePattern") != null){
							criteria.setSummaryReportFilePattern(AppConfig.getValue(serviceCode+"_summaryReportFilePattern"));
						}					
					}
					if(AppConfig.getValue(serviceCode+"_detailReportFilePattern") != null){
						String str = AppConfig.getValue(serviceCode+"_detailReportFilePattern");
						if(auth.getBranch() != null){
							str = str.replace("${branch}", auth.getBranch());
						}else{
							str = str.replace("${branch}", "(\\d{4})");
						}						
						criteria.setDetailReportFilePattern(str);
					}
				}else{
					criteria.setEnableAuthorize("N");
				}
				if(AppConfig.getValue("ignoreFilePattern") != null){
					criteria.setIgnoreFilePattern(AppConfig.getValue("ignoreFilePattern"));
				}
			}
			
			result = new FileDownloadListModel(paging.getActivePage() + 1, Integer.parseInt(AppConfig.getValue(AppConfig.PAGE_SIZE_KEY)), getUserInfo());
			result.setCriteria(criteria);
			setGridResultModel(result);
		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Service ID", AppUtil.toString(billerService.getBLLR_SRVC_ID()));
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
		} finally {
			fileDownloadInquiryService = null;
			billerServiceCodes=null;
			auth = null;
			serviceCode = null;
		}
		log.info(getStopLogMessage(pageName));
	}

	@Listen("onDownload = #gdResult")
	public void download(ForwardEvent event) {
		log.info(getOperationLogMessage(pageName, "Download", ""));
		checkSessionTimeOut("Download " + pageName);
		String oid = null;
		String BT_DOWNLOAD_PREFIX = "btDownload_";
		try {
			FileDownloadInquiryService fileDownloadInquiryService = BillerwebServiceFactory.getFileDownloadInquiryService();
			String btID = ((Button) event.getOrigin().getTarget()).getId();
			log.info("Download oid::" + btID);
			if (btID.indexOf(BT_DOWNLOAD_PREFIX) > -1) {
				oid = btID.substring(BT_DOWNLOAD_PREFIX.length());
				BatchMastFile batchMastFile = fileDownloadInquiryService.getBatchMastFileById(oid);
				if (!AppUtil.isEmpty(batchMastFile)) {
					byte[] b = fileDownloadInquiryService.getFileFromBatchMastFileId(oid);
					Filedownload.save(b, "text/html", batchMastFile.getBTCH_MAST_FILE_NAME());
					log.debug("File Size::" + b.length);
				}
			}
		} catch (Exception ex) {
			log.error(getErrorLogMessage(pageName, "download", "Download ID ::" + oid, ex));
			showErrorMessage(ex);
		}
		log.info(getStopLogMessage(pageName));
	}

	@Listen("onClick = #btReset")
	public void onReset() {
		log.info(getOperationLogMessage(pageName, "Reset", ""));
		dbFromDate.setValue(null);
		dbToDate.setValue(null);
		lbBillerService.setSelectedIndex(0);
		/* ## Set default date time */
		setDefaultSearchDateTime();
		log.info(getStopLogMessage(pageName));
	}

	@Listen("onChange=#dbFromDate")
	public void onChangeFromDate() {
		Date date = dbFromDate.getValue();
		log.debug("Date:" + date.toString());

		String dateStr;
		try {
			dateStr = DateTimeUtil.getDateTime(date, "yyyyMMdd");
			SimpleDateConstraint sd = new SimpleDateConstraint("after " + dateStr + ",no future");
			dbToDate.setConstraint(sd);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		}

	}
	
	@Listen("onAfterRender=#lbBillerService")
	public void onAfterRenderBillerService() {
		DropdownlistBillservice billerService=null;
		serviceNameMap=new HashMap<String, String>();
		try {
			for(Listitem item:lbBillerService.getItems()){
				billerService=item.getValue();
				serviceNameMap.put(billerService.getBLLR_SRVC_CODE(), billerService.getBLLR_SRVC_NAME_EN());
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
			showErrorMessage(ex);
		}

	}


	public Map<String, String> getServiceNameMap() {
		return serviceNameMap;
	}

	public void setServiceNameMap(Map<String, String> serviceNameMap) {
		this.serviceNameMap = serviceNameMap;
	}

	

}
