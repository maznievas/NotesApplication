package com.andrey.notesapplication;

//import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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

        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        fabNewNote = view.findViewById(R.id.floatingButton);

        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Date d = new Date();
        context = getContext();

        dbPresenter = new DataBasePresenter(this, this.getContext());
     //
      //  Log.d(TAG, String.valueOf(new SimpleDateFormat("d MMMM, yyyy  HH:mm", Locale.getDefault()).format(new Date())));


        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                if(dbPresenter.getPassword(mAdapter.getIdByPosition(position)) == "" ||
                        dbPresenter.getPassword(mAdapter.getIdByPosition(position)) == null) {
                    Intent intent = new Intent(getActivity(), EditNoteActivity.class);
                    intent.putExtra("id", String.valueOf(mAdapter.getIdByPosition(position)));
                    startActivity(intent);
                }
                else if(dbPresenter.getPassword(mAdapter.getIdByPosition(position)) != "" ||
                        dbPresenter.getPassword(mAdapter.getIdByPosition(position)) != null)
                {
                  showAlterDialogCheckPassword(mAdapter.getIdByPosition(position));
                }
            }

            @Override
            public void onLongClick(View view, int position) {
              //  Toast.makeText(context, "Long press on position :"+position,
             //           Toast.LENGTH_LONG).show();
                showPopupMenu(view, mAdapter.getIdByPosition(position));
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



    public void onResume() {
        super.onResume();
        dbPresenter.updateList();
    }

    @Override
    public boolean onContextItemSelected() {
        return false;
    }

    public void showPopupMenu(View view, final int id)
    {
        PopupMenu popupMenu = new PopupMenu(this.getContext(), view);
        popupMenu.inflate(R.menu.popup_menu);

        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.delete:
                               Log.d(TAG, "delete");
                                dbPresenter.deleteRow(id);
                                return true;
                            case R.id.setPassword:
                                Log.d(TAG, "password first = " + dbPresenter.getPassword(id));
                                if(dbPresenter.getPassword(id) == "" || dbPresenter.getPassword(id) == null)
                                {
                                    showAlterDialogFirst(id);
                                }
                                else
                                    showAlterDialogChangePassword(id);
                                return true;
                            default:
                                return false;
                        }
                    }
                });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {

            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });
        popupMenu.show();
    }

    public void showAlterDialogFirst(final int id)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());

        alert.setTitle("Setting password");
        alert.setMessage("Enter password");

// Set an EditText view to get user input
        final EditText input = new EditText(this.getContext());
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dbPresenter.setPassword(input.getText().toString(), id);
                Log.d(TAG, "After password " + dbPresenter.getPassword(id));
                dbPresenter.updateList();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    public void showAlterDialogChangePassword(final int id)
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());

        alert.setTitle("Change password");
        alert.setMessage("Enter old password and than new one");

        final EditText input = new EditText(this.getContext());
        final EditText inputConfirm = new EditText(this.getContext());

        LinearLayout ll = new LinearLayout(this.getContext());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(input);
        ll.addView(inputConfirm);
        alert.setView(ll);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(input.getText().toString().equals(dbPresenter.getPassword(id))) {
                    dbPresenter.setPassword(inputConfirm.getText().toString(), id);
                    Log.d(TAG, "password " + dbPresenter.getPassword(id));
                }
                else
                    alert.setMessage("Enter equal passwords to each field.");
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    public void showAlterDialogCheckPassword(final int id)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());

        alert.setTitle("Checking password");
        alert.setMessage("Enter password");

        final EditText input = new EditText(this.getContext());
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.d(TAG, "alter dialog checking " + dbPresenter.checkPassword(id, input.getText().toString()));
                if(dbPresenter.checkPassword(id, input.getText().toString())) {
                        Log.d(TAG, "correct password");
                        //Log.d(TAG, "if correct " + dbPresenter.checkPassword(mAdapter.getIdByPosition(position), showAlterDialogCheckPassword()));
                        Intent intent = new Intent(getActivity(), EditNoteActivity.class);
                        intent.putExtra("id", String.valueOf(id));
                        startActivity(intent);
                    }
                else Toast.makeText(context, "Enter correct password",
                           Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }


    @Override
    public void udateList(List<Note> allNotes) {
     
        for(Note note : allNotes)
        {
            String log = "id = " + note.getId() + " date: " + note.getDate() + " content: " + note.getContent();
            Log.d(TAG, log);
        }
        mAdapter = new RecyclerAdapter(allNotes, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }


}
