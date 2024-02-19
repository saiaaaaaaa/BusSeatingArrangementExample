package com.salazarisaiahnoel.busseatingarrangementexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 31){
            Intent i = new Intent(MainActivity.this, BusList.class);
            startActivity(i);
        } else {
            new Handler().postDelayed(() -> {
                Intent i = new Intent(MainActivity.this, BusList.class);
                startActivity(i);
            }, 1500);
        }
    }
}