package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.GWService;
import mfs.biller.persistence.bean.OBJGW_SERVICE;

import com.dtac.billerweb.common.BaseForm;

public class BW2210Form extends BaseForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6898940676322539910L;
	private Integer serviceId;
	private String serviceName;
	private Integer serviceMapId;
	private Integer outboundId;
	private String activeFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;

	public GWService toServiceGateway() {
		GWService serviceGateway = new GWService();
		serviceGateway.setGW_SRVC_ID(this.serviceId);
		serviceGateway.setGW_SRVC_NAME(this.serviceName);
		serviceGateway.setGW_SRVC_MAP_ID(this.serviceMapId);
		serviceGateway.setGW_OUTB_ID(this.outboundId);
		serviceGateway.setACT_FLAG(this.activeFlag);
		serviceGateway.setCRTD_BY(this.createBy);
		serviceGateway.setCRTD_DTTM(this.createDate);
		serviceGateway.setLAST_CHNG_BY(this.updateBy);
		serviceGateway.setLAST_CHNG_DTTM(this.updateDate);
		return serviceGateway;
	}

	public BW2210Form toBW2210Form(GWService serviceGateway) {
		this.serviceId = serviceGateway.getGW_SRVC_ID();
		this.serviceName = serviceGateway.getGW_SRVC_NAME();
		this.serviceMapId = serviceGateway.getGW_SRVC_MAP_ID();
		this.outboundId = serviceGateway.getGW_OUTB_ID();
		this.activeFlag = serviceGateway.getACT_FLAG();
		this.createBy = serviceGateway.getCRTD_BY();
		this.createDate = serviceGateway.getCRTD_DTTM();
		this.updateBy = serviceGateway.getLAST_CHNG_BY();
		this.updateDate = serviceGateway.getLAST_CHNG_DTTM();
		return this;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getServiceMapId() {
		return serviceMapId;
	}

	public void setServiceMapId(Integer serviceMapId) {
		this.serviceMapId = serviceMapId;
	}


	public Integer getOutboundId() {
		return outboundId;
	}

	public void setOutboundId(Integer outboundId) {
		this.outboundId = outboundId;
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
