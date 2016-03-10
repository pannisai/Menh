package com.dtac.billerweb.form;

import org.zkoss.zul.Listitem;

import com.dtac.billerweb.common.BaseForm;

public class BW1600Form extends BaseForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Listitem billerShortCode;
	private Listitem bankName;
	private Listitem status;
	
	public Listitem getBillerShortCode() {
		return billerShortCode;
	}
	public void setBillerShortCode(Listitem billerShortCode) {
		this.billerShortCode = billerShortCode;
	}
	public Listitem getStatus() {
		return status;
	}
	public void setStatus(Listitem status) {
		this.status = status;
	}
	public Listitem getBankName() {
		return bankName;
	}
	public void setBankName(Listitem bankName) {
		this.bankName = bankName;
	}
}
