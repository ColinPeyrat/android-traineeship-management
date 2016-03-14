package info.iut.acy.fr.miniproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by peyratc on 02/03/2016.
 */
public class ContactAdapter {

    public static final String NOM_TABLE_CONTACT= "contact";
    public static final String KEY_IDCONTACT = "_id";
    public static final String KEY_IDCOMPANY = "idcompany";
    public static final String KEY_CONTACTMEANS = "contactmeans";
    public static final String KEY_CONTACTDATE = "contactdate";
    public static final String KEY_CONTACTDESCRIPTION = "contactdescription";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DBAdapter.DATABASE_NAME, null, DBAdapter.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx
     *            the Context within which to work
     */
    public ContactAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the cars database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @throws SQLiteException
     *             if the database could be neither opened or created
     */
    public void open() throws SQLiteException{
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        try{
            this.mDb = this.mDbHelper.getWritableDatabase();
            // LogCat message
            Log.i("ContactAdapter", "Base ouverte en ecriture : "+NOM_TABLE_CONTACT);
        }catch (SQLiteException e){
            this.mDb = this.mDbHelper.getReadableDatabase();
            Log.i("ContactAdapter", "Base ouverte en lecture "+NOM_TABLE_CONTACT);
        }
    }

    /**
     * close return type: void
     */
    public void close() {
        this.mDbHelper.close();
    }

    // select * (renvoie tous les �l�ments de la table)
    public Cursor getAllContact(String sort){
        return mDb.query(ContactAdapter.NOM_TABLE_CONTACT  +
                " LEFT OUTER JOIN " + TraineeshipAdapter.NOM_TABLE_COMPANY + " ON " +
                        TraineeshipAdapter.NOM_TABLE_COMPANY + "." + TraineeshipAdapter.KEY_ID + " = " + ContactAdapter.KEY_IDCOMPANY,
                new String[]{
                        ContactAdapter.NOM_TABLE_CONTACT + "." + ContactAdapter.KEY_IDCONTACT,
                        TraineeshipAdapter.KEY_NAME,
                        ContactAdapter.KEY_CONTACTMEANS,
                        ContactAdapter.KEY_CONTACTDATE,
                        ContactAdapter.KEY_CONTACTDESCRIPTION
                }, null, null, null, null, ContactAdapter.KEY_CONTACTDATE+" "+sort);
    }

    // select * (renvoie tous les �l�ments de la table)
    public Cursor getContactByCompany(Long company, String sort){
        return mDb.query(ContactAdapter.NOM_TABLE_CONTACT  +
                        " LEFT OUTER JOIN " + TraineeshipAdapter.NOM_TABLE_COMPANY + " ON " +
                        TraineeshipAdapter.NOM_TABLE_COMPANY + "." + TraineeshipAdapter.KEY_ID + " = " + ContactAdapter.KEY_IDCOMPANY,
                new String[]{
                        ContactAdapter.NOM_TABLE_CONTACT + "." + ContactAdapter.KEY_IDCONTACT,
                        TraineeshipAdapter.KEY_NAME,
                        ContactAdapter.KEY_CONTACTMEANS,
                        ContactAdapter.KEY_CONTACTDATE,
                        ContactAdapter.KEY_CONTACTDESCRIPTION
                }, ContactAdapter.KEY_IDCOMPANY + " = " + company, null, null, null, ContactAdapter.KEY_CONTACTDATE+" "+sort);
    }

    public long insertContact(Long idcompany, String means, String desciption, String date){
        Log.i("insertContact", "appele");
        ContentValues newValue  = new ContentValues();
        newValue.put(ContactAdapter.KEY_IDCOMPANY,idcompany);
        newValue.put(ContactAdapter.KEY_CONTACTMEANS,means);
        newValue.put(ContactAdapter.KEY_CONTACTDESCRIPTION,desciption);
        newValue.put(ContactAdapter.KEY_CONTACTDATE,date);

        return mDb.insert(ContactAdapter.NOM_TABLE_CONTACT, null, newValue);
    }


}
