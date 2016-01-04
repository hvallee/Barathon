package com.example.hvallee.barathon.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yoannlt on 20/11/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // logcat Tag
    private static final String LOG_TAG = MySQLiteHelper.class.getName();

    // Database version
    private static final int DATABASE_VERSION = 3;

    // Database name
    private static final String DATABASE_NAME = "barathon";

    //Table BARS
    public static final String TABLE_BARS = "bars";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_LATITUDE = "LATITUDE";
    public static final String COLUMN_LONGITUDE = "LONGITUDE";

    //Table PARCOURS
    public static final String TABLE_PARCOURS = "parcours";
    public static final String COLUMN_ID_PARCOURS = "_id";
    public static final String COLUMN_NAME_PARCOURS = "name";
    public static final String COLUMN_DESCRIPTION_PARCOURS = "description";
    public static final String COLUMN_DIFFICULTY_PARCOURS = "difficulty";
    public static final String COLUMN_BUDGET_PARCOURS = "budget";

    //Table BARS_PARCOURS faisant le lien entre un parcour et ses bars
    public static final String TABLE_BARS_PARCOURS = "bars_parcours";
    public static final String COLUMN_BARS_ID = "_id_bars";
    public static final String COLUMN_PARCOURS_ID = "_id_parcours";

    // table bars creation sql statement
    private static final String CREATE_TABLE_BARS = "create table "
            + TABLE_BARS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text not null, " + COLUMN_ADDRESS
            + " text not null, " + COLUMN_PHONE
            + " text not null, " + COLUMN_LATITUDE
            + " text not null, " + COLUMN_LONGITUDE
            + " text not null);";

    // table parcours creation sql statement
    private static final String CREATE_TABLE_PARCOURS = "create table "
            + TABLE_PARCOURS + "(" + COLUMN_ID_PARCOURS
            + " integer primary key autoincrement, " + COLUMN_NAME_PARCOURS
            + " text not null, " + COLUMN_DESCRIPTION_PARCOURS
            + " text, " + COLUMN_DIFFICULTY_PARCOURS
            + " integer, " + COLUMN_BUDGET_PARCOURS
            + "integer);";

    // table bars_parcours creation sql statement
    private static final String CREATE_TABLE_BARS_PARCOURS = "create table "
            + TABLE_BARS_PARCOURS + "(" + COLUMN_BARS_ID
            + " INTEGER NOT NULL REFERENCES " + TABLE_BARS +"("+ COLUMN_ID +"), "
            + COLUMN_PARCOURS_ID
            + " INTEGER NOT NULL REFERENCES " + TABLE_PARCOURS +"("+ COLUMN_ID_PARCOURS +"),"
            + " PRIMARY KEY ("+COLUMN_BARS_ID+","+COLUMN_PARCOURS_ID+"));";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_BARS);
        database.execSQL(CREATE_TABLE_PARCOURS);
        database.execSQL(CREATE_TABLE_BARS_PARCOURS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARS_PARCOURS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARCOURS);
        onCreate(db);
    }
}