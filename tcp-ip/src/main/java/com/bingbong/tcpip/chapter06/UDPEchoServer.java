package com.bingbong.tcpip.chapter06;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPEchoServer extends Thread {

    private final DatagramSocket socket;
    private final byte[] buffer = new byte[256]; // 버퍼의 초기 설정

    public UDPEchoServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
        System.out.println(port + " 포트로 UDP 서버가 실행됐습니다!");
    }

    @Override
    public void run() {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            try {
                socket.receive(packet); // 클라이언트로부터 패킷을 받길 대기중...
                // 받은 패킷의 길이와 주소, 포트로 UDP 패킷을 다시 만들어준다. 그래야 받은 문자열 길이만큼 버퍼가 만들어짐
                packet = new DatagramPacket(buffer, packet.getLength(), packet.getAddress(), packet.getPort());
                // 받은 패킷의 바이트 배열은 초기 buffer 크기와 같음.
                // 그래서 그냥 packet.getData()만 넣어주면 초기 buffer 크기와 같아서 남은 배열 즉, Hello World 뒤에 null이 찍힘
                String receivedData = new String(packet.getData(), 0, packet.getLength());
                System.out.println("클라이언트로부터 받은 메시지 : " + receivedData);

                if ("q".equals(receivedData)) { // 클라이언트로부터 받은 메시지가 "q"라면 종료
                    socket.send(packet); // 클라이언트가 계속 receive하고 있을테니까 보내주고 종료
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

//        for (int i = 0; i < 4; i++) {
//            try {
//                Thread.sleep(5000);
//                socket.receive(packet); // 클라이언트로부터 패킷을 받길 대기중...
//                // 받은 패킷의 길이와 주소, 포트로 UDP 패킷을 다시 만들어준다. 그래야 받은 문자열 길이만큼 버퍼가 만들어짐
//                packet = new DatagramPacket(buffer, packet.getLength(), packet.getAddress(), packet.getPort());
//                // 받은 패킷의 바이트 배열은 초기 buffer 크기와 같음.
//                // 그래서 그냥 packet.getData()만 넣어주면 초기 buffer 크기와 같아서 남은 배열 즉, Hello World 뒤에 null이 찍힘
//                String receivedData = new String(packet.getData(), 0, packet.getLength());
//                System.out.println("클라이언트로부터 받은 메시지 : " + receivedData);
//
//                if ("q".equals(receivedData)) { // 클라이언트로부터 받은 메시지가 "q"라면 종료
//                    socket.send(packet); // 클라이언트가 계속 receive하고 있을테니까 보내주고 종료
//                    System.out.println("서버를 종료합니다.");
//                    break;
//                }
//                System.out.println("클라이언트로부터 받은 메시지를 재전송합니다.");
//                socket.send(packet);
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//                break;
//            }
//        }

        // 스레드 끝날 때 소켓도 종료
        socket.close();
    }
}
