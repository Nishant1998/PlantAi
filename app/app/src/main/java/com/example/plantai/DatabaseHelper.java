package com.example.plantai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "PlantAi.db";
    public final static String TABLE_NAME = "Records";
    public final static String COL_1 = "ID";
    public final static String COL_2 = "DATE";
    public final static String COL_3 = "TIME";
    public final static String COL_4 = "MAX1";
    public final static String COL_5 = "MAX2";
    public final static String COL_6 = "MAX3";
    public final static String COL_7 = "PRB1";
    public final static String COL_8 = "PRB2";
    public final static String COL_9 = "PRB3";
    public final static String COL_10 = "PATH";
    public final static String COL_11 = "JSONARRAY";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME+
                "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_2 + " DATE,"+
                COL_3 + " TEXT,"+
                COL_4 + " INTEGER,"+
                COL_5 + " INTEGER,"+
                COL_6 + " INTEGER,"+
                COL_7 + " INTEGER,"+
                COL_8 + " INTEGER,"+
                COL_9 + " INTEGER,"+
                COL_10 + " TEXT,"+
                COL_11 + " TEXT)"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(String date,String time, int max1,int max2,int max3,int prb1,int prb2,int prb3,String path,String jsonArray)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,date);
        contentValues.put(COL_3,time);
        contentValues.put(COL_4,max1);
        contentValues.put(COL_5,max2);
        contentValues.put(COL_6,max3);
        contentValues.put(COL_7,prb1);
        contentValues.put(COL_8,prb2);
        contentValues.put(COL_9,prb3);
        contentValues.put(COL_10,path);
        contentValues.put(COL_11,jsonArray);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1)
        {
            return  false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getData(String id)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE ID='"+id+"'";
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }
    public Cursor getAllData()
    {
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return cursor;
    }

    public int deleteData(String id)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID=?",new String[]{id});
    }

    public void deleteAllData()
    {
        SQLiteDatabase db =this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }


}
