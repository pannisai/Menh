package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.BillerFeeMast;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.util.AppUtil;

public class BillerFeeMastListModel<T> extends AbstractSelectBoxListModel<BillerFeeMast> {

	public BillerFeeMastListModel(List<BillerFeeMast> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		BillerFeeMast all = new BillerFeeMast();
		all.setFEE_TYPE(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBLLR_FEE_MAST_ID(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		BillerFeeMast select = new BillerFeeMast();
		select.setFEE_TYPE(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setBLLR_FEE_MAST_ID(null);
		addData(0, select);
		addToSelection(select);
	}

	public int findIndexOfId(Integer id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getBLLR_FEE_MAST_ID() != null) {
					int dataId = data.get(i).getBLLR_FEE_MAST_ID();
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}
	
	public String findFeeTypeOfId(Integer id) throws Exception {
		String feeType = "";
		if(AppUtil.isNotNull(id) && AppUtil.isNotNull(data)){
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getBLLR_FEE_MAST_ID() != null) {
					if (data.get(i).getBLLR_FEE_MAST_ID() == id.intValue()) {
						return data.get(i).getFEE_TYPE();
					}
				}
			}
		}
		return feeType;
	}
}
