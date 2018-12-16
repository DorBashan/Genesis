package export;

import com.google.common.collect.Lists;
import model.ExcelSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ExcelExporterTest
{
    private static final String TEST_FILE_NAME = "Test";
    private final ExcelExporter excelExporter = new ExcelExporter();

    @AfterAll
    public static void deleteTestFilesIfExists() {
        File dir = new File(ExcelExporter.EXPORT_PATH);
        if (dir.exists()) {
            Stream.of(dir.listFiles()).filter(f->f.getName().contains(TEST_FILE_NAME)).forEach(File::delete);
        }
    }

    @Test
    public void exportEmptySheet_shouldThrowExceptionEmptySheet() {

    }

    @Test
    public void exportSheetOnlyHeaders_shouldThrowExceptionEmptySheet() {

    }

    @Test
    public void exportSheetOnlyRows_shouldThrowExceptionNoHeaders() {

    }

    @Test
    public void exportSheetOneRow_shouldCreateSheetOneRow() throws IOException, InvalidFormatException {
        ExcelSheet excelSheet = new ExcelSheet("Test");
        excelSheet.setHeaders(Lists.newArrayList("Test Header 1", "Test Header 2"));
        excelSheet.setRows(Lists.newArrayList(
                Lists.newArrayList("Row1Cel1", "Row2Cel2"),
                Lists.newArrayList("Row2Cell2", "Row2Cell2")
        ));

        String path = excelExporter.export(excelSheet);
        File excelFile = new File(path);

        assertTrue(excelFile.exists());
        assertFileContent(excelFile, excelSheet);
    }

    private void assertFileContent(File created, ExcelSheet data) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(created);
        Sheet sheet = workbook.getSheet(data.getName());
        assertNotNull(sheet);
        assertExcelHeaders(data.getHeaders(), sheet);
        assertExcelData(data.getRows(), sheet);
    }

    private void assertExcelHeaders(List<String> headers, Sheet sheet) {
        Row headerRow = sheet.getRow(0);

        for (Cell cell : headerRow) {
            assertEquals(headers.get(cell.getColumnIndex()), cell.getStringCellValue());
        }
    }

    private void assertExcelData(List<List<String>> data, Sheet sheet) {
        for (Row row: sheet) {
            if (row.getRowNum() != 0) {
                List<String> rowData = data.get(row.getRowNum() - 1);
                for(Cell cell: row) {
                    assertEquals(rowData.get(cell.getColumnIndex()), cell.getStringCellValue());
                }
            }
        }
    }
}