package com.example.Network;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client
{
    private InetAddress ipAddress;
    private int port;
    private DatagramSocket socket;
    public Client(String ipAddress, int port, DatagramSocket socket) throws UnknownHostException {
        this.ipAddress = InetAddress.getByName(ipAddress);
        this.port = port;
        this.socket = socket;
    }

    // LD SD, BR CR, PS
    // EB = stop

    public void sendMessage(String input) throws IOException {
        byte buf[] = null;
        buf = input.getBytes();
        DatagramPacket DpSend =
                new DatagramPacket(buf, buf.length, ipAddress, port);
        socket.send(DpSend);
    }

    public String receiveMessage() throws IOException {
        byte[] buf = new byte[1024];
        DatagramPacket DpReceive = new DatagramPacket(buf, buf.length);
        socket.receive(DpReceive);
        byte[] received = new byte[DpReceive.getLength()];
        System.arraycopy(DpReceive.getData(), DpReceive.getOffset(), received, 0, DpReceive.getLength());
        System.out.println("Received Message");
        return new String(received);
    }

}
