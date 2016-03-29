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

    // membres public permettant de définir les champs de la base
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
     * constructeur prenant le contexte pour que la base de données soit ouverte / créée
     * @param ctx Contexte pour que la base fonctionne
     *
     */
    public InformationAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Ouvre la base, si elle ne peut être ouverte , essaye d'en créer une autre, si on ne peut en en créer une renvoi une erreur pour signaler le problème
     * @throws SQLiteException Si la base ne peut être ni ouverte ni créée
     *
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

    /**
     * Insert into ( ajoute des éléments à la table)
     * @param name nom de l'élève
     * @param firstname prénom de l'élève
     * @param email  email de l'élève
     * @return l'insert avec les valeurs rempli précédemment pour ajouté à la table 'information'
     */
    public long insertOrUpdateMyInformation(String name, String firstname, String email){
        Log.i("insertInformation", "appele");
        ContentValues newValue  = new ContentValues();
        newValue.put(InformationAdapter.KEY_NAME,name);
        newValue.put(InformationAdapter.KEY_FIRSTNAME, firstname);
        newValue.put(InformationAdapter.KEY_EMAIL, email);

        Cursor value = getAllInformation();

        if(value.getCount() > 0)
            return mDb.update(InformationAdapter.NOM_TABLE_INFORMATION, newValue, null, null);
        else
            return mDb.insert(InformationAdapter.NOM_TABLE_INFORMATION, null, newValue);
    }
    /**
     * Insert into ( ajoute des éléments à la table)
     * @param email  email du professeur responsable
     * @return l'insert avec les valeurs rempli précédemment pour ajouté à la table 'information'
     */
    public long insertOrUpdateResponsable(String email){
        Log.i("insertInformation", "appele");
        ContentValues newValue  = new ContentValues();
        newValue.put(InformationAdapter.KEY_EMAIL_RESPONSABLE, email);

        Cursor value = getAllInformation();

        if(value.getCount() > 0)
            return mDb.update(InformationAdapter.NOM_TABLE_INFORMATION, newValue, null, null);
        else
            return mDb.insert(InformationAdapter.NOM_TABLE_INFORMATION, null, newValue);
    }

    /**
     * Effectue un select *
     * @return les informations correspondant à tous les champs de la table 'Information'
     */
    public Cursor getAllInformation(){
        return mDb.query(InformationAdapter.NOM_TABLE_INFORMATION, new String[]{InformationAdapter.KEY_NAME,InformationAdapter.KEY_FIRSTNAME,InformationAdapter.KEY_EMAIL,InformationAdapter.KEY_EMAIL_RESPONSABLE}, null, null, null, null, null);
    }
}
