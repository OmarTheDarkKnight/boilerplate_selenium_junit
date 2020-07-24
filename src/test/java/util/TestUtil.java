package util;

import base.BaseTest;

public class TestUtil extends BaseTest {
    /**
     * Reads data from @sheetName and populates the Object[][] data array
     * returns the data array
     * */
    public Object[][] readData(String sheetName) {
        if(excelFileReader == null) {
            excelFileReader = new ExcelFileReader(xlsxFilePath);
        }

        Object[][] data = null;
        try {
            int rowLength = excelFileReader.getRowCount(sheetName);
            int cellLength = excelFileReader.getCellCount();
            data = new Object[rowLength][cellLength];

            for(int rowNum = 1; rowNum < rowLength; rowNum++) {
                for(int cellNum = 0; cellNum < cellLength; cellNum++) {
                    data[rowNum-1][cellNum] = excelFileReader.getCellData(rowNum, cellNum);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * returns the skip or run condition for a test
     * true if the value is Y
     * false if the value is N
     * */
    public static boolean runTest(String testCase){
        try {
            int rowLength = excelFileReader.getRowCount("Test Cases");
            for(int rowNum = 1; rowNum < rowLength; rowNum++) {
                if(testCase.equals(excelFileReader.getCellData(rowNum, "Test Class Name"))){
                    return excelFileReader.getCellData(rowNum, "Run Mode").equals("Y");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
