package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import mfs.biller.persistence.bean.ImportExcelBean;

import com.dtac.billerweb.common.AbstractSelectBoxListModel;
import com.dtac.billerweb.common.AppMessage;

public class ImportExcelListModel<T> extends AbstractSelectBoxListModel<ImportExcelBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3416042621570274714L;

	public ImportExcelListModel(List<ImportExcelBean> data) {
		super(data);
	}

	@Override
	public void addItemAll() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void addItemSelect() throws Exception {
		ImportExcelBean select = new ImportExcelBean();
		select.setTABLE_NAME(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT));
		addData(0, select);
		addToSelection(select);
	}

}
