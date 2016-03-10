package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.BankMasterBean;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.data.BankMasterSO;

public interface BankMasterService {
	public List<BankMasterSO> searchBankMaster(BankMasterBean criteria, int currentPage, int pageSize, UserInfoBean userInfo);

	public BankMasterBean getByID(String id, UserInfoBean userInfo);

	public void save(BankMasterBean bankMasterBean, UserInfoBean userInfo) throws IsExistException;

	public void update(BankMasterBean bankMasterBean, UserInfoBean userInfo) throws IsExistException;
	
	public boolean isExistBankCode(String bankCode, UserInfoBean userInfo);
}
