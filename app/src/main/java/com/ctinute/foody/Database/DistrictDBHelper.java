package com.ctinute.foody.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ctinute.foody.Objects.City;
import com.ctinute.foody.Objects.District;
import com.ctinute.foody.Objects.Street;

import java.util.ArrayList;
import java.util.List;

public class DistrictDBHelper {
    private static final String TABLE_NAME = "DISTRICT";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";

    private SQLiteDatabase db;

    public DistrictDBHelper(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;
    }

    public boolean insertDistrict (String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        return db.rawQuery( "select * from "+TABLE_NAME+" where "+COLUMN_ID+"="+id+"", null );
    }

    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    public boolean updateDistrict (Integer id, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        db.update(TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteDistrict (Integer id) {
        return db.delete(TABLE_NAME,
                COLUMN_ID+" = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<District> getDistrictList(int cityID) {
        ArrayList<District> list = new ArrayList<>();

        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+ " where CITYID="+cityID, null );

        if ((res.moveToFirst()) && res.getCount() > 0){
            while(!res.isAfterLast()){
                District district = new District(res.getInt(res.getColumnIndex(COLUMN_ID)),res.getString(res.getColumnIndex(COLUMN_NAME)));

                // doc street list
                List<Street> streetList = new ArrayList<>();
                Cursor streetCur =  db.rawQuery( "select * from STREET where DISTRICTID = "+district.getId(), null );
                if ((streetCur.moveToFirst()) && streetCur.getCount() > 0){
                    while(!streetCur.isAfterLast()){
                        Street street = new Street(
                                streetCur.getInt(streetCur.getColumnIndex(COLUMN_ID)),
                                streetCur.getString(streetCur.getColumnIndex(COLUMN_NAME)));
                        streetList.add(street);
                        Log.w("db","District "+district.getName()+" add a street: "+street.getName());
                        streetCur.moveToNext();
                    }
                }
                district.setStreetList(streetList);
                list.add(district);
                Log.w("db","add a district: "+district.getName());
                streetCur.close();
                res.moveToNext();
            }
        }
        res.close();
        return list;
    }
}
