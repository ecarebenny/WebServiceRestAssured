package ExcelUtilities;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.IllegalFormatException;

import org.apache.poi.xssf.usermodel.XSSFCell;

import org.apache.poi.xssf.usermodel.XSSFRow;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils {
		
	public static void ExcelWrite(String filepath, int RowNum, int CellNum, Object data, int sheetIndex)
			throws IOException, IllegalFormatException {

		File file = new File(filepath);
		FileInputStream fs = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fs);
		XSSFSheet sheet = wb.getSheetAt(sheetIndex);
		XSSFCell cell = sheet.getRow(RowNum).getCell(CellNum);  
		
		cell.setCellValue((String)data);
		FileOutputStream fo = new FileOutputStream(file);
		wb.write(fo);
		fo.close();
	}

	public static String ExcelRead(String filepath, int RowNum, int CellNum, int sheetIndex)
			throws Exception {

		File file = new File(filepath);
		FileInputStream fs = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fs);
		XSSFSheet sheet = wb.getSheetAt(sheetIndex);
		XSSFCell cell = sheet.getRow(RowNum).getCell(CellNum);  
		String value = cell.getStringCellValue();
		fs.close();
		wb.close();
		return value;

	}
}