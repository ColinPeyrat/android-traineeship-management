package info.iut.acy.fr.miniproject.Contact;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;

import info.iut.acy.fr.miniproject.Company.CompanyActivity;
import info.iut.acy.fr.miniproject.Company.CompanyDetailsActivity;
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
            calendar.set(Calendar.YEAR, arg1);
            calendar.set(Calendar.MONTH,arg2);
            calendar.set(Calendar.DAY_OF_MONTH,arg3);
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

                String companyName = contactCompanySpinner.getSelectedItem().toString();
                Long companyID = companys.get(companyName);
                String contactMeans = contactMeansSpinner.getSelectedItem().toString();
                String contactDescription = contactDesciptionEditText.getText().toString();
                String contactDate = storeDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH));

                if(contactDescription.trim().equals(""))
                    contactDesciptionEditText.setError("La description est requise");
                else{
                    Toast.makeText(getApplicationContext(), "Contact ajoutée", Toast.LENGTH_LONG).show();
                    ContactDB.insertContact(companyID, contactMeans, contactDescription, contactDate);
                    this.createNotification(companyID,"Recontacter l'entreprise "+companyName,"Cela fait 7 jours que vous n'avez pas contacter l'entreprise "+companyName);
                    finish();
                }
                break;
            case R.id.contact_date:
                showDialog(999);
        }
    }

    private final void createNotification(Long idCompany,String notificationTitle,String notificationDesc){
        //Récupération du notification Manager
        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        //Définition de la redirection au moment du clic sur la notification. Dans notre cas la notification redirige vers notre application
        final Intent intent = new Intent(this, CompanyDetailsActivity.class);
        intent.putExtra("idCompany", idCompany);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        //Définition de la date de notification
        final Calendar datenotification = Calendar.getInstance();
        datenotification.add(Calendar.DATE,14);

        //Création de la notification avec spécification de l'icône de la notification et le texte qui apparait à la création de la notification
        final Notification notification = new Notification.Builder(getBaseContext())
                .setContentTitle(notificationTitle)
                .setContentText(notificationDesc)
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setWhen(datenotification.getTimeInMillis())
                .setVibrate(new long[]{0, 200, 100, 200, 100, 200})
                .setDefaults(Notification.DEFAULT_SOUND |
                        Notification.DEFAULT_VIBRATE)
                .setSound(
                        RingtoneManager.getDefaultUri(
                                RingtoneManager.TYPE_NOTIFICATION))
                .setColor(Color.BLUE)
                .build();

        notificationManager.notify(idCompany.intValue(), notification);
    }

    private void deleteNotification(Integer idCompany){
        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //la suppression de la notification se fait grâce à son ID
        notificationManager.cancel(idCompany);
    }
}
