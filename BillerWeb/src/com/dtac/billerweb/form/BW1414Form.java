package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.BillerForm;
import mfs.biller.persistence.bean.BillerServiceChannel;

import com.dtac.billerweb.common.BaseForm;

public class BW1414Form extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -920370583931055446L;
	private Integer formId;
	private Integer billerServiceId;
	private String formName;
	private String formCaptionEng;
	private String formCaptionTH;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public BillerForm toBillerForm() {
		BillerForm billerForm = new BillerForm();
		billerForm.setBLLR_FORM_ID(this.formId);
		billerForm.setBLLR_SRVC_ID(this.billerServiceId);
		billerForm.setBLLR_FORM_NAME(this.formName);
		billerForm.setBLLR_FORM_CAPT_EN(this.formCaptionEng);
		billerForm.setBLLR_FORM_CAPT_TH(this.formCaptionTH);
		billerForm.setCRTD_BY(this.createBy);
		billerForm.setCRTD_DTTM(this.createDate);
		billerForm.setLAST_CHNG_BY(this.updateBy);
		billerForm.setLAST_CHNG_DTTM(this.updateDate);
		return billerForm;
	}

	public BW1414Form toBW1414Form(BillerForm billerForm) {
		this.formId = billerForm.getBLLR_FORM_ID();
		this.billerServiceId = billerForm.getBLLR_SRVC_ID();
		this.formName = billerForm.getBLLR_FORM_NAME();
		this.formCaptionEng = billerForm.getBLLR_FORM_CAPT_EN();
		this.formCaptionTH = billerForm.getBLLR_FORM_CAPT_TH();
		this.createBy = billerForm.getCRTD_BY();
		this.createDate = billerForm.getCRTD_DTTM();
		this.updateBy = billerForm.getLAST_CHNG_BY();
		this.updateDate = billerForm.getLAST_CHNG_DTTM();
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

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormCaptionEng() {
		return formCaptionEng;
	}

	public void setFormCaptionEng(String formCaptionEng) {
		this.formCaptionEng = formCaptionEng;
	}

	public String getFormCaptionTH() {
		return formCaptionTH;
	}

	public void setFormCaptionTH(String formCaptionTH) {
		this.formCaptionTH = formCaptionTH;
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
