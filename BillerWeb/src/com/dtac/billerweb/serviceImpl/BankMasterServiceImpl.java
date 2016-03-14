package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.BankMasterRemote;
import mfs.biller.persistence.bean.BankMasterBean;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.constants.Constants;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.BankMasterSO;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.BankMasterService;
import com.dtac.billerweb.util.AppUtil;

public class BankMasterServiceImpl extends BaseService implements BankMasterService {
	private Logger log = Logger.getLogger(BankMasterServiceImpl.class);

	@Override
	public List<BankMasterSO> searchBankMaster(BankMasterBean criteria, int currentPage, int pageSize, UserInfoBean userInfo) {
		log.info("start searchBankMaster");
		BankMasterRemote bankMasterRemote = null;
		List<BankMasterSO> bankMasterSOs = null;
		List<BankMasterBean> bankMasterBeans = null;
		try {
			bankMasterRemote = (BankMasterRemote) EJBInitialContext.lookup(Constants.JNDI.bankMaster);
			bankMasterBeans = bankMasterRemote.searchBankMasterAll(criteria, userInfo);
			log.info("bankMasterBeans size::" + bankMasterBeans.size());

			bankMasterSOs = toBankMasterSOList(bankMasterBeans);
			log.info("bankMasterSOs size::" + bankMasterSOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankMasterBeans = null;
		}

		return bankMasterSOs;
	}

	private List<BankMasterSO> toBankMasterSOList(List<BankMasterBean> bankMasterBeans) throws Exception {

		List<BankMasterSO> bankMasterSOs = new ArrayList<BankMasterSO>();
		try {
			if (AppUtil.isEmpty(bankMasterBeans)) {
				return bankMasterSOs;
			}
			BankMasterSO bankMasterSO = null;
			for (BankMasterBean bankMasterBean : bankMasterBeans) {
				bankMasterSO = new BankMasterSO();
				bankMasterSO = bankMasterSO.toBankMasterSO(bankMasterBean);
				bankMasterSOs.add(bankMasterSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankMasterBeans = null;
		}
		return bankMasterSOs;

	}

	@Override
	public BankMasterBean getByID(String id, UserInfoBean userInfo) {
		BankMasterRemote bankMasterRemote = null;
		BankMasterBean bankMasterBean = new BankMasterBean();
		try {
			bankMasterRemote = (BankMasterRemote) EJBInitialContext.lookup(Constants.JNDI.bankMaster);
			bankMasterBean = bankMasterRemote.findBankMaster(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankMasterRemote = null;
		}
		if (bankMasterBean == null) {
			return new BankMasterBean();
		}
		return bankMasterBean;
	}

	@Override
	public void save(BankMasterBean bankMasterBean, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		BankMasterRemote bankMasterRemote = null;
		try {
			bankMasterRemote = (BankMasterRemote) EJBInitialContext.lookup(Constants.JNDI.bankMaster);
			bankMasterRemote.insertBankMaster(bankMasterBean, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankMasterRemote = null;
		}
	}

	@Override
	public void update(BankMasterBean bankMasterBean, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		BankMasterRemote bankMasterRemote = null;
		try {
			bankMasterRemote = (BankMasterRemote) EJBInitialContext.lookup(Constants.JNDI.bankMaster);
			bankMasterRemote.updateBankMaster(bankMasterBean, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankMasterRemote = null;
		}
	}

	@Override
	public boolean isExistBankCode(String bankCode, UserInfoBean userInfo) {
		// TODO Auto-generated method stub
		BankMasterRemote bankMasterRemote = null;
		boolean result = false;
		try {
			bankMasterRemote = (BankMasterRemote) EJBInitialContext.lookup(Constants.JNDI.bankMaster);
			result = bankMasterRemote.isExistBankMstrCode(bankCode, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankMasterRemote = null;
		}
		return result;
	}

}
