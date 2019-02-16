package com.james.fyp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

public class PhraseDBHandler extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "phraseDB.db";
    public static final String TABLE_NAME = "Phrasebook";
    public static final String COLUMN_PHRASE = "PhraseWord";
    public static final String COLUMN_MEANING = "PhraseMeaning";
    public static final String COLUMN_EXAMPLE = "PhraseExample";

    //initialize the database
    public PhraseDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_PHRASE + " TEXT," + COLUMN_MEANING + " TEXT, " + COLUMN_EXAMPLE + " TEXT )";
        db.execSQL(CREATE_TABLE);
        fillPhrasebook();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    public void loadHandler(TextView word, TextView meaning, TextView example) {
        String query = "Select*FROM " + TABLE_NAME + " ORDER BY RANDOM() LIMIT 1";
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            //View tablerow = LayoutInflater.from(context).inflate(R.layout.table_score,null,false);

            //get the attribute values from db table
            String result_0 = cursor.getString(0);
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);


            //add values to the tablelayout
            word.setText(result_0);
            meaning.setText(result_1);
            example.setText(result_2);
        } //iterate the whole db table


        cursor.close();


    }


    public void addHandler(String word, String meaning, String example) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHRASE, word);
        values.put(COLUMN_MEANING, meaning);
        values.put(COLUMN_EXAMPLE, example);
        db.insert(TABLE_NAME, null, values);

    }


    private void fillPhrasebook() {
        addHandler("今天", "Today", "今天天气怎么样？\n( How is the weather today? )");
        addHandler("我", "I / Me", "我是小明\n( I am Xiao Ming. )");
        addHandler("你", "You", "你是谁?\n( who are you? )");
        addHandler("明天", "Tomorrow", "明天你得空吗?\n( Are you free tomorrow? )");
    }


}
