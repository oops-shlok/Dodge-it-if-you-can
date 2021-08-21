package com.example.spider_ballgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;
    private Context context;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        textView = findViewById(R.id.textView);
        textView2=findViewById(R.id.textView3);
        int points=getIntent().getExtras().getInt("point");
        textView.setText("Points: "+points);
        sharedPreferences=getSharedPreferences("hs",0);
        int highest=sharedPreferences.getInt("hs",0);
        if(points>highest){
            highest=points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("hs",highest);
            editor.commit();
        }
        textView2.setText("High Score: "+highest);
    }

}