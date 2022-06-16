package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

import java.util.concurrent.TimeUnit;

public class ConnectActivity extends AppCompatActivity {

    private Timer timer;
    private DatagramSocket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        getSupportActionBar().hide();

        TextView connectionstring = (TextView) findViewById(R.id.connectionString);

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        if (Controller.getInstance().setConnection("141.252.29.102", 8080, socket))
        {
            Controller.getInstance().receiveMessage();
            Controller.getInstance().pingMessage();
            connectionstring.setText("Connected.");
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
        else
        {
            connectionstring.setText("Failed.");
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    finish();
                }
            }, 1000);
        }
    }
}