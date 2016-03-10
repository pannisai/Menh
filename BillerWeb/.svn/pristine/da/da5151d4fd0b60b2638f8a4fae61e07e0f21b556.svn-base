package com.dtac.billerweb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mfs.biller.persistence.bean.ImportExcelBean;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;

import com.dtac.billerweb.common.AppMessage;
import com.dtac.billerweb.common.SearchPageController;
import com.dtac.billerweb.data.ImportExcelSO;
import com.dtac.billerweb.exception.BillerWebException;
import com.dtac.billerweb.exception.BillerWebSessionTimeOutException;
import com.dtac.billerweb.factory.BillerwebServiceFactory;
import com.dtac.billerweb.listmodel.selectbox.ImportExcelListModel;
import com.dtac.billerweb.service.ImportExcelService;

public class ImportExcelController extends SearchPageController {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4744474678549252908L;

	private Logger log = Logger.getLogger(ImportExcelController.class);

	@Wire
	private Textbox txtUploadName;
	@Wire
	private Button btnUpload;
	@Wire
	private Listbox lbTableImport;
	@Wire
	private Button btnImport;
	@Wire
	private Textbox txtTotal;
	@Wire
	private Textbox txtSuccess;
	@Wire
	private Textbox txtFail;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnReset;

	private final ImportExcelService importExcelService = BillerwebServiceFactory.getImportExcelService();
	private final ListModel<ImportExcelSO> emptyListModel = new SimpleListModel<ImportExcelSO>(new ArrayList<ImportExcelSO>());
	private Media tempMedia;
	private ImportExcelBean importExcelBean;

	public ImportExcelController() throws BillerWebSessionTimeOutException {
		super("Import Excel", "4321");
	}

	@Override
	protected void init(Component com) throws Exception {
		setupPage();

	}

	private void setupPage() {
		log.info(getOperationLogMessage(pageName, "setupPage", ""));
		ImportExcelListModel<ImportExcelBean> importExcelListModel = null;
		try {
			chkInsertPermission(btnUpload);
			chkInsertPermission(btnImport);
			chkInsertPermission(btnSave);
			importExcelListModel = importExcelService.getDropDownTableList();
			importExcelListModel.addItemSelect();
			lbTableImport.setModel(importExcelListModel);

			gdResult.setPaginal(paging);
		} catch (Exception ex) {
			throw new BillerWebException(ex);
		}
	}

	/* ## Event ## */
	@Listen("onUpload = #btnUpload")
	public void validateXLSFile(UploadEvent event) {
		log.info(getOperationLogMessage(pageName, "validateXLSFile", ""));
		checkSessionTimeOut("uploadXLS " + pageName);
		clearPage();
		if ("xls".equals(event.getMedia().getFormat())) {
			txtUploadName.setText(event.getMedia().getName());
			tempMedia = event.getMedia();
		} else {
			showErrorMessage("Unsupport " + event.getMedia().getFormat() + " Format.");
		}
	}

