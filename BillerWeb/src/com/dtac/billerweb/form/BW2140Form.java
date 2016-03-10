package com.dtac.billerweb.form;

import mfs.biller.persistence.bean.GWXmlDataSrcBean;
import mfs.biller.persistence.bean.ObjMapGWxml;

import com.dtac.billerweb.common.BaseForm;

public class BW2140Form extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8864860863350991008L;
	private Integer mapId;
	private String mapName;
	private String mapDesc;
	private String mapXmlData;
	
	public GWXmlDataSrcBean toMapGWXml() {
		GWXmlDataSrcBean mapGwXml = new GWXmlDataSrcBean();
		mapGwXml.setXML_DATA_ID(this.mapId);
		mapGwXml.setXML_DATA_TYPE(this.mapName);
		mapGwXml.setXML_DATA_DESC(this.mapDesc);
		mapGwXml.setXML_DATA_SRC(this.mapXmlData);
		return mapGwXml;
	}

	public BW2140Form toBW2140Form(GWXmlDataSrcBean mapGwXml) {
		this.mapId = mapGwXml.getXML_DATA_ID();
		this.mapName = mapGwXml.getXML_DATA_TYPE();
		this.mapDesc = mapGwXml.getXML_DATA_DESC();
		this.mapXmlData = mapGwXml.getXML_DATA_SRC();
		return this;
	}
	public Integer getMapId() {
		return mapId;
	}
	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public String getMapDesc() {
		return mapDesc;
	}
	public void setMapDesc(String mapDesc) {
		this.mapDesc = mapDesc;
	}
	public String getMapXmlData() {
		return mapXmlData;
	}
	public void setMapXmlData(String mapXmlData) {
		this.mapXmlData = mapXmlData;
	}
}
