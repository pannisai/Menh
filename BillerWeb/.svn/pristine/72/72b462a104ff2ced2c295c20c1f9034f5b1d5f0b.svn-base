package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.BillerBarcode;
import mfs.biller.persistence.bean.BillerRef;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;

public class BillerBarcodeListModel<T> extends AbstractSelectBoxListModel<BillerBarcode> {

	public BillerBarcodeListModel(List<BillerBarcode> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		BillerBarcode all = new BillerBarcode();
		all.setBARC_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBARC_ID(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		// TODO Auto-generated method stub
		BillerBarcode select = new BillerBarcode();
		select.setBARC_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setBARC_ID(null);
		addData(0, select);
		addToSelection(select);
	}

	public void addItemCreateNew() throws Exception {
		BillerBarcode createNew = new BillerBarcode();
		createNew.setBARC_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_CREATE_NEW));
		createNew.setBARC_ID(-1);
		addData(createNew);
	}

	public int findIndexOfId(Integer id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getBARC_ID() != null) {
					int dataId = data.get(i).getBARC_ID();
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}

}
