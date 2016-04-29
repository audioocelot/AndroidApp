package com.example.joshuathomas.audioocelot;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import  android.content.Intent;
import android.view.View.OnClickListener;


import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final int AUDIO_LIBRARY_REQUEST = 20;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private Button record, select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnClickButtonListener();

        record = (Button) findViewById(R.id.button1);
        select = (Button) findViewById(R.id.button2);

    }

    public void OnClickButtonListener(){
         record=(Button)findViewById(R.id.button1); //if record is clicked
         record.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Intent rec_intent = new Intent(MainActivity.this, Record.class);
                            startActivity(rec_intent);
                       }
                }
        );

        select=(Button)findViewById(R.id.button2); //if select song is clicked
        select.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*
                        //invoke music library using an implicit intent
                        Intent audio_picker = new Intent(Intent.ACTION_PICK);

                        //Where do we want to find the data?
                        File audioDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                        String audioDirectoryPath = audioDirectory.getPath();
                        //get a URI representation
                        Uri data = Uri.parse(audioDirectoryPath);

                        //set the data and type. Get mp3
                        audio_picker.setDataAndType(data, "audio/*"); //error is here. "audio" is wrong word?

                        startActivityForResult(audio_picker, AUDIO_LIBRARY_REQUEST);

                        */

                        Intent sel_intent = new Intent(MainActivity.this, Select.class);
                        startActivity(sel_intent);


                    }
                }
        );



    }



}
