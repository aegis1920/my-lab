package com.bingbong.tcpip.chapter06;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDPEchoClient {

    private final DatagramSocket socket = new DatagramSocket();
    private final int port;
    private final InetAddress address;

    public UDPEchoClient(InetAddress address, int port) throws SocketException {
        this.address = address;
        this.port = port;
//        socket.connect(address, port);
    }

    public String send(String message) throws IOException {
        byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
        // connect 시
//        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        // 문자열을 버퍼로 만들고 packet 생성
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        return new String(packet.getData());
    }

    public void close() {
        socket.close();
    }
}
