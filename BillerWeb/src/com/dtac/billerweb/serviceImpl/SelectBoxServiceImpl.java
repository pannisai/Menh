package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.BankRptTransBeanRemote;
import mfs.biller.ejb.interfaces.BillerServiceBeanRemote;
import mfs.biller.ejb.interfaces.DropdownlistRemote;
import mfs.biller.persistence.bean.BankChannelBean;
import mfs.biller.persistence.bean.BankFdmCode;
import mfs.biller.persistence.bean.BankMasterBean;
import mfs.biller.persistence.bean.BankServicebean;
import mfs.biller.persistence.bean.BillerBarcode;
import mfs.biller.persistence.bean.BillerCategory;
import mfs.biller.persistence.bean.BillerChannel;
import mfs.biller.persistence.bean.BillerFeeMast;
import mfs.biller.persistence.bean.BillerIntegration;
import mfs.biller.persistence.bean.BillerMaster;
import mfs.biller.persistence.bean.BillerRef;
import mfs.biller.persistence.bean.BillerRefDataType;
import mfs.biller.persistence.bean.BillerRefParam;
import mfs.biller.persistence.bean.DropdownlistBillMSRT;
import mfs.biller.persistence.bean.DropdownlistBillservice;
import mfs.biller.persistence.bean.GWBankDetail;
import mfs.biller.persistence.bean.GWOutboundMap;
import mfs.biller.persistence.bean.GWService;
import mfs.biller.persistence.bean.GWServiceMap;
import mfs.biller.persistence.bean.INBOUNDMAPID;
import mfs.biller.persistence.bean.MFSServiceId;
import mfs.biller.persistence.bean.OutboundId;
import mfs.biller.persistence.bean.SendReceipt;
import mfs.biller.persistence.bean.SendReceiptId;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.NotFoundDataException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.BatchMastFileStatusDO;
import com.dtac.billerweb.exception.ServiceException;
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
import com.dtac.billerweb.service.SelectBoxService;
import com.dtac.billerweb.util.AppUtil;

public class SelectBoxServiceImpl extends BaseService implements SelectBoxService {
	private Logger log = Logger.getLogger(SelectBoxServiceImpl.class);

	/* ## Biller Master ## */
	@Override
	public BillerMasterListModel getBillerCodeListModel() throws Exception {
		BillerMasterListModel billerMasterListModel = new BillerMasterListModel(new ArrayList<BillerMaster>());
		try {
			List<BillerMaster> list = getBillerCodeListByCatId(null);
			billerMasterListModel = new BillerMasterListModel(list);

		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return billerMasterListModel;
	}

	@Override
	public BillerMasterListModel getBillerCodeListModel(Integer billerCategoryId) throws Exception {
		BillerMasterListModel billerMasterListModel = new BillerMasterListModel(new ArrayList<BillerMaster>());
		try {
			List<BillerMaster> list = getBillerCodeListByCatId(billerCategoryId);
			billerMasterListModel = new BillerMasterListModel(list);

		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return billerMasterListModel;
	}

	private List<BillerMaster> getBillerCodeListByCatId(Integer billerCategoryId) {
		List<BillerMaster> list = new ArrayList<BillerMaster>();
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);

			if (dropdownlistRemote == null) {
				return list;
			}
			list = new ArrayList<BillerMaster>(dropdownlistRemote.getBillerMasterAll(billerCategoryId));
		} catch (Exception ex) {
			throw new ServiceException("Get BillerMasters Fail", ex);
		} finally {
			dropdownlistRemote = null;
		}
		if (AppUtil.isNull(list)) {
			list = new ArrayList<BillerMaster>();
		}
		return list;
	}

