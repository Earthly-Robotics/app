package com.example.Network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.approbotica.Controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    private InetAddress IpAddress;
    private int Port;
    private DatagramSocket Socket;
    boolean ReceiveMessage = false;
    private List<Thread> threads = new ArrayList<Thread>();
    private Lock lock = new ReentrantLock();

    public Client(String ipAddress, int port, DatagramSocket socket) throws UnknownHostException {
        IpAddress = InetAddress.getByName(ipAddress);
        Port = port;
        Socket = socket;
    }

    public void sendMessage(String input) throws IOException, ParseException {
        byte buf[] = null;
        buf = input.getBytes();
        DatagramPacket DpSend =
                new DatagramPacket(buf, buf.length, IpAddress, Port);
        Socket.send(DpSend);
    }

    //TODO Gooi Receive op een Thread
    public void startListening() throws IOException, ParseException, InterruptedException {
        boolean loop = true;
        int i = 0;
        while (loop){
            byte[] buf = new byte[1000000];
            //Receive Message
            DatagramPacket DpReceive = new DatagramPacket(buf, buf.length);
            Socket.receive(DpReceive);
            //Delete unnecessary bytes
            byte[] received = new byte[DpReceive.getLength()];
            System.arraycopy(DpReceive.getData(), DpReceive.getOffset(), received, 0, DpReceive.getLength());
            //Convert String to JsonObject
            JSONObject jsonObject = new Message(new String(received)).getJSONObject();
            int finalI = i;
            new Thread(() -> {
                handleMessage(jsonObject);
                try {
                    System.out.println("Closed thread: " + finalI);
                    Thread.currentThread().join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public Bitmap getImage(String bits)
    {
        byte[] decodedString = Base64.decode(bits, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    private void handleMessage(JSONObject jsonObject){
        switch (jsonObject.get("MT").toString()) {
            case "BATTERY"://Camera Feed
                lock.lock();
                Controller.getInstance().setBattery((Integer) jsonObject.get("Battery"));
                lock.unlock();
                break;
            case "VELOCITY":// Orientation & Speed
                lock.lock();
                Controller.getInstance().setVelocity("" + jsonObject.get("Velocity"));
                lock.unlock();
                break;
            case "WEIGHT":// Battery Percentage
                lock.lock();
                Controller.getInstance().setWeight("" + jsonObject.get("Weight") + " g");
                lock.unlock();
                break;
            case "DECIBEL":// Weight
                lock.lock();
                Controller.getInstance().setDecibel((Integer) jsonObject.get("Decibel"));
                lock.unlock();
                break;
            case "CAMERA":
                lock.lock();
                Controller.getInstance().setCamera(getImage("" + jsonObject.get("Camera")));
                lock.unlock();
                break;
            case "CAMERA_DEBUG":// Weight
                lock.lock();
                Controller.getInstance().setCameraDebug(getImage("" + jsonObject.get("Camera_Debug")));
                lock.unlock();
                break;
            case "BLUE_BLOCK_VALUES":
                lock.lock();
                Controller.getInstance().setLowerArea("" + jsonObject.get("Lower_Area"));
                Controller.getInstance().setUpperArea("" + jsonObject.get("Upper_Area"));
                Controller.getInstance().setLowerShape("" + jsonObject.get("Lower_Shape"));
                Controller.getInstance().setUpperShape("" + jsonObject.get("Upper_Shape"));
                lock.unlock();
                break;
            case "LINE_DANCE":
                lock.lock();
                Controller.getInstance().setCurrentAction("Line Dance");
                lock.unlock();
                break;
            case "SOLO_DANCE":
                lock.lock();
                Controller.getInstance().setCurrentAction("Solo Dance");
                lock.unlock();
                break;
            case "PLANT":
                lock.lock();
                Controller.getInstance().setCurrentAction("Plant Seeds");
                lock.unlock();
                break;
            case "BLUE_BLOCK":
                lock.lock();
                Controller.getInstance().setCurrentAction("Recognize Blue Block");
                lock.unlock();
                break;
            case "MANUAL":
                lock.lock();
                Controller.getInstance().setCurrentAction("Manual");
                lock.unlock();
                break;
            case "PING":
                lock.lock();
                Controller.getInstance().setConnection(true);
                Controller.getInstance().setConnectionPing(true);
                lock.unlock();
                break;
        }
    }
}
