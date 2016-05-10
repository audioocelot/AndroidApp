package com.example.joshuathomas.audioocelot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Results extends AppCompatActivity {

   TextView myText = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        Bundle b=this.getIntent().getExtras();

        ArrayList<String> list = getIntent().getStringArrayListExtra("result");


        LinearLayout lView = new LinearLayout(this);

        myText= new TextView(this);
        lView.addView(myText);
        for (int i=0; i<list.size();i++){
            myText.append(list.get(i));
            myText.append("\n");
        }

        myText.setId();
       setContentView(lView); //results are here





    }
}
