package com.example.taskappproject;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NoteHelper extends SQLiteOpenHelper {
    public static final String NOTE_TABLE = "NOTE_TABLE";
    public static final String COLUMN_ID = "COLUMN_ID";
    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String COLUMN_BODY = "COLUMN_BODY";

    public static NoteHelper instance;

    public NoteHelper(@Nullable Context context) {super(context, "notes.db", null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + NOTE_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " + COLUMN_NAME + " TEXT, " + COLUMN_BODY + " TEXT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(Note note){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(COLUMN_NAME, note.getName());
            cv.put(COLUMN_BODY, note.getBody());

        long insert = db.insert(NOTE_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        return true;
    }

    public int updateNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, note.getName());
        cv.put(COLUMN_BODY, note.getBody());

        return db.update(NOTE_TABLE, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
    }

    @SuppressLint("Range")
    public ArrayList<Note> getAll(){
        ArrayList<Note> returnList = new ArrayList<>();
        String qString = "SELECT * FROM " + NOTE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qString, null);
        for (int count = 0; count < cursor.getCount(); count++) {
            cursor.moveToPosition(count);
            Note note = new Note();
            note.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            note.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            note.setBody(cursor.getString(cursor.getColumnIndex(COLUMN_BODY)));
            returnList.add(note);
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean deleteOne(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        String qString = "DELETE FROM " + NOTE_TABLE + " WHERE " + COLUMN_ID + " = " + note.getId();
        Cursor cursor = db.rawQuery(qString, null);
        if(cursor.moveToFirst()){
            db.close();
            cursor.close();
            return true;
        }cursor.close();
        db.close();
        return false;
    }

    @SuppressLint("Range")
    public Note getNote(int i){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + NOTE_TABLE + " WHERE " + COLUMN_ID + " = " + i;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note();
        note.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        note.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        note.setBody(cursor.getString(cursor.getColumnIndex(COLUMN_BODY)));
        return note;
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete()
    }
}
