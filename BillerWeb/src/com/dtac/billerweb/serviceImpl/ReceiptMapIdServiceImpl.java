package com.dtac.billerweb.serviceImpl;

import mfs.biller.ejb.interfaces.GWXmlDataSrcRemote;
import mfs.biller.persistence.bean.GWXmlDataSrcBean;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.constants.Constants;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.ReceiptMapIdService;

public class ReceiptMapIdServiceImpl extends BaseService implements ReceiptMapIdService {
	private Logger log = Logger.getLogger(ReceiptMapIdServiceImpl.class);

	@Override
	public GWXmlDataSrcBean getByID(Integer id, UserInfoBean userInfo) {
		GWXmlDataSrcRemote gwXmlDataSrcRemote = null;
		GWXmlDataSrcBean mapGWxml = new GWXmlDataSrcBean();
		try {
			gwXmlDataSrcRemote = (GWXmlDataSrcRemote) EJBInitialContext.lookup(Constants.JNDI.gWXmlDataSrc);
			mapGWxml = gwXmlDataSrcRemote.findGWXmlDataSrc(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwXmlDataSrcRemote = null;
		}
		if (mapGWxml == null) {
			return new GWXmlDataSrcBean();
		}
		return mapGWxml;
	}

	@Override
	public Integer save(GWXmlDataSrcBean mapGwXml, UserInfoBean userInfo) throws IsExistException {
		GWXmlDataSrcRemote gwXmlDataSrcRemote = null;
		Integer oid;
		try {
			gwXmlDataSrcRemote = (GWXmlDataSrcRemote) EJBInitialContext.lookup(Constants.JNDI.gWXmlDataSrc);
			oid = gwXmlDataSrcRemote.insertGWXmlDataSrc(mapGwXml, userInfo);

		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwXmlDataSrcRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;
	}

	@Override
	public void update(GWXmlDataSrcBean mapGwXml, UserInfoBean userInfo) throws IsExistException {
		// TODO Auto-generated method stub
		GWXmlDataSrcRemote gwXmlDataSrcRemote = null;
		try {
			gwXmlDataSrcRemote = (GWXmlDataSrcRemote) EJBInitialContext.lookup(Constants.JNDI.gWXmlDataSrc);
			gwXmlDataSrcRemote.updateGWXmlDataSrc(mapGwXml, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			gwXmlDataSrcRemote = null;
		}
	}

}
