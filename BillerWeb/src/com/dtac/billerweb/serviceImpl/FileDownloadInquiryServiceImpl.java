package com.dtac.billerweb.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mfs.biller.ejb.interfaces.BatchMastFileBeanRemote;
import mfs.biller.persistence.bean.BatchMastFile;
import mfs.biller.persistence.bean.BatchMastFileParam;
import mfs.constants.Constants;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.FileDownloadInquiryDO;
import com.dtac.billerweb.service.FileDownloadInquiryService;
import com.dtac.billerweb.util.AppUtil;

public class FileDownloadInquiryServiceImpl extends BaseService implements
		FileDownloadInquiryService {
	private Logger log = Logger.getLogger(FileDownloadInquiryServiceImpl.class);

	@Override
	public List<FileDownloadInquiryDO> searchFileDownloadInquiry(
			BatchMastFileParam condition, int startPage, int pageSize)
			throws Exception {
		log.info("start searchFileDownloadInquiry");
		Collection<BatchMastFile> batchMastFiles = searchBatchMastFile(
				condition, startPage, pageSize);
		if (!AppUtil.isEmpty(batchMastFiles)) {
			log.info("BatchMastFiles size::" + batchMastFiles.size());
		}
		List<FileDownloadInquiryDO> fileDownloadInquiryForms = convertBatchMastFilesToFileDownloadInquiryForms(batchMastFiles);

		if (!AppUtil.isEmpty(fileDownloadInquiryForms)) {
			log.info("FileDownloadInquiryForms size::"+ fileDownloadInquiryForms.size());
		}
		return fileDownloadInquiryForms;
	}

	private Collection<BatchMastFile> searchBatchMastFile(
			BatchMastFileParam condition, int currentPage, int pageSize)
			throws Exception {
		BatchMastFileBeanRemote batchMastFileBeanRemote = (BatchMastFileBeanRemote) EJBInitialContext
				.lookup(Constants.JNDI.batchMastFileBean);

		condition.setPAGE_NO(currentPage);
		condition.setPAGE_SIZE(pageSize);
		Collection<BatchMastFile> batchMastFiles = batchMastFileBeanRemote
				.getBatchMastFileAll(condition);
		/*--Garbage-*/
		batchMastFileBeanRemote=null;
		return batchMastFiles;
	}

	private List<FileDownloadInquiryDO> convertBatchMastFilesToFileDownloadInquiryForms(
			Collection<BatchMastFile> batchMastFiles) throws Exception {
		List<FileDownloadInquiryDO> fileDownloadInquiryForms = new ArrayList<FileDownloadInquiryDO>();
		if (!AppUtil.isEmpty(batchMastFiles)) {
			FileDownloadInquiryDO fileDownloadInquiryForm = null;
			for (BatchMastFile batchMastFile : batchMastFiles) {
				fileDownloadInquiryForm = new FileDownloadInquiryDO();
				fileDownloadInquiryForm = fileDownloadInquiryForm
						.toFileDownloadInquiryForm(batchMastFile);
				fileDownloadInquiryForms.add(fileDownloadInquiryForm);
			}
		}
		return fileDownloadInquiryForms;
	}

	@Override
	public int getFileDownloadInquiryRowCount(BatchMastFileParam condition) throws Exception {
		BatchMastFileBeanRemote batchMastFileBeanRemote = (BatchMastFileBeanRemote) EJBInitialContext
				.lookup(Constants.JNDI.batchMastFileBean);
		BigDecimal rownum = batchMastFileBeanRemote.countRowAll(condition);
	
		/*--Garbage-*/
		batchMastFileBeanRemote=null;
		return rownum.intValue();
	}

	@Override
	public byte[] getFileFromBatchMastFileId(String id) throws Exception {
		BatchMastFileBeanRemote batchMastFileBeanRemote = (BatchMastFileBeanRemote) EJBInitialContext
				.lookup(Constants.JNDI.batchMastFileBean);
		byte[] file = batchMastFileBeanRemote.getBatchMastFile(AppUtil.trim(id));
		
		/*--Garbage-*/
		batchMastFileBeanRemote=null;
		return file;
	}

	@Override
	public BatchMastFile getBatchMastFileById(String id) throws Exception {
		BatchMastFileBeanRemote batchMastFileBeanRemote = (BatchMastFileBeanRemote) EJBInitialContext
				.lookup(Constants.JNDI.batchMastFileBean);
		BatchMastFile batchMastFile = batchMastFileBeanRemote.findBatchMastFile(AppUtil.trim(id));
		/*--Garbage-*/
		batchMastFileBeanRemote=null;
		return batchMastFile;
	}

}
