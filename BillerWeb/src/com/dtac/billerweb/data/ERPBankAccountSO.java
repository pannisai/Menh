package com.dtac.billerweb.data;

import java.util.Date;

import mfs.biller.persistence.bean.BillerChannel;
import mfs.biller.persistence.bean.ERPBankAccountDtail;

import com.dtac.billerweb.common.BaseDO;

public class ERPBankAccountSO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer accountId;
	private Integer bankAccountSeq;
	private String billerShortCode;
	private String billerShortName;
	private String bankAccount;
	private String bankAccountName;
	private String bankName;
	private String bankBranchName;
	private String activeFlag;
	private String updateBy;
	private Date updateDate;
	
	public ERPBankAccountSO toERPBankAccountSO(ERPBankAccountDtail erpBankAccDtail) {
		this.accountId = erpBankAccDtail.getBLLR_SRVC_FIN_ID();
		this.bankAccountSeq = erpBankAccDtail.getBLLR_ACCT_SEQ();
		this.billerShortCode = erpBankAccDtail.getBLLR_SRVC_CODE();
		this.billerShortName=erpBankAccDtail.getBLLR_SRVC_NAME_EN();
		this.bankAccount=erpBankAccDtail.getBLLR_ACCT_NO();
		this.bankAccountName=erpBankAccDtail.getBLLR_ACCT_NAME();
		this.bankName=erpBankAccDtail.getBANK_NAME();
		this.bankBranchName=erpBankAccDtail.getBLLR_ACCT_BRAC_NAME();
		this.activeFlag = erpBankAccDtail.getACT_FLAG();
		this.updateBy = erpBankAccDtail.getLAST_CHNG_BY();
		this.updateDate = erpBankAccDtail.getLAST_CHNG_DTTM();
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

	public String getBillerShortCode() {
		return billerShortCode;
	}

	public void setBillerShortCode(String billerShortCode) {
		this.billerShortCode = billerShortCode;
	}

	public String getBillerShortName() {
		return billerShortName;
	}

	public void setBillerShortName(String billerShortName) {
		this.billerShortName = billerShortName;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
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
