package com.dtac.bmweb.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.dtac.bmweb.exception.ReportServiceException;

public class ExcelUtils {

	@SuppressWarnings({ "rawtypes", "null" })
	public static ByteArrayOutputStream genBodyOutputStream_XLSX(List listData, Class cls, String criteria, String xlsxHeader, String fieldHeader) throws ReportServiceException, FileNotFoundException, IOException {
		XSSFWorkbook wb = null;
		XSSFSheet sheet = null;
		XSSFRow row = null;
		XSSFCellStyle cellStyleTemp = null;
		XSSFFont fontBoldStyle = null;
		XSSFFont fontNormalStyle = null;
		XSSFCell cell = null;

		int dataSize = 0;
		int printIndex = 0;
		int totalColumn = 0;
		String[] header = null;
		String[] field = null;
		int rowPerSheet = 40000;
		ByteArrayOutputStream outputStream = null;		

		try {
			outputStream = new ByteArrayOutputStream();
			if (!isEmptyString(xlsxHeader)) {
				header = xlsxHeader.split("\\|");
				totalColumn = header.length;
			} else {
				totalColumn = cls.getDeclaredFields().length;
				header = new String[totalColumn];
				for (int i = 0; i < totalColumn; i++) {
					header[i] = cls.getFields()[i].getName();
				}
			}
			if (!isEmptyString(fieldHeader)) {
				field = fieldHeader.split("\\|");
			} else {
				header = new String[totalColumn];
				for (int i = 0; i < totalColumn; i++) {
					field[i] = cls.getFields()[i].getName();
				}
			}

			if (listData != null) {
				dataSize = listData.size();
			}

			wb = new XSSFWorkbook();

			fontBoldStyle = getFontObj(wb, (short) 9, (short) 0, true);
			fontNormalStyle = getFontObj(wb, (short) 9, (short) 0, false);

			for (int i = 0; i < dataSize || dataSize == 0; i++) {
				if (i % rowPerSheet == 0) {
					sheet = wb.createSheet();

					for (int j = 0; j < totalColumn; j++) {
						sheet.setColumnWidth(j, 7680);
						cellStyleTemp = getCellObj(wb, fontNormalStyle, "General", XSSFCellStyle.ALIGN_LEFT, false, false);
						sheet.setDefaultColumnStyle((short) j, cellStyleTemp);
					}

					// gen Criteria
					if(criteria!=null){
						row = sheet.createRow(0);
						row.setHeightInPoints(15);

						cell = row.createCell(0);
						cellStyleTemp = getCellObj(wb, fontBoldStyle, "General", XSSFCellStyle.ALIGN_RIGHT, false, false);
						cell.setCellStyle(cellStyleTemp);
						cell.setCellValue(new XSSFRichTextString("criteria : "));

						cell = row.createCell(1);
						sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, totalColumn - 1));
						cell.setCellValue(new XSSFRichTextString(criteria));						
					}

					// gen Header
					cellStyleTemp = getCellObj(wb, fontBoldStyle, "General", XSSFCellStyle.ALIGN_CENTER, true, true);
					genRowHeader(sheet, (criteria!=null?1:0), 15, cellStyleTemp, header, totalColumn);

					printIndex = (criteria!=null?2:1);
				} else {
					printIndex++;
				}

				if (dataSize != 0) {
					// gen Body
					genRowBody(sheet, printIndex, 15, listData.get(i), totalColumn, field);
				} else {
					break;
				}
			}

			if (dataSize != 0) {
				printIndex++;
			}
			wb.write(outputStream);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ReportServiceException("ERROR IN WebUtils.genBodyOutputStream_XLS()");
		}
		return outputStream;
	}
	
	public static XSSFFont getFontObj(XSSFWorkbook workbook, short fontSize, short fontColor, Boolean fontBold) throws ReportServiceException {
		XSSFFont objFont = null;

		try {
			objFont = workbook.createFont();
			objFont.setFontName("Tahoma");

			if (fontSize != 0) {
				objFont.setFontHeightInPoints(fontSize);
			}
			if (fontColor != 0) {
				objFont.setColor(fontColor);
			}
			if (fontBold) {
				objFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			}

		} catch (Exception e) {
			throw new ReportServiceException("ERROR IN JavaReportService.getFontObj()");
		}
		return objFont;
	}
	
	public static XSSFCellStyle getCellObj(XSSFWorkbook workbook, XSSFFont objFont, String dataFormat, short alignment, Boolean haveBorder, Boolean fillColor) throws ReportServiceException {
		XSSFCellStyle objCell = null;
		XSSFDataFormat objDataFormat = null;

		try {
			objCell = workbook.createCellStyle();

			objCell.setFont(objFont);

			objDataFormat = workbook.createDataFormat();
			objCell.setDataFormat(objDataFormat.getFormat(dataFormat));

			objCell.setAlignment(alignment);

			if (fillColor) {
				objCell.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
				objCell.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			}
			if (haveBorder) {
				objCell.setBorderBottom(XSSFCellStyle.BORDER_THIN);
				objCell.setBorderLeft(XSSFCellStyle.BORDER_THIN);
				objCell.setBorderTop(XSSFCellStyle.BORDER_THIN);
				objCell.setBorderRight(XSSFCellStyle.BORDER_THIN);
			}

		} catch (Exception e) {
			throw new ReportServiceException("ERROR IN WebUtils..getCellObj()");
		}
		return objCell;
	}
	
	public static void genRowHeader(XSSFSheet sheet, int rowOfHeader, float rowHeight, XSSFCellStyle cellStyle, String[] reportHeader, int totalColumn) throws ReportServiceException {
		XSSFRow row = null;
		XSSFCell cell = null;

		try {
			row = sheet.createRow(rowOfHeader);
			row.setHeightInPoints(rowHeight);

			for (int i = 0; i < totalColumn; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(new XSSFRichTextString(new String(reportHeader[i].getBytes("UTF-8"), "UTF-8")));
				cell.setAsActiveCell();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ReportServiceException("ERROR IN WebUtils.genHeader()");
		}
	}

	@SuppressWarnings("rawtypes")
	public static void genRowBody(XSSFSheet sheet, int rowOfBody, float rowHeight, Object arrayObj, int totalColumn, String[] fieldHeader) throws ReportServiceException {
		XSSFRow row = null;
		XSSFCell cell = null;

		try {
			Class cls = arrayObj.getClass();
			if (cls != null) {
				row = sheet.createRow(rowOfBody);
				row.setHeightInPoints(rowHeight);
				for (int i = 0; i < totalColumn; i++) {
					Field fields[] = cls.getDeclaredFields();
					for (Field field : fields) {
						if (fieldHeader[i].equals(field.getName())) {
							cell = row.createCell(i);
							field.setAccessible(true);
							// System.out.println("Field : " + fieldHeader[i] +
							// " Value : " + (field.get(arrayObj) != null ?
							// String.valueOf((field.get(arrayObj))) : ""));
							cell.setCellValue(new XSSFRichTextString((field.get(arrayObj) != null ? String.valueOf((field.get(arrayObj))) : "")));
							// set background
//							if(BeanUtil.isNotEmpty(bgColor)){
//								XSSFCellStyle cellStyle = workbook.createCellStyle();
//								cellStyle.setFillForegroundColor(bgColor);
//								cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
//								cell.setCellStyle(cellStyle);
//							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ReportServiceException("ERROR IN WebUtils..genRowBody()");
		}
	}
	
	private static boolean isEmptyString(String str) {
		boolean ch = true;
		if (str != null && !"".equals(str)) {
			ch = false;
		}
		return ch;
	}
}
