package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.GWServiceMap;
import mfs.biller.persistence.bean.OutboundId;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;

public class OutboundIdListModel<T> extends AbstractSelectBoxListModel<OutboundId> {

	public OutboundIdListModel(List<OutboundId> data) {
		super(data);
	}

	@Override
	public void addItemAll() throws Exception {
		OutboundId all = new OutboundId();
		all.setGW_OUTB_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setGW_OUTB_ID(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		OutboundId select = new OutboundId();
		select.setGW_OUTB_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setGW_OUTB_ID(null);
		addData(0, select);
		addToSelection(select);
	}
	
	public void addItemCreateNew() throws Exception {
		OutboundId createNew = new OutboundId();
		createNew.setGW_OUTB_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_CREATE_NEW));
		createNew.setGW_OUTB_ID(-1);
		addData(createNew);
	}
	
	public int findIndexOfId(Integer id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getGW_OUTB_ID() != null) {
					int dataId = data.get(i).getGW_OUTB_ID();
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}

}
