package com.james.fyp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class ScoreDBHandler extends SQLiteOpenHelper {

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scoreDB.db";
    public static final String TABLE_NAME = "Scoreboard";
    public static final String COLUMN_ID = "RankID";
    public static final String COLUMN_PLAYER = "PlayerName";
    public static final String COLUMN_SCORE = "PlayerScore";
    //initialize the database
    public ScoreDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +  COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_PLAYER+ " TEXT, "+COLUMN_SCORE+" INTEGER )";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public void loadHandler(View view, Context context) {
        TableLayout tablelayout = view.findViewById(R.id.scoreTable);
        String query = "Select*FROM " + TABLE_NAME+ " ORDER BY "+ COLUMN_SCORE +" DESC , " + COLUMN_ID + " DESC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = 1;
        while (cursor.moveToNext()) {
            View tablerow = LayoutInflater.from(context).inflate(R.layout.table_score,null,false);

            //get the attribute values from db table
            String result_0 = cursor.getString(1);
            int result_1 = cursor.getInt(2);

            //declare variable that linked to xml textview
            TextView ranking = tablerow.findViewById(R.id.ranking_number);
            TextView playername  = tablerow.findViewById(R.id.playerName);
            TextView score  = tablerow.findViewById(R.id.playerScore);

            //add values to the tablelayout
            ranking.setText(Integer.toString(count++));
            playername.setText(result_0);
            score.setText(Integer.toString(result_1));
            tablelayout.addView(tablerow);
        } //iterate the whole db table
        cursor.close();
        db.close();

    }


    public void addHandler(String playerName, Integer score) {
        ContentValues values = new ContentValues();
        values.putNull(COLUMN_ID);
        values.put(COLUMN_PLAYER, playerName);
        values.put(COLUMN_SCORE, score);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteAllHandler(Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("delete from "+ TABLE_NAME);
        context.deleteDatabase(DATABASE_NAME);
        db.close();
    }


    public void loadPlayerHandler(View view, Context context, String player) {

        GraphView graph = view.findViewById(R.id.graph);
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_PLAYER + " = " + "'" + player + "'" + " ORDER BY "+ COLUMN_ID + " ASC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int i = 0;
        /*DataPoint[] dp = new DataPoint[10];
        for(int i=0;i<10;i++){
            dp[i] = new DataPoint(i, i);
        }*/
        List<DataPoint> list = new ArrayList<>();
        list.add(new DataPoint(0,0));
        while(cursor.moveToNext())
        {
            int score = cursor.getInt(2);
            list.add(new DataPoint(i+1,score));
            i++;
        }
        cursor.close();
        //DataPoint[] dp = new DataPoint[i];

        DataPoint[] dpArray = new DataPoint[list.size()];
        dpArray = list.toArray(dpArray);


        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dpArray);

        graph.addSeries(series);
        db.close();
    }



}
