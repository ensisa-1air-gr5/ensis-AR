package com.example.ensisar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.activity_main_play_btn1);
        button2 = (Button) findViewById(R.id.activity_main_play_btn2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   setContentView(R.layout.activity_ux);
                Intent visite = new Intent(MainActivity.this, EnsisarVisit.class);
                startActivity(visite);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameActivity2 = new Intent(MainActivity.this, Qrcode.class);
                startActivity(gameActivity2);
            }
        });
    }
}
