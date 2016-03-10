package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mfs.biller.ejb.interfaces.BankChannelRemote;
import mfs.biller.ejb.interfaces.BankMasterRemote;
import mfs.biller.persistence.bean.BankChannelBean;
import mfs.biller.persistence.bean.BankMasterBean;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.BankChannelSO;
import com.dtac.billerweb.data.BankMasterSO;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.BankChannelService;
import com.dtac.billerweb.util.AppUtil;

public class BankChannelServiceImpl extends BaseService implements BankChannelService {
	private Logger log = Logger.getLogger(BankChannelServiceImpl.class);

	@Override
	public List<BankChannelSO> searchBankChannel(BankChannelBean criteria, int currentPage, int pageSize, UserInfoBean userInfo) {
		log.info("start searchBankChannel");
		BankChannelRemote bankChannelRemote = null;
		List<BankChannelSO> bankChannelSOs = null;
		List<BankChannelBean> bankChannelBeans = null;
		try {
			bankChannelRemote = (BankChannelRemote) EJBInitialContext.lookup(BankChannelRemote.JNDI_WEBLOGIC);
			bankChannelBeans = bankChannelRemote.searchBankChannelAll(criteria, userInfo);
			log.info("bankChannelBeans size::" + bankChannelBeans.size());

			bankChannelSOs = toBankChannelSOList(bankChannelBeans);
			log.info("bankChannelSOs size::" + bankChannelSOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankChannelBeans = null;
		}

		return bankChannelSOs;
	}

	private List<BankChannelSO> toBankChannelSOList(List<BankChannelBean> bankChannelBeans) throws Exception {

		List<BankChannelSO> bankChannelSOs = new ArrayList<BankChannelSO>();
		try {
			if (AppUtil.isEmpty(bankChannelBeans)) {
				return bankChannelSOs;
			}
			BankChannelSO bankChannelSO = null;
			for (BankChannelBean bankChannelBean : bankChannelBeans) {
				bankChannelSO = new BankChannelSO();
				bankChannelSO = bankChannelSO.toBankChannelSO(bankChannelBean);
				bankChannelSOs.add(bankChannelSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankChannelBeans = null;
		}
		return bankChannelSOs;

	}

	@Override
	public BankChannelBean getByID(String id, UserInfoBean userInfo) {
		BankChannelRemote bankChannelRemote = null;
		BankChannelBean bankChannelBean = new BankChannelBean();
		try {
			bankChannelRemote = (BankChannelRemote) EJBInitialContext.lookup(BankChannelRemote.JNDI_WEBLOGIC);
			bankChannelBean = bankChannelRemote.findBankChannel(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankChannelRemote = null;
		}
		if (bankChannelBean == null) {
			return new BankChannelBean();
		}
		return bankChannelBean;
	}

	@Override
	public void save(BankChannelBean bankChannelBean, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		BankChannelRemote bankChannelRemote = null;
		try {
			bankChannelRemote = (BankChannelRemote) EJBInitialContext.lookup(BankChannelRemote.JNDI_WEBLOGIC);
			bankChannelRemote.insertBankChannel(bankChannelBean, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankChannelRemote = null;
		}
	}

	@Override
	public void update(BankChannelBean bankChannelBean, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		BankChannelRemote bankChannelRemote = null;
		try {
			bankChannelRemote = (BankChannelRemote) EJBInitialContext.lookup(BankChannelRemote.JNDI_WEBLOGIC);
			bankChannelRemote.updateBankChannel(bankChannelBean, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankChannelRemote = null;
		}
	}

	@Override
	public boolean isExistBankChannelCode(String bankChannelCode, UserInfoBean userInfo) {
		// TODO Auto-generated method stub
		BankChannelRemote bankChannelRemote = null;
		boolean result = false;
		try {
			bankChannelRemote = (BankChannelRemote) EJBInitialContext.lookup(BankChannelRemote.JNDI_WEBLOGIC);
			result = bankChannelRemote.isExistBankChannelCode(bankChannelCode, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankChannelRemote = null;
		}
		return result;
	}

}
