package com.example.joshuathomas.audioocelot;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Record extends AppCompatActivity {

    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private Button start, submit, play;

    File rec_file;

    //added to submit
    private MusicService musicSrv;

    private static final int SHORT_DELAY = 1000; // 1 second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        start = (Button) findViewById(R.id.button1);
        submit = (Button) findViewById(R.id.button2);
        play = (Button) findViewById(R.id.button3);

        submit.setEnabled(false);
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
			/*Two basic methods prepare and start to start recording the audio.*/
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        start.setEnabled(false);
        submit.setEnabled(false);
        Toast.makeText(getApplicationContext(), "Recording started",
                Toast.LENGTH_LONG).show();


        CountDownTimer cdt = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {

                if(millisUntilFinished/1000 == 15) {
                    Toast.makeText(getApplicationContext(), "15 seconds remaining", Toast.LENGTH_SHORT).show();
                }

                if(millisUntilFinished/1000 == 10) {
                    Toast.makeText(getApplicationContext(), "10 seconds remaining", Toast.LENGTH_SHORT).show();
                }

                if(millisUntilFinished/1000 == 5) {
                    Toast.makeText(getApplicationContext(), "5 seconds remaining", Toast.LENGTH_SHORT).show();
                }
            }

            public void onFinish() {


                /*This method stops the recording process.*/
                myAudioRecorder.stop();


                //set up the recorded audio as file to be submitted
                MediaPlayer m = new MediaPlayer();
                try {
                    m.setDataSource(outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //this is the recording file
                rec_file = new File(outputFile);


		/*This method should be called when the recorder instance is needed.*/
                myAudioRecorder.release();
                myAudioRecorder = null;
                submit.setEnabled(true);
                play.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio recorded successfully",
                        Toast.LENGTH_LONG).show();
            }

        }.start();

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


    public void submit(View view) {
//		/*This method stops the recording process.*/
//        myAudioRecorder.stop();
//
//		/*This method should be called when the recorder instance is needed.*/
//        myAudioRecorder.release();
//        myAudioRecorder = null;
//        submit.setEnabled(false);
//        play.setEnabled(true);
//        Toast.makeText(getApplicationContext(), "Audio recorded successfully",
//                Toast.LENGTH_LONG).show();



        //musicSrv.setSong(Integer.parseInt(view.getTag().toString()));     //put this in onFinish

        //File uploadFile1 = musicSrv.getSongFile();

        File uploadFile1 = rec_file;
        if (uploadFile1.exists()) {
            System.out.println("The song exists");
        }
        new GetData().execute(uploadFile1);
    }


    //added
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


            Intent intent = new Intent(Record.this, Results.class);
            Bundle b = new Bundle();
            b.putStringArrayList("result", (ArrayList<String>) response);
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
            finish();

        }
    }

}