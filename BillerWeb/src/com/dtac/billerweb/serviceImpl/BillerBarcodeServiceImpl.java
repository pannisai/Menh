package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.BillerBarcodeBeanRemote;
import mfs.biller.persistence.bean.BillerBarcode;
import mfs.biller.persistence.bean.BillerBarcodeParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.constants.Constants;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.listmodel.selectbox.BillerBarcodeListModel;
import com.dtac.billerweb.service.BillerBarcodeService;
import com.dtac.billerweb.util.AppUtil;

public class BillerBarcodeServiceImpl extends BaseService implements BillerBarcodeService {
	
	private Logger log = Logger.getLogger(BillerBarcodeServiceImpl.class);
	
	@Override
	public BillerBarcodeListModel getBillerBarcodeListModel(UserInfoBean user) throws Exception {
		BillerBarcodeListModel billerBarcodeListModel = new BillerBarcodeListModel(new ArrayList<BillerBarcode>());
		BillerBarcodeBeanRemote objRemote = null;
		try {
			log.info("Get BillerBarcode");
			
			List<BillerBarcode> list = new ArrayList<BillerBarcode>();
			
			objRemote = (BillerBarcodeBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerBarcodeBean);
			if (objRemote != null) {
				list = objRemote.getBillerBarcodeAll(user);
			}
			if (AppUtil.isNull(list)) {
				list = new ArrayList<BillerBarcode>();
			}
			log.info(" BillerBarcodeList Size::" + list.size());
			
			billerBarcodeListModel = new BillerBarcodeListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			objRemote = null;
		}
		return billerBarcodeListModel;
	}
		
	public List<BillerBarcode> searchBillerBarcode(BillerBarcodeParam condition, int currentPage, int pageSize, UserInfoBean userInfo){
		log.info("start searchBillerBarcode");
		BillerBarcodeBeanRemote objRemote = null;
		List<BillerBarcode> billerBarcode = null;
		try {
			objRemote = (BillerBarcodeBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerBarcodeBean);
			billerBarcode = objRemote.searchBillerBarcode(condition, userInfo);
			
			if (AppUtil.isEmpty(billerBarcode)) {
				billerBarcode = new ArrayList<BillerBarcode>();
			}
			
			log.info("billerCategorys size::" + billerBarcode.size());
			
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			objRemote = null;
		}
		return billerBarcode;
	}
	
	public BillerBarcode getByID(Integer id, UserInfoBean userInfo){
		log.info("start getByID");
		BillerBarcodeBeanRemote objRemote = null;
		BillerBarcode billerBarcode = null;
		try {
			objRemote = (BillerBarcodeBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerBarcodeBean);
			billerBarcode = objRemote.findBillerBarcode(id.intValue(), userInfo);
			
			if (billerBarcode == null) {
				billerBarcode = new BillerBarcode();
			}
			
			log.info("result::" + billerBarcode.toString());
			
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			objRemote = null;
		}
		return billerBarcode;
	}
	
	@Override
	public Integer save(BillerBarcode billerBarcode, UserInfoBean userInfo) throws Exception {
		BillerBarcodeBeanRemote objRemote = null;
		Integer oid=-1;
		try {
			objRemote = (BillerBarcodeBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerBarcodeBean);
			oid = objRemote.insertBillerBarcode(billerBarcode, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			objRemote = null;
		}
		if (oid==null){
			return -1;
		}
		return oid;
	}
	
	@Override
	public void update(BillerBarcode billerBarcode, UserInfoBean userInfo) throws Exception{
		BillerBarcodeBeanRemote objRemote = null;
		try {
			objRemote = (BillerBarcodeBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerBarcodeBean);
			objRemote.updateBillerBarcode(billerBarcode, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			objRemote = null;
		}
	}
}