	/* ## Batch Master File Status ## */
	@Override
	public BatchMastFileStatusListModel getBatchMastFileStatusListModel() throws Exception {
		List<BatchMastFileStatusDO> batchMastFileStatusForms = new ArrayList<BatchMastFileStatusDO>();
		BatchMastFileStatusDO success = new BatchMastFileStatusDO();
		success.setId("S");
		success.setValue("Success");
		BatchMastFileStatusDO fail = new BatchMastFileStatusDO();
		fail.setId("F");
		fail.setValue("Fail");
		BatchMastFileStatusDO all = new BatchMastFileStatusDO();
		all.setId("");
		all.setValue(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		batchMastFileStatusForms.add(all);
		batchMastFileStatusForms.add(success);
		batchMastFileStatusForms.add(fail);
		BatchMastFileStatusListModel batchMastFileStatusListModel = new BatchMastFileStatusListModel(batchMastFileStatusForms);
		batchMastFileStatusListModel.addToSelection(all);
		return batchMastFileStatusListModel;
	}

	/* ## Biller Service ## */
	public BillerServiceListModel getBillerServiceListModel() throws Exception {
		BillerServiceListModel billerServiceListModel = new BillerServiceListModel(new ArrayList<DropdownlistBillservice>());
		try {
			billerServiceListModel = getBillerServiceListModel(new BillerCategory(), new DropdownlistBillMSRT());

		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return billerServiceListModel;
	}

	public BillerServiceListModel getBillerServiceListModel(BillerCategory billerCatagory, DropdownlistBillMSRT billMSRT) throws Exception {
		BillerServiceListModel billerServiceListModel = new BillerServiceListModel(new ArrayList<DropdownlistBillservice>());
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return billerServiceListModel;
			}
			List<DropdownlistBillservice> list = dropdownlistRemote.getDropdownlistSERVICE(billerCatagory, billMSRT);
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

	/* ## Biller Catalog ## */
	@Override
	public BillerCatalogListModel getBillerCatalogListModel() throws Exception {
		BillerCatalogListModel billerCatalogListModel = new BillerCatalogListModel(new ArrayList<BillerCategory>());
		try {
			List<BillerCategory> list = getBillerCatelogList();
			billerCatalogListModel = new BillerCatalogListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return billerCatalogListModel;
	}

	private List<BillerCategory> getBillerCatelogList() {
		log.info("Get BillerCatelog");
		List<BillerCategory> list = new ArrayList<BillerCategory>();
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return list;
			}
			list = dropdownlistRemote.getDropdownlistCATG();
		} catch (NotFoundDataException nfd) {

		} catch (Exception ex) {
			throw new ServiceException("Get Biller CatalogListModel Fail", ex);
		} finally {
			dropdownlistRemote = null;
		}
		if (AppUtil.isNull(list)) {
			list = new ArrayList<BillerCategory>();
		}
		log.info(" BillerCatelogList Size::" + list.size());
		return list;
	}

	/* ## Biller Code by condition ## */

	@Override
	public BillerCodeListModel getBillerCodeListModel(BillerCategory condition) throws Exception {
		BillerCodeListModel billerCodeListModel = new BillerCodeListModel(new ArrayList<DropdownlistBillMSRT>());
		try {
			List<DropdownlistBillMSRT> list = getBillerCodeList(condition);
			billerCodeListModel = new BillerCodeListModel(list);

		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return billerCodeListModel;
	}

	private List<DropdownlistBillMSRT> getBillerCodeList(BillerCategory condition) {
		log.info("Get DropdownlistBillMSRT By condition");
		List<DropdownlistBillMSRT> list = new ArrayList<DropdownlistBillMSRT>();
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return list;
			}
			list = dropdownlistRemote.getDropdownlistMSTR(condition);
		} catch (NotFoundDataException nfd) {

		} catch (Exception ex) {
			throw new ServiceException("Get DropdownlistBillMSRT Fail", ex);
		} finally {
			dropdownlistRemote = null;
		}
		if (AppUtil.isNull(list)) {
			list = new ArrayList<DropdownlistBillMSRT>();
		}
		log.info(" DropdownlistBillMSRT Size::" + list.size());
		return list;
	}

