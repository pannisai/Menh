package com.dtac.billerweb.serviceImpl;

import org.apache.log4j.Logger;

import mfs.biller.ejb.interfaces.GWBankGWMapBeanRemote;
import mfs.biller.ejb.interfaces.GWBankInbMapBeanRemote;
import mfs.biller.persistence.bean.ObjMapGWxml;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.BankGWServiceMapService;

public class BankGWServiceMapServiceImpl extends BaseService implements BankGWServiceMapService {
	private Logger log = Logger.getLogger(BankGWServiceMapServiceImpl.class);

	@Override
	public ObjMapGWxml getByID(Integer id, UserInfoBean userInfo) {
		// TODO Auto-generated method stub
		GWBankGWMapBeanRemote gwBankGWMapBeanRemote = null;
		ObjMapGWxml mapGWxml = new ObjMapGWxml();
		try {
			gwBankGWMapBeanRemote = (GWBankGWMapBeanRemote) EJBInitialContext.lookup(GWBankGWMapBeanRemote.JNDI_WEBLOGIC);
			mapGWxml = gwBankGWMapBeanRemote.findGWBankGWMap(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwBankGWMapBeanRemote = null;
		}
		if (mapGWxml == null) {
			return new ObjMapGWxml();
		}
		return mapGWxml;
	}

	@Override
	public Integer save(ObjMapGWxml mapGwXml, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		GWBankGWMapBeanRemote gwBankGWMapBeanRemote = null;
		Integer oid;
		try {
			gwBankGWMapBeanRemote = (GWBankGWMapBeanRemote) EJBInitialContext.lookup(GWBankGWMapBeanRemote.JNDI_WEBLOGIC);
			oid = gwBankGWMapBeanRemote.insertGWBankGWMap(mapGwXml, userInfo);

		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwBankGWMapBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void update(ObjMapGWxml mapGwXml, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		GWBankGWMapBeanRemote gwBankGWMapBeanRemote = null;
		try {
			gwBankGWMapBeanRemote = (GWBankGWMapBeanRemote) EJBInitialContext.lookup(GWBankGWMapBeanRemote.JNDI_WEBLOGIC);
			gwBankGWMapBeanRemote.updateGWBankGWMap(mapGwXml, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwBankGWMapBeanRemote = null;
		}
	}

}
