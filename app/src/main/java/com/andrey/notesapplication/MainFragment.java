package com.andrey.notesapplication;

//import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Created by sts on 19.09.17.
 */

public class MainFragment extends Fragment implements DataBasePresenter.View {

    final String TAG = "mLog";
    DataBasePresenter dbPresenter;

    RecyclerView mRecyclerView;
    FloatingActionButton fabNewNote;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter mAdapter;

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);

        fabNewNote = view.findViewById(R.id.floatingButton);

        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Date d = new Date();
        context = getContext();

        dbPresenter = new DataBasePresenter(this, this.getContext());
     //
      //  Log.d(TAG, String.valueOf(new SimpleDateFormat("d MMMM, yyyy  HH:mm", Locale.getDefault()).format(new Date())));
        dbPresenter.updateList();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Toast.makeText(context, "Single Click on position :" + position,
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), EditNoteActivity.class);
                intent.putExtra("id",  String.valueOf(mAdapter.getIdByPosition(position)));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(context, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));

        fabNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), EditNoteActivity.class);
                intent.putExtra("id",  "-1");
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void udateList(List<Note> allNotes) {
        for(Note note : allNotes)
        {
            String log = "id = " + note.getId() + " date: " + note.getDate() + " content: " + note.getContent();
            Log.d(TAG, log);
        }
        mAdapter = new RecyclerAdapter(allNotes);
        //mAdapter.
        mRecyclerView.setAdapter(mAdapter);
    }


}
