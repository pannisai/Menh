package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.OutboundGatewayRemote;
import mfs.biller.ejb.interfaces.ServiceGatewayRemote;
import mfs.biller.persistence.bean.GWOutbound;
import mfs.biller.persistence.bean.GWOutboundDtail;
import mfs.biller.persistence.bean.GWService;
import mfs.biller.persistence.bean.OutboundGatewayParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.OutboundGatewaySO;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.OutboundGatewayService;
import com.dtac.billerweb.util.AppUtil;

public class OutboundGatewayServiceImpl extends BaseService implements OutboundGatewayService {
	private Logger log = Logger.getLogger(OutboundGatewayServiceImpl.class);

	@Override
	public List<OutboundGatewaySO> searchOutboundGateway(OutboundGatewayParam condition, int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		log.info("start searchOutboundGateway");
		List<OutboundGatewaySO> outboundGatewaySO = null;
		List<GWOutboundDtail> gwOutbounds = null;
		try {
			gwOutbounds = getOutboundGatewayResult(condition, currentPage, pageSize,userInfo);
			log.info("gwOutbounds size::" + gwOutbounds.size());
			outboundGatewaySO = convertToOutboundGatewaySO(gwOutbounds);
			log.info("outboundGatewaySO size::" + outboundGatewaySO.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwOutbounds = null;
		}
		return outboundGatewaySO;
	}

	@Override
	public int getOutboundGatewayRowCount(OutboundGatewayParam condition,UserInfoBean userInfo) throws Exception {
		OutboundGatewayRemote outboundGatewayRemote = null;
		Integer rownum = 0;
		try {
			outboundGatewayRemote = (OutboundGatewayRemote) EJBInitialContext.lookup(OutboundGatewayRemote.JNDI_WEBLOGIC);
			try {
				rownum = outboundGatewayRemote.countRowAll(condition,userInfo).intValue();

			} catch (NullPointerException npe) {

			}
		} catch (Exception ex) {
			throw new ServiceException("Get Outbound Gateway Row Count Fail", ex);
		} finally {
			/*--Garbage-*/
			outboundGatewayRemote = null;
			if (rownum == null) {
				rownum = 0;
			}
		}
		return rownum;
	}

	private List<GWOutboundDtail> getOutboundGatewayResult(OutboundGatewayParam condition, int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		OutboundGatewayRemote outboundGatewayRemote = null;
		List<GWOutboundDtail> gwOutbounds = null;
		try {
			outboundGatewayRemote = (OutboundGatewayRemote) EJBInitialContext.lookup(OutboundGatewayRemote.JNDI_WEBLOGIC);
			condition.setPAGE_NO(currentPage);
			condition.setPAGE_SIZE(pageSize);
			gwOutbounds = outboundGatewayRemote.getOutboundGateway(condition,userInfo);
			if (AppUtil.isEmpty(gwOutbounds)) {
				gwOutbounds = new ArrayList<GWOutboundDtail>();
			}
		} catch (Exception ex) {
			throw new ServiceException("Get GWOutboundDtail fail", ex);
		} finally {
			/*--Garbage-*/
			outboundGatewayRemote = null;
		}

		return gwOutbounds;
	}

	private List<OutboundGatewaySO> convertToOutboundGatewaySO(List<GWOutboundDtail> outboundGatewayResults) throws Exception {
		log.info("convertOutboundGatewayResultToOutboundGatewaySO");
		List<OutboundGatewaySO> outboundGatewaySOs = new ArrayList<OutboundGatewaySO>();
		try {
			if (AppUtil.isEmpty(outboundGatewayResults)) {
				return outboundGatewaySOs;
			}
			OutboundGatewaySO outboundGatewaySO = null;
			for (GWOutboundDtail gwOutbound : outboundGatewayResults) {
				outboundGatewaySO = new OutboundGatewaySO();
				outboundGatewaySO = outboundGatewaySO.toOutboundGatewaySO(gwOutbound);
				outboundGatewaySOs.add(outboundGatewaySO);
			}
		} catch (Exception ex) {
			throw new ServiceException("Convert to Outbound gateway Result Fail" + ex);
		} finally {
			outboundGatewayResults = null;
		}
		return outboundGatewaySOs;
	}

	@Override
	public GWOutbound getByID(Integer id, UserInfoBean userInfo) {
		OutboundGatewayRemote outboundGatewayRemote = null;
		GWOutbound outboundGateway = new GWOutbound();
		try {
			outboundGatewayRemote = (OutboundGatewayRemote) EJBInitialContext.lookup(OutboundGatewayRemote.JNDI_WEBLOGIC);
			outboundGateway = outboundGatewayRemote.searchOutboundGateway(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			outboundGatewayRemote = null;
		}
		if (outboundGateway == null) {
			return new GWOutbound();
		}
		return outboundGateway;
	}

	@Override
	public Integer save(GWOutbound outboundGateway, UserInfoBean userInfo) throws IsExistException {
		OutboundGatewayRemote outboundGatewayRemote = null;
		Integer oid = -1;
		try {
			outboundGatewayRemote = (OutboundGatewayRemote) EJBInitialContext.lookup(OutboundGatewayRemote.JNDI_WEBLOGIC);
			oid = outboundGatewayRemote.insertOutboundGateway(outboundGateway, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			outboundGatewayRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void update(GWOutbound outboundGateway, UserInfoBean userInfo) throws IsExistException {
		OutboundGatewayRemote outboundGatewayRemote = null;
		try {
			outboundGatewayRemote = (OutboundGatewayRemote) EJBInitialContext.lookup(OutboundGatewayRemote.JNDI_WEBLOGIC);
			outboundGatewayRemote.updateOutboundGateway(outboundGateway, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			outboundGatewayRemote = null;
		}
	}

}
