package com.example.Network;

import java.io.IOException;
import java.net.*;

public class Client
{
    private InetAddress IpAddress;
    private int Port;
    public Client(String ipAddress, int port) throws UnknownHostException {
        IpAddress = InetAddress.getByName(ipAddress);
        Port = port;
    }

    // LD SD, BR CR, PS
    // EB = stop

    public void sendMessage(String input) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        byte buf[] = null;
        buf = input.getBytes();
        DatagramPacket DpSend =
                new DatagramPacket(buf, buf.length, IpAddress, Port);
        ds.send(DpSend);
    }

    public void receiveMessage(){

    }

}
