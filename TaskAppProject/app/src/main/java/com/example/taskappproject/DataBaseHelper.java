package com.example.taskappproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TASK_TABLE = "TASK_TABLE";
    public static final String COLUMN_TASK_NAME = "TASK_NAME";
    public static final String COLUMN_TASK_TYPE = "TASK_TYPE";
    public static final String COLUMN_TASK_PRIORITY = "TASK_PRIORITY";
    public static final String COLUMN_TASK_MONTH = "TASK_MONTH";
    public static final String COLUMN_TASK_DAY = "TASK_DAY";
    public static final String COLUMN_TASK_YEAR = "TASK_YEAR";
    public static final String COLUMN_TASK_HOUR = "TASK_HOUR";
    public static final String COLUMN_TASK_MINUTE = "TASK_MINUTE";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "taskInfo.db", null, 1);
    }

    //Used when Creating the database for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TASK_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TASK_NAME + " TEXT, " + COLUMN_TASK_TYPE + " TEXT, " + COLUMN_TASK_PRIORITY + " TEXT, " + COLUMN_TASK_MONTH + " INT, " + COLUMN_TASK_DAY + " INT, " + COLUMN_TASK_YEAR + " INT, " + COLUMN_TASK_HOUR + " INT, " + COLUMN_TASK_MINUTE + " INT)";

        db.execSQL(createTableStatement);
    }

    //Used when upgrading the database to a new version
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(TaskInformationModel taskInformationModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TASK_NAME, taskInformationModel.getTaskName());
        cv.put(COLUMN_TASK_TYPE, taskInformationModel.getTaskType());
        cv.put(COLUMN_TASK_PRIORITY, taskInformationModel.getTaskPriority());
        cv.put(COLUMN_TASK_MONTH, taskInformationModel.getMonth());
        cv.put(COLUMN_TASK_DAY, taskInformationModel.getDay());
        cv.put(COLUMN_TASK_YEAR, taskInformationModel.getYear());
        cv.put(COLUMN_TASK_HOUR, taskInformationModel.getHour());
        cv.put(COLUMN_TASK_MINUTE, taskInformationModel.getMinute());

        long insert = db.insert(TASK_TABLE, null, cv);
        if (insert == 1){
            return false;
        }else {
            return true;
        }
    };
}
