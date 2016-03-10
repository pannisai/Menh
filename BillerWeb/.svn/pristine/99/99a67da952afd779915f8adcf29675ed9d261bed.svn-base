package com.dtac.billerweb.listmodel.selectbox;

import java.util.List;

import org.zkoss.zul.AbstractListModel;

import com.dtac.billerweb.data.BatchMastFileStatusDO;

public class BatchMastFileStatusListModel extends AbstractListModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<BatchMastFileStatusDO> data;

	public BatchMastFileStatusListModel(List<BatchMastFileStatusDO> data) {
		this.data = data;
	}

	@Override
	public Object getElementAt(int index) {
		return data.get(index);

	}

	@Override
	public int getSize() {
		return data.size();
	}

}
