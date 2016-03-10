package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.BillerCategory;
import mfs.biller.persistence.bean.BillerChannel;
import mfs.biller.persistence.bean.MFSServiceId;

import org.zkoss.zul.AbstractListModel;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;

public class BillerCatalogListModel<T> extends AbstractSelectBoxListModel<BillerCategory> {

	public BillerCatalogListModel(List<BillerCategory> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		BillerCategory all = new BillerCategory();
		all.setBLLR_CATG_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBLLR_CATG_ID(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		BillerCategory select = new BillerCategory();
		select.setBLLR_CATG_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setBLLR_CATG_ID(null);
		addData(0, select);
		addToSelection(select);
	}

	public void addItemCreateNew() throws Exception {
		BillerCategory createNew = new BillerCategory();
		createNew.setBLLR_CATG_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_CREATE_NEW));
		createNew.setBLLR_CATG_ID(-1);
		addData(createNew);
	}

	public int findIndexOfId(Integer id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getBLLR_CATG_ID() != null) {
					int dataId = data.get(i).getBLLR_CATG_ID();
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}

}
