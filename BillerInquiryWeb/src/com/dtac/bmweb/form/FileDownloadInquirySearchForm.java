package com.dtac.bmweb.form;

import java.util.Date;

import com.dtac.bmweb.common.BaseForm;


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
