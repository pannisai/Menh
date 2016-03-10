package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.BankChannelBean;
import mfs.biller.persistence.bean.BankMasterBean;

import com.dtac.billerweb.common.BaseForm;

public class BW4210Form extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 118330757174219209L;
	private String bankChanCode;
	private String bankChanName;
	private String bankChanNameEn;
	private String bankChanNameTh;
	private String activeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	
	public BankChannelBean toBankChannel() {
		BankChannelBean bankChannelBean = new BankChannelBean();
		bankChannelBean.setBANK_CHNL_CODE(this.bankChanCode);
		bankChannelBean.setBANK_CHNL_NAME(this.bankChanName);
		bankChannelBean.setBANK_CHNL_FULL_NAME_EN(this.bankChanNameEn);
		bankChannelBean.setBANK_CHNL_FULL_NAME_TH(this.bankChanNameTh);
		bankChannelBean.setACT_FLAG(this.activeFlag);
		bankChannelBean.setCRTD_BY(this.createBy);
		bankChannelBean.setCRTD_DTTM(this.createDate);
		bankChannelBean.setLAST_CHNG_BY(this.updateBy);
		bankChannelBean.setLAST_CHNG_DTTM(this.updateDate);
		return bankChannelBean;

	}
	
	public BW4210Form toBW4210Form(BankChannelBean bankChannelBean){
		this.bankChanCode=bankChannelBean.getBANK_CHNL_CODE();
		this.bankChanName=bankChannelBean.getBANK_CHNL_NAME();
		this.bankChanNameEn=bankChannelBean.getBANK_CHNL_FULL_NAME_EN();
		this.bankChanNameTh=bankChannelBean.getBANK_CHNL_FULL_NAME_TH();
		this.activeFlag=bankChannelBean.getACT_FLAG();
		this.updateBy=bankChannelBean.getLAST_CHNG_BY();
		this.updateDate=bankChannelBean.getLAST_CHNG_DTTM();
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
