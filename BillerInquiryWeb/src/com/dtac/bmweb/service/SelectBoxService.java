package com.dtac.bmweb.service;

import java.util.List;

import mfs.biller.persistence.bean.BillerCategory;
import mfs.biller.persistence.bean.DropdownlistBillMSRT;

import com.dtac.bmweb.listmodel.selectbox.BillerMasterListModel;
import com.dtac.bmweb.listmodel.selectbox.BillerServiceListModel;

public interface SelectBoxService {
	public BillerMasterListModel getBillerCodeListModel() throws Exception;

	public BillerMasterListModel getBillerCodeListModel(Integer billerCategoryId) throws Exception;

	public BillerServiceListModel getBillerServiceListModel() throws Exception;
	
	public BillerServiceListModel getBillerServiceListModel(List<Integer> serviceIds) throws Exception;
	
	public BillerServiceListModel getChannelServiceListModel(Integer billerCode) throws Exception;
	
	public BillerServiceListModel getNetworkServiceListModel() throws Exception;
}
