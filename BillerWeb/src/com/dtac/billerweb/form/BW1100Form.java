package com.dtac.billerweb.form;

import org.zkoss.zul.Listitem;

import com.dtac.billerweb.common.BaseForm;

public class BW1100Form extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7754600918277995621L;
	private Listitem billerCatalogName;
	private Listitem status;
	public Listitem getBillerCatalogName() {
		return billerCatalogName;
	}
	public void setBillerCatalogName(Listitem billerCatalogName) {
		this.billerCatalogName = billerCatalogName;
	}
	public Listitem getStatus() {
		return status;
	}
	public void setStatus(Listitem status) {
		this.status = status;
	}
}
