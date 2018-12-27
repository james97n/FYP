package com.james.fyp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


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

    public void loadHandler(View view, Context context) {
        TableLayout tablelayout = view.findViewById(R.id.historyTable);
        String query = "Select*FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            View tablerow = LayoutInflater.from(context).inflate(R.layout.table_history_words,null,false);

            //get the attribute values from db table
            String result_0 = cursor.getString(0);
            String result_1 = cursor.getString(1);

            //declare variable that linked to xml textview
            TextView name  = tablerow.findViewById(R.id.word);
            TextView title  = tablerow.findViewById(R.id.meaning);

            //add values to the tablelayout
            name.setText(result_0);
            title.setText(result_1);
            tablelayout.addView(tablerow);
        } //iterate the whole db table
        cursor.close();
        db.close();
    }

    /*public String loadHandler() {
        String result = "";
        String query = "Select*FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String result_0 = cursor.getString(1);
            String result_1 = cursor.getString(2);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }*/

    public void addHandler(String text, String translatedtext) {
        ContentValues values = new ContentValues();
        //values.put(COLUMN_ID, history.getHistoryID());
        values.put(COLUMN_WORD, text);
        values.put(COLUMN_MEANING, translatedtext);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //public History findHandler(String history) {}
    public void deleteAllHandler() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        //context.deleteDatabase(DATABASE_NAME);
        db.close();
    }

    //public boolean updateHandler(int ID, String name) {}
}
