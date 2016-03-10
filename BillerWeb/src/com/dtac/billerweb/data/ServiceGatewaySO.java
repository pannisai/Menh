package com.dtac.billerweb.data;

import mfs.biller.persistence.bean.ServiceGatewayResult;

import com.dtac.billerweb.common.BaseDO;
import com.dtac.billerweb.util.AppUtil;

public class ServiceGatewaySO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9176449936978565300L;
	private String serviceGatewayId;
	private String serviceGatewayIdDisplay;
	private String serviceGatewayName;
	private String serviceMapId;
	private String serviceMapName;
	private String outboundId;
	private String outboundName;
	private String activeFlag;
	private String updateBy;
	private String updateDate;

	public ServiceGatewaySO toServiceGatewaySO(ServiceGatewayResult serviceGatewayResult) {
		this.serviceGatewayId = serviceGatewayResult.getGW_SRVC_ID();
		this.serviceGatewayIdDisplay = AppUtil.addPadding(this.serviceGatewayId, "0", 5);
		this.serviceGatewayName = serviceGatewayResult.getGW_SRVC_NAME();
		this.serviceMapId = serviceGatewayResult.getGW_SRVC_MAP_ID();
		this.serviceMapName=serviceGatewayResult.getDATA_MAP_NAME();
		this.outboundId = serviceGatewayResult.getGW_OUTB_ID();
		this.outboundName=serviceGatewayResult.getGW_OUTB_NAME();
		this.activeFlag = serviceGatewayResult.getACT_FLAG();
		this.updateBy = serviceGatewayResult.getLAST_CHNG_BY();
		this.updateDate = serviceGatewayResult.getLAST_CHNG_DTTM();
		return this;
	}

	public String getServiceGatewayId() {
		return serviceGatewayId;
	}

	public void setServiceGatewayId(String serviceGatewayId) {
		this.serviceGatewayId = serviceGatewayId;
	}

	public String getServiceGatewayIdDisplay() {
		return serviceGatewayIdDisplay;
	}

	public void setServiceGatewayIdDisplay(String serviceGatewayIdDisplay) {
		this.serviceGatewayIdDisplay = serviceGatewayIdDisplay;
	}

	public String getServiceGatewayName() {
		return serviceGatewayName;
	}

	public void setServiceGatewayName(String serviceGatewayName) {
		this.serviceGatewayName = serviceGatewayName;
	}

	public String getServiceMapId() {
		return serviceMapId;
	}

	public void setServiceMapId(String serviceMapId) {
		this.serviceMapId = serviceMapId;
	}

	public String getOutboundId() {
		return outboundId;
	}

	public void setOutboundId(String outboundId) {
		this.outboundId = outboundId;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getOutboundName() {
		return outboundName;
	}

	public void setOutboundName(String outboundName) {
		this.outboundName = outboundName;
	}

	public String getServiceMapName() {
		return serviceMapName;
	}

	public void setServiceMapName(String serviceMapName) {
		this.serviceMapName = serviceMapName;
	}

}
