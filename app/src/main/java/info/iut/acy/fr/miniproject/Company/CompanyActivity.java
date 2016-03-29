package info.iut.acy.fr.miniproject.Company;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import info.iut.acy.fr.miniproject.Database.TraineeshipAdapter;
import info.iut.acy.fr.miniproject.R;


public class CompanyActivity extends Activity implements OnClickListener{

    TraineeshipAdapter TraineeshipDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        Button btnRefresh = (Button)findViewById(R.id.btnAdd);
        btnRefresh.setOnClickListener(this);

        TraineeshipDB = new TraineeshipAdapter(getApplicationContext());

        ListView lvCompany = (ListView) findViewById(R.id.ListViewCompany);

        /**
         * Métode OnClick qui réagit en fonction de l'item du menu qui a été cliqué
         *
         * @param View  La vue de l Adapter qui a été cliquée
         * @param position La position de la vue dans l'adapter
         * @param id: L'Id de l'item cliqué
         */
        lvCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a,
                                    View v, int position, long id) {

                Long idCompany = id;
                Intent intent = new Intent(v.getContext(), CompanyDetailsActivity.class);

                //Envoi l'id de l'entreprise qui a été cliqué
                intent.putExtra("idCompany", idCompany);
                startActivity(intent);
            }
        });
        lvCompany.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapter,
                                           View v, int position, final long id) {
                final AlertDialog.Builder b = new AlertDialog.Builder(CompanyActivity.this);
                b.setIcon(android.R.drawable.ic_dialog_alert);
                b.setMessage("Supprimer cette offre de stage ?");
                b.setPositiveButton("Oui",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                Toast.makeText(getApplicationContext(), "Supprimé", Toast.LENGTH_LONG).show();

                                // supprime la ligne dans la base de donnée correspondant a l'item dans la ListView
                                TraineeshipDB.removeCompany(id);

                                //rafraichis la liste view
                                populate();
                            }
                        });
                b.setNegativeButton("Non",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                b.show();
                return true;
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        TraineeshipDB.open();

        // Rafraîchis la ListView
        populate();

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
        Cursor companyCursor = TraineeshipDB.getAllCompanyOrderByAccepted();
        // Trouve la ListView pour populate
        ListView lvCompany = (ListView) findViewById(R.id.ListViewCompany);
        // Utilise le cursor précédemment crée pour
        CompanyCursorAdapter todoAdapter = new CompanyCursorAdapter(this, companyCursor);
        // Attache le curseur de l'adapter à la ListView
        lvCompany.setAdapter(todoAdapter);
    }
}
