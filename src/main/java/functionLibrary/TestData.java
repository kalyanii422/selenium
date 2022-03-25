package functionLibrary;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestData {
	private FileInputStream fis = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	public TestData(String xlFilePath) throws IOException {
		fis = new FileInputStream(xlFilePath);
		workbook = new XSSFWorkbook(fis);

		fis.close();
	}

	private int getTCRow(String TCID) {
		int rowIndex = -1;
		sheet = workbook.getSheetAt(0);

		for (rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			row = sheet.getRow(rowIndex);
			if (row != null) {
				Cell cell = row.getCell(0);
				// Found column and there is value in the cell.
				if (cell != null) {

					String cellValue = cell.getStringCellValue();

					if (cellValue.equalsIgnoreCase(TCID) == true) {
						System.out.println("found value= " + cellValue + "row number= " + rowIndex);
						break;
					}
				}
			}
		}

		return rowIndex;
	}

	public String getCellData(String colName, int rowNum) {
		try {
			int col_Num = -1;
			// sheet = workbook.getSheet(sheetName);
			sheet = workbook.getSheetAt(0);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}

			row = sheet.getRow(rowNum);
			cell = row.getCell(col_Num);

			if (cell.getCellType() == CellType.STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
				String cellValue = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					DateFormat df = new SimpleDateFormat("dd/MM/yy");
					Date date = cell.getDateCellValue();
					cellValue = df.format(date);
				}
				return cellValue;
			} else if (cell.getCellType() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist  in Excel";
		}
	}

	public String getTestData(String TCID, String colName) {
		String TCData;

		int TCRow = getTCRow(TCID);
		if (TCRow != -1) {
			TCData = getCellData(colName, TCRow);

		} else {
			TCData = "TEST CASE ID NOT FOUND IN TCID COLUMN OF TEST DATA FILE";
		}

		return TCData;

	}

}