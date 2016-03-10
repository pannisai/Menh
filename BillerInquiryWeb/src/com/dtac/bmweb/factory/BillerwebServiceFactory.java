package com.dtac.bmweb.factory;

import com.dtac.bmweb.service.BillerInquiryService;
import com.dtac.bmweb.service.FileDownloadInquiryService;
import com.dtac.bmweb.service.SelectBoxService;
import com.dtac.bmweb.serviceImpl.BillerInquiryServiceImpl;
import com.dtac.bmweb.serviceImpl.FileDownloadInquiryServiceImpl;
import com.dtac.bmweb.serviceImpl.SelectBoxServiceImpl;

public abstract class BillerwebServiceFactory {
	

	public static FileDownloadInquiryService getFileDownloadInquiryService() {
		return new FileDownloadInquiryServiceImpl();
	}

	public static BillerInquiryService getBillerInquiryService() {
		return new BillerInquiryServiceImpl();
	}
	
	public static SelectBoxService getSelectBoxService() {
		return new SelectBoxServiceImpl();
	}
}
