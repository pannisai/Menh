package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.BankSystemMATNBeanRemote;
import mfs.biller.persistence.bean.BankSystemMATN;
import mfs.biller.persistence.bean.BankSystemMATNDetail;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.constants.Constants;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.BankMaintenanceSO;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.BankMaintenanceService;
import com.dtac.billerweb.util.AppUtil;

public class BankMaintenanceServiceImpl extends BaseService implements BankMaintenanceService {
	private Logger log = Logger.getLogger(BankMaintenanceServiceImpl.class);

	@Override
	public List<BankMaintenanceSO> searchBankMaintenance(BankSystemMATNDetail criteria, int currentPage, int pageSize, UserInfoBean userInfo) {
		log.info("start searchBankMaintenance");
		BankSystemMATNBeanRemote bankSystemMATNBeanRemote = null;
		List<BankMaintenanceSO> bankMaintenanceSOs = null;
		List<BankSystemMATNDetail> bankSystemMATNDetails = null;
		try {
			bankSystemMATNBeanRemote = (BankSystemMATNBeanRemote) EJBInitialContext.lookup(Constants.JNDI.bankSystemMATNBean);
			bankSystemMATNDetails = bankSystemMATNBeanRemote.searchBankSystemMATNAll(criteria, userInfo);
			log.info("bankSystemMATNDetails size::" + bankSystemMATNDetails.size());

			bankMaintenanceSOs = toBankMaintenanceSOList(bankSystemMATNDetails);
			log.info("bankMaintenanceSOs size::" + bankMaintenanceSOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankSystemMATNDetails = null;
		}

		return bankMaintenanceSOs;
	}

	private List<BankMaintenanceSO> toBankMaintenanceSOList(List<BankSystemMATNDetail> bankSystemMATNDetails) throws Exception {

		List<BankMaintenanceSO> bankMaintenanceSOs = new ArrayList<BankMaintenanceSO>();
		try {
			if (AppUtil.isEmpty(bankSystemMATNDetails)) {
				return bankMaintenanceSOs;
			}
			BankMaintenanceSO bankMaintenanceSO = null;
			for (BankSystemMATNDetail bankSystemMATNDetail : bankSystemMATNDetails) {
				bankMaintenanceSO = new BankMaintenanceSO();
				bankMaintenanceSO = bankMaintenanceSO.toBankMaintenanceSO(bankSystemMATNDetail);
				bankMaintenanceSOs.add(bankMaintenanceSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return bankMaintenanceSOs;

	}

	@Override
	public BankSystemMATN getByID(Integer id, UserInfoBean userInfo) {
		BankSystemMATNBeanRemote bankSystemMATNBeanRemote = null;
		BankSystemMATN bankSystemMATN = new BankSystemMATN();
		try {
			bankSystemMATNBeanRemote = (BankSystemMATNBeanRemote) EJBInitialContext.lookup(Constants.JNDI.bankSystemMATNBean);
			bankSystemMATN = bankSystemMATNBeanRemote.findBankSystemMATN(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankSystemMATNBeanRemote = null;
		}
		if (bankSystemMATN == null) {
			return new BankSystemMATN();
		}
		return bankSystemMATN;
	}

	@Override
	public Integer save(BankSystemMATN bankSystemMATN, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		BankSystemMATNBeanRemote bankSystemMATNBeanRemote = null;
		Integer oid = -1;
		try {
			bankSystemMATNBeanRemote = (BankSystemMATNBeanRemote) EJBInitialContext.lookup(Constants.JNDI.bankSystemMATNBean);
			oid = bankSystemMATNBeanRemote.insertBankSystemMATN(bankSystemMATN, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankSystemMATNBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void update(BankSystemMATN bankSystemMATN, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		BankSystemMATNBeanRemote bankSystemMATNBeanRemote = null;
		try {
			bankSystemMATNBeanRemote = (BankSystemMATNBeanRemote) EJBInitialContext.lookup(Constants.JNDI.bankSystemMATNBean);
			bankSystemMATNBeanRemote.updateBankSystemMATN(bankSystemMATN, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankSystemMATNBeanRemote = null;
		}
	}

}
