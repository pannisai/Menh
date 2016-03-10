package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.BillerIntegration;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;

public class IntegrationListModel<T> extends AbstractSelectBoxListModel<BillerIntegration> {

	public IntegrationListModel(List<BillerIntegration> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		// TODO Auto-generated method stub
		BillerIntegration all = new BillerIntegration();
		all.setBLLR_INGT_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBLLR_INGT_ID(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		// TODO Auto-generated method stub
		BillerIntegration select = new BillerIntegration();
		select.setBLLR_INGT_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setBLLR_INGT_ID(null);
		addData(0, select);
		addToSelection(select);
	}

	public int findIndexOfId(Integer id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getBLLR_INGT_ID() != null) {
					int dataId = data.get(i).getBLLR_INGT_ID();
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}
}
