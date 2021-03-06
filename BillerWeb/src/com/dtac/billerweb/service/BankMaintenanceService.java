package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.BankSystemMATN;
import mfs.biller.persistence.bean.BankSystemMATNDetail;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.data.BankMaintenanceSO;

public interface BankMaintenanceService {
	public List<BankMaintenanceSO> searchBankMaintenance(BankSystemMATNDetail criteria, int currentPage, int pageSize, UserInfoBean userInfo);

	public BankSystemMATN getByID(Integer id, UserInfoBean userInfo);

	public Integer save(BankSystemMATN bankSystemMATN, UserInfoBean userInfo) throws IsExistException;

	public void update(BankSystemMATN bankSystemMATN, UserInfoBean userInfo) throws IsExistException;
}
