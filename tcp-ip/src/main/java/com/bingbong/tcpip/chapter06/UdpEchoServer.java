package com.bingbong.tcpip.chapter06;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpEchoServer extends Thread {

    private final DatagramSocket socket;
    private final byte[] buffer = new byte[256];

    public UdpEchoServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
        System.out.println(port + " 포트로 UDP 서버가 실행됐습니다!");
    }

    @Override
    public void run() {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            try {
                socket.receive(packet);
                packet = new DatagramPacket(buffer, packet.getLength(), packet.getAddress(), packet.getPort());
                String receivedData = new String(packet.getData(), 0, packet.getLength());
                System.out.println("클라이언트로부터 받은 메시지 : " + receivedData);

                if ("q".equals(receivedData)) {
                    socket.send(packet);
                    System.out.println("서버를 종료합니다.");
                    break;
                }
                System.out.println("클라이언트로부터 받은 메시지를 재전송합니다.");
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        socket.close();
    }
}
