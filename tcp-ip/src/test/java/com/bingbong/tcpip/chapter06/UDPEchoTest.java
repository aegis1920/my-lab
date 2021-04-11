package com.bingbong.tcpip.chapter06;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UDPEchoTest {

    private UDPEchoClient client;

    @BeforeEach
    public void setup() throws UnknownHostException, SocketException {
        int port = 5001;
        new UDPEchoServer(port).start();
        client = new UDPEchoClient(InetAddress.getLocalHost(), port);
    }

    @AfterEach
    void tearDown() throws IOException {
        client.send("q");
        client.close();
    }

    @Test
    void sendMessage() throws IOException {
        String message1 = "Hello World!1";
        String receivedMessage1 = client.send(message1);

        assertThat(receivedMessage1).isEqualTo(message1);

        String message2 = "Hello World!2";
        String receivedMessage2 = client.send(message2);

        assertThat(receivedMessage2).isEqualTo(message2);

        String message3 = "Hello World!3";
        String receivedMessage3 = client.send(message3);

        assertThat(receivedMessage3).isEqualTo(message3);
    }
}
