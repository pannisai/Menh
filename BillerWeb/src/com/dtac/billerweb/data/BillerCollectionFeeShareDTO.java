package com.dtac.billerweb.data;

import java.util.List;

import com.dtac.billerweb.common.BaseDO;

public class BillerCollectionFeeShareDTO extends BaseDO {	
	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMssg;
	private List<BillerCollectionFeeShareData> dataList;
	
	public List<BillerCollectionFeeShareData> getDataList() {
		return dataList;
	}

	public void setDataList(List<BillerCollectionFeeShareData> dataList) {
		this.dataList = dataList;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMssg() {
		return errorMssg;
	}

	public void setErrorMssg(String errorMssg) {
		this.errorMssg = errorMssg;
	}
}