package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.DropdownlistBillMSRT;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;

public class BillerCodeListModel extends AbstractSelectBoxListModel<DropdownlistBillMSRT> {
	public BillerCodeListModel(List<DropdownlistBillMSRT> data) {
		super(data);
	}

	@Override
	public void addItemAll() throws Exception {
		DropdownlistBillMSRT all = new DropdownlistBillMSRT();
		all.setBLLR_CODE(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBLLR_MSTR_ID(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
