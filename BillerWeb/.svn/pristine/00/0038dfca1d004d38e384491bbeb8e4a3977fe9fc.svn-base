package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.BillerChannelBeanRemote;
import mfs.biller.persistence.bean.BillerChannel;
import mfs.biller.persistence.bean.BillerChannelParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.BillerChannelSO;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.BillerChannelService;
import com.dtac.billerweb.util.AppUtil;

public class BillerChannelServiceImpl extends BaseService implements BillerChannelService {
	private Logger log = Logger.getLogger(BillerChannelServiceImpl.class);

	@Override
	public List<BillerChannelSO> searchBillerChannel(BillerChannelParam condition, int currentPage, int pageSize, UserInfoBean userInfo) {
		log.info("start searchBillerChannel");
		List<BillerChannelSO> billerChannelSOs = null;
		List<BillerChannel> billerChannels = null;
		try {
			billerChannels = getBillerChannels(condition, currentPage, pageSize, userInfo);
			log.info("billerChannels size::" + billerChannels.size());

			billerChannelSOs = toBillerChannelSOList(billerChannels);
			log.info("billerChannelSOs size::" + billerChannelSOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerChannels = null;
		}

		return billerChannelSOs;
	}

	private List<BillerChannel> getBillerChannels(BillerChannelParam condition, int currentPage, int pageSize, UserInfoBean userInfo) {
		BillerChannelBeanRemote billerChannelBeanRemote = null;
		List<BillerChannel> billerChannels = null;
		try {
			billerChannelBeanRemote = (BillerChannelBeanRemote) EJBInitialContext.lookup(BillerChannelBeanRemote.JNDI_WEBLOGIC);
			// condition.setPAGE_NO(currentPage);
			// condition.setPAGE_SIZE(pageSize);
			billerChannels = billerChannelBeanRemote.searchBillerChannel(condition, userInfo);
			if (AppUtil.isEmpty(billerChannels)) {
				billerChannels = new ArrayList<BillerChannel>();
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			/*--Garbage-*/
			billerChannelBeanRemote = null;
		}
		return billerChannels;
	}

	private List<BillerChannelSO> toBillerChannelSOList(List<BillerChannel> billerChannels) throws Exception {

		List<BillerChannelSO> billerChannelSOs = new ArrayList<BillerChannelSO>();
		try {
			if (AppUtil.isEmpty(billerChannels)) {
				return billerChannelSOs;
			}
			BillerChannelSO billerChannelSO = null;
			for (BillerChannel billerChannel : billerChannels) {
				billerChannelSO = new BillerChannelSO();
				billerChannelSO = billerChannelSO.toBillerChannelSO(billerChannel);
				billerChannelSOs.add(billerChannelSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerChannels = null;
		}
		return billerChannelSOs;

	}

	@Override
	public BillerChannel getByID(Integer id, UserInfoBean userInfo) {
		BillerChannelBeanRemote billerChannelBeanRemote = null;
		BillerChannel billerChannel = new BillerChannel();
		try {
			billerChannelBeanRemote = (BillerChannelBeanRemote) EJBInitialContext.lookup(BillerChannelBeanRemote.JNDI_WEBLOGIC);
			billerChannel = billerChannelBeanRemote.findBillerChannel(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerChannelBeanRemote = null;
		}
		if (billerChannel == null) {
			return new BillerChannel();
		}
		return billerChannel;
	}

	@Override
	public Integer save(BillerChannel billerChannel, UserInfoBean userInfo) throws IsExistException {
		BillerChannelBeanRemote billerChannelBeanRemote = null;
		Integer oid = -1;
		try {
			billerChannelBeanRemote = (BillerChannelBeanRemote) EJBInitialContext.lookup(BillerChannelBeanRemote.JNDI_WEBLOGIC);
			billerChannel.setBLLR_CHNL_ID(0);
			oid = billerChannelBeanRemote.insertBillerChannel(billerChannel, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerChannelBeanRemote = null;
		}
		if (oid == null) {
			return -1;
		}
		return oid;

	}

	@Override
	public void update(BillerChannel billerChannel, UserInfoBean userInfo) throws IsExistException {
		BillerChannelBeanRemote billerChannelBeanRemote = null;		
		try {
			billerChannelBeanRemote = (BillerChannelBeanRemote) EJBInitialContext.lookup(BillerChannelBeanRemote.JNDI_WEBLOGIC);	
			billerChannelBeanRemote.updateBillerChannel(billerChannel, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerChannelBeanRemote = null;
		}		
	}
	
	@Override
	public boolean isExistBillerChannelName(int BLLR_CHNL_ID, String BLLR_CHNL_NAME, UserInfoBean userInfo) {
		BillerChannelBeanRemote objRemote = null;
		boolean result = false;
		try {
			objRemote = (BillerChannelBeanRemote) EJBInitialContext.lookup(BillerChannelBeanRemote.JNDI_WEBLOGIC);
			result = objRemote.isExistBllrChnlName(BLLR_CHNL_ID, BLLR_CHNL_NAME, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			objRemote = null;
		}
		return result;
	}
	
	@Override
	public boolean isExistBillerChannelCode(int BLLR_CHNL_ID, String BLLR_CHNL_CODE, UserInfoBean userInfo) {
		BillerChannelBeanRemote objRemote = null;
		boolean result = false;
		try {
			objRemote = (BillerChannelBeanRemote) EJBInitialContext.lookup(BillerChannelBeanRemote.JNDI_WEBLOGIC);
			result = objRemote.isExistBllrChnlCode(BLLR_CHNL_ID, BLLR_CHNL_CODE, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			objRemote = null;
		}
		return result;
	}
}
