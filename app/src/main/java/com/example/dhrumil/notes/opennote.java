package com.example.dhrumil.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.dhrumil.notes.MainActivityFragment.newar;
import static com.example.dhrumil.notes.MainActivityFragment.ar;

import Models.Note;


public class opennote extends Fragment {

    int x;
    String val;
    EditText e1;
    Gson gson1;
    SharedPreferences mPrefs;
    public interface Onnulllistener
    {
        void removeelement();
    }
    Onnulllistener onnulllistener;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.activity_opennote,container,false);
        //CanvasView customcanvas=(CanvasView) getActivity().findViewById(R.id.signature_canvas);
        return view;
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle bundle=this.getArguments();
        if(bundle!=null){
            x=bundle.getInt("My Key",0);
        }
    }
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Activity activity=(Activity) context;
        try {
            onnulllistener = (Onnulllistener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        e1=(EditText) view.findViewById(R.id.edt1);
        val=ar.get(x).getNote();
        e1.setText(val);
    }
    public void onPause() {
        super.onPause();
        if(e1.getText().toString().equals("")){
            onnulllistener.removeelement();
        }
        else {
            ar.get(x).setNote(e1.getText().toString());
        }
        if(ar!=null) {
            gson1=new Gson();
            newar=new arraylistclass();
            newar.setAr(ar);
            mPrefs = getActivity().getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor prefseditor = mPrefs.edit();
            String json = gson1.toJson(newar);
            prefseditor.putString("yaah", json);
            prefseditor.apply();
        }
        else{
            Toast.makeText(getActivity(),"It is null",Toast.LENGTH_SHORT).show();
        }
    }
}