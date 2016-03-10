package com.dtac.billerweb.data;

import java.util.Date;

import mfs.biller.persistence.bean.BillerCategory;

import com.dtac.billerweb.common.BaseDO;

public class BillerCategorySO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7323137468968548017L;
	private Integer billerCategoryId;
	private Integer categoryMenuSeq;
	private String billerCategoryName;
	private String billerCategoryDesc;
	private String activeFlag;
	private String updateBy;
	private Date updateDate;

	public BillerCategorySO toBillerCategorySO(BillerCategory billerCategory) {
		
		this.billerCategoryId = billerCategory.getBLLR_CATG_ID();
		this.categoryMenuSeq = billerCategory.getBLLR_CATG_MENU_SEQ();
		this.billerCategoryName = billerCategory.getBLLR_CATG_NAME();
		this.billerCategoryDesc = billerCategory.getBLLR_CATG_DESC();
		this.activeFlag = billerCategory.getACT_FLAG();
		this.updateBy = billerCategory.getLAST_CHNG_BY();
		this.updateDate = billerCategory.getLAST_CHNG_DTTM();
		return this;
	}

	public Integer getBillerCategoryId() {
		return billerCategoryId;
	}

	public void setBillerCategoryId(Integer billerCategoryId) {
		this.billerCategoryId = billerCategoryId;
	}

	public Integer getCategoryMenuSeq() {
		return categoryMenuSeq;
	}

	public void setCategoryMenuSeq(Integer categoryMenuSeq) {
		this.categoryMenuSeq = categoryMenuSeq;
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

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
}
