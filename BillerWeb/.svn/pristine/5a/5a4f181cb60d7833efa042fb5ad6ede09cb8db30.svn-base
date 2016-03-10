package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.SendReceipt;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.util.AppUtil;

public class SendReceiptListModel<T> extends AbstractSelectBoxListModel<SendReceipt>{

	public SendReceiptListModel(List<SendReceipt> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		SendReceipt all = new SendReceipt();
		all.setSEND_RCPT_FLAG(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setINBN_SRVC_ID(null);
		addData(0, all);
		addToSelection(all);
		
	}

	@Override
	public void addItemSelect() throws Exception {
		SendReceipt select = new SendReceipt();
		select.setSEND_RCPT_FLAG(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setINBN_SRVC_ID(null);
		addData(0, select);
		addToSelection(select);
	}
	public int findIndexOfId(String id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getSEND_RCPT_FLAG() != null) {
					String dataId = data.get(i).getSEND_RCPT_FLAG();
					if (AppUtil.trim(dataId).endsWith(AppUtil.trim(id))) {
						return i;
					}
				}
			}
		}
		return index;
	}

}
