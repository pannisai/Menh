package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.BillerChannel;

import com.dtac.billerweb.common.BaseForm;

public class BW1310Form extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -584947709838287512L;
	private Integer billerChannelId;
	private String billerChannelName;
	private String billerChannelCode;
	private String activeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public BillerChannel toBillerChannel() {
		BillerChannel billerChannel = new BillerChannel();
		billerChannel.setBLLR_CHNL_ID(this.billerChannelId);
		billerChannel.setBLLR_CHNL_NAME(this.billerChannelName);
		billerChannel.setBLLR_CHNL_CODE(this.billerChannelCode);
		billerChannel.setACT_FLAG(this.activeFlag);
		billerChannel.setCRTD_BY(this.createBy);
		billerChannel.setCRTD_DTTM(this.createDate);
		billerChannel.setLAST_CHNG_BY(this.updateBy);
		billerChannel.setLAST_CHNG_DTTM(this.updateDate);
		return billerChannel;
	}

	public BW1310Form toBW1310Form(BillerChannel billerChannel) {
		this.billerChannelId = billerChannel.getBLLR_CHNL_ID();
		this.billerChannelName = billerChannel.getBLLR_CHNL_NAME();
		this.billerChannelCode = billerChannel.getBLLR_CHNL_CODE();
		this.activeFlag = billerChannel.getACT_FLAG();
		this.createBy = billerChannel.getCRTD_BY();
		this.createDate = billerChannel.getCRTD_DTTM();
		this.updateBy = billerChannel.getLAST_CHNG_BY();
		this.updateDate = billerChannel.getLAST_CHNG_DTTM();
		return this;
	}

	public Integer getBillerChannelId() {
		return billerChannelId;
	}

	public void setBillerChannelId(Integer billerChannelId) {
		this.billerChannelId = billerChannelId;
	}

	public String getBillerChannelName() {
		return billerChannelName;
	}

	public void setBillerChannelName(String billerChannelName) {
		this.billerChannelName = billerChannelName;
	}

	public String getBillerChannelCode() {
		return billerChannelCode;
	}

	public void setBillerChannelCode(String billerChannelCode) {
		this.billerChannelCode = billerChannelCode;
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
