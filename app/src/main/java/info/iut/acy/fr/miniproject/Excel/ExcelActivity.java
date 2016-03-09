package info.iut.acy.fr.miniproject.Excel;


import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import info.iut.acy.fr.miniproject.Database.InformationAdapter;
import info.iut.acy.fr.miniproject.Information.InformationActivity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExcelActivity extends Activity
{

    InformationAdapter InformationDB;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        InformationDB = new InformationAdapter(getApplicationContext());


    }

    @Override
    protected void onResume() {
        super.onResume();

        InformationDB.open();
        Cursor info = InformationDB.getAllInformation();
        info.moveToFirst();
        if(info.getCount() != 0 && info.getString(0).length() != 0 && info.getString(1).length() != 0 && info.getString(2).length() != 0 && info.getString(3).length() != 0){
            createOffreStage(info);
        }
        else{
            Intent infointent = new Intent(getApplicationContext(), InformationActivity.class);
            startActivity(infointent);
            Toast.makeText(getApplicationContext(), "Veuillez remplir vos information", Toast.LENGTH_SHORT).show();
            Log.i("Excel","redirect to myinformation");
        }
    }

    private void createOffreStage(Cursor info){
        AssetManager assetm = getAssets();

        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(assetm.open("offreStage.xlsx"));
        } catch (IOException e) {
            Log.i("Excel","Can't create file");
            Toast.makeText(getApplicationContext(), "Erreur lors de la création du fichier", Toast.LENGTH_SHORT).show();
        }

        XSSFSheet sheet = workbook.getSheetAt(0);

        Row row = sheet.getRow(3);

        //student block
        row.getCell(0).setCellValue(info.getString(2)); //mail
        row.getCell(1).setCellValue(info.getString(0)); //nom
        row.getCell(2).setCellValue(info.getString(1)); //prenom

        //company block
        row.getCell(3).setCellValue(""); //nom
        row.getCell(4).setCellValue(""); //adresse
        row.getCell(5).setCellValue(""); //cp
        row.getCell(6).setCellValue(""); //ville
        row.getCell(7).setCellValue(""); //pays
        row.getCell(8).setCellValue(""); //nom du service
        row.getCell(9).setCellValue(""); //adresse lieu de stage
        row.getCell(10).setCellValue(""); //telephone
        row.getCell(11).setCellValue(""); //mail
        row.getCell(12).setCellValue(""); //site internet
        row.getCell(13).setCellValue(""); //taille
        row.getCell(14).setCellValue(""); //nom du representant
        row.getCell(15).setCellValue(""); //prenom du representant
        row.getCell(16).setCellValue(""); //fonction du representant
        row.getCell(17).setCellValue(""); //competence info
        row.getCell(18).setCellValue(""); //activité

        //tuteur block
        row.getCell(19).setCellValue(""); //nom
        row.getCell(20).setCellValue(""); //prenom
        row.getCell(21).setCellValue(""); //fonction
        row.getCell(22).setCellValue(""); //mail
        row.getCell(23).setCellValue(""); //telephone

        //stage block
        row.getCell(24).setCellValue(""); //date debut
        row.getCell(25).setCellValue(""); //date fin
        row.getCell(26).setCellValue(""); //sujet
        row.getCell(27).setCellValue(""); //competence à acquerir
        row.getCell(28).setCellValue(""); //activite confiees
        row.getCell(29).setCellValue("last"); //origin offre

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
            Toast.makeText(getApplicationContext(), "Erreur lors de la création du fichier", Toast.LENGTH_SHORT).show();
        }
    }
}