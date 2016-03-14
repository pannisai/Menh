package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.BillerCatalogBeanRemote;
import mfs.biller.persistence.bean.BillerCategory;
import mfs.biller.persistence.bean.BillerCategoryParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.constants.Constants;
import mfs.exception.IsExistException;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.BillerCategorySO;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.service.BillerCategoryService;
import com.dtac.billerweb.util.AppUtil;

public class BillerCategoryServiceImpl extends BaseService implements BillerCategoryService {
	private Logger log = Logger.getLogger(BillerCategoryServiceImpl.class);

	@Override
	public List<BillerCategorySO> searchBillerCategory(BillerCategoryParam condition, int currentPage, int pageSize, UserInfoBean userInfo) {
		log.info("start searchBillerCategory");
		List<BillerCategorySO> billerCategorySOs = null;
		List<BillerCategory> billerCategorys = null;
		try {
			billerCategorys = getBillerCategory(condition, currentPage, pageSize, userInfo);
			log.info("billerCategorys size::" + billerCategorys.size());

			billerCategorySOs = convertBillerCategoryToBillerCategorySO(billerCategorys);

			log.info("BillerCategorySOs size::" + billerCategorySOs.size());
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerCategorys = null;
		}

		return billerCategorySOs;
	}

	@Override
	public int getBillerCategoryRowCount(BillerCategoryParam condition, UserInfoBean userInfo) {
		BillerCatalogBeanRemote billerCatalogBeanRemote = null;
		Integer rownum = 0;
		try {
			billerCatalogBeanRemote = (BillerCatalogBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerCatalogBean);
			try {
				// rownum = //billerCatalogBeanRemote.
			} catch (NullPointerException npe) {

			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			/*--Garbage-*/
			billerCatalogBeanRemote = null;
			if (rownum == null) {
				rownum = 0;
			}
		}
		return rownum;
	}

	private List<BillerCategory> getBillerCategory(BillerCategoryParam condition, int currentPage, int pageSize, UserInfoBean userInfo) {
		BillerCatalogBeanRemote billerCatalogBeanRemote = null;
		List<BillerCategory> billerCategorys = null;
		try {
			billerCatalogBeanRemote = (BillerCatalogBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerCatalogBean);
			// condition.setPAGE_NO(currentPage);
			// condition.setPAGE_SIZE(pageSize);
			billerCategorys = billerCatalogBeanRemote.searchBillerCatalog(condition, userInfo);
			if (AppUtil.isEmpty(billerCategorys)) {
				billerCategorys = new ArrayList<BillerCategory>();
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			/*--Garbage-*/
			billerCatalogBeanRemote = null;
		}
		return billerCategorys;
	}

	private List<BillerCategorySO> convertBillerCategoryToBillerCategorySO(List<BillerCategory> billerCategorys) throws Exception {

		List<BillerCategorySO> billerCategorySOs = new ArrayList<BillerCategorySO>();
		try {
			if (AppUtil.isEmpty(billerCategorys)) {
				return billerCategorySOs;
			}
			BillerCategorySO billerCategorySO = null;
			for (BillerCategory billerCategory : billerCategorys) {
				billerCategorySO = new BillerCategorySO();
				billerCategorySO = billerCategorySO.toBillerCategorySO(billerCategory);
				billerCategorySOs.add(billerCategorySO);
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerCategorys = null;
		}
		return billerCategorySOs;

	}

	@Override
	public BillerCategory getByID(Integer id, UserInfoBean userInfo) {
		BillerCatalogBeanRemote billerCatalogBeanRemote = null;
		BillerCategory billerCategory = new BillerCategory();
		try {
			billerCatalogBeanRemote = (BillerCatalogBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerCatalogBean);
			billerCategory = billerCatalogBeanRemote.findBillerCatalog(id, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerCatalogBeanRemote = null;
		}
		if (billerCategory == null) {
			return new BillerCategory();
		}
		return billerCategory;
	}

	@Override
	public boolean isExistBillMenuSeq(Integer id, Integer menuSeq, UserInfoBean userInfo) {
		BillerCatalogBeanRemote billerCatalogBeanRemote = null;
		boolean result = false;
		try {
			billerCatalogBeanRemote = (BillerCatalogBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerCatalogBean);
			result = billerCatalogBeanRemote.isExistBillMenuSeq(id, menuSeq, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerCatalogBeanRemote = null;
		}
		return result;
	}

	@Override
	public boolean isExistBllrCatgName(Integer id, String billerCateName, UserInfoBean userInfo) {
		BillerCatalogBeanRemote billerCatalogBeanRemote = null;
		boolean result = false;
		try {
			billerCatalogBeanRemote = (BillerCatalogBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerCatalogBean);
			result = billerCatalogBeanRemote.isExistBllrCatgName(id, billerCateName, userInfo);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerCatalogBeanRemote = null;
		}
		return result;
	}

	@Override
	public Integer save(BillerCategory billerCategory, UserInfoBean userInfo) throws IsExistException {
		BillerCatalogBeanRemote billerCatalogBeanRemote = null;
		Integer oid=-1;
		try {
			billerCatalogBeanRemote = (BillerCatalogBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerCatalogBean);
			billerCategory.setBLLR_CATG_ID(0);
			oid=billerCatalogBeanRemote.insertBillerCatalog(billerCategory, userInfo);		
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerCatalogBeanRemote = null;
		}
		if(oid==null){
			return -1;
		}
		return oid;
	}

	@Override
	public void update(BillerCategory billerCategory, UserInfoBean userInfo) throws IsExistException{
		BillerCatalogBeanRemote billerCatalogBeanRemote = null;
		try {
			billerCatalogBeanRemote = (BillerCatalogBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerCatalogBean);
			billerCatalogBeanRemote.updateBillerCatalog(billerCategory, userInfo);
		} catch (IsExistException iex) {
			throw iex;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			billerCatalogBeanRemote = null;
		}
	}

}
