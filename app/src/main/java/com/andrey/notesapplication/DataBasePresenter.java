package com.andrey.notesapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;


/**
 * Created by sts on 19.09.17.
 */

public class DataBasePresenter {

    DataBaseHandler db;
    Note note;
    View view;
   // Context context;

    public DataBasePresenter(View view, Context context)
    {
        this.view = view;
        note = new Note();
        db = new DataBaseHandler(context);
       // db.delete();
    }

    public void addNote(String content, String date)
    {
        db.addNote(new Note(content, date));
        view.udateList(db.getAllNotes());
    }

    public void updateList()
    {
        if(db.getAllNotes() != null)
            view.udateList(db.getAllNotes());
    }

    public void deleteRow(int id)
    {
        db.deleteNote(id);
        view.udateList(db.getAllNotes());
    }

    public void setPassword(String password, int id)
    {
        note = db.getNote(id);
        note.setPassword(password);
        db.setPassword(note);
    }

    public String getPassword(int id)
    {
        return db.getNote(id).getPassword();
    }

    public boolean checkPassword(int id, String password)
    {
        Log.d("mLog", "input " + password + " output " + db.getNote(id).getPassword());
        if(db.getNote(id).getPassword().equals(password))
            return true;
        else
            return false;
    }


    public interface View
    {
        boolean onContextItemSelected();

        public void udateList(List<Note> allNotes);
    }
}
