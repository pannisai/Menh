package com.dtac.billerweb.data;

import com.dtac.billerweb.common.BaseDO;

public class BatchMastFileStatusDO extends BaseDO{
	private String id;
	private String value;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
