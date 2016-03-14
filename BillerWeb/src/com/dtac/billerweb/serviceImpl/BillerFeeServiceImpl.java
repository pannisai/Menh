package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.BillerFeeBeanRemote;
import mfs.biller.persistence.bean.BillerFee;
import mfs.biller.persistence.bean.BillerFeeParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.constants.Constants;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.BillerFeeSO;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.BillerFeeService;
import com.dtac.billerweb.util.AppUtil;

public class BillerFeeServiceImpl extends BaseService implements BillerFeeService {
	private Logger log = Logger.getLogger(BillerFeeServiceImpl.class);

	@Override
	public List<BillerFeeSO> searchBillerFee(BillerFeeParam condition, int currentPage, int pageSize, UserInfoBean userInfo) {
		log.info("start searchBillerFee");
		List<BillerFeeSO> billerFeeSO = null;
		List<BillerFee> billerFee = null;
		try {
			billerFee = getBillerFee(condition, currentPage, pageSize, userInfo);
			log.info("billerFee size::" + billerFee.size());

			billerFeeSO = convertBillerFeeToBillerFeeSO(billerFee);

			log.info("BillerFeeSO size::" + billerFeeSO.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerFee = null;
		}

		return billerFeeSO;
	}
	
	private List<BillerFee> getBillerFee(BillerFeeParam condition, int currentPage, int pageSize, UserInfoBean userInfo) {
		BillerFeeBeanRemote billerFeeBeanRemote = null;
		List<BillerFee> billerFee = null;
		try {
			billerFeeBeanRemote = (BillerFeeBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerFeeBean);
			//condition.setPAGE_NO(currentPage);
			//condition.setPAGE_SIZE(pageSize);
			billerFee = billerFeeBeanRemote.searchBillerFee(condition, userInfo);
			if (AppUtil.isEmpty(billerFee)) {
				billerFee = new ArrayList<BillerFee>();
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			/*--Garbage-*/
			billerFeeBeanRemote = null;
		}
		return billerFee;
	}

	@Override
	public int getBillerFeeRowCount(BillerFeeParam condition, UserInfoBean userInfo) {
		BillerFeeBeanRemote billerFeeBeanRemote = null;
		Integer rownum = 0;
		try {
			billerFeeBeanRemote = (BillerFeeBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerFeeBean);
			try {
				// rownum = //billerFeeBeanRemote.
			} catch (NullPointerException npe) {

			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			/*--Garbage-*/
			billerFeeBeanRemote = null;
			if (rownum == null) {
				rownum = 0;
			}
		}
		return rownum;
	}

	private List<BillerFeeSO> convertBillerFeeToBillerFeeSO(List<BillerFee> billerFeeList) throws Exception {

		List<BillerFeeSO> billerFeeSOList = new ArrayList<BillerFeeSO>();
		try {
			if (AppUtil.isEmpty(billerFeeList)) {
				return billerFeeSOList;
			}
			BillerFeeSO billerFeeSO = null;
			for (BillerFee billerFee : billerFeeList) {
				billerFeeSO = new BillerFeeSO();
				billerFeeSO = billerFeeSO.toBillerFeeSO(billerFee);
				billerFeeSOList.add(billerFeeSO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerFeeList = null;
		}
		return billerFeeSOList;
	}

	@Override
	public BillerFee getByID(Integer id, UserInfoBean userInfo) {
		BillerFeeBeanRemote billerFeeBeanRemote = null;
		BillerFee billerFee = new BillerFee();
		try {
			billerFeeBeanRemote = (BillerFeeBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerFeeBean);
			billerFee = billerFeeBeanRemote.findBillerFee(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerFeeBeanRemote = null;
		}
		if (billerFee == null) {
			return new BillerFee();
		}
		return billerFee;
	}
	
	@Override
	public BillerFee getByServiceID(Integer id, UserInfoBean userInfo) {
		BillerFeeBeanRemote billerFeeBeanRemote = null;
		BillerFee billerFee = new BillerFee();
		try {
			billerFeeBeanRemote = (BillerFeeBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerFeeBean);
			billerFee = billerFeeBeanRemote.findBillerFeeExpired(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerFeeBeanRemote = null;
		}
		if (billerFee == null) {
			return new BillerFee();
		}
		return billerFee;
	}

	@Override
	public Integer save(BillerFee billerFee, UserInfoBean userInfo) throws IsExistException {
		BillerFeeBeanRemote billerFeeBeanRemote = null;
		Integer oid=-1;
		try {
			billerFeeBeanRemote = (BillerFeeBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerFeeBean);
			oid=billerFeeBeanRemote.insertBillerFee(billerFee, userInfo);		
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerFeeBeanRemote = null;
		}
		if(oid==null){
			return -1;
		}
		return oid;
	}

	@Override
	public void update(BillerFee billerFee, UserInfoBean userInfo) throws IsExistException{
		BillerFeeBeanRemote billerFeeBeanRemote = null;
		try {
			billerFeeBeanRemote = (BillerFeeBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerFeeBean);
			billerFeeBeanRemote.updateBillerFee(billerFee, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerFeeBeanRemote = null;
		}
	}

	@Override
	public boolean isExistBillerFee(BillerFeeParam condition, UserInfoBean userInfo) {
		BillerFeeBeanRemote billerFeeBeanRemote = null;
		boolean result = false;
		try {
			billerFeeBeanRemote = (BillerFeeBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerFeeBean);
			result = billerFeeBeanRemote.isExistBillerFee(condition, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerFeeBeanRemote = null;
		}
		return result;
	}
}
