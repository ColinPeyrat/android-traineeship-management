package info.iut.acy.fr.miniproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
     * constructeur prenant le contexte pour que la base de données soit ouverte / créée
     * @param ctx Contexte pour que la base fonctionne
     *
     */
    public ContactAdapter(Context ctx) {
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

    /**
     * select * (renvoie tous les éléments de la table)
     * @param sort correspond à l'order by, c'est à dire comment on veut trier
     */
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

    /**
     * select * (renvoie tous les éléments de la table en fonction de l'entreprise)
     * @param company correspond à l'entreprise
     * @param sort correspond à l'oderby, c'est à dire comment on veut trier
     */
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
    /**
     * Insert into  ( ajoute des éléments à la table)
     * @param idcompany id de l'entreprise
     * @param means moyen pour contacter l'entreprise
     * @param desciption description de l'enreprise
     * @param date date de contact avec l'entreprise
     * @return l'insert avec les valeurs rempli précédemment pour ajouté à la table 'Contact'
     */
    public long insertContact(Long idcompany, String means, String desciption, String date){
        Log.i("insertContact", "appele");
        ContentValues newValue  = new ContentValues();
        newValue.put(ContactAdapter.KEY_IDCOMPANY,idcompany);
        newValue.put(ContactAdapter.KEY_CONTACTMEANS,means);
        newValue.put(ContactAdapter.KEY_CONTACTDESCRIPTION,desciption);
        newValue.put(ContactAdapter.KEY_CONTACTDATE,date);

        return mDb.insert(ContactAdapter.NOM_TABLE_CONTACT, null, newValue);
    }
    public boolean removeContact(long ligneID){
        Log.i("removeLine", "appelé");
        return mDb.delete(ContactAdapter.NOM_TABLE_CONTACT, ContactAdapter.KEY_IDCONTACT + " = " + ligneID, null)>0;
    }


}
