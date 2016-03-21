package com.dtac.billerweb.data;

import java.math.BigDecimal;
import java.util.Date;

import mfs.biller.persistence.bean.BillerFee;

import com.dtac.billerweb.common.BaseDO;

public class BillerFeeSO extends BaseDO {	
	private static final long serialVersionUID = -7323137468968548017L;
	private String billerFeeId;
	private String billerSrvc;
	private String feeType;	
	private Date efftDate;
	private Date exprDate;
	private BigDecimal srvcFee;
	private BigDecimal feeAmount;	
	private BigDecimal fundamoFeeAmount;
	private String updateBy;
	private Date updateDate;

	public BillerFeeSO toBillerFeeSO(BillerFee billerFee) {		
		this.billerFeeId = billerFee.getBLLR_FEE_ID().toString();
		this.billerSrvc = billerFee.getBLLR_SRVC_NAME_EN();
		this.feeType = billerFee.getFEE_TYPE();		
		this.efftDate = billerFee.getEFFT_DATE();
		this.exprDate = billerFee.getEXPR_DATE();	
		this.srvcFee = billerFee.getSRVC_FEE();
		this.feeAmount = billerFee.getFEE_AMOUNT();
		this.fundamoFeeAmount = billerFee.getFUNDAMO_FEE_AMOUNT();	
		this.updateBy = billerFee.getLAST_CHNG_BY();
		this.updateDate = billerFee.getLAST_CHNG_DTTM();
		return this;
	}

	public String getBillerFeeId() {
		return billerFeeId;
	}

	public void setBillerFeeId(String billerFeeId) {
		this.billerFeeId = billerFeeId;
	}

	public String getBillerSrvc() {
		return billerSrvc;
	}

	public void setBillerSrvc(String billerSrvc) {
		this.billerSrvc = billerSrvc;
	}

	public Date getEfftDate() {
		return efftDate;
	}

	public void setEfftDate(Date efftDate) {
		this.efftDate = efftDate;
	}

	public Date getExprDate() {
		return exprDate;
	}

	public void setExprDate(Date exprDate) {
		this.exprDate = exprDate;
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

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public BigDecimal getSrvcFee() {
		return srvcFee;
	}

	public void setSrvcFee(BigDecimal srvcFee) {
		this.srvcFee = srvcFee;
	}
}
