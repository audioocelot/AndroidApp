package com.example.joshuathomas.audioocelot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.ListView;
import android.os.IBinder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.example.joshuathomas.audioocelot.MusicService.MusicBinder;

public class Select extends AppCompatActivity {

    private ArrayList<Song> songList;
    private ListView songView;

    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        songView = (ListView)findViewById(R.id.song_list);
        songList = new ArrayList<Song>();
        getSongList();

        /*Collections.sort(songList, new Comparator<Song>() {  //sorts songs alphabetically
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });*/

        SongAdapter songAdt = new SongAdapter(this, songList);
        songView.setAdapter(songAdt);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    public void getSongList() {
        //retrieve song info
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int filePathColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DATA);

            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String filePath = musicCursor.getString(filePathColumn);
                songList.add(new Song(thisId, thisTitle, thisArtist, filePath));
            }
            while (musicCursor.moveToNext());
        }
    }

    //if song is picked
    public void songPicked(final View view){

       //button that plays song
       AlertDialog.Builder songalert = new AlertDialog.Builder(this);
        songalert.setMessage("Select an option").setNegativeButton("Play Song", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
                musicSrv.playSong();
            }
        }).setNeutralButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getApplicationContext(), "Sending data",
                        Toast.LENGTH_LONG).show();

                musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
                File uploadFile1 = musicSrv.getSongFile(Integer.parseInt(view.getTag().toString()));
                if (uploadFile1.exists()) {
                    System.out.println("The song exists");
                }
                new GetData().execute(uploadFile1);


            }
        }).setPositiveButton("Stop Song", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
                //if song is playing, stop it

                musicSrv.stopsong();

            }
        })
                .create();
        songalert.show();

    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv=null;
        super.onDestroy();
    }

  class GetData extends AsyncTask<File, Void, List<String>> {

        private Exception exception;

      protected List<String> doInBackground(File... file) {
            //send to server


            List<String> response = new ArrayList<String>() ;
            String charset = "UTF-8";


            String requestURL = "https://ocelot.audio/upload";

            try {
                MultipartUtility multipart = new MultipartUtility(requestURL, charset);

                System.out.println(file[0]);
                multipart.addFilePart("audio", file[0]);

                response = multipart.finish();

                System.out.println("SERVER REPLIED:");

                for (String line : response) {
                    System.out.println(line);
                }
            } catch (IOException ex) {
                System.out.println("myError:");
                System.err.println(ex);
            }
            return response;
        }

        protected void onPostExecute(List<String> response) {
            System.out.println("myAfterExecute");
            System.out.println(response);

//            Toast.makeText(getApplicationContext(), "received",
//                    Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(Select.this, Results.class);
            Bundle b = new Bundle();
            b.putStringArrayList("result", (ArrayList<String>) response);
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
            finish();

        }
    }

}
