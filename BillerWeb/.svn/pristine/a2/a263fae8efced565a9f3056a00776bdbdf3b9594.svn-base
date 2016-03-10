package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.BillerBarcodeRef;
import mfs.biller.persistence.bean.BillerRefDetail;

import com.dtac.billerweb.common.BaseForm;

public class BW1450Form extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6428992807308933409L;
	private Integer inputId;
	private String formName;
	private String labelTH;
	private String labelEng;
	private Integer barcodeRefId;
	private Integer barcodeLineId;
	private Integer barcodeTypeId;
	private String removeChar;
	private String partialFlag;
	private String activeFlag;
	private Integer partialStart;
	private Integer partialLength;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public BillerBarcodeRef toBillerBarcodeRef() {
		BillerBarcodeRef billerBarcodeRef = new BillerBarcodeRef();
		billerBarcodeRef.setREF_ID(this.inputId);
		billerBarcodeRef.setBARC_REF_ID(this.barcodeRefId);
		billerBarcodeRef.setBARC_LINE_ID(this.barcodeLineId);
		billerBarcodeRef.setBARC_ID(this.barcodeTypeId);
		billerBarcodeRef.setBARC_REMV_CHAR(this.removeChar);
		billerBarcodeRef.setBARC_PART_FLAG(this.partialFlag);
		billerBarcodeRef.setBARC_PART_STAT(this.partialStart);
		billerBarcodeRef.setBARC_PART_LENT(this.partialLength);
		billerBarcodeRef.setACT_FLAG(this.activeFlag);
		billerBarcodeRef.setCRTD_BY(this.createBy);
		billerBarcodeRef.setCRTD_DTTM(this.createDate);
		billerBarcodeRef.setLAST_CHNG_BY(this.updateBy);
		billerBarcodeRef.setLAST_CHNG_DTTM(this.updateDate);
		return billerBarcodeRef;
	}

	public BW1450Form toBW1450Form(BillerRefDetail billerRefDetail, BillerBarcodeRef billerBarcodeRef) {
		this.inputId = billerRefDetail.getREF_ID();
		this.formName = billerRefDetail.getBLLR_FORM_NAME();
		this.labelTH = billerRefDetail.getREF_LABL_TH();
		this.labelEng = billerRefDetail.getREF_LABL_EN();
		this.barcodeRefId = billerBarcodeRef.getBARC_REF_ID();
		this.barcodeLineId = billerBarcodeRef.getBARC_LINE_ID();
		this.barcodeTypeId = billerBarcodeRef.getBARC_ID();
		this.removeChar = billerBarcodeRef.getBARC_REMV_CHAR();
		this.partialFlag = billerBarcodeRef.getBARC_PART_FLAG();
		this.partialStart = billerBarcodeRef.getBARC_PART_STAT();
		this.partialLength = billerBarcodeRef.getBARC_PART_LENT();
		this.activeFlag = billerBarcodeRef.getACT_FLAG();
		this.createBy = billerBarcodeRef.getCRTD_BY();
		this.createDate = billerBarcodeRef.getCRTD_DTTM();
		this.updateBy = billerBarcodeRef.getLAST_CHNG_BY();
		this.updateDate = billerBarcodeRef.getLAST_CHNG_DTTM();
		return this;
	}

	public Integer getInputId() {
		return inputId;
	}

	public void setInputId(Integer inputId) {
		this.inputId = inputId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
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

	public Integer getBarcodeRefId() {
		return barcodeRefId;
	}

	public void setBarcodeRefId(Integer barcodeRefId) {
		this.barcodeRefId = barcodeRefId;
	}

	public Integer getBarcodeLineId() {
		return barcodeLineId;
	}

	public void setBarcodeLineId(Integer barcodeLineId) {
		this.barcodeLineId = barcodeLineId;
	}

	public Integer getBarcodeTypeId() {
		return barcodeTypeId;
	}

	public void setBarcodeTypeId(Integer barcodeTypeId) {
		this.barcodeTypeId = barcodeTypeId;
	}

	public String getRemoveChar() {
		return removeChar;
	}

	public void setRemoveChar(String removeChar) {
		this.removeChar = removeChar;
	}

	public String getPartialFlag() {
		return partialFlag;
	}

	public void setPartialFlag(String partialFlag) {
		this.partialFlag = partialFlag;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Integer getPartialStart() {
		return partialStart;
	}

	public void setPartialStart(Integer partialStart) {
		this.partialStart = partialStart;
	}

	public Integer getPartialLength() {
		return partialLength;
	}

	public void setPartialLength(Integer partialLength) {
		this.partialLength = partialLength;
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

}
