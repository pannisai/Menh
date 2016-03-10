package com.dtac.billerweb.form;

import org.zkoss.zul.Listitem;

import com.dtac.billerweb.common.BaseForm;

public class BW2100Form extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1692673256485812650L;
	private Listitem function;
	private Listitem status;
	private Listitem billerServiceCode;
	
	public Listitem getBillerServiceCode() {
		return billerServiceCode;
	}
	public void setBillerServiceCode(Listitem billerServiceCode) {
		this.billerServiceCode = billerServiceCode;
	}
	public Listitem getFunction() {
		return function;
	}
	public void setFunction(Listitem function) {
		this.function = function;
	}
	public Listitem getStatus() {
		return status;
	}
	public void setStatus(Listitem status) {
		this.status = status;
	}
}
