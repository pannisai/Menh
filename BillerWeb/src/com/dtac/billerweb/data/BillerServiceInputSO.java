package com.dtac.billerweb.data;

import mfs.biller.persistence.bean.BillerRef;

import com.dtac.billerweb.common.BaseDO;

public class BillerServiceInputSO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4100070103297672986L;
	private Integer formId;
	private Integer billerServiceId;
	private Integer inputId;
	private Integer inputSeq;
	private String labelThai;
	private String labelEng;
	private String primaryFlag;
	private String requireFlag;
	private String hiddenFlag;
	private String type;
	private String defaultValue;
	private Integer minLenght;
	private Integer maxLength;
	private String dataType;
	private String dataFormat;
	private String duplicationFlag;
	private String activeFlag;	
	private String allowKeyInFlag;
	private String blindingFormat;
	private String searchFlag;
	private String enableDefaultValueFlag;
	private String defaultblankvalue;
	private String smsFlag;

	public BillerServiceInputSO toBillerServiceInputSO(BillerRef billerRef) {
		this.formId = billerRef.getBLLR_FORM_ID();
		this.billerServiceId = billerRef.getBLLR_SRVC_ID();
		this.inputId = billerRef.getREF_ID();
		this.inputSeq = billerRef.getREF_SEQ();
		this.labelThai = billerRef.getREF_LABL_TH();
		this.labelEng = billerRef.getREF_LABL_EN();
		this.primaryFlag=billerRef.getREF_PRIM_FLAG();
		this.requireFlag = billerRef.getREF_REQU_FLAG();
		this.hiddenFlag = billerRef.getREF_HIDN_FLAG();
		this.type = billerRef.getREF_TYPE();
		this.defaultValue = billerRef.getREF_DEFT_VALE();
		this.minLenght = billerRef.getREF_VALE_LENT_MIN();
		this.maxLength = billerRef.getREF_VALE_LENT_MAX();
		this.dataFormat=billerRef.getREF_DATA_FOMT();
		this.dataType=billerRef.getREF_DATA_TYPE();
		this.duplicationFlag=billerRef.getREF_DUP_FLAG();
		this.activeFlag = billerRef.getACT_FLAG();		
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

	public Integer getInputSeq() {
		return inputSeq;
	}

	public void setInputSeq(Integer inputSeq) {
		this.inputSeq = inputSeq;
	}

	public String getLabelThai() {
		return labelThai;
	}

	public void setLabelThai(String labelThai) {
		this.labelThai = labelThai;
	}

	public String getLabelEng() {
		return labelEng;
	}

	public void setLabelEng(String labelEng) {
		this.labelEng = labelEng;
	}

	public String getRequireFlag() {
		return requireFlag;
	}

	public void setRequireFlag(String requireFlag) {
		this.requireFlag = requireFlag;
	}

	public String getHiddenFlag() {
		return hiddenFlag;
	}

	public void setHiddenFlag(String hiddenFlag) {
		this.hiddenFlag = hiddenFlag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getMinLenght() {
		return minLenght;
	}

	public void setMinLenght(Integer minLenght) {
		this.minLenght = minLenght;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
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

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getDuplicationFlag() {
		return duplicationFlag;
	}

	public void setDuplicationFlag(String duplicationFlag) {
		this.duplicationFlag = duplicationFlag;
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
