package com.bingbong.effectivejava.item29;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NotGenericStackTest {

    @DisplayName("다른 타입이 나온다")
    @Test
    void pushAndPopTest() {
        NotGenericStack stack = new NotGenericStack();

        stack.push(1);
        stack.push("1"); // Object라서 다 들어간다

        // 서로 다른 클래스!
        assertThat(stack.pop()).isInstanceOf(String.class);
        assertThat(stack.pop()).isInstanceOf(Integer.class);
    }
}
