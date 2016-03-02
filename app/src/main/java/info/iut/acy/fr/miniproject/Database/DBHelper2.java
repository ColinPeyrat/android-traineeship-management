//package info.iut.acy.fr.miniproject.Database;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
public class DBHelper2 {
//
//    private static final String DATABASE_NAME = "database.db";
//
//    public static final int DATABASE_VERSION = 1;
//
//    // String permettant la creation de la table
//    private static final String DATABASE_CREATE_COMPANY = "create table " + TraineeshipAdapter2.NOM_TABLE_COMPANY +
//            " (" + KEY_ID + " integer primary key autoincrement, " +
//            KEY_NAME + " text not null, " +
//            KEY_ADRESS + " text not null, " +
//            KEY_POSTAL + " text not null, " +
//            KEY_TOWN + " text not null, " +
//            KEY_COUNTRY + " text not null, " +
//            KEY_SERVICE + " text, " +
//            KEY_PHONE + " text not null, " +
//            KEY_MAIL + " text not null, " +
//            KEY_WEBSITE + " text, " +
//            KEY_DESCRIPTION + " text, " +
//            KEY_SIZE + " text );";
//
//    private static final String CREATE_TABLE_BOATS = "create table boats (_id integer primary key autoincrement, " //$NON-NLS-1$
//            +BoatsDBAdapter.NAME+" TEXT," //$NON-NLS-1$
//            +BoatsDBAdapter.MODEL+" TEXT," //$NON-NLS-1$
//            +BoatsDBAdapter.YEAR+" TEXT"+ ");"; //$NON-NLS-1$  //$NON-NLS-2$
//
//    private static final String CREATE_TABLE_CYCLES = "create table cycles (_id integer primary key autoincrement, " //$NON-NLS-1$
//            +CyclesDBAdapter.NAME+" TEXT," //$NON-NLS-1$
//            +CyclesDBAdapter.MODEL+" TEXT," //$NON-NLS-1$
//            +CyclesDBAdapter.YEAR+" TEXT"+ ");"; //$NON-NLS-1$  //$NON-NLS-2$
//
//
//    private final Context context;
//    private DatabaseHelper DBHelper;
//    private SQLiteDatabase db;
//
//    /**
//     * Constructor
//     * @param ctx
//     */
//    public DBHelper2(Context ctx)
//    {
//        this.context = ctx;
//        this.DBHelper = new DatabaseHelper(this.context);
//    }
//
//    private static class DatabaseHelper extends SQLiteOpenHelper
//    {
//        DatabaseHelper(Context context)
//        {
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db)
//        {
//            db.execSQL(CREATE_TABLE_CARS);
//            db.execSQL(CREATE_TABLE_BOATS);
//            db.execSQL(CREATE_TABLE_CYCLES);
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion,
//                              int newVersion)
//        {
//            // Adding any table mods to this guy here
//        }
//    }
//
//    /**
//     * open the db
//     * @return this
//     * @throws SQLException
//     * return type: DBHelper2
//     */
//    public DBAdapter open() throws SQLException
//    {
//        this.db = this.DBHelper.getWritableDatabase();
//        return this;
//    }
//
//    /**
//     * close the db
//     * return type: void
//     */
//    public void close()
//    {
//        this.DBHelper.close();
//    }
}
