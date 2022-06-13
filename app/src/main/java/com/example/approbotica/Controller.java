package com.example.approbotica;

import com.example.Network.Client;
import com.example.Network.Message;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

public class Controller {
    private boolean connection;
    private int battery = 69;
    private String velocity = "0 m/s";
    private String weight = "0 g";
    private int decibel;
    private int currentGrid;
    private String currentAction = "Manual";

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
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
        if (!testConnection())
            return false;
        return true;
    }

    //TODO function to test connection
    public boolean testConnection()
    {
        connection = true;
        return connection;
    }

    public boolean getConnection(){ return connection; }
    public int getBattery(){ return battery; }
    public String getVelocity(){ return velocity; }
    public String getWeight(){ return weight; }
    public String getCurrentAction() { return currentAction; }
    public int getDecibel(){ return decibel; }

    public void setConnection(boolean connection){ this.connection = connection; }
    public void setBattery(int battery){ this.battery = battery; }
    public void setVelocity(String velocity){ this.velocity = velocity; }
    public void setWeight(String weight){this.weight = weight; }
    public void setCurrentAction(String currentAction) { this.currentAction = currentAction; }
    public void setDecibel(int decibel){ this.decibel = decibel; }

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

}
