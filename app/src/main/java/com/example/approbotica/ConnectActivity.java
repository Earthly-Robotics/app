package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

public class ConnectActivity extends AppCompatActivity {

    private Timer timer;
    private DatagramSocket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        getSupportActionBar().hide();
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (Controller.getInstance().setClient("141.252.29.102", 7070, socket))
        {
            Controller.getInstance().receiveMessage();
            Controller.getInstance().setConnection(false);
            Controller.getInstance().setConnectionPing(false);
            tryConnection();
        }
    }

    public void tryConnection()
    {
        Runnable Listen = new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().isDaemon();
                TextView connectionstring = (TextView) findViewById(R.id.connectionString);
                try {
                    Thread.currentThread().sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Controller.getInstance().sendMessage(new String [] {"MT"}, new String [] {"PING"});
                try {
                    Thread.currentThread().sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Controller.getInstance().getConnection())
                            connectionstring.setText("Connected.");
                        else
                            connectionstring.setText("Failed.");
                    }
                });
                try {
                    Thread.currentThread().sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Controller.getInstance().setActive(true);
                Controller.getInstance().pingMessage();
                Intent intent = new Intent(ConnectActivity.this, RobotActivity.class);
                startActivity(intent);
                finish();
            }
        };
        new Thread(Listen).start();
    }
}