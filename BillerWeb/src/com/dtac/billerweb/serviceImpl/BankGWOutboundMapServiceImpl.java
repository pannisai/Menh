package com.dtac.billerweb.serviceImpl;

import org.apache.log4j.Logger;

import mfs.biller.ejb.interfaces.GWBankInbMapBeanRemote;
import mfs.biller.ejb.interfaces.GWBankOutbMapBeanRemote;
import mfs.biller.persistence.bean.ObjMapGWxml;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.BankGWOutboundMapService;

public class BankGWOutboundMapServiceImpl extends BaseService implements BankGWOutboundMapService{
	private Logger log = Logger.getLogger(BankGWOutboundMapServiceImpl.class);
	@Override
	public ObjMapGWxml getByID(Integer id, UserInfoBean userInfo) {
		// TODO Auto-generated method stub
		GWBankOutbMapBeanRemote gwBankOutbMapBeanRemote = null;
		ObjMapGWxml mapGWxml = new ObjMapGWxml();
		try {
			gwBankOutbMapBeanRemote = (GWBankOutbMapBeanRemote) EJBInitialContext.lookup(GWBankOutbMapBeanRemote.JNDI_WEBLOGIC);
			mapGWxml = gwBankOutbMapBeanRemote.findGWBankOutboundMap(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwBankOutbMapBeanRemote = null;
		}
		if (mapGWxml == null) {
			return new ObjMapGWxml();
		}
		return mapGWxml;
	}

	@Override
	public Integer save(ObjMapGWxml mapGwXml, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		GWBankOutbMapBeanRemote gwBankOutbMapBeanRemote = null;
		Integer oid;
		try {
			gwBankOutbMapBeanRemote = (GWBankOutbMapBeanRemote) EJBInitialContext.lookup(GWBankOutbMapBeanRemote.JNDI_WEBLOGIC);
			oid = gwBankOutbMapBeanRemote.insertGWBankOutbMap(mapGwXml, userInfo);

		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwBankOutbMapBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void update(ObjMapGWxml mapGwXml, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		GWBankOutbMapBeanRemote gwBankOutbMapBeanRemote = null;
		try {
			gwBankOutbMapBeanRemote = (GWBankOutbMapBeanRemote) EJBInitialContext.lookup(GWBankOutbMapBeanRemote.JNDI_WEBLOGIC);
			gwBankOutbMapBeanRemote.updateGWBankOutbMap(mapGwXml, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwBankOutbMapBeanRemote = null;
		}
	}

}
