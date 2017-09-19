package com.andrey.notesapplication;

//import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

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

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);

        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        dbPresenter = new DataBasePresenter(this, this.getContext());
        //dbPresenter.addNote("Hello, this is seventh test note with MVP and fragment. I hope it will be success. la la la la la la al al.", "18.09.2017 16:52");
        dbPresenter.updateList();

        return view;
    }

    @Override
    public void udateList(List<Note> allNotes) {
        /*for(Note note : allNotes)
        {
            String log = "id = " + note.getId() + " date: " + note.getDate() + " content: " + note.getContent();
            Log.d(TAG, log);
        }*/
        mAdapter = new RecyclerAdapter(allNotes);
        mRecyclerView.setAdapter(mAdapter);
    }
}
