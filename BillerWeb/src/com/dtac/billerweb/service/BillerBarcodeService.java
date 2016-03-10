package com.dtac.billerweb.service;

import java.util.List;

import com.dtac.billerweb.listmodel.selectbox.BillerBarcodeListModel;

import mfs.biller.persistence.bean.BillerBarcode;
import mfs.biller.persistence.bean.BillerBarcodeParam;
import mfs.biller.persistence.bean.UserInfoBean;

public interface BillerBarcodeService {
	
	public BillerBarcodeListModel getBillerBarcodeListModel(UserInfoBean user) throws Exception;
	public List<BillerBarcode> searchBillerBarcode(BillerBarcodeParam condition, int currentPage, int pageSize, UserInfoBean userInfo);
	public BillerBarcode getByID(Integer id, UserInfoBean userInfo);
	public Integer save(BillerBarcode billerBarcode, UserInfoBean userInfo) throws Exception;
	public void update(BillerBarcode billerBarcode, UserInfoBean userInfo) throws Exception;
}
