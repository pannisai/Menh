package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.BankServiceRemote;
import mfs.biller.persistence.bean.BankServiceDetail;
import mfs.biller.persistence.bean.BankServicebean;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.constants.Constants;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.BankServiceSO;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.BankServiceService;
import com.dtac.billerweb.util.AppUtil;

public class BankServiceServiceImpl extends BaseService implements BankServiceService {
	private Logger log = Logger.getLogger(BankServiceServiceImpl.class);

	@Override
	public List<BankServiceSO> searchBankService(BankServicebean criteria, int currentPage, int pageSize, UserInfoBean userInfo) {
		log.info("start searchBankService");
		BankServiceRemote bankServiceRemote = null;
		List<BankServiceSO> bankServiceSOs = null;
		List<BankServiceDetail> bankServiceDetails = null;
		try {
			bankServiceRemote = (BankServiceRemote) EJBInitialContext.lookup(Constants.JNDI.bankService);
			bankServiceDetails = bankServiceRemote.searchBankServiceAll(criteria, userInfo);
			log.info("bankServiceDetails size::" + bankServiceDetails.size());

			bankServiceSOs = toBankServiceSOList(bankServiceDetails);
			log.info("bankServiceSOs size::" + bankServiceSOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankServiceDetails = null;
		}

		return bankServiceSOs;
	}

	private List<BankServiceSO> toBankServiceSOList(List<BankServiceDetail> bankServiceDetails) throws Exception {

		List<BankServiceSO> bankServiceSOs = new ArrayList<BankServiceSO>();
		try {
			if (AppUtil.isEmpty(bankServiceDetails)) {
				return bankServiceSOs;
			}
			BankServiceSO bankServiceSO = null;
			for (BankServiceDetail bankServiceDetail : bankServiceDetails) {
				bankServiceSO = new BankServiceSO();
				bankServiceSO = bankServiceSO.toBankServiceSO(bankServiceDetail);
				bankServiceSOs.add(bankServiceSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankServiceDetails = null;
		}
		return bankServiceSOs;

	}

	@Override
	public BankServicebean getByID(Integer id, UserInfoBean userInfo) {
		BankServiceRemote bankServiceRemote = null;
		BankServicebean bankServicebean = new BankServicebean();
		try {
			bankServiceRemote = (BankServiceRemote) EJBInitialContext.lookup(Constants.JNDI.bankService);
			bankServicebean = bankServiceRemote.findBankService(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankServiceRemote = null;
		}
		if (bankServicebean == null) {
			return new BankServicebean();
		}
		return bankServicebean;
	}

	@Override
	public Integer save(BankServicebean bankServicebean, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		BankServiceRemote bankServiceRemote = null;
		Integer oid = -1;
		try {
			bankServiceRemote = (BankServiceRemote) EJBInitialContext.lookup(Constants.JNDI.bankService);
			oid = bankServiceRemote.insertBankService(bankServicebean, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankServiceRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void update(BankServicebean bankServicebean, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		BankServiceRemote bankServiceRemote = null;
		try {
			bankServiceRemote = (BankServiceRemote) EJBInitialContext.lookup(Constants.JNDI.bankService);
			bankServiceRemote.updateBankService(bankServicebean, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankServiceRemote = null;
		}
	}

}
