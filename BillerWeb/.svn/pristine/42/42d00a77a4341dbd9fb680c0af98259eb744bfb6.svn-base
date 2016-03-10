package com.dtac.billerweb.data;

import java.util.Date;

import mfs.biller.persistence.bean.BillerServiceChannel;

import com.dtac.billerweb.common.BaseDO;

public class BillerServiceChannelSO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7682784322615587888L;
	private Integer billerChannelId;
	private Integer billerServiceId;
	private String billerChannelCode;
	private String allowedKeyIn;
	private String allowedBarcode;
	private String activeFlag;
	private String updateBy;
	private Date updateDate;

	public BillerServiceChannelSO toBillerServiceChannelSO(BillerServiceChannel billerChannel) {
		this.billerChannelId = billerChannel.getBLLR_CHNL_ID();
		this.billerServiceId=billerChannel.getBLLR_SRVC_ID();
		this.billerChannelCode = billerChannel.getBLLR_SRVC_CHNL_CODE();
		this.allowedKeyIn = billerChannel.getBLLR_SRVC_CHNL_KEYN_FLAG();
		this.allowedBarcode = billerChannel.getBLLR_SRVC_CHNL_BARC_FLAG();
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

	public String getBillerChannelCode() {
		return billerChannelCode;
	}

	public void setBillerChannelCode(String billerChannelCode) {
		this.billerChannelCode = billerChannelCode;
	}

	public String getAllowedKeyIn() {
		return allowedKeyIn;
	}

	public void setAllowedKeyIn(String allowedKeyIn) {
		this.allowedKeyIn = allowedKeyIn;
	}

	public String getAllowedBarcode() {
		return allowedBarcode;
	}

	public void setAllowedBarcode(String allowedBarcode) {
		this.allowedBarcode = allowedBarcode;
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

	public Integer getBillerServiceId() {
		return billerServiceId;
	}

	public void setBillerServiceId(Integer billerServiceId) {
		this.billerServiceId = billerServiceId;
	}
}
