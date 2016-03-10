package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.BillerCategory;
import mfs.biller.persistence.bean.BillerRef;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;

public class BillerRefListModel<T> extends AbstractSelectBoxListModel<BillerRef> {

	public BillerRefListModel(List<BillerRef> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		// TODO Auto-generated method stub
		BillerRef all = new BillerRef();
		all.setREF_LABL_EN(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setREF_ID(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		// TODO Auto-generated method stub
		BillerRef select = new BillerRef();
		select.setREF_LABL_EN(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setREF_ID(null);
		addData(0, select);
		addToSelection(select);
	}

	public void addItemCreateNew() throws Exception {
		BillerRef createNew = new BillerRef();
		createNew.setREF_LABL_EN(AppMessage.getMessage(AppMessage.SELECTBOX_CREATE_NEW));
		createNew.setREF_ID(-1);
		addData(createNew);
	}

	public int findIndexOfId(Integer id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getREF_ID() != null) {
					int dataId = data.get(i).getREF_ID();
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}

}
