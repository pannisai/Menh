package com.dtac.bmweb.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mfs.biller.ejb.interfaces.BatchMastFileBeanRemote;
import mfs.biller.persistence.bean.BatchMastFile;
import mfs.biller.persistence.bean.BatchMastFileParam;
import mfs.constants.Constants;
import mfs.exception.NotFoundDataException;

import org.apache.log4j.Logger;

import com.dtac.bmweb.common.BaseService;
import com.dtac.bmweb.common.EJBInitialContext;
import com.dtac.bmweb.data.FileDownloadInquirySO;
import com.dtac.bmweb.service.FileDownloadInquiryService;
import com.dtac.bmweb.util.AppUtil;

public class FileDownloadInquiryServiceImpl extends BaseService implements FileDownloadInquiryService {
	private Logger log = Logger.getLogger(FileDownloadInquiryServiceImpl.class);

	@Override
	public List<FileDownloadInquirySO> searchFileDownloadInquiry(BatchMastFileParam condition, int startPage, int pageSize) throws Exception {
		log.info("start searchFileDownloadInquiry");
		Collection<BatchMastFile> batchMastFiles = searchBatchMastFile(condition, startPage, pageSize);
		if (!AppUtil.isEmpty(batchMastFiles)) {
			log.info("BatchMastFiles size::" + batchMastFiles.size());
		}
		List<FileDownloadInquirySO> fileDownloadInquiryForms = convertBatchMastFilesToFileDownloadInquiryForms(batchMastFiles);

		if (!AppUtil.isEmpty(fileDownloadInquiryForms)) {
			log.info("FileDownloadInquiryForms size::" + fileDownloadInquiryForms.size());
		}
		return fileDownloadInquiryForms;
	}

	private Collection<BatchMastFile> searchBatchMastFile(BatchMastFileParam condition, int currentPage, int pageSize) throws Exception {
		BatchMastFileBeanRemote batchMastFileBeanRemote = (BatchMastFileBeanRemote) EJBInitialContext.lookup(Constants.JNDI.batchMastFileBean);

		condition.setPAGE_NO(currentPage);
		condition.setPAGE_SIZE(pageSize);
		Collection<BatchMastFile> batchMastFiles = null;
		try {
			batchMastFiles = batchMastFileBeanRemote.getBatchMastFileAll(condition);
		} catch (NotFoundDataException nfde) {

		}
		/*--Garbage-*/
		batchMastFileBeanRemote = null;
		return batchMastFiles;
	}

	private List<FileDownloadInquirySO> convertBatchMastFilesToFileDownloadInquiryForms(Collection<BatchMastFile> batchMastFiles) throws Exception {
		List<FileDownloadInquirySO> fileDownloadInquiryForms = new ArrayList<FileDownloadInquirySO>();
		if (!AppUtil.isEmpty(batchMastFiles)) {
			FileDownloadInquirySO fileDownloadInquiryForm = null;
			for (BatchMastFile batchMastFile : batchMastFiles) {
				fileDownloadInquiryForm = new FileDownloadInquirySO();
				fileDownloadInquiryForm = fileDownloadInquiryForm.toFileDownloadInquiryForm(batchMastFile);
				fileDownloadInquiryForms.add(fileDownloadInquiryForm);
			}
		}
		return fileDownloadInquiryForms;
	}

	@Override
	public int getFileDownloadInquiryRowCount(BatchMastFileParam condition) throws Exception {
		BatchMastFileBeanRemote batchMastFileBeanRemote = (BatchMastFileBeanRemote) EJBInitialContext.lookup(Constants.JNDI.batchMastFileBean);
		BigDecimal rownum = new BigDecimal(0);
		try {
			rownum = batchMastFileBeanRemote.countRowAll(condition);
		} catch (NotFoundDataException nfde) {

		}
		/*--Garbage-*/
		batchMastFileBeanRemote = null;
		return rownum.intValue();
	}

	@Override
	public byte[] getFileFromBatchMastFileId(String id) throws Exception {
		BatchMastFileBeanRemote batchMastFileBeanRemote = (BatchMastFileBeanRemote) EJBInitialContext.lookup(Constants.JNDI.batchMastFileBean);
		byte[] file = null;
		try {
			file = batchMastFileBeanRemote.getBatchMastFile(AppUtil.trim(id));
		} catch (NotFoundDataException nfde) {

		}
		/*--Garbage-*/
		batchMastFileBeanRemote = null;
		return file;
	}

	@Override
	public BatchMastFile getBatchMastFileById(String id) throws Exception {
		BatchMastFileBeanRemote batchMastFileBeanRemote = (BatchMastFileBeanRemote) EJBInitialContext.lookup(Constants.JNDI.batchMastFileBean);
		BatchMastFile batchMastFile = null;
		try {
			batchMastFile = batchMastFileBeanRemote.findBatchMastFile(AppUtil.trim(id));
		} catch (NotFoundDataException nfde) {

		}
		/*--Garbage-*/
		batchMastFileBeanRemote = null;
		return batchMastFile;
	}

}
