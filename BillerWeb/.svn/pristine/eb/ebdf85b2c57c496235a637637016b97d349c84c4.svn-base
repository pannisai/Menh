package com.dtac.billerweb.data;

import java.util.Date;

import mfs.biller.persistence.bean.getInboundGatewayResult;

import com.dtac.billerweb.common.BaseDO;
import com.dtac.billerweb.util.AppUtil;

public class InboundGatewaySO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1170907642092859711L;
	private InboundGatewaySOPK inboundGatewaySOPK;
	private String billerServiceCode;
	private String billerServiceCodeDisplay;
	private String billerServiceName;
	private String billerCode;
	private String billerName;
	private String billerCatalog;
	private String function;
	private String inboundName;
	private String serviceGatewayId;
	private String serviceGatewayName;
	private String inboundMapId;
	private String inboundMapName;
	private String activeFlag;
	private String updateBy;
	private String updateDate;

	public InboundGatewaySO toInboundGatewaySO(getInboundGatewayResult inboundGatewayResult) {
		this.inboundGatewaySOPK = new InboundGatewaySOPK();
		inboundGatewaySOPK.setInbn_SRVC_ID(inboundGatewayResult.getBLLR_SRVC_CODE());//use bllr_srve_code instead inbn_srvc_id
		inboundGatewaySOPK.setDest_SRVC_ID(Integer.parseInt(inboundGatewayResult.getDEST_SRVC_ID()));
		inboundGatewaySOPK.setSrce_SRVC_ID(Integer.parseInt(inboundGatewayResult.getSRCE_SRVC_ID()));
		this.billerServiceCode = inboundGatewayResult.getBLLR_SRVC_CODE();
		this.billerServiceCodeDisplay = AppUtil.addPadding(this.billerServiceCode, "0", 4);
		this.billerServiceName = inboundGatewayResult.getBLLR_SRVC_DESC();
		this.billerCode = inboundGatewayResult.getBLLR_CODE();
		this.billerName = inboundGatewayResult.getBLLR_MSTR_NAME();
		this.billerCatalog = inboundGatewayResult.getBLLR_CATG_NAME();
		this.function = inboundGatewayResult.getSRCE_SRVC_ID();
		this.inboundName=inboundGatewayResult.getINBN_SRVC_NAME();
		this.serviceGatewayId=inboundGatewayResult.getGW_SRVC_ID();
		this.serviceGatewayName=inboundGatewayResult.getGW_SRVC_NAME();
		this.inboundMapId=inboundGatewayResult.getGW_INBN_MAP_ID();
		this.inboundMapName=inboundGatewayResult.getDATA_MAP_NAME();
		this.activeFlag = inboundGatewayResult.getACT_FLAG();
		this.updateBy = inboundGatewayResult.getLAST_CHNG_BY();
		this.updateDate = inboundGatewayResult.getLAST_CHNG_DTTM();
		return this;
	}

	public String getBillerServiceCode() {
		return billerServiceCode;
	}

	public void setBillerServiceCode(String billerServiceCode) {
		this.billerServiceCode = billerServiceCode;
	}

	public String getBillerServiceCodeDisplay() {
		return billerServiceCodeDisplay;
	}

	public void setBillerServiceCodeDisplay(String billerServiceCodeDisplay) {
		this.billerServiceCodeDisplay = billerServiceCodeDisplay;
	}

	public String getBillerServiceName() {
		return billerServiceName;
	}

	public void setBillerServiceName(String billerServiceName) {
		this.billerServiceName = billerServiceName;
	}

	public String getBillerCode() {
		return billerCode;
	}

	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
	}

	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	public String getBillerCatalog() {
		return billerCatalog;
	}

	public void setBillerCatalog(String billerCatalog) {
		this.billerCatalog = billerCatalog;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
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

	public InboundGatewaySOPK getInboundGatewaySOPK() {
		return inboundGatewaySOPK;
	}

	public void setInboundGatewaySOPK(InboundGatewaySOPK inboundGatewaySOPK) {
		this.inboundGatewaySOPK = inboundGatewaySOPK;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getInboundName() {
		return inboundName;
	}

	public void setInboundName(String inboundName) {
		this.inboundName = inboundName;
	}

	public String getServiceGatewayId() {
		return serviceGatewayId;
	}

	public void setServiceGatewayId(String serviceGatewayId) {
		this.serviceGatewayId = serviceGatewayId;
	}

	public String getServiceGatewayName() {
		return serviceGatewayName;
	}

	public void setServiceGatewayName(String serviceGatewayName) {
		this.serviceGatewayName = serviceGatewayName;
	}

	public String getInboundMapId() {
		return inboundMapId;
	}

	public void setInboundMapId(String inboundMapId) {
		this.inboundMapId = inboundMapId;
	}

	public String getInboundMapName() {
		return inboundMapName;
	}

	public void setInboundMapName(String inboundMapName) {
		this.inboundMapName = inboundMapName;
	}
}
