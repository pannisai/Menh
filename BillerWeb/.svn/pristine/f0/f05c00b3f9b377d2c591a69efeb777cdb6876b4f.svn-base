package com.dtac.billerweb.form;

import java.util.Date;

import mfs.biller.persistence.bean.OBJGW_INBOUND;
import mfs.biller.persistence.bean.ObjMapGWxml;

import com.dtac.billerweb.common.BaseForm;

public class BW2130Form extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9182339891433019777L;
	private Integer mapId;
	private String mapName;
	private String mapDesc;
	private String mapXmlData;

	
	public ObjMapGWxml toMapGWXml() {
		ObjMapGWxml mapGwXml = new ObjMapGWxml();
		mapGwXml.setDATA_MAP_ID(this.mapId);
		mapGwXml.setXML_DATA_TYPE(this.mapName);
		mapGwXml.setXML_DATA_DESC(this.mapDesc);
		mapGwXml.setXML_DATA_SRC(this.mapXmlData);
		return mapGwXml;
	}

	public BW2130Form toBW2130Form(ObjMapGWxml mapGwXml) {
		this.mapId = mapGwXml.getDATA_MAP_ID();
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
