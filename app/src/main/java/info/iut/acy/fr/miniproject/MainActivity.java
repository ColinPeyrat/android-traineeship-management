package info.iut.acy.fr.miniproject;

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

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView MainMenu = (ListView)findViewById(R.id.MainMenuListView);

        //remplis la arraylist avec la méthode fillHashMap
        ArrayList<HashMap<String, String>> menuItemList = new ArrayList<HashMap<String, String>>();
        menuItemList.add(fillHashMap("Mes offres de stage", String.valueOf((R.drawable.company))));
        menuItemList.add(fillHashMap("Mes contacts",String.valueOf(R.drawable.contact)));
        menuItemList.add(fillHashMap("Mes informations", String.valueOf(R.drawable.people)));

        // Création d'un SimpleAdapter qui met en correspondance les items présents dans la list avec ceux de la vue
        SimpleAdapter itemsAdapter = new SimpleAdapter(this.getBaseContext(), menuItemList, R.layout.main_menu_item,
                new String[]{"MenuTitle", "MenuIcon"}, new int[]{R.id.titre_menu, R.id.imageMenu});

        MainMenu.setAdapter(itemsAdapter);

        MainMenu.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("position item menu", String.valueOf(position));
                switch (position) {

                    // Item "Mes offres de stages" clicked
                    case 0:
                        Log.i("menu","Mes offres de stages");
                        Intent intentCompany = new Intent(getBaseContext(),CompanyActivity.class);
//                            Intent intentCalc = new Intent(getBaseContext(), Calculette.class);
                        startActivity(intentCompany);
//                            Log.e("exception", "activité pas trouvé");
//                            // avertis l'utilisateur par un toast si c'est le cas
                            Toast.makeText(getApplicationContext(), "Lancements de vos offres de stages", Toast.LENGTH_SHORT).show();
                        break;

                    // Item "Mes contacts" clicked
                    case 1:
                        Log.i("menu","Mes contacts");
                        break;

                    // Item "Mes informations" clicked
                    case 2:
                        Log.i("menu","Mes contacts");
                        break;
                }


                }
        });
    }

    // méthode privée permettant de remplir un HashMap
    private HashMap<String, String> fillHashMap (String Title, String icon){
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("MenuTitle", Title);
        item.put("MenuIcon", icon);
        return item;
    }

}
