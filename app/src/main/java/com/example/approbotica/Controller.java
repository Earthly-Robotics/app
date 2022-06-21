package com.example.approbotica;

import android.graphics.Bitmap;

import com.example.Network.Client;
import com.example.Network.Message;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

public class Controller {

    private boolean noMessage = true; //Test mode on/off
    public boolean getNoMessage(){ return noMessage; }

    private boolean connection = false; //Connection
    private boolean connectionPing = false; //Test variable for connection
    private boolean active = false; //send pings on/off

    private String currentAction = "Manual"; //Action pi is currently doing
    private int battery = 69; //Does nothing (cant read battery with pi or it wil explode)
    private String velocity = "0 m/s";
    private String weight = "0 g";
    private int decibel;
    private int currentGrid;

    private Bitmap cameraDebug = null; //Camera frame (blue block)
    private Bitmap camera = null; //Camera frame (view)

    //settings vision (blue block)
    private String lowerArea = "0";
    private String upperArea = "0";
    private String lowerShape = "0";
    private String upperShape = "0";

    //settings seed
    private String rowAmount = "0";
    private String distanceRow = "0";
    private String seedAmount = "0";
    private String distanceSeed = "0";

    /** Singleton functions*/
    private static Controller controller;
    private Controller(){}
    public static Controller getInstance() {
        if (controller == null)
            controller = new Controller();
        return controller;
    }

    /**
     * Send necessary data to create an Client.
     *
     * @param ip
     * @param port
     * @param socket
     * @return (boolean) depending on if it was created
     */
    private static Client client;
    public boolean setClient(String ip, int port, DatagramSocket socket){
        if (noMessage)
            return true;
        try {
            client = new Client(ip, port, socket);
            active = true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /** get(variable) */
    public boolean getConnection(){ return connection; }

    public String getCurrentAction() { return currentAction; }
    public int getBattery(){ return battery; }
    public String getVelocity(){ return velocity; }
    public String getWeight(){ return weight; }
    public int getDecibel(){ return decibel; }

    public Bitmap getCameraDebug(){ return cameraDebug; }
    public Bitmap getCamera(){ return camera; }

    public String getLowerArea(){return lowerArea; }
    public String getUpperArea(){return upperArea; }
    public String getLowerShape(){return lowerShape; }
    public String getUpperShape(){return upperShape; }

    public String getRowAmount(){return rowAmount; }
    public String getDistanceRow(){return distanceRow; }
    public String getSeedAmount(){return seedAmount; }
    public String getDistanceSeed(){return distanceSeed; }


    /** set(variable) */
    public void setConnection(boolean connection){ this.connection = connection; }
    public void setConnectionPing(boolean connectionPing){ this.connectionPing = connectionPing; }
    public void setActive(boolean active){ this.active = active; }

    public void setCurrentAction(String currentAction) { this.currentAction = currentAction; }
    public void setVelocity(String velocity){ this.velocity = velocity; }
    public void setWeight(String weight){this.weight = weight; }
    public void setDecibel(int decibel){ this.decibel = decibel; }

    public void setCameraDebug(Bitmap cameraDebug){ this.cameraDebug = cameraDebug; }
    public void setCamera(Bitmap camera){ this.camera = camera; }

    public void setLowerArea(String lowerArea){ this.lowerArea = lowerArea; }
    public void setUpperArea(String upperArea){ this.upperArea = upperArea; }
    public void setLowerShape(String lowerShape){ this.lowerShape = lowerShape; }
    public void setUpperShape(String upperShape){ this.upperShape = upperShape; }

    public void setRowAmount(String rowAmount){ this.rowAmount = rowAmount; }
    public void setDistanceRow(String distanceRow){ this.distanceRow = distanceRow; }
    public void setSeedAmount(String seedAmount){ this.seedAmount = seedAmount; }
    public void setDistanceSeed(String distanceSeed){ this.distanceSeed = distanceSeed; }

    /**
     * Creates an Message what is an json object.
     * Sends json String from created message to Client.
     *
     * @param a an string array to make an json string. Names
     * @param b an string array to make an json string. Variables
     */
    public void sendMessage(String[] a, String[] b){
        if (noMessage)
            return;
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

    /**
     * TODO see if there are not too many threads created at once
     */
    public void receiveMessage() {
        if (noMessage)
            return;
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

    /**
     * Sets connectionPing on false.
     * Sends PING message and waits for 9 seconds.
     * Client disconnects with the app after 20 seconds of not receiving an PING message. Thats why the wait is 9 seconds so atleast 2 PING messages are send.
     *
     *      When the other side receives that message it sends an PING message back.
     *      if that message is received
     * connectionPing is set on true
     *      else
     * connectionPing is still on false
     *
     * After the 9 seconds connection is set on the same boolean as connectionPing
     */
    public void pingMessage() {
        if (noMessage){
            connection = true;
            return;
        }
        Runnable listenerThread = new Runnable() {
            @Override
            public void run() {
                String [] a = {"MT"};
                String [] b = {"PING"};
                Thread.currentThread().isDaemon();
                while (active)
                {
                    connectionPing = false;
                    Controller.getInstance().sendMessage(a,b);
                    try {
                        Thread.currentThread().sleep(9000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    connection = connectionPing;
                    if (!connection){
                        currentAction = "Manual"; //Resets current action (also done in Client)
                    }
                }
            }
        };
        new Thread(listenerThread).start();
    }
}
