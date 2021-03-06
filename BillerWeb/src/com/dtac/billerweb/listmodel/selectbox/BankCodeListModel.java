package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.BankMasterBean;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.util.AppUtil;

public class BankCodeListModel<T> extends AbstractSelectBoxListModel<BankMasterBean> {

	public BankCodeListModel(List<BankMasterBean> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		// TODO Auto-generated method stub
		BankMasterBean all = new BankMasterBean();
		all.setBANK_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBANK_CODE(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		// TODO Auto-generated method stub
		BankMasterBean select = new BankMasterBean();
		select.setBANK_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setBANK_CODE(null);
		addData(0, select);
		addToSelection(select);
	}

	public void addItemCreateNew() throws Exception {
		BankMasterBean createNew = new BankMasterBean();
		createNew.setBANK_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_CREATE_NEW));
		createNew.setBANK_CODE("-1");
		addData(createNew);
	}

	public int findIndexOfId(String id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getBANK_CODE() != null) {
					String dataId = data.get(i).getBANK_CODE();
					if (AppUtil.trim(dataId).equals(AppUtil.trim(id))) {
						return i;
					}
				}
			}
		}
		return index;
	}
}
