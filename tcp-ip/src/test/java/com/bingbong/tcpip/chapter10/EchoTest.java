package com.bingbong.tcpip.chapter10;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.bingbong.tcpip.chapter12.EchoClient;
import com.bingbong.tcpip.chapter12.EchoServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EchoTest {

    Process server;
    EchoClient client;

    @BeforeEach
    void setUp() throws Exception {
        server = EchoServer.start();
        client = EchoClient.start();
    }

    @DisplayName("클라이언트에서 문자열을 입력하면 똑같이 서버에서 온다")
    @Test
    void serverEchoMessage() {
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");

        assertThat("hello").isEqualTo(resp1);
        assertThat("world").isEqualTo(resp2);
    }

    @AfterEach
    void tearDown() throws Exception {
        server.destroy();
        EchoClient.stop();
    }
}
