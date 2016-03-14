package com.dtac.billerweb.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import mfs.biller.ejb.interfaces.ImportExcelRemote;
import mfs.biller.persistence.bean.ImportExcelBean;
import mfs.constants.Constants;

import com.dtac.billerweb.common.BaseService;
import com.dtac.billerweb.common.EJBInitialContext;
import com.dtac.billerweb.exception.ServiceException;
import com.dtac.billerweb.listmodel.selectbox.ImportExcelListModel;
import com.dtac.billerweb.service.ImportExcelService;
import com.dtac.billerweb.util.AppUtil;

public class ImportExcelServiceImpl extends BaseService implements ImportExcelService {

	@Override
	public ImportExcelListModel<ImportExcelBean> getDropDownTableList() {
		ImportExcelListModel<ImportExcelBean> importExcelListModel = new ImportExcelListModel<ImportExcelBean>(new ArrayList<ImportExcelBean>());
		List<ImportExcelBean> list = new ArrayList<ImportExcelBean>();
		ImportExcelRemote importExcelRemote;
		try {
			importExcelRemote = (ImportExcelRemote) EJBInitialContext.lookup(Constants.JNDI.importExcel);
			if (importExcelRemote == null) {
				return importExcelListModel;
			}
			list = importExcelRemote.getDropDownTableList();
			if (AppUtil.isNull(list)) {
				list = new ArrayList<ImportExcelBean>();
			}
			importExcelListModel = new ImportExcelListModel<ImportExcelBean>(list);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			importExcelRemote = null;
		}
		return importExcelListModel;
	}

	@Override
	public ImportExcelBean validateImportExcel(ImportExcelBean tempBean) {
		ImportExcelRemote importExcelRemote;
		ImportExcelBean importExcelBean;
		try {
			importExcelRemote = (ImportExcelRemote) EJBInitialContext.lookup(Constants.JNDI.importExcel);
			importExcelBean = importExcelRemote.validateImportExcel(tempBean);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			importExcelRemote = null;
		}
		return importExcelBean;
	}

	@Override
	public ImportExcelBean insertImportExcel(ImportExcelBean tempBean) {
		ImportExcelRemote importExcelRemote;
		ImportExcelBean importExcelBean;
		try {
			importExcelRemote = (ImportExcelRemote) EJBInitialContext.lookup(Constants.JNDI.importExcel);
			importExcelBean = importExcelRemote.insertImportExcel(tempBean);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			importExcelRemote = null;
		}
		return importExcelBean;
	}

}
