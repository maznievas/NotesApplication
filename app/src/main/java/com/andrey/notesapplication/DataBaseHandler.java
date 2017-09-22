package com.andrey.notesapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


/**
 * Created by sts on 18.09.17.
 */

public class DataBaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NotesManager";
    private static final String TABLE_NOTES = "Notes";

    private static final String KEY_ID = "id";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";
    private static final String KEY_PASSWORD = "password";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "( "
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_CONTENT + " TEXT, "
                + KEY_DATE + " TEXT,"
                + KEY_PASSWORD + "TEXT)";
        sqLiteDatabase.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + TABLE_NOTES);
        onCreate(sqLiteDatabase);
    }

    public void delete()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        Log.d("mLog", "delete and create");
        //onCreate(sqLiteDatabase);
        //sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NOTES + " ADD COLUMN " + KEY_PASSWORD + " TEXT");
    }

    public void addNote(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(KEY_CONTENT, note.getContent());
        cValues.put(KEY_DATE, note.getDate());

        // inserting raw
        db.insert(TABLE_NOTES, null, cValues);
        db.close();
    }

    public Note getNote(int id)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTES,
                        new String[] {KEY_ID, KEY_DATE,
                        KEY_CONTENT, KEY_PASSWORD},
                        KEY_ID + "=?",
                        new String [] {String.valueOf(id)},
                        null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)), cursor.getString(2), cursor.getString(1));
        note.setPassword(cursor.getString(3));
        return note;
    }

    public List<Note> getAllNotes()
    {
        final List<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        final Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do{
                Note note = new Note(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
                notes.add(note);
            }
            while(cursor.moveToNext());
        }

        return notes;
    }

    public int getNotesCount()
    {
        String query = "SELECT * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateSingleNote(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, note.getContent());
        values.put(KEY_DATE, note.getDate());

        // updating row
        return db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(note.getId())});
    }

    public boolean deleteNote(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NOTES, KEY_ID + "=" + String.valueOf(id), null) > 0;
    }

    public int setPassword(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(KEY_PASSWORD, note.getPassword());

        Log.d("mLog", "database " + note.getPassword() + " " + note.getId() + " cVal " + cValues.toString());

        return db.update(TABLE_NOTES, cValues, KEY_ID + " = ?",
                new String[] { String.valueOf(note.getId())});
    }
}
