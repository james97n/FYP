package com.james.fyp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class HistoryDBHandler extends SQLiteOpenHelper {

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "historyDB.db";
    public static final String TABLE_NAME = "History";
    public static final String COLUMN_WORD = "HistoryWord";
    public static final String COLUMN_MEANING = "HistoryMeaning";
    //initialize the database
    public HistoryDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +  COLUMN_WORD + " TEXT,"+COLUMN_MEANING+ " TEXT )";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public void loadHandler(ArrayList<CardItem> mCardList) {

        String query = "Select*FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {


            //get the attribute values from db table
            String result_0 = cursor.getString(0);
            String result_1 = cursor.getString(1);

            mCardList.add(0, new CardItem(result_0, result_1));


        } //iterate the whole db table
        cursor.close();
        db.close();
    }


    public void addHandler(String text, String translatedtext) {
        ContentValues values = new ContentValues();
        //values.put(COLUMN_ID, history.getHistoryID());
        values.put(COLUMN_WORD, text);
        values.put(COLUMN_MEANING, translatedtext);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public void deleteAllHandler() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        //context.deleteDatabase(DATABASE_NAME);
        db.close();
    }


}
