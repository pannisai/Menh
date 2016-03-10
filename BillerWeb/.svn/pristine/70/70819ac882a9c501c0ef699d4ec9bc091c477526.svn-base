package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.BankRptTransBeanRemote;
import mfs.biller.ejb.interfaces.BillerServiceBeanRemote;
import mfs.biller.ejb.interfaces.InboundGatewayResultRemote;
import mfs.biller.persistence.bean.BankReportTransDetail;
import mfs.biller.persistence.bean.BankReportTransParam;
import mfs.biller.persistence.bean.BillerService;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.NotFoundDataException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.BankReportTranSO;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.BankReportTranService;
import com.dtac.billerweb.util.AppUtil;

public class BankReportTranServiceImpl extends BaseService implements BankReportTranService {
	private Logger log = Logger.getLogger(BankReportTranServiceImpl.class);

	@Override
	public List<BankReportTranSO> searchBankReportTran(BankReportTransParam criteria, int currentPage, int pageSize, UserInfoBean userInfo) {
		log.info("start searchBankReportTran");
		BankRptTransBeanRemote bankRptTransBeanRemote = null;
		List<BankReportTranSO> bankReportTranSOs = null;
		List<BankReportTransDetail> bankReportTransDetails = null;
		try {
			bankRptTransBeanRemote = (BankRptTransBeanRemote) EJBInitialContext.lookup(BankRptTransBeanRemote.JNDI_WEBLOGIC);
			criteria.setPAGE_NO(currentPage);
			criteria.setPAGE_SIZE(pageSize);
			bankReportTransDetails = bankRptTransBeanRemote.searchBankReportTrans(criteria, userInfo);
			log.info("bankReportTransDetails size::" + bankReportTransDetails.size());

			bankReportTranSOs = toBankReportTranSOList(bankReportTransDetails);
			log.info("bankReportTranSOs size::" + bankReportTranSOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankReportTransDetails = null;
		}

		return bankReportTranSOs;
	}

	private List<BankReportTranSO> toBankReportTranSOList(List<BankReportTransDetail> bankReportTransDetails) throws Exception {

		List<BankReportTranSO> bankReportTranSOs = new ArrayList<BankReportTranSO>();
		try {
			if (AppUtil.isEmpty(bankReportTransDetails)) {
				return bankReportTranSOs;
			}
			BankReportTranSO bankReportTranSO = null;
			for (BankReportTransDetail bankReportTransDetail : bankReportTransDetails) {
				bankReportTranSO = new BankReportTranSO();
				bankReportTranSO = bankReportTranSO.toBankReportTranSO(bankReportTransDetail);
				bankReportTranSOs.add(bankReportTranSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankReportTransDetails = null;
		}
		return bankReportTranSOs;
	}

	@Override
	public int getBankReportTranRowCount(BankReportTransParam criteria, UserInfoBean userInfo) throws Exception {
		// TODO Auto-generatInboundGatewayResultRemote
		// inboundGatewayResultRemote = null;
		BankRptTransBeanRemote bankRptTransBeanRemote = null;
		Integer rownum = 0;
		try {
			bankRptTransBeanRemote = (BankRptTransBeanRemote) EJBInitialContext.lookup(BankRptTransBeanRemote.JNDI_WEBLOGIC);
			try {
				rownum = bankRptTransBeanRemote.countRowBankReportTrans(criteria, userInfo);

			} catch (NullPointerException npe) {

			}
		} catch (Exception ex) {
			throw new ServiceException("Get Bank Report Transaction Row Count Fail", ex);
		} finally {
			/*--Garbage-*/
			bankRptTransBeanRemote = null;
			if (rownum == null) {
				rownum = 0;
			}
		}
		return rownum;
	}

	@Override
	public BankReportTranSO getBankReportTranByID(String id, UserInfoBean userInfo) {
		BankRptTransBeanRemote bankRptTransBeanRemote = null;
		BankReportTranSO bankReportTranSO = new BankReportTranSO();
		BankReportTransDetail bankReportTransDetail = new BankReportTransDetail();
		try {
			bankRptTransBeanRemote = (BankRptTransBeanRemote) EJBInitialContext.lookup(BankRptTransBeanRemote.JNDI_WEBLOGIC);
			bankReportTransDetail = bankRptTransBeanRemote.findBankMasterTrans(id, userInfo);

		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			bankRptTransBeanRemote = null;
		}
		if (bankReportTransDetail == null) {
			return new BankReportTranSO();
		}
		return bankReportTranSO.toBankReportTranSO(bankReportTransDetail);
	}
}
