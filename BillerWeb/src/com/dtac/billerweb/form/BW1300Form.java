package com.dtac.billerweb.form;

import org.zkoss.zul.Listitem;

import com.dtac.billerweb.common.BaseForm;

public class BW1300Form extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2388351107554408045L;
	private Listitem billerChannel;
	private Listitem status;
	public Listitem getBillerChannel() {
		return billerChannel;
	}
	public void setBillerChannel(Listitem billerChannel) {
		this.billerChannel = billerChannel;
	}
	public Listitem getStatus() {
		return status;
	}
	public void setStatus(Listitem status) {
		this.status = status;
	}
}
