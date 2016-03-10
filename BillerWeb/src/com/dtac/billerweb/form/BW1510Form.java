package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.BillerBarcode;

import com.dtac.billerweb.common.BaseForm;

public class BW1510Form extends BaseForm {

	private static final long serialVersionUID = 1L;

	private Integer BARC_ID;
	private String BARC_NAME;
	private String BARC_CRRG_RETN_FLAG;
	private String BARC_PERF_FLAG;
	private String BARC_PERF_VALUE;
	private Integer BARC_LINE_MAX;
	private String barcodeType;
	private String ACT_FLAG;
	private Date BARC_EFFT_DATE;
	private Integer BARC_LINE_NO;
	private Integer billServiceId;
	private String barcNewLinePost;
	private String barcServiceCode;
	private String CRTD_BY;
	private Date CRTD_DTTM;
	private String LAST_CHNG_BY;
	private Date LAST_CHNG_DTTM;

	private Integer ACT_FLAG_INDEX;
	private Integer BARC_CRRG_RETN_FLAG_INDEX;
	private Integer BARC_PERF_FLAG_INDEX;
	private Date BARC_EXPR_DATE;

	public BillerBarcode toBillerBarcode() {
		BillerBarcode bean = new BillerBarcode();

		bean.setBARC_ID(this.BARC_ID);
		bean.setBARC_NAME(this.BARC_NAME);
		bean.setBARC_CRRG_RETN_FLAG(this.BARC_CRRG_RETN_FLAG);
		bean.setBARC_PERF_FLAG(this.BARC_PERF_FLAG);
		bean.setBARC_PERF_VALUE(this.BARC_PERF_VALUE);
		bean.setBARC_LINE_MAX(this.BARC_LINE_MAX);
		bean.setBARC_TYPE(this.barcodeType);
		bean.setACT_FLAG(this.ACT_FLAG);
		bean.setBARC_EFFT_DATE(this.BARC_EFFT_DATE);
		bean.setBARC_LINE_NO(this.BARC_LINE_NO);
		bean.setBLLR_SRVC_ID(this.billServiceId);
		bean.setBARC_NEW_LINE_POST(this.barcNewLinePost);
		bean.setBARC_SRVC_CODE(this.barcServiceCode);
		bean.setCRTD_BY(this.CRTD_BY);
		bean.setCRTD_DTTM(this.CRTD_DTTM);
		bean.setLAST_CHNG_BY(this.LAST_CHNG_BY);
		bean.setLAST_CHNG_DTTM(this.LAST_CHNG_DTTM);
		bean.setBARC_EXPR_DATE(this.BARC_EXPR_DATE);
		return bean;
	}

	public Integer getBARC_ID() {
		return BARC_ID;
	}

	public void setBARC_ID(Integer bARC_ID) {
		BARC_ID = bARC_ID;
	}

	public String getBARC_NAME() {
		return BARC_NAME;
	}

	public void setBARC_NAME(String bARC_NAME) {
		BARC_NAME = bARC_NAME;
	}

	public String getBARC_CRRG_RETN_FLAG() {
		return BARC_CRRG_RETN_FLAG;
	}

	public void setBARC_CRRG_RETN_FLAG(String bARC_CRRG_RETN_FLAG) {
		BARC_CRRG_RETN_FLAG = bARC_CRRG_RETN_FLAG;
	}

	public String getBARC_PERF_FLAG() {
		return BARC_PERF_FLAG;
	}

	public void setBARC_PERF_FLAG(String bARC_PERF_FLAG) {
		BARC_PERF_FLAG = bARC_PERF_FLAG;
	}

	public String getBARC_PERF_VALUE() {
		return BARC_PERF_VALUE;
	}

	public void setBARC_PERF_VALUE(String bARC_PERF_VALUE) {
		BARC_PERF_VALUE = bARC_PERF_VALUE;
	}

