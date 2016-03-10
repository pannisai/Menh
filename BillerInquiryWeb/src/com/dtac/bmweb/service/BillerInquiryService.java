package com.dtac.bmweb.service;

import java.math.BigDecimal;
import java.util.List;

import mfs.biller.persistence.bean.BillerService;
import mfs.biller.persistence.bean.GWMasterTransParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.NotFoundDataException;

import com.dtac.bmweb.data.BillerInquirySO;

public interface BillerInquiryService {
	public List<BillerInquirySO> searchBillerInquiry(GWMasterTransParam condition,int currentPage,int pageSize) throws Exception;
	public int getBillerInquiryRowCount(GWMasterTransParam condition) throws Exception;	
	public BigDecimal getBillerInquiryTotalAmount(GWMasterTransParam condition) throws Exception;	
	public BillerService getBillerServiceByID(Integer id, UserInfoBean userInfo) throws NotFoundDataException;
}
