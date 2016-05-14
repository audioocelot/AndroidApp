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

            if (!(i ==0 || i ==6 || i ==10 || i == 17 || i ==19 || i== 22 || i == 23 || i ==25 || i == 28 || i== 29|| i==31 || i ==34 || i ==35 || i ==37 || i ==40 || i ==41 || i ==43 || i ==46 || i ==47 || i ==49 || i ==51 || i ==53 || i ==55 || i ==57 || i ==59 || i ==61 || i ==63 || i ==65 || i ==67 || i ==69 || i ==71 || i ==73 || i ==75 || i== 77 || i==78 || i ==79 || i ==80)){
                //i++;
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


        resultsTextView.setText(myText);
    }
}
