package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.BillerMaster;

import com.dtac.billerweb.common.BaseForm;

public class BW1210Form extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5100064924497341515L;

	private Integer billerMasterId;
	private String billerMasterName;
	private String billerMasterNameTH;
	private String billerCode;
	private String billerTaxId;
	private String secondaryBillerTaxId;
	private String companyCode;
	private String activeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public BillerMaster toBillerMaster() {
		BillerMaster billerMaster = new BillerMaster();
		billerMaster.setBLLR_MSTR_ID(this.billerMasterId);
		billerMaster.setBLLR_MSTR_NAME(this.billerMasterName);
		billerMaster.setNAME_THA(this.billerMasterNameTH);
		billerMaster.setBLLR_CODE(this.billerCode);
		billerMaster.setBLLR_TAX_NUMB(this.billerTaxId);
		billerMaster.setBLLR_TAX_NUMB2(this.secondaryBillerTaxId);
		billerMaster.setCOMP_CODE(this.companyCode);
		billerMaster.setACT_FLAG(this.activeFlag);
		billerMaster.setCRTD_BY(this.createBy);
		billerMaster.setCRTD_DTTM(this.createDate);
		billerMaster.setLAST_CHNG_BY(this.updateBy);
		billerMaster.setLAST_CHNG_DTTM(this.updateDate);
		return billerMaster;
	}

	public BW1210Form toBW1210Form(BillerMaster billerMaster) {
		this.billerMasterId = billerMaster.getBLLR_MSTR_ID();
		this.billerMasterName = billerMaster.getBLLR_MSTR_NAME();
		this.billerMasterNameTH = billerMaster.getNAME_THA();
		this.billerCode = billerMaster.getBLLR_CODE();
		this.billerTaxId = billerMaster.getBLLR_TAX_NUMB();
		this.secondaryBillerTaxId = billerMaster.getBLLR_TAX_NUMB2();
		this.companyCode = billerMaster.getCOMP_CODE();
		this.activeFlag = billerMaster.getACT_FLAG();
		this.createBy = billerMaster.getCRTD_BY();
		this.createDate = billerMaster.getCRTD_DTTM();
		this.updateBy = billerMaster.getLAST_CHNG_BY();
		this.updateDate = billerMaster.getLAST_CHNG_DTTM();
		return this;
	}

	public Integer getBillerMasterId() {
		return billerMasterId;
	}

	public void setBillerMasterId(Integer billerMasterId) {
		this.billerMasterId = billerMasterId;
	}

	public String getBillerMasterName() {
		return billerMasterName;
	}

	public void setBillerMasterName(String billerMasterName) {
		this.billerMasterName = billerMasterName;
	}

	public String getBillerMasterNameTH() {
		return billerMasterNameTH;
	}

	public void setBillerMasterNameTH(String billerMasterNameTH) {
		this.billerMasterNameTH = billerMasterNameTH;
	}

	public String getBillerCode() {
		return billerCode;
	}

	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
	}

	public String getBillerTaxId() {
		return billerTaxId;
	}

	public void setBillerTaxId(String billerTaxId) {
		this.billerTaxId = billerTaxId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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

	public String getSecondaryBillerTaxId() {
		return secondaryBillerTaxId;
	}

	public void setSecondaryBillerTaxId(String secondaryBillerTaxId) {
		this.secondaryBillerTaxId = secondaryBillerTaxId;
	}

}
