package com.ctinute.foody.Database;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ctinute.foody.Objects.ItemWhat;
import com.ctinute.foody.Objects.ItemWhat;

import java.util.ArrayList;

public class ItemWhatDB {
    private static final String TABLE_NAME = "ITEM";
    private static final String COLUMN_ID = "ID";

    private SQLiteDatabase db;

    public ItemWhatDB(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;
    }

    /*
    public boolean insertDistrict (String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", name);
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
    */

    private ArrayList<ItemWhat> getItemList(String query) {
        ArrayList<ItemWhat> list = new ArrayList<>();
        Cursor res =  db.rawQuery(query,null);
        if ((res.moveToFirst()) && res.getCount() > 0){
            while(!res.isAfterLast()){
                ItemWhat item = new ItemWhat();
                // TODO: change this after change item-what database
                item.setId(res.getInt(res.getColumnIndex("ID")));
                item.setImage(res.getString(res.getColumnIndex("IMG")));
                item.setFoodName("<Tên món ăn>");
                item.setLocationName(res.getString(res.getColumnIndex("NAME")));
                item.setAddress(res.getString(res.getColumnIndex("ADDRESS")));
                list.add(item);
                res.moveToNext();
            }
        }
        res.close();
        return list;
    }

    public ArrayList<ItemWhat> findItemsByCity(int cityId) {
        String query =  "select * from "+TABLE_NAME+ " where CITYID = "+cityId;
        return getItemList(query);
    }

    public ArrayList<ItemWhat> findItemsByDistrict(int districtId) {
        String query =  "select * from "+TABLE_NAME+ " where DISTRICTID = "+districtId;
        return getItemList(query);
    }

    public ArrayList<ItemWhat> findItemsByStreet(int streetId) {
        String query =  "select * from "+TABLE_NAME+ " where STREETID = "+streetId;
        return getItemList(query);
    }

    public ArrayList<ItemWhat> findItemsByFields(int cityId, int districtId, int streetId, int categoryId) {
        String query;
        if (categoryId <= 1){
            // no category (all)
            if (streetId > 0) {
                // tim theo duong
                query =  "select * from "+TABLE_NAME+ " where STREETID = "+streetId;
            }
            else if (districtId > 0) {
                // tim theo quan/huyen
                query =  "select * from "+TABLE_NAME+ " where DISTRICTID = "+districtId;
            }
            else if (cityId > 0) {
                query =  "select * from "+TABLE_NAME+ " where CITYID = "+cityId;
            }
            else
                query =  "select * from "+TABLE_NAME;    // lay het
        }
        else {
            // thim theo category
            if (streetId > 0) {
                // tim theo duong
                query =  "select * from "+TABLE_NAME+ " where CATEGORYID="+categoryId+" and STREETID = "+streetId;
            }
            else if (districtId > 0) {
                // tim theo quan/huyen
                query =  "select * from "+TABLE_NAME+ " where CATEGORYID="+categoryId+" and DISTRICTID = "+districtId;
            }
            else if (cityId > 0) {
                query =  "select * from "+TABLE_NAME+ " where CATEGORYID="+categoryId+" and CITYID = "+cityId;
            }
            else
                query =  "select * from "+TABLE_NAME+" where CATEGORYID="+categoryId;    // lay het
        }
        return getItemList(query);
    }
}
