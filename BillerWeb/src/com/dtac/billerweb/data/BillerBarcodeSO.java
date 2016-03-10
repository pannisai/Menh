package com.dtac.billerweb.data;

import java.util.Date;

import mfs.biller.persistence.bean.BillerBarcode;

import com.dtac.billerweb.common.BaseDO;

public class BillerBarcodeSO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 755556020848168227L;
	private Integer barcodeId;
	private String barcodeName;
	private String carriageReturnFlag;
	private String prefixFlag;
	private String prefixValue;
	private Integer lineMaxLength;
	private Integer lineNo;
	private Date effactiveDate;

	public BillerBarcodeSO toBillerBarcodeSO(BillerBarcode billerBarcode) {
		this.barcodeId = billerBarcode.getBARC_ID();
		this.barcodeName = billerBarcode.getBARC_NAME();
		this.carriageReturnFlag = billerBarcode.getBARC_CRRG_RETN_FLAG();
		this.prefixFlag = billerBarcode.getBARC_PERF_FLAG();
		this.prefixValue = billerBarcode.getBARC_PERF_VALUE();
		this.lineMaxLength = billerBarcode.getBARC_LINE_MAX();
		this.lineNo = billerBarcode.getBARC_LINE_NO();
		this.effactiveDate = billerBarcode.getBARC_EFFT_DATE();
		return this;
	}

	public Integer getBarcodeId() {
		return barcodeId;
	}

	public void setBarcodeId(Integer barcodeId) {
		this.barcodeId = barcodeId;
	}

	public String getBarcodeName() {
		return barcodeName;
	}

	public void setBarcodeName(String barcodeName) {
		this.barcodeName = barcodeName;
	}

	public String getCarriageReturnFlag() {
		return carriageReturnFlag;
	}

	public void setCarriageReturnFlag(String carriageReturnFlag) {
		this.carriageReturnFlag = carriageReturnFlag;
	}

	public String getPrefixFlag() {
		return prefixFlag;
	}

	public void setPrefixFlag(String prefixFlag) {
		this.prefixFlag = prefixFlag;
	}

	public String getPrefixValue() {
		return prefixValue;
	}

	public void setPrefixValue(String prefixValue) {
		this.prefixValue = prefixValue;
	}

	public Integer getLineMaxLength() {
		return lineMaxLength;
	}

	public void setLineMaxLength(Integer lineMaxLength) {
		this.lineMaxLength = lineMaxLength;
	}

	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	public Date getEffactiveDate() {
		return effactiveDate;
	}

	public void setEffactiveDate(Date effactiveDate) {
		this.effactiveDate = effactiveDate;
	}
}
