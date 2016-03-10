package com.dtac.billerweb.junit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import mfs.biller.persistence.bean.BatchMastFileParam;

import org.junit.Test;

import com.dtac.billerweb.data.FileDownloadInquiryDO;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.service.FileDownloadInquiryService;

public class FileDownloadInquiryServiceTest {
	
	FileDownloadInquiryService fileDownloadInquiryService=BillerwebServiceFactory.getFileDownloadInquiryService();
	
//	@Test
	public void testSearchBatchMastFileByCondition(){
		BatchMastFileParam condition=new BatchMastFileParam();
//		condition.setBTCH_MAST_FILE_ID(800);
		try {
			 List<FileDownloadInquiryDO> result=fileDownloadInquiryService.searchFileDownloadInquiry(condition, 1, 10);
			 assertNotNull(result);
			 assertTrue("Size > 0 ::"+result.size(), result.size()>0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	@Test
	public void testSearchMastFile(){
		FileDownloadInquiryService fileDownloadInqService=null;
		try {
		 fileDownloadInqService = BillerwebServiceFactory
					.getFileDownloadInquiryService();
		 fileDownloadInqService.getFileDownloadInquiryRowCount(new BatchMastFileParam());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	@Test
	public void test() {
//		FileDownloadListModel fdlm=new FileDownloadListModel(0, 10);
	}

}
