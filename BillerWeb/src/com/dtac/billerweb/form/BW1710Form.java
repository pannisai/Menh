package com.dtac.billerweb.form;

import java.math.BigDecimal;
import java.util.Date;

import mfs.biller.persistence.bean.BillerFee;

import com.dtac.billerweb.common.BaseForm;

public class BW1710Form extends BaseForm {

	private static final long serialVersionUID = 1L;

	private Integer bllrFeeId;
	private Integer bllrSrvcId;
	private Integer bllrFeeMastId;
	private Date efftDate;
	private Date exprDate;
	private String actFlag;
	private BigDecimal srvcFee;	
	private BigDecimal feeAmount;	
	private BigDecimal fundamoFeeAmount;

	public BillerFee toBillerFee() {
		BillerFee bean = new BillerFee();

		bean.setBLLR_FEE_ID(this.bllrFeeId);
		bean.setBLLR_SRVC_ID(this.bllrSrvcId);
		bean.setBLLR_FEE_MAST_ID(this.bllrFeeMastId);
		bean.setFEE_AMOUNT(this.feeAmount);
		bean.setFUNDAMO_FEE_AMOUNT(this.fundamoFeeAmount);
		bean.setEFFT_DATE(this.efftDate);
		bean.setEXPR_DATE(this.exprDate);
		bean.setACT_FLAG(this.actFlag);
		return bean;
	}

	public Integer getBllrSrvcId() {
		return bllrSrvcId;
	}

	public void setBllrSrvcId(Integer bllrSrvcId) {
		this.bllrSrvcId = bllrSrvcId;
	}

	public Integer getBllrFeeMastId() {
		return bllrFeeMastId;
	}

	public void setBllrFeeMastId(Integer bllrFeeMastId) {
		this.bllrFeeMastId = bllrFeeMastId;
	}

	public Date getEfftDate() {
		return efftDate;
	}

	public void setEfftDate(Date efftDate) {
		this.efftDate = efftDate;
	}

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}

	public BigDecimal getFundamoFeeAmount() {
		return fundamoFeeAmount;
	}

	public void setFundamoFeeAmount(BigDecimal fundamoFeeAmount) {
		this.fundamoFeeAmount = fundamoFeeAmount;
	}

	public Integer getBllrFeeId() {
		return bllrFeeId;
	}

	public void setBllrFeeId(Integer bllrFeeId) {
		this.bllrFeeId = bllrFeeId;
	}

	public BigDecimal getSrvcFee() {
		return srvcFee;
	}

	public void setSrvcFee(BigDecimal srvcFee) {
		this.srvcFee = srvcFee;
	}

	public Date getExprDate() {
		return exprDate;
	}

	public void setExprDate(Date exprDate) {
		this.exprDate = exprDate;
	}

	public String getActFlag() {
		return actFlag;
	}

	public void setActFlag(String actFlag) {
		this.actFlag = actFlag;
	}

}
