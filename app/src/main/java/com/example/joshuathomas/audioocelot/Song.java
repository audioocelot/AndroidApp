package com.example.joshuathomas.audioocelot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Song extends AppCompatActivity {

    private long id;
    private String title;
    private String artist;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
    }

    public Song(long songID, String songTitle, String songArtist, String filePath) {
        id=songID;
        title=songTitle;
        artist=songArtist;
        this.filePath = filePath;
    }

    public long getID(){return id;}
    public String getSongTitle(){return title;} //this was changed from getTitle
    public String getArtist(){return artist;}

    public String getFilePath() {
        return filePath;
    }

}
