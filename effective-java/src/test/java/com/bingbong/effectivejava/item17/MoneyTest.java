package com.bingbong.effectivejava.item17;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyTest {

    @DisplayName("불변 클래스 plus")
    @Test
    void plusTest() {
        assertThat(new Money(1000).plus(1000).getValue()).isEqualTo(2000);
    }
}
