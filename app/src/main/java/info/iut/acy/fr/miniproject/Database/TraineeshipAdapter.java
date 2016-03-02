package info.iut.acy.fr.miniproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by peyratc on 02/03/2016.
 */
public class TraineeshipAdapter{

    // membres public permettant de dfinir les champs de la base
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
    public static final String KEY_SIZE= "size";
    public static final String KEY_DESCRIPTION= "description";

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
    public TraineeshipAdapter(Context ctx) {
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
    public void open() throws SQLiteException{
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        try{
            this.mDb = this.mDbHelper.getWritableDatabase();
            // LogCat message
            Log.i("InformationAdapter2", "Base ouverte en ecriture : "+NOM_TABLE_COMPANY);
        }catch (SQLiteException e){
            this.mDb = this.mDbHelper.getReadableDatabase();
            Log.i("InformationAdapter2", "Base ouverte en lecture "+NOM_TABLE_COMPANY);
        }
    }

    /**
     * close return type: void
     */
    public void close()
    {

        this.mDbHelper.close();
    }

    // select * (renvoie tous les éléments de la table)
    public Cursor getAllCompany(){
        return mDb.query(TraineeshipAdapter.NOM_TABLE_COMPANY, new String[]{ TraineeshipDBHelper.KEY_ID,TraineeshipDBHelper.KEY_NAME,
                TraineeshipDBHelper.KEY_ADRESS,TraineeshipDBHelper.KEY_POSTAL,TraineeshipDBHelper.KEY_TOWN,TraineeshipDBHelper.KEY_COUNTRY,
                TraineeshipDBHelper.KEY_SERVICE,TraineeshipDBHelper.KEY_PHONE,TraineeshipDBHelper.KEY_MAIL,TraineeshipDBHelper.KEY_WEBSITE,TraineeshipDBHelper.KEY_SIZE,TraineeshipDBHelper.KEY_DESCRIPTION}, null, null, null, null, null);
    }
    // insert a company
    public long insertCompany(String name, String address, String postal, String town, String country,String service, String phone, String mail, String website, String size,String description){
        Log.i("insertCompany", "appele");
        ContentValues newValue  = new ContentValues();
        newValue.put(TraineeshipDBHelper.KEY_NAME,name);
        newValue.put(TraineeshipDBHelper.KEY_ADRESS,address);
        newValue.put(TraineeshipDBHelper.KEY_POSTAL,postal);
        newValue.put(TraineeshipDBHelper.KEY_TOWN,town);
        newValue.put(TraineeshipDBHelper.KEY_COUNTRY,country);
        newValue.put(TraineeshipDBHelper.KEY_SERVICE,service);
        newValue.put(TraineeshipDBHelper.KEY_PHONE,phone);
        newValue.put(TraineeshipDBHelper.KEY_MAIL,mail);
        newValue.put(TraineeshipDBHelper.KEY_WEBSITE,website);
        newValue.put(TraineeshipDBHelper.KEY_SIZE,size);
        newValue.put(TraineeshipDBHelper.KEY_DESCRIPTION,description);
        return mDb.insert(TraineeshipDBHelper.NOM_TABLE_COMPANY, null, newValue);
    }

   /* *//**
     * Create a new car. If the car is successfully created return the new
     * rowId for that car, otherwise return a -1 to indicate failure.
     *
     * @param name
     * @param model
     * @param year
     * @return rowId or -1 if failed
     *//*
    public long createCar(String name, String model, String year){
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME, name);
        initialValues.put(MODEL, model);
        initialValues.put(YEAR, year);
        return this.mDb.insert(DATABASE_TABLE, null, initialValues);
    }*/

}
