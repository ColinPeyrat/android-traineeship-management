package info.iut.acy.fr.miniproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

public class InformationAdapter {

    // membres public permettant de dfinir les champs de la base
    public static final String NOM_TABLE_INFORMATION = "information";
    public static final String KEY_NAME = "name";
    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_EMAIL_RESPONSABLE = "email_responsable";

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
    public InformationAdapter(Context ctx) {
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
            Log.i("InformationAdapter2", "Base ouverte en ecriture : " + NOM_TABLE_INFORMATION);
        }catch (SQLiteException e){
            this.mDb = this.mDbHelper.getReadableDatabase();
            Log.i("InformationAdapter2", "Base ouverte en lecture " + NOM_TABLE_INFORMATION);
        }
    }

    /**
     * close return type: void
     */
    public void close() {
        this.mDbHelper.close();
    }

    // insert information
    public long insertOrUpdateMyInformation(String name, String firstname, String email){
        Log.i("insertInformation", "appele");
        ContentValues newValue  = new ContentValues();
        newValue.put(InformationDBHelper.KEY_NAME,name);
        newValue.put(InformationDBHelper.KEY_FIRSTNAME, firstname);
        newValue.put(InformationDBHelper.KEY_EMAIL, email);

        Cursor value = getAllInformation();

        if(value.getCount() > 0)
            return mDb.update(InformationDBHelper.NOM_TABLE_INFORMATION, newValue, null, null);
        else
            return mDb.insert(InformationDBHelper.NOM_TABLE_INFORMATION, null, newValue);
    }

    public long insertOrUpdateResponsable(String email){
        Log.i("insertInformation", "appele");
        ContentValues newValue  = new ContentValues();
        newValue.put(InformationDBHelper.KEY_EMAIL_RESPONSABLE, email);

        Cursor value = getAllInformation();

        if(value.getCount() > 0)
            return mDb.update(InformationDBHelper.NOM_TABLE_INFORMATION, newValue, null, null);
        else
            return mDb.insert(InformationDBHelper.NOM_TABLE_INFORMATION, null, newValue);
    }

    // select * (renvoie tous les éléments de la table)
    public Cursor getAllInformation(){
        return mDb.query(InformationDBHelper.NOM_TABLE_INFORMATION, new String[]{InformationDBHelper.KEY_NAME,InformationDBHelper.KEY_FIRSTNAME,InformationDBHelper.KEY_EMAIL,InformationDBHelper.KEY_EMAIL_RESPONSABLE}, null, null, null, null, null);
    }
}
