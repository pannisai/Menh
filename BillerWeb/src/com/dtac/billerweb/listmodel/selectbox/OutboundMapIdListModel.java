package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.GWOutboundMap;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;

public class OutboundMapIdListModel<T> extends AbstractSelectBoxListModel<GWOutboundMap> {

	public OutboundMapIdListModel(List<GWOutboundMap> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		GWOutboundMap all = new GWOutboundMap();
		all.setDATA_MAP_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setDATA_MAP_ID(null);
		addData(0, all);
		addToSelection(all);

	}

	@Override
	public void addItemSelect() throws Exception {
		// TODO Auto-generated method stub
		GWOutboundMap select = new GWOutboundMap();
		select.setDATA_MAP_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setDATA_MAP_ID(null);
		addData(0, select);
		addToSelection(select);
	}

	public void addItemCreateNew() throws Exception {
		GWOutboundMap createNew = new GWOutboundMap();
		createNew.setDATA_MAP_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_CREATE_NEW));
		createNew.setDATA_MAP_ID(-1);
		addData(createNew);
	}

	public int findIndexOfId(Integer id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getDATA_MAP_ID() != null) {
					int dataId = data.get(i).getDATA_MAP_ID();
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}

}
