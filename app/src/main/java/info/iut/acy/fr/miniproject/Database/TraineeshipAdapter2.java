package info.iut.acy.fr.miniproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class TraineeshipAdapter2 {

    // variables de définition de la base gérée
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase shotsDB; // reference vers une base de données
    private TraineeshipDBHelper dbHelper; // référence vers le Helper de gestion de la base

    public TraineeshipAdapter2(Context context) { // constructeur
        dbHelper = new TraineeshipDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLiteException {
        try{
            shotsDB=dbHelper.getWritableDatabase();
            // LogCat message
            Log.i("TraineeshipDBHelper", "Base ouverte en ecriture " + shotsDB);
        }catch (SQLiteException e){
            shotsDB=dbHelper.getReadableDatabase();
            Log.i("TraineeshipDBHelper", "Base ouverte en lecture " + shotsDB);
        }
    }

    public void close(){
        Log.i("TraineeshipDBHelper", "close: demande de fermeture de la base");
        dbHelper.close();
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
        return shotsDB.insert(TraineeshipDBHelper.NOM_TABLE_COMPANY, null, newValue);
    }

    // select * (renvoie tous les éléments de la table)
    public Cursor getAllCompany(){
        return shotsDB.query(dbHelper.NOM_TABLE_COMPANY, new String[]{ TraineeshipDBHelper.KEY_ID,TraineeshipDBHelper.KEY_NAME,
        TraineeshipDBHelper.KEY_ADRESS,TraineeshipDBHelper.KEY_POSTAL,TraineeshipDBHelper.KEY_TOWN,TraineeshipDBHelper.KEY_COUNTRY,
        TraineeshipDBHelper.KEY_SERVICE,TraineeshipDBHelper.KEY_PHONE,TraineeshipDBHelper.KEY_MAIL,TraineeshipDBHelper.KEY_WEBSITE,TraineeshipDBHelper.KEY_SIZE,TraineeshipDBHelper.KEY_DESCRIPTION}, null, null, null, null, null);
    }
//
//    // insertion
//    public long insertShot(String chemin, String typeShot, String commentaire){
//        Log.i("insertShot", "appelé");
//        ContentValues newValue  = new ContentValues();
//        newValue.put(dbHelper.KEY_PATH, chemin);
//        newValue.put(dbHelper.KEY_TYPE, typeShot);
//        newValue.put(dbHelper.KEY_COMMENT, commentaire);
//        return shotsDB.insert(ShotsDBhelper.NOM_TABLE, null, newValue);
//    }
//    // modification
//    public boolean updateShot(int ligneID, String chemin, String typeShot, String commentaire){
//        Log.i("updateShot", "appelé");
//        ContentValues newValue = new ContentValues();
//        newValue.put(dbHelper.KEY_PATH, chemin);
//        newValue.put(dbHelper.KEY_TYPE, typeShot);
//        newValue.put(dbHelper.KEY_COMMENT, commentaire);
//        return shotsDB.update(ShotsDBhelper.NOM_TABLE, newValue,
//                ShotsDBhelper.KEY_ID + " = " + ligneID, null) > 0;
//    }
//
//    // suppression
//    public boolean removeShot(long ligneID){
//        Log.i("removeLine", "appelé");
//        return shotsDB.delete(ShotsDBhelper.NOM_TABLE, ShotsDBhelper.KEY_ID + " = " + ligneID, null)>0;
//    }
//
//    // select * (renvoie tous les éléments de la table)
//    public Cursor getAllData(){
//        return shotsDB.query(dbHelper.NOM_TABLE, new String[]{ ShotsDBhelper.KEY_ID,
//                ShotsDBhelper.KEY_PATH, ShotsDBhelper.KEY_TYPE,ShotsDBhelper.KEY_COMMENT}, null, null, null, null, dbHelper.KEY_ID+" DESC");
//    }
//
//    // renvoie un seul éléments de la table identifié par son ID
//    public Cursor getSingleShot(long ligneID){
//        Cursor reponse = shotsDB.query(ShotsDBhelper .NOM_TABLE, new String[]{
//                        ShotsDBhelper.KEY_ID, ShotsDBhelper.KEY_PATH, ShotsDBhelper.KEY_TYPE,
//                        ShotsDBhelper.KEY_COMMENT}, ShotsDBhelper.KEY_ID + " = " + ligneID, null, null,
//                null, null);
//        return reponse;
//    }
//
//    // renvoie tous les éléments de la table qui ont le type_media voulu.
//    public Cursor getAllShotsOfAtype(String type_media){
//        Cursor reponse = shotsDB.query(ShotsDBhelper .NOM_TABLE, new String[]{
//                        ShotsDBhelper.KEY_ID, ShotsDBhelper.KEY_PATH, ShotsDBhelper.KEY_TYPE,
//                        ShotsDBhelper.KEY_COMMENT}, ShotsDBhelper.KEY_TYPE + " = " + type_media, null, null,
//                null, null);
//        return reponse;
//    }


}