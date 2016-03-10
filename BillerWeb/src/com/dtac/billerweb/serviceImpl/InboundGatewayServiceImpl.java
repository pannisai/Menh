package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.InboundGatewayResultRemote;
import mfs.biller.persistence.bean.GWInboundPK;
import mfs.biller.persistence.bean.OBJGW_INBOUND;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.biller.persistence.bean.getInboundGatewayResult;
import mfs.biller.persistence.bean.getInboundGatewayResultParam;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.InboundGatewaySO;
import com.dtac.billerweb.data.InboundGatewaySOPK;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.InboundGatewayService;
import com.dtac.billerweb.util.AppUtil;

public class InboundGatewayServiceImpl extends BaseService implements InboundGatewayService {
	private Logger log = Logger.getLogger(InboundGatewayServiceImpl.class);

	@Override
	public List<InboundGatewaySO> searchInboundGateway(getInboundGatewayResultParam condition, int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		log.info("start searchInboundGateway");
		List<InboundGatewaySO> inboundGatewaySOs = null;
		List<getInboundGatewayResult> inboundGatewayResults = null;
		try {
			inboundGatewayResults = getInboundGatewayResult(condition, currentPage, pageSize,userInfo);
			log.info("inboundGatewayResults size::" + inboundGatewayResults.size());

			inboundGatewaySOs = convertInboundGatewayResultToInboundGatewaySO(inboundGatewayResults);

			log.info("inboundGatewaySOs size::" + inboundGatewaySOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			inboundGatewayResults = null;
		}

		return inboundGatewaySOs;
	}

	@Override
	public int getInboundGatewayRowCount(getInboundGatewayResultParam condition,UserInfoBean userInfo) throws Exception {
		InboundGatewayResultRemote inboundGatewayResultRemote = null;
		Integer rownum = 0;
		try {
			inboundGatewayResultRemote = (InboundGatewayResultRemote) EJBInitialContext.lookup(InboundGatewayResultRemote.JNDI_WEBLOGIC);
			try {
				rownum = inboundGatewayResultRemote.countRowAll(condition,userInfo).intValue();

			} catch (NullPointerException npe) {

			}
		} catch (Exception ex) {
			throw new ServiceException("Get Inbound Gateway Row Count Fail", ex);
		} finally {
			/*--Garbage-*/
			inboundGatewayResultRemote = null;
			if (rownum == null) {
				rownum = 0;
			}
		}
		return rownum;
	}

	private List<getInboundGatewayResult> getInboundGatewayResult(getInboundGatewayResultParam condition, int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		InboundGatewayResultRemote inboundGatewayResultRemote = null;
		List<getInboundGatewayResult> getInboundGatewayResults = null;
		try {
			inboundGatewayResultRemote = (InboundGatewayResultRemote) EJBInitialContext.lookup(InboundGatewayResultRemote.JNDI_WEBLOGIC);
			condition.setPAGE_NO(currentPage);
			condition.setPAGE_SIZE(pageSize);
			getInboundGatewayResults = inboundGatewayResultRemote.getInboundGatewayResult(condition,userInfo);
			if (AppUtil.isEmpty(getInboundGatewayResults)) {
				getInboundGatewayResults = new ArrayList<getInboundGatewayResult>();
			}
		} catch (Exception ex) {
			throw new ServiceException("Get InboundGateway fail", ex);
		} finally {
			/*--Garbage-*/
			inboundGatewayResultRemote = null;
		}

		return getInboundGatewayResults;
	}

	private List<InboundGatewaySO> convertInboundGatewayResultToInboundGatewaySO(List<getInboundGatewayResult> inboundGatewayResults) throws Exception {
		log.info("convertInboundGatewayResultToInboundGatewaySO");
		List<InboundGatewaySO> inboundGatewaySOs = new ArrayList<InboundGatewaySO>();
		try {
			if (AppUtil.isEmpty(inboundGatewayResults)) {
				return inboundGatewaySOs;
			}
			InboundGatewaySO inboundGatewaySO = null;
			for (getInboundGatewayResult inboundGatewayResult : inboundGatewayResults) {
				inboundGatewaySO = new InboundGatewaySO();
				inboundGatewaySO = inboundGatewaySO.toInboundGatewaySO(inboundGatewayResult);
				inboundGatewaySOs.add(inboundGatewaySO);
			}
		} catch (Exception ex) {
			throw new ServiceException("Convert to Inbound gateway Result Fail", ex);
		} finally {
			inboundGatewayResults = null;
		}
		return inboundGatewaySOs;

	}

	@Override
	public OBJGW_INBOUND getByID(InboundGatewaySOPK id, UserInfoBean userInfo) {
		InboundGatewayResultRemote inboundGatewayResultRemote = null;
		OBJGW_INBOUND objGW_INBOUND = new OBJGW_INBOUND();
		try {
			inboundGatewayResultRemote = (InboundGatewayResultRemote) EJBInitialContext.lookup(InboundGatewayResultRemote.JNDI_WEBLOGIC);
			getInboundGatewayResultParam inboundGatewayResultParam = new getInboundGatewayResultParam();
			inboundGatewayResultParam.setINBN_SRVC_ID(Integer.parseInt(id.getInbn_SRVC_ID()));
			inboundGatewayResultParam.setSRCE_SRVC_ID(id.getSrce_SRVC_ID());
			inboundGatewayResultParam.setDEST_SRVC_ID(id.getDest_SRVC_ID());
			log.debug("inboundGatewayResultParam.getINBN_SRVC_ID:"+inboundGatewayResultParam.getINBN_SRVC_ID());
			objGW_INBOUND = inboundGatewayResultRemote.getInboundGatewayResultED(inboundGatewayResultParam,userInfo);
			log.debug("inboundGatewayResultParam.getINBN_SRVC_ID:"+inboundGatewayResultParam.getINBN_SRVC_ID());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			inboundGatewayResultRemote = null;
		}
		if (objGW_INBOUND == null) {
			return new OBJGW_INBOUND();
		}
		return objGW_INBOUND;
	}

	@Override
	public InboundGatewaySOPK save(OBJGW_INBOUND objGW_INBOUND, UserInfoBean userInfo) throws IsExistException {
		InboundGatewayResultRemote inboundGatewayResultRemote = null;
		InboundGatewaySOPK oid = new InboundGatewaySOPK();
		GWInboundPK pk = null;
		try {
			inboundGatewayResultRemote = (InboundGatewayResultRemote) EJBInitialContext.lookup(InboundGatewayResultRemote.JNDI_WEBLOGIC);

			pk = inboundGatewayResultRemote.insertGW_INBOUND(objGW_INBOUND, userInfo);
			if (pk == null) {
				return oid;
			}
			oid.setInbn_SRVC_ID(AppUtil.toString(pk.getINBN_SRVC_ID()));
			oid.setDest_SRVC_ID(pk.getDEST_SRVC_ID());
			oid.setSrce_SRVC_ID(pk.getSRCE_SRVC_ID());
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			inboundGatewayResultRemote = null;
			pk = null;
		}
		return oid;
	}

	@Override
	public void update(OBJGW_INBOUND objGW_INBOUND, UserInfoBean userInfo) throws IsExistException {
		InboundGatewayResultRemote inboundGatewayResultRemote = null;
		try {
			inboundGatewayResultRemote = (InboundGatewayResultRemote) EJBInitialContext.lookup(InboundGatewayResultRemote.JNDI_WEBLOGIC);
			inboundGatewayResultRemote.updateGW_INBOUND(objGW_INBOUND, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			inboundGatewayResultRemote = null;
		}
	}

}
