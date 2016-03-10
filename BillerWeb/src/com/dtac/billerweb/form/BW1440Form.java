package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.BillerRef;

import com.dtac.billerweb.common.BaseForm;

public class BW1440Form extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4574726690964863742L;
	private Integer formId;
	private Integer billerServiceId;
	private Integer inputId;
	private Integer inputSeqId;
	private String labelTH;
	private String labelEng;
	private String inputType;
	private String primaryFlag;
	private String dataType;
	private String duplicationFlag;
	private String dataFormat;
	private Integer minimumLength;
	private Integer maximumLength;
	private String activeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private String hiddenFlag;
	private String allowKeyInFlag;
	private String blindingFormat;
	private String searchFlag;
	private String enableDefaultValueFlag;
	private String defaultblankvalue;
	private String smsFlag;

	public BillerRef toBillerRef() {
		BillerRef billerRef = new BillerRef();
		billerRef.setBLLR_FORM_ID(this.formId);
		billerRef.setBLLR_SRVC_ID(this.billerServiceId);
		billerRef.setREF_ID(this.inputId);
		billerRef.setREF_SEQ(this.inputSeqId);
		billerRef.setREF_LABL_TH(this.labelTH);
		billerRef.setREF_LABL_EN(this.labelEng);
		billerRef.setREF_TYPE(this.inputType);
		billerRef.setREF_PRIM_FLAG(this.primaryFlag);
		billerRef.setREF_DATA_FOMT(this.dataFormat);
		billerRef.setREF_DATA_TYPE(this.dataType);
		billerRef.setREF_DUP_FLAG(this.duplicationFlag);
		billerRef.setREF_VALE_LENT_MIN(this.minimumLength);
		billerRef.setREF_VALE_LENT_MAX(this.maximumLength);
		billerRef.setACT_FLAG(this.activeFlag);
		billerRef.setCRTD_BY(this.createBy);
		billerRef.setCRTD_DTTM(this.createDate);
		billerRef.setLAST_CHNG_BY(this.updateBy);
		billerRef.setLAST_CHNG_DTTM(this.updateDate);		
		billerRef.setREF_HIDN_FLAG(this.hiddenFlag);
		billerRef.setREF_ALLW_KEY_IN(this.allowKeyInFlag);	
		billerRef.setREF_SRCH_FLAG(this.searchFlag);
		billerRef.setENBL_DFLT_VALU(this.enableDefaultValueFlag);
		billerRef.setDFLT_BLNK_VALU(this.defaultblankvalue);
		billerRef.setREF_SMS_FLAG(this.smsFlag);
		billerRef.setREF_BLIND_FOMT(this.blindingFormat);
		return billerRef;
	}

	public BW1440Form toBW1440Form(BillerRef billerRef) {
		this.formId = billerRef.getBLLR_FORM_ID();
		this.billerServiceId = billerRef.getBLLR_SRVC_ID();
		this.inputId = billerRef.getREF_ID();
		this.inputSeqId = billerRef.getREF_SEQ();
		this.labelTH = billerRef.getREF_LABL_TH();
		this.labelEng = billerRef.getREF_LABL_EN();
		this.inputType = billerRef.getREF_TYPE();
		this.primaryFlag=billerRef.getREF_PRIM_FLAG();
		this.dataFormat=billerRef.getREF_DATA_FOMT();
		this.dataType=billerRef.getREF_DATA_TYPE();
		this.duplicationFlag=billerRef.getREF_DUP_FLAG();
		this.minimumLength=billerRef.getREF_VALE_LENT_MIN();
		this.maximumLength=billerRef.getREF_VALE_LENT_MAX();
		this.activeFlag = billerRef.getACT_FLAG();
		this.createBy = billerRef.getCRTD_BY();
		this.createDate = billerRef.getCRTD_DTTM();
		this.updateBy = billerRef.getLAST_CHNG_BY();
		this.updateDate = billerRef.getLAST_CHNG_DTTM();	
		this.hiddenFlag = billerRef.getREF_HIDN_FLAG();
		this.allowKeyInFlag = billerRef.getREF_ALLW_KEY_IN();
		this.blindingFormat = billerRef.getREF_BLIND_FOMT();
		this.searchFlag = billerRef.getREF_SRCH_FLAG();
		this.enableDefaultValueFlag = billerRef.getENBL_DFLT_VALU();
		this.defaultblankvalue = billerRef.getDFLT_BLNK_VALU();
		this.smsFlag = billerRef.getREF_SMS_FLAG();
		return this;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Integer getBillerServiceId() {
		return billerServiceId;
	}

	public void setBillerServiceId(Integer billerServiceId) {
		this.billerServiceId = billerServiceId;
	}

	public Integer getInputId() {
		return inputId;
	}

	public void setInputId(Integer inputId) {
		this.inputId = inputId;
	}

	public Integer getInputSeqId() {
		return inputSeqId;
	}

	public void setInputSeqId(Integer inputSeqId) {
		this.inputSeqId = inputSeqId;
	}

	public String getLabelTH() {
		return labelTH;
	}

	public void setLabelTH(String labelTH) {
		this.labelTH = labelTH;
	}

	public String getLabelEng() {
		return labelEng;
	}

	public void setLabelEng(String labelEng) {
		this.labelEng = labelEng;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getPrimaryFlag() {
		return primaryFlag;
	}

	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDuplicationFlag() {
		return duplicationFlag;
	}

	public void setDuplicationFlag(String duplicationFlag) {
		this.duplicationFlag = duplicationFlag;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public Integer getMinimumLength() {
		return minimumLength;
	}

	public void setMinimumLength(Integer minimumLength) {
		this.minimumLength = minimumLength;
	}

	public Integer getMaximumLength() {
		return maximumLength;
	}

	public void setMaximumLength(Integer maximumLength) {
		this.maximumLength = maximumLength;
	}

	public String getHiddenFlag() {
		return hiddenFlag;
	}

	public void setHiddenFlag(String hiddenFlag) {
		this.hiddenFlag = hiddenFlag;
	}

	public String getAllowKeyInFlag() {
		return allowKeyInFlag;
	}

	public void setAllowKeyInFlag(String allowKeyInFlag) {
		this.allowKeyInFlag = allowKeyInFlag;
	}

	public String getBlindingFormat() {
		return blindingFormat;
	}

	public void setBlindingFormat(String blindingFormat) {
		this.blindingFormat = blindingFormat;
	}

	public String getSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}

	public String getEnableDefaultValueFlag() {
		return enableDefaultValueFlag;
	}

	public void setEnableDefaultValueFlag(String enableDefaultValueFlag) {
		this.enableDefaultValueFlag = enableDefaultValueFlag;
	}

	public String getDefaultblankvalue() {
		return defaultblankvalue;
	}

	public void setDefaultblankvalue(String defaultblankvalue) {
		this.defaultblankvalue = defaultblankvalue;
	}

	public String getSmsFlag() {
		return smsFlag;
	}

	public void setSmsFlag(String smsFlag) {
		this.smsFlag = smsFlag;
	}
}
