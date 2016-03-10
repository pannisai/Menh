package com.dtac.bmweb.listmodel;

import java.util.List;

import mfs.biller.persistence.bean.BatchMastFileParam;
import mfs.biller.persistence.bean.UserInfoBean;

import org.apache.log4j.Logger;
import org.zkoss.zul.ListModelList;

import com.dtac.bmweb.common.AbstractPagingListModel;
import com.dtac.bmweb.data.FileDownloadInquirySO;
import com.dtac.bmweb.exception.BillerManageWebException;
import com.dtac.bmweb.factory.BillerwebServiceFactory;
import com.dtac.bmweb.service.FileDownloadInquiryService;

public class FileDownloadListModel extends
		AbstractPagingListModel<FileDownloadInquirySO> {
	private static Logger log = Logger.getLogger(FileDownloadListModel.class);
	private BatchMastFileParam criteria;

	public FileDownloadListModel(int currentPage, int pageSize, UserInfoBean userInfo) throws Exception {
		super(currentPage, pageSize, userInfo);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int getTotalSize(UserInfoBean userInfo) throws Exception {
		// TODO Auto-generated method stub
		FileDownloadInquiryService fileDownloadInqService = null;
		int rownum = 0;
		try {
			fileDownloadInqService = BillerwebServiceFactory.getFileDownloadInquiryService();
			if (criteria == null) {
				criteria = new BatchMastFileParam();
			}
			rownum = fileDownloadInqService.getFileDownloadInquiryRowCount(criteria);
		} catch (Exception ex) {
			throw new BillerManageWebException(ex);
		} finally {
			/*--Garbage--*/
			fileDownloadInqService = null;
		}

		return rownum;
	}

	@Override
	protected ListModelList<FileDownloadInquirySO> getPageData(int currentPage, int pageSize, UserInfoBean userInfo) throws Exception {
		// TODO Auto-generated method stub
		log.info("getPageData[currentPage:" + currentPage + ",pageSize:" + pageSize);
		FileDownloadInquiryService fileDownloadInqService = null;
		List<FileDownloadInquirySO> fileDownloadInquirySOs = null;
		ListModelList<FileDownloadInquirySO> listModel = new ListModelList<FileDownloadInquirySO>();
		try {
			fileDownloadInqService = BillerwebServiceFactory.getFileDownloadInquiryService();

			if (criteria == null) {
				criteria = new BatchMastFileParam();
			}
			log.debug("currentPage::" + currentPage);
			log.debug("pageSize::" + pageSize);
			fileDownloadInquirySOs = fileDownloadInqService.searchFileDownloadInquiry(criteria, currentPage, pageSize);

			log.info("fileDownloadInquirySOs Size::" + fileDownloadInquirySOs.size());
			listModel = new ListModelList<FileDownloadInquirySO>(fileDownloadInquirySOs);

			/*--Garbage--*/
		} catch (Exception ex) {
			throw new BillerManageWebException(ex);
		} finally {
			fileDownloadInqService = null;
			fileDownloadInquirySOs = null;
		}
		return listModel;
	}

	public BatchMastFileParam getCriteria() {
		return criteria;
	}

	public void setCriteria(BatchMastFileParam criteria) {
		this.criteria = criteria;
	}

}
