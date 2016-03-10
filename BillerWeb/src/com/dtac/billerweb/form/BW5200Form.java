package com.dtac.billerweb.form;

import org.zkoss.zul.Listitem;

import com.dtac.billerweb.common.BaseForm;

public class BW5200Form extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6144576595325116553L;
	private Listitem bankService;
	private Listitem status;
	public Listitem getBankService() {
		return bankService;
	}
	public void setBankService(Listitem bankService) {
		this.bankService = bankService;
	}
	public Listitem getStatus() {
		return status;
	}
	public void setStatus(Listitem status) {
		this.status = status;
	}
}
