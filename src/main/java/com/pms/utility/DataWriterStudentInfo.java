package com.pms.utility;

import com.pms.entity.Company;
import com.pms.entity.Project;
import com.pms.entity.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;

public class DataWriterStudentInfo {

    private static final SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
    private static final Integer numCol = 23;

    public static String getHeader(String hashVal) {
        StringBuilder sb = new StringBuilder();

        sb.append(hashVal).append("%");
        sb.append("ID NO%");
        sb.append("Project ID%");
        sb.append("First Name%");
        sb.append("Middle Name%");
        sb.append("Last Name%");
        sb.append("Internal Guide-1%");
        sb.append("Internal Guide-2%");
        sb.append("Phone #%");
        sb.append("Email%");
        sb.append("Confirmation Letter Submitted (Y)%");
        sb.append("Name of Company%");
        sb.append("Project Title%");
        sb.append("Tools%");
        sb.append("Technologies%");
        sb.append("Address of the Company%");
        sb.append("Joining Date%");
        sb.append("Contact Number of the Company%");
        sb.append("Name of Company Coordinator (External Guide)%");
        sb.append("Contact Number (External Guide)%");
        sb.append("Email Address (External Guide)%");
        sb.append("Name of HR Person%");
        sb.append("Contact No. of HR\n");

        return sb.toString();
    }

    public static String getContent(Student tempStudent) {
        Project p = tempStudent.getProject();
        Company c = p == null ? null : p.getCompany();

        boolean notNullP = p != null;

        StringBuilder sb = new StringBuilder();

        sb.append(tempStudent.getUsername()).append("%");

        if (notNullP && p.getProjectId() != null) {
            sb.append(p.getProjectId());
        }
        sb.append("%");

        sb.append(tempStudent.getFirstname()).append("%");

        if (tempStudent.getMiddlename() != null) {
            sb.append(tempStudent.getMiddlename());
        }
        sb.append("%");

        sb.append(tempStudent.getLastname()).append("%");

        if (notNullP && p.getPrimaryGuide() != null) {
            sb.append(p.getPrimaryGuide().getName());
        }
        sb.append("%");

        if (notNullP && p.getSecondaryGuide() != null) {
            sb.append(p.getSecondaryGuide().getName());
        }
        sb.append("%");

        if (tempStudent.getPhone() != null) {
            sb.append(tempStudent.getPhone());
        }
        sb.append("%");

        sb.append(tempStudent.getEmail()).append("%");

        if (tempStudent.getConfLetterStatus() == 1) {
            sb.append("Y");
        }
        sb.append("%");

        if (notNullP) {
            sb.append(c.getName()).append("%");
            sb.append(p.getTitle()).append("%");
//only csv    sb.append("\"").append(p.getTools()).append("\"").append("%");
//            sb.append("\"").append(p.getTechnologies()).append("\"").append("%");

            sb.append(p.getTools()).append("%");
            sb.append(p.getTechnologies()).append("%");

            sb.append(c.getAddress()).append("%");
            sb.append(format.format(p.getStartingDate())).append("%");
            sb.append(c.getContactno()).append("%");
            sb.append(c.getExternalGuideName()).append("%");
            sb.append(c.getExternalGuideContact()).append("%");
            sb.append(c.getExternalGuideEmail()).append("%");
            sb.append(c.getHrname()).append("%");
            sb.append(c.getHrContact());
        } else {
            sb.append("\t%\t%\t%\t%\t%\t%\t%\t%\t%\t%\t%\t%");
        }

        sb.append("\n");

        return sb.toString();
    }


    public static byte[] csv2excel(String inputCSVFile, String delimeter) {
        try {
            InputStream InputStream = new ByteArrayInputStream(inputCSVFile.getBytes(StandardCharsets.UTF_8));


            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("Students Information");
            String currentLine;
            int RowNum = 0;
            BufferedReader br = new BufferedReader(new InputStreamReader(InputStream));

            XSSFCellStyle style = getBorderedStyle(workBook);

            XSSFCellStyle emptyStyle = getBorderedStyle(workBook);
            emptyStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            emptyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            while ((currentLine = br.readLine()) != null) {
                String[] str = currentLine.split(delimeter);
                XSSFRow currentRow = sheet.createRow(RowNum);
                for (int i = 0; i < str.length; i++) {
                    Cell curCell = currentRow.createCell(i);
                    curCell.setCellValue(str[i]);
                    curCell.setCellStyle(style);
                    if (curCell.getStringCellValue() == null || curCell.getStringCellValue().trim().equals("")) {
                        curCell.setCellStyle(emptyStyle);
                    }
                }
                RowNum++;
            }

            makeRowBold(workBook, sheet.getRow(0));

            for (int i = 0; i < numCol; i++) {
                sheet.autoSizeColumn(i);
            }


            ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
            workBook.write(fileOutputStream);
            return fileOutputStream.toByteArray();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private static void makeRowBold(XSSFWorkbook wb, Row row) {
        CellStyle style = getBorderedStyle(wb);//Create style
        Font font = wb.createFont();//Create font
        font.setBold(true);//Make font bold
        style.setFont(font);//set it to bold
        style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        row.setHeight((short) 700);

        for (int i = 0; i < row.getLastCellNum(); i++) {
            row.getCell(i).setCellStyle(style);
        }
    }

    private static XSSFCellStyle getBorderedStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        return style;
    }
}
