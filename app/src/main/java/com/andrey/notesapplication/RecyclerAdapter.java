package com.andrey.notesapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sts on 19.09.17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    final String TAG = "mLog";
    private List<Note> notes;

    public RecyclerAdapter(List<Note> dataset) {
        notes = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.content.setText(notes.get(position).getContent());
        holder.date.setText(notes.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public Note getNoteByPosition(int position)
    {
        if(position <= getItemCount()) {
            Log.d(TAG, "id of selected note = " + String.valueOf(notes.get(position).getId()));
            return notes.get(position);
        }
        else
            return new Note();
    }

    public int getIdByPosition(int position)
    {
        if(position <= getItemCount()) {
            return notes.get(position).getId();
        }
        else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView content;
        public TextView date;
        final String TAG = "mLog";

        public ViewHolder(View v) {
            super(v);
            content = (TextView) v.findViewById(R.id.content);
            date = (TextView) v.findViewById(R.id.date);
        }
    }
}
