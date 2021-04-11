package com.bingbong.tcpip.chapter01;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPEchoServer {

    public static void main(String[] args) {
        Socket socket;

        // 소켓 생성
        try (ServerSocket serverSocket = new ServerSocket()) {
            // 포트, host 바인딩(bind) 및 연결 가능 상태(listen)로 변경
            serverSocket.bind(new InetSocketAddress("localhost", 5001));

            System.out.println("[연결 기다림]");
            socket = serverSocket.accept(); // 클라이언트가 접속해 오기를 기다린다. 접속이 되면 연결을 수락하고 통신용 socket 을 리턴한다.
            System.out.println("[연결 수락함]");

            byte[] bytes;
            String message;

            // 데이터 받기
            InputStream is = socket.getInputStream();
            bytes = new byte[100];
            int readByteCount = is.read(bytes);
            message = new String(bytes, 0, readByteCount, StandardCharsets.UTF_8);
            System.out.println("[데이터 받기 성공] " + message);

            // 데이터 보내기
            OutputStream os = socket.getOutputStream();
            message = "Hello Client";
            bytes = message.getBytes(StandardCharsets.UTF_8);
            os.write(bytes);
            os.flush();
            System.out.println("[데이터 보내기 성공]");

            is.close();
            os.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
