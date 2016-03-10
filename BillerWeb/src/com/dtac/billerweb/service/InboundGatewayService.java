package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.BillerMaster;
import mfs.biller.persistence.bean.OBJGW_INBOUND;
import mfs.biller.persistence.bean.ObjMapGWxml;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.biller.persistence.bean.getInboundGatewayResult;
import mfs.biller.persistence.bean.getInboundGatewayResultParam;
import mfs.exception.IsExistException;

import com.dtac.billerweb.data.InboundGatewaySO;
import com.dtac.billerweb.data.InboundGatewaySOPK;

public interface InboundGatewayService {
	public List<InboundGatewaySO> searchInboundGateway(getInboundGatewayResultParam condition, int currentPage, int pageSize,UserInfoBean userInfo) throws Exception;

	public int getInboundGatewayRowCount(getInboundGatewayResultParam condition,UserInfoBean userInfo) throws Exception;

	public OBJGW_INBOUND getByID(InboundGatewaySOPK id, UserInfoBean userInfo);

	public InboundGatewaySOPK save(OBJGW_INBOUND obJGW_INBOUND, UserInfoBean userInfo) throws IsExistException;

	public void update(OBJGW_INBOUND obJGW_INBOUND, UserInfoBean userInfo) throws IsExistException;

}
