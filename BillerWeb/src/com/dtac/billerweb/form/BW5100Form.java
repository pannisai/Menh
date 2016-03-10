package com.dtac.billerweb.form;

import org.zkoss.zul.Listitem;

import com.dtac.billerweb.common.BaseForm;

public class BW5100Form extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3627404345347131039L;
	private Listitem bankService;
	private Listitem bankCode;
	private Listitem status;
	public Listitem getBankService() {
		return bankService;
	}
	public void setBankService(Listitem bankService) {
		this.bankService = bankService;
	}
	public Listitem getBankCode() {
		return bankCode;
	}
	public void setBankCode(Listitem bankCode) {
		this.bankCode = bankCode;
	}
	public Listitem getStatus() {
		return status;
	}
	public void setStatus(Listitem status) {
		this.status = status;
	}
}
