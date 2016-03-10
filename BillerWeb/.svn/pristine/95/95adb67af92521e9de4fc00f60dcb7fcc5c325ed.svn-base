package com.dtac.billerweb.listmodel;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zul.ListModelList;

import com.dtac.billerweb.common.AbstractPagingListModel;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.service.ReportTransService;

import mfs.biller.persistence.bean.ReportTransDetail;
import mfs.biller.persistence.bean.ReportTransParam;
import mfs.biller.persistence.bean.UserInfoBean;

public class ReportTransDetailListModel extends AbstractPagingListModel<ReportTransDetail> {
	
	private static Logger log = Logger.getLogger(ReportTransDetailListModel.class);
	private ReportTransParam condition;
	
	public ReportTransDetailListModel(int currentPage, int pageSize, UserInfoBean userInfo) throws Exception {
		super(currentPage, pageSize, userInfo);
	}
	
	@Override
	public int getTotalSize(UserInfoBean userInfo) throws Exception {
		ReportTransService reportTransService = null;
		int rownum = 0;
		try {
			reportTransService = BillerwebServiceFactory.getReportTransService();
			if (condition == null) {
				condition = new ReportTransParam();
			}
			rownum = reportTransService.countRowReportTrans(condition, userInfo);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new BillerWebException(ex);
		} finally {
			reportTransService = null;
		}
		return rownum;
	}
	
	@Override
	protected ListModelList<ReportTransDetail> getPageData(int currentPage, int pageSize, UserInfoBean userInfo) throws Exception {
		log.info("getPageData[currentPage:" + currentPage + ",pageSize:" + pageSize);
		ReportTransService reportTransService = null;
		List<ReportTransDetail> reportTransDetail = null;
		ListModelList<ReportTransDetail> listModel = new ListModelList<ReportTransDetail>();
		try {
			reportTransService = BillerwebServiceFactory.getReportTransService();
			if (condition == null) {
				condition = new ReportTransParam();
			}

			reportTransDetail = reportTransService.searchReportTrans(condition, currentPage, pageSize, userInfo);

			log.info("Size::" + reportTransDetail.size());
			listModel = new ListModelList<ReportTransDetail>(reportTransDetail);

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new BillerWebException(ex);
		} finally {
			reportTransService = null;
		}
		return listModel;
	}
	
	public ReportTransParam getCondition() {
		return condition;
	}

	public void setCondition(ReportTransParam condition) {
		this.condition = condition;
	}
}
