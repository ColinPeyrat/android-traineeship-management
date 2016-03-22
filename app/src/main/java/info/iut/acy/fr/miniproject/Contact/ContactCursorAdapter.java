package info.iut.acy.fr.miniproject.Contact;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import info.iut.acy.fr.miniproject.Database.ContactAdapter;
import info.iut.acy.fr.miniproject.Database.TraineeshipAdapter;
import info.iut.acy.fr.miniproject.R;


public class ContactCursorAdapter extends CursorAdapter {
    public ContactCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }



    @Override
    /**
     * On ajoute la vue de l'item à la vue
     *
     * @param context  contexte
     * @param cursor curseur
     * @param parent Vu de groupe
     */
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_contact, parent, false);
    }
    /**
     * Métode OnClick qui réagit en fonction de l'item du menu qui a été cliqué
     *
     * @param view  La vue a bindé
     * @param context context
     * @param cursor: curseur
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvcompany = (TextView) view.findViewById(R.id.company_name);
        TextView tvMeans = (TextView) view.findViewById(R.id.contact_means);
        TextView tvDescription = (TextView) view.findViewById(R.id.contact_description);
        TextView tvDate = (TextView) view.findViewById(R.id.contact_date);

        // Extrait les propriétés du curseur
        String company = cursor.getString(cursor.getColumnIndexOrThrow(TraineeshipAdapter.KEY_NAME));
        String means = cursor.getString(cursor.getColumnIndexOrThrow(ContactAdapter.KEY_CONTACTMEANS));
        String descritpion = cursor.getString(cursor.getColumnIndexOrThrow(ContactAdapter.KEY_CONTACTDESCRIPTION));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(ContactAdapter.KEY_CONTACTDATE));
        String[] d = date.split("-");
        date = d[2]+"/"+d[1]+"/"+d[0];

        tvcompany.setText(company);
        tvMeans.setText(means);
        tvDescription.setText(descritpion);
        tvDate.setText(date);

    }
}
