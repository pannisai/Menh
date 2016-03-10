package com.dtac.bmweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.DropdownlistBillservice;

import com.dtac.bmweb.common.AbstractSelectBoxListModel;
import com.dtac.bmweb.common.AppMessage;
import com.dtac.bmweb.util.AppUtil;

public class BillerServiceListModel<T> extends AbstractSelectBoxListModel<DropdownlistBillservice> {

	public BillerServiceListModel(List<DropdownlistBillservice> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		DropdownlistBillservice all = new DropdownlistBillservice();

		all.setBLLR_SRVC_NAME_EN(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBLLR_SRVC_ID(null);
		all.setBLLR_SRVC_CODE(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		DropdownlistBillservice select = new DropdownlistBillservice();

		select.setBLLR_SRVC_NAME_EN(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setBLLR_SRVC_ID(null);
		select.setBLLR_SRVC_CODE(null);
		addData(0, select);
		addToSelection(select);
	}
	
	public int findIndexOfId(Integer id) throws Exception{
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getBLLR_SRVC_ID() != null) {					
					int dataId = data.get(i).getBLLR_SRVC_ID();
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}
	
	public int findIndexByServiceCode(Integer id) throws Exception{
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (!AppUtil.isEmpty(data.get(i).getBLLR_SRVC_CODE())) {					
					int dataId = Integer.parseInt(data.get(i).getBLLR_SRVC_CODE());
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}
}
