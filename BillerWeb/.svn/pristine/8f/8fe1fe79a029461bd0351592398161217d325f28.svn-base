package com.dtac.billerweb.data;

import java.util.Date;

import mfs.biller.persistence.bean.BankSystemMATNDetail;
import mfs.biller.persistence.bean.GWBankDetail;

import com.dtac.billerweb.common.BaseDO;
import com.dtac.billerweb.util.AppUtil;

public class BankMaintenanceSO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 424258540187399142L;
	private Integer bankMaintenanceId;
	private String bankMaintenanceName;
	private Integer bankServiceId;
	private String bankServiceName;
	private String startTime;
	private String endTime;
	private String activeFlag;
	private String updateBy;
	private Date updateDate;
	
	public BankMaintenanceSO toBankMaintenanceSO(BankSystemMATNDetail bankMaintenance) {
		this.bankMaintenanceId = bankMaintenance.getBANK_SYSM_MATN_ID();
		this.bankMaintenanceName = bankMaintenance.getBANK_SYSM_MATN_NAME();
		this.bankServiceId = bankMaintenance.getBANK_SRVC_ID();
		this.bankServiceName = bankMaintenance.getBANK_SRVC_NAME();
		if (!AppUtil.isEmpty(bankMaintenance.getBANK_STAR_TIME())) {
			this.startTime = bankMaintenance.getBANK_STAR_TIME().toString();
			this.startTime= AppUtil.addPadding(this.startTime, "0", 4);
			this.startTime= this.startTime.substring(0, 2) + ":" + this.startTime.subSequence(2, 4);
		}
		if (!AppUtil.isEmpty(bankMaintenance.getBANK_END_TIME())) {
			this.endTime = bankMaintenance.getBANK_END_TIME().toString();
			this.endTime= AppUtil.addPadding(this.endTime, "0", 4);
			this.endTime= this.endTime.substring(0, 2) + ":" + this.endTime.subSequence(2, 4);
		}
		this.activeFlag = bankMaintenance.getACT_FLAG();
		this.updateBy = bankMaintenance.getLAST_CHNG_BY();
		this.updateDate = bankMaintenance.getLAST_CHNG_DTTM();
		return this;
	}
	
	public Integer getBankMaintenanceId() {
		return bankMaintenanceId;
	}
	public void setBankMaintenanceId(Integer bankMaintenanceId) {
		this.bankMaintenanceId = bankMaintenanceId;
	}
	public String getBankMaintenanceName() {
		return bankMaintenanceName;
	}
	public void setBankMaintenanceName(String bankMaintenanceName) {
		this.bankMaintenanceName = bankMaintenanceName;
	}
	public Integer getBankServiceId() {
		return bankServiceId;
	}
	public void setBankServiceId(Integer bankServiceId) {
		this.bankServiceId = bankServiceId;
	}
	public String getBankServiceName() {
		return bankServiceName;
	}
	public void setBankServiceName(String bankServiceName) {
		this.bankServiceName = bankServiceName;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
