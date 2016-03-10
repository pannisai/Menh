package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.BankFdmCode;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;

public class BankFDMCodeListModel<T> extends AbstractSelectBoxListModel<BankFdmCode> {

	public BankFDMCodeListModel(List<BankFdmCode> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		// TODO Auto-generated method stub
		BankFdmCode all = new BankFdmCode();
		all.setBANK_MSGE_CODE_MSGE_EN(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBANK_MSGE_CODE(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		// TODO Auto-generated method stub
		BankFdmCode select = new BankFdmCode();
		select.setBANK_MSGE_CODE_MSGE_EN(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setBANK_MSGE_CODE(null);
		addData(0, select);
		addToSelection(select);
	}

}
