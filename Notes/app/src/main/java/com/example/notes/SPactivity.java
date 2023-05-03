package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SPactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable(){
            public void run(){
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        },3000);
    }
}