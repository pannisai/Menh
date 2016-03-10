package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.GWBankBean;
import mfs.biller.persistence.bean.GWBankDetail;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.data.BankGatewaySO;

public interface BankGatewayService {
	public List<BankGatewaySO> searchBankGateway(GWBankDetail criteria, int currentPage, int pageSize, UserInfoBean userInfo);

	public GWBankBean getByID(Integer id, UserInfoBean userInfo);

	public Integer save(GWBankBean gwBankBean, UserInfoBean userInfo) throws IsExistException;

	public void update(GWBankBean gwBankBean, UserInfoBean userInfo) throws IsExistException;
}
