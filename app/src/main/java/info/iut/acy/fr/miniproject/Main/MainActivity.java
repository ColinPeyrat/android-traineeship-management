package info.iut.acy.fr.miniproject.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import info.iut.acy.fr.miniproject.Company.CompanyActivity;
import info.iut.acy.fr.miniproject.Contact.ContactActivity;
import info.iut.acy.fr.miniproject.Database.ContactAdapter;
import info.iut.acy.fr.miniproject.Database.DBAdapter;
import info.iut.acy.fr.miniproject.Excel.ExcelActivity;
import info.iut.acy.fr.miniproject.Information.InformationActivity;
import info.iut.acy.fr.miniproject.R;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity{

    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView MainMenu = (ListView)findViewById(R.id.MainMenuListView);

        db = new DBAdapter(getApplicationContext());

        //remplis la arraylist avec la méthode fillHashMap
        ArrayList<HashMap<String, String>> menuItemList = new ArrayList<HashMap<String, String>>();
        menuItemList.add(fillHashMap("Mes offres de stage", String.valueOf((R.drawable.company))));
        menuItemList.add(fillHashMap("Mes contacts",String.valueOf(R.drawable.contact)));
        menuItemList.add(fillHashMap("Mes informations", String.valueOf(R.drawable.people)));
        menuItemList.add(fillHashMap("test excel", String.valueOf(R.drawable.people)));

        // Création d'un SimpleAdapter qui met en correspondance les items présents dans la list avec ceux de la vue
        SimpleAdapter itemsAdapter = new SimpleAdapter(this.getBaseContext(), menuItemList, R.layout.main_menu_item,
                new String[]{"MenuTitle", "MenuIcon"}, new int[]{R.id.titre_menu, R.id.imageMenu});

        MainMenu.setAdapter(itemsAdapter);

        /**
        * Métode OnClick qui réagit en fonction de l'item du menu qui a été cliqué
        *
        * @param View  La vue de l Adapter qui a été cliquée
        * @param position La position de la vue dans l'adapter
        * @param id: L'Id de l'item cliqué
        */
        MainMenu.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("position item menu", String.valueOf(position));
                switch (position) {

                    // Item "Mes offres de stages" clické
                    case 0:
                        Log.i("menu","Mes offres de stage");
                        Intent intentCompany = new Intent(getBaseContext(),CompanyActivity.class);
                        startActivity(intentCompany);
                        // avertis l'utilisateur par un toast si c'est le cas
                        Toast.makeText(getApplicationContext(), "Lancements de vos offres de stage", Toast.LENGTH_SHORT).show();
                        break;

                    // Item "Mes contacts" clické
                    case 1:
                        Log.i("menu","Mes contacts");
                        Intent intentContact = new Intent(getBaseContext(), ContactActivity.class);
                        startActivity(intentContact);
                        // avertis l'utilisateur par un toast si c'est le cas
                        Toast.makeText(getApplicationContext(), "Accès à vos contacts", Toast.LENGTH_SHORT).show();
                        break;

                    // Item "Mes informations" clicked
                    case 2:
                        Log.i("menu","Mes informations");
                        Intent intentInformation = new Intent(getBaseContext(), InformationActivity.class);
                        startActivity(intentInformation);
                        // avertis l'utilisateur par un toast si c'est le cas
                        Toast.makeText(getApplicationContext(), "Accès à vos informations", Toast.LENGTH_SHORT).show();
                        break;
                    // Item "Mes informations" clické
                    case 3:
                        Log.i("menu","testExcel");
                        Intent intentexcel = new Intent(getBaseContext(), ExcelActivity.class);
                        startActivity(intentexcel);
                        // avertis l'utilisateur par un toast si c'est le cas
                        Toast.makeText(getApplicationContext(), "test excel", Toast.LENGTH_SHORT).show();
                        break;
                }


                }
        });
    }

    @Override
    /**
     * Appelé quand l'activité va intéragir avec l'utilisateur, ouvre la base de données
     */
    protected void onResume() {
        super.onResume();
        db.open();
    }

    /**
     * méthode privée permettant de remplir un HashMap
     * @param icon Affiche l'icône correspondant à l'item
     * @param Title Affiche le titre correspondant à l'item
     * @return item avec titre et icône
     */

    private HashMap<String, String> fillHashMap (String Title, String icon){
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("MenuTitle", Title);
        item.put("MenuIcon", icon);
        return item;
    }

}
