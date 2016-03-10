package com.dtac.bmweb.service;

import java.util.List;

import mfs.biller.persistence.bean.BatchMastFile;
import mfs.biller.persistence.bean.BatchMastFileParam;

import com.dtac.bmweb.data.FileDownloadInquirySO;

public interface FileDownloadInquiryService {
	public List<FileDownloadInquirySO> searchFileDownloadInquiry(BatchMastFileParam condition,int currentPage,int pageSize) throws Exception;
	public int getFileDownloadInquiryRowCount(BatchMastFileParam condition) throws Exception;
	public byte[] getFileFromBatchMastFileId(String id) throws Exception;
	public BatchMastFile getBatchMastFileById(String id) throws Exception;
}
