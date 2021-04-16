package com.bingbong.tcpip.chapter06;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpEchoServerMain {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket(5001);
        byte[] buffer = new byte[256];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        System.out.println("UDP 서버가 실행됐습니다!");

        while (true) {
            try {
                socket.receive(packet);
                String receivedData = new String(packet.getData(), 0, packet.getLength());
                System.out.println("클라이언트로부터 받은 메시지 : " + receivedData);

                if ("q".equals(receivedData)) {
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
