package info.iut.acy.fr.miniproject.Company;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        TextView tvTest = (TextView)findViewById(R.id.test);

        Long idCompany = null;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            idCompany = extras.getLong("idCompany");
        }
        Cursor company = TraineeshipDB.getSingleCompany(idCompany);
        if(company.getCount() > 0){
            company.moveToFirst();
            tvTest.setText(company.getString(company.getColumnIndex(TraineeshipDB.KEY_NAME)));
        } else {
            finish();
            Toast.makeText(getApplicationContext(), "Impossible de récuperer l'entreprise", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
