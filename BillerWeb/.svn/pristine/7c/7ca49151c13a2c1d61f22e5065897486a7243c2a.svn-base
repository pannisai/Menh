package com.dtac.billerweb.data;

import java.util.Date;

import mfs.biller.persistence.bean.BillerMaster;

import com.dtac.billerweb.common.BaseDO;

public class BillerMasterSO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8443051043322100303L;
	private Integer billerId;
	private String billerNameEn;
	private String billerNameTh;
	private String billerCode;
	private String taxId;
	private String secondaryTaxId;
	private String companyCode;
	private String activeFlag;
	private String updateBy;
	private Date updateDate;

	public BillerMasterSO toBillerMasterSO(BillerMaster billerMaster) {
		this.billerId = billerMaster.getBLLR_MSTR_ID();
		this.billerNameEn = billerMaster.getBLLR_MSTR_NAME();
		this.billerNameTh = billerMaster.getNAME_THA();
		this.billerCode = billerMaster.getBLLR_CODE();
		this.taxId=billerMaster.getBLLR_TAX_NUMB();
		this.secondaryTaxId=billerMaster.getBLLR_TAX_NUMB2();
		this.companyCode=billerMaster.getCOMP_CODE();
		this.activeFlag = billerMaster.getACT_FLAG();
		this.updateBy = billerMaster.getLAST_CHNG_BY();
		this.updateDate = billerMaster.getLAST_CHNG_DTTM();
		return this;
	}

	public Integer getBillerId() {
		return billerId;
	}

	public void setBillerId(Integer billerId) {
		this.billerId = billerId;
	}

	public String getBillerNameEn() {
		return billerNameEn;
	}

	public void setBillerNameEn(String billerNameEn) {
		this.billerNameEn = billerNameEn;
	}

	public String getBillerNameTh() {
		return billerNameTh;
	}

	public void setBillerNameTh(String billerNameTh) {
		this.billerNameTh = billerNameTh;
	}

	public String getBillerCode() {
		return billerCode;
	}

	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
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

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getSecondaryTaxId() {
		return secondaryTaxId;
	}

	public void setSecondaryTaxId(String secondaryTaxId) {
		this.secondaryTaxId = secondaryTaxId;
	}
}
