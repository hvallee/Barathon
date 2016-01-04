package com.example.hvallee.barathon.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hvallee.barathon.Model.Bar;
import com.example.hvallee.barathon.Model.Parcours;

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

    private String[] allColumnsParcours = {
            MySQLiteHelper.COLUMN_ID_PARCOURS,
            MySQLiteHelper.COLUMN_NAME_PARCOURS,
            MySQLiteHelper.COLUMN_DESCRIPTION_PARCOURS};

    private String[] allColumnsBarParcours = {
            MySQLiteHelper.COLUMN_BARS_ID,
            MySQLiteHelper.COLUMN_PARCOURS_ID};

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

    private Parcours cursorToParcours(Cursor cursor) {
        Parcours parcours = new Parcours(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2));
        return parcours;
    }

    public Parcours createParcours(String name, String description){

        // Création du content values contenant les infos du parcours
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.COLUMN_NAME_PARCOURS, name);
        contentValues.put(MySQLiteHelper.COLUMN_DESCRIPTION_PARCOURS, description);

        // Requête d'insertion qui renvoit l'id de l'objet créé
        Long insertId = database.insert(MySQLiteHelper.TABLE_PARCOURS, null, contentValues);


        // On récupère via un cursor le parcours créé, avec l'id
        Cursor cursor = database.query(MySQLiteHelper.TABLE_PARCOURS,
                allColumnsParcours, MySQLiteHelper.COLUMN_ID_PARCOURS + " = " + insertId, null,
                null, null, null);

        // On transforme le cursor en Parcours
        cursor.moveToFirst();
        Parcours parcours = cursorToParcours(cursor);
        cursor.close();

        // On return le parcours
        return parcours;
    }

    public Parcours getParcoursById(int id) {
        Parcours parcours = null;
        Cursor cursor = database.query(MySQLiteHelper.TABLE_PARCOURS, allColumnsParcours, MySQLiteHelper.COLUMN_ID_PARCOURS
                + " = " + id, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            parcours = cursorToParcours(cursor);
        }
        return parcours;
    }

    public Long insertBarIntoParcours(int idParcours, int idBar) {

        // Création du contentvalues
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.COLUMN_BARS_ID, idBar);
        contentValues.put(MySQLiteHelper.COLUMN_PARCOURS_ID, idParcours);

        Long insertId = database.insert(MySQLiteHelper.TABLE_BARS_PARCOURS, null, contentValues);

        // Return id car pas de bars_parcours dans le modèle
        return insertId;
    }

    public void deleteBarInParcours(Parcours parcours, Bar bar) {
        database.delete(MySQLiteHelper.TABLE_BARS_PARCOURS,
                MySQLiteHelper.COLUMN_PARCOURS_ID + " = " + parcours.getId() + " AND " + MySQLiteHelper.COLUMN_BARS_ID +  "=" + bar.getId(), null);
    }

    public List<Bar> getAllBarsOfParcours(int idParcours) {
        // On crée une List de Bar vide
        List<Bar> bars = new ArrayList<Bar>();

        // Une requête qui remplit le cursor
        Cursor cursor = database.query(MySQLiteHelper.TABLE_BARS_PARCOURS, allColumnsBarParcours, MySQLiteHelper.COLUMN_PARCOURS_ID
                + " = " + idParcours,null,null,null,null,null);
        cursor.moveToFirst();

        //Tant que le cursor n'est pas vide, on recup les Bars via une requête
        while (!cursor.isAfterLast()) {
            Bar bar = getBarById(cursor.getInt(0));
            bars.add(bar);
            cursor.moveToNext();
        }
        cursor.close();

        return bars;
    }

    public void deleteParcours(Parcours parcours) {
        // On commence par delete les lignes dans bars_parcours
        database.delete(MySQLiteHelper.TABLE_BARS_PARCOURS, MySQLiteHelper.COLUMN_PARCOURS_ID + " = " + parcours.getId(), null);
        // Puis le parcours lui même
        database.delete(MySQLiteHelper.TABLE_PARCOURS, MySQLiteHelper.COLUMN_ID_PARCOURS + " = " + parcours.getId(), null);
    }
}