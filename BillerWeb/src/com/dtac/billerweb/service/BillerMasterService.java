package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.BillerMaster;
import mfs.biller.persistence.bean.BillerMasterParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.data.BillerMasterSO;

public interface BillerMasterService {
	public List<BillerMasterSO> searchBillerMaster(BillerMasterParam condition, int currentPage, int pageSize, UserInfoBean userInfo);

	public BillerMaster getByID(Integer id, UserInfoBean userInfo);

	public boolean isExistBillerMasterId(Integer id, UserInfoBean userInfo);

	public boolean isExistBlllerCode(Integer id, String billerCode, UserInfoBean userInfo);

	public void save(BillerMaster billerMaster, UserInfoBean userInfo) throws IsExistException;

	public void update(BillerMaster billerMaster, UserInfoBean userInfo) throws IsExistException;
}
