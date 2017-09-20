package com.andrey.notesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.skoumal.fragmentback.BackFragmentFragmentActivity;

/**
 * Created by sts on 19.09.17.
 */

public class EditNoteActivity extends  BackFragmentFragmentActivity {

    EditNoteFragment editNoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note_activity);

        Intent intent = getIntent();
        String  idStr = intent.getStringExtra("id");
        Log.d("mLog", "getted id = " + idStr);


        if(savedInstanceState == null)
        {
            editNoteFragment = EditNoteFragment.newInstance(idStr);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, editNoteFragment)
                    .commit();
        }
    }
}
