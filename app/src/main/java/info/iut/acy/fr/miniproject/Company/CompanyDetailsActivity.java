package info.iut.acy.fr.miniproject.Company;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import info.iut.acy.fr.miniproject.Database.TraineeshipAdapter;
import info.iut.acy.fr.miniproject.R;

public class CompanyDetailsActivity extends Activity {

    TraineeshipAdapter TraineeshipDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        TraineeshipDB = new TraineeshipAdapter(getApplicationContext());

        TraineeshipDB.open();

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

        Long idCompany = null;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            idCompany = extras.getLong("idCompany");
        }
        Cursor company = TraineeshipDB.getSingleCompany(idCompany);
        if(company.getCount() > 0){
            company.moveToFirst();

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


            if(serviceCursor.length() > 0)
                tvService.setText(serviceCursor);

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

            if(websiteCursor.length() > 0)
                tvWebsite.setText(websiteCursor);

            if(sizeCursor.length() > 0)
                tvSize.setText(sizeCursor);

            if(descriptionCursor.length() > 0)
                tvDescription.setText(descriptionCursor);


        } else {
            finish();
            Toast.makeText(getApplicationContext(), "Impossible de récuperer l'entreprise", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    public SpannableString setStringUnderline(String string){

        SpannableString spanString = new SpannableString(string);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        return spanString;
    }
}
