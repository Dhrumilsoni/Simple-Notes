package com.example.dhrumil.notes;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import static com.example.dhrumil.notes.MainActivityFragment.ar;
import static com.example.dhrumil.notes.MainActivityFragment.recyclebin;

public class RecycleBin extends AppCompatActivity {

    RecyclerView recyclerView;
    customAdapter adapter;
    RecycleBin thisobject=this;
    public interface ClickListener {
        void onClick(View view, int position);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_bin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.recycletoolbar);
        toolbar.setTitle("Trash");
        setSupportActionBar(toolbar);

        //adapter=new customAdapter(recyclebin);
        adapter=new customAdapter(recyclebin, new customAdapter.RecyclerViewClickListener() {
            @Override
            public void onDeleteViewClicked(View v, int position) {
                final int pos=position;
                AlertDialog.Builder diabox=new AlertDialog.Builder(thisobject);
                diabox.setMessage("Do you want to delete this note ?").setTitle("ALERT");
                diabox.setCancelable(true);
                diabox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //adapter.remove(ar.get(pos));

                        recyclebin.remove(recyclebin.get(pos));
                        adapter.notifyDataSetChanged();
                        //save();
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
                recyclebin.get(position).toggleFav();
            }

            @Override
            public void onRowViewClicked(View v,final int position) {
                final int pos=position;
                AlertDialog.Builder diabox=new AlertDialog.Builder(thisobject);
                diabox.setMessage("Do you want to recover this note ?").setTitle("ALERT");
                diabox.setCancelable(true);
                diabox.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ar.add(recyclebin.get(position));
                        recyclebin.remove(position);
                        adapter.notifyDataSetChanged();
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
        });
        recyclerView=(RecyclerView) findViewById(R.id.recyclerrecycle);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);



        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),recyclerView,new ClickListener() {
            public void onClick(View view, final int position) {
                final int pos=position;
                AlertDialog.Builder diabox=new AlertDialog.Builder(thisobject);
                diabox.setMessage("Do you want to recover this note ?").setTitle("ALERT");
                diabox.setCancelable(true);
                diabox.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ar.add(recyclebin.get(position));
                        recyclebin.remove(position);
                        adapter.notifyDataSetChanged();
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
        }));*/

    }


    /*class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private RecycleBin.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final RecycleBin.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }*/
}
