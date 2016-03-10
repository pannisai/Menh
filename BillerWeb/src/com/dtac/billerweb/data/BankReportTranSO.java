package com.dtac.billerweb.data;

import java.math.BigDecimal;
import java.util.Date;

import mfs.biller.persistence.bean.BankReportTransDetail;

import com.dtac.billerweb.common.BaseDO;

public class BankReportTranSO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6873937559990014289L;
	private String tranID;
	private Date tranDate;
	private String bankCode;
	private String bankName;
	private String bankService;
	private BigDecimal amount;
	private String accountTo;
	private String accountFrom;
	private String accountName;
	private String bankBanceCode;
	private String tranStatus;
	private Date updateDate;
	private Date effectDate;
	private Date tranferDate;
	private String bankRef;
	private String bankReturnCode;
	private String bankReturnName;
	private String reversalStatus;
	private Date reversalDate;
	
	public BankReportTranSO toBankReportTranSO(BankReportTransDetail bankReportTranDetail) {
		this.tranID = bankReportTranDetail.getTRNS_ID();
		this.tranDate=bankReportTranDetail.getTRNS_DTTM();
		this.bankCode=bankReportTranDetail.getTRNS_BANK_CODE();
		this.bankName=bankReportTranDetail.getBANK_NAME();
		this.bankService=bankReportTranDetail.getBANK_SRVC_NAME();
		this.amount=bankReportTranDetail.getTRNS_TNSF_AMNT();
		this.accountTo=bankReportTranDetail.getTRNS_ACCT_TO();
		this.accountFrom=bankReportTranDetail.getTRNS_ACCT_FROM();
		this.accountName=bankReportTranDetail.getTRNS_ACCT_HOLD_NAME();
		this.bankBanceCode=bankReportTranDetail.getTRNS_BANK_BRNC_CODE();
		this.tranStatus=bankReportTranDetail.getBANK_MSGE_CODE_MSGE_EN();
		this.tranferDate=bankReportTranDetail.getTRNS_TNSF_DTTM();
		this.updateDate = bankReportTranDetail.getLAST_CHNG_DTTM();
		this.effectDate=bankReportTranDetail.getTRNS_EFCT_DTTM();
		this.bankRef=bankReportTranDetail.getTRNS_BANK_REF();
		this.bankReturnCode=bankReportTranDetail.getTRNS_BANK_STTS_CODE();
		this.bankReturnName=bankReportTranDetail.getTRNS_BANK_STTS_MSG();
		this.reversalStatus=bankReportTranDetail.getTRNS_RVSL_STTS_CODE();
		this.reversalDate=bankReportTranDetail.getTRNS_RVSL_DTTM();
		return this;
	}

	public String getTranID() {
		return tranID;
	}

	public void setTranID(String tranID) {
		this.tranID = tranID;
	}

	public Date getTranDate() {
		return tranDate;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
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

	public String getBankService() {
		return bankService;
	}

	public void setBankService(String bankService) {
		this.bankService = bankService;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(String accountTo) {
		this.accountTo = accountTo;
	}

	public String getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(String accountFrom) {
		this.accountFrom = accountFrom;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankBanceCode() {
		return bankBanceCode;
	}

	public void setBankBanceCode(String bankBanceCode) {
		this.bankBanceCode = bankBanceCode;
	}

	public String getTranStatus() {
		return tranStatus;
	}

	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public Date getTranferDate() {
		return tranferDate;
	}

	public void setTranferDate(Date tranferDate) {
		this.tranferDate = tranferDate;
	}

	public String getBankRef() {
		return bankRef;
	}

	public void setBankRef(String bankRef) {
		this.bankRef = bankRef;
	}

	public String getBankReturnCode() {
		return bankReturnCode;
	}

	public void setBankReturnCode(String bankReturnCode) {
		this.bankReturnCode = bankReturnCode;
	}

	public String getBankReturnName() {
		return bankReturnName;
	}

	public void setBankReturnName(String bankReturnName) {
		this.bankReturnName = bankReturnName;
	}

	public String getReversalStatus() {
		return reversalStatus;
	}

	public void setReversalStatus(String reversalStatus) {
		this.reversalStatus = reversalStatus;
	}

	public Date getReversalDate() {
		return reversalDate;
	}

	public void setReversalDate(Date reversalDate) {
		this.reversalDate = reversalDate;
	}

	
	
}

