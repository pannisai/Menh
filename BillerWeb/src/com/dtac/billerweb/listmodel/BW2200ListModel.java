package com.dtac.billerweb.listmodel;

import java.util.List;

import mfs.biller.persistence.bean.ServiceGatewayResultParam;
import mfs.biller.persistence.bean.UserInfoBean;

import org.apache.log4j.Logger;
import org.zkoss.zul.ListModelList;

import com.dtac.billerweb.common.AbstractPagingListModel;
import com.dtac.billerweb.data.ServiceGatewaySO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.service.ServiceGatewayService;

public class BW2200ListModel extends AbstractPagingListModel<ServiceGatewaySO> {
	private static Logger log = Logger.getLogger(BW2200ListModel.class);
	ServiceGatewayResultParam criteria;
	

	public BW2200ListModel(int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		super(currentPage, pageSize,userInfo);
	}

	@Override
	protected int getTotalSize(UserInfoBean userInfo) throws Exception {
		ServiceGatewayService serviceGatewayService = null;
		int rownum = 0;
		try {
			serviceGatewayService = BillerwebServiceFactory.getServiceGatewayService();
			if (criteria == null) {
				criteria = new ServiceGatewayResultParam();
			}
			rownum = serviceGatewayService.getServiceGatewayRowCount(criteria,userInfo);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			/*--Garbage--*/
			serviceGatewayService = null;
		}

		return rownum;
	}

	@Override
	protected ListModelList<ServiceGatewaySO> getPageData(int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		log.info("getPageData[currentPage:" + currentPage + ",pageSize:" + pageSize);
		ServiceGatewayService serviceGatewayService = null;
		List<ServiceGatewaySO> serviceGatewaySOs = null;
		ListModelList<ServiceGatewaySO> listModel = new ListModelList<ServiceGatewaySO>();
		try {
			serviceGatewayService = BillerwebServiceFactory.getServiceGatewayService();

			if (criteria == null) {
				criteria = new ServiceGatewayResultParam();
			}
			log.debug("currentPage::" + currentPage);
			log.debug("pageSize::" + pageSize);
			serviceGatewaySOs = serviceGatewayService.searchServiceGateway(criteria, currentPage, pageSize,userInfo);
			log.info("serviceGatewaySOs Size::" + serviceGatewaySOs.size());
			listModel = new ListModelList<ServiceGatewaySO>(serviceGatewaySOs);

			/*--Garbage--*/
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			serviceGatewayService = null;
			serviceGatewaySOs = null;
		}
		return listModel;
	}

	public ServiceGatewayResultParam getCriteria() {
		return criteria;
	}

	public void setCriteria(ServiceGatewayResultParam criteria) {
		this.criteria = criteria;
	}

}
