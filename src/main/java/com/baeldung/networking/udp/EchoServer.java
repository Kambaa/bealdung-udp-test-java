package com.baeldung.networking.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class EchoServer extends Thread {

    protected DatagramSocket socket = null;
    protected boolean running;
    protected byte[] buf = new byte[256];

    public EchoServer() throws IOException {
        socket = new DatagramSocket(4445);
        System.out.println("New Data DatagramSocket Instantiated!");
    }

    public void run() {
        running = true;
        System.out.println("Thread Run Started!");
        while (running) {
            try {

                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                System.out.println("New Data DatagramPacket Instantiated!");
                socket.receive(packet);
                System.out.println("socket.receive(packet) called!");

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received = new String(packet.getData(), 0, packet.getLength());
                
                if (received.equals("end")) {
                    running = false;
                    continue;
                }
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }
        }
        socket.close();
    }
}
