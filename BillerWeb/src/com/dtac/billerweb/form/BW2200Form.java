package com.dtac.billerweb.form;

import org.zkoss.zul.Listitem;

import com.dtac.billerweb.common.BaseForm;

public class BW2200Form extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1314086067842518656L;
	private Listitem billerService;
	private Listitem outboundId;
	private Listitem status;
	public Listitem getBillerService() {
		return billerService;
	}
	public void setBillerService(Listitem billerService) {
		this.billerService = billerService;
	}
	public Listitem getOutboundId() {
		return outboundId;
	}
	public void setOutboundId(Listitem outboundId) {
		this.outboundId = outboundId;
	}
	public Listitem getStatus() {
		return status;
	}
	public void setStatus(Listitem status) {
		this.status = status;
	}
}
