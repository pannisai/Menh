package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.BillerChannel;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;

public class BillerChannelListModel<T> extends AbstractSelectBoxListModel<BillerChannel> {

	public BillerChannelListModel(List<BillerChannel> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		BillerChannel all = new BillerChannel();
		all.setBLLR_CHNL_CODE(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBLLR_CHNL_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBLLR_CHNL_ID(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		BillerChannel select = new BillerChannel();
		select.setBLLR_CHNL_CODE(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setBLLR_CHNL_ID(null);
		addData(0, select);
		addToSelection(select);
	}
	
	public void addItemCreateNew() throws Exception {
		BillerChannel createNew = new BillerChannel();
		createNew.setBLLR_CHNL_CODE(AppMessage.getMessage(AppMessage.SELECTBOX_CREATE_NEW));
		createNew.setBLLR_CHNL_ID(-1);
		addData(createNew);
	}

	public int findIndexOfId(Integer id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getBLLR_CHNL_ID() != null) {
					int dataId = data.get(i).getBLLR_CHNL_ID();
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}

}
