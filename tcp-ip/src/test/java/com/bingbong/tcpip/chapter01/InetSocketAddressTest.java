package com.bingbong.tcpip.chapter01;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetSocketAddress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InetSocketAddressTest {


    @DisplayName("port만 입력하면 모든 IP를 의미하는 0.0.0.0이 나온다")
    @Test
    void inetSocketAddressWihtPort() {
        int port = 5001;
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);

        assertThat(inetSocketAddress.getAddress().toString()).hasToString("0.0.0.0/0.0.0.0");
        assertThat(inetSocketAddress.getHostName()).isEqualTo("0.0.0.0");
        assertThat(inetSocketAddress.getHostString()).isEqualTo("0.0.0.0");
        assertThat(inetSocketAddress.getPort()).isEqualTo(5001);
    }

    @DisplayName("hostname을 인식해서 localhost를 입력하면 127.0.0.1이 나온다")
    @Test
    void inetSocketAddressWihtPortAndHostname() {
        String hostname = "localhost";
        int port = 5001;
        InetSocketAddress inetSocketAddress = new InetSocketAddress(hostname, port);

        assertThat(inetSocketAddress.getAddress().toString()).hasToString("localhost/127.0.0.1");
        assertThat(inetSocketAddress.getHostName()).isEqualTo("localhost");
        assertThat(inetSocketAddress.getHostString()).isEqualTo("localhost");
        assertThat(inetSocketAddress.getPort()).isEqualTo(5001);
    }
}
