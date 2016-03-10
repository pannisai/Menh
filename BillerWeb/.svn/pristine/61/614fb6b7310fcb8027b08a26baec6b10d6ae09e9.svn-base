package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.BankServicebean;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.data.BankServiceSO;

public interface BankServiceService {
	public List<BankServiceSO> searchBankService(BankServicebean criteria, int currentPage, int pageSize, UserInfoBean userInfo);

	public BankServicebean getByID(Integer id, UserInfoBean userInfo);

	public Integer save(BankServicebean bankServicebean, UserInfoBean userInfo) throws IsExistException;

	public void update(BankServicebean bankServicebean, UserInfoBean userInfo) throws IsExistException;
}
