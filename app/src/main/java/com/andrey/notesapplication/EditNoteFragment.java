package com.andrey.notesapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;

import net.skoumal.fragmentback.BackFragment;

import org.reactivestreams.Subscriber;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sts on 19.09.17.
 */

public class EditNoteFragment extends Fragment  implements EditNotePresenter.View, BackFragment {

    EditNotePresenter editNotePresenter;
    final String TAG = "mLog";
    MultiAutoCompleteTextView mAutoCompTV;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_note, null);

        mAutoCompTV = (MultiAutoCompleteTextView) view.findViewById(R.id.multiAutoCompleteTextView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        editNotePresenter = new EditNotePresenter(this, getContext());
        editNotePresenter.setIdNOte(Integer.parseInt(getArguments().getString("KEY_ID")));
        if(Integer.parseInt(getArguments().getString("KEY_ID")) != -1) {
            editNotePresenter.openNote();
        }

        return view;
    }

    @Override
    public void saveNote(Note note) {

    }

    @Override
    public void openNote(String noteData) {
      //  Log.d(TAG, "content of note " + noteData);
        mAutoCompTV.setText(noteData);
    }

    public static EditNoteFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("KEY_ID", id);

        EditNoteFragment fragment = new EditNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onBackPressed() {
        Log.d(TAG, "onBackPressed()");

        Log.d(TAG, "Note id " + editNotePresenter.getIdNOte());
        if(editNotePresenter.getIdNOte() == -1)
            editNotePresenter.addNote(mAutoCompTV.getText().toString());
        else
            editNotePresenter.saveNote(mAutoCompTV.getText().toString(), editNotePresenter.getIdNOte());

        return false;
    }

    @Override
    public int getBackPriority() {
        return 0;
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
