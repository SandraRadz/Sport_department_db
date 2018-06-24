
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
		HSSFCellStyle txtStyle = (HSSFCellStyle) workbook.createCellStyle();
		HSSFFont txtFont = (HSSFFont) workbook.createFont();
		txtFont.setFontName("Arial");
		txtFont.setFontHeight((short) (8 * 20));
		txtStyle.setFont(txtFont);

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

				cell = row.createCell(k, CellType.STRING);
				cell.setCellValue((String) table.getValueAt(sum, k));
				cell.setCellStyle(txtStyle);
				String str = (String) (table.getValueAt(sum, k));
				int strLen = str.length();
				if (strLen == 0) {
					strLen = 1;
				}

				sheet.autoSizeColumn(k);

			}
			sum++;
		}

		String fileName1 = transliterate(fileName);

		File file = new File("D:/files/" + fileName1 + ".xls");
		file.getParentFile().mkdirs();

		FileOutputStream outFile = new FileOutputStream(file);
		workbook.write(outFile);
		System.out.println("Created file: " + file.getAbsolutePath());
	}

	public static String transliterate(String message) {
		char[] abcCyr = { ' ', 'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'і', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с',
				'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'є', 'ю', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'Й',
				'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Є', 'Ю', 'Я', 'a', 'b',
				'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		String[] abcLat = { "_", "a", "b", "v", "g", "d", "e", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p", "r",
				"s", "t", "u", "f", "h", "ts", "ch", "sh", "sch", "e", "ju", "ja", "A", "B", "V", "G", "D", "E", "Zh",
				"Z", "I", "Y", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "Sh", "Sch", "E",
				"Ju", "Ja", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
				"s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "0" };
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < message.length(); i++) {
			for (int x = 0; x < abcCyr.length; x++) {
				if (message.charAt(i) == abcCyr[x]) {
					builder.append(abcLat[x]);
				}
			}
		}
		return builder.toString();
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