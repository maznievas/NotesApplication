package com.andrey.notesapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sts on 19.09.17.
 */

public class EditNoteFragment extends Fragment  implements EditNotePresenter.View{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_note, null);
        return view;
    }

    @Override
    public void saveNote(Note note) {

    }
}
