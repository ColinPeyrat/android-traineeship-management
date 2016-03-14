package info.iut.acy.fr.miniproject.Contact;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import info.iut.acy.fr.miniproject.Database.ContactAdapter;
import info.iut.acy.fr.miniproject.Database.TraineeshipAdapter;
import info.iut.acy.fr.miniproject.R;

public class AddContactActivity extends Activity implements View.OnClickListener {

    private Spinner contactCompanySpinner;
    private Spinner contactMeansSpinner;
    private EditText contactDesciptionEditText;


    private Calendar calendar;
    private EditText contactDateEditText;
    private int year, month, day;

    ContactAdapter ContactDB;
    TraineeshipAdapter CompanyDB;

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

        // Spinner Drop down elements
        List<String> companys = new ArrayList<String>();

        if (company != null ) {
            if  (company.moveToFirst()) {
                do {
                    String name = company.getString(company.getColumnIndex(TraineeshipAdapter.KEY_NAME));
                    companys.add(name);
                }while (company.moveToNext());
            }
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterCompanys = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, companys);

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
        contactDateEditText.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
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

                Long companyID = contactCompanySpinner.getSelectedItemId();
                String contactMeans = contactMeansSpinner.getSelectedItem().toString();
                String contactDescription = contactDesciptionEditText.getText().toString();
                String contactDate = contactDateEditText.getText().toString();

                ContactDB.insertContact(companyID,contactMeans,contactDescription,contactDate);
                break;
            case R.id.contact_date:
                showDialog(999);
        }
    }
}