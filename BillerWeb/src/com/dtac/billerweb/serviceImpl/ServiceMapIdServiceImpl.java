package com.dtac.billerweb.serviceImpl;

import mfs.biller.ejb.interfaces.GWServiceMapBeanRemote;
import mfs.biller.persistence.bean.ObjMapGWxml;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.constants.Constants;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.ServiceMapIdService;

public class ServiceMapIdServiceImpl extends BaseService implements ServiceMapIdService {
	private Logger log = Logger.getLogger(ServiceMapIdServiceImpl.class);

	@Override
	public ObjMapGWxml getByID(Integer id, UserInfoBean userInfo) {
		GWServiceMapBeanRemote gwServiceMapBeanRemote = null;
		ObjMapGWxml mapGWxml = new ObjMapGWxml();
		try {
			gwServiceMapBeanRemote = (GWServiceMapBeanRemote) EJBInitialContext.lookup(Constants.JNDI.gWServiceMapBean);
			mapGWxml = gwServiceMapBeanRemote.findGWServiceMap(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwServiceMapBeanRemote = null;
		}
		if (mapGWxml == null) {
			return new ObjMapGWxml();
		}
		return mapGWxml;
	}

	@Override
	public Integer save(ObjMapGWxml mapGwXml, UserInfoBean userInfo) throws IsExistException {
		GWServiceMapBeanRemote gwServiceMapBeanRemote = null;
		Integer oid;
		try {
			gwServiceMapBeanRemote = (GWServiceMapBeanRemote) EJBInitialContext.lookup(Constants.JNDI.gWServiceMapBean);
			oid = gwServiceMapBeanRemote.insertGWServiceMap(mapGwXml, userInfo);

		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwServiceMapBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;

	}

	@Override
	public void update(ObjMapGWxml mapGwXml, UserInfoBean userInfo) throws IsExistException {
		GWServiceMapBeanRemote gwServiceMapBeanRemote = null;
		try {
			gwServiceMapBeanRemote = (GWServiceMapBeanRemote) EJBInitialContext.lookup(Constants.JNDI.gWServiceMapBean);
			gwServiceMapBeanRemote.updateGWServiceMap(mapGwXml, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwServiceMapBeanRemote = null;
		}
	}
}
