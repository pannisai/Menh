package com.dtac.billerweb.listmodel;

import java.util.List;

import mfs.biller.persistence.bean.OutboundGatewayParam;
import mfs.biller.persistence.bean.UserInfoBean;

import org.apache.log4j.Logger;
import org.zkoss.zul.ListModelList;

import com.dtac.billerweb.common.AbstractPagingListModel;
import com.dtac.billerweb.data.OutboundGatewaySO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.service.OutboundGatewayService;

public class BW2300ListModel extends AbstractPagingListModel<OutboundGatewaySO> {
	private static Logger log = Logger.getLogger(BW2300ListModel.class);
	private OutboundGatewayParam condition;

	public BW2300ListModel(int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		super(currentPage, pageSize,userInfo);
	}

	@Override
	public int getTotalSize(UserInfoBean userInfo) throws Exception {
		OutboundGatewayService outboundGatewayService = null;
		int rownum = 0;
		try {
			outboundGatewayService = BillerwebServiceFactory.getOutboundGatewayService();
			if (condition == null) {
				condition = new OutboundGatewayParam();
			}
			rownum = outboundGatewayService.getOutboundGatewayRowCount(condition,userInfo);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		} finally {
			/*--Garbage--*/
			outboundGatewayService = null;
		}

		return rownum;
	}

	@Override
	protected ListModelList<OutboundGatewaySO> getPageData(int currentPage, int pageSize,UserInfoBean userInfo) throws Exception {
		log.info("getPageData[currentPage:" + currentPage + ",pageSize:"
				+ pageSize);
		OutboundGatewayService outboundGatewayService = null;
		List<OutboundGatewaySO> outboundGatewaySOs = null;
		ListModelList<OutboundGatewaySO> listModel = new ListModelList<OutboundGatewaySO>();
		try {
			outboundGatewayService = BillerwebServiceFactory.getOutboundGatewayService();

			if (condition == null) {
				condition = new OutboundGatewayParam();
			}
			log.debug("currentPage::" + currentPage);
			log.debug("pageSize::" + pageSize);
			outboundGatewaySOs = outboundGatewayService.searchOutboundGateway(condition, currentPage, pageSize,userInfo);

			log.info("outboundGatewaySOs Size::" + outboundGatewaySOs.size());
			listModel = new ListModelList<OutboundGatewaySO>(outboundGatewaySOs);

			/*--Garbage--*/
		} catch (Exception ex) {			
			throw new BillerWebException(ex);
		} finally {
			outboundGatewayService = null;
			outboundGatewaySOs = null;
		}
		return listModel;
	}

	public OutboundGatewayParam getCondition() {
		return condition;
	}

	public void setCondition(OutboundGatewayParam condition) {
		this.condition = condition;
	}

}
