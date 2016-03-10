package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.BillerChannel;
import mfs.biller.persistence.bean.GWOutbound;
import mfs.biller.persistence.bean.GWService;
import mfs.biller.persistence.bean.OBJGW_SERVICE;
import mfs.biller.persistence.bean.OutboundGatewayParam;
import mfs.biller.persistence.bean.OutboundId;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.data.OutboundGatewaySO;

public interface OutboundGatewayService {
	public List<OutboundGatewaySO> searchOutboundGateway(OutboundGatewayParam condition, int currentPage, int pageSize,UserInfoBean userInfo) throws Exception;

	public int getOutboundGatewayRowCount(OutboundGatewayParam condition,UserInfoBean userInfo) throws Exception;

	public GWOutbound getByID(Integer id, UserInfoBean userInfo);

	public Integer save(GWOutbound outboundGateway, UserInfoBean userInfo) throws IsExistException;

	public void update(GWOutbound outboundGateway, UserInfoBean userInfo) throws IsExistException;
}
