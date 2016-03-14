package com.dtac.billerweb.serviceImpl;

import mfs.biller.ejb.interfaces.GWOutboundMapBeanRemote;
import mfs.biller.persistence.bean.ObjMapGWxml;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.constants.Constants;
import mfs.exception.IsExistException;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.OutboundMapIdService;

public class OutboundMapIdServiceImpl extends BaseService implements OutboundMapIdService {

	@Override
	public ObjMapGWxml getByID(Integer id, UserInfoBean userInfo) {
		GWOutboundMapBeanRemote gwOutboundMapBeanRemote = null;
		ObjMapGWxml mapGWxml = new ObjMapGWxml();
		try {
			gwOutboundMapBeanRemote = (GWOutboundMapBeanRemote) EJBInitialContext.lookup(Constants.JNDI.gWOutboundMapBean);
			mapGWxml = gwOutboundMapBeanRemote.findGWOutboundMap(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwOutboundMapBeanRemote = null;
		}
		if (mapGWxml == null) {
			return new ObjMapGWxml();
		}
		return mapGWxml;
	}

	@Override
	public Integer save(ObjMapGWxml mapGwXml, UserInfoBean userInfo) throws IsExistException {
		GWOutboundMapBeanRemote gwOutboundMapBeanRemote = null;
		Integer oid;
		try {
			gwOutboundMapBeanRemote = (GWOutboundMapBeanRemote) EJBInitialContext.lookup(Constants.JNDI.gWOutboundMapBean);
			oid = gwOutboundMapBeanRemote.insertGWOutboundMap(mapGwXml, userInfo);

		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwOutboundMapBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void update(ObjMapGWxml mapGwXml, UserInfoBean userInfo) throws IsExistException {
		GWOutboundMapBeanRemote gwOutboundMapBeanRemote = null;
		try {
			gwOutboundMapBeanRemote = (GWOutboundMapBeanRemote) EJBInitialContext.lookup(Constants.JNDI.gWOutboundMapBean);
			gwOutboundMapBeanRemote.updateGWOutboundMap(mapGwXml, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwOutboundMapBeanRemote = null;
		}
	}

}
