package info.iut.acy.fr.miniproject.Information;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.iut.acy.fr.miniproject.Database.InformationAdapter;
import info.iut.acy.fr.miniproject.Database.InformationDBHelper;
import info.iut.acy.fr.miniproject.R;

public class InformationActivity extends Activity implements View.OnClickListener {

    InformationAdapter InformationDB;

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

        Button btnSaveResponsable = (Button)findViewById(R.id.btnSubmitResponsable);
        btnSaveResponsable.setOnClickListener(this);

        information_name = (EditText)findViewById(R.id.txtName);
        information_firstname = (EditText)findViewById(R.id.txtFirstname);
        information_email = (EditText)findViewById(R.id.txtEmail);
        information_email_responsable = (EditText)findViewById(R.id.txtEmailResponsable);

        InformationDB = new InformationAdapter(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        InformationDB.open();
        populate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmitMyInformation:
                if(information_name.getText().length() == 0 || !isValidString(information_name.getText().toString(), "[A-Za-z]+")){
                    information_name.setError("Le nom est obligatoire et doit comporter que des lettres");
                    Log.i("InformationActivity", "nom faux");
                }
                else if(information_firstname.getText().length() == 0 || !isValidString(information_firstname.getText().toString(),"[A-Za-z]+")){
                    information_firstname.setError("Le prénom est obligatoire et doit comporter que des lettres");
                    Log.i("InformationActivity", "prenom faux");
                }
                else if(information_email.getText().length() == 0 || !isValidString(information_email.getText().toString(), "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
                    information_email.setError("L'email est obligatoire et doit se présenter comme ceci : adresse@email.fr");
                    Log.i("InformationActivity", "email faux");
                }
                else{
                    InformationDB.insertOrUpdateMyInformation(information_name.getText().toString(), information_firstname.getText().toString(), information_email.getText().toString());
                    Log.i("InformationActivity", "enregistrement des information");
                }
                break;
            case R.id.btnSubmitResponsable:
                if(information_email_responsable.getText().length() == 0 || !isValidString(information_email_responsable.getText().toString(), "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
                    information_email_responsable.setError("L'email est obligatoire et doit se présenter comme ceci : adresse@email.fr");
                    Log.i("InformationActivity", "email faux");
                }
                else{
                    InformationDB.insertOrUpdateResponsable(information_email_responsable.getText().toString());
                    Log.i("InformationActivity", "enregistrement des information");
                }
        }
    }

    public void populate(){
        Cursor info = InformationDB.getAllInformation();
        if(info.getCount() > 0){
            info.moveToFirst();
            information_name.setText(info.getString(0));
            information_firstname.setText(info.getString(1));
            information_email.setText(info.getString(2));
            information_email_responsable.setText(info.getString(3));
        }
        else
            information_email_responsable.setText("nathalie.gruson@univ-savoie.fr");
    }

    private boolean isValidString(String string,String pattern) {

        Pattern pat = Pattern.compile(pattern);
        Matcher match = pat.matcher(string);
        return match.matches();
    }
}
