package info.iut.acy.fr.miniproject.Company;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import info.iut.acy.fr.miniproject.R;


public class AddCompanyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        Button btnSubmint = (Button)findViewById(R.id.submitCompany);
        btnSubmint.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText companyName = (EditText)findViewById(R.id.EditTextName);
                EditText companyMail = (EditText)findViewById(R.id.EditTextMail);
                EditText companyPhone = (EditText)findViewById(R.id.EditTextPhone);

                if( companyName.getText().toString().trim().equals("")) {
                    companyName.setError("Le nom de l'entreprise est requis");

                // verify if one of phone or mail is not empty
                } else if(companyPhone.getText().toString().trim().equals("")  || companyMail.getText().toString().trim().equals("")){

                    Toast.makeText(getApplicationContext(), "contact", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getApplicationContext(), "C'est bon zinc", Toast.LENGTH_LONG).show();

                }


            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_add_company, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
