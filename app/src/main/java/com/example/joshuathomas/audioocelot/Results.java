package com.example.joshuathomas.audioocelot;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Results extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        Bundle b=this.getIntent().getExtras();

        ArrayList<String> list = getIntent().getStringArrayListExtra("result");


        String myText = "";

//        this.addView(myText);
//        for (int i=0; i<list.size();i++){
//            myText.append(list.get(i));
//            myText.append("\n");
//        }

        TextView resultsTextView = (TextView) findViewById(R.id.results_text_view);
        for (int i = 0; i < list.size(); i++) {

            if(!(i <= 386 || i == 392 || i == 396 || i == 399 || i == 403 || i == 405 || i == 407 ||i == 409 || i == 411 || i == 413 || i == 415 || i == 417 || i == 420 || i == 421 || i == 423 || i == 425 || i == 427 || i == 429 || i == 431 || i == 433 || i == 435 || i == 437 || i == 439 || i == 441 || i == 443 || i == 445 || i == 447 || i == 449 || i == 451 || i == 453 || i == 455 || i == 457 || i == 459 || i > 461)){
                myText += list.get(i) + "\n";
            }


        }
//        Toast.makeText(getApplicationContext(), list.get(17),
//                Toast.LENGTH_SHORT).show();

        myText = myText.replace("{", "");
        myText = myText.replace("}", "");
        myText = myText.replace("[", "");
        myText = myText.replace("]", "");
        myText = myText.replace(",", "");
        myText = myText.replace("\"", "");

        resultsTextView.setText(myText);
    }
}
