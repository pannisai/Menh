package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mfs.biller.ejb.interfaces.BankChannelRemote;
import mfs.biller.ejb.interfaces.GWBankRemote;
import mfs.biller.persistence.bean.BankChannelBean;
import mfs.biller.persistence.bean.GWBankBean;
import mfs.biller.persistence.bean.GWBankDetail;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.BankChannelSO;
import com.dtac.billerweb.data.BankGatewaySO;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.BankGatewayService;
import com.dtac.billerweb.util.AppUtil;

public class BankGatewayServiceImpl extends BaseService implements BankGatewayService {
	private Logger log = Logger.getLogger(BankGatewayServiceImpl.class);

	@Override
	public List<BankGatewaySO> searchBankGateway(GWBankDetail criteria, int currentPage, int pageSize, UserInfoBean userInfo) {
		log.info("start searchBankGateway");
		GWBankRemote gwBankRemote = null;
		List<BankGatewaySO> bankGatewaySOs = null;
		List<GWBankDetail> gwBankDetails = null;
		try {
			gwBankRemote = (GWBankRemote) EJBInitialContext.lookup(GWBankRemote.JNDI_WEBLOGIC);
			gwBankDetails = gwBankRemote.searchGWBankAll(criteria, userInfo);
			log.info("gwBankDetails size::" + gwBankDetails.size());

			bankGatewaySOs = toBankGatewaySOList(gwBankDetails);
			log.info("bankGatewaySOs size::" + bankGatewaySOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwBankRemote = null;
			gwBankDetails = null;
		}

		return bankGatewaySOs;
	}

	private List<BankGatewaySO> toBankGatewaySOList(List<GWBankDetail> gwBankDetails) throws Exception {

		List<BankGatewaySO> bankGatewaySOs = new ArrayList<BankGatewaySO>();
		try {
			if (AppUtil.isEmpty(gwBankDetails)) {
				return bankGatewaySOs;
			}
			BankGatewaySO bankGatewaySO = null;
			for (GWBankDetail gwBankDetail : gwBankDetails) {
				bankGatewaySO = new BankGatewaySO();
				bankGatewaySO = bankGatewaySO.toBankGatewaySO(gwBankDetail);
				bankGatewaySOs.add(bankGatewaySO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwBankDetails = null;
		}
		return bankGatewaySOs;

	}

	@Override
	public GWBankBean getByID(Integer id, UserInfoBean userInfo) {
		GWBankRemote gwBankRemote = null;
		GWBankBean gwBankBean = new GWBankBean();
		try {
			gwBankRemote = (GWBankRemote) EJBInitialContext.lookup(GWBankRemote.JNDI_WEBLOGIC);
			gwBankBean = gwBankRemote.findGWBank(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwBankRemote = null;
		}
		if (gwBankBean == null) {
			return new GWBankBean();
		}
		return gwBankBean;
	}

	@Override
	public Integer save(GWBankBean gwBankBean, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		GWBankRemote gwBankRemote = null;
		Integer oid = -1;
		try {
			gwBankRemote = (GWBankRemote) EJBInitialContext.lookup(GWBankRemote.JNDI_WEBLOGIC);
			oid = gwBankRemote.insertGWBank(gwBankBean, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwBankRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void update(GWBankBean gwBankBean, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		GWBankRemote gwBankRemote = null;
		try {
			gwBankRemote = (GWBankRemote) EJBInitialContext.lookup(GWBankRemote.JNDI_WEBLOGIC);
			gwBankRemote.updateGWBank(gwBankBean, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwBankRemote = null;
		}
	}

}
