package com.example.dhrumil.notes;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.view.WindowManager.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import android.support.v7.widget.CardView;
import java.io.Serializable;
import java.util.ArrayList;
import Models.Note;
import static android.content.Context.MODE_PRIVATE;


public class MainActivityFragment extends Fragment{
    Onqwertylistener Callback;
    ArrayList<Note> nonfavourites;
    RecyclerView recyclerView;
    static ArrayList<Note> ar = new ArrayList<Note>();
    static ArrayList<Note> recyclebin=new ArrayList<Note>();
    customAdapter adapter;
    static int pos;
    static arraylistclass newar = new arraylistclass();
    FloatingActionButton fab1;
    SharedPreferences mPrefs;
    Gson gson1;
    DrawerLayout mDrawerLayout;
    MainActivity activity= (MainActivity) getActivity();

    public void removeelement() {
        ar.remove(pos);
        adapter.notifyDataSetChanged();
    }

    /*@Override
    public void onDeleteViewClicked(View v, int position) {
        final int pos=position;
        AlertDialog.Builder diabox=new AlertDialog.Builder(getActivity());
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
        ar.get(position).toggleFav();
    }

    @Override
    public void onRowViewClicked(View v, int position) {
        Callback.onNoteSelected(position);
    }*/

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public interface Onqwertylistener
    {
        void onNoteSelected(int pos);
    }
    /*void Columns(Boolean b){
        if(b==true){
            recyclerView.setNumColumns(1);
        }
        else{
            gridView.setNumColumns(3);
        }
    }*/
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.listfrag,container,false);
        return view;
    }
    public void onViewCreated(View view,Bundle savedInstancestate)
    {
        recyclerView=(RecyclerView) view.findViewById(R.id.gv);
        //gridView.setBackgroundResource(R.drawable.customshape);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //Callback=(Onqwertylistener) getActivity();

        mDrawerLayout=(DrawerLayout) getActivity().findViewById(R.id.drawerlayout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        fab1= (FloatingActionButton) view.findViewById(R.id.fab);
        fab1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                pos = ar.size();
                ar.add(new Note("", pos +1));
                adapter.notifyDataSetChanged();
                Callback.onNoteSelected(pos);
            }
        });
        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(),recyclerView,new ClickListener() {
            public void onClick(View view, int position) {
                Callback.onNoteSelected(position);
            }
            public void onLongClick(View view,int position){
                final int pos=position;
                AlertDialog.Builder diabox=new AlertDialog.Builder(getActivity());
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
        /*gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //adapter.remove(ar.get(position));
                final int pos=position;
                AlertDialog.Builder diabox=new AlertDialog.Builder(getActivity());
                diabox.setMessage("Do you want to delete this note ?").setTitle("ALERT");
                diabox.setCancelable(true);
                diabox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //adapter.remove(ar.get(pos));
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
                return true;
            }
        });*/

    }
    public void showNonFav(){
        for(int i=0;i<nonfavourites.size();i++){
            ar.add(nonfavourites.get(i));
        }
        adapter.notifyDataSetChanged();
    }
    public void hideNonFav(){

        nonfavourites=new ArrayList<>();
        for(int i=0;i<ar.size();i++){
            if(ar.get(i).isFav()){
                ar.remove(ar.get(i));
            }else{
                nonfavourites.add(ar.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mPrefs = getActivity().getPreferences(MODE_PRIVATE);
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

        adapter=new customAdapter(ar, new customAdapter.RecyclerViewClickListener() {
            @Override
            public void onDeleteViewClicked(View v, int position) {
                final int pos=position;
                AlertDialog.Builder diabox=new AlertDialog.Builder(getActivity());
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
                ar.get(position).toggleFav();
            }

            @Override
            public void onRowViewClicked(View v, int position) {
                Callback.onNoteSelected(position);
            }
        });
    }
    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
        save();
    }
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Activity activity=(Activity) context;
        try {
            Callback = (Onqwertylistener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    public void save(){
        if(ar!=null) {
            gson1=new Gson();
            newar=new arraylistclass();
            newar.setAr(ar);
            newar.setDeleted(recyclebin);
            mPrefs = getActivity().getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor prefseditor = mPrefs.edit();
            String json = gson1.toJson(newar);
            //String recy= gson1.toJson(recyclebin);
            //prefseditor.putString("RecycleBin",recy);
            prefseditor.putString("yaah", json);
            prefseditor.apply();
        }
        else{
            Toast.makeText(getActivity(),"It is null",Toast.LENGTH_SHORT).show();
        }
    }
    /*public void onPause() {
        super.onPause();
          if (newar != null)
            newar.setAr(ar);
        mPrefs = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefseditor = mPrefs.edit();
        String json = gson1.toJson(newar);
        prefseditor.putString("yaah", json);
        prefseditor.apply();
    }*/



    /*static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

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
    }*/
}
