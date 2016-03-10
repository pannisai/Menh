package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.BillerDenominateBeanRemote;
import mfs.biller.ejb.interfaces.BillerServiceBeanRemote;
import mfs.biller.persistence.bean.BillerBarcode;
import mfs.biller.persistence.bean.BillerBarcodeDetail;
import mfs.biller.persistence.bean.BillerBarcodeRef;
import mfs.biller.persistence.bean.BillerDenominate;
import mfs.biller.persistence.bean.BillerForm;
import mfs.biller.persistence.bean.BillerPaymentValidate;
import mfs.biller.persistence.bean.BillerRef;
import mfs.biller.persistence.bean.BillerRefDetail;
import mfs.biller.persistence.bean.BillerRefParam;
import mfs.biller.persistence.bean.BillerService;
import mfs.biller.persistence.bean.BillerServiceChannel;
import mfs.biller.persistence.bean.BillerServiceDetail;
import mfs.biller.persistence.bean.BillerServiceImage;
import mfs.biller.persistence.bean.BillerServiceParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;
import mfs.exception.NotFoundDataException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.BillerBarcodeDetailSO;
import com.dtac.billerweb.data.BillerBarcodeSO;
import com.dtac.billerweb.data.BillerDenominateSO;
import com.dtac.billerweb.data.BillerServiceChannelSO;
import com.dtac.billerweb.data.BillerServiceInputSO;
import com.dtac.billerweb.data.BillerServiceSO;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.BillerServiceService;
import com.dtac.billerweb.util.AppUtil;

public class BillerServiceServiceImpl extends BaseService implements BillerServiceService {
	private Logger log = Logger.getLogger(BillerServiceServiceImpl.class);

