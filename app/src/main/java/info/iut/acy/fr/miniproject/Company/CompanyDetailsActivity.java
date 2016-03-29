package info.iut.acy.fr.miniproject.Company;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import info.iut.acy.fr.miniproject.Database.InformationAdapter;
import info.iut.acy.fr.miniproject.Database.TraineeshipAdapter;
import info.iut.acy.fr.miniproject.Information.InformationActivity;
import info.iut.acy.fr.miniproject.R;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CompanyDetailsActivity extends Activity {

    TraineeshipAdapter TraineeshipDB;
    InformationAdapter InformationDB;

    Long idCompany = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        TraineeshipDB = new TraineeshipAdapter(getApplicationContext());
        InformationDB = new InformationAdapter(getApplicationContext());
        TraineeshipDB.open();
        InformationDB.open();


        if(TraineeshipDB.isOneTraineeshipAlreadyAccepted()){
            Log.d("CompanyDetails","Une offre déja accepté");

        } else {
            Log.d("CompanyDetails","Aucune offre encore accepté");

        }

        // Binding des TextView
        TextView tvName = (TextView)findViewById(R.id.tvName);
        TextView tvAdress = (TextView)findViewById(R.id.tvAdress);
        TextView tvCp = (TextView)findViewById(R.id.tvCp);
        TextView tvCity = (TextView)findViewById(R.id.tvCity);
        TextView tvCountry = (TextView)findViewById(R.id.tvCountry);
        TextView tvService = (TextView)findViewById(R.id.tvService);
        TextView tvPhone = (TextView)findViewById(R.id.tvPhone);
        TextView tvMail = (TextView)findViewById(R.id.tvMail);
        TextView tvWebsite = (TextView)findViewById(R.id.tvWebsite);
        TextView tvSize = (TextView)findViewById(R.id.tvSize);
        TextView tvDescription = (TextView)findViewById(R.id.tvDescription);

        final Button btnAccepted = (Button)findViewById(R.id.btnAccepted);

        Bundle extras = getIntent().getExtras();
        // recupère l'id de l'entreprise sur laquelle on a cliqué
        if(extras != null) {
            if(extras.get("idCompany") instanceof Long)
                idCompany = extras.getLong("idCompany");
            else if(extras.get("idCompany") instanceof Integer)
                idCompany = Long.valueOf(extras.getInt("idCompany"));
        }

        Cursor company = TraineeshipDB.getSingleCompany(idCompany);

        if(company.getCount() > 0){
            company.moveToFirst();
            // Curseur récupérant les champs de la base
            final String companyCursor = company.getString(company.getColumnIndex(TraineeshipDB.KEY_NAME));
            String serviceCursor = company.getString(company.getColumnIndex(TraineeshipDB.KEY_SERVICE));
            final String mailCursor = company.getString(company.getColumnIndex(TraineeshipDB.KEY_MAIL));
            final String phoneCursor = company.getString(company.getColumnIndex(TraineeshipDB.KEY_PHONE));
            String websiteCursor = company.getString(company.getColumnIndex(TraineeshipDB.KEY_WEBSITE));
            String sizeCursor = company.getString(company.getColumnIndex(TraineeshipDB.KEY_SIZE));
            String descriptionCursor = company.getString(company.getColumnIndex(TraineeshipDB.KEY_WEBSITE));

            tvName.setText(companyCursor);
            tvAdress.setText(company.getString(company.getColumnIndex(TraineeshipDB.KEY_ADRESS)));
            tvCp.setText(company.getString(company.getColumnIndex(TraineeshipDB.KEY_POSTAL)));
            tvCity.setText(company.getString(company.getColumnIndex(TraineeshipDB.KEY_TOWN)));
            tvCountry.setText(company.getString(company.getColumnIndex(TraineeshipDB.KEY_COUNTRY)));


            if(serviceCursor.length() > 0) {
                tvService.setText(serviceCursor);
            }

            if(mailCursor.length() > 0){
                tvMail.setText(setStringUnderline(mailCursor));
                tvMail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getApplicationContext(), "Préparation de l'envoi d'un email a l'entreprise " + companyCursor, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL, new String[]{mailCursor});
                        try {
                            startActivity(Intent.createChooser(i, "Envoyer le mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getApplicationContext(), "Il n'y a pas d'application mail installé..." + companyCursor, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            if(phoneCursor.length() > 0) {
                tvPhone.setText(setStringUnderline(phoneCursor));
                tvPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneCursor, null)));
                        Toast.makeText(getApplicationContext(), "Appel de l'entreprise " + companyCursor, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            if(websiteCursor.length() > 0) {
                tvWebsite.setText(websiteCursor);
            }

            if(sizeCursor.length() > 0) {
                tvSize.setText(sizeCursor);
            }

            if(descriptionCursor.length() > 0) {
                tvDescription.setText(descriptionCursor);
            }


        } else {
            finish();
            Toast.makeText(getApplicationContext(), "Impossible de récuperer l'entreprise", Toast.LENGTH_SHORT).show();
        }

        final Long finalIdCompany = idCompany;
        btnAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TraineeshipDB.isOneTraineeshipAlreadyAccepted()){
                    Cursor oldTraineeshipAccepted = TraineeshipDB.getTraineeshipAccepted();
                    if(oldTraineeshipAccepted.getCount() > 0) {
                        oldTraineeshipAccepted.moveToFirst();

                        int idOldTraineeshipAccepted = oldTraineeshipAccepted.getInt(oldTraineeshipAccepted.getColumnIndexOrThrow(TraineeshipDB.KEY_ID));
                        String nameOldTraineeshipAccepted = oldTraineeshipAccepted.getString(oldTraineeshipAccepted.getColumnIndexOrThrow(TraineeshipDB.KEY_NAME));


                        //si cette offre est déja l'offre de stage validé
                        if(finalIdCompany == idOldTraineeshipAccepted){

                            Toast.makeText(getApplicationContext(), "Cette offre de stage a déja été validé", Toast.LENGTH_SHORT).show();

                        // sinon
                        } else{
                            Toast.makeText(getApplicationContext(), "L'ancienne offre de stage validé de " +nameOldTraineeshipAccepted+ " a été remplacée par celle ci", Toast.LENGTH_SHORT).show();
                        }

                        TraineeshipDB.setTraineeshipAccepted(idOldTraineeshipAccepted, 1);
                        TraineeshipDB.setTraineeshipAccepted(finalIdCompany,0);
                    }

                } else {
                    TraineeshipDB.setTraineeshipAccepted(finalIdCompany,0);
                }

                generateExcel();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_company_details, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(getApplicationContext(), EditCompanyActivity.class);
                //send to the details activity the id of the company clicked
                intent.putExtra("idCompany", idCompany);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public SpannableString setStringUnderline(String string){
        SpannableString spanString = new SpannableString(string);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        return spanString;
    }

    private void generateExcel(){
        Cursor info = InformationDB.getAllInformation();
        info.moveToFirst();

        Cursor company = TraineeshipDB.getAllCompanyOrderByAccepted();
        company.moveToFirst();
        company = TraineeshipDB.getSingleCompany(company.getInt(company.getColumnIndexOrThrow(TraineeshipDB.KEY_ID)));
        company.moveToFirst();

        // vérifie que chaque champs de la table informations est rempli
        if(info.getCount() != 0 && info.getString(0).length() != 0 && info.getString(1).length() != 0 && info.getString(2).length() != 0 && info.getString(3).length() != 0){
            createOffreStage(info,company);
        }
        // renvoie vers la vue ou on remplit ses informations
        else{
            Intent infointent = new Intent(getApplicationContext(), InformationActivity.class);
            startActivity(infointent);
            Toast.makeText(getApplicationContext(), "Veuillez remplir vos information", Toast.LENGTH_SHORT).show();
            Log.i("Excel", "redirect to myinformation");
        }
    }

    /**
     * Méthode de création d'une offre de stage, remplis automatiquement chaque champs de la feuille "offreStage.xlsx"
     * @param info Curseur qui contient les infos de la table 'information'
     * @throws IOException Si erreur lors de la création du fichier
     */
    private void createOffreStage(Cursor info, Cursor company){
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

        //Bloc élève rempli avec le curseur
        row.getCell(0).setCellValue(info.getString(info.getColumnIndexOrThrow(InformationDB.KEY_EMAIL))); //mail
        row.getCell(1).setCellValue(info.getString(info.getColumnIndexOrThrow(InformationDB.KEY_NAME))); //nom
        row.getCell(2).setCellValue(info.getString(info.getColumnIndexOrThrow(InformationDB.KEY_FIRSTNAME))); //prenom

        //bloc de l'entreprise rempli avec un curseur de la table entreprise
        row.getCell(3).setCellValue(company.getString(company.getColumnIndexOrThrow(TraineeshipDB.KEY_NAME))); //nom
        row.getCell(4).setCellValue(company.getString(company.getColumnIndexOrThrow(TraineeshipDB.KEY_ADRESS))); //adresse
        row.getCell(5).setCellValue(company.getString(company.getColumnIndexOrThrow(TraineeshipDB.KEY_POSTAL))); //cp
        row.getCell(6).setCellValue(company.getString(company.getColumnIndexOrThrow(TraineeshipDB.KEY_TOWN))); //ville
        row.getCell(7).setCellValue(company.getString(company.getColumnIndexOrThrow(TraineeshipDB.KEY_COUNTRY))); //pays
        row.getCell(8).setCellValue(company.getString(company.getColumnIndexOrThrow(TraineeshipDB.KEY_SERVICE))); //nom du service
        row.getCell(9).setCellValue(""); //adresse lieu de stage
        row.getCell(10).setCellValue(company.getString(company.getColumnIndexOrThrow(TraineeshipDB.KEY_PHONE))); //telephone
        row.getCell(11).setCellValue(company.getString(company.getColumnIndexOrThrow(TraineeshipDB.KEY_MAIL))); //mail
        row.getCell(12).setCellValue(company.getString(company.getColumnIndexOrThrow(TraineeshipDB.KEY_WEBSITE))); //site internet
        row.getCell(13).setCellValue(company.getString(company.getColumnIndexOrThrow(TraineeshipDB.KEY_SIZE))); //taille
        row.getCell(14).setCellValue(""); //nom du representant
        row.getCell(15).setCellValue(""); //prenom du representant
        row.getCell(16).setCellValue(""); //fonction du representant
        row.getCell(17).setCellValue(""); //competence info
        row.getCell(18).setCellValue(""); //activité

        // Bloc du tuteur
        row.getCell(19).setCellValue(""); //nom
        row.getCell(20).setCellValue(""); //prenom
        row.getCell(21).setCellValue(""); //fonction
        row.getCell(22).setCellValue(""); //mail
        row.getCell(23).setCellValue(""); //telephone

        //Bloc du stage
        row.getCell(24).setCellValue(""); //date debut
        row.getCell(25).setCellValue(""); //date fin
        row.getCell(26).setCellValue(company.getString(company.getColumnIndexOrThrow(TraineeshipDB.KEY_DESCRIPTION))); //sujet
        row.getCell(27).setCellValue(""); //competence à acquerir
        row.getCell(28).setCellValue(""); //activite confiees
        row.getCell(29).setCellValue(""); //origin offre

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
            Log.i("Excel", e.toString());
            Toast.makeText(getApplicationContext(), "Erreur lors de la création du fichier", Toast.LENGTH_SHORT).show();
        }
    }
}
