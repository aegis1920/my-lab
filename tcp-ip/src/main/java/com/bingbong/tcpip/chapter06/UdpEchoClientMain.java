package com.bingbong.tcpip.chapter06;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UdpEchoClientMain {

    private static final int PORT = 5001;

    public static void main(String[] args) throws Exception {

        // DatagramSocket에 address와 port를 bind해줄 수 있음
        // 만약 아무런 매개변수 없이 만들면 로컬 IP와 0번 port를 bind함
        // 근데 포트가 0번이면 비어있는 랜덤 임시 포트가 할당됨
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getLocalHost();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            System.out.print("Insert message(q to quit): ");
            String message = br.readLine();
            byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);

            if ("q".equals(message)) {
                socket.send(packet);
                System.out.println("클라이언트를 종료합니다.");
                break;
            }

            socket.send(packet);
            socket.receive(packet);
            System.out.println("Message from server: " + new String(packet.getData()));
        }

        socket.close();
    }
}
