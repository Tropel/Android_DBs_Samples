package site.withoutcaps.android_dbs_samples;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class SQL_DB_Helper extends SQLiteOpenHelper {
    public static final String TAG = "SQL_DB_Helper";
    public static final int DATABASE_VERSION = 1;               //Needs to be incremented if database layout is changed
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TC.TABLE_NAME + " (" +
                    TC._ID + " INTEGER PRIMARY KEY," +
                    TC.COLUMN_NAME_TITLE + " TEXT," +
                    TC.COLUMN_NAME_SUBTITLE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TC.TABLE_NAME;

    public SQL_DB_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long insert(SQL_DB_Helper helper, String title, String subtitle) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TC.COLUMN_NAME_TITLE, title);
        values.put(TC.COLUMN_NAME_SUBTITLE, subtitle);

        return db.insert(TC.TABLE_NAME, null, values);
    }


    public Cursor find(SQL_DB_Helper helper, String where, String[] values) {     //query database. "find" because i like mongo
        SQLiteDatabase db = helper.getReadableDatabase();
        String sortOrder = TC.COLUMN_NAME_SUBTITLE + " DESC";   //sort order

        String[] projection = {                                 //can be set to null for all
                TC._ID,
                TC.COLUMN_NAME_TITLE,
                TC.COLUMN_NAME_SUBTITLE
        };

        return db.query(
                TC.TABLE_NAME,                            // The table to query
                projection,                               // The columns to return
                where,                                    // The columns for the WHERE clause
                values,                                   // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
    }

    public void delete(SQL_DB_Helper helper, String where, String[] args){
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(TC.TABLE_NAME, where, args);
    }

//--------------------------------[Inner Classes]----------------------------------//

    public static class TC implements BaseColumns {       //Class that defines table content
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
}