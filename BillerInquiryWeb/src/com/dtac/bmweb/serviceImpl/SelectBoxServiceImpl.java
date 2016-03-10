package com.dtac.bmweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.DropdownlistRemote;
import mfs.biller.persistence.bean.BillerMaster;
import mfs.biller.persistence.bean.DropdownlistBillservice;
import mfs.constants.Constants;

import org.apache.log4j.Logger;

import com.dtac.bmweb.common.AppMessage;
import com.dtac.bmweb.common.BaseService;
import com.dtac.bmweb.common.EJBInitialContext;
import com.dtac.bmweb.exception.ServiceException;
import com.dtac.bmweb.listmodel.selectbox.BillerMasterListModel;
import com.dtac.bmweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.bmweb.service.SelectBoxService;
import com.dtac.bmweb.util.AppUtil;
import com.dtac.bmweb.util.ValidateChannelServiceUtils;

public class SelectBoxServiceImpl extends BaseService implements SelectBoxService {
	private Logger log = Logger.getLogger(SelectBoxServiceImpl.class);

	/* ## Biller Master ## */
	@Override
	public BillerMasterListModel getBillerCodeListModel() throws Exception {
		BillerMasterListModel billerMasterListModel = new BillerMasterListModel(new ArrayList<BillerMaster>());
		try {
			billerMasterListModel = getBillerCodeListModel(null);

		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return billerMasterListModel;
	}

	@Override
	public BillerMasterListModel getBillerCodeListModel(Integer billerCategoryId) throws Exception {
		BillerMasterListModel billerMasterListModel = new BillerMasterListModel(new ArrayList<BillerMaster>());
		DropdownlistRemote dropdownlistRemote = null;
		try {			
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(Constants.JNDI.dropdownlist);
			if (dropdownlistRemote == null) {
				return billerMasterListModel;
			}
			List<BillerMaster> list = new ArrayList<BillerMaster>(dropdownlistRemote.getBillerMasterAll(billerCategoryId));
			if (AppUtil.isNull(list)) {
				return billerMasterListModel;
			}
			billerMasterListModel = new BillerMasterListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			dropdownlistRemote = null;
		}
		return billerMasterListModel;
	}

	/* ## Biller Service ## */
	public BillerServiceListModel getBillerServiceListModel() throws Exception {
		BillerServiceListModel billerServiceListModel = new BillerServiceListModel(new ArrayList<DropdownlistBillservice>());
		try {
			billerServiceListModel = getBillerServiceListModel(null);	

		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return billerServiceListModel;
	}

	public BillerServiceListModel getBillerServiceListModel(List<Integer> serviceIds) throws Exception {
		BillerServiceListModel billerServiceListModel = new BillerServiceListModel(new ArrayList<DropdownlistBillservice>());
		DropdownlistRemote dropdownlistRemote = null;
		try {	
			log.debug("eiei");
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(Constants.JNDI.dropdownlist);
			if (dropdownlistRemote == null) {
				return billerServiceListModel;
			}
			List<DropdownlistBillservice> list = dropdownlistRemote.getDropdownlistInquiryService(serviceIds);
			if (AppUtil.isNull(list)) {
				return billerServiceListModel;
			}
			billerServiceListModel = new BillerServiceListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			dropdownlistRemote = null;
		}
		return billerServiceListModel;
	}
	
	public BillerServiceListModel getChannelServiceListModel(Integer billerCode)
			throws Exception {
		ValidateChannelServiceUtils validChannel = new ValidateChannelServiceUtils();
		BillerServiceListModel billerServiceListModel = new BillerServiceListModel(
				new ArrayList<DropdownlistBillservice>());
		List<DropdownlistBillservice> list = new ArrayList<DropdownlistBillservice>();
		DropdownlistBillservice tempData;
		tempData = new DropdownlistBillservice();
		tempData.setBLLR_SRVC_NAME_EN("Jaew");
		tempData.setBLLR_SRVC_ID(0);
		tempData.setBLLR_SRVC_CODE("0");
		list.add(tempData);
		if (null != billerCode) {
			if (validChannel.isMobileCreditCard(billerCode)) {
				tempData = new DropdownlistBillservice();
				tempData.setBLLR_SRVC_NAME_EN("Mobile Credit Card");
				tempData.setBLLR_SRVC_ID(1);
				tempData.setBLLR_SRVC_CODE("1");
				list.add(tempData);
			}
		}
		if (list.size() > 1) {
			tempData = new DropdownlistBillservice();
			tempData.setBLLR_SRVC_NAME_EN(AppMessage
					.getMessage(AppMessage.SELECTBOX_ALL));
			tempData.setBLLR_SRVC_ID(null);
			tempData.setBLLR_SRVC_CODE(null);
			list.add(0, tempData);
		}
		billerServiceListModel = new BillerServiceListModel(list);
		billerServiceListModel.addDataToSelection(0);
		return billerServiceListModel;
	}
	public BillerServiceListModel getNetworkServiceListModel() throws Exception{
		
		List<DropdownlistBillservice> list = new ArrayList<DropdownlistBillservice>();
		DropdownlistBillservice tempData;
		tempData = new DropdownlistBillservice();
		tempData.setBLLR_SRVC_NAME_EN("TOT");
		tempData.setBLLR_SRVC_ID(5);
		tempData.setBLLR_SRVC_CODE("1");
		list.add(tempData);
		tempData = new DropdownlistBillservice();
		tempData.setBLLR_SRVC_NAME_EN("TRUE");
		tempData.setBLLR_SRVC_ID(5);
		tempData.setBLLR_SRVC_CODE("2");
		list.add(tempData);
		tempData = new DropdownlistBillservice();
		tempData.setBLLR_SRVC_NAME_EN("TT&T");
		tempData.setBLLR_SRVC_ID(52);
		tempData.setBLLR_SRVC_CODE("3");
		list.add(tempData);
		tempData = new DropdownlistBillservice();
		tempData.setBLLR_SRVC_NAME_EN(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		tempData.setBLLR_SRVC_ID(null);
		tempData.setBLLR_SRVC_CODE(null);
		list.add(0, tempData);
		BillerServiceListModel billerServiceListModel = new BillerServiceListModel(list);
		billerServiceListModel.addDataToSelection(0);
		
		return billerServiceListModel;
	}
}
