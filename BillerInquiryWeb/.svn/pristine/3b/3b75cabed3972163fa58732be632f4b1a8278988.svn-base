package com.dtac.bmweb.data;

import java.math.BigDecimal;
import java.util.Date;

import mfs.biller.persistence.bean.GWMasterTrans;

import com.dtac.bmweb.common.BaseSO;
import com.dtac.bmweb.util.AppUtil;

public class BillerInquirySO extends BaseSO{
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
	private String serviceName;
	private String transField2;
	private Date crtdDttm;
	private String channel;
	private String agentType;
	private String rcptTotlAmnt;
	private String channelService;
	private String reversalFlag;
	
	public BillerInquirySO toBillerInquiryForm(GWMasterTrans gwMasterTran){				
		this.transId=gwMasterTran.getTRNS_ID();
		this.transDate=gwMasterTran.getTRNS_DTTM();
		this.ref1=isNullStr(gwMasterTran.getTRNS_REF1())?"":gwMasterTran.getTRNS_REF1();
		this.ref2=isNullStr(gwMasterTran.getTRNS_REF2())?"":gwMasterTran.getTRNS_REF2();
		this.ref3=isNullStr(gwMasterTran.getTRNS_REF3())?"":gwMasterTran.getTRNS_REF3();
		this.ref4=isNullStr(gwMasterTran.getTRNS_REF4())?"":gwMasterTran.getTRNS_REF4();
		this.ref5=isNullStr(gwMasterTran.getTRNS_REF5())?"":gwMasterTran.getTRNS_REF5();		
		this.paidAmount=gwMasterTran.getTRNS_PAID_AMNT();
		this.custNameTH=isNullStr(gwMasterTran.getTRNS_CUST_NAME_TH())?"":gwMasterTran.getTRNS_CUST_NAME_TH();		
		this.CRNCBLNC=gwMasterTran.getTRNS_CRNT_BLNC();
		this.extraAmount=gwMasterTran.getTRNS_EXTR_AMNT();	
		this.serviceCode=isNullStr(gwMasterTran.getTRNS_SRVC_CODE())?"":gwMasterTran.getTRNS_SRVC_CODE();
		this.serviceName=isNullStr(gwMasterTran.getBLLR_SRVC_NAME_EN())?"":gwMasterTran.getBLLR_SRVC_NAME_EN();
		this.transField2=isNullStr(gwMasterTran.getTRNS_FIELD2())?"":gwMasterTran.getTRNS_FIELD2();
		this.crtdDttm=gwMasterTran.getCRTD_DTTM();
		this.channel=isNullStr(gwMasterTran.getCHNL_CODE())?"":gwMasterTran.getCHNL_CODE();
		this.agentType=isNullStr(gwMasterTran.getRCPT_CRTD_GRUP_CODE())?"":gwMasterTran.getRCPT_CRTD_GRUP_CODE();
		this.rcptTotlAmnt=gwMasterTran.getRCPT_TOTL_AMNT();
		this.reversalFlag=gwMasterTran.getREV_STATUS();
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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Date getCrtdDttm() {
		return crtdDttm;
	}

	public void setCrtdDttm(Date crtdDttm) {
		this.crtdDttm = crtdDttm;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public String getRcptTotlAmnt() {
		return rcptTotlAmnt;
	}

	public void setRcptTotlAmnt(String rcptTotlAmnt) {
		this.rcptTotlAmnt = rcptTotlAmnt;
	}

	public String getChannelService() {
		return channelService;
	}

	public void setChannelService(String channelService) {
		this.channelService = channelService;
	}

	public String getReversalFlag() {
		return reversalFlag;
	}

	public void setReversalFlag(String reversalFlag) {
		this.reversalFlag = reversalFlag;
	}

	public String getTransField2() {
		return transField2;
	}

	public void setTransField2(String transField2) {
		this.transField2 = transField2;
	}
	
	public static boolean isNullStr(Object object){
	   if(object instanceof String){
	      String objString = (String)object;
	      if(objString.trim().equals("null") )
	        return true;
	    }
	    return false;
	}
}
