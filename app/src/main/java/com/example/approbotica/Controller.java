package com.example.approbotica;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.example.Network.Client;
import com.example.Network.Message;

import java.io.IOException;
import java.net.UnknownHostException;

public class Controller {
    private boolean connection;
    private int battery = 69;
    private int speed = 0;
    private int weight = 0;
    private int[] decibel;
    private int currentGrid;
    private String cameraFeed;
    private String currentAction = "controller";

    private volatile boolean stopThread = false;
    private static final String TAG = "MainActivity";

    private static Client client;

    static {
        try {
            client = new Client("192.168.50.1", 8080);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static Controller controller;
    private Controller(){}

    public static Controller getInstance() {
        if (controller == null)
            controller = new Controller();
        return controller;
    }

    public boolean getConnection(){ return connection; }
    public int getBattery(){ return battery; }
    public int getSpeed(){ return speed; }
    public int getWeight(){ return weight; }
    public String getCurrentAction() { return currentAction; }

    public void sendSignal(String[] a, String[] b){
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable(a,b);
        new Thread(runnable).start();
    }

    public boolean setConnection(){
        connection = true;
        //do connection stuff here
        return connection;
    }

    public void startThread(View view) {
    }

    public void stopThread(View view) {
        stopThread = true;
    }

    class ExampleRunnable implements Runnable {
        Message message;

        ExampleRunnable(String [] a, String [] b) {
            message = new Message(a,b);
        }

        @Override
        public void run() {
            if (stopThread)
                return;
            try {
                client.sendMessage(message.getStringObject());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
