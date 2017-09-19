package com.andrey.notesapplication;



import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String TAG = "mLog";
    MainFragment mainFragment;
    FragmentTransaction fTrans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            mainFragment = new MainFragment();

            fTrans = getSupportFragmentManager().beginTransaction();
            fTrans.add(R.id.frgmCont, mainFragment);
            fTrans.commit();
        }

       // DataBaseHandler db = new DataBaseHandler(this);
//
//        db.addNote(new Note("Hello, this is first test note. I hope it will be success. la la la la la la al al.", "18.09.2017 16:52"));
//        db.addNote(new Note("Hello, this is second test note. I hope it will be success. la la la la la la al al.", "18.09.2017 16:53"));
//        db.addNote(new Note("Hello, this is third test note. I hope it will be success. la la la la la la al al.", "18.09.2017 16:54"));
   //     db.addNote(new Note("Hello, this is fifth test note. I hope it will be success. la la la la la la al al.", "18.09.2017 16:57"));
//
//        List<Note> notes = db.getAllNotes();
//
//        for(Note note : notes)
//        {
//            String log = "id = " + note.getId() + " date: " + note.getDate() + " content: " + note.getContent();
//            Log.d(TAG, log);
//        }
    }


}
