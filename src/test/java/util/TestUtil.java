package util;

import base.BaseTest;

public class TestUtil extends BaseTest {
    /**
     * returns the skip condition for a test
     * true if the value is Y
     * false if the value is N
     * */
    public static boolean isSkip(String testCase){
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
