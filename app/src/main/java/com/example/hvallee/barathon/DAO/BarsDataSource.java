package com.example.hvallee.barathon.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hvallee.barathon.Model.Bar;
import com.example.hvallee.barathon.Model.Parcour;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoannlt on 20/11/2015.
 */
public class BarsDataSource {

    private final String LOG_TAG = this.getClass().getSimpleName();

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumnsBar = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_ADDRESS,
            MySQLiteHelper.COLUMN_PHONE,
            MySQLiteHelper.COLUMN_LATITUDE,
            MySQLiteHelper.COLUMN_LONGITUDE};

    private String[] allColumnsParcour = {
            MySQLiteHelper.COLUMN_ID_PARCOURS,
            MySQLiteHelper.COLUMN_NAME_PARCOURS,
            MySQLiteHelper.COLUMN_DESCRIPTION_PARCOURS};

    public BarsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Bar createBar(String name, String address, String phone, String latitude, String longitude) {

        // Création et instanciation du content value
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_ADDRESS, address);
        values.put(MySQLiteHelper.COLUMN_PHONE, phone);
        values.put(MySQLiteHelper.COLUMN_LATITUDE, latitude);
        values.put(MySQLiteHelper.COLUMN_LONGITUDE, longitude);

        // insertion du bar : l'id est retourné
        long insertId = database.insert(MySQLiteHelper.TABLE_BARS, null,
                values);

        // Requête qui recupère le Bar précédement inséré
        Cursor cursor = database.query(MySQLiteHelper.TABLE_BARS,
                allColumnsBar, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        // On recup le bar via le cursor + close() du cursor
        cursor.moveToFirst();
        Bar newBar = cursorToBar(cursor);
        cursor.close();

        // Return le bar créé
        return newBar;
    }

    public void deleteBar(Bar bar) {
        // On recup l'id du bar en param et delete sur la base de données.
        long id = bar.getId();
        database.delete(MySQLiteHelper.TABLE_BARS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
        Log.d(LOG_TAG, "Bar deleted with id: " + id);
    }

    public List<Bar> getAllBars() {
        // On crée une List de Bar vide
        List<Bar> bars = new ArrayList<Bar>();

        // Une requête qui remplit le cursor
        Cursor cursor = database.query(MySQLiteHelper.TABLE_BARS,
                allColumnsBar, null, null, null, null, null);
        cursor.moveToFirst();
        //Tant que le cursor n'est pas vide, on insère les Bars dans la liste
        while (!cursor.isAfterLast()) {
            Bar bar = cursorToBar(cursor);
            bars.add(bar);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return bars;
    }

    public Bar getBarById(int id) {
        Bar bar = null;
        Cursor cursor = database.query(MySQLiteHelper.TABLE_BARS, allColumnsBar, MySQLiteHelper.COLUMN_ID
                + " = " + id,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            bar = cursorToBar(cursor);
        }
        return bar;
    }

    public Bar getBarByName(String name) {
        Bar bar = null;
        Cursor cursor = database.query(MySQLiteHelper.TABLE_BARS, allColumnsBar, MySQLiteHelper.COLUMN_NAME
                + " = \"" + name + "\"",null,null,null,null,null);
        if (cursor.moveToFirst()) {
            bar = cursorToBar(cursor);
        }
        return bar;
    }

    private Bar cursorToBar(Cursor cursor) {
        // Création d'un nouveau bar à partir du cursor
        Bar bar = new Bar(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));
        return bar;
    }

    private Parcour cursorToParcour(Cursor cursor) {
        Parcour parcour = new Parcour(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2));
        return parcour;
    }

    public Parcour createParcour(String name, String description){

        // Création du content values contenant les infos du parcour
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.COLUMN_NAME_PARCOURS, name);
        contentValues.put(MySQLiteHelper.COLUMN_DESCRIPTION_PARCOURS, description);

        // Requête d'insertion qui renvoit l'id de l'objet créé
        Long insertId = database.insert(MySQLiteHelper.TABLE_PARCOURS, null, contentValues);


        // On récupère via un cursor le parcour créé, avec l'id
        Cursor cursor = database.query(MySQLiteHelper.TABLE_PARCOURS,
                allColumnsParcour, MySQLiteHelper.COLUMN_ID_PARCOURS + " = " + insertId, null,
                null, null, null);

        // On transforme le cursor en Parcour
        cursor.moveToFirst();
        Parcour parcour = cursorToParcour(cursor);
        cursor.close();

        // On return le parcour
        return parcour;
    }
}