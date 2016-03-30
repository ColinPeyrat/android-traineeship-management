package info.iut.acy.fr.miniproject.Information;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import info.iut.acy.fr.miniproject.Database.InformationAdapter;
import info.iut.acy.fr.miniproject.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InformationActivity extends Activity implements View.OnClickListener {

    InformationAdapter InformationDB;

    // Nommage des Edit Text du layout
    EditText information_name;
    EditText information_firstname;
    EditText information_email;
    EditText information_email_responsable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Button btnSaveMyInformation = (Button)findViewById(R.id.btnSubmitMyInformation);
        btnSaveMyInformation.setOnClickListener(this);

        // Binding des Edit text du layout
        information_name = (EditText)findViewById(R.id.txtName);
        information_firstname = (EditText)findViewById(R.id.txtFirstname);
        information_email = (EditText)findViewById(R.id.txtEmail);
        information_email_responsable = (EditText)findViewById(R.id.txtEmailResponsable);

        InformationDB = new InformationAdapter(getApplicationContext());
    }

    @Override
    /**
     * Appelé quand l'activité va intéragir avec l'utilisateur, ouvre la base de données
     */
    protected void onResume() {
        super.onResume();
        InformationDB.open();
        populate();
    }

    @Override
    /**
     * Métode OnClick qui réagit en fonction de l'item du menu qui a été cliqué
     *
     * @param v  La vue avec le formulaire
     */
    public void onClick(View v) {
        switch (v.getId()){
            // Quand le bouton est cliqué
            case R.id.btnSubmitMyInformation:
                // Le champs 'nom' doit être rempli et être seulement composé de lettres sinon affiche un message d'erreur et un log "nom faux"
                if(information_name.getText().length() == 0 || !isValidString(information_name.getText().toString(), "[A-Za-z]+")){
                    information_name.setError("Le nom est obligatoire et doit comporter que des lettres");
                    Log.i("InformationActivity", "nom faux");
                }
                // Le champs 'Prenom' doit être rempli et être seulement composé de lettres sinon affiche un message d'erreur et un log "prenom faux"
                else if(information_firstname.getText().length() == 0 || !isValidString(information_firstname.getText().toString(),"[A-Za-z]+")){
                    information_firstname.setError("Le prénom est obligatoire et doit comporter que des lettres");
                    Log.i("InformationActivity", "prenom faux");
                }
                // Le champs 'Email' doit être rempli et être de la forme 'Adresse@email.nomdomaine' sinon affiche message d'erreur et un log 'mail faux'
                else if(information_email.getText().length() == 0 || !isValidString(information_email.getText().toString(), "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                    information_email.setError("L'email est obligatoire et doit se présenter comme ceci : adresse@email.fr");
                    Log.i("InformationActivity", "email faux");
                // Même chose que pour un email classique
                } else if(information_email_responsable.getText().length() == 0 || !isValidString(information_email_responsable.getText().toString(), "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
                    information_email_responsable.setError("L'email est obligatoire et doit se présenter comme ceci : adresse@email.fr");
                    Log.i("InformationActivity", "email faux");
                // si tout marche , effectue un insert dans la base pour mettre à jour ses informations, met à jour la table 'information' avec les champs remplis, affiche un log de confirmation
                } else{
                    InformationDB.insertOrUpdateMyInformation(information_name.getText().toString(), information_firstname.getText().toString(), information_email.getText().toString());
                    Log.i("InformationActivity", "enregistrement des information");
                    InformationDB.insertOrUpdateResponsable(information_email_responsable.getText().toString());
                    Log.i("InformationActivity", "enregistrement des information");
                    // Toast informant l'utilisateur que tout s'est bien passé
                    Toast.makeText(getApplicationContext(), "Vos informations ont bien été mise à jour", Toast.LENGTH_LONG).show();
                    finish();

                }
                break;
        }
    }

    /**
     * Métode avec un curseur qui parcours les informations de la base pour remplir chacun des champs avec le champs correspondant de la table information de la base
     *
     */
    public void populate(){
        Cursor info = InformationDB.getAllInformation();
        if(info.getCount() > 0){
            info.moveToFirst();
            information_name.setText(info.getString(info.getColumnIndex(InformationDB.KEY_NAME)));
            information_firstname.setText(info.getString(info.getColumnIndex(InformationDB.KEY_FIRSTNAME)));
            information_email.setText(info.getString(info.getColumnIndex(InformationDB.KEY_EMAIL)));
            information_email_responsable.setText(info.getString(info.getColumnIndex(InformationDB.KEY_EMAIL_RESPONSABLE)));
        }
        else
            information_email_responsable.setText("nathalie.gruson@univ-savoie.fr");
    }
    /**
     * Métode utilisée pour la validation des champs
     *
     * @param string s'agit du champs à contrôler
     * @param pattern correspondant à l'expression régulière à tester
     */
    private boolean isValidString(String string,String pattern) {
        Pattern pat = Pattern.compile(pattern);
        Matcher match = pat.matcher(string);
        return match.matches();
    }
}
