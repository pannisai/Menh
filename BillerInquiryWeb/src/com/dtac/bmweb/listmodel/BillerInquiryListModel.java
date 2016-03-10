package com.dtac.bmweb.listmodel;

import java.util.List;

import mfs.biller.persistence.bean.GWMasterTransParam;
import mfs.biller.persistence.bean.UserInfoBean;

import org.apache.log4j.Logger;
import org.zkoss.zul.ListModelList;

import com.dtac.bmweb.common.AbstractPagingListModel;
import com.dtac.bmweb.data.BillerInquirySO;
import com.dtac.bmweb.exception.BillerManageWebException;
import com.dtac.bmweb.factory.BillerwebServiceFactory;
import com.dtac.bmweb.service.BillerInquiryService;

public class BillerInquiryListModel extends AbstractPagingListModel<BillerInquirySO> {
	private static Logger log = Logger.getLogger(BillerInquiryListModel.class);
	private GWMasterTransParam criteria;

	public BillerInquiryListModel(int currentPage, int pageSize, UserInfoBean userInfo) throws Exception {
		super(currentPage, pageSize, userInfo);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int getTotalSize(UserInfoBean userInfo) throws Exception {
		// TODO Auto-generated method stub
		BillerInquiryService billerInqService = null;
		int rownum = 0;
		try {
			billerInqService = BillerwebServiceFactory.getBillerInquiryService();
			if (criteria == null) {
				criteria = new GWMasterTransParam();
			}
			rownum = billerInqService.getBillerInquiryRowCount(criteria);
		} catch (Exception ex) {
			throw new BillerManageWebException(ex);
		} finally {
			/*--Garbage--*/
			billerInqService = null;
		}

		return rownum;
	}

	@Override
	protected ListModelList<BillerInquirySO> getPageData(int currentPage, int pageSize, UserInfoBean userInfo) throws Exception {
		// TODO Auto-generated method stub
		log.info("getPageData[currentPage:" + currentPage + ",pageSize:" + pageSize);
		BillerInquiryService billerInqService = null;
		List<BillerInquirySO> billerInquirySOs = null;
		ListModelList<BillerInquirySO> listModel = new ListModelList<BillerInquirySO>();
		try {
			billerInqService = BillerwebServiceFactory.getBillerInquiryService();

			if (criteria == null) {
				criteria = new GWMasterTransParam();
			}
			log.debug("currentPage::" + currentPage);
			log.debug("pageSize::" + pageSize);
			billerInquirySOs = billerInqService.searchBillerInquiry(criteria, currentPage, pageSize);

			log.info("billerInquirySOs Size::" + billerInquirySOs.size());
			listModel = new ListModelList<BillerInquirySO>(billerInquirySOs);

			/*--Garbage--*/
		} catch (Exception ex) {
			throw new BillerManageWebException(ex);
		} finally {
			billerInqService = null;
			billerInquirySOs = null;
		}
		return listModel;
	}

	public GWMasterTransParam getCriteria() {
		return criteria;
	}

	public void setCriteria(GWMasterTransParam criteria) {
		this.criteria = criteria;
	}


}
