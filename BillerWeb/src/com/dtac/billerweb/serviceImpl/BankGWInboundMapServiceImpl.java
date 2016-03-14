package com.dtac.billerweb.serviceImpl;

import mfs.biller.ejb.interfaces.GWBankInbMapBeanRemote;
import mfs.biller.persistence.bean.ObjMapGWxml;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.constants.Constants;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.BankGWInboundMapService;

public class BankGWInboundMapServiceImpl extends BaseService implements BankGWInboundMapService{
	private Logger log = Logger.getLogger(BankGWInboundMapServiceImpl.class);
	@Override
	public ObjMapGWxml getByID(Integer id, UserInfoBean userInfo) {
		GWBankInbMapBeanRemote gwBankInbMapBeanRemote = null;
		ObjMapGWxml mapGWxml = new ObjMapGWxml();
		try {
			gwBankInbMapBeanRemote = (GWBankInbMapBeanRemote) EJBInitialContext.lookup(Constants.JNDI.gWBankInbMapBean);
			mapGWxml = gwBankInbMapBeanRemote.findGWBankInbMap(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwBankInbMapBeanRemote = null;
		}
		if (mapGWxml == null) {
			return new ObjMapGWxml();
		}
		return mapGWxml;
	}

	@Override
	public Integer save(ObjMapGWxml mapGwXml, UserInfoBean userInfo) throws IsExistException {
		GWBankInbMapBeanRemote gwBankInbMapBeanRemote = null;
		Integer oid;
		try {
			gwBankInbMapBeanRemote = (GWBankInbMapBeanRemote) EJBInitialContext.lookup(Constants.JNDI.gWBankInbMapBean);
			oid = gwBankInbMapBeanRemote.insertGWBankInbMap(mapGwXml, userInfo);

		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwBankInbMapBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void update(ObjMapGWxml mapGwXml, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		GWBankInbMapBeanRemote gwBankInbMapBeanRemote = null;
		try {
			gwBankInbMapBeanRemote = (GWBankInbMapBeanRemote) EJBInitialContext.lookup(Constants.JNDI.gWBankInbMapBean);
			gwBankInbMapBeanRemote.updateGWBankInbMap(mapGwXml, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwBankInbMapBeanRemote = null;
		}
	}

}
