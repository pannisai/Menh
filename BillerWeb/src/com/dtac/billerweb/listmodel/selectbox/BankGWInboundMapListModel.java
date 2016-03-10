package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.BankServicebean;
import mfs.biller.persistence.bean.GWBankDetail;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;

public class BankGWInboundMapListModel<T> extends AbstractSelectBoxListModel<GWBankDetail> {

	public BankGWInboundMapListModel(List<GWBankDetail> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		// TODO Auto-generated method stub
		GWBankDetail all = new GWBankDetail();
		all.setINB_DATA_MAP_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setINB_MAP_ID(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		// TODO Auto-generated method stub
		GWBankDetail select = new GWBankDetail();
		select.setINB_DATA_MAP_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setINB_MAP_ID(null);
		addData(0, select);
		addToSelection(select);
	}

	public void addItemCreateNew() throws Exception {
		GWBankDetail createNew = new GWBankDetail();
		createNew.setINB_DATA_MAP_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_CREATE_NEW));
		createNew.setINB_MAP_ID(-1);
		addData(createNew);
	}

	public int findIndexOfId(Integer id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getINB_MAP_ID() != null) {
					int dataId = data.get(i).getINB_MAP_ID();
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}
}
