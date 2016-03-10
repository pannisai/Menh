package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.BankChannelBean;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.data.BankChannelSO;

public interface BankChannelService {
	public List<BankChannelSO> searchBankChannel(BankChannelBean criteria, int currentPage, int pageSize, UserInfoBean userInfo);

	public BankChannelBean getByID(String id, UserInfoBean userInfo);

	public void save(BankChannelBean bankChannelBean, UserInfoBean userInfo) throws IsExistException;

	public void update(BankChannelBean bankChannelBean, UserInfoBean userInfo) throws IsExistException;
	
	public boolean isExistBankChannelCode(String bankChannelCode, UserInfoBean userInfo);
}
