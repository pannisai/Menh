package com.dtac.billerweb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mfs.biller.persistence.bean.BatchMastFileParam;
import mfs.biller.persistence.bean.BillerMaster;
import mfs.biller.persistence.bean.BillerServiceParam;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Selectbox;

import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.SearchPageController;
import com.dtac.billerweb.data.BatchMastFileStatusDO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.listmodel.FileDownloadListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerMasterListModel;
import com.dtac.billerweb.service.FileDownloadInquiryService;
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;

public class FileDownloadInquiryController extends SearchPageController {
	public FileDownloadInquiryController() {
		super("File Download Inquiry","");
		// TODO Auto-generated constructor stub
	}

	private Logger log = Logger.getLogger(FileDownloadInquiryController.class);

	@Wire
	private Datebox dbToDate;

	@Wire
	private Datebox dbFromDate;

	@Wire
	private Selectbox sbBillerCode;

	@Wire
	private Selectbox sbStatus;

	@Wire
	private Grid gdResult;

	@Wire
	private Paging paging;

	private FileDownloadInquiryService fileDownloadInquiryService = BillerwebServiceFactory.getFileDownloadInquiryService();

	@Override
	protected void init(Component com) throws Exception {
		// TODO Auto-generated method stub
		setupPage();
	}

	@Override
	protected void setupPageFromSession() {
		// TODO Auto-generated method stub

	}

	private void setupPage() {
		log.info("Set up Page");
		SelectBoxService selectBoxService = null;
		BillerMasterListModel billerMasterListModel = null;
		try {
			selectBoxService = BillerwebServiceFactory.getSelectBoxService();
			billerMasterListModel = selectBoxService.getBillerCodeListModel();
			billerMasterListModel.addItemAll();
			sbBillerCode.setModel(billerMasterListModel);
			sbStatus.setModel(selectBoxService.getBatchMastFileStatusListModel());

		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			selectBoxService = null;
			billerMasterListModel = null;
		}
	}

	/* ## Event ## */
	@Listen("onClick = #btSearch")
	public void search() throws Exception {
		log.info(getOperationLogMessage(pageName, "Search", ""));

		FileDownloadInquiryService fileDownloadInquiryService = null;
		FileDownloadListModel result = null;
		BillerMaster billercode = null;
		BatchMastFileStatusDO status = null;
		try {
			fileDownloadInquiryService = BillerwebServiceFactory.getFileDownloadInquiryService();
			BillerServiceParam criteria = new BillerServiceParam();

			Date fromDate = dbFromDate.getValue();
			Date toDate = dbToDate.getValue();
			billercode = (BillerMaster) sbBillerCode.getModel().getElementAt(sbBillerCode.getSelectedIndex());
			status = (BatchMastFileStatusDO) sbStatus.getModel().getElementAt(sbStatus.getSelectedIndex());

			BatchMastFileParam condition = new BatchMastFileParam();
			condition.setFROM_DTTM(fromDate);
			condition.setTO_DTTM(toDate);
			String billMstrId = "";
			if (billercode.getBLLR_MSTR_ID() != null) {
				billMstrId = billercode.getBLLR_MSTR_ID() + "";
			}
			condition.setBTCH_DEST_CODE(billMstrId);
			condition.setBTCH_MAST_FILE_STTS(status.getId());

			result = new FileDownloadListModel(1, Integer.parseInt(AppConfiguration.getValue(AppConfiguration.PAGE_SIZE_KEY)), getUserInfo());
			result.setCondition(condition);
			setGridResultModel(result);

		} catch (Exception ex) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Biller Code ID", AppUtil.toString(billercode.getBLLR_MSTR_ID()));
			params.put("Status", status.getValue());
			log.error(getErrorLogMessage(pageName, "Search", params, ex));
			showErrorMessage(ex);
		} finally {
			fileDownloadInquiryService = null;
		}
	}
}
