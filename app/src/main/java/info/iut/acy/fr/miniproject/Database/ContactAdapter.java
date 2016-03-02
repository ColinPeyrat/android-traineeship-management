package info.iut.acy.fr.miniproject.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by peyratc on 02/03/2016.
 */
public class ContactAdapter {

    public static final String NOM_TABLE_CONTACT= "contact";
    public static final String KEY_IDCONTACT = "_id";
    public static final String KEY_IDCOMPANY = "idcompany";
    public static final String KEY_CONTACTMEANS = "contactmeans";
    public static final String KEY_CONTACTDATE = "contactdate";

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
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException
     *             if the database could be neither opened or created
     */
    public void open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        try{
            this.mDb = this.mDbHelper.getWritableDatabase();
            // LogCat message
            Log.i("InformationAdapter2", "Base ouverte en ecriture : " + NOM_TABLE_CONTACT);
        }catch (SQLiteException e){
            this.mDb = this.mDbHelper.getReadableDatabase();
            Log.i("InformationAdapter2", "Base ouverte en lecture "+NOM_TABLE_CONTACT);
        }
    }

    /**
     * close return type: void
     */
    public void close() {
        this.mDbHelper.close();
    }

}
