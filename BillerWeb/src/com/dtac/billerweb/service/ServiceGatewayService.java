package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.GWService;
import mfs.biller.persistence.bean.OBJGW_SERVICE;
import mfs.biller.persistence.bean.ServiceGatewayResultParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.data.ServiceGatewaySO;

//BW2200 And BW2210
public interface ServiceGatewayService {
	public List<ServiceGatewaySO> searchServiceGateway(ServiceGatewayResultParam criteria,int currentPage,int pageSize,UserInfoBean userInfo) throws Exception;
	public int getServiceGatewayRowCount(ServiceGatewayResultParam criteria,UserInfoBean userInfo) throws Exception;
	
	public GWService getByID(Integer id, UserInfoBean userInfo);

	public Integer save(GWService serviceGateway, UserInfoBean userInfo) throws IsExistException;

	public void update(GWService serviceGateway, UserInfoBean userInfo) throws IsExistException;
}
