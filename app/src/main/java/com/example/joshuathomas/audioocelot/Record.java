package com.example.joshuathomas.audioocelot;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class Record extends AppCompatActivity {

    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private Button start, stop, play;

    private static final int SHORT_DELAY = 1000; // 1 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        start = (Button) findViewById(R.id.button1);
        stop = (Button) findViewById(R.id.button2);
        play = (Button) findViewById(R.id.button3);

        stop.setEnabled(false);
        play.setEnabled(false);
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        outputFile = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/" + ts + ".wav";
        ;


        // 1. Create a MediaRecorder object
        myAudioRecorder = new MediaRecorder();

		/*set the source , output and encoding format and output file.*/

        //2. This method specifies the source of audio to be recorded
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        //3. This method specifies the audio format in which audio to be stored
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        //4. This method specifies the audio encoder to be used
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        //5. Choose the file to save audio
        myAudioRecorder.setOutputFile(outputFile);
    }


    //6. Methods in the mediaRecorder allow you to to start and stop recording
    public void start(View view) {
        try {
			/*Two basic methods perpare and start to start recording the audio.*/
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        start.setEnabled(false);
        stop.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Recording started",
                Toast.LENGTH_LONG).show();


        CountDownTimer cdt = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                Toast.makeText(getApplicationContext(), "seconds remaining: " + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();

            }

            public void onFinish() {
                //Toast.makeText(getApplicationContext(),"done!",Toast.LENGTH_SHORT).show();

                //////////////////
                /*This method stops the recording process.*/
                myAudioRecorder.stop();

		/*This method should be called when the recorder instance is needed.*/
                myAudioRecorder.release();
                myAudioRecorder = null;
                stop.setEnabled(false);
                play.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio recorded successfully",
                        Toast.LENGTH_LONG).show();
            }

        }.start();

   }



    //countdown timer function
//    public void cdt()
//
//    {
//        new CountDownTimer(30000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                Toast.makeText(getApplicationContext(), "seconds remaining: " + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
//                //here you can have your logic to set text to edittext
//            }
//
//            public void onFinish() {
//                Toast.makeText(getApplicationContext(),"done!",Toast.LENGTH_SHORT).show();
//            }
//
//        }.start();
//
//    }

    public void stop(View view) {
		/*This method stops the recording process.*/
        myAudioRecorder.stop();

		/*This method should be called when the recorder instance is needed.*/
        myAudioRecorder.release();
        myAudioRecorder = null;
        stop.setEnabled(false);
        play.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Audio recorded successfully",
                Toast.LENGTH_LONG).show();
    }

    //7. After the recording is done, we create a MediaPlayer object which gives us methods to play the audio.

    public void play(View view) throws IllegalArgumentException,
            SecurityException, IllegalStateException, IOException {

        MediaPlayer m = new MediaPlayer();
        m.setDataSource(outputFile);
        m.prepare();
        m.start();
        Toast.makeText(getApplicationContext(), "Playing audio",
                Toast.LENGTH_LONG).show();

    }
}