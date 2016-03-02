package info.iut.acy.fr.miniproject.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class InformationDBHelper extends SQLiteOpenHelper {

    // membres public permettant de dfinir les champs de la base
    public static final String NOM_TABLE_INFORMATION = "information";
    public static final String KEY_NAME = "name";
    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_EMAIL_RESPONSABLE = "email_responsable";

    // String permettant la creation de la table
    private static final String DATABASE_CREATE_INFORMATION = "create table " + NOM_TABLE_INFORMATION +
            " (" + KEY_NAME + " text not null, " +
            KEY_FIRSTNAME + " text not null, " +
            KEY_EMAIL + " text not null, " +
            KEY_EMAIL_RESPONSABLE + " default 'nathalie.gruson@univ-savoie.fr');";

    public InformationDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.i("InformationAdapter2", "Helper construit");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creation de la table
        db.execSQL(DATABASE_CREATE_INFORMATION);

        Log.i("InformationAdapter2", "Database created with instruction : " +
                DATABASE_CREATE_INFORMATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // destruction de la base et recreation
        db.execSQL("DROP TABLE IF EXISTS " + NOM_TABLE_INFORMATION);
        Log.i("InformationAdapter2", "Base mise a jour !!!");
        onCreate(db);
    }
}
