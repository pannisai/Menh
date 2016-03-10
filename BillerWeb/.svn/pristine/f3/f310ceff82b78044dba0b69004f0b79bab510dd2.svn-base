package com.dtac.billerweb.service;

import mfs.biller.persistence.bean.GWXmlDataSrcBean;
import mfs.biller.persistence.bean.ObjMapGWxml;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

public interface ReceiptMapIdService {
	public GWXmlDataSrcBean getByID(Integer id, UserInfoBean userInfo);

	public Integer save(GWXmlDataSrcBean mapGwXml, UserInfoBean userInfo) throws IsExistException;

	public void update(GWXmlDataSrcBean mapGwXml, UserInfoBean userInfo) throws IsExistException;
}
