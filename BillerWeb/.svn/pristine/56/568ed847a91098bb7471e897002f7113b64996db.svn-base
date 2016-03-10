package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.BillerCatalogBeanRemote;
import mfs.biller.ejb.interfaces.BillerMasterBeanRemote;
import mfs.biller.persistence.bean.BillerCategory;
import mfs.biller.persistence.bean.BillerMaster;
import mfs.biller.persistence.bean.BillerMasterParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.BillerMasterSO;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.BillerMasterService;
import com.dtac.billerweb.util.AppUtil;

public class BillerMasterServiceImpl extends BaseService implements BillerMasterService {
	private Logger log = Logger.getLogger(BillerMasterServiceImpl.class);

	@Override
	public List<BillerMasterSO> searchBillerMaster(BillerMasterParam condition, int currentPage, int pageSize, UserInfoBean userInfo) {
		log.info("start searchBillerMaster");
		List<BillerMasterSO> billerMasterSOs = null;
		List<BillerMaster> billerMasters = null;
		try {
			billerMasters = getBillerMasters(condition, currentPage, pageSize, userInfo);
			log.info("billerMasters size::" + billerMasters.size());

			billerMasterSOs = toBillerMasterSOList(billerMasters);
			log.info("billerMasterSOs size::" + billerMasterSOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerMasters = null;
		}

		return billerMasterSOs;
	}

	private List<BillerMaster> getBillerMasters(BillerMasterParam condition, int currentPage, int pageSize, UserInfoBean userInfo) {
		BillerMasterBeanRemote billerMasterBeanRemote = null;
		List<BillerMaster> billerMasters = null;
		try {
			billerMasterBeanRemote = (BillerMasterBeanRemote) EJBInitialContext.lookup(BillerMasterBeanRemote.JNDI_WEBLOGIC);
			// condition.setPAGE_NO(currentPage);
			// condition.setPAGE_SIZE(pageSize);
			billerMasters = billerMasterBeanRemote.searchBillerMaster(condition, userInfo);
			if (AppUtil.isEmpty(billerMasters)) {
				billerMasters = new ArrayList<BillerMaster>();
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			/*--Garbage-*/
			billerMasterBeanRemote = null;
		}
		return billerMasters;
	}

	private List<BillerMasterSO> toBillerMasterSOList(List<BillerMaster> billerMasters) throws Exception {

		List<BillerMasterSO> billerMasterSOs = new ArrayList<BillerMasterSO>();
		try {
			if (AppUtil.isEmpty(billerMasters)) {
				return billerMasterSOs;
			}
			BillerMasterSO billerMasterSO = null;
			for (BillerMaster billerMaster : billerMasters) {
				billerMasterSO = new BillerMasterSO();
				billerMasterSO = billerMasterSO.toBillerMasterSO(billerMaster);
				billerMasterSOs.add(billerMasterSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerMasters = null;
		}
		return billerMasterSOs;

	}

	@Override
	public BillerMaster getByID(Integer id, UserInfoBean userInfo) {
		BillerMasterBeanRemote billerMasterBeanRemote = null;
		BillerMaster billerMaster = new BillerMaster();
		try {
			billerMasterBeanRemote = (BillerMasterBeanRemote) EJBInitialContext.lookup(BillerMasterBeanRemote.JNDI_WEBLOGIC);
			billerMaster = billerMasterBeanRemote.findBillerMaster(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerMasterBeanRemote = null;
		}
		if (billerMaster == null) {
			return new BillerMaster();
		}
		return billerMaster;
	}

	@Override
	public boolean isExistBillerMasterId(Integer id, UserInfoBean userInfo) {
		BillerMasterBeanRemote billerMasterBeanRemote = null;
		boolean result = false;
		try {
			billerMasterBeanRemote = (BillerMasterBeanRemote) EJBInitialContext.lookup(BillerMasterBeanRemote.JNDI_WEBLOGIC);
			result = billerMasterBeanRemote.isExistBllrMstrId(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerMasterBeanRemote = null;
		}
		return result;
	}

	@Override
	public boolean isExistBlllerCode(Integer id, String billerCode, UserInfoBean userInfo) {
		BillerMasterBeanRemote billerMasterBeanRemote = null;
		boolean result = false;
		try {
			billerMasterBeanRemote = (BillerMasterBeanRemote) EJBInitialContext.lookup(BillerMasterBeanRemote.JNDI_WEBLOGIC);
			result = billerMasterBeanRemote.isExistBllrCode(id, billerCode, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerMasterBeanRemote = null;
		}
		return result;
	}

	@Override
	public void save(BillerMaster billerMaster, UserInfoBean userInfo) throws IsExistException {
		BillerMasterBeanRemote billerMasterBeanRemote = null;		
		try {
			billerMasterBeanRemote = (BillerMasterBeanRemote) EJBInitialContext.lookup(BillerMasterBeanRemote.JNDI_WEBLOGIC);	
			billerMasterBeanRemote.insertBillerMaster(billerMaster, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerMasterBeanRemote = null;
		}		
	}

	@Override
	public void update(BillerMaster billerMaster, UserInfoBean userInfo) throws IsExistException {
		BillerMasterBeanRemote billerMasterBeanRemote = null;		
		try {
			billerMasterBeanRemote = (BillerMasterBeanRemote) EJBInitialContext.lookup(BillerMasterBeanRemote.JNDI_WEBLOGIC);	
			billerMasterBeanRemote.updateBillerMaster(billerMaster, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerMasterBeanRemote = null;
		}		
	}

}
