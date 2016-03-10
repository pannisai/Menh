package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.BillerCategory;

import com.dtac.billerweb.common.BaseForm;

public class BW1110Form extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5119668917722919603L;
	private Integer billerCategoryId;
	private String billerCategoryName;
	private String billerCategoryDesc;
	private Integer categoryMenuSeq;
	private String activeFlag;
	private Integer activeFlagIndex;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	
	public BillerCategory toBillerCategory(){
		BillerCategory billerCategory=new BillerCategory();
		billerCategory.setACT_FLAG(this.activeFlag);
		billerCategory.setBLLR_CATG_DESC(this.billerCategoryDesc);
		billerCategory.setBLLR_CATG_ID(this.billerCategoryId);
		billerCategory.setBLLR_CATG_MENU_SEQ(this.categoryMenuSeq);
		billerCategory.setBLLR_CATG_NAME(this.billerCategoryName);
		billerCategory.setCRTD_BY(this.createBy);
		billerCategory.setCRTD_DTTM(this.createDate);
		billerCategory.setLAST_CHNG_BY(this.updateBy);
		billerCategory.setLAST_CHNG_DTTM(this.updateDate);
		return billerCategory;
		
	}
	public Integer getBillerCategoryId() {
		return billerCategoryId;
	}

	public void setBillerCategoryId(Integer billerCategoryId) {
		this.billerCategoryId = billerCategoryId;
	}

	public String getBillerCategoryName() {
		return billerCategoryName;
	}

	public void setBillerCategoryName(String billerCategoryName) {
		this.billerCategoryName = billerCategoryName;
	}

	public String getBillerCategoryDesc() {
		return billerCategoryDesc;
	}

	public void setBillerCategoryDesc(String billerCategoryDesc) {
		this.billerCategoryDesc = billerCategoryDesc;
	}

	public Integer getCategoryMenuSeq() {
		return categoryMenuSeq;
	}

	public void setCategoryMenuSeq(Integer categoryMenuSeq) {
		this.categoryMenuSeq = categoryMenuSeq;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Integer getActiveFlagIndex() {
		return activeFlagIndex;
	}

	public void setActiveFlagIndex(Integer activeFlagIndex) {
		this.activeFlagIndex = activeFlagIndex;
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
