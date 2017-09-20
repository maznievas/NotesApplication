package com.andrey.notesapplication;

import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by sts on 19.09.17.
 */

public class EditNotePresenter {

    Note note;
    DataBaseHandler db;
    View view;

    public EditNotePresenter(View editNoteFragment, Context context)
    {
        note = new Note();
        db = new DataBaseHandler(context);
        view = editNoteFragment;
    }

    public void setIdNOte(int id)
    {
        note.setId(id);
    }

    public int getIdNOte()
    {
        return note.getId();
    }

    public void openNote()
    {
        note = db.getNote(note.getId());
        view.openNote(note.getContent());
    }

    public void saveNote(String text, int id)
    {
        db.updateSingleNote(new Note(id,
                text,
                String.valueOf(new SimpleDateFormat("d MMMM, yyyy  HH:mm", Locale.getDefault()).format(new Date()))));
    }

    public void addNote(String content)
    {
        db.addNote(new Note(content,
                String.valueOf(new SimpleDateFormat("d MMMM, yyyy  HH:mm", Locale.getDefault()).format(new Date()))));
    }

    public interface View
    {
        public void saveNote(Note note);
        public void openNote(String noteData);
    }
}

