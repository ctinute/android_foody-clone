package com.ctinute.foody.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ctinute.foody.Objects.City;

import java.util.ArrayList;
import java.util.HashMap;

public class CityDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "foody.db";
    private static final String TABLE_NAME = "CITY";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private HashMap hashMap;

    public CityDBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (" +
                COLUMN_ID + " integer primary key, " +
                COLUMN_NAME+ " text");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertCity (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select * from "+TABLE_NAME+" where "+COLUMN_ID+"="+id+"", null );
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    public boolean updateCity (Integer id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        db.update(TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                COLUMN_ID+" = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<City> getCityList() {
        ArrayList<City> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            City city = new City(res.getInt(res.getColumnIndex(COLUMN_ID)),res.getString(res.getColumnIndex(COLUMN_NAME)));
            list.add(city);
            res.moveToNext();
        }

        db.close();
        res.close();
        return list;
    }
}