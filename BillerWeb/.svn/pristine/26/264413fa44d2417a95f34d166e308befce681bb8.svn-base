package com.dtac.billerweb.data;

import java.math.BigDecimal;
import java.util.Date;

import mfs.biller.persistence.bean.BillerDenominate;

import com.dtac.billerweb.common.BaseDO;

public class BillerDenominateSO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4382407132698815488L;
	private Integer billerDenominateId;
	private Integer billerServiceId;
	private BigDecimal denominateRate;
	private Integer billerMaxMonthNo;
	private String activeFlag;
	private String updateBy;
	private Date updateDate;
	

	public BillerDenominateSO toBillerDenominateSO(BillerDenominate billerDenominate) {
		this.billerDenominateId = billerDenominate.getBLLR_DENM_ID();
		this.billerServiceId = billerDenominate.getBLLR_SRVC_ID();
		this.denominateRate = billerDenominate.getBLLR_DENM_RATE();
		this.billerMaxMonthNo = billerDenominate.getBLLR_MAX_NO_MNTH();
		this.activeFlag =billerDenominate.getACT_FLAG();
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

	public Integer getBillerMaxMonthNo() {
		return billerMaxMonthNo;
	}

	public void setBillerMaxMonthNo(Integer billerMaxMonthNo) {
		this.billerMaxMonthNo = billerMaxMonthNo;
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
