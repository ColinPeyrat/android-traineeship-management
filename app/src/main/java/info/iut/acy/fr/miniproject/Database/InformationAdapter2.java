package info.iut.acy.fr.miniproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class InformationAdapter2 {

    // variables de définition de la base gérée
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase shotsDB; // reference vers une base de données
    private InformationDBHelper dbHelper; // référence vers le Helper de gestion de la base

    public InformationAdapter2(Context context) { // constructeur
        dbHelper = new InformationDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLiteException {
        try{
            shotsDB=dbHelper.getWritableDatabase();
            // LogCat message
            Log.i("InformationAdapter2", "Base ouverte en ecriture " + shotsDB);
        }catch (SQLiteException e){
            shotsDB=dbHelper.getReadableDatabase();
            Log.i("InformationAdapter2", "Base ouverte en lecture " + shotsDB);
        }
    }

    public void close(){
        Log.i("InformationAdapter2", "close: demande de fermeture de la base");
        dbHelper.close();
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
            return shotsDB.update(InformationDBHelper.NOM_TABLE_INFORMATION, newValue, null, null);
        else
            return shotsDB.insert(InformationDBHelper.NOM_TABLE_INFORMATION, null, newValue);
    }

    public long insertOrUpdateResponsable(String email){
        Log.i("insertInformation", "appele");
        ContentValues newValue  = new ContentValues();
        newValue.put(InformationDBHelper.KEY_EMAIL_RESPONSABLE, email);

        Cursor value = getAllInformation();

        if(value.getCount() > 0)
            return shotsDB.update(InformationDBHelper.NOM_TABLE_INFORMATION, newValue, null, null);
        else
            return shotsDB.insert(InformationDBHelper.NOM_TABLE_INFORMATION, null, newValue);
    }

    // select * (renvoie tous les éléments de la table)
    public Cursor getAllInformation(){
        return shotsDB.query(InformationDBHelper.NOM_TABLE_INFORMATION, new String[]{InformationDBHelper.KEY_NAME,InformationDBHelper.KEY_FIRSTNAME,InformationDBHelper.KEY_EMAIL,InformationDBHelper.KEY_EMAIL_RESPONSABLE}, null, null, null, null, null);
    }

}
