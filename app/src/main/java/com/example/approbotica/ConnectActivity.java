package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import java.util.concurrent.TimeUnit;

public class ConnectActivity extends AppCompatActivity {

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        getSupportActionBar().hide();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                final TextView textViewToChange = (TextView) findViewById(R.id.textView);
                textViewToChange.setText("Connected.");
            }
        }, 3000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent (ConnectActivity.this, RobotActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    private void setup() {
        try {
            if (connectToApp()){
                final TextView textViewToChange = (TextView) findViewById(R.id.textView);
                textViewToChange.setText("Connected.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean connectToApp() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return true;
    }
}