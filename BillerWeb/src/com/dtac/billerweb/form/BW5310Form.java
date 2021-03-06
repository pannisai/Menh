package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.BankServicebean;
import mfs.biller.persistence.bean.BankSystemMATN;
import mfs.biller.util.DateTimeUtil;

import com.dtac.billerweb.common.BaseForm;
import com.dtac.billerweb.util.AppUtil;

public class BW5310Form extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5626009734622985255L;
	private Integer bankMaintenanceId;
	private String bankMaintenanceName;
	private Integer bankServiceId;
	private String bankServiceName;
	private Date startTime;
	private Date endTime;
	private String activeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public BankSystemMATN toBankMaintenance() {
		BankSystemMATN bankSystemMATN = new BankSystemMATN();
		bankSystemMATN.setBANK_SYSM_MATN_ID(this.bankMaintenanceId);
		bankSystemMATN.setBANK_SYSM_MATN_NAME(this.bankMaintenanceName);
		bankSystemMATN.setBANK_SRVC_ID(this.bankServiceId);

		if (this.startTime != null) {
			String hh = AppUtil.addPadding(AppUtil.toString(this.startTime.getHours()), "0", 2);
			String mm = AppUtil.addPadding(AppUtil.toString(this.startTime.getMinutes()), "0", 2);
			bankSystemMATN.setBANK_STAR_TIME(Integer.parseInt((hh + mm)));
			hh = null;
			mm = null;
		}
		if (this.endTime != null) {
			String hh = AppUtil.addPadding(AppUtil.toString(this.endTime.getHours()), "0", 2);
			String mm = AppUtil.addPadding(AppUtil.toString(this.endTime.getMinutes()), "0", 2);
			bankSystemMATN.setBANK_END_TIME(Integer.parseInt((hh + mm)));
			hh = null;
			mm = null;
		}
		bankSystemMATN.setACT_FLAG(this.activeFlag);
		bankSystemMATN.setCRTD_BY(this.createBy);
		bankSystemMATN.setCRTD_DTTM(this.createDate);
		bankSystemMATN.setLAST_CHNG_BY(this.updateBy);
		bankSystemMATN.setLAST_CHNG_DTTM(this.updateDate);
		return bankSystemMATN;
	}

	public BW5310Form toBW5310Form(BankSystemMATN bankSystemMATN) {
		this.bankMaintenanceId = bankSystemMATN.getBANK_SYSM_MATN_ID();
		this.bankMaintenanceName = bankSystemMATN.getBANK_SYSM_MATN_NAME();
		this.bankServiceId = bankSystemMATN.getBANK_SRVC_ID();

		Integer startTimeI = bankSystemMATN.getBANK_STAR_TIME();
		if (startTimeI != null) {
			String startTimeStr = AppUtil.addPadding(startTimeI + "", "0", 4);
			int hh = Integer.parseInt(startTimeStr.substring(0, 2));
			int mm = Integer.parseInt(startTimeStr.substring(2, 4));
			this.startTime = DateTimeUtil.getCurrent();
			this.startTime.setHours(hh);
			this.startTime.setMinutes(mm);
			startTimeStr = null;
			startTimeI = null;
		}

		Integer endTimeI = bankSystemMATN.getBANK_END_TIME();
		if (endTimeI != null) {
			String endTimeStr = AppUtil.addPadding(endTimeI + "", "0", 4);
			int hh = Integer.parseInt(endTimeStr.substring(0, 2));
			int mm = Integer.parseInt(endTimeStr.substring(2, 4));
			this.endTime = DateTimeUtil.getCurrent();
			this.endTime.setHours(hh);
			this.endTime.setMinutes(mm);
			endTimeStr = null;
			endTimeI = null;
		}
		
		this.activeFlag = bankSystemMATN.getACT_FLAG();
		this.updateBy = bankSystemMATN.getLAST_CHNG_BY();
		this.updateDate = bankSystemMATN.getLAST_CHNG_DTTM();
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