	private void clearPage() {
		tempMedia = null;
		txtUploadName.setText("");
		lbTableImport.setSelectedIndex(0);
		txtTotal.setText("");
		txtSuccess.setText("");
		txtFail.setText("");
		try {
			setGridResultModel(emptyListModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		btnSave.setDisabled(true);
	}

	private void XLStoListData(ImportExcelBean importExcelBean) {
		log.info(getOperationLogMessage(pageName, "setupImportXLS", ""));

		POIFSFileSystem fileSystem;
		HSSFWorkbook workBook;
		HSSFSheet sheet;
		HSSFRow rowHeader = null;
		HSSFRow rowData;
		HSSFCell cellData;
		int cellType;
		int rows;

		List<Map<String, Object>> listRowData = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listRowStatus = new ArrayList<Map<String, Object>>();
		Map<String, Object> rowDataMap;
		Map<String, Object> rowStatusMap;
		try {
			fileSystem = new POIFSFileSystem(tempMedia.getStreamData());
			workBook = new HSSFWorkbook(fileSystem);
			sheet = workBook.getSheetAt(0);
			rows = sheet.getPhysicalNumberOfRows();
			// System.out.println("row :" + rows);
			for (int row = 0; row < rows; row++) {
				if (row == 0) {
					rowHeader = sheet.getRow(row);
				} else {
					rowData = sheet.getRow(row);
					if (rowData != null) {
						rowDataMap = new HashMap<String, Object>();
						rowStatusMap = new HashMap<String, Object>();
						for (int cell = 0; cell < rowHeader.getPhysicalNumberOfCells(); cell++) {
							cellData = rowData.getCell(cell);
							if (null != rowHeader.getCell(cell)) {
								if (cellData != null) {
									cellType = rowData.getCell(cell).getCellType();
									if (Cell.CELL_TYPE_STRING == cellType) {
										if ("".equals(cellData.getStringCellValue())) {
											rowDataMap.put(rowHeader.getCell(cell).getStringCellValue(), null);
										} else {
											rowDataMap.put(rowHeader.getCell(cell).getStringCellValue(), cellData.getStringCellValue());
										}
									} else if (Cell.CELL_TYPE_NUMERIC == cellType) {
										rowDataMap.put(rowHeader.getCell(cell).getStringCellValue(), cellData.getNumericCellValue());
									} else if (Cell.CELL_TYPE_BOOLEAN == cellType) {
										rowDataMap.put(rowHeader.getCell(cell).getStringCellValue(), cellData.getBooleanCellValue());
									} else {
										rowDataMap.put(rowHeader.getCell(cell).getStringCellValue(), null);
									}
								} else {
									rowDataMap.put(rowHeader.getCell(cell).getStringCellValue(), null);
								}
							}
						}
						listRowData.add(rowDataMap);
						listRowStatus.add(rowStatusMap);
					}
				}
			}
			importExcelBean.setListRowData(listRowData);
			importExcelBean.setListRowStatus(listRowStatus);
		} catch (IOException ex) {
			showErrorMessage(ex);
		} finally {
			workBook = null;
			fileSystem = null;
			sheet = null;
			rowHeader = null;
			rowData = null;
			cellData = null;
		}
	}

	@Listen("onClick = #btnImport")
	public void clickImport() {
		log.info(getOperationLogMessage(pageName, "Import Excel", ""));
		checkSessionTimeOut("ImportXLS " + pageName);
		ImportExcelBean selectItem = (ImportExcelBean) lbTableImport.getSelectedItem().getValue();
		if (null != tempMedia) {
			if (!selectItem.getTABLE_NAME().equals(AppMessage.getMessage(AppMessage.SELECTBOX_SELECT))) {
				ImportExcelBean importExcelBean = new ImportExcelBean();
				importExcelBean.setTABLE_NAME(selectItem.getTABLE_NAME());
				XLStoListData(importExcelBean);
				importExcelBean = importExcelService.validateImportExcel(importExcelBean);
				genResultValidateData(importExcelBean);
				btnSave.setDisabled(false);
			} else {
				showErrorMessage("Please select table.");
			}
		} else {
			showErrorMessage("Please select excel file.");
		}
	}

	private void genResultValidateData(ImportExcelBean importExcelBean) {
		this.importExcelBean = importExcelBean;
		ListModel<ImportExcelSO> importExcelListModel = null;
		List<ImportExcelSO> importExcelSOs = new ArrayList<ImportExcelSO>();
		ImportExcelSO importExcelSO;
		int totalRow = 0;
		int successRow = 0;
		int failRow = 0;
		for (Map<String, Object> tempMap : importExcelBean.getListRowStatus()) {
			importExcelSO = new ImportExcelSO();
			importExcelSO.setRowNo(String.valueOf(tempMap.get("rowNo")));
			importExcelSO.setRowStatus(String.valueOf(tempMap.get("rowStatus")));
			importExcelSOs.add(importExcelSO);
			if (importExcelSO.getRowStatus().contains("Success")) {
				successRow++;
				tempMap.put("ActiveStatus", "1");
			} else {
				tempMap.put("ActiveStatus", "2");
				failRow++;
			}
			totalRow++;
		}
		try {
			txtTotal.setText(String.valueOf(totalRow));
			txtSuccess.setText(String.valueOf(successRow));
			txtFail.setText(String.valueOf(failRow));

			importExcelListModel = new SimpleListModel<ImportExcelSO>(importExcelSOs);
			setGridResultModel(importExcelListModel);
		} catch (Exception e) {
			showErrorMessage(e);
		}
	}

	@Listen("onClick = #btnSave")
	public void saveImportTable() {
		checkSessionTimeOut("saveXLS " + pageName);
		if (0 < Integer.valueOf(txtSuccess.getValue())) {
			EventListener<Event> saveConfirmListener = new EventListener<Event>() {
				@Override
				public void onEvent(Event evt) throws Exception {
					if (evt.getName().equals("onOK")) {
						importExcelBean = importExcelService.insertImportExcel(importExcelBean);
						if (importExcelBean.isSuccess()) {
							EventListener<Event> okListener = new EventListener<Event>() {
								public void onEvent(Event evt) {
									if (evt.getName().equals("onOK")) {
									}
								}
							};
							showSaveSuccessMessage(okListener);
						} else {
							showSaveFailMessage();
						}
					}
				}
			};
			showSaveConfirmMessage(saveConfirmListener);
		} else {
			showErrorMessage("No validate success to insert.");
		}
	}

	@Listen("onClick = #btnReset")
	public void cancelImportTable() {
		EventListener<Event> cancelConfirmListener = new EventListener<Event>() {
			@Override
			public void onEvent(Event evt) throws Exception {
				if (evt.getName().equals("onOK")) {
					clearPage();
				}
			}
		};
		showResetConfirmMessage(cancelConfirmListener);
	}

	@Override
	protected void setupPageFromSession() {
		// TODO Auto-generated method stub

	}

}
