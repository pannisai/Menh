package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.ERPBankAccount;

import com.dtac.billerweb.common.BaseForm;

public class BW1610Form extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID =  1L;
	
	private Integer accountId;
	private Integer bankAccountSeq;
	private Integer billerShotCode;
	private String bankAccount;
	private String bankAccountName;
	private String bankCode;
	private String bankBranchName;
	private String bankAccountType;
	private String erpSuppCode;
	private String erpSuppSitCode;
	private String erpLineSuppCode;
	private String erpLineSuppSitCode;
	private String erpCustomerCode;
	private String erpCustBillToCode;
	private String erpCustShipToCode;
	private Date activeDate;
	private Date expireDate;
	private String activeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	
	public ERPBankAccount toERPBankAccount() {
		ERPBankAccount erpBankAccount = new ERPBankAccount();
		erpBankAccount.setBLLR_SRVC_FIN_ID(this.accountId);
		erpBankAccount.setBLLR_SRVC_ID(this.billerShotCode);
		erpBankAccount.setBLLR_ACCT_BANK_CODE(this.bankCode);
		erpBankAccount.setBLLR_ACCT_SEQ(this.bankAccountSeq);
		erpBankAccount.setBLLR_ACCT_NO(this.bankAccount);
		erpBankAccount.setBLLR_ACCT_NAME(this.bankAccountName);
		erpBankAccount.setBLLR_ACCT_PROD_TYPE(this.bankAccountType);
		erpBankAccount.setBLLR_ACCT_BRAC_NAME(this.bankBranchName);
		erpBankAccount.setBLLR_ERP_SUP_CODE(this.erpSuppCode);
		erpBankAccount.setBLLR_ERP_SUP_SITE_CODE(this.erpSuppSitCode);
		erpBankAccount.setBLLR_ERP_LINE_SUP_NUMB(this.erpLineSuppCode);
		erpBankAccount.setBLLR_ERP_LINE_SUP_SITE_CODE(this.erpLineSuppSitCode);
		erpBankAccount.setBLLR_ERP_CUST_CODE(this.erpCustomerCode);
		erpBankAccount.setBLLR_ERP_CUST_BILL_TO_CODE(this.erpCustBillToCode);
		erpBankAccount.setBLLR_ERP_CUST_SHIP_TO_CODE(this.erpCustShipToCode);
		erpBankAccount.setBLLR_ACCT_STRT_DTTM(this.activeDate);
		erpBankAccount.setBLLR_ACCT_STOP_DTTM(this.expireDate);
		erpBankAccount.setACT_FLAG(this.activeFlag);
		erpBankAccount.setCRTD_BY(this.createBy);
		erpBankAccount.setCRTD_DTTM(this.createDate);
		erpBankAccount.setLAST_CHNG_BY(this.updateBy);
		erpBankAccount.setLAST_CHNG_DTTM(this.updateDate);
		return erpBankAccount;
	}

	public BW1610Form toBW1610Form(ERPBankAccount erpBankAccount) {
		this.accountId = erpBankAccount.getBLLR_SRVC_FIN_ID();
		this.billerShotCode = erpBankAccount.getBLLR_SRVC_ID();
		this.bankCode = erpBankAccount.getBLLR_ACCT_BANK_CODE();
		this.bankAccountSeq = erpBankAccount.getBLLR_ACCT_SEQ();
		this.bankAccount = erpBankAccount.getBLLR_ACCT_NO();
		this.bankAccountName = erpBankAccount.getBLLR_ACCT_NAME();
		this.bankAccountType=erpBankAccount.getBLLR_ACCT_PROD_TYPE();
		this.bankBranchName=erpBankAccount.getBLLR_ACCT_BRAC_NAME();
		this.erpSuppCode=erpBankAccount.getBLLR_ERP_SUP_CODE();
		this.erpSuppSitCode=erpBankAccount.getBLLR_ERP_SUP_SITE_CODE();
		this.erpLineSuppCode=erpBankAccount.getBLLR_ERP_LINE_SUP_NUMB();
		this.erpLineSuppSitCode=erpBankAccount.getBLLR_ERP_LINE_SUP_SITE_CODE();
		this.erpCustomerCode=erpBankAccount.getBLLR_ERP_CUST_CODE();
		this.erpCustBillToCode=erpBankAccount.getBLLR_ERP_CUST_BILL_TO_CODE();
		this.erpCustShipToCode=erpBankAccount.getBLLR_ERP_CUST_SHIP_TO_CODE();
		this.activeDate=erpBankAccount.getBLLR_ACCT_STRT_DTTM();
		this.expireDate=erpBankAccount.getBLLR_ACCT_STOP_DTTM();
		this.activeFlag = erpBankAccount.getACT_FLAG();
		this.createBy = erpBankAccount.getCRTD_BY();
		this.createDate = erpBankAccount.getCRTD_DTTM();
		this.updateBy = erpBankAccount.getLAST_CHNG_BY();
		this.updateDate = erpBankAccount.getLAST_CHNG_DTTM();
		return this;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getBankAccountSeq() {
		return bankAccountSeq;
	}

	public void setBankAccountSeq(Integer bankAccountSeq) {
		this.bankAccountSeq = bankAccountSeq;
	}

	public Integer getBillerShotCode() {
		return billerShotCode;
	}

	public void setBillerShotCode(Integer billerShotCode) {
		this.billerShotCode = billerShotCode;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
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

	public String getErpSuppCode() {
		return erpSuppCode;
	}

	public void setErpSuppCode(String erpSuppCode) {
		this.erpSuppCode = erpSuppCode;
	}

	public String getErpSuppSitCode() {
		return erpSuppSitCode;
	}

	public void setErpSuppSitCode(String erpSuppSitCode) {
		this.erpSuppSitCode = erpSuppSitCode;
	}

	public String getErpLineSuppCode() {
		return erpLineSuppCode;
	}

	public void setErpLineSuppCode(String erpLineSuppCode) {
		this.erpLineSuppCode = erpLineSuppCode;
	}

	public String getErpLineSuppSitCode() {
		return erpLineSuppSitCode;
	}

	public void setErpLineSuppSitCode(String erpLineSuppSitCode) {
		this.erpLineSuppSitCode = erpLineSuppSitCode;
	}

	public String getErpCustomerCode() {
		return erpCustomerCode;
	}

	public void setErpCustomerCode(String erpCustomerCode) {
		this.erpCustomerCode = erpCustomerCode;
	}

	public String getErpCustBillToCode() {
		return erpCustBillToCode;
	}

	public void setErpCustBillToCode(String erpCustBillToCode) {
		this.erpCustBillToCode = erpCustBillToCode;
	}

	public String getErpCustShipToCode() {
		return erpCustShipToCode;
	}

	public void setErpCustShipToCode(String erpCustShipToCode) {
		this.erpCustShipToCode = erpCustShipToCode;
	}
}
