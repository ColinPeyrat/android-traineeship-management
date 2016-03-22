package info.iut.acy.fr.miniproject.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

public class DBAdapter {

    public static final String DATABASE_NAME = "database.db"; //$NON-NLS-1$

    public static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_COMPANY = "create table " + TraineeshipAdapter.NOM_TABLE_COMPANY +
            " (" + TraineeshipAdapter.KEY_ID + " integer primary key autoincrement, " +
            TraineeshipAdapter.KEY_NAME + " text not null, " +
            TraineeshipAdapter.KEY_ADRESS + " text not null, " +
            TraineeshipAdapter.KEY_POSTAL + " text not null, " +
            TraineeshipAdapter.KEY_TOWN + " text not null, " +
            TraineeshipAdapter.KEY_COUNTRY + " text not null, " +
            TraineeshipAdapter.KEY_SERVICE + " text, " +
            TraineeshipAdapter.KEY_PHONE + " text not null, " +
            TraineeshipAdapter.KEY_MAIL + " text not null, " +
            TraineeshipAdapter.KEY_WEBSITE + " text, " +
            TraineeshipAdapter.KEY_DESCRIPTION + " text, " +
            TraineeshipAdapter.KEY_SIZE + " text );";

    private static final String CREATE_TABLE_CONTACT = "create table " + ContactAdapter.NOM_TABLE_CONTACT +
            " (" + ContactAdapter.KEY_IDCONTACT + " integer primary key autoincrement, " +
            ContactAdapter.KEY_IDCOMPANY + " integer not null, " +
            ContactAdapter.KEY_CONTACTMEANS + " text not null, " +
            ContactAdapter.KEY_CONTACTDESCRIPTION + " text not null, " +
            ContactAdapter.KEY_CONTACTDATE + " date not null); ";

    // String permettant la creation de la table
    private static final String CREATE_TABLE_INFORMATION = "create table " + InformationAdapter.NOM_TABLE_INFORMATION +
            " (" + InformationAdapter.KEY_NAME + " text not null, " +
            InformationAdapter.KEY_FIRSTNAME + " text not null, " +
            InformationAdapter.KEY_EMAIL + " text not null, " +
            InformationAdapter.KEY_EMAIL_RESPONSABLE + " default 'nathalie.gruson@univ-savoie.fr');";


    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    /**
     * Constructor
     * @param ctx comme ContactAdapter
     */
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        this.DBHelper = new DatabaseHelper(this.context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            Log.d("DBAdapter","OnCreate Appelé");
            db.execSQL(CREATE_TABLE_COMPANY);
            db.execSQL(CREATE_TABLE_CONTACT);
            db.execSQL(CREATE_TABLE_INFORMATION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TraineeshipAdapter.NOM_TABLE_COMPANY);
            db.execSQL("DROP TABLE IF EXISTS " + ContactAdapter.NOM_TABLE_CONTACT);
            db.execSQL("DROP TABLE IF EXISTS " + InformationAdapter.NOM_TABLE_INFORMATION);
        }
    }

    /**
     * open the db
     * @return void
     * @throws SQLException
     */
    public void open() throws SQLiteException {
        try{
            db=DBHelper.getWritableDatabase();
            // LogCat message
            Log.i("InformationAdapter2", "Base ouverte en ecriture ");
        }catch (SQLiteException e){
            db=DBHelper.getReadableDatabase();
            Log.i("InformationAdapter2", "Base ouverte en lecture ");
        }
    }

    /**
     * close the db
     * return type: void
     */
    public void close()
    {
        this.DBHelper.close();
    }
}