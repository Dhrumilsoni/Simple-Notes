package Models;

/**
 * Created by dhrumil on 26/01/2017.
 */

public class Note {
    public String note;
    public int id;
    boolean fav=false;
    public Note(String note, int id) {
        this.note = note;
        this.id = id;
    }

    public Note() {
    }
    public String toString()
    {
        if(note.isEmpty())
            return "New Note";
        if(note.length()<=10)
            return note;
        else
            return note.substring(0,10)+"...";
    }
    public void toggleFav(){fav=!fav;}
    public boolean isFav(){
        return fav;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}