package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.BillerPymtCode;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;

public class BillerPymtCodeListModel extends AbstractSelectBoxListModel<BillerPymtCode> {
	
	public BillerPymtCodeListModel(List<BillerPymtCode> data) {
		super(data);
	}

	@Override
	public void addItemAll() throws Exception {
		BillerPymtCode all = new BillerPymtCode();
		all.setBLLR_MSGE_CODE_MSGE_EN(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBLLR_MSGE_CODE(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
