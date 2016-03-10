package com.dtac.billerweb.data;

import java.io.Serializable;

public class ImportExcelSO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2349281838936624287L;
	private String rowNo;
	private String rowStatus;

	public String getRowNo() {
		return rowNo;
	}

	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}

	public String getRowStatus() {
		return rowStatus;
	}

	public void setRowStatus(String rowStatus) {
		this.rowStatus = rowStatus;
	}

}
