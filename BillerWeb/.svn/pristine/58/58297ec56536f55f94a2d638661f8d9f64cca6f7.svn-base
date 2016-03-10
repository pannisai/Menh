package com.dtac.billerweb.data;

import java.math.BigDecimal;
import java.util.Date;

import mfs.biller.persistence.bean.BatchMastFile;
import mfs.biller.persistence.bean.GWMasterTrans;

import com.dtac.billerweb.common.BaseDO;
import com.dtac.billerweb.common.BaseForm;

public class BillerInquiryDO extends BaseDO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4621669961352051165L;
	private String transId;
	private Date transDate;
	private String ref1;
	private String ref2;
	private String ref3;
	private String ref4;
	private String ref5;
	private BigDecimal paidAmount;
	private String custNameTH;
	private BigDecimal CRNCBLNC;
	private String extraAmount;
	private String serviceCode;
	
	
	
	public BillerInquiryDO toBillerInquiryForm(GWMasterTrans gwMasterTran){
		this.transId=gwMasterTran.getTRNS_ID();
		this.transDate=gwMasterTran.getTRNS_DTTM();
		this.ref1=gwMasterTran.getTRNS_REF1();
		this.ref2=gwMasterTran.getTRNS_REF2();
		this.ref3=gwMasterTran.getTRNS_REF3();
		this.ref4=gwMasterTran.getTRNS_REF4();
		this.ref5=gwMasterTran.getTRNS_REF5();		
		this.paidAmount=gwMasterTran.getTRNS_PAID_AMNT();
		this.custNameTH=gwMasterTran.getTRNS_CUST_NAME_TH();		
		this.CRNCBLNC=gwMasterTran.getTRNS_CRNT_BLNC();
		this.extraAmount=gwMasterTran.getTRNS_EXTR_AMNT();	
		this.serviceCode=gwMasterTran.getTRNS_SRVC_CODE();
		return this;
	}




	public String getTransId() {
		return transId;
	}



	public void setTransId(String transId) {
		this.transId = transId;
	}



	public Date getTransDate() {
		return transDate;
	}



	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}



	public String getRef1() {
		return ref1;
	}



	public void setRef1(String ref1) {
		this.ref1 = ref1;
	}



	public String getRef2() {
		return ref2;
	}



	public void setRef2(String ref2) {
		this.ref2 = ref2;
	}



	public String getRef3() {
		return ref3;
	}



	public void setRef3(String ref3) {
		this.ref3 = ref3;
	}



	public String getRef4() {
		return ref4;
	}



	public void setRef4(String ref4) {
		this.ref4 = ref4;
	}



	public String getRef5() {
		return ref5;
	}



	public void setRef5(String ref5) {
		this.ref5 = ref5;
	}



	public BigDecimal getPaidAmount() {
		return paidAmount;
	}



	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}



	public String getCustNameTH() {
		return custNameTH;
	}



	public void setCustNameTH(String custNameTH) {
		this.custNameTH = custNameTH;
	}



	public BigDecimal getCRNCBLNC() {
		return CRNCBLNC;
	}



	public void setCRNCBLNC(BigDecimal cRNCBLNC) {
		CRNCBLNC = cRNCBLNC;
	}



	public String getExtraAmount() {
		return extraAmount;
	}



	public void setExtraAmount(String extraAmount) {
		this.extraAmount = extraAmount;
	}



	public String getServiceCode() {
		return serviceCode;
	}



	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	

}
