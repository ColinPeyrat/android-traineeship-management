package info.iut.acy.fr.miniproject.Company;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import info.iut.acy.fr.miniproject.R;

public class CompanyDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        Long data = null;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
             data = extras.getLong("idCompany");
        }
        TextView tvTest = (TextView)findViewById(R.id.test);
        tvTest.setText(String.valueOf(data));


    }
}
