package com.dtac.billerweb.data;

import java.util.Date;

import mfs.biller.persistence.bean.BankChannelBean;
import mfs.biller.persistence.bean.BankServiceDetail;
import mfs.biller.persistence.bean.BankServicebean;

import com.dtac.billerweb.common.BaseDO;
import com.dtac.billerweb.util.AppUtil;

public class BankServiceSO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3031655996211998898L;
	private Integer bankServiceId;
	private String bankServiceIdDisplay;
	private String bankServiceCode;
	private String bankServiceType;
	private String bankServiceName;
	private String bankCode;
	private String bankName;
	private String bankCutoffTime;
	private String activeFlag;
	private String updateBy;
	private Date updateDate;

	public BankServiceSO toBankServiceSO(BankServiceDetail bankServiceDetail) {
		this.bankServiceId = bankServiceDetail.getBANK_SRVC_ID();
		this.bankServiceIdDisplay = AppUtil.addPadding(AppUtil.toString(this.bankServiceId), "0", 4);
		this.bankServiceCode = bankServiceDetail.getBANK_SRVC_CODE();
		this.bankServiceType = bankServiceDetail.getFDM_MTI_CODE();
		this.bankServiceName = bankServiceDetail.getBANK_SRVC_NAME();
		this.bankCode = bankServiceDetail.getBANK_CODE();
		this.bankName = bankServiceDetail.getBANK_NAME();

		String bankCutOffTimeStr = "";
		if (!AppUtil.isEmpty(bankServiceDetail.getBANK_CUTF_TIME())) {
			bankCutOffTimeStr = bankServiceDetail.getBANK_CUTF_TIME().toString();
			bankCutOffTimeStr = AppUtil.addPadding(bankCutOffTimeStr, "0", 4);
			bankCutOffTimeStr = bankCutOffTimeStr.substring(0, 2) + ":" + bankCutOffTimeStr.subSequence(2, 4);
		}
		this.bankCutoffTime = bankCutOffTimeStr;
		this.activeFlag = bankServiceDetail.getACT_FLAG();
		this.updateBy = bankServiceDetail.getLAST_CHNG_BY();
		this.updateDate = bankServiceDetail.getLAST_CHNG_DTTM();
		bankCutOffTimeStr = null;
		return this;
	}

	public Integer getBankServiceId() {
		return bankServiceId;
	}

	public void setBankServiceId(Integer bankServiceId) {
		this.bankServiceId = bankServiceId;
	}

	public String getBankServiceIdDisplay() {
		return bankServiceIdDisplay;
	}

	public void setBankServiceIdDisplay(String bankServiceIdDisplay) {
		this.bankServiceIdDisplay = bankServiceIdDisplay;
	}

	public String getBankServiceType() {
		return bankServiceType;
	}

	public void setBankServiceType(String bankServiceType) {
		this.bankServiceType = bankServiceType;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCutoffTime() {
		return bankCutoffTime;
	}

	public void setBankCutoffTime(String bankCutoffTime) {
		this.bankCutoffTime = bankCutoffTime;
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

	public String getBankServiceCode() {
		return bankServiceCode;
	}

	public void setBankServiceCode(String bankServiceCode) {
		this.bankServiceCode = bankServiceCode;
	}
}
