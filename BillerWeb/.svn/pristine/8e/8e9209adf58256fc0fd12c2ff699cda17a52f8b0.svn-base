package com.dtac.billerweb.form;

import java.math.BigDecimal;
import java.util.Date;

import mfs.biller.persistence.bean.BillerPaymentValidate;

import com.dtac.billerweb.common.BaseForm;

public class BW1412Form extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7539453422379109230L;
	private Integer paymentValidateId;
	private Integer billerServiceId;
	private String denominateFlag;
	private Integer denoMaxMonth;
	private String paymentPresentFlag;
	private String paymentPartialFlag;
	private String amtAvaliableFlag;
	private String paymentOverFlag;
	private String overdueFlag;
	private BigDecimal paymentMin;
	private BigDecimal paymentMax;
	private String nonFundamoFeeFlag;
	private String comNonFundamoFeeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public BillerPaymentValidate toBillerPaymentValidate() {
		BillerPaymentValidate billerPaymentVal = new BillerPaymentValidate();
		billerPaymentVal.setBLLR_PMNT_VALD_ID(this.paymentValidateId);
		billerPaymentVal.setBLLR_SRVC_ID(this.billerServiceId);
		 billerPaymentVal.setBLLR_DENM_FLAG(this.denominateFlag);
		 billerPaymentVal.setBLLR_MAX_NO_MNTH(this.denoMaxMonth);
		billerPaymentVal.setBLLR_PRES_FLAG(this.paymentPresentFlag);
		billerPaymentVal.setBLLR_PMNT_PART_FLAG(this.paymentPartialFlag);
		billerPaymentVal.setBLLR_BILL_AMNT_FLAG(this.amtAvaliableFlag);
		billerPaymentVal.setBLLR_PMNT_OVER_FLAG(this.paymentOverFlag);
		billerPaymentVal.setBLLR_OVER_DUE_FLAG(this.overdueFlag);
		billerPaymentVal.setBLLR_PMNT_AMNT_MIN(this.paymentMin);
		billerPaymentVal.setBLLR_PMNT_AMNT_MAX(this.paymentMax);
		billerPaymentVal.setBLLR_NON_FDM_FEE_FLAG(this.nonFundamoFeeFlag);
		billerPaymentVal.setBLLR_NON_FDM_COMS_FLAG(this.comNonFundamoFeeFlag);
		billerPaymentVal.setCRTD_BY(this.createBy);
		billerPaymentVal.setCRTD_DTTM(this.createDate);
		billerPaymentVal.setLAST_CHNG_BY(this.updateBy);
		billerPaymentVal.setLAST_CHNG_DTTM(this.updateDate);
		return billerPaymentVal;
	}

	public BW1412Form toBW1412Form(BillerPaymentValidate billerPaymentVal) {
		this.paymentValidateId = billerPaymentVal.getBLLR_PMNT_VALD_ID();
		this.billerServiceId = billerPaymentVal.getBLLR_SRVC_ID();
		this.denominateFlag=billerPaymentVal.getBLLR_DENM_FLAG();
		this.denoMaxMonth=billerPaymentVal.getBLLR_MAX_NO_MNTH();
		this.paymentPresentFlag = billerPaymentVal.getBLLR_PRES_FLAG();
		this.paymentPartialFlag = billerPaymentVal.getBLLR_PMNT_PART_FLAG();
		this.amtAvaliableFlag = billerPaymentVal.getBLLR_BILL_AMNT_FLAG();
		this.paymentOverFlag = billerPaymentVal.getBLLR_PMNT_OVER_FLAG();
		this.overdueFlag = billerPaymentVal.getBLLR_OVER_DUE_FLAG();
		this.paymentMin = billerPaymentVal.getBLLR_PMNT_AMNT_MIN();
		this.paymentMax = billerPaymentVal.getBLLR_PMNT_AMNT_MAX();
		this.nonFundamoFeeFlag = billerPaymentVal.getBLLR_NON_FDM_FEE_FLAG();
		this.comNonFundamoFeeFlag = billerPaymentVal.getBLLR_NON_FDM_COMS_FLAG();
		this.createBy = billerPaymentVal.getCRTD_BY();
		this.createDate = billerPaymentVal.getCRTD_DTTM();
		this.updateBy = billerPaymentVal.getLAST_CHNG_BY();
		this.updateDate = billerPaymentVal.getLAST_CHNG_DTTM();
		return this;
	}

	public Integer getPaymentValidateId() {
		return paymentValidateId;
	}

	public void setPaymentValidateId(Integer paymentValidateId) {
		this.paymentValidateId = paymentValidateId;
	}

	public Integer getBillerServiceId() {
		return billerServiceId;
	}

	public void setBillerServiceId(Integer billerServiceId) {
		this.billerServiceId = billerServiceId;
	}

	public String getPaymentPresentFlag() {
		return paymentPresentFlag;
	}

	public void setPaymentPresentFlag(String paymentPresentFlag) {
		this.paymentPresentFlag = paymentPresentFlag;
	}

	public String getPaymentPartialFlag() {
		return paymentPartialFlag;
	}

	public void setPaymentPartialFlag(String paymentPartialFlag) {
		this.paymentPartialFlag = paymentPartialFlag;
	}

	public String getAmtAvaliableFlag() {
		return amtAvaliableFlag;
	}

	public void setAmtAvaliableFlag(String amtAvaliableFlag) {
		this.amtAvaliableFlag = amtAvaliableFlag;
	}

	public String getPaymentOverFlag() {
		return paymentOverFlag;
	}

	public void setPaymentOverFlag(String paymentOverFlag) {
		this.paymentOverFlag = paymentOverFlag;
	}

	public String getOverdueFlag() {
		return overdueFlag;
	}

	public void setOverdueFlag(String overdueFlag) {
		this.overdueFlag = overdueFlag;
	}

	public BigDecimal getPaymentMin() {
		return paymentMin;
	}

	public void setPaymentMin(BigDecimal paymentMin) {
		this.paymentMin = paymentMin;
	}

	public BigDecimal getPaymentMax() {
		return paymentMax;
	}

	public void setPaymentMax(BigDecimal paymentMax) {
		this.paymentMax = paymentMax;
	}

	public String getNonFundamoFeeFlag() {
		return nonFundamoFeeFlag;
	}

	public void setNonFundamoFeeFlag(String nonFundamoFeeFlag) {
		this.nonFundamoFeeFlag = nonFundamoFeeFlag;
	}

	public String getComNonFundamoFeeFlag() {
		return comNonFundamoFeeFlag;
	}

	public void setComNonFundamoFeeFlag(String comNonFundamoFeeFlag) {
		this.comNonFundamoFeeFlag = comNonFundamoFeeFlag;
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

	public String getDenominateFlag() {
		return denominateFlag;
	}

	public void setDenominateFlag(String denominateFlag) {
		this.denominateFlag = denominateFlag;
	}

	public Integer getDenoMaxMonth() {
		return denoMaxMonth;
	}

	public void setDenoMaxMonth(Integer denoMaxMonth) {
		this.denoMaxMonth = denoMaxMonth;
	}



}
