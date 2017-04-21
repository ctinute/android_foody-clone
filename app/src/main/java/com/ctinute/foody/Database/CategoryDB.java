package com.ctinute.foody.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ctinute.foody.Objects.Category;
import com.ctinute.foody.Objects.City;

import java.util.ArrayList;

public class CategoryDB {

    private SQLiteDatabase db;

    public CategoryDB(SQLiteDatabase db) {
        this.db = db;
    }

    /*
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
    */



    // lay tat ca
    public ArrayList<Category> getCategoryList() {
        ArrayList<Category> list = new ArrayList<>();

        Cursor res =  db.rawQuery( "select * from CATEGORYWHERE", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            Category category = new Category();
            category.setId(res.getInt(res.getColumnIndex("ID")));
            category.setName(res.getString(res.getColumnIndex("NAME")));
            category.setImage(res.getString(res.getColumnIndex("IMAGE")));
            list.add(category);
            res.moveToNext();
        }

        res.close();
        return list;
    }

}
