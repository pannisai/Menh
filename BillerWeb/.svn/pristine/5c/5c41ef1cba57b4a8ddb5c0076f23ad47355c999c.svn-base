package com.dtac.billerweb.data;

import java.io.Serializable;
import java.util.Date;

import mfs.biller.persistence.bean.BatchMastFile;

import com.dtac.billerweb.common.BaseForm;

public class FileDownloadInquiryDO extends BaseForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8323182226458692393L;
	private String id;
	private Date fileTransactionDate;
	private String biller;
	private String fileName;
	private String fileTotalRecord;
	private String status;
	

	public FileDownloadInquiryDO toFileDownloadInquiryForm(BatchMastFile batchMastFile){
		this.id=batchMastFile.getBTCH_MAST_FILE_ID()+"";
		this.fileTransactionDate=batchMastFile.getBTCH_SEND_FILE_DTTM();
		this.biller=batchMastFile.getBTCH_DEST_CODE();
		this.fileName=batchMastFile.getBTCH_MAST_FILE_NAME();
		this.fileTotalRecord=batchMastFile.getBTCH_DETL_TRNS_TOTL()+"";
		this.status=batchMastFile.getBTCH_MAST_FILE_STTS();
		return this;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFileTransactionDate() {
		return fileTransactionDate;
	}

	public void setFileTransactionDate(Date fileTransactionDate) {
		this.fileTransactionDate = fileTransactionDate;
	}

	public String getBiller() {
		return biller;
	}

	public void setBiller(String biller) {
		this.biller = biller;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileTotalRecord() {
		return fileTotalRecord;
	}

	public void setFileTotalRecord(String fileTotalRecord) {
		this.fileTotalRecord = fileTotalRecord;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
