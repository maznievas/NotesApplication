package com.andrey.notesapplication;

import android.view.View;

/**
 * Created by sts on 19.09.17.
 */


public interface ClickListener{
    public void onClick(View view, int position);
    public void onLongClick(View view,int position);
}