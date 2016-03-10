package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.BillerCategory;
import mfs.biller.persistence.bean.BillerCategoryParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.data.BillerCategorySO;

public interface BillerCategoryService {
	public List<BillerCategorySO> searchBillerCategory(BillerCategoryParam condition, int currentPage, int pageSize, UserInfoBean userInfo);
	public int getBillerCategoryRowCount(BillerCategoryParam condition, UserInfoBean userInfo);
	public BillerCategory getByID(Integer id,UserInfoBean userInfo);
	public boolean isExistBillMenuSeq(Integer id,Integer menuSeq,UserInfoBean userInfo);
	public boolean isExistBllrCatgName(Integer id, String billerCateName, UserInfoBean userInfo);
	public Integer save(BillerCategory billerCategory,UserInfoBean userInfo) throws IsExistException;
	public void update(BillerCategory billerCategory,UserInfoBean userInfo) throws IsExistException;
}
