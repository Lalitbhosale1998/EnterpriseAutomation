package com.enterprise.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public final class ExcelUtil {

    private static final String EXCEL_PATH = "src/test/resources/testdata/TestData.xlsx";

    private ExcelUtil() {
        // Prevent instantiation
    }

    /**
     * Reads an Excel sheet and returns only the rows where Column A ('Execute') == 'Y'.
     * Each row is returned as a Map of ColumnHeader -> CellValue.
     */
    public static List<Map<String, String>> getExecutableTestData(String sheetName) {
        List<Map<String, String>> activeRows = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in " + EXCEL_PATH);
            }

            Row headerRow = sheet.getRow(0);
            int totalCols = headerRow.getLastCellNum();
            int totalRows = sheet.getLastRowNum();

            // Read header names
            List<String> headers = new ArrayList<>();
            for (int col = 0; col < totalCols; col++) {
                headers.add(headerRow.getCell(col).getStringCellValue().trim());
            }

            DataFormatter formatter = new DataFormatter();

            // Iterate data rows (starting from row index 1)
            for (int i = 1; i <= totalRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Column A (index 0) is the "Execute" flag
                String executeFlag = formatter.formatCellValue(row.getCell(0)).trim();

                if (executeFlag.equalsIgnoreCase("Y")) {
                    Map<String, String> rowData = new HashMap<>();
                    for (int col = 0; col < totalCols; col++) {
                        String header = headers.get(col);
                        String value = formatter.formatCellValue(row.getCell(col)).trim();
                        rowData.put(header, value);
                    }
                    activeRows.add(rowData);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file at: " + EXCEL_PATH, e);
        }

        return activeRows;
    }

    /**
     * Helper to fetch data for a specific test case name
     */
    public static Map<String, String> getTestCaseData(String sheetName, String testCaseName) {
        List<Map<String, String>> rows = getExecutableTestData(sheetName);
        for (Map<String, String> row : rows) {
            if (row.get("TestCaseName").equalsIgnoreCase(testCaseName)) {
                return row;
            }
        }
        return Collections.emptyMap(); // Returns empty if marked 'N' or not found
    }
}