package com.dtac.billerweb.form.filedownloadinquiry;

import java.util.Date;

import com.dtac.billerweb.common.BaseForm;


public class FileDownloadInquirySearchForm extends BaseForm{
	private Date fromDate;
	private Date toDate;
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
}
