package com.example.btl.mp3player.models;

import java.io.Serializable;
import java.util.ArrayList;



public class Artist implements Serializable {
    private int id;
    private String name;
    private ArrayList<Song> lstSong;

    public Artist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Artist(int id, String name, ArrayList<Song> lstSong) {
        this.id = id;
        this.name = name;
        this.lstSong = lstSong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Song> getLstSong() {
        return lstSong;
    }

    public void setLstSong(ArrayList<Song> lstSong) {
        this.lstSong = lstSong;
    }
}
