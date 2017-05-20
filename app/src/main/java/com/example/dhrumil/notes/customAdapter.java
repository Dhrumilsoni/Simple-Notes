package com.example.dhrumil.notes;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dhrumil on 04/05/2017.
 */
import java.util.ArrayList;
import java.util.List;

import Models.Note;

public class customAdapter extends RecyclerView.Adapter<customAdapter.MyViewHolder> {
    /*private int resource;
    public customAdapter(Context context,int resource,List<Note> objects) {
        super(context, resource, objects);
        this.resource=resource;
    }*/
    public interface RecyclerViewClickListener{
        void onDeleteViewClicked(View v, int position);
        void onSwitchViewClicked(View v,int position);
        void onRowViewClicked(View v,int position);
    }
    public RecyclerViewClickListener recyclerViewClickListener;
    private ArrayList<Note> ar;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView title,detailnote;
        ImageView DeleteButton;
        Switch aSwitch;
        public MyViewHolder(View view){
            super(view);

            title=(TextView) view.findViewById(R.id.title);
            detailnote=(TextView) view.findViewById(R.id.notedetail);
            DeleteButton=(ImageView) view.findViewById(R.id.DeleteButton);
            aSwitch=(Switch) view.findViewById(R.id.switch1);

            view.setOnClickListener(this);
            DeleteButton.setOnClickListener(this);
            aSwitch.setOnClickListener(this);
            //FavButton=(ImageView) view.findViewById(R.id.FavButton);
            /*DeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                      listener.onViewClicked(v,getAdapterPosition());
                    }
                    /*customAdapter.this.ar.remove(ar.get(getAdapterPosition()));
                    customAdapter.this.notifyDataSetChanged();

                }
            });*/
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==DeleteButton.getId()){
                recyclerViewClickListener.onDeleteViewClicked(v,getAdapterPosition());
                Toast.makeText(v.getContext(),"Del",Toast.LENGTH_SHORT).show();
            }else if(v.getId()==aSwitch.getId()){
                recyclerViewClickListener.onSwitchViewClicked(v,getAdapterPosition());
                Toast.makeText(v.getContext(),"Switch",Toast.LENGTH_SHORT).show();
            }else{
                recyclerViewClickListener.onRowViewClicked(v,getAdapterPosition());
                Toast.makeText(v.getContext(),"Row",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public customAdapter(ArrayList<Note> ar,RecyclerViewClickListener recyclerViewClickListener){
        this.ar=ar;
        this.recyclerViewClickListener=recyclerViewClickListener;
    }
    /*public View getView(int position, View convertView,ViewGroup parent){
        View view =convertView;
        if(view==null){
            LayoutInflater layoutInflater=LayoutInflater.from(getContext());
            view=layoutInflater.inflate(resource,null);
        }
        Note note=getItem(position);
        if (note != null) {
            TextView notetext=(TextView) view.findViewById(R.id.Notename);
            //Button deleteButton=(Button) view.findViewById(R.id.DeleteButton);
            //Button favButton=(Button) view.findViewById(R.id.FavButton);
            if(notetext!=null){
                notetext.setText(note.toString());
            }
            //if(deleteButton!=null){

            //}
        }

        return view;
    }
*/
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_griditem,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Note note=ar.get(position);
        holder.title.setText(note.toString());
        holder.detailnote.setText(note.getNote());

    }

    @Override
    public int getItemCount() {
        return ar.size();
    }
}
