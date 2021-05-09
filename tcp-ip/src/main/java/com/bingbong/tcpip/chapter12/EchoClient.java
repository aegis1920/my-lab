package com.bingbong.tcpip.chapter12;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class EchoClient {

    private static SocketChannel client;
    private static ByteBuffer buffer;
    private static EchoClient instance;

    public static EchoClient start() {
        if (instance == null) {
            instance = new EchoClient();
        }
        return instance;
    }

    public static void stop() throws Exception {
        client.close();
        buffer = null;
    }

    private EchoClient() {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", 15454));
            buffer = ByteBuffer.allocate(256);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String msg) {
        buffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
        String response = null;
        try {
            client.write(buffer);
            buffer.clear();
            client.read(buffer);
            response = new String(buffer.array()).trim();
            System.out.println("response = " + response);
            buffer.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
