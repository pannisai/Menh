package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.GWOutbound;

import com.dtac.billerweb.common.BaseForm;

public class BW2310Form extends BaseForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8470074044681898732L;
	private Integer outboundId;
	private String outboundName;
	private Integer outboundMapId;
	private String activeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public GWOutbound toGwOutbound() {
		GWOutbound gwOutbound = new GWOutbound();
		gwOutbound.setGW_OUTB_ID(this.outboundId);
		gwOutbound.setGW_OUTB_NAME(this.outboundName);
		gwOutbound.setGW_OUTB_MAP_ID(this.outboundMapId);
		gwOutbound.setACT_FLAG(this.activeFlag);
		gwOutbound.setCRTD_BY(this.createBy);
		gwOutbound.setCRTD_DTTM(this.createDate);
		gwOutbound.setLAST_CHNG_BY(this.updateBy);
		gwOutbound.setLAST_CHNG_DTTM(this.updateDate);
		return gwOutbound;
	}

	public BW2310Form toBW2310Form(GWOutbound gwOutbound) {
		this.outboundId = gwOutbound.getGW_OUTB_ID();
		this.outboundName = gwOutbound.getGW_OUTB_NAME();
		this.outboundMapId = gwOutbound.getGW_OUTB_MAP_ID();
		this.activeFlag = gwOutbound.getACT_FLAG();
		this.createBy = gwOutbound.getCRTD_BY();
		this.createDate = gwOutbound.getCRTD_DTTM();
		this.updateBy = gwOutbound.getLAST_CHNG_BY();
		this.updateDate = gwOutbound.getLAST_CHNG_DTTM();
		return this;
	}

	public Integer getOutboundId() {
		return outboundId;
	}

	public void setOutboundId(Integer outboundId) {
		this.outboundId = outboundId;
	}

	public String getOutboundName() {
		return outboundName;
	}

	public void setOutboundName(String outboundName) {
		this.outboundName = outboundName;
	}

	public Integer getOutboundMapId() {
		return outboundMapId;
	}

	public void setOutboundMapId(Integer outboundMapId) {
		this.outboundMapId = outboundMapId;
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

}
