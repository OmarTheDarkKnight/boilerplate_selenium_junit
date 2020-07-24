package util;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelFileReader {
    private String filePath;
    private FileInputStream fileInputStream;
    private XSSFWorkbook workbook;
    private XSSFSheet workSheet;
    private XSSFRow row;


    public ExcelFileReader(String filePath) {
        this.filePath = filePath;
        try {
            fileInputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInputStream);
            workSheet = workbook.getSheetAt(0);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ExcelFileReader(String filePath, String workSheetName) {
        this.filePath = filePath;
        try {
            fileInputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInputStream);
            setWorkSheet(workSheetName);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setWorkSheet(String sheetName) throws Exception {
        if(workbook != null) {
            int sheetIndex = workbook.getSheetIndex(sheetName);
            workSheet = sheetIndex == -1 ? workbook.getSheetAt(0) : workbook.getSheetAt(sheetIndex);
        } else throw new Exception("File not set yet.");
    }

    public XSSFSheet getWorkSheet() {
        return this.workSheet;
    }

    public int getRowCount() {
        return getWorkSheet().getLastRowNum()+1;
    }

    public int getRowCount(String sheetName) {
        try {
            setWorkSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRowCount();
    }

    public int getCellCount() {
        row = getWorkSheet().getRow(0);
        return row == null ? 0 : row.getLastCellNum()+1;
    }

    public int getCellCount(String sheetName) {
        try {
            setWorkSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getCellCount();
    }
}
