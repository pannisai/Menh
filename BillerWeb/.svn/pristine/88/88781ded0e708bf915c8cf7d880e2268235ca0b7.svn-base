package com.dtac.billerweb.listmodel;

import java.util.List;

import mfs.biller.persistence.bean.BatchMastFileParam;
import mfs.biller.persistence.bean.UserInfoBean;

import org.apache.log4j.Logger;
import org.zkoss.zul.ListModelList;

import com.dtac.billerweb.common.AbstractPagingListModel;
import com.dtac.billerweb.data.FileDownloadInquiryDO;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.service.FileDownloadInquiryService;
import com.dtac.billerweb.util.AppUtil;

public class FileDownloadListModel extends
		AbstractPagingListModel<FileDownloadInquiryDO> {
	private static Logger log = Logger.getLogger(FileDownloadListModel.class);
	private BatchMastFileParam condition;


	public FileDownloadListModel(int currentPage, int pageSize,UserInfoBean userInfo)
			throws Exception {

		super(currentPage, pageSize,userInfo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getTotalSize(UserInfoBean userInfo) throws Exception {
		
		FileDownloadInquiryService fileDownloadInqService = BillerwebServiceFactory
				.getFileDownloadInquiryService();
		if (condition == null) {
			condition = new BatchMastFileParam();
		}
		int rownum = fileDownloadInqService
				.getFileDownloadInquiryRowCount(condition);
		/*--Garbage--*/
		fileDownloadInqService = null;
		return rownum;
	}

	@Override
	protected ListModelList<FileDownloadInquiryDO> getPageData(
			int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		FileDownloadInquiryService fileDownloadInqService = null;
		ListModelList<FileDownloadInquiryDO> listModel = new ListModelList<FileDownloadInquiryDO>();
		try {

			fileDownloadInqService = BillerwebServiceFactory
					.getFileDownloadInquiryService();
			List<FileDownloadInquiryDO> fileDownloadInquiryForms = null;

			if (condition == null) {
				condition = new BatchMastFileParam();
			}

			log.debug("currentPage::" + currentPage);
			log.debug("pageSize::" + pageSize);
			fileDownloadInquiryForms = fileDownloadInqService
					.searchFileDownloadInquiry(condition, currentPage, pageSize);
			if (!AppUtil.isEmpty(fileDownloadInquiryForms)) {
				
				listModel = new ListModelList<FileDownloadInquiryDO>(
						fileDownloadInquiryForms);
			}
			/*--Garbage--*/
		} catch (Exception ex) {
			// log.error(ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			fileDownloadInqService = null;
		}

		return listModel;
	}

	public BatchMastFileParam getCondition() {
		return condition;
	}

	public void setCondition(BatchMastFileParam condition) {
		this.condition = condition;
	}

}
