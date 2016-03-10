package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.INBOUNDMAPID;
import mfs.biller.persistence.bean.MFSServiceId;
import mfs.biller.persistence.bean.SendReceiptId;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppConfiguration;
import com.dtac.billerweb.common.AppMessage;

public class SendReceiptIdListModel<T> extends AbstractSelectBoxListModel<SendReceiptId> {

	public SendReceiptIdListModel(List<SendReceiptId> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addItemAll() throws Exception {
		SendReceiptId all = new SendReceiptId();
		all.setGW_RCPT_CONF_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_ALL));
		all.setGW_RCPT_CONF_ID(null);
		addData(0, all);
		addToSelection(all);
	}

	@Override
	public void addItemSelect() throws Exception {
		SendReceiptId select = new SendReceiptId();
		select.setGW_RCPT_CONF_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		select.setGW_RCPT_CONF_ID(null);
		addData(0, select);
		addToSelection(select);
	}

	public void addItemCreateNew() throws Exception {
		SendReceiptId createNew = new SendReceiptId();
		createNew.setGW_RCPT_CONF_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_CREATE_NEW));
		createNew.setGW_RCPT_CONF_ID(-1);
		addData(createNew);
	}

	public int findIndexOfId(Integer id) throws Exception {
		int index = -1;
		if (id == null || data == null) {
			return -1;
		} else {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).getGW_RCPT_CONF_ID() != null) {
					int dataId = data.get(i).getGW_RCPT_CONF_ID();
					if (dataId == id.intValue()) {
						return i;
					}
				}
			}
		}
		return index;
	}

}
