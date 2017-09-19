package com.andrey.notesapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sts on 19.09.17.
 */

public class EditNoteActivity extends AppCompatActivity {

    EditNotePresenter editNotePresenter;
    FragmentTransaction fTrans;
    EditNoteFragment editNoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note_activity);

        editNotePresenter = new EditNotePresenter();

        if(savedInstanceState == null)
        {
            editNoteFragment = new EditNoteFragment();

            fTrans = getSupportFragmentManager().beginTransaction();
            fTrans.add(R.id.fragmentContainer, editNoteFragment);
            fTrans.commit();
        }
    }
}
