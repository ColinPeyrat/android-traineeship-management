package info.iut.acy.fr.miniproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

public class TraineeshipAdapter {

    // membres public permettant de définir les champs de la base
    public static final String NOM_TABLE_COMPANY = "company";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADRESS = "adress";
    public static final String KEY_POSTAL = "postal";
    public static final String KEY_TOWN = "town";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_SERVICE = "service";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_MAIL = "mail";
    public static final String KEY_WEBSITE = "website";
    public static final String KEY_SIZE = "size";
    public static final String KEY_DESCRIPTION = "description";

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
     *
     * @param ctx Contexte pour que la base fonctionne
     */
    public TraineeshipAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Ouvre la base, si elle ne peut être ouverte , essaye d'en créer une autre, si on ne peut en en créer une renvoi une erreur pour signaler le problème
     *
     * @throws SQLiteException Si la base ne peut être ni ouverte ni créée
     */
    public void open() throws SQLiteException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        try {
            this.mDb = this.mDbHelper.getWritableDatabase();
            // LogCat message
            Log.i("InformationAdapter2", "Base ouverte en ecriture : " + NOM_TABLE_COMPANY);
        } catch (SQLiteException e) {
            this.mDb = this.mDbHelper.getReadableDatabase();
            Log.i("InformationAdapter2", "Base ouverte en lecture " + NOM_TABLE_COMPANY);
        }
    }

    /**
     * close return type: void
     */
    public void close() {

        this.mDbHelper.close();
    }

    /**
     * select * (renvoie tous les éléments de la table)
     */
    public Cursor getAllCompany() {
        return mDb.query(TraineeshipAdapter.NOM_TABLE_COMPANY, new String[]{TraineeshipDBHelper.KEY_ID, TraineeshipDBHelper.KEY_NAME,
                TraineeshipDBHelper.KEY_ADRESS, TraineeshipDBHelper.KEY_POSTAL, TraineeshipDBHelper.KEY_TOWN, TraineeshipDBHelper.KEY_COUNTRY,
                TraineeshipDBHelper.KEY_SERVICE, TraineeshipDBHelper.KEY_PHONE, TraineeshipDBHelper.KEY_MAIL, TraineeshipDBHelper.KEY_WEBSITE, TraineeshipDBHelper.KEY_SIZE, TraineeshipDBHelper.KEY_DESCRIPTION}, null, null, null, null, KEY_ID + " DESC");
    }

    /**
     * Insert into  ( ajoute des éléments à la table)
     *
     * @param name        nom de l'entreprise
     * @param address     adresse de l'entreprise
     * @param postal      code postal de l'entreprise
     * @param town        ville de l'entreprise
     * @param country     Pays de l'entreprise
     * @param service     Service de l'entreprise ( service info / service com) en fonction de la taille de l'entreprise
     * @param phone       Téléphone de l'enreprise
     * @param mail        email de l'entreprise
     * @param website     site internet de l'entreprise si elle en a un
     * @param size        taille de l'entreprise ( nombre d'employés )
     * @param description description de l'enreprise
     * @return l'insert avec les valeurs rempli précédemment pour ajouté à la table 'Contact'
     */
    public long insertCompany(String name, String address, String postal, String town, String country, String service, String phone, String mail, String website, String size, String description) {
        Log.i("insertCompany", "appele");
        ContentValues newValue = new ContentValues();
        newValue.put(TraineeshipDBHelper.KEY_NAME, name);
        newValue.put(TraineeshipDBHelper.KEY_ADRESS, address);
        newValue.put(TraineeshipDBHelper.KEY_POSTAL, postal);
        newValue.put(TraineeshipDBHelper.KEY_TOWN, town);
        newValue.put(TraineeshipDBHelper.KEY_COUNTRY, country);
        newValue.put(TraineeshipDBHelper.KEY_SERVICE, service);
        newValue.put(TraineeshipDBHelper.KEY_PHONE, phone);
        newValue.put(TraineeshipDBHelper.KEY_MAIL, mail);
        newValue.put(TraineeshipDBHelper.KEY_WEBSITE, website);
        newValue.put(TraineeshipDBHelper.KEY_SIZE, size);
        newValue.put(TraineeshipDBHelper.KEY_DESCRIPTION, description);
        return mDb.insert(TraineeshipDBHelper.NOM_TABLE_COMPANY, null, newValue);
    }

    /**
     * Récupère une seule offre de stage correspondant à l'id passé en parametre
     *
     * @param ligneID
     * @return reponse
     */
    public Cursor getSingleCompany(long ligneID) {
        Cursor reponse = mDb.query(TraineeshipAdapter.NOM_TABLE_COMPANY, new String[]{TraineeshipDBHelper.KEY_ID, TraineeshipDBHelper.KEY_NAME,
                        TraineeshipDBHelper.KEY_ADRESS, TraineeshipDBHelper.KEY_POSTAL, TraineeshipDBHelper.KEY_TOWN, TraineeshipDBHelper.KEY_COUNTRY,
                        TraineeshipDBHelper.KEY_SERVICE, TraineeshipDBHelper.KEY_PHONE, TraineeshipDBHelper.KEY_MAIL, TraineeshipDBHelper.KEY_WEBSITE, TraineeshipDBHelper.KEY_SIZE, TraineeshipDBHelper.KEY_DESCRIPTION}, TraineeshipAdapter.KEY_ID + " = " + ligneID, null, null,
                null, null);
        return reponse;
    }
}
