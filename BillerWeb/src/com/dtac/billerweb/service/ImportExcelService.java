package com.dtac.billerweb.service;

import mfs.biller.persistence.bean.ImportExcelBean;

import com.dtac.billerweb.listmodel.selectbox.ImportExcelListModel;

public interface ImportExcelService {
	public ImportExcelListModel<ImportExcelBean> getDropDownTableList();

	public ImportExcelBean validateImportExcel(ImportExcelBean tempBean);

	public ImportExcelBean insertImportExcel(ImportExcelBean tempBean);
}
