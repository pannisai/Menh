package com.dtac.billerweb.form;

import org.zkoss.zul.Listitem;

import com.dtac.billerweb.common.BaseForm;

public class BW4200Form extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1190043837681203618L;
	private Listitem bankChannel;
	private Listitem status;
	public Listitem getBankChannel() {
		return bankChannel;
	}
	public void setBankChannel(Listitem bankChannel) {
		this.bankChannel = bankChannel;
	}
	public Listitem getStatus() {
		return status;
	}
	public void setStatus(Listitem status) {
		this.status = status;
	}
}
