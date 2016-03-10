package com.dtac.billerweb.service;

import mfs.biller.persistence.bean.BankChannelBean;
import mfs.biller.persistence.bean.BankFdmCode;
import mfs.biller.persistence.bean.BankMasterBean;
import mfs.biller.persistence.bean.BankServicebean;
import mfs.biller.persistence.bean.BillerCategory;
import mfs.biller.persistence.bean.BillerIntegration;
import mfs.biller.persistence.bean.BillerRefDataType;
import mfs.biller.persistence.bean.BillerRefParam;
import mfs.biller.persistence.bean.DropdownlistBillMSRT;
import mfs.biller.persistence.bean.GWBankDetail;
import mfs.biller.persistence.bean.UserInfoBean;

import com.dtac.billerweb.listmodel.selectbox.BankChannelListModel;
import com.dtac.billerweb.listmodel.selectbox.BankCodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BankFDMCodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BankGWInboundMapListModel;
import com.dtac.billerweb.listmodel.selectbox.BankGWOutboundMapListModel;
import com.dtac.billerweb.listmodel.selectbox.BankGWServiceMapListModel;
import com.dtac.billerweb.listmodel.selectbox.BankServiceListModel;
import com.dtac.billerweb.listmodel.selectbox.BatchMastFileStatusListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerBarcodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerCatalogListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerChannelListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerCodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerFeeMastListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerMasterListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerRefDataTypeListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerRefListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerServiceListModel;
import com.dtac.billerweb.listmodel.selectbox.InboundMapIdListmodel;
import com.dtac.billerweb.listmodel.selectbox.IntegrationListModel;
import com.dtac.billerweb.listmodel.selectbox.MFSServiceIdListModel;
import com.dtac.billerweb.listmodel.selectbox.OutboundIdListModel;
import com.dtac.billerweb.listmodel.selectbox.OutboundMapIdListModel;
import com.dtac.billerweb.listmodel.selectbox.SendReceiptIdListModel;
import com.dtac.billerweb.listmodel.selectbox.SendReceiptListModel;
import com.dtac.billerweb.listmodel.selectbox.ServiceGatewayListModel;
import com.dtac.billerweb.listmodel.selectbox.ServiceMapIdListModel;

public interface SelectBoxService {
	public BillerMasterListModel getBillerCodeListModel() throws Exception;

	public BillerMasterListModel getBillerCodeListModel(Integer billerCategoryId) throws Exception;

	public BatchMastFileStatusListModel getBatchMastFileStatusListModel() throws Exception;

	public BillerServiceListModel getBillerServiceListModel() throws Exception;

	public BillerServiceListModel getBillerServiceListModel(BillerCategory billerCatagory, DropdownlistBillMSRT billMSRT) throws Exception;

	public BillerCatalogListModel getBillerCatalogListModel() throws Exception;

	public BillerCodeListModel getBillerCodeListModel(BillerCategory condition) throws Exception;

	public OutboundIdListModel getOutboundIdListModel() throws Exception;

	public MFSServiceIdListModel getMFSServiceIdListModel() throws Exception;

	public InboundMapIdListmodel getInboundMapIdListModel() throws Exception;

	public SendReceiptIdListModel getSendReceiptIdListModel() throws Exception;

	public SendReceiptListModel getSendReceiptListModel() throws Exception;

	public BillerChannelListModel getBillerChannelListModel() throws Exception;

	public BillerChannelListModel getBillerChannelListModel(Integer serviceId, UserInfoBean userInfo) throws Exception;

	public ServiceMapIdListModel getServiceMapIdListModel() throws Exception;

	public OutboundMapIdListModel getOutboundMapIdListModel() throws Exception;

	public BillerBarcodeListModel getBillerBarcodeListModel(UserInfoBean user) throws Exception;

	public BillerRefListModel getBillerRefListModel(BillerRefParam criteria, UserInfoBean userInfo) throws Exception;

	public ServiceGatewayListModel getServiceGatewayListModel() throws Exception;

	public BankCodeListModel<BankMasterBean> getBankCodeListModel() throws Exception;

	public BankChannelListModel<BankChannelBean> getBankChannelListModel() throws Exception;

	public BankServiceListModel<BankServicebean> getBankServiceListModel() throws Exception;

	public BankServiceListModel<BankServicebean>  getBankServiceGatewayListModel() throws Exception;

	public BankGWInboundMapListModel<GWBankDetail> getBankGWInboundMapListModel() throws Exception;

	public BankGWServiceMapListModel<GWBankDetail> getBankGWServiceMapListModel() throws Exception;

	public BankGWOutboundMapListModel<GWBankDetail> getBankGWOutboundMapListModel() throws Exception;
	
	public BankFDMCodeListModel<BankFdmCode> getBankFDMCodeListModel(UserInfoBean user) throws Exception;
	
	public IntegrationListModel<BillerIntegration> getIntegrationListModel() throws Exception;
	
	public BillerRefDataTypeListModel<BillerRefDataType> getBillerRefDataTypeListModel(UserInfoBean userInfo) throws Exception;
	
	public BillerFeeMastListModel getBillerFeeMastListModel() throws Exception;

}
