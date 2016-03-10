package com.dtac.billerweb.data;

import java.util.Date;

import mfs.biller.persistence.bean.GWBankDetail;

import com.dtac.billerweb.common.BaseDO;

public class BankGatewaySO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6723606537079423267L;
	private Integer bankServiceId;
	private String bankServiceName;
	private Integer inboundId;
	private String inboundName;
	private Integer gwServiceId;
	private String gwServiceName;
	private Integer outboundId;
	private String outboundName;
	private String activeFlag;
	private String updateBy;
	private Date updateDate;
	public BankGatewaySO toBankGatewaySO(GWBankDetail gwBankDetail) {
		this.bankServiceId = gwBankDetail.getBANK_SRVC_ID();
		this.bankServiceName = gwBankDetail.getBANK_SRVC_NAME();
		this.inboundId = gwBankDetail.getINB_MAP_ID();
		this.inboundName = gwBankDetail.getINB_DATA_MAP_NAME();
		this.gwServiceId=gwBankDetail.getGW_MAP_ID();
		this.gwServiceName=gwBankDetail.getGW_DATA_MAP_NAME();
		this.outboundId=gwBankDetail.getOUTB_MAP_ID();
		this.outboundName=gwBankDetail.getOUTB_DATA_MAP_NAME();
		this.activeFlag = gwBankDetail.getACT_FLAG();
		this.updateBy = gwBankDetail.getLAST_CHNG_BY();
		this.updateDate = gwBankDetail.getLAST_CHNG_DTTM();
		return this;
	}
	public Integer getBankServiceId() {
		return bankServiceId;
	}
	public void setBankServiceId(Integer bankServiceId) {
		this.bankServiceId = bankServiceId;
	}
	public String getBankServiceName() {
		return bankServiceName;
	}
	public void setBankServiceName(String bankServiceName) {
		this.bankServiceName = bankServiceName;
	}
	public Integer getInboundId() {
		return inboundId;
	}
	public void setInboundId(Integer inboundId) {
		this.inboundId = inboundId;
	}
	public String getInboundName() {
		return inboundName;
	}
	public void setInboundName(String inboundName) {
		this.inboundName = inboundName;
	}
	public Integer getGwServiceId() {
		return gwServiceId;
	}
	public void setGwServiceId(Integer gwServiceId) {
		this.gwServiceId = gwServiceId;
	}
	public String getGwServiceName() {
		return gwServiceName;
	}
	public void setGwServiceName(String gwServiceName) {
		this.gwServiceName = gwServiceName;
	}
	public Integer getOutboundId() {
		return outboundId;
	}
	public void setOutboundId(Integer outboundId) {
		this.outboundId = outboundId;
	}
	public String getOutboundName() {
		return outboundName;
	}
	public void setOutboundName(String outboundName) {
		this.outboundName = outboundName;
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
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
