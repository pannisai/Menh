package com.dtac.billerweb.listmodel;

import java.util.List;

import mfs.biller.persistence.bean.BankReportTransParam;
import mfs.biller.persistence.bean.UserInfoBean;

import org.apache.log4j.Logger;
import org.zkoss.zul.ListModelList;

import com.dtac.billerweb.common.AbstractPagingListModel;
import com.dtac.billerweb.data.BankReportTranSO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.service.BankReportTranService;

public class BW6000ListModel extends AbstractPagingListModel<BankReportTranSO> {
	private static Logger log = Logger.getLogger(BW6000ListModel.class);
	private BankReportTransParam criteria;

	public BW6000ListModel(int currentPage, int pageSize, UserInfoBean userInfo) throws Exception {
		super(currentPage, pageSize, userInfo);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int getTotalSize(UserInfoBean userInfo) throws Exception {
		// TODO Auto-generated method stub
		BankReportTranService bankReportTranService = null;
		int rownum = 0;
		try {
			bankReportTranService = BillerwebServiceFactory.getBankReportTranService();
			if (criteria == null) {
				criteria = new BankReportTransParam();
			}
			rownum = bankReportTranService.getBankReportTranRowCount(criteria, userInfo);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			/*--Garbage--*/
			bankReportTranService = null;
		}

		return rownum;
	}

	@Override
	protected ListModelList<BankReportTranSO> getPageData(int currentPage, int pageSize, UserInfoBean userInfo) throws Exception {
		// TODO Auto-generated method stub
		log.info("getPageData[currentPage:" + currentPage + ",pageSize:" + pageSize);
		BankReportTranService bankReportTranService = null;
		List<BankReportTranSO> bankReportTranSOs = null;
		ListModelList<BankReportTranSO> listModel = new ListModelList<BankReportTranSO>();
		try {
			bankReportTranService = BillerwebServiceFactory.getBankReportTranService();

			if (criteria == null) {
				criteria = new BankReportTransParam();
			}
			log.debug("currentPage::" + currentPage);
			log.debug("pageSize::" + pageSize);
			bankReportTranSOs = bankReportTranService.searchBankReportTran(criteria, currentPage, pageSize, userInfo);

			log.info("bankReportTranSOs Size::" + bankReportTranSOs.size());
			listModel = new ListModelList<BankReportTranSO>(bankReportTranSOs);

			/*--Garbage--*/
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			bankReportTranService = null;
			bankReportTranSOs = null;
		}
		return listModel;
	}

	public BankReportTransParam getCriteria() {
		return criteria;
	}

	public void setCriteria(BankReportTransParam criteria) {
		this.criteria = criteria;
	}

}
