package info.iut.acy.fr.miniproject.Company;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import info.iut.acy.fr.miniproject.R;
import info.iut.acy.fr.miniproject.Database.TraineeshipAdapter;
import info.iut.acy.fr.miniproject.Database.TraineeshipDBHelper;


public class CompanyActivity extends Activity implements OnClickListener{

    TraineeshipAdapter TraineeshipDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        Button btnRefresh = (Button)findViewById(R.id.btnAdd);
        btnRefresh.setOnClickListener(this);

        TraineeshipDB = new TraineeshipAdapter(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        TraineeshipDB.open();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.btnTest:
//                TraineeshipDB.insertCompany("test","test","test","test","test","test","test","test","test","test");
//                break;
            case R.id.btnAdd:
                Intent intentAddCompany = new Intent(getBaseContext(),AddCompanyActivity.class);
                startActivity(intentAddCompany);
                // avertis l'utilisateur par un toast si c'est le cas
                // populate();
                break;
        }
    }

    // alimentation de la liste par le contenu de la base de données
    private void populate(){
        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_item_comany, TraineeshipDB.getAllCompany(),
                new String[] {TraineeshipDBHelper.KEY_NAME, TraineeshipDBHelper.KEY_ADRESS},
                new int[] {R.id.company_name, R.id.company_address});

        // Le coordonne avec le nouvel adaptateur
        ((ListView)findViewById(R.id.ListViewCompany)).setAdapter(adapter);
    }
}
