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
            myText += list.get(i) + "\n";
        }

        resultsTextView.setText(myText);
    }
}
