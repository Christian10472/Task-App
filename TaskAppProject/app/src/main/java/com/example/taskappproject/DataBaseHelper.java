package com.example.taskappproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

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
    public static final String COLUMN_TASK_COMPLETE = "TASK_COMPLETE";

    public static DataBaseHelper instance;

    public DataBaseHelper(@Nullable Context context) {
        super(context, "taskInfo.db", null, 1);
    }

    //Used when Creating the database for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TASK_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TASK_NAME + " TEXT, " + COLUMN_TASK_TYPE + " TEXT, " + COLUMN_TASK_PRIORITY + " TEXT, " + COLUMN_TASK_MONTH + " INT, " + COLUMN_TASK_DAY + " INT, " + COLUMN_TASK_YEAR + " INT, " + COLUMN_TASK_HOUR + " INT, " + COLUMN_TASK_MINUTE + " INT, " + COLUMN_TASK_COMPLETE + " BOOL)";

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
        cv.put(COLUMN_TASK_COMPLETE, taskInformationModel.getComplete());

        long insert = db.insert(TASK_TABLE, null, cv);
        if (insert == 1){
            return false;
        }else {
            return true;
        }
    }

    public boolean deleteTask(TaskInformationModel taskInformationModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TASK_TABLE + " WHERE " + COLUMN_ID + " = " + taskInformationModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }else{
            return false;
        }
    }

    public int updateComplete(TaskInformationModel taskInformationModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TASK_COMPLETE, true);

        return db.update(TASK_TABLE, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(taskInformationModel.getId())});
    }

    public int updateTask(TaskInformationModel taskInformationModel){
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
        cv.put(COLUMN_TASK_COMPLETE, taskInformationModel.getComplete());

        return db.update(TASK_TABLE, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(taskInformationModel.getId())});
    }

    public TaskInformationModel getTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TASK_TABLE + " WHERE " + COLUMN_ID + " = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        TaskInformationModel taskInformationModel = new TaskInformationModel(cursor.getInt(0)
                ,cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4),cursor.getInt(5)
                , cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), false);

        return taskInformationModel;
    }

    /* Task retrieval methods */

    public ArrayList<TaskInformationModel> getAllTasks(){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TASK_TABLE,null);
        return loadTaskListFromCursor(cursor);
    }

    @SuppressLint("Range")
    public ArrayList<TaskInformationModel> getTasksDueToday(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return getTasksDueOn(day, month, year);
    }

    public ArrayList<TaskInformationModel> getTasksDueOn(int day, int month, int year){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TASK_TABLE + " WHERE " + COLUMN_TASK_DAY + " = " + day + " AND " +
                COLUMN_TASK_YEAR + " = " + year + " AND " + COLUMN_TASK_MONTH + " = " + month,null);
        return loadTaskListFromCursor(cursor);
    }

    // Returns list of tasks due within the next 7 days
    public ArrayList<TaskInformationModel> getTasksDueSoon() {
        ArrayList<TaskInformationModel> result = new ArrayList<TaskInformationModel>();
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < 7; i ++){
            c.add(Calendar.DATE, 1);
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH) + 1;
            int year = c.get(Calendar.YEAR);
            result.addAll(getTasksDueOn(day, month, year));
        }
        return result;
    }

    public ArrayList<TaskInformationModel> getTasksDueInMonth(int month, int year){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TASK_TABLE + " WHERE " + COLUMN_TASK_YEAR + "=" + year + " AND " + COLUMN_TASK_MONTH + "=" + month,null);
        return loadTaskListFromCursor(cursor);
    }

    @SuppressLint("Range")
    private ArrayList<TaskInformationModel> loadTaskListFromCursor(Cursor cursor){
        ArrayList<TaskInformationModel> result = new ArrayList<TaskInformationModel>();
        for (int count = 0; count < cursor.getCount(); count ++){
            cursor.moveToPosition(count);

            TaskInformationModel task = new TaskInformationModel();
            task.setTaskName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
            task.setTaskType(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_TYPE)));
            task.setTaskPriority(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_PRIORITY)));
            task.setMonth(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_MONTH)));
            task.setDay(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_DAY)));
            task.setYear(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_YEAR)));
            task.setHour(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_HOUR)));
            task.setMinute(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_MINUTE)));
            task.setComplete(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_COMPLETE)) > 0);

            result.add(task);
        }
        cursor.close();
        return result;
    }

}
