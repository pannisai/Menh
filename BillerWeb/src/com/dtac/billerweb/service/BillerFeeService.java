package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.BillerFee;
import mfs.biller.persistence.bean.BillerFeeParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.data.BillerFeeSO;

public interface BillerFeeService {
	public List<BillerFeeSO> searchBillerFee(BillerFeeParam condition, int currentPage, int pageSize, UserInfoBean userInfo);
	public int getBillerFeeRowCount(BillerFeeParam condition, UserInfoBean userInfo);
	public BillerFee getByID(Integer id, UserInfoBean userInfo);
	public BillerFee getByServiceID(Integer id, UserInfoBean userInfo);
	public Integer save(BillerFee billerFee, UserInfoBean userInfo) throws IsExistException;
	public void update(BillerFee billerFee, UserInfoBean userInfo) throws IsExistException;
	public boolean isExistBillerFee(BillerFeeParam condition, UserInfoBean userInfo);
}
