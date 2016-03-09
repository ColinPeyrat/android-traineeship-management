package info.iut.acy.fr.miniproject.Excel;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ExcelActivity extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName("Mon offre"));

        //create differrente row
        sheet.createRow(0);
        Row row1 = sheet.createRow(1);
        Row row2 = sheet.createRow(2);
        Row row3 = sheet.createRow(3);

        //student block
        //row 1
        createCell(sheet, workbook, row1, (short) 0, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Etudiant");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));
        //row2
        createCell(sheet, workbook,row2, (short) 0, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "mail");
        createCell(sheet, workbook,row2, (short) 1, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "nom");
        createCell(sheet, workbook,row2, (short) 2, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "prénom");
        //row3
        createCell(sheet, workbook,row3, (short) 0, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "benji.grus@icloud.com");
        createCell(sheet, workbook,row3, (short) 1, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "Grus");
        createCell(sheet, workbook,row3, (short) 2, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "Benjamin");

        //entreprise block
        //row 1
        createCell(sheet, workbook, row1, (short) 3, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Entreprise");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 18));
        //row 2
        createCell(sheet, workbook,row2, (short) 3, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "nom");
        createCell(sheet, workbook,row2, (short) 4, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "adresse");
        createCell(sheet, workbook,row2, (short) 5, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "code postal");
        createCell(sheet, workbook,row2, (short) 6, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "ville");
        createCell(sheet, workbook,row2, (short) 7, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "pays");
        createCell(sheet, workbook,row2, (short) 8, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "Nom du service");
        createCell(sheet, workbook,row2, (short) 9, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "adresse lieu de stage (si <> adresse entreprise)");
        createCell(sheet, workbook,row2, (short) 10, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "téléphone");
        createCell(sheet, workbook,row2, (short) 11, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "mail");
        createCell(sheet, workbook,row2, (short) 12, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "site internet");
        createCell(sheet, workbook,row2, (short) 13, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "taille");
        createCell(sheet, workbook,row2, (short) 14, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "nom représentant");
        createCell(sheet, workbook,row2, (short) 15, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "prénom représentant");
        createCell(sheet, workbook,row2, (short) 16, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "fonction représentant");
        createCell(sheet, workbook,row2, (short) 17, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "compétence informatique");
        createCell(sheet, workbook,row2, (short) 18, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "activité");
        //row 3
        createCell(sheet, workbook,row3, (short) 3, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "nom");
        createCell(sheet, workbook,row3, (short) 4, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "adresse");
        createCell(sheet, workbook,row3, (short) 5, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "code postal");
        createCell(sheet, workbook,row3, (short) 6, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "ville");
        createCell(sheet, workbook,row3, (short) 7, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "pays");
        createCell(sheet, workbook,row3, (short) 8, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "Nom du service");
        createCell(sheet, workbook,row3, (short) 9, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "adresse lieu de stage (si <> adresse entreprise)");
        createCell(sheet, workbook,row3, (short) 10, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "téléphone");
        createCell(sheet, workbook,row3, (short) 11, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "mail");
        createCell(sheet, workbook,row3, (short) 12, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "site internet");
        createCell(sheet, workbook,row3, (short) 13, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "taille");
        createCell(sheet, workbook,row3, (short) 14, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "nom représentant");
        createCell(sheet, workbook,row3, (short) 15, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "prénom représentant");
        createCell(sheet, workbook,row3, (short) 16, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "fonction représentant");
        createCell(sheet, workbook,row3, (short) 17, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "compétence informatique");
        createCell(sheet, workbook,row3, (short) 18, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "activité");

        //tuteur block
        //row 1
        createCell(sheet, workbook, row1, (short) 19, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Tuteur");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 19, 23));
        //row 2
        createCell(sheet, workbook,row2, (short) 19, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "nom");
        createCell(sheet, workbook,row2, (short) 20, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "prénom");
        createCell(sheet, workbook,row2, (short) 21, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "fonction");
        createCell(sheet, workbook,row2, (short) 22, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "mail");
        createCell(sheet, workbook,row2, (short) 23, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "téléphone");
        //row 3
        createCell(sheet, workbook,row3, (short) 19, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "nom");
        createCell(sheet, workbook,row3, (short) 20, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "prénom");
        createCell(sheet, workbook,row3, (short) 21, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "fonction");
        createCell(sheet, workbook,row3, (short) 22, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "mail");
        createCell(sheet, workbook,row3, (short) 23, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "téléphone");

        //stage block
        //row 1
        createCell(sheet, workbook, row1, (short) 24, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, "Stage");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 24, 29));
        //row 2
        createCell(sheet, workbook,row2, (short) 24, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "date début");
        createCell(sheet, workbook,row2, (short) 25, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "date de fin");
        createCell(sheet, workbook,row2, (short) 26, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "sujet");
        createCell(sheet, workbook,row2, (short) 27, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "compétences à acquérir");
        createCell(sheet, workbook,row2, (short) 28, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "activités confiées");
        createCell(sheet, workbook,row2, (short) 29, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "origine offre");
        //row 3
        createCell(sheet, workbook,row3, (short) 24, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "date début");
        createCell(sheet, workbook,row3, (short) 25, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "date de fin");
        createCell(sheet, workbook,row3, (short) 26, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "sujet");
        createCell(sheet, workbook,row3, (short) 27, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "compétences à acquérir");
        createCell(sheet, workbook,row3, (short) 28, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "activités confiées");
        createCell(sheet, workbook,row3, (short) 29, CellStyle.ALIGN_CENTER,CellStyle.VERTICAL_CENTER, "origine offre");


        String outFileName = "offreStage.xlsx";
        try {
            Log.i("Excel","writing file " + outFileName);
            File cacheDir = getCacheDir();
            File outFile = new File(cacheDir, outFileName);
            OutputStream outputStream = new FileOutputStream(outFile.getAbsolutePath());
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            /* proper exception handling to be here */
            Log.i("Excel", e.toString());
        }
    }

    /**
     * Creates a cell and aligns it a certain way.
     *
     * @param wb     the workbook
     * @param row    the row to create the cell in
     * @param column the column number to create the cell in
     * @param halign the horizontal alignment for the cell.
     */
    private static void createCell(Sheet sheet, Workbook wb, Row row, short column, short halign, short valign, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cell.setCellStyle(cellStyle);
    }
}