	/* ## Outbound ID ## */
	@Override
	public OutboundIdListModel getOutboundIdListModel() throws Exception {
		OutboundIdListModel outboundIdListModel = new OutboundIdListModel(new ArrayList<OutboundId>());
		try {
			List<OutboundId> list = getOutboundIdist();
			outboundIdListModel = new OutboundIdListModel(list);

		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return outboundIdListModel;
	}

	private List<OutboundId> getOutboundIdist() {
		log.info("Get OutboundId");
		List<OutboundId> list = new ArrayList<OutboundId>();
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return list;
			}
			list = dropdownlistRemote.getOutboundId();
		} catch (Exception ex) {
			throw new ServiceException("Get OutboundId Fail", ex);
		} finally {
			dropdownlistRemote = null;
		}
		if (AppUtil.isNull(list)) {
			list = new ArrayList<OutboundId>();
		}
		log.info("OutboundId Size::" + list.size());
		return list;
	}

	/* ## MFS Service ID ## */
	@Override
	public MFSServiceIdListModel getMFSServiceIdListModel() throws Exception {
		MFSServiceIdListModel mfsServiceIdListModel = new MFSServiceIdListModel(new ArrayList<MFSServiceId>());
		try {
			List<MFSServiceId> list = getMFSServiceIdist();
			mfsServiceIdListModel = new MFSServiceIdListModel(list);

		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return mfsServiceIdListModel;
	}

	private List<MFSServiceId> getMFSServiceIdist() {
		log.info("Get MFSServiceIds");
		List<MFSServiceId> list = new ArrayList<MFSServiceId>();
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return list;
			}
			list = dropdownlistRemote.getMFSServiceId();
		} catch (Exception ex) {
			throw new ServiceException("Get MFSServiceIds Fail", ex);
		} finally {
			dropdownlistRemote = null;
		}
		if (AppUtil.isNull(list)) {
			list = new ArrayList<MFSServiceId>();
		}
		log.info("MFSServiceIds Size::" + list.size());
		return list;
	}

	/* ## Inbound Map ID ## */
	@Override
	public InboundMapIdListmodel getInboundMapIdListModel() throws Exception {
		InboundMapIdListmodel inboundMapIdListmodel = new InboundMapIdListmodel(new ArrayList<INBOUNDMAPID>());
		try {
			List<INBOUNDMAPID> list = getInboundMapIdist();
			inboundMapIdListmodel = new InboundMapIdListmodel(list);

		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return inboundMapIdListmodel;
	}

	private List<INBOUNDMAPID> getInboundMapIdist() {
		log.info("Get INBOUNDMAPIDs");
		List<INBOUNDMAPID> list = new ArrayList<INBOUNDMAPID>();
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return list;
			}
			list = dropdownlistRemote.getINBOUNDMAPID();
		} catch (Exception ex) {
			throw new ServiceException("Get INBOUNDMAPIDs Fail", ex);
		} finally {
			dropdownlistRemote = null;
		}
		if (AppUtil.isNull(list)) {
			list = new ArrayList<INBOUNDMAPID>();
		}
		log.info("INBOUNDMAPIDs Size::" + list.size());
		return list;
	}

	/* ## Send receipt ID ## */
	@Override
	public SendReceiptIdListModel getSendReceiptIdListModel() throws Exception {
		SendReceiptIdListModel sendReceiptIdListModel = new SendReceiptIdListModel(new ArrayList<SendReceiptId>());
		try {
			List<SendReceiptId> list = getSendReceiptIdist();
			sendReceiptIdListModel = new SendReceiptIdListModel(list);

		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return sendReceiptIdListModel;
	}

	private List<SendReceiptId> getSendReceiptIdist() {
		log.info("Get SendReceiptIds");
		List<SendReceiptId> list = new ArrayList<SendReceiptId>();
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return list;
			}
			list = dropdownlistRemote.getSendReceiptId();
		} catch (Exception ex) {
			throw new ServiceException("Get SendReceiptIds Fail", ex);
		} finally {
			dropdownlistRemote = null;
		}
		if (AppUtil.isNull(list)) {
			list = new ArrayList<SendReceiptId>();
		}
		log.info("SendReceiptIds Size::" + list.size());
		return list;
	}

	/* ## Send receipt ## */
	@Override
	public SendReceiptListModel getSendReceiptListModel() throws Exception {
		SendReceiptListModel sendReceiptListModel = new SendReceiptListModel(new ArrayList<SendReceipt>());
		try {
			List<SendReceipt> list = getSendReceiptist();
			sendReceiptListModel = new SendReceiptListModel(list);

		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return sendReceiptListModel;
	}

	private List<SendReceipt> getSendReceiptist() {
		log.info("Get SendReceipts");
		List<SendReceipt> list = new ArrayList<SendReceipt>();
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return list;
			}
			list = dropdownlistRemote.getSendReceipt();
		} catch (Exception ex) {
			throw new ServiceException("Get SendReceiptss Fail", ex);
		} finally {
			dropdownlistRemote = null;
		}
		if (AppUtil.isNull(list)) {
			list = new ArrayList<SendReceipt>();
		}
		log.info("SendReceipts Size::" + list.size());
		return list;
	}

	/* ## Biller Channel## */
	@Override
	public BillerChannelListModel getBillerChannelListModel() throws Exception {
		BillerChannelListModel billerChannelListModel = new BillerChannelListModel(new ArrayList<BillerChannel>());
		try {
			List<BillerChannel> list = getBillerChannelList();
			billerChannelListModel = new BillerChannelListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return billerChannelListModel;
	}

	@Override
	public BillerChannelListModel getBillerChannelListModel(Integer serviceId, UserInfoBean userInfo) throws Exception {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		BillerChannelListModel billerChannelListModel = new BillerChannelListModel(new ArrayList<BillerChannel>());
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			List<BillerChannel> list = billerServiceBeanRemote.searchBillerChannel(serviceId, userInfo);
			billerChannelListModel = new BillerChannelListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		return billerChannelListModel;

	}

	private List<BillerChannel> getBillerChannelList() {
		log.info("Get BillerChannels");
		List<BillerChannel> list = new ArrayList<BillerChannel>();
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return list;
			}
			list = dropdownlistRemote.getPaymentChanel();
		} catch (Exception ex) {
			throw new ServiceException("Get BillerChannels Fail", ex);
		} finally {
			dropdownlistRemote = null;
		}
		if (AppUtil.isNull(list)) {
			list = new ArrayList<BillerChannel>();
		}
		log.info("BillerChannels Size::" + list.size());
		return list;
	}

	/* ## Service Map Id ## */
	@Override
	public ServiceMapIdListModel getServiceMapIdListModel() throws Exception {
		ServiceMapIdListModel serviceMapIdListModel = new ServiceMapIdListModel(new ArrayList<GWServiceMap>());
		try {
			List<GWServiceMap> list = getServiceMapIdList();
			serviceMapIdListModel = new ServiceMapIdListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return serviceMapIdListModel;
	}

	private List<GWServiceMap> getServiceMapIdList() {
		log.info("Get Service Map Id");
		List<GWServiceMap> list = new ArrayList<GWServiceMap>();
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return list;
			}
			list = dropdownlistRemote.getServicemapId();
		} catch (Exception ex) {
			throw new ServiceException("Get GWServiceMaps Fail", ex);
		} finally {
			dropdownlistRemote = null;
		}
		if (AppUtil.isNull(list)) {
			list = new ArrayList<GWServiceMap>();
		}
		log.info("GWServiceMaps Size::" + list.size());
		return list;
	}

	/* ## Outbound Map Id ## */
	@Override
	public OutboundMapIdListModel getOutboundMapIdListModel() throws Exception {
		OutboundMapIdListModel outboundMapIdListModel = new OutboundMapIdListModel(new ArrayList<GWOutboundMap>());
		try {
			List<GWOutboundMap> list = getOutboundMapIdList();
			outboundMapIdListModel = new OutboundMapIdListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return outboundMapIdListModel;
	}

	private List<GWOutboundMap> getOutboundMapIdList() {
		log.info("Get Outbound Map Id");
		List<GWOutboundMap> list = new ArrayList<GWOutboundMap>();
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return list;
			}
			list = dropdownlistRemote.getOutboundMapId();
		} catch (Exception ex) {
			throw new ServiceException("Get GWOutboundMap Fail", ex);
		} finally {
			dropdownlistRemote = null;
		}
		if (AppUtil.isNull(list)) {
			list = new ArrayList<GWOutboundMap>();
		}
		log.info("GWOutboundMaps Size::" + list.size());
		return list;
	}

	/* ## Biller Barcode ## */
	@Override
	public BillerBarcodeListModel getBillerBarcodeListModel(UserInfoBean user) throws Exception {
		BillerBarcodeListModel billerBarcodeListModel = new BillerBarcodeListModel(new ArrayList<BillerBarcode>());
		try {
			List<BillerBarcode> list = getBillerBarcodeList(user);
			billerBarcodeListModel = new BillerBarcodeListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return billerBarcodeListModel;
	}

	private List<BillerBarcode> getBillerBarcodeList(UserInfoBean user) {
		log.info("Get BillerBarcode");
		List<BillerBarcode> list = new ArrayList<BillerBarcode>();
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			if (billerServiceBeanRemote == null) {
				return list;
			}
			list = billerServiceBeanRemote.getBillerBarcodeAll(user);
		} catch (Exception ex) {
			throw new ServiceException("Get Biller BarcodeListModel Fail", ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (AppUtil.isNull(list)) {
			list = new ArrayList<BillerBarcode>();
		}
		log.info(" BillerBarcodeList Size::" + list.size());
		return list;
	}

	/* ## Biller Ref ## */
	@Override
	public BillerRefListModel getBillerRefListModel(BillerRefParam criteria, UserInfoBean userInfo) throws Exception {
		BillerRefListModel billerRefListModel = new BillerRefListModel(new ArrayList<BillerRef>());
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			if (billerServiceBeanRemote == null) {
				return billerRefListModel;
			}
			List<BillerRef> list = billerServiceBeanRemote.searchBillerRef(criteria, userInfo);
			if (AppUtil.isNull(list)) {
				return billerRefListModel;
			}
			billerRefListModel = new BillerRefListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		return billerRefListModel;
	}

	/* ## Service Gateway ## */
	@Override
	public ServiceGatewayListModel getServiceGatewayListModel() throws Exception {
		ServiceGatewayListModel serviceGatewayListModel = new ServiceGatewayListModel(new ArrayList<GWService>());
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return serviceGatewayListModel;
			}
			List<GWService> list = dropdownlistRemote.getServiceGateway();
			if (AppUtil.isNull(list)) {
				return serviceGatewayListModel;
			}
			serviceGatewayListModel = new ServiceGatewayListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			dropdownlistRemote = null;
		}
		return serviceGatewayListModel;
	}

	/* ## BankCode ListModel ## */
	@Override
	public BankCodeListModel<BankMasterBean> getBankCodeListModel() throws Exception {
		BankCodeListModel bankCodeListModel = new BankCodeListModel(new ArrayList<BankMasterBean>());
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return bankCodeListModel;
			}
			List<BankMasterBean> list = dropdownlistRemote.getBankCode();
			if (AppUtil.isNull(list)) {
				return bankCodeListModel;
			}
			bankCodeListModel = new BankCodeListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			dropdownlistRemote = null;
		}
		return bankCodeListModel;
	}

	/* ## Bank Channel ListModel ## */
	@Override
	public BankChannelListModel<BankChannelBean> getBankChannelListModel() throws Exception {
		BankChannelListModel bankChannelListModel = new BankChannelListModel(new ArrayList<BankChannelBean>());
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return bankChannelListModel;
			}
			List<BankChannelBean> list = dropdownlistRemote.getBankChannel();
			if (AppUtil.isNull(list)) {
				return bankChannelListModel;
			}
			bankChannelListModel = new BankChannelListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			dropdownlistRemote = null;
		}
		return bankChannelListModel;
	}

	/* ## Bank Service ListModel ## */
	@Override
	public BankServiceListModel<BankServicebean> getBankServiceListModel() throws Exception {
		BankServiceListModel bankServiceListModel = new BankServiceListModel(new ArrayList<BankServicebean>());
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return bankServiceListModel;
			}
			List<BankServicebean> list = dropdownlistRemote.getBankService();
			if (AppUtil.isNull(list)) {
				return bankServiceListModel;
			}
			bankServiceListModel = new BankServiceListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			dropdownlistRemote = null;
		}
		return bankServiceListModel;

	}

	@Override
	public BankServiceListModel<BankServicebean> getBankServiceGatewayListModel() throws Exception {
		BankServiceListModel bankServiceListModel = new BankServiceListModel(new ArrayList<BankServicebean>());
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return bankServiceListModel;
			}
			List<BankServicebean> list = dropdownlistRemote.getGWBankService();
			if (AppUtil.isNull(list)) {
				return bankServiceListModel;
			}
			bankServiceListModel = new BankServiceListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			dropdownlistRemote = null;
		}
		return bankServiceListModel;
	}

	@Override
	public BankGWInboundMapListModel<GWBankDetail> getBankGWInboundMapListModel() throws Exception {
		// TODO Auto-generated method stub
		BankGWInboundMapListModel bankGatewayMapListModel = new BankGWInboundMapListModel(new ArrayList<GWBankDetail>());
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return bankGatewayMapListModel;
			}
			List<GWBankDetail> list = dropdownlistRemote.getGWInboundMapID();
			if (AppUtil.isNull(list)) {
				return bankGatewayMapListModel;
			}
			bankGatewayMapListModel = new BankGWInboundMapListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			dropdownlistRemote = null;
		}
		return bankGatewayMapListModel;
	}

	@Override
	public BankGWServiceMapListModel<GWBankDetail> getBankGWServiceMapListModel() throws Exception {
		// TODO Auto-generated method stub
		BankGWServiceMapListModel bankGWServiceMapListModel = new BankGWServiceMapListModel(new ArrayList<GWBankDetail>());
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return bankGWServiceMapListModel;
			}
			List<GWBankDetail> list = dropdownlistRemote.getGWServiceMapID();
			if (AppUtil.isNull(list)) {
				return bankGWServiceMapListModel;
			}
			bankGWServiceMapListModel = new BankGWServiceMapListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			dropdownlistRemote = null;
		}
		return bankGWServiceMapListModel;
	}

	@Override
	public BankGWOutboundMapListModel<GWBankDetail> getBankGWOutboundMapListModel() throws Exception {
		// TODO Auto-generated method stub
		BankGWOutboundMapListModel bankGWOutboundMapListModel = new BankGWOutboundMapListModel(new ArrayList<GWBankDetail>());
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return bankGWOutboundMapListModel;
			}
			List<GWBankDetail> list = dropdownlistRemote.getGWOutboundMapID();
			if (AppUtil.isNull(list)) {
				return bankGWOutboundMapListModel;
			}
			bankGWOutboundMapListModel = new BankGWOutboundMapListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			dropdownlistRemote = null;
		}
		return bankGWOutboundMapListModel;
	}

	@Override
	public BankFDMCodeListModel<BankFdmCode> getBankFDMCodeListModel(UserInfoBean user) throws Exception {
		// TODO Auto-generated method stub
		BankFDMCodeListModel bankFDMCodeListModel = new BankFDMCodeListModel(new ArrayList<BankFdmCode>());
		BankRptTransBeanRemote bankRptTransBeanRemote = null;
		try {
			bankRptTransBeanRemote = (BankRptTransBeanRemote) EJBInitialContext.lookup(BankRptTransBeanRemote.JNDI_WEBLOGIC);
			if (bankRptTransBeanRemote == null) {
				return bankFDMCodeListModel;
			}
			List<BankFdmCode> list = bankRptTransBeanRemote.getBankFdmCodeAll(user);
			if (AppUtil.isNull(list)) {
				return bankFDMCodeListModel;
			}
			bankFDMCodeListModel = new BankFDMCodeListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankRptTransBeanRemote = null;
		}
		return bankFDMCodeListModel;
	}

	@Override
	public IntegrationListModel<BillerIntegration> getIntegrationListModel() throws Exception {
		// TODO Auto-generated method stub
		IntegrationListModel integrationListModel = new IntegrationListModel(new ArrayList<BillerIntegration>());
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return integrationListModel;
			}
			List<BillerIntegration> list = dropdownlistRemote.getBillerIntegration();
			if (AppUtil.isNull(list)) {
				return integrationListModel;
			}
			integrationListModel = new IntegrationListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			dropdownlistRemote = null;
		}
		return integrationListModel;
	}

	@Override
	public BillerRefDataTypeListModel<BillerRefDataType> getBillerRefDataTypeListModel(UserInfoBean userInfo) throws Exception {
		// TODO Auto-generated method stub
		BillerRefDataTypeListModel billerRefDataTypeListModel = new BillerRefDataTypeListModel(new ArrayList<BillerRefDataType>());
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return billerRefDataTypeListModel;
			}
			List<BillerRefDataType> list = dropdownlistRemote.getBillerRefDataType(userInfo);
			if (AppUtil.isNull(list)) {
				return billerRefDataTypeListModel;
			}
			billerRefDataTypeListModel = new BillerRefDataTypeListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			dropdownlistRemote = null;
		}
		return billerRefDataTypeListModel;
	}
	
	@Override
	public BillerFeeMastListModel<BillerFeeMast> getBillerFeeMastListModel() throws Exception {
		// TODO Auto-generated method stub
		BillerFeeMastListModel billerFeeMastListModel = new BillerFeeMastListModel(new ArrayList<BillerFeeMast>());
		DropdownlistRemote dropdownlistRemote = null;
		try {
			dropdownlistRemote = (DropdownlistRemote) EJBInitialContext.lookup(DropdownlistRemote.JNDI_WEBLOGIC);
			if (dropdownlistRemote == null) {
				return billerFeeMastListModel;
			}
			List<BillerFeeMast> list = dropdownlistRemote.getBillerFeeMast();
			if (AppUtil.isNull(list)) {
				return billerFeeMastListModel;
			}
			billerFeeMastListModel = new BillerFeeMastListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			dropdownlistRemote = null;
		}
		return billerFeeMastListModel;
	}
}
