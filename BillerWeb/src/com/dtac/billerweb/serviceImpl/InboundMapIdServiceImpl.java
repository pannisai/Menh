package com.dtac.billerweb.serviceImpl;

import mfs.biller.ejb.interfaces.GWInboundMapBeanRemote;
import mfs.biller.persistence.bean.ObjMapGWxml;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.InboundMapIdService;

public class InboundMapIdServiceImpl extends BaseService implements InboundMapIdService {
	private Logger log = Logger.getLogger(InboundMapIdServiceImpl.class);

	@Override
	public ObjMapGWxml getByID(Integer id, UserInfoBean userInfo) {
		GWInboundMapBeanRemote gwInboundMapBeanRemote = null;
		ObjMapGWxml mapGWxml = new ObjMapGWxml();
		try {
			gwInboundMapBeanRemote = (GWInboundMapBeanRemote) EJBInitialContext.lookup(GWInboundMapBeanRemote.JNDI_WEBLOGIC);
			mapGWxml = gwInboundMapBeanRemote.findGWInboundMap(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwInboundMapBeanRemote = null;
		}
		if (mapGWxml == null) {
			return new ObjMapGWxml();
		}
		return mapGWxml;
	}

	@Override
	public Integer save(ObjMapGWxml mapGwXml, UserInfoBean userInfo) throws IsExistException {
		GWInboundMapBeanRemote gwInboundMapBeanRemote = null;
		Integer oid;
		try {
			gwInboundMapBeanRemote = (GWInboundMapBeanRemote) EJBInitialContext.lookup(GWInboundMapBeanRemote.JNDI_WEBLOGIC);
			oid = gwInboundMapBeanRemote.insertGWInboundMap(mapGwXml, userInfo);

		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwInboundMapBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void update(ObjMapGWxml mapGwXml, UserInfoBean userInfo) throws IsExistException {
		GWInboundMapBeanRemote gwInboundMapBeanRemote = null;
		try {
			gwInboundMapBeanRemote = (GWInboundMapBeanRemote) EJBInitialContext.lookup(GWInboundMapBeanRemote.JNDI_WEBLOGIC);
			gwInboundMapBeanRemote.updateGWInboundMap(mapGwXml, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwInboundMapBeanRemote = null;
		}
	}

}
