package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.RptTransBeanRemote;
import mfs.biller.persistence.bean.ReportTransDetail;
import mfs.biller.persistence.bean.ReportTransParam;
import mfs.biller.persistence.bean.BillerPymtCode;
import mfs.biller.persistence.bean.BillerFdmCode;
import mfs.biller.persistence.bean.UserInfoBean;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.listmodel.selectbox.BillerPymtCodeListModel;
import com.dtac.billerweb.listmodel.selectbox.BillerFdmCodeListModel;
import com.dtac.billerweb.service.ReportTransService;
import com.dtac.billerweb.util.AppUtil;

public class ReportTransServiceImpl extends BaseService implements ReportTransService{
	
	private Logger log = Logger.getLogger(ReportTransServiceImpl.class);
	
	@Override
	public BillerFdmCodeListModel getBillerFdmCodeListModel(UserInfoBean user) throws Exception {
		BillerFdmCodeListModel billerFdmCodeListModel = new BillerFdmCodeListModel(new ArrayList<BillerFdmCode>());
		RptTransBeanRemote objRemote = null;
		try {
			log.info("Get BillerFdmCode");
			
			List<BillerFdmCode> list = new ArrayList<BillerFdmCode>();
			
			objRemote = (RptTransBeanRemote) EJBInitialContext.lookup(RptTransBeanRemote.JNDI_WEBLOGIC);
			if (objRemote != null) {
				list = objRemote.getBillerFdmCodeAll(user);
			}
			if (AppUtil.isNull(list)) {
				list = new ArrayList<BillerFdmCode>();
			}
			log.info(" BillerFdmCodeList Size::" + list.size());
			
			billerFdmCodeListModel = new BillerFdmCodeListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			objRemote = null;
		}
		return billerFdmCodeListModel;
	}
	
	@Override
	public BillerPymtCodeListModel getBillerPymtCodeListModel(UserInfoBean user) throws Exception {
		BillerPymtCodeListModel billerPymtCodeListModel = new BillerPymtCodeListModel(new ArrayList<BillerPymtCode>());
		RptTransBeanRemote objRemote = null;
		try {
			log.info("Get BillerPymtCode");
			
			List<BillerPymtCode> list = new ArrayList<BillerPymtCode>();
			
			objRemote = (RptTransBeanRemote) EJBInitialContext.lookup(RptTransBeanRemote.JNDI_WEBLOGIC);
			if (objRemote != null) {
				list = objRemote.getBillerPymtCodeAll(user);
			}
			if (AppUtil.isNull(list)) {
				list = new ArrayList<BillerPymtCode>();
			}
			log.info(" BillerPymtCodeList Size::" + list.size());
			
			billerPymtCodeListModel = new BillerPymtCodeListModel(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			objRemote = null;
		}
		return billerPymtCodeListModel;
	}
	
	public List<ReportTransDetail> searchReportTrans(ReportTransParam condition, int currentPage, int pageSize, UserInfoBean userInfo){
		log.info("start searchReportTransaction");
		RptTransBeanRemote objRemote = null;
		List<ReportTransDetail> reportTransDetail = null;
		try {
			condition.setPAGE_NO(currentPage);
			condition.setPAGE_SIZE(pageSize);
			
			objRemote = (RptTransBeanRemote) EJBInitialContext.lookup(RptTransBeanRemote.JNDI_WEBLOGIC);
			reportTransDetail = objRemote.searchReportTrans(condition, userInfo);
			
			if (AppUtil.isEmpty(reportTransDetail)) {
				reportTransDetail = new ArrayList<ReportTransDetail>();
			}
			
			log.info("ReportTransDetail List size::" + reportTransDetail.size());
			
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			objRemote = null;
		}
		return reportTransDetail;
	}
	
	public int countRowReportTrans(ReportTransParam condition, UserInfoBean userInfo){
		log.info("start countRowReportTransaction");
		RptTransBeanRemote objRemote = null;
		int rownum = 0;
		try {
			objRemote = (RptTransBeanRemote) EJBInitialContext.lookup(RptTransBeanRemote.JNDI_WEBLOGIC);
			rownum = objRemote.countRowReportTrans(condition, userInfo);
			
			log.info("ReportTrans Total Row::" + rownum);
			
		} catch (Exception ex) {
			throw new ServiceException("Get Report Transaction Row Count Fail", ex);
		} finally {
			objRemote = null;
		}
		return rownum;
	}
}