	public Integer getBARC_LINE_MAX() {
		return BARC_LINE_MAX;
	}

	public void setBARC_LINE_MAX(Integer bARC_LINE_MAX) {
		BARC_LINE_MAX = bARC_LINE_MAX;
	}

	public String getACT_FLAG() {
		return ACT_FLAG;
	}

	public void setACT_FLAG(String aCT_FLAG) {
		ACT_FLAG = aCT_FLAG;
	}

	public Date getBARC_EFFT_DATE() {
		return BARC_EFFT_DATE;
	}

	public void setBARC_EFFT_DATE(Date bARC_EFFT_DATE) {
		BARC_EFFT_DATE = bARC_EFFT_DATE;
	}

	public Integer getBARC_LINE_NO() {
		return BARC_LINE_NO;
	}

	public void setBARC_LINE_NO(Integer bARC_LINE_NO) {
		BARC_LINE_NO = bARC_LINE_NO;
	}

	public String getCRTD_BY() {
		return CRTD_BY;
	}

	public void setCRTD_BY(String cRTD_BY) {
		CRTD_BY = cRTD_BY;
	}

	public Date getCRTD_DTTM() {
		return CRTD_DTTM;
	}

	public void setCRTD_DTTM(Date cRTD_DTTM) {
		CRTD_DTTM = cRTD_DTTM;
	}

	public String getLAST_CHNG_BY() {
		return LAST_CHNG_BY;
	}

	public void setLAST_CHNG_BY(String lAST_CHNG_BY) {
		LAST_CHNG_BY = lAST_CHNG_BY;
	}

	public Date getLAST_CHNG_DTTM() {
		return LAST_CHNG_DTTM;
	}

	public void setLAST_CHNG_DTTM(Date lAST_CHNG_DTTM) {
		LAST_CHNG_DTTM = lAST_CHNG_DTTM;
	}

	public Integer getACT_FLAG_INDEX() {
		return ACT_FLAG_INDEX;
	}

	public void setACT_FLAG_INDEX(Integer aCT_FLAG_INDEX) {
		ACT_FLAG_INDEX = aCT_FLAG_INDEX;
	}

	public Integer getBARC_CRRG_RETN_FLAG_INDEX() {
		return BARC_CRRG_RETN_FLAG_INDEX;
	}

	public void setBARC_CRRG_RETN_FLAG_INDEX(Integer bARC_CRRG_RETN_FLAG_INDEX) {
		BARC_CRRG_RETN_FLAG_INDEX = bARC_CRRG_RETN_FLAG_INDEX;
	}

	public Integer getBARC_PERF_FLAG_INDEX() {
		return BARC_PERF_FLAG_INDEX;
	}

	public void setBARC_PERF_FLAG_INDEX(Integer bARC_PERF_FLAG_INDEX) {
		BARC_PERF_FLAG_INDEX = bARC_PERF_FLAG_INDEX;
	}

	public String getBarcodeType() {
		return barcodeType;
	}

	public void setBarcodeType(String barcodeType) {
		this.barcodeType = barcodeType;
	}

	public Integer getBillServiceId() {
		return billServiceId;
	}

	public void setBillServiceId(Integer billServiceId) {
		this.billServiceId = billServiceId;
	}

	public String getBarcNewLinePost() {
		return barcNewLinePost;
	}

	public void setBarcNewLinePost(String barcNewLinePost) {
		this.barcNewLinePost = barcNewLinePost;
	}

	public String getBarcServiceCode() {
		return barcServiceCode;
	}

	public void setBarcServiceCode(String barcServiceCode) {
		this.barcServiceCode = barcServiceCode;
	}

	public Date getBARC_EXPR_DATE() {
		return BARC_EXPR_DATE;
	}

	public void setBARC_EXPR_DATE(Date bARC_EXPR_DATE) {
		BARC_EXPR_DATE = bARC_EXPR_DATE;
	}

}
