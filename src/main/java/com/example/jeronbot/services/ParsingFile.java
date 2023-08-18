package com.example.jeronbot.services;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class ParsingFile {

    public Map<Integer, List<Object>> read(String filename) throws IOException {
        Workbook workbook = loadWorkbook(filename);
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        Map<Integer, List<Object>> map = new HashMap<>();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            map = processSheet(sheet);
        }
        workbook.close();
        return map;
    }

    private Workbook loadWorkbook(String filename) throws IOException {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        FileInputStream file = new FileInputStream(new File(filename));
        switch (extension) {
            case "xls":
                // old format
                return new HSSFWorkbook(file);
            case "xlsx":
                // new format
                return new XSSFWorkbook(file);
            default:
                throw new RuntimeException("Unknown Excel file extension: " + extension);
        }
    }

    private Map<Integer, List<Object>> processSheet(Sheet sheet) {
        HashMap<Integer, List<Object>> data = new HashMap<>();
        Iterator<Row> iterator = sheet.rowIterator();
        for (int rowIndex = 0; iterator.hasNext(); rowIndex++) {
            Row row = iterator.next();
            processRow(data, rowIndex, row);
        }
        return data;
    }

    private void processRow(HashMap<Integer, List<Object>> data, int rowIndex, Row row) {
        data.put(rowIndex, new ArrayList<>());
        for (int z=0;z<row.getHeight(); z++) {
            if (row.getCell(z) == null) {
                row.createCell(z).setCellValue("*");
            }
        }
        for (Cell cell : row) {
            processCell(cell, data.get(rowIndex));
        }
    }
    private void processCell(Cell cell, List<Object> dataRow) {
        switch (cell.getCellType()) {
            case STRING:
                dataRow.add(cell.getStringCellValue());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    dataRow.add(cell.getLocalDateTimeCellValue());
                } else {
                    dataRow.add(NumberToTextConverter.toText(cell.getNumericCellValue()));
                }
                break;
            case BOOLEAN:
                dataRow.add(cell.getBooleanCellValue());
                break;
            case FORMULA:
                dataRow.add(cell.getCellFormula());
                break;
            case _NONE:
                dataRow.add(cell.getColumnIndex());
                break;
            default:
                dataRow.add(" ");
        }


    }
}
