package info.iut.acy.fr.miniproject.Contact;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.sql.SQLException;

import info.iut.acy.fr.miniproject.Database.ContactAdapter;
import info.iut.acy.fr.miniproject.R;

public class ContactActivity extends Activity implements View.OnClickListener {

    ContactAdapter ContactDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact);

        Button btnRefresh = (Button)findViewById(R.id.btnAdd);
        btnRefresh.setOnClickListener(this);

        ContactDB = new ContactAdapter(getApplicationContext());

        ListView lvContact = (ListView) findViewById(R.id.ListViewContact);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ContactDB.open();

        // Rafraîchis la ListView
        populate();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdd:
                Intent intentAddContact = new Intent(getBaseContext(),AddContactActivity.class);
                startActivity(intentAddContact);
                // avertis l'utilisateur par un toast si c'est le cas
                // populate();
                break;
        }
    }

    // alimentation de la liste par le contenu de la base de données
    private void populate(){
        Cursor contactCursor = ContactDB.getAllContact();
        // Find ListView to populate
        ListView lvContact = (ListView) findViewById(R.id.ListViewContact);
        // Setup cursor adapter using cursor from last step
        ContactCursorAdapter todoAdapter = new ContactCursorAdapter(this, contactCursor);
        // Attach cursor adapter to the ListView
        lvContact.setAdapter(todoAdapter);
    }

}
