package info.iut.acy.fr.miniproject.Company;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.iut.acy.fr.miniproject.Database.TraineeshipAdapter;
import info.iut.acy.fr.miniproject.R;

public class EditCompanyActivity extends Activity implements OnClickListener{

    Long idCompany = null;

    private EditText companyNameEditText;
    private EditText companyAdressEditText;
    private EditText companyPostalEditText;
    private EditText companyCityEditText;
    private EditText companyCountryEditText;
    private EditText companyServiceEditText;
    private EditText companyPhoneEditText;
    private EditText companyMailEditText;
    private EditText companyWebSiteEditText;
    private EditText companySizeEditText;
    private EditText companyDescriptionEditText;


    TraineeshipAdapter TraineeshipDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company);

        TraineeshipDB = new TraineeshipAdapter(getApplicationContext());

        Button btnSubmit = (Button)findViewById(R.id.submitCompany);
        btnSubmit.setOnClickListener(this);


        Bundle extras = getIntent().getExtras();
        // Récupère l'id de l'entreprise sur laquelle on a cliqué
        if(extras != null) {
            idCompany = extras.getLong("idCompany");
        }


        // Binding des Edit text
        companyNameEditText = (EditText)findViewById(R.id.EditTextName);
        companyAdressEditText = (EditText)findViewById(R.id.EditTextAdresse);
        companyPostalEditText = (EditText)findViewById(R.id.EditTextPostal);
        companyCityEditText = (EditText)findViewById(R.id.EditTextCity);
        companyCountryEditText = (EditText)findViewById(R.id.EditTextCountry);
        companyServiceEditText = (EditText)findViewById(R.id.EditTextService);
        companyMailEditText = (EditText)findViewById(R.id.EditTextMail);
        companyPhoneEditText = (EditText)findViewById(R.id.EditTextPhone);
        companyWebSiteEditText = (EditText)findViewById(R.id.EditTextWebsite);
        companySizeEditText = (EditText)findViewById(R.id.EditTextSize);
        companyDescriptionEditText = (EditText)findViewById(R.id.EditTextDescription);



    }

    @Override
    protected void onResume() {
        super.onResume();
        TraineeshipDB.open();
        populate(idCompany);

    }

    public void populate(long idCompany){
        Cursor company = TraineeshipDB.getSingleCompany(idCompany);
        if(company.getCount() > 0){
            company.moveToFirst();
            companyNameEditText.setText(company.getString(company.getColumnIndexOrThrow(TraineeshipAdapter.KEY_NAME)));
            companyAdressEditText.setText(company.getString(company.getColumnIndexOrThrow(TraineeshipAdapter.KEY_ADRESS)));
            companyPostalEditText.setText(company.getString(company.getColumnIndexOrThrow(TraineeshipAdapter.KEY_POSTAL)));
            companyCityEditText.setText(company.getString(company.getColumnIndexOrThrow(TraineeshipAdapter.KEY_TOWN)));
            companyCountryEditText.setText(company.getString(company.getColumnIndexOrThrow(TraineeshipAdapter.KEY_COUNTRY)));
            companyServiceEditText.setText(company.getString(company.getColumnIndexOrThrow(TraineeshipAdapter.KEY_SERVICE)));
            companyPhoneEditText.setText(company.getString(company.getColumnIndexOrThrow(TraineeshipAdapter.KEY_PHONE)));
            companyMailEditText.setText(company.getString(company.getColumnIndexOrThrow(TraineeshipAdapter.KEY_MAIL)));
            companyWebSiteEditText.setText(company.getString(company.getColumnIndexOrThrow(TraineeshipAdapter.KEY_WEBSITE)));
            companySizeEditText.setText(company.getString(company.getColumnIndexOrThrow(TraineeshipAdapter.KEY_SIZE)));
            companyDescriptionEditText.setText(company.getString(company.getColumnIndexOrThrow(TraineeshipAdapter.KEY_DESCRIPTION)));
        }
        else
            finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitCompany:

                String companyName = companyNameEditText.getText().toString();
                String companyAdress = companyAdressEditText.getText().toString();
                String companyPostal = companyPostalEditText.getText().toString();
                String companyCity = companyCityEditText.getText().toString();
                String companyCountry = companyCountryEditText.getText().toString();
                String companyService = companyServiceEditText.getText().toString();
                String companyPhone = companyPhoneEditText.getText().toString();
                String companyMail = companyMailEditText.getText().toString();
                String companyWebsite = companyWebSiteEditText.getText().toString();
                String companySize = companySizeEditText.getText().toString();
                String companyDescription = companyDescriptionEditText.getText().toString();

                // Vérification que tous les champs sont renseignés
                if( companyName.trim().equals("")) {
                    companyNameEditText.setError("Le nom de l'entreprise est requis");

                } else if(companyAdress.trim().equals("")) {
                    companyAdressEditText.setError("L'adresse est obligatoire");

                } else if(companyPostal.trim().equals("")) {
                    companyPostalEditText.setError("Le code postal est obligatoire");
                } else if(!isValidPostalCode(companyPostal)){
                    companyPostalEditText.setError("Le code postal est invalide");

                } else if(companyCity.trim().equals("")) {
                    companyCityEditText.setError("La ville est obligatoire");

                } else if(companyCountry.trim().equals("")){
                    companyCountryEditText.setError("Le pays est obligatoire");

                    // Vérifier qu'une forme de contact est bien rentré
                } else if(companyPhone.trim().equals("")  && companyMail.trim().equals("")) {
                    companyPhoneEditText.setError("Vous devez renseigner au moins un champ de contact");
                    companyMailEditText.setError("Vous devez renseigner au moins un champ de contact");
                } else if(!companyPhone.trim().equals("") && !isValidPhoneNumber(companyPhone)){
                    companyPhoneEditText.setError("Numéro de télephone invalide");
                    companyMailEditText.setError(null);

                } else if(!companyMail.trim().equals("") && !isValidEmail(companyMail)){
                    companyPhoneEditText.setError(null);
                    companyMailEditText.setError("Email invalide");
                }else{
                    Toast.makeText(getApplicationContext(), "Entreprise modifié", Toast.LENGTH_LONG).show();

                    //Suppression des erreurs
                    companyPhoneEditText.setError(null);
                    companyMailEditText.setError(null);

                    TraineeshipDB.updateCompany(idCompany,companyName,companyAdress,companyPostal,companyCity,companyCountry,companyService,companyPhone,companyMail,companyWebsite,companySize,companyDescription);
                    finish();
                }
                break;

        }

    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Validation du Code postal
    private boolean isValidPostalCode(String postal) {
        String POSTAL_PATTERN = "^[0-9]{5,5}$";
        Pattern pattern = Pattern.compile(POSTAL_PATTERN);
        Matcher matcher = pattern.matcher(postal);
        return matcher.matches();
    }
    // Validation du Numero de Telephone
    private boolean isValidPhoneNumber(String phone) {
        String POSTAL_PATTERN = "^(\\d\\d){5}$";
        Pattern pattern = Pattern.compile(POSTAL_PATTERN);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
