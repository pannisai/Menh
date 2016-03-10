package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.BillerRefDataType;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.util.AppUtil;

public class BillerRefDataTypeListModel <T> extends AbstractSelectBoxListModel<BillerRefDataType>{

	public BillerRefDataTypeListModel(List<BillerRefDataType> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		// TODO Auto-generated method stub
		BillerRefDataType all = new BillerRefDataType();
		all.setBILL_REF_DATA_TYPE_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBILL_REF_DATA_TYPE_NAME(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		// TODO Auto-generated method stub
		BillerRefDataType select = new BillerRefDataType();
		select.setBILL_REF_DATA_TYPE_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setBILL_REF_DATA_TYPE_NAME(null);
		addData(0, select);
		addToSelection(select);
	}
	
	public int findIndexByBillRefDataTypeName(String dataTypeName) throws Exception{
		int index = -1;
		if (dataTypeName == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (!AppUtil.isNull(data.get(i).getBILL_REF_DATA_TYPE_NAME())) {					
					String dataId = AppUtil.trim(data.get(i).getBILL_REF_DATA_TYPE_NAME());
					if (dataId.equals(AppUtil.trim(dataTypeName))) {
						return i;
					}
				}
			}
		}
		return index;
	}

}
