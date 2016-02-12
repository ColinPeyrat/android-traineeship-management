package info.iut.acy.fr.miniproject;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TraineeshipDBHelper extends SQLiteOpenHelper {

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

    public static final String NOM_TABLE_CONTACT= "contact";
    public static final String KEY_IDCONTACT = "_id";
    public static final String KEY_IDCOMPANY = "idcompany";
    public static final String KEY_CONTACTMEANS = "contactmeans";
    public static final String KEY_CONTACTDATE = "contactdate";



    // String permettant la creation de la table
    private static final String DATABASE_CREATE_COMPANY = "create table " + NOM_TABLE_COMPANY +
            " (" + KEY_ID + " integer primary key autoincrement, " +
            KEY_NAME + " text not null, " +
            KEY_ADRESS + " text not null, " +
            KEY_POSTAL + " text not null, " +
            KEY_TOWN + " text not null, " +
            KEY_COUNTRY + " text not null, " +
            KEY_SERVICE + " text, " +
            KEY_PHONE + " text not null, " +
            KEY_MAIL + " text not null, " +
            KEY_WEBSITE + " text, " +
            KEY_SIZE + " text );";

    private static final String DATABASE_CREATE_CONTACT = "create table " + NOM_TABLE_CONTACT +
            " (" + KEY_IDCONTACT + " integer primary key autoincrement, " +
            KEY_IDCOMPANY + " integer not null, " +
            KEY_CONTACTMEANS + " text not null, " +
            KEY_CONTACTDATE + " text not null); ";

    public TraineeshipDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.i("TraineeshipDBHelper", "Helper construit");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // creation de la table
        db.execSQL(DATABASE_CREATE_COMPANY);
        db.execSQL(DATABASE_CREATE_CONTACT);

        Log.i("TraineeshipDBHelper", "Database created with instruction : " +
                DATABASE_CREATE_COMPANY);
        Log.i("TraineeshipDBHelper", "Database created with instruction : " +
                DATABASE_CREATE_CONTACT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // destruction de la base et recreation
        db.execSQL("DROP TABLE IF EXISTS " + NOM_TABLE_COMPANY);
        db.execSQL("DROP TABLE IF EXISTS " + NOM_TABLE_CONTACT);
        Log.i("TraineeshipDBHelper", "Base mise a jour !!!");
        onCreate(db);
    }

}