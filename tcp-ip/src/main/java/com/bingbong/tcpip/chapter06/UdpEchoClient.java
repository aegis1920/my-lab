package com.bingbong.tcpip.chapter06;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UdpEchoClient {

    private final DatagramSocket socket = new DatagramSocket();
    private final int port;
    private final InetAddress address;

    public UdpEchoClient(InetAddress address, int port) throws SocketException {
        this.address = address;
        this.port = port;
    }

    public void makeConnectedUdp() {
        socket.connect(address, port);
    }

    public String send(String message) throws IOException {
        byte[] buffer = message.getBytes(StandardCharsets.UTF_8);

        DatagramPacket packet;

        if (socket.isConnected()) {
            packet = new DatagramPacket(buffer, buffer.length);
        } else {
            packet = new DatagramPacket(buffer, buffer.length, address, port);
        }

        socket.send(packet);
        socket.receive(packet);

        return new String(packet.getData());
    }

    public void close() {
        socket.close();
    }

    public boolean isConnected() {
        return socket.isConnected();
    }
}
