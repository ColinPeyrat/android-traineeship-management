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


    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_contact, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvcompany = (TextView) view.findViewById(R.id.company_name);
        TextView tvMeans = (TextView) view.findViewById(R.id.contact_means);
        TextView tvDescription = (TextView) view.findViewById(R.id.contact_description);
        TextView tvDate = (TextView) view.findViewById(R.id.contact_date);

        // Extract properties from cursor
        String company = cursor.getString(cursor.getColumnIndexOrThrow(TraineeshipAdapter.KEY_NAME));
        String means = cursor.getString(cursor.getColumnIndexOrThrow(ContactAdapter.KEY_CONTACTMEANS));
        String descritpion = cursor.getString(cursor.getColumnIndexOrThrow(ContactAdapter.KEY_CONTACTDESCRIPTION));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(ContactAdapter.KEY_CONTACTDATE));

        // Populate fields with extracted properties
        tvcompany.setText(company);
        tvMeans.setText(means);
        tvDescription.setText(descritpion);
        tvDate.setText(date);

    }
}
