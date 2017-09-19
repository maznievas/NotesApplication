package com.andrey.notesapplication;

import android.content.Context;

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
    }

    public void addNote(String content, String date)
    {
        db.addNote(new Note(content, date));
        view.udateList(db.getAllNotes());
    }

    public void updateList()
    {
        view.udateList(db.getAllNotes());
    }

    public interface View
    {
        public void udateList(List<Note> allNotes);
    }
}
