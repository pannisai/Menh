package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.BillerChannel;
import mfs.biller.persistence.bean.BillerChannelParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.data.BillerChannelSO;

public interface BillerChannelService {
	public List<BillerChannelSO> searchBillerChannel(BillerChannelParam condition, int currentPage, int pageSize, UserInfoBean userInfo);

	public BillerChannel getByID(Integer id, UserInfoBean userInfo);

	public Integer save(BillerChannel billerChannel, UserInfoBean userInfo) throws IsExistException;

	public void update(BillerChannel billerChannel, UserInfoBean userInfo) throws IsExistException;
	
	public boolean isExistBillerChannelName(int BLLR_CHNL_ID, String BLLR_CHNL_NAME, UserInfoBean userInfo);
	public boolean isExistBillerChannelCode(int BLLR_CHNL_ID, String BLLR_CHNL_CODE, UserInfoBean userInfo);
}
