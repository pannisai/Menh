package com.dtac.billerweb.form;

import java.math.BigDecimal;
import java.util.Date;

import mfs.biller.persistence.bean.BillerDenominate;

import com.dtac.billerweb.common.BaseForm;

public class BW1460Form extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6480285700082562521L;
	private Integer billerDenominateId;
	private Integer billerServiceId;
	private BigDecimal denominateRate;
	private String activeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;


	public BillerDenominate toBillerDenominate() {
		BillerDenominate billerDenominate = new BillerDenominate();
		billerDenominate.setBLLR_DENM_ID(this.billerDenominateId);
		billerDenominate.setBLLR_SRVC_ID(this.billerServiceId);
		billerDenominate.setBLLR_DENM_RATE(this.denominateRate);
		billerDenominate.setACT_FLAG(this.activeFlag);
		billerDenominate.setCRTD_BY(this.createBy);
		billerDenominate.setCRTD_DTTM(this.createDate);
		billerDenominate.setLAST_CHNG_BY(this.updateBy);
		billerDenominate.setLAST_CHNG_DTTM(this.updateDate);
		return billerDenominate;
	}

	public BW1460Form toBW1460Form(BillerDenominate billerDenominate) {
		this.billerDenominateId = billerDenominate.getBLLR_DENM_ID();
		this.billerServiceId = billerDenominate.getBLLR_SRVC_ID();
		this.denominateRate = billerDenominate.getBLLR_DENM_RATE();
		this.activeFlag = billerDenominate.getACT_FLAG();
		this.createBy = billerDenominate.getCRTD_BY();
		this.createDate = billerDenominate.getCRTD_DTTM();
		this.updateBy = billerDenominate.getLAST_CHNG_BY();
		this.updateDate = billerDenominate.getLAST_CHNG_DTTM();
		return this;
	}

	public Integer getBillerDenominateId() {
		return billerDenominateId;
	}

	public void setBillerDenominateId(Integer billerDenominateId) {
		this.billerDenominateId = billerDenominateId;
	}

	public Integer getBillerServiceId() {
		return billerServiceId;
	}

	public void setBillerServiceId(Integer billerServiceId) {
		this.billerServiceId = billerServiceId;
	}

	public BigDecimal getDenominateRate() {
		return denominateRate;
	}

	public void setDenominateRate(BigDecimal denominateRate) {
		this.denominateRate = denominateRate;
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

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
}
