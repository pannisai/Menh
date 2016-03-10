package com.dtac.billerweb.data;

import mfs.biller.persistence.bean.BillerBarcode;
import mfs.biller.persistence.bean.BillerBarcodeDetail;

import com.dtac.billerweb.common.BaseDO;

public class BillerBarcodeDetailSO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7899789643607716820L;
	private Integer inputId;
	private String labelTH;
	private String labelEng;
	private Integer barcodeId;
	private String barCodeName;
	private Integer barcodeRefId;
	private Integer line;
	private String partialFlag;
	private Integer start;
	private Integer length;
	private String removeChar;
	private String activeFlag;
	public BillerBarcodeDetailSO toBillerBarcodeDetailSO(BillerBarcodeDetail billerBarDetail) {
		this.inputId = billerBarDetail.getREF_ID();
		this.labelTH = billerBarDetail.getREF_LABL_TH();
		this.labelEng = billerBarDetail.getREF_LABL_EN();
		this.barcodeId = billerBarDetail.getBARC_ID();
		this.barCodeName = billerBarDetail.getBARC_NAME();
		this.barcodeRefId = billerBarDetail.getBARC_REF_ID();
		this.line = billerBarDetail.getBARC_LINE_ID();
		this.partialFlag = billerBarDetail.getBARC_PART_FLAG();
		this.start=billerBarDetail.getBARC_PART_STAT();
		this.length=billerBarDetail.getBARC_PART_LENT();
		this.removeChar=billerBarDetail.getBARC_REMV_CHAR();
		this.activeFlag=billerBarDetail.getACT_FLAG();
		return this;
	}
	public Integer getInputId() {
		return inputId;
	}
	public void setInputId(Integer inputId) {
		this.inputId = inputId;
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
	public Integer getBarcodeId() {
		return barcodeId;
	}
	public void setBarcodeId(Integer barcodeId) {
		this.barcodeId = barcodeId;
	}
	public String getBarCodeName() {
		return barCodeName;
	}
	public void setBarCodeName(String barCodeName) {
		this.barCodeName = barCodeName;
	}
	public Integer getBarcodeRefId() {
		return barcodeRefId;
	}
	public void setBarcodeRefId(Integer barcodeRefId) {
		this.barcodeRefId = barcodeRefId;
	}
	public Integer getLine() {
		return line;
	}
	public void setLine(Integer line) {
		this.line = line;
	}
	public String getPartialFlag() {
		return partialFlag;
	}
	public void setPartialFlag(String partialFlag) {
		this.partialFlag = partialFlag;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public String getRemoveChar() {
		return removeChar;
	}
	public void setRemoveChar(String removeChar) {
		this.removeChar = removeChar;
	}
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
}
