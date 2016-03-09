package info.iut.acy.fr.miniproject.Company;

import info.iut.acy.fr.miniproject.Database.TraineeshipAdapter;
import info.iut.acy.fr.miniproject.R;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CompanyCursorAdapter extends CursorAdapter{
    public CompanyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, 0);
    }

    public CompanyCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
       return LayoutInflater.from(context).inflate(R.layout.list_item_company, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvName = (TextView) view.findViewById(R.id.company_name);
        TextView tvAdress = (TextView) view.findViewById(R.id.company_address);
        TextView tvCity = (TextView) view.findViewById(R.id.TextViewCompanyCity);
        TextView tvPostal = (TextView) view.findViewById(R.id.TextViewCompanyPostal);
        TextView tvCountry = (TextView) view.findViewById(R.id.TextViewCompanyCountry);

        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow(TraineeshipAdapter.KEY_NAME));
        String adress = cursor.getString(cursor.getColumnIndexOrThrow(TraineeshipAdapter.KEY_ADRESS));
        String city = cursor.getString(cursor.getColumnIndexOrThrow(TraineeshipAdapter.KEY_TOWN));
        String postal = cursor.getString(cursor.getColumnIndexOrThrow(TraineeshipAdapter.KEY_POSTAL));
        String country = cursor.getString(cursor.getColumnIndexOrThrow(TraineeshipAdapter.KEY_COUNTRY));

        // Populate fields with extracted properties
        tvName.setText(name);
        tvAdress.setText(adress);
        tvCity.setText(city);
        tvPostal.setText(postal);
        tvCountry.setText(country);
    }
}
