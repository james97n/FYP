package com.james.fyp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.james.fyp.QuestionContract.QuestionsTable;

import java.util.ArrayList;
import java.util.List;


public class QuizDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Quiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER," +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("明天",
                "Today", "Tomorrow", "Yesterday", 1, Question.DIFFICULTY_BEGINNER);
        addQuestion(q1);
        Question q2 = new Question("年",
                "Day", "Week", "Year", 3, Question.DIFFICULTY_BEGINNER);
        addQuestion(q2);
        Question q3 = new Question("星期",
                "Day", "Week", "Year", 2, Question.DIFFICULTY_BEGINNER);
        addQuestion(q3);
        Question q4 = new Question("小时",
                "Second", "Minute", "Hour", 3, Question.DIFFICULTY_BEGINNER);
        addQuestion(q4);
        Question q5 = new Question("今天",
                "Today", "Tomorrow", "Yesterday", 1, Question.DIFFICULTY_BEGINNER);
        addQuestion(q5);
        Question q6 = new Question("笑",
                "Laugh", "Walk", "Scare", 1, Question.DIFFICULTY_BEGINNER);
        addQuestion(q6);
        Question q7 = new Question("看",
                "Smell", "Go", "Look", 3, Question.DIFFICULTY_BEGINNER);
        addQuestion(q7);
        Question q8 = new Question("跳",
                "Cry", "Jump", "Walk", 2, Question.DIFFICULTY_BEGINNER);
        addQuestion(q8);
        Question q9 = new Question("听",
                "Second", "Minute", "Hour", 3, Question.DIFFICULTY_BEGINNER);
        addQuestion(q9);
        Question q10 = new Question("走",
                "Walk", "Jump", "Run", 1, Question.DIFFICULTY_BEGINNER);
        addQuestion(q10);
        Question q11 = new Question("漂亮",
                "Bad", "Nice", "Pretty", 3, Question.DIFFICULTY_BEGINNER);
        addQuestion(q11);
        Question q12 = new Question("好",
                "Weak", "Good", "Bad", 2, Question.DIFFICULTY_BEGINNER);
        addQuestion(q12);
        Question q13 = new Question("大",
                "Big", "Ugly", "Tall", 3, Question.DIFFICULTY_BEGINNER);
        addQuestion(q13);
        Question q14 = new Question("容易",
                "Easy", "Normal", "Hard", 1, Question.DIFFICULTY_BEGINNER);
        addQuestion(q14);
        Question q15 = new Question("近",
                "Near", "Red", "Good", 1, Question.DIFFICULTY_BEGINNER);
        addQuestion(q15);
        Question q16 = new Question("晚安",
                "Thank you", "Hello", "Good Night", 3, Question.DIFFICULTY_BEGINNER);
        addQuestion(q16);
        Question q17 = new Question("谢谢",
                "Hello", "Thank you", "Goodbye", 2, Question.DIFFICULTY_BEGINNER);
        addQuestion(q17);
        Question q18 = new Question("你好",
                "Goodbye", "Thank you", "Hello", 3, Question.DIFFICULTY_BEGINNER);
        addQuestion(q18);
        Question q19 = new Question("再见",
                "Goodbye", "Thank you", "Hello", 1, Question.DIFFICULTY_BEGINNER);
        addQuestion(q19);
        Question q20 = new Question("早安",
                "Good morning", "Good night", "Good evening", 1, Question.DIFFICULTY_BEGINNER);
        addQuestion(q20);
        Question q21 = new Question("我",
                "I", "You", "Him", 1, Question.DIFFICULTY_BEGINNER);
        addQuestion(q21);
        Question q22 = new Question("你",
                "I", "You", "We", 2, Question.DIFFICULTY_BEGINNER);
        addQuestion(q22);
        Question q23 = new Question("他",
                "I", "We", "Him", 3, Question.DIFFICULTY_BEGINNER);
        addQuestion(q23);
        Question q24 = new Question("我们",
                "We", "She", "He", 1, Question.DIFFICULTY_BEGINNER);
        addQuestion(q24);
        Question q25 = new Question("他们",
                "He", "We", "They", 3, Question.DIFFICULTY_BEGINNER);
        addQuestion(q25);
        Question q26 = new Question("一",
                "One", "Two", "Three", 1, Question.DIFFICULTY_BEGINNER);
        addQuestion(q26);
        Question q27 = new Question("三",
                "One", "Two", "Three", 3, Question.DIFFICULTY_BEGINNER);
        addQuestion(q27);
        Question q28 = new Question("六",
                "Four", "Five", "Six", 3, Question.DIFFICULTY_BEGINNER);
        addQuestion(q28);
        Question q29 = new Question("八",
                "Eight", "Nine", "Ten", 1, Question.DIFFICULTY_BEGINNER);
        addQuestion(q29);
        Question q30 = new Question("十",
                "Eight", "Nine", "Ten", 3, Question.DIFFICULTY_BEGINNER);
        addQuestion(q30);
        /*Question q31 = new Question("COMPETENT: C is correct",
                "A", "B", "C", 3, Question.DIFFICULTY_COMPETENT);
        addQuestion(q31);*/

    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }


    public List<Question> getQuestions(String difficulty) {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
