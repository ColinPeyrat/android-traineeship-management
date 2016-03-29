package info.iut.acy.fr.miniproject.Company;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import info.iut.acy.fr.miniproject.R;

public class EditCompanyActivity extends Activity {

    Long idCompany = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company);

        Bundle extras = getIntent().getExtras();
        // Récupère l'id de l'entreprise sur laquelle on a cliqué
        if(extras != null) {
            idCompany = extras.getLong("idCompany");
        }

        TextView tv3 = (TextView)findViewById(R.id.textView3);
        tv3.setText(String.valueOf(idCompany));
    }

}
