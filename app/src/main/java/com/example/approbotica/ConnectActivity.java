package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import java.util.concurrent.TimeUnit;

public class ConnectActivity extends AppCompatActivity {

    private Controller controller = new Controller();
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        getSupportActionBar().hide();
        TextView connectionstring = (TextView) findViewById(R.id.connectionString);
        if (controller.setConnection()){
            connectionstring.setText("Connected.");
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(ConnectActivity.this, RobotActivity.class);
                    intent.putExtra("CONTROLLER", controller);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        }
        else {
            connectionstring.setText("Failed.");
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(ConnectActivity.this, RobotActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        }
    }
}