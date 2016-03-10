package com.dtac.billerweb.listmodel;

import java.util.List;

import mfs.biller.persistence.bean.GWMasterTransParam;
import mfs.biller.persistence.bean.UserInfoBean;

import org.apache.log4j.Logger;
import org.zkoss.zul.ListModelList;

import com.dtac.billerweb.common.AbstractPagingListModel;
import com.dtac.billerweb.data.BillerInquiryDO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.service.BillerInquiryService;

public class BillerInquiryListModel extends AbstractPagingListModel<BillerInquiryDO> {
	private static Logger log = Logger.getLogger(BillerInquiryListModel.class);
	private GWMasterTransParam condition;

	public BillerInquiryListModel(int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		super(currentPage, pageSize,userInfo);
	}

	@Override
	public int getTotalSize(UserInfoBean userInfo) throws Exception {
		BillerInquiryService billerInqService = null;
		int rownum = 0;
		try {
			billerInqService = BillerwebServiceFactory.getBillerInquiryService();
			if (condition == null) {
				condition = new GWMasterTransParam();
			}
			rownum = billerInqService.getBillerInquiryRowCount(condition);
		} catch (Exception ex) {
//			log.error(ex.getMessage());
			throw new BillerWebException(ex);
		} finally {
			/*--Garbage--*/
			billerInqService = null;
		}

		return rownum;
	}

	@Override
	protected ListModelList<BillerInquiryDO> getPageData(int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		BillerInquiryService billerInqService = null;
		ListModelList<BillerInquiryDO> listModel = new ListModelList<BillerInquiryDO>();
		try {
			billerInqService = BillerwebServiceFactory.getBillerInquiryService();
			List<BillerInquiryDO> billerInquiryForms = null;
			if (condition == null) {
				condition = new GWMasterTransParam();
			}
			log.debug("currentPage::" + currentPage);
			log.debug("pageSize::" + pageSize);
			billerInquiryForms = billerInqService.searchBillerInquiry(condition, currentPage, pageSize);

			log.debug("Size::" + billerInquiryForms.size());
			listModel = new ListModelList<BillerInquiryDO>(billerInquiryForms);

			/*--Garbage--*/
		} catch (Exception ex) {
//			log.error(ex.getMessage());
			throw new BillerWebException(ex);
		} finally {
			billerInqService = null;
		}
		// TestPagingData testData = new TestPagingData();
		// List<BillerInquiryForm> alist =
		// testData.getBillerDataTest(currentPage,
		// pageSize);
		// ListModelList<BillerInquiryForm> listModel = new
		// ListModelList<BillerInquiryForm>();
		// for (BillerInquiryForm obj : alist) {
		// listModel.add(obj);
		// }
		return listModel;
	}

	public GWMasterTransParam getCondition() {
		return condition;
	}

	public void setCondition(GWMasterTransParam condition) {
		this.condition = condition;
	}

}
