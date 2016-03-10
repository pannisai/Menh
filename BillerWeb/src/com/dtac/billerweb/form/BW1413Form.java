package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.BillerPaymentValidate;
import mfs.biller.persistence.bean.BillerServiceChannel;

import com.dtac.billerweb.common.BaseForm;

public class BW1413Form extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer billerChannelId;
	private Integer billerServiceId;
	private String billerChannelCode;
	private String allowedKeyIn;
	private String allowedBarcode;
	private String activeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public BillerServiceChannel toBillerServiceChannel() {
		BillerServiceChannel billerServiceChannel = new BillerServiceChannel();
		billerServiceChannel.setBLLR_CHNL_ID(this.billerChannelId);
		billerServiceChannel.setBLLR_SRVC_ID(this.billerServiceId);
		billerServiceChannel.setBLLR_SRVC_CHNL_CODE(this.billerChannelCode);
		billerServiceChannel.setBLLR_SRVC_CHNL_KEYN_FLAG(this.allowedKeyIn);
		billerServiceChannel.setBLLR_SRVC_CHNL_BARC_FLAG(this.allowedBarcode);
		billerServiceChannel.setACT_FLAG(this.activeFlag);
		billerServiceChannel.setCRTD_BY(this.createBy);
		billerServiceChannel.setCRTD_DTTM(this.createDate);
		billerServiceChannel.setLAST_CHNG_BY(this.updateBy);
		billerServiceChannel.setLAST_CHNG_DTTM(this.updateDate);
		return billerServiceChannel;
	}

	public BW1413Form toBW1413Form(BillerServiceChannel billerServiceChannel) {
		this.billerChannelId = billerServiceChannel.getBLLR_CHNL_ID();
		this.billerServiceId = billerServiceChannel.getBLLR_SRVC_ID();
		this.billerChannelCode=billerServiceChannel.getBLLR_SRVC_CHNL_CODE();
		this.allowedKeyIn = billerServiceChannel.getBLLR_SRVC_CHNL_KEYN_FLAG();
		this.allowedBarcode = billerServiceChannel.getBLLR_SRVC_CHNL_BARC_FLAG();
		this.activeFlag = billerServiceChannel.getACT_FLAG();
		this.createBy = billerServiceChannel.getCRTD_BY();
		this.createDate = billerServiceChannel.getCRTD_DTTM();
		this.updateBy = billerServiceChannel.getLAST_CHNG_BY();
		this.updateDate = billerServiceChannel.getLAST_CHNG_DTTM();
		return this;
	}

	public Integer getBillerChannelId() {
		return billerChannelId;
	}

	public void setBillerChannelId(Integer billerChannelId) {
		this.billerChannelId = billerChannelId;
	}

	public Integer getBillerServiceId() {
		return billerServiceId;
	}

	public void setBillerServiceId(Integer billerServiceId) {
		this.billerServiceId = billerServiceId;
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

	public String getBillerChannelCode() {
		return billerChannelCode;
	}

	public void setBillerChannelCode(String billerChannelCode) {
		this.billerChannelCode = billerChannelCode;
	}
}
