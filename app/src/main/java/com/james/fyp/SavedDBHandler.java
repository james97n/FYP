package com.james.fyp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SavedDBHandler extends SQLiteOpenHelper {

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "savedDB.db";
    private static final String TABLE_NAME = "Saved";
    private static final String COLUMN_ID = "SavedID";
    private static final String COLUMN_WORD = "SavedWord";
    private static final String COLUMN_MEANING = "SavedMeaning";
    private static final String COLUMN_EXAMPLE = "SavedExample";
    //initialize the database
    public SavedDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_WORD + " TEXT," + COLUMN_MEANING + " TEXT," + COLUMN_EXAMPLE + " TEXT )";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public void loadHandler(ArrayList<SavedCardItem> mCardList) {
        //TableLayout tablelayout = view.findViewById(R.id.savedTable);
        String query = "Select*FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {

            //get the attribute values from db table
            long result_0 = cursor.getLong(0);
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            String result_3 = cursor.getString(3);

            //add variable to card list
            mCardList.add(0, new SavedCardItem(result_0, result_1, result_2, result_3));


        } //iterate the whole db table
        cursor.close();
        db.close();

    }


    public void addHandler(String text, String translatedtext, String example) {
        ContentValues values = new ContentValues();
        values.putNull(COLUMN_ID);
        values.put(COLUMN_WORD, text);
        values.put(COLUMN_MEANING, translatedtext);
        values.put(COLUMN_EXAMPLE, example);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteAllHandler() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }

    public void deleteItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //position = position -1;
        db.execSQL("delete from " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + "'" + id + "'");
        db.close();
    }



}
