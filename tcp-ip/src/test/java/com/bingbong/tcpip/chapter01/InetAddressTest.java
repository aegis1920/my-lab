package com.bingbong.tcpip.chapter01;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InetAddressTest {

    @DisplayName("localhost를 인식해 IP 주소 확인")
    @Test
    void getLocalHostTest() throws UnknownHostException {

        // 요청할 때마다 받는 IP가 달라진다
        // InetAddress inetAddress = InetAddress.getByName("www.naver.com");
        InetAddress inetAddress = InetAddress.getLocalHost();

        assertThat(inetAddress.getHostAddress()).isEqualTo("127.0.0.1");
        assertThat(inetAddress.getHostName()).isNotNull();
        assertThat(inetAddress.getAddress()).containsExactly(127, 0, 0, 1);
    }

    @DisplayName("IP 주소가 1바이트를 넘을 경우(0미만 또는 255 초과) 예외 처리")
    @ValueSource(strings = {"256", "-1"})
    @ParameterizedTest
    void getByName_ThrownException(String input) {
        assertThatThrownBy(() -> InetAddress.getByName("127." + input + ".124.79"))
            .isInstanceOf(UnknownHostException.class);
    }

    @DisplayName("IP 주소를 바이트 배열로 표현")
    @Test
    void getAddress() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName("127.255.124.79");

        // java는 unsigned byte가 없어서 127까지만 표현가능하기 때문에 255가 -1로 나온다.
        assertThat(inetAddress.getAddress()).containsExactly(127, -1, 124, 79);

        // 그런데 containsExactly 내부적으로 toByteArray를 진행하고 있어서 그대로 255를 넣어도 된다.
        assertThat(inetAddress.getAddress()).containsExactly(127, 255, 124, 79);
    }
}
