package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;

import com.example.Network.Client;
import com.example.Network.Message;

import java.io.IOException;
import java.net.UnknownHostException;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "MainActivity2";

    private Button buttonStartThread;

    private Handler mainHandler = new Handler();

    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        buttonStartThread = findViewById(R.id.button_start_thread);
    }

    public void startThread(View view) {
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable(10);
        new Thread(runnable).start();
        /*
        ExampleThread thread = new ExampleThread(10);
        thread.start();
        */
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                //work
            }
        }).start();
        */
    }

    public void stopThread(View view) {
        stopThread = true;
    }

    class ExampleThread extends Thread {
        int seconds;

        ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) {
                Log.d(TAG, "startThread: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ExampleRunnable implements Runnable {
        int seconds;

        ExampleRunnable(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) {
                if (stopThread)
                    return;
                if (i == 5) {
                    Client client = null;
                    try {
                        client = new Client("192.168.50.1", 8080);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    String [] a = {"MT"};
                    String [] b = {"SD"};
                    Message msg = new Message(a,b);
                    try {
                        client.sendMessage(msg.getStringObject());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "startThread: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}