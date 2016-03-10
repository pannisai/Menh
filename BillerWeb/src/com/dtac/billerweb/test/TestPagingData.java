package com.dtac.billerweb.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.dtac.billerweb.data.BillerInquiryDO;
import com.dtac.billerweb.data.FileDownloadInquiryDO;

public class TestPagingData {
	static Logger log=Logger.getLogger(TestPagingData.class);
	public int getDataCount() {
		log.info("Test Log");
		return 1000;
	}

	public List<FileDownloadInquiryDO> getDataTest(String condition,int itemStartNumber,
			int itemPageSize) {
		System.out.println("ItemstartNumber:"+itemStartNumber);
		System.out.println("pageSize:"+ (itemStartNumber + itemPageSize));
		FileDownloadInquiryDO f1 = null;
		List<FileDownloadInquiryDO> fileDownloads = new ArrayList<FileDownloadInquiryDO>();
	
		for (int i = itemStartNumber; i < (itemStartNumber + itemPageSize); i++) {
			
			f1 = new FileDownloadInquiryDO();
			f1.setId("" + i);
			f1.setBiller("Biller"+condition + i);
			f1.setFileName("fileName" + i);
			f1.setFileTotalRecord("fileTotalRecord" + i);
			f1.setFileTransactionDate(new Date());
			f1.setStatus("status" + i);
			fileDownloads.add(f1);
		}
		return fileDownloads;
	}
	public List<BillerInquiryDO> getBillerDataTest(int itemStartNumber,
			int itemPageSize) {
		System.out.println("ItemstartNumber:"+itemStartNumber);
		System.out.println("pageSize:"+ (itemStartNumber + itemPageSize));
		BillerInquiryDO f1 = null;
		List<BillerInquiryDO> billers = new ArrayList<BillerInquiryDO>();
	
		for (int i = itemStartNumber; i < (itemStartNumber + itemPageSize); i++) {
			f1 = new BillerInquiryDO();
//			f1.setId("" + i);
//			f1.setContactAccount("ContactAccount"+i);
//			f1.setCustomerName("CustomerName" + i);
////			f1.setDueDate(i);
//			f1.setInvoiceNo("InvoiceNo"+i);
//			f1.setPaidAmount(new BigDecimal(i+0.5));
//			f1.setPaymentDate(new Date());
//			f1.setPaymentRef("PaymentRef"+i);
			billers.add(f1);
		}
		return billers;
	}
	public static void main(String[] argh){
		log.info("Test Log");
	}
}
