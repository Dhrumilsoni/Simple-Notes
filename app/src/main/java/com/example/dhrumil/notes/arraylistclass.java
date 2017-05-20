package com.example.dhrumil.notes;

import java.util.ArrayList;

import Models.Note;

/**
 * Created by dhrumil on 28/01/2017.
 */

public class arraylistclass {
    ArrayList<Note> ar=new ArrayList<Note>();
    ArrayList<Note> recyclebin=new ArrayList<>();
    public ArrayList<Note> getAr()
    {
        return ar;
    }

    public void setAr(ArrayList<Note> ar) {
        this.ar = ar;
    }
    public ArrayList<Note> getDeleted(){return recyclebin;}
    public void setDeleted(ArrayList<Note> recyclebin){
        this.recyclebin=recyclebin;
    }
}
