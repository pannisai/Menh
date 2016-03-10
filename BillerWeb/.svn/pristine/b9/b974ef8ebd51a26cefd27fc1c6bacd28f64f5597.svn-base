package com.dtac.billerweb.listmodel;

import java.util.List;

import mfs.biller.persistence.bean.UserInfoBean;
import mfs.biller.persistence.bean.getInboundGatewayResultParam;

import org.apache.log4j.Logger;
import org.zkoss.zul.ListModelList;

import com.dtac.billerweb.common.AbstractPagingListModel;
import com.dtac.billerweb.data.InboundGatewaySO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.service.InboundGatewayService;

public class InboundGatewayListModel extends AbstractPagingListModel<InboundGatewaySO> {
	private static Logger log = Logger.getLogger(InboundGatewayListModel.class);
	private getInboundGatewayResultParam condition;

	public InboundGatewayListModel(int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		super(currentPage, pageSize, userInfo);
	}

	@Override
	public int getTotalSize(UserInfoBean userInfo) throws Exception {
		InboundGatewayService inboundGatewayService = null;
		int rownum = 0;
		try {
			inboundGatewayService = BillerwebServiceFactory.getInboundGatewayService();
			if (condition == null) {
				condition = new getInboundGatewayResultParam();
			}
			rownum = inboundGatewayService.getInboundGatewayRowCount(condition,userInfo);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new BillerWebException(ex);
		} finally {
			/*--Garbage--*/
			inboundGatewayService = null;
		}

		return rownum;
	}

	@Override
	protected ListModelList<InboundGatewaySO> getPageData(int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		log.info("getPageData[currentPage:" + currentPage + ",pageSize:"
				+ pageSize);
		InboundGatewayService inboundGatewayService = null;
		List<InboundGatewaySO> inboundGatewaySOs = null;
		ListModelList<InboundGatewaySO> listModel = new ListModelList<InboundGatewaySO>();
		try {
			inboundGatewayService = BillerwebServiceFactory.getInboundGatewayService();

			if (condition == null) {
				condition = new getInboundGatewayResultParam();
			}
			log.debug("currentPage::" + currentPage);
			log.debug("pageSize::" + pageSize);
			inboundGatewaySOs = inboundGatewayService.searchInboundGateway(condition, currentPage, pageSize,userInfo);

			log.info("Size::" + inboundGatewaySOs.size());
			listModel = new ListModelList<InboundGatewaySO>(inboundGatewaySOs);

			/*--Garbage--*/
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new BillerWebException(ex);
		} finally {
			inboundGatewayService = null;
			//inboundGatewaySOs = null;
		}
		return listModel;
	}

	public getInboundGatewayResultParam getCondition() {
		return condition;
	}

	public void setCondition(getInboundGatewayResultParam condition) {
		this.condition = condition;
	}

}
