package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.MFSServiceId;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;

public class MFSServiceIdListModel<T> extends AbstractSelectBoxListModel<MFSServiceId> {

	public MFSServiceIdListModel(List<MFSServiceId> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		MFSServiceId all = new MFSServiceId();
		all.setGW_SRVC_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setGW_SRVC_ID(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		MFSServiceId select = new MFSServiceId();
		select.setGW_SRVC_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setGW_SRVC_ID(null);
		addData(0, select);
		addToSelection(select);
	}
	
	public void addItemCreateNew() throws Exception {
		MFSServiceId createNew = new MFSServiceId();
		createNew.setGW_SRVC_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_CREATE_NEW));
		createNew.setGW_SRVC_ID(-1);
		addData(createNew);		
	}

	public int findIndexOfId(Integer id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getGW_SRVC_ID() != null) {
					int dataId = data.get(i).getGW_SRVC_ID();
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}

}
