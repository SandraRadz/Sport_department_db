
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class Export {

	public Export(JTable table, String nameOf, String fileName) throws IOException {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(nameOf);

		// List list = getRows(table);

		int rownum = 0;
		int columnCount = table.getColumnCount();
		int rowCount = table.getRowCount();
		Cell cell;
		Row row;
		//
		HSSFCellStyle style = createStyleForTitle(workbook);

		row = sheet.createRow(rownum);

		for (int i = 0; i < columnCount; ++i) {
			cell = row.createCell(i, CellType.STRING);
			cell.setCellValue(table.getColumnName(i));
			cell.setCellStyle(style);
		}

		int sum = 0;
		// Data
		for (int j = 0; j <= rowCount - 1; ++j) {
			rownum++;
			row = sheet.createRow(rownum);

			for (int k = 0; k <= columnCount - 1; ++k) {
				int strLen = 1;
				cell = row.createCell(k, CellType.STRING);
				int widthh = sheet.getColumnWidth(k);
				cell.setCellValue((String) table.getValueAt(sum, k));
				String str = (String) (table.getValueAt(sum, k));
				if(str!=null)
				{
					 strLen = str.length() * 170;
				}
				
				System.out.println(strLen);
				System.out.println(widthh); 
				if (strLen > widthh) {
					sheet.setColumnWidth(k, 6000);
				}else 
				if (strLen < widthh) {
					sheet.setColumnWidth(k, 3000);
				}
			}
			sum++;
		}

		File file = new File("D:/files/" + fileName + ".xls");
		file.getParentFile().mkdirs();

		FileOutputStream outFile = new FileOutputStream(file);
		workbook.write(outFile);
		System.out.println("Created file: " + file.getAbsolutePath());
	}

	private <T> List getRows(JTable table1) {
		List<T> mlist = new ArrayList<T>();

		int columnCount = table1.getColumnCount();
		int rowCount = table1.getRowCount();

		for (int i = 0; i <= rowCount; ++i) {
			String[] array = new String[columnCount];
			for (int j = 0; j <= columnCount; ++j) {
				String str = (String) table1.getValueAt(i, j);
				array[j] = str;
			}
			mlist.add((T) array);
		}

		return mlist;
	}

	private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		return style;
	}

}