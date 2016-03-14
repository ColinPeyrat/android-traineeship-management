package info.iut.acy.fr.miniproject.Contact;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import info.iut.acy.fr.miniproject.Database.ContactAdapter;
import info.iut.acy.fr.miniproject.Database.TraineeshipAdapter;
import info.iut.acy.fr.miniproject.R;

import java.util.HashMap;

public class ContactActivity extends Activity implements View.OnClickListener {

    private HashMap<String,Long> companys;
    private Spinner companySpinner;
    private ListView lvContact;
    private ContactAdapter ContactDB;
    private TraineeshipAdapter CompanyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact);

        Button btnRefresh = (Button)findViewById(R.id.btnAdd);
        btnRefresh.setOnClickListener(this);

        companySpinner = (Spinner)findViewById(R.id.SpinnerCompany);
        companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("OnItemSelected",String.valueOf(id));
                Log.i("OnItemSelected",parent.getSelectedItem().toString());

                switch ((int) id){
                    case 0:
                        populate();
                        break;
                    default:
                        Long idcompany = companys.get(parent.getSelectedItem().toString());
                        populate(idcompany);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                populate();
            }
        });

        ContactDB = new ContactAdapter(getApplicationContext());
        CompanyDB = new TraineeshipAdapter(getApplicationContext());

        lvContact = (ListView) findViewById(R.id.ListViewContact);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ContactDB.open();
        CompanyDB.open();

        // Rafra�chis la ListView
        populate();

        Cursor company = CompanyDB.getAllCompany();

        String[] spinnerArray =  new String[company.getCount()+1];
        spinnerArray[0] = "Toutes les entreprises";
        // Spinner Drop down elements
        companys = new HashMap<String, Long>();
        companys.put("Toutes les entreprises", (long) 0);

        if (company != null ) {
            if  (company.moveToFirst()) {
                Integer i=1;
                do {
                    String name = company.getString(company.getColumnIndex(TraineeshipAdapter.KEY_NAME));
                    Long id = Long.valueOf(company.getString(company.getColumnIndexOrThrow(TraineeshipAdapter.KEY_ID)));
                    companys.put(name,id);
                    spinnerArray[i] = name;
                    i++;
                }while (company.moveToNext());
            }
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterCompanys = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);

        // Drop down layout style - list view with radio button
        dataAdapterCompanys.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        companySpinner.setAdapter(dataAdapterCompanys);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdd:
                Intent intentAddContact = new Intent(getBaseContext(),AddContactActivity.class);
                startActivity(intentAddContact);
                // avertis l'utilisateur par un toast si c'est le cas
                // populate();
                break;
        }
    }

    // alimentation de la liste par le contenu de la base de donn�es
    private void populate(){
        Cursor contactCursor = ContactDB.getAllContact();
        // Setup cursor adapter using cursor from last step
        ContactCursorAdapter todoAdapter = new ContactCursorAdapter(this, contactCursor);
        // Attach cursor adapter to the ListView
        lvContact.setAdapter(todoAdapter);
    }

    private void populate(Long company){
        Cursor contactCursor = ContactDB.getContactByCompany(company);
        // Setup cursor adapter using cursor from last step
        ContactCursorAdapter todoAdapter = new ContactCursorAdapter(this, contactCursor);
        // Attach cursor adapter to the ListView
        lvContact.setAdapter(todoAdapter);
    }

}
