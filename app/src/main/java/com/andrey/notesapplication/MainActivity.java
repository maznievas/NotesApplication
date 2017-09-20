package com.andrey.notesapplication;



import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import net.skoumal.fragmentback.BackFragmentAppCompatActivity;
import net.skoumal.fragmentback.BackFragmentHelper;

import java.util.List;

public class MainActivity extends BackFragmentAppCompatActivity {

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

    }

    @Override
    public void onBackPressed() {
        // first ask your fragments to handle back-pressed event
        if(!BackFragmentHelper.fireOnBackPressedEvent(this)) {
            // lets do the default back action if fragments don't consume it
            super.onBackPressed();
        }
    }


}
