package com.example.approbotica;

import android.graphics.Bitmap;

import com.example.Network.Client;
import com.example.Network.Message;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

public class Controller {
    private boolean connection;
    private boolean connectionPing = false;
    private boolean active = true;
    private int battery = 69;
    private String velocity = "0 m/s";
    private String weight = "0 g";
    private int decibel;
    private int currentGrid;
    private String currentAction = "Manual";
    private Bitmap cameraDebug = null;
    private Bitmap camera = null;

    private String lowerArea = "0";
    private String upperArea = "0";
    private String lowerShape = "0";
    private String upperShape = "0";

    private static Controller controller;
    private Controller(){}

    public static Controller getInstance() {
        if (controller == null)
            controller = new Controller();
        return controller;
    }

    private static Client client;

    public boolean setConnection(String ip, int port, DatagramSocket socket){
        try {
            client = new Client(ip, port, socket);
            active = true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getConnection(){ return connection; }
    public int getBattery(){ return battery; }
    public String getVelocity(){ return velocity; }
    public String getWeight(){ return weight; }
    public String getCurrentAction() { return currentAction; }
    public int getDecibel(){ return decibel; }
    public Bitmap getCameraDebug(){ return cameraDebug; }
    public Bitmap getCamera(){ return camera; }

    public String getLowerArea(){return lowerArea; }
    public String getUpperArea(){return upperArea; }
    public String getLowerShape(){return lowerShape; }
    public String getUpperShape(){return upperShape; }

    public void setConnection(boolean connection){ this.connection = connection; }
    public void setActive(boolean active){ this.active = active; }
    public void setConnectionPing(boolean connectionPing){ this.connectionPing = connectionPing; }
    public void setBattery(int battery){ this.battery = battery; }
    public void setVelocity(String velocity){ this.velocity = velocity; }
    public void setWeight(String weight){this.weight = weight; }
    public void setCurrentAction(String currentAction) { this.currentAction = currentAction; }
    public void setDecibel(int decibel){ this.decibel = decibel; }
    public void setCameraDebug(Bitmap cameraDebug){ this.cameraDebug = cameraDebug; }
    public void setCamera(Bitmap camera){ this.camera = camera; }

    public void setLowerArea(String lowerArea){ this.lowerArea = lowerArea; }
    public void setUpperArea(String upperArea){ this.upperArea = upperArea; }
    public void setLowerShape(String lowerShape){ this.lowerShape = lowerShape; }
    public void setUpperShape(String upperShape){ this.upperShape = upperShape; }

    public void sendMessage(String[] a, String[] b){
        Message message = new Message(a,b);
        Runnable messageThread = new Runnable() {
            @Override
            public void run() {
                try {
                    client.sendMessage(message.getStringObject());
                    Thread.currentThread().join();
                } catch (IOException | ParseException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(messageThread).start();
    }

    public void receiveMessage() {
        Runnable listenerThread = new Runnable() {
            @Override
            public void run() {
                try {
                    client.startListening();
                    Thread.currentThread().isDaemon();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(listenerThread).start();
    }

    public void pingMessage() {
        Runnable listenerThread = new Runnable() {
            @Override
            public void run() {
                String [] a = {"MT"};
                String [] b = {"PING"};
                Thread.currentThread().isDaemon();
                while (active)
                {
                    Controller.getInstance().sendMessage(a,b);
                    connectionPing = false;
                    try {
                        Thread.currentThread().sleep(9000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!connectionPing)
                        connection = false;
                }
            }
        };
        new Thread(listenerThread).start();
    }
}
