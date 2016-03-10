package com.dtac.bmweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.BillerMaster;

import com.dtac.bmweb.common.AbstractSelectBoxListModel;
import com.dtac.bmweb.common.AppMessage;

public class BillerMasterListModel<T> extends AbstractSelectBoxListModel<BillerMaster> {

	public BillerMasterListModel(List<BillerMaster> data) {
		super(data);
	}

	@Override
	public void addItemAll() throws Exception {
		BillerMaster all = new BillerMaster();
		all.setBLLR_CODE(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBLLR_MSTR_ID(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		// TODO Auto-generated method stub
		BillerMaster select = new BillerMaster();
		select.setBLLR_CODE(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setBLLR_MSTR_ID(null);
		addData(0, select);
		addToSelection(select);
	}

	public void addItemCreateNew() throws Exception {
		BillerMaster createNew = new BillerMaster();
		createNew.setBLLR_CODE(AppMessage.getMessage(AppMessage.SELECTBOX_CREATE_NEW));
		createNew.setBLLR_MSTR_ID(-1);
		addData(createNew);
	}

	public int findIndexOfId(Integer id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			
			for (int i = 0; i < data.size(); i++) {				
				if (data.get(i).getBLLR_MSTR_ID() != null) {
					
					int dataId = data.get(i).getBLLR_MSTR_ID();
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}
}
