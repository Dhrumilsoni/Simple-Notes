package com.example.dhrumil.notes;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import Models.Note;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    static ArrayList<Note> ar = new ArrayList<Note>();
    static ArrayList<Note> recyclebin=new ArrayList<Note>();
    ArrayList<Note> search=new ArrayList<>();
    customAdapter adapter;
    static int pos;
    static arraylistclass newar = new arraylistclass();
    SharedPreferences mPrefs;
    Gson gson1;

    public void removeelement() {
        ar.remove(pos);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Notes");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mPrefs = getPreferences(MODE_PRIVATE);
        gson1  = new Gson();
        newar.setAr(ar);
        newar.setDeleted(recyclebin);
        String json = mPrefs.getString("yaah", (new Gson()).toJson(newar));
        newar = gson1.fromJson(json, arraylistclass.class);
        if ( newar != null) {
            ar = newar.getAr();
            recyclebin=newar.getDeleted();
        }
        else {
            ar = new ArrayList<Note>();
            recyclebin=new ArrayList<Note>();
        }

        //adapter=new customAdapter(search);
        adapter=new customAdapter(search, new customAdapter.RecyclerViewClickListener() {
            @Override
            public void onDeleteViewClicked(View v, int position) {
                final int pos=position;
                AlertDialog.Builder diabox=new AlertDialog.Builder(getApplication());
                diabox.setMessage("Do you want to delete this note ?").setTitle("ALERT");
                diabox.setCancelable(true);
                diabox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //adapter.remove(ar.get(pos));
                        recyclebin.add(ar.get(pos));
                        ar.remove(pos);
                        adapter.notifyDataSetChanged();
                        save();
                        dialog.cancel();
                    }
                });
                diabox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog deletedia=diabox.create();
                deletedia.show();
            }

            @Override
            public void onSwitchViewClicked(View v, int position) {
                search.get(position).toggleFav();
            }

            @Override
            public void onRowViewClicked(View v, int position) {
                (new MainActivity()).onNoteSelected(position);
            }
        });
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }

        recyclerView=(RecyclerView) findViewById(R.id.gv);
        //gridView.setBackgroundResource(R.drawable.customshape);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(adapter);
        /*recyclerView.addOnItemTouchListener(new MainActivityFragment.RecyclerTouchListener(getApplicationContext(),recyclerView,new MainActivityFragment.ClickListener() {
            public void onClick(View view, int position) {
                (new MainActivity()).onNoteSelected(position);
            }
            public void onLongClick(View view,int position){
                final int pos=position;
                AlertDialog.Builder diabox=new AlertDialog.Builder(getApplication());
                diabox.setMessage("Do you want to delete this note ?").setTitle("ALERT");
                diabox.setCancelable(true);
                diabox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //adapter.remove(ar.get(pos));
                        recyclebin.add(ar.get(pos));
                        ar.remove(pos);
                        adapter.notifyDataSetChanged();
                        save();
                        dialog.cancel();
                    }
                });
                diabox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog deletedia=diabox.create();
                deletedia.show();
            }
        }));
*/
    }
    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
        save();
    }
    public void save(){
        if(ar!=null) {
            gson1=new Gson();
            newar=new arraylistclass();
            newar.setAr(ar);
            newar.setDeleted(recyclebin);
            mPrefs = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor prefseditor = mPrefs.edit();
            String json = gson1.toJson(newar);
            //String recy= gson1.toJson(recyclebin);
            //prefseditor.putString("RecycleBin",recy);
            prefseditor.putString("yaah", json);
            prefseditor.apply();
        }
        else{
            Toast.makeText(this,"It is null",Toast.LENGTH_SHORT).show();
        }
    }
    private void doMySearch(String query) {
        for(int i=0;i<ar.size();i++){
            if(ar.get(i).getNote().contains(query)){
                pos=search.size();
                search.add(ar.get(i));
                adapter.notifyDataSetChanged();
            }
        }
    }
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivityFragment.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivityFragment.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildLayoutPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
