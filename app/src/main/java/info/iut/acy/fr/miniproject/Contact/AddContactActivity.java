package info.iut.acy.fr.miniproject.Contact;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import info.iut.acy.fr.miniproject.Database.ContactAdapter;
import info.iut.acy.fr.miniproject.Database.TraineeshipAdapter;
import info.iut.acy.fr.miniproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AddContactActivity extends Activity implements View.OnClickListener {

    private Spinner contactCompanySpinner;
    private Spinner contactMeansSpinner;
    private EditText contactDesciptionEditText;
    private HashMap<String,Long> companys;

    private Calendar calendar;
    private EditText contactDateEditText;
    private int year, month, day;

    private ContactAdapter ContactDB;
    private TraineeshipAdapter CompanyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        ContactDB = new ContactAdapter(getApplicationContext());
        CompanyDB = new TraineeshipAdapter(getApplicationContext());

        Button btnSubmint = (Button)findViewById(R.id.submitContact);
        btnSubmint.setOnClickListener(this);

        contactDateEditText = (EditText) findViewById(R.id.contact_date);
        contactDateEditText.setOnClickListener(this);

        contactCompanySpinner = (Spinner)findViewById(R.id.contact_company);
        contactMeansSpinner = (Spinner)findViewById(R.id.contact_means);
        contactDesciptionEditText = (EditText)findViewById(R.id.contact_description);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
    }

    @Override
    protected void onResume(){
        super.onResume();
        ContactDB.open();
        CompanyDB.open();

        // Spinner Drop down elements
        List<String> means = new ArrayList<String>();
        means.add("Téléphone");
        means.add("Email");
        means.add("Rendez-vous");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterMeans = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, means);

        // Drop down layout style - list view with radio button
        dataAdapterMeans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        contactMeansSpinner.setAdapter(dataAdapterMeans);

        Cursor company = CompanyDB.getAllCompany();

        String[] spinnerArray =  new String[company.getCount()];
        // Spinner Drop down elements
        companys = new HashMap<String, Long>();

        if (company != null ) {
            if  (company.moveToFirst()) {
                Integer i=0;
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
        contactCompanySpinner.setAdapter(dataAdapterCompanys);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        String smonth;
        String sday;
        if(month < 10)
            smonth = "0"+month;
        else
            smonth = String.valueOf(month);
        if(day < 10)
            sday = "0"+day;
        else
            sday = String.valueOf(day);

        contactDateEditText.setText(new StringBuilder().append(sday).append("/")
                .append(smonth).append("/").append(year));
    }

    private String storeDate(int year, int month, int day){
        String smonth;
        String sday;
        if(month < 10)
            smonth = "0"+month;
        else
            smonth = String.valueOf(month);
        if(day < 10)
            sday = "0"+day;
        else
            sday = String.valueOf(day);
        return String.valueOf(new StringBuilder().append(year).append("-")
                .append(smonth).append("-").append(sday));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.submitContact:

                Long companyID = companys.get(contactCompanySpinner.getSelectedItem().toString());
                String contactMeans = contactMeansSpinner.getSelectedItem().toString();
                String contactDescription = contactDesciptionEditText.getText().toString();
                String contactDate = storeDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

                if(contactDescription.trim().equals(""))
                    contactDesciptionEditText.setError("La description est requise");
                else{
                    Toast.makeText(getApplicationContext(), "Contact ajoutée", Toast.LENGTH_LONG).show();
                    ContactDB.insertContact(companyID,contactMeans,contactDescription,contactDate);
                    finish();
                }
                break;
            case R.id.contact_date:
                showDialog(999);
        }
    }
}
