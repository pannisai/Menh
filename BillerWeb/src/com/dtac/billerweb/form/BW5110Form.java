package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.BankServicebean;
import mfs.biller.util.DateTimeUtil;

import com.dtac.billerweb.common.BaseForm;
import com.dtac.billerweb.util.AppUtil;

public class BW5110Form extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4045997883918858750L;
	private Integer bankServiceId;
	private String bankServiceCode;
	private String bankServiceName;
	private String bankCode;
	private String bankServiceType;
	private Date bankCutoffTime;
	private String activeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public BankServicebean toBankService() {
		BankServicebean bankServicebean = new BankServicebean();
		bankServicebean.setBANK_SRVC_ID(this.bankServiceId);
		bankServicebean.setBANK_SRVC_CODE(this.bankServiceCode);
		bankServicebean.setBANK_SRVC_NAME(this.bankServiceName);
		bankServicebean.setBANK_CODE(this.bankCode);
		bankServicebean.setFDM_MTI_CODE(this.bankServiceType);
		if (bankCutoffTime != null) {
			String hh = AppUtil.addPadding(AppUtil.toString(bankCutoffTime.getHours()), "0", 2);
			String mm = AppUtil.addPadding(AppUtil.toString(bankCutoffTime.getMinutes()), "0", 2);
			bankServicebean.setBANK_CUTF_TIME(Integer.parseInt((hh + mm)));
			hh = null;
			mm = null;
		}
		bankServicebean.setACT_FLAG(this.activeFlag);
		bankServicebean.setCRTD_BY(this.createBy);
		bankServicebean.setCRTD_DTTM(this.createDate);
		bankServicebean.setLAST_CHNG_BY(this.updateBy);
		bankServicebean.setLAST_CHNG_DTTM(this.updateDate);
		return bankServicebean;
	}

	public BW5110Form toBW5110Form(BankServicebean bankServicebean) {
		this.bankServiceId = bankServicebean.getBANK_SRVC_ID();
		this.bankServiceCode = bankServicebean.getBANK_SRVC_CODE();
		this.bankServiceName = bankServicebean.getBANK_SRVC_NAME();
		this.bankCode = bankServicebean.getBANK_CODE();
		this.bankServiceType = bankServicebean.getFDM_MTI_CODE();
		Integer bankCutoffTimeI = bankServicebean.getBANK_CUTF_TIME();
		if (bankCutoffTimeI != null) {
			String bankCutOffTimeStr = AppUtil.addPadding(bankCutoffTimeI + "", "0", 4);
			int hh = Integer.parseInt(bankCutOffTimeStr.substring(0, 2));
			int mm = Integer.parseInt(bankCutOffTimeStr.substring(2, 4));
			this.bankCutoffTime = DateTimeUtil.getCurrent();
			this.bankCutoffTime.setHours(hh);
			this.bankCutoffTime.setMinutes(mm);
			bankCutOffTimeStr = null;
			bankCutoffTimeI = null;
		}

		this.activeFlag = bankServicebean.getACT_FLAG();
		this.updateBy = bankServicebean.getLAST_CHNG_BY();
		this.updateDate = bankServicebean.getLAST_CHNG_DTTM();
		return this;
	}

	public String getBankServiceCode() {
		return bankServiceCode;
	}

	public void setBankServiceCode(String bankServiceCode) {
		this.bankServiceCode = bankServiceCode;
	}

	public String getBankServiceName() {
		return bankServiceName;
	}

	public void setBankServiceName(String bankServiceName) {
		this.bankServiceName = bankServiceName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Date getBankCutoffTime() {
		return bankCutoffTime;
	}

	public void setBankCutoffTime(Date bankCutoffTime) {
		this.bankCutoffTime = bankCutoffTime;
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

	public Integer getBankServiceId() {
		return bankServiceId;
	}

	public void setBankServiceId(Integer bankServiceId) {
		this.bankServiceId = bankServiceId;
	}

	public String getBankServiceType() {
		return bankServiceType;
	}

	public void setBankServiceType(String bankServiceType) {
		this.bankServiceType = bankServiceType;
	}

}
