package com.dtac.billerweb.service;

import java.util.List;

import mfs.biller.persistence.bean.BillerBarcodeRef;
import mfs.biller.persistence.bean.BillerChannelParam;
import mfs.biller.persistence.bean.BillerDenominate;
import mfs.biller.persistence.bean.BillerForm;
import mfs.biller.persistence.bean.BillerMaster;
import mfs.biller.persistence.bean.BillerPaymentValidate;
import mfs.biller.persistence.bean.BillerRef;
import mfs.biller.persistence.bean.BillerRefDetail;
import mfs.biller.persistence.bean.BillerRefParam;
import mfs.biller.persistence.bean.BillerService;
import mfs.biller.persistence.bean.BillerServiceChannel;
import mfs.biller.persistence.bean.BillerServiceImage;
import mfs.biller.persistence.bean.BillerServiceParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;
import mfs.exception.NotFoundDataException;

import com.dtac.billerweb.data.BillerBarcodeDetailSO;
import com.dtac.billerweb.data.BillerBarcodeSO;
import com.dtac.billerweb.data.BillerChannelSO;
import com.dtac.billerweb.data.BillerDenominateSO;
import com.dtac.billerweb.data.BillerServiceChannelSO;
import com.dtac.billerweb.data.BillerServiceInputSO;
import com.dtac.billerweb.data.BillerServiceSO;

public interface BillerServiceService {
	public List<BillerServiceSO> searchBillerService(BillerServiceParam condition, int currentPage, int pageSize, UserInfoBean userInfo);

	public BillerService getBillerServiceInfoByID(Integer id, UserInfoBean userInfo) throws NotFoundDataException;

	public Integer saveBillerServiceInfo(BillerService billerService, UserInfoBean userInfo) throws IsExistException;

	public void updateBillerServiceInfo(BillerService billerervice, UserInfoBean userInfo) throws IsExistException;

	public BillerPaymentValidate getBillerPaymentValidateByID(Integer id, UserInfoBean userInfo) throws NotFoundDataException;

	public Integer saveBillerPaymentValidate(BillerPaymentValidate billerPaymentVal, UserInfoBean userInfo) throws IsExistException;

	public void updateBillerPaymentValidate(BillerPaymentValidate billerPaymentVal, UserInfoBean userInfo) throws IsExistException;

	public List<BillerServiceChannelSO> searchBillerServiceChannel(Integer billerServiceId, UserInfoBean userInfo);

	public BillerServiceChannel getBillerServiceChannelByID(Integer id, Integer serviceId, UserInfoBean userInfo);

	public void saveBillerServiceChannel(BillerServiceChannel billerServiceChannel, UserInfoBean userInfo) throws IsExistException;

	public void updateBillerServiceChannel(BillerServiceChannel billerServiceChannel, UserInfoBean userInfo) throws IsExistException;

	public List<BillerServiceInputSO> searchBillerServiceInput(BillerRefParam billerRefParam, UserInfoBean userInfo);

	public BillerForm getBillerFormByServiceID(Integer serviceId, UserInfoBean userInfo);

	public Integer saveBillerForm(BillerForm billerForm, UserInfoBean userInfo) throws IsExistException;

	public void updateBillerForm(BillerForm billerForm, UserInfoBean userInfo) throws IsExistException;

	public BillerForm getBillerFormByID(Integer id, UserInfoBean userInfo);

	public BillerRef getBillerRefByID(Integer id, UserInfoBean userInfo);

	public Integer saveBillerRef(BillerRef billerRef, UserInfoBean userInfo) throws IsExistException;

	public void updateBillerRef(BillerRef billerRef, UserInfoBean userInfo) throws IsExistException;

	public List<BillerBarcodeSO> searchBillerBarcodeByServiceId(Integer billerServiceId, UserInfoBean userInfo);

	public List<BillerBarcodeDetailSO> searchBillerBarcodeDetailByServiceId(Integer billerServiceId, UserInfoBean userInfo);

	public BillerRefDetail getBillerRefDetailByID(Integer refId, UserInfoBean userInfo);

	public BillerBarcodeRef getBillerBarcodeRefByID(Integer id, UserInfoBean userInfo);

	public Integer saveBillerBarcodeRef(BillerBarcodeRef billerBarcodeRef, UserInfoBean userInfo) throws IsExistException;

	public void updateBillerBarcodeRef(BillerBarcodeRef billerBarcodeRef, UserInfoBean userInfo) throws IsExistException;
	
	
	public List<BillerDenominateSO> searchBillerDenominate(Integer serviceId, UserInfoBean userInfo);

	public BillerDenominate getBillerDenominateByID(Integer id, UserInfoBean userInfo);

	public Integer saveBillerDenominate(BillerDenominate billerDenominate, UserInfoBean userInfo) throws IsExistException;

	public void updateBillerDenominate(BillerDenominate billerDenominate, UserInfoBean userInfo) throws IsExistException;

	public BillerServiceImage getBillerServiceImage(Integer serviceId, UserInfoBean userInfo);
	
	public Integer saveBillerServiceImage(BillerServiceImage billerServiceImage, UserInfoBean userInfo) throws IsExistException;

	public void updateBillerServiceImage(BillerServiceImage billerServiceImage, UserInfoBean userInfo) throws IsExistException;
}
