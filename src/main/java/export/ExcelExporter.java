package export;

import model.ExcelSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

public class ExcelExporter
{
    public static final String EXPORT_PATH = "src/main/java/export/results";
    public static final String SEPARATOR = System.getProperty("file.separator");
    public static final String FILE_SUFIX = ".xlsx";

    public String export(ExcelSheet excelSheet) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(excelSheet.getName());

        writeHeaders(sheet, excelSheet.getHeaders());
        writeData(sheet, excelSheet.getRows());
        Path path = writeFile(excelSheet.getName(), workbook);

        return path.toString();
    }

    private Path writeFile(String fileName, Workbook workbook) throws IOException {
        Path path = createFile(fileName);
        FileOutputStream outputStream = new FileOutputStream(path.toAbsolutePath().toString());
        workbook.write(outputStream);
        workbook.close();
        return path;
    }

    private Path createFile(String fileName) {
        new File(EXPORT_PATH).mkdirs();
        String currentRunFileName = EXPORT_PATH + SEPARATOR + fileName + " - " + new Date() + FILE_SUFIX;
        return Paths.get(currentRunFileName);
    }

    private void writeData(Sheet excelSheet, List<List<String>> data) {
        int rows = data.size();
        int cels = data.get(0).size();

        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            Row sheetRow = excelSheet.createRow(rowIndex + 1);
            for (int cellIndex = 0; cellIndex < cels; cellIndex++) {
                Cell cell = sheetRow.createCell(cellIndex);
                cell.setCellValue(data.get(rowIndex).get(cellIndex));
            }
        }
    }

    private void writeHeaders(Sheet sheet, List<String> headers) {
        Row header = sheet.createRow(0);

        for (int i = 0; i < headers.size(); i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(headers.get(i));
        }
    }
}