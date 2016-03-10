package com.dtac.billerweb.data;

import java.util.Date;

import mfs.biller.persistence.bean.BankChannelBean;
import mfs.biller.persistence.bean.BankMasterBean;

import com.dtac.billerweb.common.BaseDO;

public class BankChannelSO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 134309075674462813L;
	private String bankChanCode;
	private String bankChanName;
	private String bankChanNameEn;
	private String bankChanNameTh;
	private String activeFlag;
	private String updateBy;
	private Date updateDate;
	
	public BankChannelSO toBankChannelSO(BankChannelBean bankChannelBean) {
		this.bankChanCode = bankChannelBean.getBANK_CHNL_CODE();
		this.bankChanName = bankChannelBean.getBANK_CHNL_NAME();
		this.bankChanNameEn = bankChannelBean.getBANK_CHNL_FULL_NAME_EN();
		this.bankChanNameTh = bankChannelBean.getBANK_CHNL_FULL_NAME_TH();
		this.activeFlag = bankChannelBean.getACT_FLAG();
		this.updateBy = bankChannelBean.getLAST_CHNG_BY();
		this.updateDate = bankChannelBean.getLAST_CHNG_DTTM();
		return this;
	}

	public String getBankChanCode() {
		return bankChanCode;
	}
	public void setBankChanCode(String bankChanCode) {
		this.bankChanCode = bankChanCode;
	}
	public String getBankChanName() {
		return bankChanName;
	}
	public void setBankChanName(String bankChanName) {
		this.bankChanName = bankChanName;
	}
	public String getBankChanNameEn() {
		return bankChanNameEn;
	}
	public void setBankChanNameEn(String bankChanNameEn) {
		this.bankChanNameEn = bankChanNameEn;
	}
	public String getBankChanNameTh() {
		return bankChanNameTh;
	}
	public void setBankChanNameTh(String bankChanNameTh) {
		this.bankChanNameTh = bankChanNameTh;
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
