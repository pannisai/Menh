package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.ERPBankAccountBeanRemote;
import mfs.biller.persistence.bean.ERPBankAccount;
import mfs.biller.persistence.bean.ERPBankAccountDtail;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.ERPBankAccountSO;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.ERPBankAccountService;
import com.dtac.billerweb.util.AppUtil;

public class ERPBankAccountServiceImpl extends BaseService implements ERPBankAccountService {
	private Logger log = Logger.getLogger(ERPBankAccountServiceImpl.class);

	@Override
	public List<ERPBankAccountSO> searchERPBankAccount(Integer billerServiceId, String accountBankCode, String acctiveFlag, int currentPage, int pageSize, UserInfoBean userInfo) {
		// TODO Auto-generated method stub
		log.info("start searchERPBankAccount");
		ERPBankAccountBeanRemote erpBankAccountBeanRemote = null;
		List<ERPBankAccountSO> erpBankAccountSOs = null;
		List<ERPBankAccountDtail> erpBankAccountDtails = null;
		try {
			erpBankAccountBeanRemote = (ERPBankAccountBeanRemote) EJBInitialContext.lookup(ERPBankAccountBeanRemote.JNDI_WEBLOGIC);
			erpBankAccountDtails = erpBankAccountBeanRemote.searchERPBankAccount(billerServiceId, accountBankCode, acctiveFlag, userInfo);
			log.info("erpBankAccountDtails size::" + erpBankAccountDtails.size());

			erpBankAccountSOs = toERPBankAccountSOList(erpBankAccountDtails);
			log.info("erpBankAccountSOs size::" + erpBankAccountSOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			erpBankAccountBeanRemote = null;
			erpBankAccountDtails = null;
		}

		return erpBankAccountSOs;
	}

	private List<ERPBankAccountSO> toERPBankAccountSOList(List<ERPBankAccountDtail> erpBankAccountDtails) throws Exception {

		List<ERPBankAccountSO> erpBankAccountSOs = new ArrayList<ERPBankAccountSO>();
		try {
			if (AppUtil.isEmpty(erpBankAccountDtails)) {
				return erpBankAccountSOs;
			}
			ERPBankAccountSO erpBankAccountSO = null;
			for (ERPBankAccountDtail erpBankAccountDtail : erpBankAccountDtails) {
				erpBankAccountSO = new ERPBankAccountSO();
				erpBankAccountSO = erpBankAccountSO.toERPBankAccountSO(erpBankAccountDtail);
				erpBankAccountSOs.add(erpBankAccountSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {

		}
		return erpBankAccountSOs;

	}

	@Override
	public ERPBankAccount getByID(Integer id, UserInfoBean userInfo) {
		// TODO Auto-generated method stub
		ERPBankAccountBeanRemote erpBankAccountBeanRemote = null;
		ERPBankAccount erpBankAccount = new ERPBankAccount();
		try {
			erpBankAccountBeanRemote = (ERPBankAccountBeanRemote) EJBInitialContext.lookup(ERPBankAccountBeanRemote.JNDI_WEBLOGIC);
			erpBankAccount = erpBankAccountBeanRemote.findERPBankAccount(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			erpBankAccountBeanRemote = null;
		}
		if (erpBankAccount == null) {
			return new ERPBankAccount();
		}
		return erpBankAccount;
	}

	@Override
	public Integer save(ERPBankAccount erpBankAccount, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		ERPBankAccountBeanRemote erpBankAccountBeanRemote = null;
		Integer oid = -1;
		try {
			erpBankAccountBeanRemote = (ERPBankAccountBeanRemote) EJBInitialContext.lookup(ERPBankAccountBeanRemote.JNDI_WEBLOGIC);
			oid = erpBankAccountBeanRemote.insertERPBankAccount(erpBankAccount, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			erpBankAccountBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void update(ERPBankAccount erpBankAccount, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		ERPBankAccountBeanRemote erpBankAccountBeanRemote = null;
		try {
			erpBankAccountBeanRemote = (ERPBankAccountBeanRemote) EJBInitialContext.lookup(ERPBankAccountBeanRemote.JNDI_WEBLOGIC);
			erpBankAccountBeanRemote.updateERPBankAccount(erpBankAccount, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			erpBankAccountBeanRemote = null;
		}
	}

}
