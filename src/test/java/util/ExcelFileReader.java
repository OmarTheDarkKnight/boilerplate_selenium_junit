package util;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelFileReader {
    private String filePath;
    private FileInputStream fileInputStream;
    private XSSFWorkbook workbook;
    private XSSFSheet workSheet;


    public ExcelFileReader(String filePath) {
        this.filePath = filePath;
        try {
            fileInputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInputStream);
            setWorkSheet(0);
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

    public ExcelFileReader(String filePath, int workSheetIndex) {
        this.filePath = filePath;
        try {
            fileInputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInputStream);
            setWorkSheet(workSheetIndex);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setWorkSheet(String sheetName) throws Exception {
        if(workbook != null) {
            workSheet = workbook.getSheet(sheetName);
        } else throw new Exception("File not set yet.");
    }

    public void setWorkSheet(int sheetIndex) throws Exception {
        if(workbook != null) {
            workSheet = workbook.getSheetAt(sheetIndex);
        } else throw new Exception("File not set yet.");
    }
}
