package com.example.mywallet.service;

import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.dto.response.TransactionResponseDto;
import com.example.mywallet.entity.enums.CategoryTypeEnum;
import com.example.mywallet.entity.enums.TransactionStatusEnum;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class FileService {
    private static final String PATH = "src/main/resources/templates/";

    public String writeExcelFile(List<TransactionResponseDto> list) {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = getSheet(workbook, "ALL");
        Sheet sheet1 = getSheet(workbook, CategoryTypeEnum.INPUT.name());
        Sheet sheet2 = getSheet(workbook, CategoryTypeEnum.OUTPUT.name());
        Sheet sheet3 = getSheet(workbook, CategoryTypeEnum.UNKNOWN.name());
        header(sheet, workbook);
        header(sheet1, workbook);
        header(sheet2, workbook);
        header(sheet3, workbook);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        writeRow(list, sheet, style);

        List<TransactionResponseDto> list1 = list.stream().filter((t) -> t.getTransactionType().equals(CategoryTypeEnum.INPUT.name())).toList();
        writeRow(list1, sheet1, style);

        List<TransactionResponseDto> list2 = list.stream().filter((t) -> t.getTransactionType().equals(CategoryTypeEnum.OUTPUT.name())).toList();
        writeRow(list2, sheet2, style);

        List<TransactionResponseDto> list3 = list.stream().filter((t) -> t.getTransactionType().equals(CategoryTypeEnum.UNKNOWN.name())).toList();
        writeRow(list3, sheet3, style);

//        File currDir = new File(PATH);
//        String path = currDir.getAbsolutePath();
        String fileName = list.get(0).getName() + ".xlsx";
        String fileLocation = PATH + fileName;

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("File write exception");
        }
        return fileName;
    }

    public HttpEntity<ByteArrayResource> getFile(String name) {
        try {
            FileInputStream fileInputStream = new FileInputStream(PATH + name);
            InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);

            byte[] excelContent = inputStreamResource.getContentAsByteArray();

            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);


            return new HttpEntity<>(new ByteArrayResource(excelContent), header);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Sheet getSheet(Workbook workbook, String type) {
        Sheet sheet = workbook.createSheet(type);
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
        return sheet;
    }

    private void header(Sheet sheet, Workbook workbook) {
        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        headerStyle.setQuotePrefixed(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Amount");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Transaction_Status");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Transaction_Type");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Created_Date");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Category");
        headerCell.setCellStyle(headerStyle);
    }

    private CellStyle getStyle(Workbook workbook, TransactionStatusEnum statusEnum) {
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Lora");
        font.setFontHeightInPoints((short) 12);
        if (statusEnum.equals(TransactionStatusEnum.IN_PROGRESS)) {
            font.setColor(IndexedColors.YELLOW.getIndex());
        } else if (statusEnum.equals(TransactionStatusEnum.SUCCESS)) {
            font.setColor(IndexedColors.GREEN.getIndex());
        } else {
            font.setColor(IndexedColors.RED.getIndex());
        }
        headerStyle.setFont(font);
        return headerStyle;
    }

    private void writeRow(List<TransactionResponseDto> list, Sheet sheet, CellStyle style) {
        style.setWrapText(true);

        Cell cell;
        for (int j = 0; j < list.size(); j++) {
            Row row = sheet.createRow(j + 1);
            TransactionResponseDto responseDto = list.get(j);

            cell = row.createCell(0);
            cell.setCellValue(String.valueOf(responseDto.getId()));
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(responseDto.getAmount());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(responseDto.getStatus().name());
            CellStyle space = getStyle(sheet.getWorkbook(), responseDto.getStatus());
            cell.setCellStyle(space);

            cell = row.createCell(3);
            cell.setCellValue(responseDto.getTransactionType());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(responseDto.getCreatedDate());
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(responseDto.getName());
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(responseDto.getCategory().getName());
            cell.setCellStyle(style);

        }
    }
}
