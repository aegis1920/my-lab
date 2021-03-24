package com.bingbong.tcpip.chapter01;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HelloClient {

    public static void main(String[] args) {
        // 소켓 생성
        try (Socket socket = new Socket()) {
            System.out.println("[연결 요청]");
            // 해당 host, port로 연결
            socket.connect(new InetSocketAddress("localhost", 5001));
            System.out.println("[연결 성공]");

            byte[] bytes;
            String message;

            OutputStream os = socket.getOutputStream();
            message = "Hello Server, I'm Client.";
            bytes = message.getBytes(StandardCharsets.UTF_8);
            os.write(bytes);
            os.flush();
            System.out.println("[데이터 보내기 성공]");

            InputStream is = socket.getInputStream();
            bytes = new byte[100];
            int readByteCount = is.read(bytes);
            message = new String(bytes, 0, readByteCount, StandardCharsets.UTF_8);
            System.out.println("[데이터 받기 성공] " + message);

            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
