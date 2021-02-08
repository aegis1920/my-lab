package com.bingbong.effectivejava.item24;

import static org.assertj.core.api.Assertions.assertThat;

import com.bingbong.effectivejava.item24.Calculator.Operation;
import org.junit.jupiter.api.Test;

class NestedClassTest {

    @Test
    void plusTest() {
        Calculator c = new Calculator();

        assertThat(c.sum(1, 2)).isEqualTo(3);
        assertThat(Operation.PLUS.name()).isEqualTo("PLUS");
    }
}
