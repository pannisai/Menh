package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.BankReportTransParam;
import mfs.biller.persistence.bean.BillerService;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.biller.persistence.bean.getInboundGatewayResultParam;
import mfs.exception.NotFoundDataException;

import com.dtac.billerweb.data.BankReportTranSO;

public interface BankReportTranService {
	public List<BankReportTranSO> searchBankReportTran(BankReportTransParam criteria, int currentPage, int pageSize, UserInfoBean userInfo);
	
	public int getBankReportTranRowCount(BankReportTransParam criteria,UserInfoBean userInfo) throws Exception;
    
	public BankReportTranSO getBankReportTranByID(String id, UserInfoBean userInfo) ;

}