	@Override
	public List<BillerServiceSO> searchBillerService(BillerServiceParam condition, int currentPage, int pageSize, UserInfoBean userInfo) {
		log.info("start searchBillerService");
		List<BillerServiceSO> billerServiceSOs = null;
		List<BillerServiceDetail> billerServiceDetails = null;
		try {
			billerServiceDetails = getBillerServiceDetails(condition, currentPage, pageSize, userInfo);
			log.info("billerServiceDetails size::" + billerServiceDetails.size());

			billerServiceSOs = toBillerServiceSOList(billerServiceDetails);
			log.info("billerServiceSOs size::" + billerServiceSOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceDetails = null;
		}

		return billerServiceSOs;
	}

	private List<BillerServiceDetail> getBillerServiceDetails(BillerServiceParam condition, int currentPage, int pageSize, UserInfoBean userInfo) {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		List<BillerServiceDetail> billerServiceDetails = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			// condition.setPAGE_NO(currentPage);
			// condition.setPAGE_SIZE(pageSize);
			billerServiceDetails = billerServiceBeanRemote.searchBillerService(condition, userInfo);
			if (AppUtil.isEmpty(billerServiceDetails)) {
				billerServiceDetails = new ArrayList<BillerServiceDetail>();
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			/*--Garbage-*/
			billerServiceBeanRemote = null;
		}
		return billerServiceDetails;
	}

	private List<BillerServiceSO> toBillerServiceSOList(List<BillerServiceDetail> billerServiceDetails) throws Exception {

		List<BillerServiceSO> billerServiceSOs = new ArrayList<BillerServiceSO>();
		try {
			if (AppUtil.isEmpty(billerServiceDetails)) {
				return billerServiceSOs;
			}
			BillerServiceSO billerServiceSO = null;
			for (BillerServiceDetail billerServiceDetail : billerServiceDetails) {
				billerServiceSO = new BillerServiceSO();
				billerServiceSO = billerServiceSO.toBillerServiceSO(billerServiceDetail);
				billerServiceSOs.add(billerServiceSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceDetails = null;
		}
		return billerServiceSOs;

	}

	@Override
	public BillerService getBillerServiceInfoByID(Integer id, UserInfoBean userInfo) throws NotFoundDataException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		BillerService billerService = new BillerService();
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerService = billerServiceBeanRemote.findBillerService(id, userInfo);
		} catch (NotFoundDataException nfde) {
			throw nfde;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (billerService == null) {
			return new BillerService();
		}
		return billerService;
	}

	@Override
	public Integer saveBillerServiceInfo(BillerService billerService, UserInfoBean userInfo) throws IsExistException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		Integer oid = -1;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			oid = billerServiceBeanRemote.insertInformation(billerService, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void updateBillerServiceInfo(BillerService billerervice, UserInfoBean userInfo) throws IsExistException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerServiceBeanRemote.updateBillerService(billerervice, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
	}

	@Override
	public BillerPaymentValidate getBillerPaymentValidateByID(Integer id, UserInfoBean userInfo) throws NotFoundDataException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		BillerPaymentValidate billerPaymentVal = new BillerPaymentValidate();
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerPaymentVal = billerServiceBeanRemote.findBillerPaymentValidate(id, userInfo);

		} catch (NotFoundDataException nfde) {
			throw nfde;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (billerPaymentVal == null) {
			return new BillerPaymentValidate();
		}
		return billerPaymentVal;
	}

	@Override
	public Integer saveBillerPaymentValidate(BillerPaymentValidate billerPaymentVal, UserInfoBean userInfo) throws IsExistException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		Integer oid = -1;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			oid = billerServiceBeanRemote.insertBillerPaymentValidate(billerPaymentVal, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void updateBillerPaymentValidate(BillerPaymentValidate billerPaymentVal, UserInfoBean userInfo) throws IsExistException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerServiceBeanRemote.updateBillerPaymentValidate(billerPaymentVal, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
	}

	@Override
	public BillerServiceChannel getBillerServiceChannelByID(Integer id, Integer serviceId, UserInfoBean userInfo) {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		BillerServiceChannel billerServiceChannel = new BillerServiceChannel();
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerServiceChannel = billerServiceBeanRemote.findBillerServiceChannel(serviceId, id, userInfo);

		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (billerServiceChannel == null) {
			return new BillerServiceChannel();
		}
		return billerServiceChannel;
	}

	@Override
	public void saveBillerServiceChannel(BillerServiceChannel billerServiceChannel, UserInfoBean userInfo) throws IsExistException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerServiceBeanRemote.insertBillerServiceChannel(billerServiceChannel, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
	}

	@Override
	public void updateBillerServiceChannel(BillerServiceChannel billerServiceChannel, UserInfoBean userInfo) throws IsExistException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerServiceBeanRemote.updateBillerServiceChannel(billerServiceChannel, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
	}

	@Override
	public List<BillerServiceChannelSO> searchBillerServiceChannel(Integer billerServiceId, UserInfoBean userInfo) {
		log.info("start searchBillerServiceChannel");
		List<BillerServiceChannelSO> billerServiceChannelSOs = null;
		List<BillerServiceChannel> billerServiceChannels = null;
		try {
			billerServiceChannels = getBillerServicChannel(billerServiceId, userInfo);
			log.info("billerServiceChannels size::" + billerServiceChannels.size());

			billerServiceChannelSOs = toBillerServiceChannelSOList(billerServiceChannels);
			log.info("billerServiceChannelSOs size::" + billerServiceChannelSOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceChannels = null;
		}

		return billerServiceChannelSOs;
	}

	private List<BillerServiceChannel> getBillerServicChannel(Integer billerServiceId, UserInfoBean userInfo) {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		List<BillerServiceChannel> billerServiceChannels = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerServiceChannels = billerServiceBeanRemote.searchBillerServiceChannel(billerServiceId, userInfo);
			if (AppUtil.isEmpty(billerServiceChannels)) {
				billerServiceChannels = new ArrayList<BillerServiceChannel>();
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			/*--Garbage-*/
			billerServiceBeanRemote = null;
		}
		return billerServiceChannels;
	}

	private List<BillerServiceChannelSO> toBillerServiceChannelSOList(List<BillerServiceChannel> billerServiceChannels) throws Exception {

		List<BillerServiceChannelSO> billerServiceChannelSOs = new ArrayList<BillerServiceChannelSO>();
		try {
			if (AppUtil.isEmpty(billerServiceChannels)) {
				return billerServiceChannelSOs;
			}
			BillerServiceChannelSO billerServiceChannelSO = null;
			for (BillerServiceChannel billerServiceChannel : billerServiceChannels) {
				billerServiceChannelSO = new BillerServiceChannelSO();
				billerServiceChannelSO = billerServiceChannelSO.toBillerServiceChannelSO(billerServiceChannel);
				billerServiceChannelSOs.add(billerServiceChannelSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceChannels = null;
		}
		return billerServiceChannelSOs;

	}

	/* ## BillerService Input Form ## */

	@Override
	public List<BillerServiceInputSO> searchBillerServiceInput(BillerRefParam billerRefParam, UserInfoBean userInfo) {
		log.info("start searchBillerServiceInput");
		List<BillerServiceInputSO> billerServiceInputSOs = null;
		List<BillerRef> billerRefs = null;
		try {
			billerRefs = getBillerRefs(billerRefParam, userInfo);
			log.info("billerRefs size::" + billerRefs.size());

			billerServiceInputSOs = toBillerServiceInputSOList(billerRefs);
			log.info("billerServiceInputSOs size::" + billerServiceInputSOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerRefs = null;
		}

		return billerServiceInputSOs;
	}

	private List<BillerRef> getBillerRefs(BillerRefParam billerRefParam, UserInfoBean userInfo) {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		List<BillerRef> billerRefs = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerRefs = billerServiceBeanRemote.searchBillerRef(billerRefParam, userInfo);
			if (AppUtil.isEmpty(billerRefs)) {
				billerRefs = new ArrayList<BillerRef>();
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			/*--Garbage-*/
			billerServiceBeanRemote = null;
		}
		return billerRefs;
	}

	private List<BillerServiceInputSO> toBillerServiceInputSOList(List<BillerRef> billerRefs) throws Exception {

		List<BillerServiceInputSO> billerServiceInputSOs = new ArrayList<BillerServiceInputSO>();
		try {
			if (AppUtil.isEmpty(billerRefs)) {
				return billerServiceInputSOs;
			}
			BillerServiceInputSO billerServiceInputSO = null;
			for (BillerRef billerRef : billerRefs) {
				billerServiceInputSO = new BillerServiceInputSO();
				billerServiceInputSO = billerServiceInputSO.toBillerServiceInputSO(billerRef);
				billerServiceInputSOs.add(billerServiceInputSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerRefs = null;
		}
		return billerServiceInputSOs;

	}

	@Override
	public BillerForm getBillerFormByServiceID(Integer serviceId, UserInfoBean userInfo) {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		BillerForm billerForm = new BillerForm();
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerForm = billerServiceBeanRemote.findBillerFormBySrvcId(serviceId, userInfo);
		} catch (NotFoundDataException nfd) {
			return new BillerForm();
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (billerForm == null) {
			return new BillerForm();
		}
		return billerForm;
	}

	@Override
	public Integer saveBillerForm(BillerForm billerForm, UserInfoBean userInfo) throws IsExistException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		Integer oid = -1;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			oid = billerServiceBeanRemote.insertBillerForm(billerForm, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void updateBillerForm(BillerForm billerForm, UserInfoBean userInfo) throws IsExistException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerServiceBeanRemote.updateBillerForm(billerForm, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
	}

	@Override
	public BillerForm getBillerFormByID(Integer id, UserInfoBean userInfo) {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		BillerForm billerForm = new BillerForm();
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerForm = billerServiceBeanRemote.findBillerForm(id, userInfo);
		} catch (NotFoundDataException nfd) {
			return new BillerForm();
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (billerForm == null) {
			return new BillerForm();
		}
		return billerForm;
	}

	@Override
	public BillerRef getBillerRefByID(Integer id, UserInfoBean userInfo) {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		BillerRef billerRef = new BillerRef();
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerRef = billerServiceBeanRemote.findBillerRef(id, userInfo);
		} catch (NotFoundDataException nfd) {
			return new BillerRef();
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (billerRef == null) {
			return new BillerRef();
		}
		return billerRef;
	}

	@Override
	public Integer saveBillerRef(BillerRef billerRef, UserInfoBean userInfo) throws IsExistException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		Integer oid = -1;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			oid = billerServiceBeanRemote.insertBillerRef(billerRef, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void updateBillerRef(BillerRef billerRef, UserInfoBean userInfo) throws IsExistException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerServiceBeanRemote.updateBillerRef(billerRef, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
	}

	/* ## BillerService Barcode ## */
	@Override
	public List<BillerBarcodeSO> searchBillerBarcodeByServiceId(Integer billerServiceId, UserInfoBean userInfo) {
		log.info("start searchBillerBarcodeByServiceId");
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		List<BillerBarcodeSO> billerBarcodeSOs = new ArrayList<BillerBarcodeSO>();
		List<BillerBarcode> billerBarcodes = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerBarcodes = billerServiceBeanRemote.searchBillerBarcode(billerServiceId, userInfo);
			if (billerBarcodes == null) {
				return billerBarcodeSOs;
			}
			log.info("billerBarcodes size::" + billerBarcodes.size());
			billerBarcodeSOs = toBillerBarcodeSOList(billerBarcodes);
			log.info("billerBarcodeSOs size::" + billerBarcodeSOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
			billerBarcodes = null;
		}

		return billerBarcodeSOs;
	}

	private List<BillerBarcodeSO> toBillerBarcodeSOList(List<BillerBarcode> billerBarcodes) throws Exception {

		List<BillerBarcodeSO> billerBarcodeSOs = new ArrayList<BillerBarcodeSO>();
		try {
			if (AppUtil.isEmpty(billerBarcodes)) {
				return billerBarcodeSOs;
			}
			BillerBarcodeSO billerBarcodeSO = null;
			for (BillerBarcode billerBarcode : billerBarcodes) {
				billerBarcodeSO = new BillerBarcodeSO();
				billerBarcodeSO = billerBarcodeSO.toBillerBarcodeSO(billerBarcode);
				billerBarcodeSOs.add(billerBarcodeSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerBarcodes = null;
		}
		return billerBarcodeSOs;

	}

	@Override
	public List<BillerBarcodeDetailSO> searchBillerBarcodeDetailByServiceId(Integer billerServiceId, UserInfoBean userInfo) {
		log.info("start searchBillerBarcodeDetailByServiceId");
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		List<BillerBarcodeDetailSO> billerBarcodeDetailSOs = new ArrayList<BillerBarcodeDetailSO>();
		List<BillerBarcodeDetail> billerBarcodeDetails = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerBarcodeDetails = billerServiceBeanRemote.searchBillerBarcodeDetail(billerServiceId, userInfo);
			if (billerBarcodeDetails == null) {
				return billerBarcodeDetailSOs;
			}
			log.info("billerBarcodeDetails size::" + billerBarcodeDetails.size());
			billerBarcodeDetailSOs = toBillerBarcodeDetailSOList(billerBarcodeDetails);
			log.info("billerBarcodeDetailSOs size::" + billerBarcodeDetailSOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
			billerBarcodeDetails = null;
		}
		return billerBarcodeDetailSOs;
	}

	private List<BillerBarcodeDetailSO> toBillerBarcodeDetailSOList(List<BillerBarcodeDetail> billerBarcodeDetails) throws Exception {

		List<BillerBarcodeDetailSO> billerBarcodeDetailSOs = new ArrayList<BillerBarcodeDetailSO>();
		try {
			if (AppUtil.isEmpty(billerBarcodeDetails)) {
				return billerBarcodeDetailSOs;
			}
			BillerBarcodeDetailSO billerBarcodeDetailSO = null;
			for (BillerBarcodeDetail billerBarcodeDetail : billerBarcodeDetails) {
				billerBarcodeDetailSO = new BillerBarcodeDetailSO();
				billerBarcodeDetailSO = billerBarcodeDetailSO.toBillerBarcodeDetailSO(billerBarcodeDetail);
				billerBarcodeDetailSOs.add(billerBarcodeDetailSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerBarcodeDetails = null;
		}
		return billerBarcodeDetailSOs;

	}

	@Override
	public BillerRefDetail getBillerRefDetailByID(Integer refId, UserInfoBean userInfo) {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		BillerRefDetail billerRefDetail = new BillerRefDetail();
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerRefDetail = billerServiceBeanRemote.findBillerRefDetail(refId, userInfo);
		} catch (NotFoundDataException nfd) {
			return new BillerRefDetail();
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (billerRefDetail == null) {
			return new BillerRefDetail();
		}
		return billerRefDetail;
	}

	@Override
	public BillerBarcodeRef getBillerBarcodeRefByID(Integer id, UserInfoBean userInfo) {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		BillerBarcodeRef billerBarcodeRef = new BillerBarcodeRef();
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerBarcodeRef = billerServiceBeanRemote.findBillerBarcodeRef(id, userInfo);
		} catch (NotFoundDataException nfd) {
			return new BillerBarcodeRef();
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (billerBarcodeRef == null) {
			return new BillerBarcodeRef();
		}
		return billerBarcodeRef;
	}

	@Override
	public Integer saveBillerBarcodeRef(BillerBarcodeRef billerBarcodeRef, UserInfoBean userInfo) throws IsExistException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		Integer oid = -1;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			oid = billerServiceBeanRemote.insertBillerBarcodeRef(billerBarcodeRef, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void updateBillerBarcodeRef(BillerBarcodeRef billerBarcodeRef, UserInfoBean userInfo) throws IsExistException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerServiceBeanRemote.updateBillerBarcodeRef(billerBarcodeRef, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}

	}

	@Override
	public List<BillerDenominateSO> searchBillerDenominate(Integer serviceId, UserInfoBean userInfo) {
		// TODO Auto-generated method stub
		log.info("start searchBillerDenoMinate");
		BillerDenominateBeanRemote billerDenominateBeanRemote = null;
		List<BillerDenominateSO> billerDenominateSOs = new ArrayList<BillerDenominateSO>();
		List<BillerDenominate> billerDenominates = null;
		try {
			billerDenominateBeanRemote = (BillerDenominateBeanRemote) EJBInitialContext.lookup(BillerDenominateBeanRemote.JNDI_WEBLOGIC);
			billerDenominates = billerDenominateBeanRemote.searchBillerDenominateAll(serviceId, userInfo);
			if (billerDenominates == null) {
				return billerDenominateSOs;
			}
			log.info("billerDenominates size::" + billerDenominates.size());
			billerDenominateSOs = toBillerDenominateSOList(billerDenominates);
			log.info("billerDenominateSOs size::" + billerDenominateSOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerDenominateBeanRemote = null;
			billerDenominates = null;
		}
		return billerDenominateSOs;
	}

	private List<BillerDenominateSO> toBillerDenominateSOList(List<BillerDenominate> billerDenominates) throws Exception {

		List<BillerDenominateSO> billerDenominateSOs = new ArrayList<BillerDenominateSO>();
		try {
			if (AppUtil.isEmpty(billerDenominates)) {
				return billerDenominateSOs;
			}
			BillerDenominateSO billerDenominateSO = null;
			for (BillerDenominate billerDenominate : billerDenominates) {
				billerDenominateSO = new BillerDenominateSO();
				billerDenominateSO = billerDenominateSO.toBillerDenominateSO(billerDenominate);
				billerDenominateSOs.add(billerDenominateSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerDenominates = null;
		}
		return billerDenominateSOs;

	}

	@Override
	public BillerDenominate getBillerDenominateByID(Integer id, UserInfoBean userInfo) {
		// TODO Auto-generated method stub
		BillerDenominateBeanRemote billerDenominateBeanRemote = null;
		BillerDenominate billerDenominate = new BillerDenominate();
		try {
			billerDenominateBeanRemote = (BillerDenominateBeanRemote) EJBInitialContext.lookup(BillerDenominateBeanRemote.JNDI_WEBLOGIC);
			billerDenominate = billerDenominateBeanRemote.findBillerDenominate(id, userInfo);
		} catch (NotFoundDataException nfd) {
			return new BillerDenominate();
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerDenominateBeanRemote = null;
		}
		if (billerDenominate == null) {
			return new BillerDenominate();
		}
		return billerDenominate;
	}

	@Override
	public Integer saveBillerDenominate(BillerDenominate billerDenominate, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		BillerDenominateBeanRemote billerDenominateBeanRemote = null;
		Integer oid = -1;
		try {
			billerDenominateBeanRemote = (BillerDenominateBeanRemote) EJBInitialContext.lookup(BillerDenominateBeanRemote.JNDI_WEBLOGIC);
			oid = billerDenominateBeanRemote.insertBillerDenominate(billerDenominate, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerDenominateBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void updateBillerDenominate(BillerDenominate billerDenominate, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		BillerDenominateBeanRemote billerDenominateBeanRemote = null;
		try {
			billerDenominateBeanRemote = (BillerDenominateBeanRemote) EJBInitialContext.lookup(BillerDenominateBeanRemote.JNDI_WEBLOGIC);
			billerDenominateBeanRemote.updateBillerDenominate(billerDenominate, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerDenominateBeanRemote = null;
		}
	}

	@Override
	public BillerServiceImage getBillerServiceImage(Integer serviceId, UserInfoBean userInfo) {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		BillerServiceImage billerServiceImage = new BillerServiceImage();
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerServiceImage = billerServiceBeanRemote.getBillerServiceImage(serviceId+"");
		} catch (NotFoundDataException nfd) {
			return new BillerServiceImage();
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (billerServiceImage == null) {
			return new BillerServiceImage();
		}
		return billerServiceImage;
	}

	@Override
	public Integer saveBillerServiceImage(BillerServiceImage billerServiceImage, UserInfoBean userInfo) throws IsExistException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		Integer oid = -1;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			oid = billerServiceBeanRemote.saveBillerImage(billerServiceImage, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void updateBillerServiceImage(BillerServiceImage billerServiceImage, UserInfoBean userInfo) throws IsExistException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(BillerServiceBeanRemote.JNDI_WEBLOGIC);
			billerServiceBeanRemote.updateBillerServiceImage(billerServiceImage, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}		
	}

}
