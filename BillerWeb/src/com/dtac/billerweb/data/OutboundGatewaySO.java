package com.dtac.billerweb.data;

import java.util.Date;

import mfs.biller.persistence.bean.GWOutboundDtail;

import com.dtac.billerweb.common.BaseDO;
import com.dtac.billerweb.util.AppUtil;

public class OutboundGatewaySO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3156732743334543809L;
	private Integer outboundId;
	private String outboundIdDisplay;
	private String outboundName;
	private Integer outboundMapId;
	private String outboundMapName;
	private String activeFlag;
	private String updateBy;
	private Date updateDate;

	public OutboundGatewaySO toOutboundGatewaySO(GWOutboundDtail gwOutbound) {
		this.outboundId = gwOutbound.getGW_OUTB_ID();
		this.outboundIdDisplay = AppUtil.addPadding(this.outboundId + "", "0", 5);
		this.outboundName = gwOutbound.getGW_OUTB_NAME();
		this.outboundMapId = gwOutbound.getGW_OUTB_MAP_ID();
		this.outboundMapName=gwOutbound.getDATA_MAP_NAME();
		this.activeFlag=gwOutbound.getACT_FLAG();
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

	public String getOutboundIdDisplay() {
		return outboundIdDisplay;
	}

	public void setOutboundIdDisplay(String outboundIdDisplay) {
		this.outboundIdDisplay = outboundIdDisplay;
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

	public String getOutboundMapName() {
		return outboundMapName;
	}

	public void setOutboundMapName(String outboundMapName) {
		this.outboundMapName = outboundMapName;
	}
}
