package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.BillerChannel;
import mfs.biller.persistence.bean.OBJGW_INBOUND;

import com.dtac.billerweb.common.BaseForm;

public class BW2110Form extends BaseForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3216648909186099484L;
	private Integer billerServiceCode;
	private String billerCode;
	private Integer billerId;
	private Integer functionId;
	private String inboundName;
	private Integer mfsServiceId;
	private Integer inboundMapId;
	private String sendReceipt;
	private Integer sendReceiptId;
	private Date startDate;
	private Date endDate;
	private String activeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public OBJGW_INBOUND toOBJGW_INBOUND() {
		OBJGW_INBOUND inboundGateway = new OBJGW_INBOUND();
		inboundGateway.setINBN_SRVC_ID(this.billerServiceCode);
		inboundGateway.setDEST_SRVC_ID(1);//11-05-2013 Requirement change to set value is 1;
		inboundGateway.setSRCE_SRVC_ID(this.functionId);
		inboundGateway.setINBN_SRVC_NAME(this.inboundName);
		inboundGateway.setGW_SRVC_ID(this.mfsServiceId);
		inboundGateway.setGW_INBN_MAP_ID(this.inboundMapId);
		inboundGateway.setSEND_RCPT_FLAG(this.sendReceipt);
		inboundGateway.setGW_RCPT_CONF_ID(this.sendReceiptId);
		inboundGateway.setSTRT_DATE(this.startDate);
		inboundGateway.setEND_DATE(this.endDate);
		inboundGateway.setACT_FLAG(this.activeFlag);
		inboundGateway.setCRTD_BY(this.createBy);
		inboundGateway.setCRTD_DTTM(this.createDate);
		inboundGateway.setLAST_CHNG_BY(this.updateBy);
		inboundGateway.setLAST_CHNG_DTTM(this.updateDate);
		return inboundGateway;
	}

	public BW2110Form toBW2110Form(OBJGW_INBOUND inboundGateway) {
		this.billerServiceCode = inboundGateway.getINBN_SRVC_ID();
		this.billerId = inboundGateway.getDEST_SRVC_ID();
		this.functionId = inboundGateway.getSRCE_SRVC_ID();
		this.inboundName = inboundGateway.getINBN_SRVC_NAME();
		this.mfsServiceId = inboundGateway.getGW_SRVC_ID();
		this.inboundMapId = inboundGateway.getGW_INBN_MAP_ID();
		this.sendReceipt = inboundGateway.getSEND_RCPT_FLAG();
		this.sendReceiptId = inboundGateway.getGW_RCPT_CONF_ID();
		this.startDate = inboundGateway.getSTRT_DATE();
		this.endDate = inboundGateway.getEND_DATE();
		this.activeFlag = inboundGateway.getACT_FLAG();
		this.createBy = inboundGateway.getCRTD_BY();
		this.createDate = inboundGateway.getCRTD_DTTM();
		this.updateBy = inboundGateway.getLAST_CHNG_BY();
		this.updateDate = inboundGateway.getLAST_CHNG_DTTM();
		return this;
	}

	public Integer getBillerServiceCode() {
		return billerServiceCode;
	}

	public void setBillerServiceCode(Integer billerServiceCode) {
		this.billerServiceCode = billerServiceCode;
	}

	public String getBillerCode() {
		return billerCode;
	}

	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
	}

	public Integer getBillerId() {
		return billerId;
	}

	public void setBillerId(Integer billerId) {
		this.billerId = billerId;
	}

	public Integer getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
	}

	public String getInboundName() {
		return inboundName;
	}

	public void setInboundName(String inboundName) {
		this.inboundName = inboundName;
	}

	public Integer getMfsServiceId() {
		return mfsServiceId;
	}

	public void setMfsServiceId(Integer mfsServiceId) {
		this.mfsServiceId = mfsServiceId;
	}

	public Integer getInboundMapId() {
		return inboundMapId;
	}

	public void setInboundMapId(Integer inboundMapId) {
		this.inboundMapId = inboundMapId;
	}

	public String getSendReceipt() {
		return sendReceipt;
	}

	public void setSendReceipt(String sendReceipt) {
		this.sendReceipt = sendReceipt;
	}

	public Integer getSendReceiptId() {
		return sendReceiptId;
	}

	public void setSendReceiptId(Integer sendReceiptId) {
		this.sendReceiptId = sendReceiptId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
