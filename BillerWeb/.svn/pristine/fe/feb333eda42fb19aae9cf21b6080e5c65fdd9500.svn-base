package com.dtac.billerweb.form;

import java.math.BigDecimal;
import java.util.Date;

import mfs.biller.persistence.bean.BillerService;

import com.dtac.billerweb.common.BaseForm;

public class BW1411Form extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5846953264089727231L;
	private Integer billerServiceId;
	private Integer billerCategoryId;
	private String serviceCode;//Change Biller Short Code
	private Integer billerMasterId;//Change to ERP Supplier Code
	private String feature;
	private Integer integrationId;
	private String serviceNameEng;
	private String serviceFullNameEng;
	private String serviceNameTH;
	private String serviceFullNameTH;
	private Date serviceActiveDate;
	private Date serviceExpireDate;
	private Integer serviceMenuSeq;
	private String reqGuestMobileNoFlag;
	private String scanBarcodeFlag;
	private BigDecimal vat;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private Integer billerFeature;
	private String billerOnlineCancelFlag;

	public BillerService toBillerService() {
		BillerService billerService = new BillerService();
		billerService.setBLLR_SRVC_ID(this.billerServiceId);
		billerService.setBLLR_CATG_ID(this.billerCategoryId);
		billerService.setBLLR_SRVC_CODE(this.serviceCode);
		billerService.setBLLR_MSTR_ID(this.billerMasterId);
		billerService.setBLLR_SRVC_FETR(this.feature);
		billerService.setBLLR_INGT_ID(this.integrationId);
		billerService.setBLLR_SRVC_NAME_EN(this.serviceNameEng);
		billerService.setBLLR_SRVC_NAME_FULL_EN(this.serviceFullNameEng);
		billerService.setBLLR_SRVC_NAME_TH(this.serviceNameTH);
		billerService.setBLLR_SRVC_NAME_FULL_TH(this.serviceFullNameTH);
		billerService.setBLLR_SRVC_STAT_DATE(this.serviceActiveDate);
		billerService.setBLLR_SRVC_EXPR_DATE(this.serviceExpireDate);
		billerService.setBLLR_SRVC_MENU_SEQ(this.serviceMenuSeq);
		billerService.setBLLR_SRVC_GUST_MOBN_FLAG(this.reqGuestMobileNoFlag);
		billerService.setBLLR_SRVC_BARC_FLAG(this.scanBarcodeFlag);
		billerService.setBLLR_SERV_VAT_PERC(this.getVat());
		billerService.setACT_FLAG("A");
		billerService.setCRTD_BY(this.createBy);
		billerService.setCRTD_DTTM(this.createDate);
		billerService.setLAST_CHNG_BY(this.updateBy);
		billerService.setLAST_CHNG_DTTM(this.updateDate);
		billerService.setBLLR_FETR_ID(this.billerFeature);
		billerService.setBLLR_CNCL_ONLN_FLAG(this.billerOnlineCancelFlag);
		return billerService;
	}

	public BW1411Form toBW1411Form(BillerService billerService) {
		this.billerServiceId = billerService.getBLLR_SRVC_ID();
		this.billerCategoryId = billerService.getBLLR_CATG_ID();
		this.serviceCode = billerService.getBLLR_SRVC_CODE();
		this.billerMasterId = billerService.getBLLR_MSTR_ID();
		this.feature=billerService.getBLLR_SRVC_FETR();
		this.integrationId=billerService.getBLLR_INGT_ID();
		this.serviceNameEng = billerService.getBLLR_SRVC_NAME_EN();
		this.serviceFullNameEng = billerService.getBLLR_SRVC_NAME_FULL_EN();
		this.serviceNameTH = billerService.getBLLR_SRVC_NAME_TH();
		this.serviceFullNameTH = billerService.getBLLR_SRVC_NAME_FULL_TH();
		this.serviceActiveDate = billerService.getBLLR_SRVC_STAT_DATE();
		this.serviceExpireDate = billerService.getBLLR_SRVC_EXPR_DATE();
		this.serviceMenuSeq = billerService.getBLLR_SRVC_MENU_SEQ();
		this.reqGuestMobileNoFlag = billerService.getBLLR_SRVC_GUST_MOBN_FLAG();
		this.scanBarcodeFlag = billerService.getBLLR_SRVC_BARC_FLAG();
		this.vat = billerService.getBLLR_SERV_VAT_PERC();
		this.createBy = billerService.getCRTD_BY();
		this.createDate = billerService.getCRTD_DTTM();
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

	public Integer getBillerCategoryId() {
		return billerCategoryId;
	}

	public void setBillerCategoryId(Integer billerCategoryId) {
		this.billerCategoryId = billerCategoryId;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Integer getBillerMasterId() {
		return billerMasterId;
	}

	public void setBillerMasterId(Integer billerMasterId) {
		this.billerMasterId = billerMasterId;
	}

	public String getServiceNameEng() {
		return serviceNameEng;
	}

	public void setServiceNameEng(String serviceNameEng) {
		this.serviceNameEng = serviceNameEng;
	}

	public String getServiceFullNameEng() {
		return serviceFullNameEng;
	}

	public void setServiceFullNameEng(String serviceFullNameEng) {
		this.serviceFullNameEng = serviceFullNameEng;
	}

	public String getServiceNameTH() {
		return serviceNameTH;
	}

	public void setServiceNameTH(String serviceNameTH) {
		this.serviceNameTH = serviceNameTH;
	}

	public String getServiceFullNameTH() {
		return serviceFullNameTH;
	}

	public void setServiceFullNameTH(String serviceFullNameTH) {
		this.serviceFullNameTH = serviceFullNameTH;
	}

	public Date getServiceActiveDate() {
		return serviceActiveDate;
	}

	public void setServiceActiveDate(Date serviceActiveDate) {
		this.serviceActiveDate = serviceActiveDate;
	}

	public Date getServiceExpireDate() {
		return serviceExpireDate;
	}

	public void setServiceExpireDate(Date serviceExpireDate) {
		this.serviceExpireDate = serviceExpireDate;
	}

	public Integer getServiceMenuSeq() {
		return serviceMenuSeq;
	}

	public void setServiceMenuSeq(Integer serviceMenuSeq) {
		this.serviceMenuSeq = serviceMenuSeq;
	}

	public String getReqGuestMobileNoFlag() {
		return reqGuestMobileNoFlag;
	}

	public void setReqGuestMobileNoFlag(String reqGuestMobileNoFlag) {
		this.reqGuestMobileNoFlag = reqGuestMobileNoFlag;
	}

	public String getScanBarcodeFlag() {
		return scanBarcodeFlag;
	}

	public void setScanBarcodeFlag(String scanBarcodeFlag) {
		this.scanBarcodeFlag = scanBarcodeFlag;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
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

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Integer getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(Integer integrationId) {
		this.integrationId = integrationId;
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

}
