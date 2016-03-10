package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.BankChannelBean;
import mfs.biller.persistence.bean.BankServicebean;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;

public class BankServiceListModel<T> extends AbstractSelectBoxListModel<BankServicebean> {

	public BankServiceListModel(List<BankServicebean> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		// TODO Auto-generated method stub
		BankServicebean all = new BankServicebean();
		all.setBANK_SRVC_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBANK_SRVC_ID(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		// TODO Auto-generated method stub
		BankServicebean select = new BankServicebean();
		select.setBANK_SRVC_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setBANK_SRVC_ID(null);
		addData(0, select);
		addToSelection(select);
	}

	public void addItemCreateNew() throws Exception {
		BankServicebean createNew = new BankServicebean();
		createNew.setBANK_SRVC_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_CREATE_NEW));
		createNew.setBANK_SRVC_ID(-1);
		addData(createNew);
	}

	public int findIndexOfId(Integer id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getBANK_SRVC_ID() != null) {
					int dataId = data.get(i).getBANK_SRVC_ID();
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}
}
