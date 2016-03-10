package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mfs.biller.ejb.interfaces.RptTransBeanRemote;
import mfs.biller.persistence.bean.GWMasterTrans;
import mfs.biller.persistence.bean.GWMasterTransParam;

import org.apache.log4j.Logger;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.data.BillerInquiryDO;
import com.dtac.billerweb.service.BillerInquiryService;
import com.dtac.billerweb.util.AppUtil;

public class BillerInquiryServiceImpl extends BaseService implements BillerInquiryService {
	private Logger log = Logger.getLogger(BillerInquiryServiceImpl.class);

	@Override
	public List<BillerInquiryDO> searchBillerInquiry(GWMasterTransParam condition, int currentPage, int pageSize) throws Exception {
		log.info("start searchBillerInquiry");
		Collection<GWMasterTrans> gwMasterTrans = searchMasterTrans(condition, currentPage, pageSize);
		log.info("gwMasterTrans size::" + gwMasterTrans.size());

		List<BillerInquiryDO> billerInquiryForms = convertGwMasterTransToBillerInquiryForms(gwMasterTrans);

		log.info("billerInquiryForms size::" + billerInquiryForms.size());
		return billerInquiryForms;
	}

	private Collection<GWMasterTrans> searchMasterTrans(GWMasterTransParam condition, int currentPage, int pageSize) throws Exception {
		RptTransBeanRemote rptTransBeanRemote = (RptTransBeanRemote) EJBInitialContext.lookup(RptTransBeanRemote.JNDI_WEBLOGIC);
		condition.setPAGE_NO(currentPage);
		condition.setPAGE_SIZE(pageSize);
		Collection<GWMasterTrans> gwMasterTrans = rptTransBeanRemote.getMasterTransAll(condition);

		if (AppUtil.isEmpty(gwMasterTrans)) {
			gwMasterTrans = new ArrayList<GWMasterTrans>();
		}
		/*--Garbage-*/
		rptTransBeanRemote = null;
		return gwMasterTrans;
	}

	private List<BillerInquiryDO> convertGwMasterTransToBillerInquiryForms(Collection<GWMasterTrans> gwMasterTrans) throws Exception {
		List<BillerInquiryDO> billerInquiryForms = new ArrayList<BillerInquiryDO>();
		if (AppUtil.isEmpty(gwMasterTrans)) {
			return billerInquiryForms;
		}
		BillerInquiryDO billerInquiryForm = null;
		for (GWMasterTrans gwMasterTran : gwMasterTrans) {
			billerInquiryForm = new BillerInquiryDO();
			billerInquiryForm = billerInquiryForm.toBillerInquiryForm(gwMasterTran);
			billerInquiryForms.add(billerInquiryForm);
		}
		if (AppUtil.isEmpty(billerInquiryForms)) {
			return new ArrayList<BillerInquiryDO>();
		} else {
			return billerInquiryForms;
		}
	}

	@Override
	public int getBillerInquiryRowCount(GWMasterTransParam condition) throws Exception {
		RptTransBeanRemote rptTransBeanRemote = null;
		Integer rownum = 0;
		try {
			rptTransBeanRemote = (RptTransBeanRemote) EJBInitialContext.lookup(RptTransBeanRemote.JNDI_WEBLOGIC);
			try {
				rownum = rptTransBeanRemote.countRowAll(condition).intValue();
			} catch (NullPointerException npe) {

			}
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

}
