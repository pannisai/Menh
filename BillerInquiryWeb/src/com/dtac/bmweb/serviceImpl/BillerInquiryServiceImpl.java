package com.dtac.bmweb.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mfs.biller.ejb.interfaces.BillerServiceBeanRemote;
import mfs.biller.ejb.interfaces.RptTransBeanRemote;
import mfs.biller.persistence.bean.BillerService;
import mfs.biller.persistence.bean.GWMasterTrans;
import mfs.biller.persistence.bean.GWMasterTransParam;
import mfs.biller.persistence.bean.UserInfoBean;
import mfs.constants.Constants;
import mfs.exception.NotFoundDataException;

import org.apache.log4j.Logger;

import com.dtac.bmweb.common.BaseService;
import com.dtac.bmweb.common.EJBInitialContext;
import com.dtac.bmweb.data.BillerInquirySO;
import com.dtac.bmweb.service.BillerInquiryService;
import com.dtac.bmweb.util.AppUtil;

public class BillerInquiryServiceImpl extends BaseService implements BillerInquiryService {
	private Logger log = Logger.getLogger(BillerInquiryServiceImpl.class);

	@Override
	public List<BillerInquirySO> searchBillerInquiry(GWMasterTransParam condition, int currentPage, int pageSize) throws Exception {
		log.info("start searchBillerInquiry");
		Collection<GWMasterTrans> gwMasterTrans = searchMasterTrans(condition, currentPage, pageSize);
		log.info("gwMasterTrans size::" + gwMasterTrans.size());

		List<BillerInquirySO> billerInquiryForms = convertGwMasterTransToBillerInquiryForms(gwMasterTrans,condition);

		log.info("billerInquiryForms size::" + billerInquiryForms.size());
		return billerInquiryForms;
	}

	private Collection<GWMasterTrans> searchMasterTrans(GWMasterTransParam condition, int currentPage, int pageSize) throws Exception {
		RptTransBeanRemote rptTransBeanRemote = (RptTransBeanRemote) EJBInitialContext.lookup(Constants.JNDI.rptTransBean);
		condition.setPAGE_NO(currentPage);
		condition.setPAGE_SIZE(pageSize);
		Collection<GWMasterTrans> gwMasterTrans = null;
		try {
			gwMasterTrans = rptTransBeanRemote.getMasterTransAll(condition);
		} catch (NotFoundDataException nfde) {

		}
		if (AppUtil.isEmpty(gwMasterTrans)) {
			gwMasterTrans = new ArrayList<GWMasterTrans>();
		}
		/*--Garbage-*/
		rptTransBeanRemote = null;
		return gwMasterTrans;
	}

	private List<BillerInquirySO> convertGwMasterTransToBillerInquiryForms(Collection<GWMasterTrans> gwMasterTrans,GWMasterTransParam condition) throws Exception {
		List<BillerInquirySO> billerInquiryForms = new ArrayList<BillerInquirySO>();
		if (AppUtil.isEmpty(gwMasterTrans)) {
			return billerInquiryForms;
		}
		BillerInquirySO billerInquiryForm = null;
		for (GWMasterTrans gwMasterTran : gwMasterTrans) {
			billerInquiryForm = new BillerInquirySO();
			billerInquiryForm = billerInquiryForm.toBillerInquiryForm(gwMasterTran);
			addChannelService(billerInquiryForm, gwMasterTran);
			//setMobileCreditAmount(billerInquiryForm, gwMasterTran);
			//addReversalFlag(billerInquiryForm, gwMasterTran,condition);
			billerInquiryForms.add(billerInquiryForm);
		}
		if (AppUtil.isEmpty(billerInquiryForms)) {
			return new ArrayList<BillerInquirySO>();
		} else {
			return billerInquiryForms;
		}
	}
	
	private void addChannelService(BillerInquirySO billerInquiryForm,
			GWMasterTrans gwMasterTran) {
		if ("PSBCC".equals(gwMasterTran.getTRNS_ID().substring(0, 5))) {
			billerInquiryForm.setChannelService("Mobile Credit Card");
			billerInquiryForm.setCrtdDttm(gwMasterTran.getTRNS_DTTM());
		} else {
			billerInquiryForm.setChannelService("Jaew");
		}
	}
	
//	private void addReversalFlag(BillerInquirySO billerInquiryForm,
//			GWMasterTrans gwMasterTran, GWMasterTransParam condition) {
//		if ("Y".equals(gwMasterTran.getREV_STATUS())) {
//			billerInquiryForm.setPaidAmount(billerInquiryForm
//					.getPaidAmount().negate());
//		}
//	}
	
	private void setMobileCreditAmount(BillerInquirySO billerInquiryForm,
			GWMasterTrans gwMasterTran) {
		if ("MOB".equals(gwMasterTran.getCHNL_CODE())) {
			billerInquiryForm.setPaidAmount(gwMasterTran.getTRNS_TOTL_AMNT());
		}
	}

	@Override
	public int getBillerInquiryRowCount(GWMasterTransParam condition) throws Exception {
		RptTransBeanRemote rptTransBeanRemote = null;
		Integer rownum = 0;
		try {
			rptTransBeanRemote = (RptTransBeanRemote) EJBInitialContext.lookup(Constants.JNDI.rptTransBean);
			try {
				rownum = rptTransBeanRemote.countRowAll(condition).intValue();
			}

			catch (NullPointerException npe) {

			}
		} catch (NotFoundDataException nfde) {

		} catch (Exception ex) {
			throw new RuntimeException("Get BillerInquiry Row Count Fail", ex);
		} finally {
			/*--Garbage-*/
			rptTransBeanRemote = null;
			if (rownum == null) {
				rownum = 0;
			}
		}

		return rownum;
	}

	@Override
	public BillerService getBillerServiceByID(Integer id, UserInfoBean userInfo) throws NotFoundDataException {
		BillerServiceBeanRemote billerServiceBeanRemote = null;
		BillerService billerService = new BillerService();
		try {
			billerServiceBeanRemote = (BillerServiceBeanRemote) EJBInitialContext.lookup(Constants.JNDI.billerServiceBean);
			billerService = billerServiceBeanRemote.findBillerService(id, userInfo);
		} catch (NotFoundDataException nfde) {
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			billerServiceBeanRemote = null;
		}
		if (billerService == null) {
			return new BillerService();
		}
		return billerService;

	}

	@Override
	public BigDecimal getBillerInquiryTotalAmount(GWMasterTransParam condition) throws Exception {
		RptTransBeanRemote rptTransBeanRemote = null;
		BigDecimal totalAmount = new BigDecimal("0");
		try {
			rptTransBeanRemote = (RptTransBeanRemote) EJBInitialContext.lookup(Constants.JNDI.rptTransBean);
			try {
				totalAmount = rptTransBeanRemote.getTotalAmount(condition);

			}catch (NullPointerException npe) {

			}
		} catch (NotFoundDataException nfde) {

		} catch (Exception ex) {
			throw new RuntimeException("Get BillerInquiry get TotalAmount Fail", ex);
		} finally {
			/*--Garbage-*/
			rptTransBeanRemote = null;
			if (totalAmount == null) {
				totalAmount = new BigDecimal("0");
			}
		}

		return totalAmount;
	}

}
