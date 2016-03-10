package com.dtac.billerweb.data;

import java.util.Date;

import mfs.biller.persistence.bean.BillerChannel;
import mfs.biller.persistence.bean.BillerMaster;

import com.dtac.billerweb.common.BaseDO;

public class BillerChannelSO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7142337830025139449L;
	private Integer billerChannelId;
	private String billerChannelName;
	private String billerchannelCode;
	private String activeFlag;
	private String updateBy;
	private Date updateDate;

	public BillerChannelSO toBillerChannelSO(BillerChannel billerChannel) {
		this.billerChannelId = billerChannel.getBLLR_CHNL_ID();
		this.billerChannelName = billerChannel.getBLLR_CHNL_NAME();
		this.billerchannelCode = billerChannel.getBLLR_CHNL_CODE();
		this.activeFlag = billerChannel.getACT_FLAG();
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

	public String getBillerchannelCode() {
		return billerchannelCode;
	}

	public void setBillerchannelCode(String billerchannelCode) {
		this.billerchannelCode = billerchannelCode;
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
