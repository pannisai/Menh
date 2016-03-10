package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.ERPBankAccount;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.data.ERPBankAccountSO;

public interface ERPBankAccountService {
	public List<ERPBankAccountSO> searchERPBankAccount(Integer billerServiceId,String accountBankCode,String acctiveFlag, int currentPage, int pageSize, UserInfoBean userInfo);

	public ERPBankAccount getByID(Integer id, UserInfoBean userInfo);

	public Integer save(ERPBankAccount erpBankAccount, UserInfoBean userInfo) throws IsExistException;

	public void update(ERPBankAccount erpBankAccount, UserInfoBean userInfo) throws IsExistException;
}
