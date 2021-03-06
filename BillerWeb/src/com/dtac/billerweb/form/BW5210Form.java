package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.BankServicebean;
import mfs.biller.persistence.bean.GWBankBean;
import mfs.biller.util.DateTimeUtil;

import com.dtac.billerweb.common.BaseForm;
import com.dtac.billerweb.util.AppUtil;

public class BW5210Form extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2934739624170537444L;
	private Integer bankServiceId;
	private String bankServiceName;
	private Integer inboundMapId;
	private Integer serviceMapId;
	private Integer outboundMapId;
	private String activeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public GWBankBean toBankGatewayService() {
		GWBankBean gwBankBean = new GWBankBean();
		gwBankBean.setBANK_SRVC_ID(this.bankServiceId);
		gwBankBean.setINB_MAP_ID(this.inboundMapId);
		gwBankBean.setGW_MAP_ID(this.serviceMapId);
		gwBankBean.setOUTB_MAP_ID(this.outboundMapId);
		gwBankBean.setACT_FLAG(this.activeFlag);
		gwBankBean.setCRTD_BY(this.createBy);
		gwBankBean.setCRTD_DTTM(this.createDate);
		gwBankBean.setLAST_CHNG_BY(this.updateBy);
		gwBankBean.setLAST_CHNG_DTTM(this.updateDate);
		return gwBankBean;
	}

	public BW5210Form toBW5210Form(GWBankBean gwBankBean) {
		this.bankServiceId = gwBankBean.getBANK_SRVC_ID();
		this.inboundMapId = gwBankBean.getINB_MAP_ID();
		this.serviceMapId = gwBankBean.getGW_MAP_ID();
		this.outboundMapId = gwBankBean.getOUTB_MAP_ID();
		this.activeFlag = gwBankBean.getACT_FLAG();
		this.updateBy = gwBankBean.getLAST_CHNG_BY();
		this.updateDate = gwBankBean.getLAST_CHNG_DTTM();
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

	public Integer getInboundMapId() {
		return inboundMapId;
	}

	public void setInboundMapId(Integer inboundMapId) {
		this.inboundMapId = inboundMapId;
	}

	public Integer getServiceMapId() {
		return serviceMapId;
	}

	public void setServiceMapId(Integer serviceMapId) {
		this.serviceMapId = serviceMapId;
	}

	public Integer getOutboundMapId() {
		return outboundMapId;
	}

	public void setOutboundMapId(Integer outboundMapId) {
		this.outboundMapId = outboundMapId;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
