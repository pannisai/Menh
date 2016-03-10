package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.ReportTransDetail;
import mfs.biller.persistence.bean.ReportTransParam;
import mfs.biller.persistence.bean.UserInfoBean;

import com.dtac.billerweb.listmodel.selectbox.BillerFdmCodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerPymtCodeListModel;

public interface ReportTransService {
	
	public BillerFdmCodeListModel getBillerFdmCodeListModel(UserInfoBean user) throws Exception;
	public BillerPymtCodeListModel getBillerPymtCodeListModel(UserInfoBean user) throws Exception;
	public List<ReportTransDetail> searchReportTrans(ReportTransParam condition, int currentPage, int pageSize, UserInfoBean userInfo);
	public int countRowReportTrans(ReportTransParam condition, UserInfoBean userInfo);
}
