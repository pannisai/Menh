package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.BankChannelBean;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.util.AppUtil;

public class 
BankChannelListModel<T> extends AbstractSelectBoxListModel<BankChannelBean>  {

	public BankChannelListModel(List<BankChannelBean> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		// TODO Auto-generated method stub
		BankChannelBean all = new BankChannelBean();
		all.setBANK_CHNL_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setBANK_CHNL_CODE(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		// TODO Auto-generated method stub
		BankChannelBean select = new BankChannelBean();
		select.setBANK_CHNL_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setBANK_CHNL_CODE(null);
		addData(0, select);
		addToSelection(select);
	}
	public void addItemCreateNew() throws Exception {
		BankChannelBean createNew = new BankChannelBean();
		createNew.setBANK_CHNL_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_CREATE_NEW));
		createNew.setBANK_CHNL_CODE("-1");
		addData(createNew);
	}

	public int findIndexOfId(String id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getBANK_CHNL_CODE() != null) {
					String dataId = data.get(i).getBANK_CHNL_CODE();
					if (AppUtil.trim(dataId).equals(AppUtil.trim(id))) {
						return i;
					}
				}
			}
		}
		return index;
	}
}
