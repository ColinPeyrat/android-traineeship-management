package info.iut.acy.fr.miniproject.Company;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.iut.acy.fr.miniproject.Database.TraineeshipAdapter;
import info.iut.acy.fr.miniproject.R;


public class AddCompanyActivity extends Activity {

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
        setContentView(R.layout.activity_add_company);

        TraineeshipDB = new TraineeshipAdapter(getApplicationContext());


        Button btnSubmint = (Button)findViewById(R.id.submitCompany);
        btnSubmint.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    Toast.makeText(getApplicationContext(), "Entreprise ajoutée", Toast.LENGTH_LONG).show();

                    //Suppression des erreurs
                    companyPhoneEditText.setError(null);
                    companyMailEditText.setError(null);

                    TraineeshipDB.insertCompany(companyName,companyAdress,companyPostal,companyCity,companyCountry,companyService,companyPhone,companyMail,companyWebsite,companySize,companyDescription);
                    finish();
                }



            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        TraineeshipDB.open();

    }
    /**
     * Méthode de validation que l'email est bien de la forme du EMAIL PATTERN
     *
     * @param email à validé
     */
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_add_company, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
