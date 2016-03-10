package com.dtac.billerweb.data;

import java.util.Date;

import mfs.biller.persistence.bean.BankMasterBean;
import mfs.biller.persistence.bean.BillerChannel;

import com.dtac.billerweb.common.BaseDO;

public class BankMasterSO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6102567812762510651L;
	private String bankCode;
	private String bankName;
	private String bankNameEn;
	private String bankNameTh;
	private String activeFlag;
	private String updateBy;
	private Date updateDate;

	public BankMasterSO toBankMasterSO(BankMasterBean bankMasterBean) {
		this.bankCode = bankMasterBean.getBANK_CODE();
		this.bankName = bankMasterBean.getBANK_NAME();
		this.bankNameEn = bankMasterBean.getBANK_FULL_NAME_EN();
		this.bankNameTh = bankMasterBean.getBANK_FULL_NAME_TH();
		this.activeFlag = bankMasterBean.getACT_FLAG();
		this.updateBy = bankMasterBean.getLAST_CHNG_BY();
		this.updateDate = bankMasterBean.getLAST_CHNG_DTTM();
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
