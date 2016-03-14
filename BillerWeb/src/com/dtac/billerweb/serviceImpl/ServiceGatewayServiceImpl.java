package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.ServiceGatewayRemote;
import mfs.biller.persistence.bean.GWService;
import mfs.biller.persistence.bean.ServiceGatewayResult;
import mfs.biller.persistence.bean.ServiceGatewayResultParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.constants.Constants;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.ServiceGatewaySO;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.ServiceGatewayService;
import com.dtac.billerweb.util.AppUtil;

public class ServiceGatewayServiceImpl extends BaseService implements ServiceGatewayService {
	private Logger log = Logger.getLogger(ServiceGatewayServiceImpl.class);

	@Override
	public List<ServiceGatewaySO> searchServiceGateway(ServiceGatewayResultParam criteria, int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		log.info("start searchServiceGateway");
		List<ServiceGatewaySO> serviceGatewaySOs = null;
		List<ServiceGatewayResult> serviceGatewayResults = null;
		try {
			serviceGatewayResults = getServiceGatewayResult(criteria, currentPage, pageSize,userInfo);
			log.info("serviceGatewayResults size::" + serviceGatewayResults.size());
			serviceGatewaySOs = convertServiceGatewayResultToServiceGatewaySO(serviceGatewayResults);
			log.info("serviceGatewaySOs size::" + serviceGatewaySOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			serviceGatewayResults = null;
		}
		return serviceGatewaySOs;
	}

	@Override
	public int getServiceGatewayRowCount(ServiceGatewayResultParam criteria,UserInfoBean userInfo) throws Exception {
		ServiceGatewayRemote serviceGatewayRemote = null;
		Integer rownum = 0;
		try {
			serviceGatewayRemote = (ServiceGatewayRemote) EJBInitialContext.lookup(Constants.JNDI.serviceGateway);
			try {
				rownum = serviceGatewayRemote.countRowAll(criteria,userInfo).intValue();

			} catch (NullPointerException npe) {

			}
		} catch (Exception ex) {
			throw new ServiceException("Get Service Gateway Row Count Fail", ex);
		} finally {
			/*--Garbage-*/
			serviceGatewayRemote = null;
			if (rownum == null) {
				rownum = 0;
			}
		}
		return rownum;
	}

	private List<ServiceGatewayResult> getServiceGatewayResult(ServiceGatewayResultParam criteria, int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		ServiceGatewayRemote serviceGatewayRemote = null;
		List<ServiceGatewayResult> serviceGatewayResults = null;
		try {
			serviceGatewayRemote = (ServiceGatewayRemote) EJBInitialContext.lookup(Constants.JNDI.serviceGateway);
			criteria.setPAGE_NO(currentPage);
			criteria.setPAGE_SIZE(pageSize);
			serviceGatewayResults = serviceGatewayRemote.getServiceGatewayResult(criteria,userInfo);
			if (AppUtil.isEmpty(serviceGatewayResults)) {
				serviceGatewayResults = new ArrayList<ServiceGatewayResult>();
			}
		} catch (Exception ex) {
			throw new ServiceException("Get ServiceGatewayResult fail", ex);
		} finally {
			/*--Garbage-*/
			serviceGatewayRemote = null;
		}

		return serviceGatewayResults;
	}

	private List<ServiceGatewaySO> convertServiceGatewayResultToServiceGatewaySO(List<ServiceGatewayResult> serviceGatewayResults) throws Exception {
		log.info("convertServiceGatewayResultToServiceGatewaySO");
		List<ServiceGatewaySO> serviceGatewaySOs = new ArrayList<ServiceGatewaySO>();
		try {
			if (AppUtil.isEmpty(serviceGatewayResults)) {
				return serviceGatewaySOs;
			}
			ServiceGatewaySO serviceGatewaySO = null;
			for (ServiceGatewayResult serviceGatewayResult : serviceGatewayResults) {
				serviceGatewaySO = new ServiceGatewaySO();
				serviceGatewaySO = serviceGatewaySO.toServiceGatewaySO(serviceGatewayResult);
				serviceGatewaySOs.add(serviceGatewaySO);
			}
		} catch (Exception ex) {
			throw new ServiceException("Convert to Service gateway Result Fail" + ex);
		} finally {
			serviceGatewayResults = null;
		}
		return serviceGatewaySOs;
	}

	@Override
	public GWService getByID(Integer id, UserInfoBean userInfo) {
		ServiceGatewayRemote serviceGatewayRemote = null;
		GWService serviceGateway = new GWService();
		try {
			serviceGatewayRemote = (ServiceGatewayRemote) EJBInitialContext.lookup(Constants.JNDI.serviceGateway);
			serviceGateway = serviceGatewayRemote.searchServiceGateway(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			serviceGatewayRemote = null;
		}
		if (serviceGateway == null) {
			return new GWService();
		}
		return serviceGateway;
	}

	@Override
	public Integer save(GWService serviceGateway, UserInfoBean userInfo) throws IsExistException {
		ServiceGatewayRemote serviceGatewayRemote = null;
		Integer oid = -1;
		try {
			serviceGatewayRemote = (ServiceGatewayRemote) EJBInitialContext.lookup(Constants.JNDI.serviceGateway);
			serviceGateway.setGW_SRVC_ID(0);
			oid = serviceGatewayRemote.insertServiceGateway(serviceGateway, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			serviceGatewayRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void update(GWService serviceGateway, UserInfoBean userInfo) throws IsExistException {
		ServiceGatewayRemote serviceGatewayRemote = null;
		try {
			serviceGatewayRemote = (ServiceGatewayRemote) EJBInitialContext.lookup(Constants.JNDI.serviceGateway);
			serviceGatewayRemote.updateServiceGateway(serviceGateway, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			serviceGatewayRemote = null;
		}
	}
}
