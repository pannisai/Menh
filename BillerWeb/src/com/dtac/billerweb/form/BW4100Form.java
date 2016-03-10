package com.dtac.billerweb.form;

import org.zkoss.zul.Listitem;

import com.dtac.billerweb.common.BaseForm;

public class BW4100Form extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 990818954856656181L;
	private Listitem bankCode;
	private Listitem status;
	
	public Listitem getStatus() {
		return status;
	}
	public void setStatus(Listitem status) {
		this.status = status;
	}
	public Listitem getBankCode() {
		return bankCode;
	}
	public void setBankCode(Listitem bankCode) {
		this.bankCode = bankCode;
	}
}
