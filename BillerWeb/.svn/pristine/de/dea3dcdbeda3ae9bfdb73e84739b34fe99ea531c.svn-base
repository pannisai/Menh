package com.dtac.billerweb.data;

import java.util.Date;

import mfs.biller.persistence.bean.BillerServiceDetail;

import com.dtac.billerweb.common.BaseDO;

public class BillerServiceSO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3983695524993658307L;
	private Integer billerServiceId;
	private String billerServiceCode;
	private String billerServiceName;
	private String billerCatName;
	private String billerCode;
	private String billerCatSeq;
	private String billerMenuSeq;
	private String scanBarcodeFlag;
	private String requestGuestMobileFlag;
	private Date activeDate;
	private Date expireDate;
	private String updateBy;
	private Date updateDate;	
	private Integer billerFeature;
	private String billerFeatureStr;
	private String billerOnlineCancelFlag;

	private Boolean offInq = false;
	private Boolean offPay = false;
	private Boolean onInq = false;
	private Boolean onPay = false;
	private Boolean advRec = false;

	public BillerServiceSO toBillerServiceSO(BillerServiceDetail billerService) {
		this.billerServiceId = billerService.getBLLR_SRVC_ID();
		this.billerServiceCode=billerService.getBLLR_SRVC_CODE();
		this.billerServiceName = billerService.getBLLR_SRVC_NAME_EN();
		this.billerCatName = billerService.getBLLR_CATG_NAME();
		this.billerCode = billerService.getBLLR_CODE();
		this.billerCatSeq = billerService.getBLLR_CATG_MENU_SEQ();
		this.billerMenuSeq=billerService.getBLLR_SRVC_MENU_SEQ();
		this.scanBarcodeFlag = billerService.getBLLR_SRVC_BARC_FLAG();
		this.requestGuestMobileFlag = billerService.getBLLR_SRVC_GUST_MOBN_FLAG();
		this.activeDate = billerService.getBLLR_SRVC_STAT_DATE();
		this.expireDate = billerService.getBLLR_SRVC_EXPR_DATE();
		this.updateBy = billerService.getLAST_CHNG_BY();
		this.updateDate = billerService.getLAST_CHNG_DTTM();		
		this.billerFeature = billerService.getBLLR_FETR_ID();
		this.billerOnlineCancelFlag = billerService.getBLLR_CNCL_ONLN_FLAG();
		return this;
	}

	public Integer getBillerServiceId() {
		return billerServiceId;
	}

	public void setBillerServiceId(Integer billerServiceId) {
		this.billerServiceId = billerServiceId;
	}

	public String getBillerServiceName() {
		return billerServiceName;
	}

	public void setBillerServiceName(String billerServiceName) {
		this.billerServiceName = billerServiceName;
	}

	public String getBillerCatName() {
		return billerCatName;
	}

	public void setBillerCatName(String billerCatName) {
		this.billerCatName = billerCatName;
	}

	public String getBillerCode() {
		return billerCode;
	}

	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
	}

	public String getBillerCatSeq() {
		return billerCatSeq;
	}

	public void setBillerCatSeq(String billerCatSeq) {
		this.billerCatSeq = billerCatSeq;
	}

	public String getScanBarcodeFlag() {
		return scanBarcodeFlag;
	}

	public void setScanBarcodeFlag(String scanBarcodeFlag) {
		this.scanBarcodeFlag = scanBarcodeFlag;
	}

	public String getRequestGuestMobileFlag() {
		return requestGuestMobileFlag;
	}

	public void setRequestGuestMobileFlag(String requestGuestMobileFlag) {
		this.requestGuestMobileFlag = requestGuestMobileFlag;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
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

	public String getBillerMenuSeq() {
		return billerMenuSeq;
	}

	public void setBillerMenuSeq(String billerMenuSeq) {
		this.billerMenuSeq = billerMenuSeq;
	}

	public String getBillerServiceCode() {
		return billerServiceCode;
	}

	public void setBillerServiceCode(String billerServiceCode) {
		this.billerServiceCode = billerServiceCode;
	}

	public Integer getBillerFeature() {
		return billerFeature;
	}

	public void setBillerFeature(Integer billerFeature) {
		this.billerFeature = billerFeature;
	}

	public String getBillerOnlineCancelFlag() {
		return billerOnlineCancelFlag;
	}

	public void setBillerOnlineCancelFlag(String billerOnlineCancelFlag) {
		this.billerOnlineCancelFlag = billerOnlineCancelFlag;
	}

	public String getBillerFeatureStr() {
		return billerFeatureStr;
	}

	public void setBillerFeatureStr(String billerFeatureStr) {
		this.billerFeatureStr = billerFeatureStr;
	}

	public Boolean getOffInq() {
		return offInq;
	}

	public void setOffInq(Boolean offInq) {
		this.offInq = offInq;
	}

	public Boolean getOffPay() {
		return offPay;
	}

	public void setOffPay(Boolean offPay) {
		this.offPay = offPay;
	}

	public Boolean getOnInq() {
		return onInq;
	}

	public void setOnInq(Boolean onInq) {
		this.onInq = onInq;
	}

	public Boolean getOnPay() {
		return onPay;
	}

	public void setOnPay(Boolean onPay) {
		this.onPay = onPay;
	}

	public Boolean getAdvRec() {
		return advRec;
	}

	public void setAdvRec(Boolean advRec) {
		this.advRec = advRec;
	}

}
