package com.example.approbotica;

import com.example.Network.Client;
import com.example.Network.Message;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Controller {
    private boolean connection;
    private int battery = 69;
    private int speed = 0;
    private int weight = 0;
    private int[] decibel;
    private String currentGrid;
    private String cameraFeed;
    private String currentAction = "controller";

    private static DatagramSocket socket;

    static {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
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

    private static Client client;

    static {
        try {
            client = new Client("192.168.50.1", 8080, socket);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public boolean getConnection(){ return connection; }
    public int getBattery(){ return battery; }
    public int getSpeed(){ return speed; }
    public int getWeight(){ return weight; }
    public String getCurrentAction() { return currentAction; }

    public void setConnection(boolean connection){ this.connection = connection; }
    public void setBattery(int battery){ this.battery = battery; }
    public void setSpeed(int speed){ this.speed = speed; }
    public void setWeight(int weight){this.weight = weight; }
    public void setCurrentAction(String currentAction) { this.currentAction = currentAction; }

    public void sendSignal(String[] a, String[] b){
        Message message = new Message(a,b);
        Runnable sendAMessage = new Runnable() {
            @Override
            public void run() {
                try {
                    client.sendMessage(message.getStringObject());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(sendAMessage).start();
    }

    public boolean setConnection(){
        connection = true;
        //do connection stuff here
        return connection;
    }
}
