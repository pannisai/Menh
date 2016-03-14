package com.dtac.billerweb.form;

import java.util.Date;

//import mfs.biller.ejb.interfaces.BillerMasterBeanLocal;
import mfs.biller.persistence.bean.BankMasterBean;

import com.dtac.billerweb.common.BaseForm;

public class BW4110Form extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -83236923341082676L;
	private String bankCode;
	private String bankName;
	private String bankNameEn;
	private String bankNameTh;
	private String activeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public BankMasterBean toBankMaster() {
		BankMasterBean bankMasterBean = new BankMasterBean();
		bankMasterBean.setBANK_CODE(this.bankCode);
		bankMasterBean.setBANK_NAME(this.bankName);
		bankMasterBean.setBANK_FULL_NAME_EN(this.bankNameEn);
		bankMasterBean.setBANK_FULL_NAME_TH(this.bankNameTh);
		bankMasterBean.setACT_FLAG(this.activeFlag);
		bankMasterBean.setCRTD_BY(this.createBy);
		bankMasterBean.setCRTD_DTTM(this.createDate);
		bankMasterBean.setLAST_CHNG_BY(this.updateBy);
		bankMasterBean.setLAST_CHNG_DTTM(this.updateDate);
		return bankMasterBean;

	}
	
	public BW4110Form toBW4110Form(BankMasterBean bankMasterBean){
		this.bankCode=bankMasterBean.getBANK_CODE();
		this.bankName=bankMasterBean.getBANK_NAME();
		this.bankNameEn=bankMasterBean.getBANK_FULL_NAME_EN();
		this.bankNameTh=bankMasterBean.getBANK_FULL_NAME_TH();
		this.activeFlag=bankMasterBean.getACT_FLAG();
		this.updateBy=bankMasterBean.getLAST_CHNG_BY();
		this.updateDate=bankMasterBean.getLAST_CHNG_DTTM();
		return this;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNameEn() {
		return bankNameEn;
	}

	public void setBankNameEn(String bankNameEn) {
		this.bankNameEn = bankNameEn;
	}

	public String getBankNameTh() {
		return bankNameTh;
	}

	public void setBankNameTh(String bankNameTh) {
		this.bankNameTh = bankNameTh;
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
}
