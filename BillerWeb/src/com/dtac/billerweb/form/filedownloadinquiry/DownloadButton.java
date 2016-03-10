package com.dtac.billerweb.form.filedownloadinquiry;

import mfs.biller.persistence.bean.BatchMastFile;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;

import com.dtac.billerweb.controller.FileDownloadInquiryController;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.service.FileDownloadInquiryService;
import com.dtac.billerweb.util.AppUtil;

public class DownloadButton extends Button {
	private Logger log = Logger.getLogger(FileDownloadInquiryController.class);

	public void onClick(MouseEvent event) {
		try {
			FileDownloadInquiryService fileDownloadInquiryService = BillerwebServiceFactory.getFileDownloadInquiryService();
			log.info("download:" + event.getTarget().getId());
			String btID = event.getTarget().getId();
			String BT_DOWNLOAD_PREFIX = "btDownload_";
			if (btID.indexOf(BT_DOWNLOAD_PREFIX) > -1) {
				String oid = btID.substring(BT_DOWNLOAD_PREFIX.length());
				log.info("Download oid::" + oid);
				BatchMastFile batchMastFile = fileDownloadInquiryService.getBatchMastFileById(oid);
				if (!AppUtil.isEmpty(batchMastFile)) {
					byte[] b = fileDownloadInquiryService.getFileFromBatchMastFileId(oid);

					Filedownload.save(b, "text/html", batchMastFile.getBTCH_MAST_FILE_NAME());
					log.debug(b.length);
				}
			}
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}
}
