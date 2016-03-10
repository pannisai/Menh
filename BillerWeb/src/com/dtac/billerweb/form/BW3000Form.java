package com.dtac.billerweb.form;

import java.util.Date;

import org.zkoss.zul.Listitem;

import com.dtac.billerweb.common.BaseForm;

public class BW3000Form extends BaseForm{

	private static final long serialVersionUID = 1L;
	
	private Date FROM_DATE;
	private Date TO_DATE;
	private Date FROM_TIME;
	private Date TO_TIME;
	private Listitem billerServiceCode;
	private Listitem billerChannelCode;
	private String TRNS_ID;
	private Listitem billerFdmCode;
	private Listitem billerPymtCode;
	
	public Date getFROM_DATE() {
		return FROM_DATE;
	}
	public void setFROM_DATE(Date fROM_DATE) {
		FROM_DATE = fROM_DATE;
	}
	public Date getTO_DATE() {
		return TO_DATE;
	}
	public void setTO_DATE(Date tO_DATE) {
		TO_DATE = tO_DATE;
	}
	public Date getFROM_TIME() {
		return FROM_TIME;
	}
	public void setFROM_TIME(Date fROM_TIME) {
		FROM_TIME = fROM_TIME;
	}
	public Date getTO_TIME() {
		return TO_TIME;
	}
	public void setTO_TIME(Date tO_TIME) {
		TO_TIME = tO_TIME;
	}
	public Listitem getBillerServiceCode() {
		return billerServiceCode;
	}
	public void setBillerServiceCode(Listitem billerServiceCode) {
		this.billerServiceCode = billerServiceCode;
	}
	public Listitem getBillerChannelCode() {
		return billerChannelCode;
	}
	public void setBillerChannelCode(Listitem billerChannelCode) {
		this.billerChannelCode = billerChannelCode;
	}
	public String getTRNS_ID() {
		return TRNS_ID;
	}
	public void setTRNS_ID(String tRNS_ID) {
		TRNS_ID = tRNS_ID;
	}
	public Listitem getBillerFdmCode() {
		return billerFdmCode;
	}
	public void setBillerFdmCode(Listitem billerFdmCode) {
		this.billerFdmCode = billerFdmCode;
	}
	public Listitem getBillerPymtCode() {
		return billerPymtCode;
	}
	public void setBillerPymtCode(Listitem billerPymtCode) {
		this.billerPymtCode = billerPymtCode;
	}
	
}
