package com.example.numerosmash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

    //Set up the database elements
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "options";

    //Options are below
    private static final String COL1 = "ID";
    private static final String COL2 = "level";
    private static final String COL3 = "completed";


    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, level INTEGER, completed INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP IF TABLE EXISTS options");
        onCreate(db);
    }

    public boolean addData(int level){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, level);
        contentValues.put(COL3, 1);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //If date as inserted correctly it will return -1
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    //Retrieve elements from database
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //Delete entry from database
    public void deleteData(int item){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME+ " WHERE "+ COL2 +"='"+item+"'";
        db.execSQL(query);
    }

    //Clear element from the database
    public void clearData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
    }

}
